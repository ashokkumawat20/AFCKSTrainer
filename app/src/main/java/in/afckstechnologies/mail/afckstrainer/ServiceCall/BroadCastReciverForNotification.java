package in.afckstechnologies.mail.afckstrainer.ServiceCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ashok Kumawat on 2/19/2018.
 */

public class BroadCastReciverForNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, NotificationService.class);
            context.startService(serviceIntent);
            Log.d("Hello","Hello ");
        }

    }


}