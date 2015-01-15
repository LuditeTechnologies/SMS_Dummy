package com.luditetechnologies.sms_dummy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyQuickResponseService extends Service {
    public MyQuickResponseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
