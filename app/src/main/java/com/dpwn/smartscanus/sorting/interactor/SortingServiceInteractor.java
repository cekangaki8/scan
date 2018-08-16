package com.dpwn.smartscanus.sorting.interactor;

import com.dpwn.smartscanus.interactor.IInteractorOutputPort;
import com.dpwn.smartscanus.interactor.async.AbstractAsyncInteractor;
import com.dpwn.smartscanus.interactor.async.IUnauthorizedOutputPort;
import com.dpwn.smartscanus.logging.L;

import com.dpwn.smartscanus.newopsapi.ISortingServiceApi;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;

import com.dpwn.smartscanus.newopsapi.resources.LinkSackRequest;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.sorting.ISortingServiceInputPort;
import com.dpwn.smartscanus.sorting.ISortingServiceOutputPort;
import com.google.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import retrofit.RetrofitError;
import rx.Observer;

/**
 * Created by Darshan on 08/13/2018.
 */
public class SortingServiceInteractor extends AbstractAsyncInteractor implements ISortingServiceInputPort {

    private static final String TAG = SortingServiceInteractor.class.getSimpleName();

    @Inject
    protected ISortingServiceApi sortingServiceApi;

    /**
     * This will call the processGenericBarcode for sorting in NOC.
     * <p/>
     * It is used for genericBarcode and slot/receptacle association
     *
     * @param barcode
     * @param slot
     */
    @Override
    public void processLinkSackScan(String barcode, String slot) {
        sendProgressIndication("Sending genericBarcode and slot to Newops ...");
        barcode = StringUtils.remove(barcode, "\u001d");
        slot = StringUtils.remove(slot, "\u001d");
        subscription = sortingServiceApi.processLinkSackScan(new LinkSackRequest(barcode, slot))
                .subscribeOn(ioSchedular)
                .observeOn(uiSchedular)
                .subscribe(new Observer<BarcodeResponse>() {

                    @Override
                    public void onCompleted() {
                        sendProgressFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        sendProgressFinished();
                        if (e instanceof RetrofitError) {
                            handleRetrofitError((RetrofitError) e);
                        } else {
                            sendUnknownErrorOccured(600, "Not specified");
                        }
                    }

                    @Override
                    public void onNext(BarcodeResponse response) {
                        if (response != null && response.getStatus() != null) {
                            if ("0".equals(response.getStatus().getReturnCode())) {
                                sendSuccessfulMessage(new MessageResponse(response.getResponse().getDescription()));
                            } else {
                                sendErrorMessage(new MessageResponse(response.getStatus().getMessage()));
                            }
                        } else {
                            sendErrorMessage(new MessageResponse("Error occurred during authentication."));
                        }
                    }

                });
    }

    private void sendSuccessfulMessage(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((ISortingServiceOutputPort) outputPort).linkScanSuccessful(msg);
            } catch (Exception ex) {
                L.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void sendErrorMessage(MessageResponse msg) {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((ISortingServiceOutputPort) outputPort).linkScanError(msg);
            } catch (Exception ex) {
                L.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void handleRetrofitError(RetrofitError error) {
        if (error.isNetworkError()) {
            networkError();
            return;
        }
        int statusCode = error.getResponse().getStatus();
        switch (statusCode) {
            case 401:
                sendUnauthorized();
                break;
            default:
                sendUnknownErrorOccured(statusCode, error.getResponse().getReason());
                break;
        }
    }

    private void sendUnauthorized() {
        for (IInteractorOutputPort outputPort : outputPorts) {
            try {
                ((IUnauthorizedOutputPort) outputPort).unauthorizedRequest();
            } catch (Exception ignored) {
                L.i(TAG, ignored.getMessage(), ignored);
            }
        }
    }

}
