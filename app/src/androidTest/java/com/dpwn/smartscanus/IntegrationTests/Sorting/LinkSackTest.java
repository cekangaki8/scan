package com.dpwn.smartscanus.IntegrationTests.Sorting;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpwn.smartscanus.IntegrationTests.BaseIntegrationTests;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.dspscanning.ui.DspScanningFragment;
import com.dpwn.smartscanus.fullServiceImb.ui.ScanFullSvcIMBFragment;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.dpwn.smartscanus.sorting.ui.LinkSackFragment;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Darshan on 08/14/2018.
 */

public class LinkSackTest extends BaseIntegrationTests {


    public void testLinkSack() throws InterruptedException {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(LinkSackFragment.class, true, null);

        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof LinkSackFragment);
            }
        }, 3000);

        LinearLayout loadSackMessagePane = (LinearLayout) solo.getView(R.id.loadSackMessagePane);
        TextView tvNextDayMsg = (TextView) solo.getView(R.id.link_sack_ErrorMessage);
        TextView tvGenericBarcodeText = (TextView) solo.getView(R.id.genericBarcodeText);
        TextView tvFinalSlotText = (TextView) solo.getView(R.id.finalSlotText);

        //Valid link of barcode to slot
        //Input valid generic barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592111");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592111", tvGenericBarcodeText.getText().toString());
        //Input valid slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A64");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A64",tvFinalSlotText.getText().toString());
        //Assertions
        solo.waitForText("Barcode has been successfully linked to slot!");
        assertEquals("Barcode has been successfully linked to slot!", tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        int backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.GREEN, backGroundColorValidMailItem); //validating background color

        //blank slot
        //Input valid generic barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592111");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592111", tvGenericBarcodeText.getText().toString());
        //Input empty slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertTrue(StringUtils.isBlank(tvFinalSlotText.getText().toString()));
        String ExpectedMsg = "Slot name either blank or empty";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

/*
        //slot not found
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592111";
        scanInputSlot = "A53";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Slot Not Found";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //Invalid/Empty barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "";
        scanInputSlot = "A64";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Generic Barcode either empty or not a valid length";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Generic barcode already processed
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592999";
        scanInputSlot = "A64";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Generic BarCode Already Processed";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //DeliveryPoint Not Found
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592222";
        scanInputSlot = "A75";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "DeliveryPoint Not Found";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Linknotsuccessful
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592000";
        scanInputSlot = "A75";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Link not successful - please try again!";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color
*/
    }

    /*public void testLinkSackOrientation() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(DspScanningFragment.class, true, null);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {

                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof DspScanningFragment);
            }
        }, 3000);


        LinearLayout loadSackMessagePane = (LinearLayout) solo.getView(R.id.img_nextdayscan);
        TextView tvNextDayMsg = (TextView) solo.getView(R.id.link_sack_ErrorMessage);

        //valid genericBarcode
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        String scanInputBarcode = "809056031899366000592111";
        String scanInputSlot = "A64";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        String ExpectedMsg = "809056031899366000592111";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        ColorDrawable colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        int backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.GREEN, backGroundColorValidMailItem); //validating background color

        //blank slot
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592111";
        scanInputSlot = "";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Slot name either blank or empty";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //slot not found
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592111";
        scanInputSlot = "A53";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Slot Not Found";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Empty barcode
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "";
        scanInputSlot = "A64";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Generic Barcode either empty or not a valid length";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Generic barcode already processed
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592999";
        scanInputSlot = "A64";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Generic BarCode Already Processed";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //DeliveryPoint Not Found
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592222";
        scanInputSlot = "A75";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "DeliveryPoint Not Found";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Linknotsuccessful
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInputBarcode = "809056031899366000592000";
        scanInputSlot = "A75";
        solo.enterText(0, scanInputBarcode);
        solo.enterText(0, scanInputSlot);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Link not successful - please try again!";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());

        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

    }*/
}
