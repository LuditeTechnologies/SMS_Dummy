package com.luditetechnologies.sms_dummy;

import android.app.Activity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.support.v7.app.ActionBarActivity;
//import android.content.Intent;

public class MainActivity extends Activity {

    private EditText phoneNo;
    private EditText messageBody;

//    @Override
//    protected  void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        messageBody = (EditText) findViewById(R.id.smsBody);
//
//        Uri uriSMSURI = Uri.parse("content://sms/inbox");
//        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
//
//        String sms = "";
//        String address = "";
//
////        while (cur.moveToNext()) {
////            address = cur.getString(cur.getColumnIndex("address"));
////            String body = cur.getString(cur.getColumnIndexOrThrow("body"));
////            sms += "From: " + address + "\n" + body + "\n\n\n";
////        }
//
//        for( int i = 0; i < cur.getColumnCount(); i++) {
//            Log.v("column", cur.getColumnName(i).toString());
//        }
//
//    }

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

        //Button shareIntent = (Button) findViewById(R.id.sendViaIntent);
//        shareIntent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                    sendIntent.putExtra("sms_body", messageBody.getText().toString());
//                    sendIntent.setType("vnd.android-dir/mms-sms");
//                    startActivity(sendIntent);
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "SMS failed. Please try again later.", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}