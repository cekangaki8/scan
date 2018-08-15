package com.dpwn.smartscanus.sorting.interactor;

import com.dpwn.smartscanus.interactor.async.AbstractAsyncInteractor;
import com.dpwn.smartscanus.newopsapi.ISortingServiceApi;
import com.dpwn.smartscanus.sorting.ISortingServiceInputPort;
import com.google.inject.Inject;

/**
 * Created by cekangak on 8/9/2018.
 */
public class SortingServiceInteractor extends AbstractAsyncInteractor implements ISortingServiceInputPort {

    private static final String TAG = SortingServiceInteractor.class.getSimpleName();

    @Inject
    protected ISortingServiceApi sortingServiceApi;

    /**
     * Empty Constructor required
     */
    public SortingServiceInteractor() {
    }
}
