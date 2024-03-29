package com.dpwn.smartscanus.IntegrationTests.DspScanning;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpwn.smartscanus.IntegrationTests.BaseIntegrationTests;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.dspscanning.ui.DspScanningFragment;
import com.dpwn.smartscanus.fullServiceImb.ui.ScanFullSvcIMBFragment;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * Created by maanik on 8/18/2015.
 */

public class DspScanningTest extends BaseIntegrationTests {


    public void testScanningDsp() {
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


        ImageView imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        TextView  tvNextDayMsg =  (TextView)  solo.getView(R.id.tvNextDayMessage);

        //Valid Mail Item
        solo.clickOnView(solo.getView(R.id.action_input));
        String scanInput = "420123459361289936300000240106";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        String ExpectedMsg = "MailItem Scanned Successfully.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidMailItem = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidMailItem = colorDrawableValidMailItem.getColor();
        assertEquals(Color.GREEN, backGroundColorValidMailItem); //validating background color

        //Duplicate Mail Item
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "420300049274888836355800000017";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "MailItem already scanned.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableDuplicateMailItem = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorDuplicateMailItem = colorDrawableDuplicateMailItem.getColor();
        assertEquals(Color.RED, backGroundColorDuplicateMailItem); //validating background color

        // Invalid Mail Item
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "InvalidMailItem ";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "MailItem not found.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableInvalidMailItem = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInvalidMailItem = colorDrawableInvalidMailItem.getColor();
        assertEquals(Color.RED, backGroundColorInvalidMailItem); //validating background color

        // Valid Receptacle
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "434025211414141000332569";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "5 MailItems associated to receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidReceptacle = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidReceptacle = colorDrawableValidReceptacle.getColor();
        assertEquals(Color.GREEN, backGroundColorValidReceptacle); //validating background color

        // New Receptacle
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "434025211414141000332541";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "No MailItems found for receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableNewReceptacle = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorNewReceptacle = colorDrawableNewReceptacle.getColor();
        assertEquals(Color.RED, backGroundColorNewReceptacle); //validating background color

        // Invalid Receptacle
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "InvalidReceptacle";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Receptacle Not Found.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableInvalidReceptacle = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInvalidReceptacle = colorDrawableInvalidReceptacle.getColor();
        assertEquals(Color.RED, backGroundColorInvalidReceptacle); //validating background color

        // Valid Container
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "99M889363070000000255";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "25 MailItems associated to container.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidContainer = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidContainer = colorDrawableValidContainer.getColor();
        assertEquals(Color.GREEN, backGroundColorValidContainer); //validating background color

        // Invalid Container Type
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "99M889363070000000111";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "Invalid TransportUnit type.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableInvalidContainerType = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInvalidContainerType = colorDrawableInvalidContainerType.getColor();
        assertEquals(Color.RED, backGroundColorInvalidContainerType); //validating background color

        // New Container
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "99M889363070000000333";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "No MailItems found for TransportUnit.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableNewContainer = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorNewContainer = colorDrawableNewContainer.getColor();
        assertEquals(Color.RED, backGroundColorNewContainer); //validating background color

        // Invalid Container
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "InvalidContainer";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "TransportUnit not found.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableInvalidContainer = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInvalidContainer = colorDrawableInvalidContainer.getColor();
        assertEquals(Color.RED, backGroundColorInvalidContainer); //validating background color

    }

    public void testScanningDspOrientation() {
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


        //Landscape View
        solo.setActivityOrientation(Solo.LANDSCAPE);
        ImageView imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        TextView  tvNextDayMsg =  (TextView)  solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        String scanInput = "99M889363070000000111";
        solo.enterText(0,scanInput);
        solo.clickOnMenuItem("OK");
        String ExpectedMsg = "Invalid TransportUnit type.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableInvalidContainerType = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInvalidContainerType = colorDrawableInvalidContainerType.getColor();
        assertEquals(Color.RED, backGroundColorInvalidContainerType); //validating background color

        //Portrait View
        solo.setActivityOrientation(Solo.PORTRAIT);
        imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        tvNextDayMsg =  (TextView)  solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "434025211414141000332569";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "5 MailItems associated to receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg,tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidReceptacle = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidReceptacle = colorDrawableValidReceptacle.getColor();
        assertEquals(Color.GREEN, backGroundColorValidReceptacle); //validating background color

    }

    public void testGenericBarcode() {
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

        // Valid Generic barcode
        solo.setActivityOrientation(Solo.LANDSCAPE);
        ImageView imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        TextView tvNextDayMsg = (TextView) solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        String scanInput = "123456789";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        String ExpectedMsg = "15 MailItems associated to receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidGenericBarcode = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidGenericBarcode = colorDrawableValidGenericBarcode.getColor();
        assertEquals(Color.GREEN, backGroundColorValidGenericBarcode); //validating background color

        //Invalid Generic barcode
        solo.setActivityOrientation(Solo.LANDSCAPE);
        imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        tvNextDayMsg = (TextView) solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "123456";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "No MailItems found for receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());
        ColorDrawable colorDrawableInValidGenericBarcode = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInValidGenericBarcode = colorDrawableInValidGenericBarcode.getColor();
        assertEquals(Color.RED, backGroundColorInValidGenericBarcode); //validating background color


        // Valid Generic barcode
        solo.setActivityOrientation(Solo.PORTRAIT);
        imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        tvNextDayMsg = (TextView) solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "123456789";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "15 MailItems associated to receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());
        ColorDrawable colorDrawableValidGenericBarcodePortrait = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorValidGenericBarcodePortrait = colorDrawableValidGenericBarcodePortrait.getColor();
        assertEquals(Color.GREEN, backGroundColorValidGenericBarcodePortrait); //validating background color

        //Invalid Generic barcode
        solo.setActivityOrientation(Solo.PORTRAIT);
        imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        tvNextDayMsg = (TextView) solo.getView(R.id.tvNextDayMessage);
        solo.clickOnView(solo.getView(R.id.action_input));
        scanInput = "123456";
        solo.enterText(0, scanInput);
        solo.clickOnMenuItem("OK");
        ExpectedMsg = "No MailItems found for receptacle.";
        solo.waitForText(ExpectedMsg);
        assertEquals(ExpectedMsg, tvNextDayMsg.getText());
        ColorDrawable colorDrawableInValidGenericBarcodePortrait = (ColorDrawable) imgNextDayScan.getBackground();
        int backGroundColorInValidGenericBarcodePortrait = colorDrawableInValidGenericBarcodePortrait.getColor();
        assertEquals(Color.RED, backGroundColorInValidGenericBarcodePortrait); //validating background color
    }
}
