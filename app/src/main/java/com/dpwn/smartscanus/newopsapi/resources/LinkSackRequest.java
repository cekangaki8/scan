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
    private String GenericBarcode;

    /**
     * The finalslot for association.
     */
    private String FinalSlot;

    /**
     * Constructor with scannedMailItemBarcode
     *
     * @param genericBarcode scanned mail item barcode
     */
    public LinkSackRequest(String genericBarcode,String slot) {
        this.GenericBarcode = genericBarcode;
        this.FinalSlot = slot;
    }

    /**
     * Getter for the genericBarcode
     *
     * @return the genericBarcode
     */
    public String getGenericBarcode() {
        return GenericBarcode;
    }

    /**
     * Setter for the GenericBarcode
     *
     * @param genericBarcode scanned mail item barcode
     */
    public void setGenericBarcode(String genericBarcode) {
        this.GenericBarcode = genericBarcode;
    }

    /**
     * Getter for the FinalSlot
     * @return the FinalSlot
     */
    public String getFinalSlot() {
        return FinalSlot;
    }

    /**
     * Setter for the FinalSlot
     * @param finalSlot scanned mail item barcode
     */
    public void setFinalSlot(String finalSlot) {
        this.FinalSlot = finalSlot;
    }

}
