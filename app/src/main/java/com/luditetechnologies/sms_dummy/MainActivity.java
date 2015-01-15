package com.luditetechnologies.sms_dummy;

import android.app.Activity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;

public class MainActivity extends Activity {

    private EditText phoneNo;
    private EditText messageBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.ParseAppId), getString(R.string.ParseClientKey));


        phoneNo = (EditText) findViewById(R.id.mobileNumber);
        messageBody = (EditText) findViewById(R.id.smsBody);

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNo.getText().toString();
                String sms = messageBody.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed. Please try again later.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

}