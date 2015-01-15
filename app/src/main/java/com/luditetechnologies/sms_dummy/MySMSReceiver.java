package com.luditetechnologies.sms_dummy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.parse.ParseObject;

import static com.luditetechnologies.sms_dummy.R.string.Parse_SMSError;
import static com.luditetechnologies.sms_dummy.R.string.ThanksForThinkingOfMe;
import static com.luditetechnologies.sms_dummy.R.string.UnableToSendSMS;

public class MySMSReceiver extends BroadcastReceiver {
    public MySMSReceiver() {
    }

    private static final String TAG = "MySMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // ---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;

        if (bundle != null) {

            Object[] pdus = (Object[]) bundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                //============================
                String msg_from = messages[i].getOriginatingAddress();

                //save the message to Parse
                android.util.Log.e(TAG, "FROM: " + msg_from + "MESSAGE: " + messages[i].getMessageBody());
                ParseObject smsObject = new ParseObject(context.getString(R.string.Parse_SMSObject));
                smsObject.put("from", msg_from);
                smsObject.put("message", messages[i].getMessageBody());
                smsObject.saveInBackground();

                try {
                    //TODO: implement a 'white list' instead of these hard-coded phone numbers.
                    //   I'm thinking that the user could select from the Contacts or something
                    String number = messages[i].getOriginatingAddress();
                    SmsManager smsManager = SmsManager.getDefault();

                    //Trap for my own phone number so an endless loop doesn't occur
                    if ( msg_from.contains("4232981490")) {
                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(1000);
                        return;
                    }

                    if (msg_from.contains("4235035997")) {
                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(1000);

                        android.util.Log.e(TAG, "New message from David has arrived!: ");
                        Toast.makeText(context, "New message from David has arrived!", Toast.LENGTH_LONG).show();

                        smsManager.sendTextMessage(number, null, context.getString(ThanksForThinkingOfMe), null, null);


                    } else {
                        smsManager.sendTextMessage(number, null, context.getString(UnableToSendSMS), null, null);
                    }
                } catch (Exception e) {
                    android.util.Log.e(TAG, "ERROR: ", e);
                    ParseObject smsError = new ParseObject(context.getString(Parse_SMSError));
                    smsError.put("from", "Exception sending to: " + msg_from);
                    smsError.put("message", e.toString());
                    smsError.saveInBackground();
                }
                //============================
            }
        }

        abortBroadcast();
    }
}