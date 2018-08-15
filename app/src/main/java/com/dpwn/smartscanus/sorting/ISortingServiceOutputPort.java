package com.dpwn.smartscanus.sorting;

import com.dpwn.smartscanus.newopsapi.resources.MessageResponse;

/**
 * The output port for sending all
 *
 * Created by Darshan Patel on 08/10/2018.
 */
public interface ISortingServiceOutputPort  {

    /**
     * This will handle all positive response returned from the API
     * @param msg
     */
    void linkScanSuccessful(MessageResponse msg);

    /**
     * This method will handle all negative response returned from the API
     * @param msg
     */
    void linkScanError(MessageResponse msg);

}
