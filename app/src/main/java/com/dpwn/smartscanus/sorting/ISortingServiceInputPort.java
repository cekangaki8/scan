package com.dpwn.smartscanus.sorting;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorInputPort;

/**
 * Interface for the endpoints associated with sorting module in NOC
 * <p/>
 * Created by Darshan Patel on 08/10/2018.
 */
public interface ISortingServiceInputPort extends IAsyncInteractorInputPort {

    /**
     * This will call the processGenericBarcode endpoint for sorting WS in NOC.
     *
     * @param genericBarcode
     * @param slot
     */

    void processLinkSackScan(String genericBarcode, String slot);
}