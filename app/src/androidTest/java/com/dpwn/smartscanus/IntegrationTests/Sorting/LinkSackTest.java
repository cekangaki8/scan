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
        assertEquals("A64", tvFinalSlotText.getText().toString());
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

        //slot not found
        //Input Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592111");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592111", tvGenericBarcodeText.getText().toString());
        //Input Slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A53");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A53", tvFinalSlotText.getText().toString());
        //Assert response
        solo.waitForText("Slot Not Found");
        assertEquals("Slot Not Found", tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //Invalid/Empty barcode
        //Input Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertTrue(StringUtils.isBlank(tvGenericBarcodeText.getText().toString()));
        //Input Slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A64");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A64", tvFinalSlotText.getText().toString());
        //Assert response
        solo.waitForText("Generic Barcode either empty or not a valid length");
        assertEquals("Generic Barcode either empty or not a valid length", tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Generic barcode already processed
        //Input Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592999");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592999", tvGenericBarcodeText.getText().toString());
        //Input Slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A64");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A64", tvFinalSlotText.getText().toString());
        //Assert response
        solo.waitForText("Generic BarCode Already Processed");
        assertEquals("Generic BarCode Already Processed", tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


        //DeliveryPoint Not Found
        //Input Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592222");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592222", tvGenericBarcodeText.getText().toString());
        //Input Slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A75");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A75", tvFinalSlotText.getText().toString());
        //Assert response
        solo.waitForText("DeliveryPoint Not Found");
        assertEquals("DeliveryPoint Not Found", tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        //Linknotsuccessful
        //Input Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "809056031899366000592000");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("809056031899366000592000", tvGenericBarcodeText.getText().toString());
        //Input Slot
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "A75");
        solo.clickOnMenuItem("OK");
        solo.waitForDialogToClose(2000);
        Thread.sleep(500L);
        assertEquals("A75", tvFinalSlotText.getText().toString());
        //Assert response
        solo.waitForText("Link not successful - please try again!");
        assertEquals("Link not successful - please try again!", tvNextDayMsg.getText().toString());
        colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
        backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

    }

    //LANDSCAPE test
    public void testLinkSackOrientation() throws InterruptedException {

        {
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
            solo.setActivityOrientation(Solo.LANDSCAPE);
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
            assertEquals("A64", tvFinalSlotText.getText().toString());
            //Assertions
            solo.waitForText("Barcode has been successfully linked to slot!");
            assertEquals("Barcode has been successfully linked to slot!", tvNextDayMsg.getText());
            ColorDrawable colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            int backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.GREEN, backGroundColorValidMailItem); //validating background color

            //blank slot
            //Input valid generic barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
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

            //slot not found
            //Input Barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "809056031899366000592111");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("809056031899366000592111", tvGenericBarcodeText.getText().toString());
            //Input Slot
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "A53");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("A53", tvFinalSlotText.getText().toString());
            //Assert response
            solo.waitForText("Slot Not Found");
            assertEquals("Slot Not Found", tvNextDayMsg.getText().toString());
            colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


            //Invalid/Empty barcode
            //Input Barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertTrue(StringUtils.isBlank(tvGenericBarcodeText.getText().toString()));
            //Input Slot
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "A64");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("A64", tvFinalSlotText.getText().toString());
            //Assert response
            solo.waitForText("Generic Barcode either empty or not a valid length");
            assertEquals("Generic Barcode either empty or not a valid length", tvNextDayMsg.getText().toString());
            colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

            //Generic barcode already processed
            //Input Barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "809056031899366000592999");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("809056031899366000592999", tvGenericBarcodeText.getText().toString());
            //Input Slot
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "A64");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("A64", tvFinalSlotText.getText().toString());
            //Assert response
            solo.waitForText("Generic BarCode Already Processed");
            assertEquals("Generic BarCode Already Processed", tvNextDayMsg.getText().toString());
            colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color


            //DeliveryPoint Not Found
            //Input Barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "809056031899366000592222");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("809056031899366000592222", tvGenericBarcodeText.getText().toString());
            //Input Slot
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "A75");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("A75", tvFinalSlotText.getText().toString());
            //Assert response
            solo.waitForText("DeliveryPoint Not Found");
            assertEquals("DeliveryPoint Not Found", tvNextDayMsg.getText().toString());
            colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

            //Linknotsuccessful
            //Input Barcode
            solo.setActivityOrientation(Solo.LANDSCAPE);
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "809056031899366000592000");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("809056031899366000592000", tvGenericBarcodeText.getText().toString());
            //Input Slot
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "A75");
            solo.clickOnMenuItem("OK");
            solo.waitForDialogToClose(2000);
            Thread.sleep(500L);
            assertEquals("A75", tvFinalSlotText.getText().toString());
            //Assert response
            solo.waitForText("Link not successful - please try again!");
            assertEquals("Link not successful - please try again!", tvNextDayMsg.getText().toString());
            colorDrawableValidMailItem = (ColorDrawable) loadSackMessagePane.getBackground();
            backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
            assertEquals(Color.RED, backGroundColorValidMailItem); //validating background color

        }
    }
}
