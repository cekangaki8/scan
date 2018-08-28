package com.dpwn.smartscanus.sorting.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.events.RingBeepEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.interactor.IInteractorInputPort;
import com.dpwn.smartscanus.interactor.async.AsyncInteractorFragment;
import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;
import com.dpwn.smartscanus.sorting.ISortingServiceInputPort;
import com.dpwn.smartscanus.sorting.ISortingServiceOutputPort;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Darshan Patel on 08/10/2018.
 */
public class LinkSackFragment extends AsyncInteractorFragment implements ISortingServiceOutputPort {
    private static final java.lang.String TAG = LinkSackFragment.class.getSimpleName();

    @InjectView(R.id.genericBarcodeText)
    TextView tvGenericBarcode;

    @InjectView(R.id.finalSlotText)
    TextView tvFinalSlot;

    @InjectView(R.id.link_sack_ErrorMessage)
    TextView tvErrorMessage;

    @InjectView(R.id.loadSackMessagePane)
    LinearLayout loadSackMessagePane;

    @Inject
    ISortingServiceInputPort interactor;

    int defaultColor;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvGenericBarcode.requestFocus();
        ColorDrawable defaultPaneColor = (ColorDrawable) loadSackMessagePane.getBackground();
        defaultColor = defaultPaneColor.getColor();
    }
    /**
     * Abstract method to get the interactors of the implementing Fragment
     *
     * @return web interactors for registration as a output ports
     */
    @Override
    public List<IInteractorInputPort> getInteractors() {
        ArrayList<IInteractorInputPort> interactors = new ArrayList<IInteractorInputPort>();
        interactors.add(interactor);
        return interactors;
    }

    /**
     * Event handler method for barcode and slot read
     *
     * @param barcode
     */
    @Override
    public void onBarcodeRead(String barcode) {
        if (outputPortHelper.isInProgress()) {
            tts("Please wait while in progress");
        } else {

            boolean isGenericBarcode = true;
            if (StringUtils.isNotBlank(tvGenericBarcode.getText()) && StringUtils.isBlank(tvFinalSlot.getText())) {
                isGenericBarcode = false;
            }

            if (isGenericBarcode) {
                tvGenericBarcode.setText(barcode);
                tvFinalSlot.setText("");
                tvErrorMessage.setText("");
                loadSackMessagePane.setBackgroundColor(defaultColor);
            } else  {
                tvFinalSlot.setText(barcode);
                interactor.processLinkSackScan(tvGenericBarcode.getText().toString(), tvFinalSlot.getText().toString());
            }
        }
    }

    /**
     * Abstract method for initializing the layout with the default method overrides like
     * onCreateView etc.
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_sorting_linksack;
    }

    /**
     * OnClick EventListener method that is called by OnClickSender
     *
     * @param view widget that is being clicked.
     */
    @Override
    public void onClick(View view) {

    }

    /**
     * This will handle all positive respsonse returned from the API
     *
     * @param msg
     */
    @Override
    public void linkScanSuccessful(MessageResponse msg) {
        loadSackMessagePane.setBackgroundColor(Color.GREEN);
        tvErrorMessage.setText(msg.getMsg());
        tvGenericBarcode.requestFocus();
    }

    /**
     * This method will handle all negative response returned from the API
     *
     * @param msg
     */
    @Override
    public void linkScanError(MessageResponse msg) {
        loadSackMessagePane.setBackgroundColor(Color.RED);
        tvErrorMessage.setText(msg.getMsg());
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
        tts(msg.getMsg());
        bus.post(new RingBeepEvent());
        tvGenericBarcode.requestFocus();
    }
}
