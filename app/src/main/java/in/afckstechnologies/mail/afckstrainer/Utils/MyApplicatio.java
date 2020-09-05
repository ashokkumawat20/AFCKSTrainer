package in.afckstechnologies.mail.afckstrainer.Utils;

import android.app.Application;

/**
 * Created by Ashok Kumawat on 3/19/2018.
 */

public class MyApplicatio extends Application {

     static MyApplicatio mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

   /* public static MyApplicatio getContext() {
        return mContext;
    }*/

    public static synchronized MyApplicatio getInstance() {
        return mContext;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}