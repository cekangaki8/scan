package com.dpwn.smartscanus.newopsapi.resources;

/**
 * The request pojo for sorting scan genericBarcode end point
 * <p/>
 * Created by Darshan on 08/13/2018.
 */
public class LinkSackRequest {

    /**
     * The scanned genericBarcode.
     */
    private String scannedGenericBarcode;

    /**
     * The finalslot for association.
     */
    private String slot;

    /**
     * Constructor with scannedMailItemBarcode
     *
     * @param scannedGenericBarcode scanned mail item barcode
     */
    public LinkSackRequest(String scannedGenericBarcode,String slot) {
        this.scannedGenericBarcode = scannedGenericBarcode;
        this.slot = slot;
    }

    /**
     * Getter for the genericBarcode
     *
     * @return the genericBarcode
     */
    public String getScannedGenericBarcode() {
        return scannedGenericBarcode;
    }

    /**
     * Setter for the scannedGenericBarcode
     *
     * @param scannedGenericBarcode scanned mail item barcode
     */
    public void setScannedGenericBarcode(String scannedGenericBarcode) {
        this.scannedGenericBarcode = scannedGenericBarcode;
    }

    /**
     * Getter for the slot
     * @return the slot
     */
    public String getSlot() {
        return slot;
    }

    /**
     * Setter for the slot
     * @param slot scanned mail item barcode
     */
    public void setSlot(String slot) {
        this.slot = slot;
    }

}
