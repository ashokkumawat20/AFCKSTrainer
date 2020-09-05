package in.afckstechnologies.mail.afckstrainer.ServiceCall;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Ashok Kumawat on 2/19/2018.
 */

public class NotificationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // here you can add whatever you want this service to do
    }

}