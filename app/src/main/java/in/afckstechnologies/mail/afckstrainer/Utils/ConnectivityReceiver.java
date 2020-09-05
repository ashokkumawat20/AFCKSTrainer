package in.afckstechnologies.mail.afckstrainer.Utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;
    //context and database helper object
    private Context context;
    private DatabaseHelper db;
    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {

        this.context = context;

        db = new DatabaseHelper(context);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }


        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                pullDataFromServer();

            }
        }
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplicatio.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    private void pullDataFromServer() {

        JSONObject json = new JSONObject();
        try {
            json.put("device_id", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GETALLVWDEPARTMENTS, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.getBoolean("status")) {
                                //updating the status in sqlite
                                //  db.updateNameStatus(id, MainActivity.NAME_SYNCED_WITH_SERVER);
                                if (!response.isNull("dataList")) {
                                    JSONArray jsonArray = response.getJSONArray("dataList");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        db.addNameSync(object.getString("id"), object.getString("dept_name"), object.getString("group_user_name"), 0);

                                    }
                                }
                                //sending the broadcast to refresh the list
                               // context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}