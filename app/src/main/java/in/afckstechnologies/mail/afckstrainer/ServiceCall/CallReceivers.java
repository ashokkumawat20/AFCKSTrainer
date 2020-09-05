package in.afckstechnologies.mail.afckstrainer.ServiceCall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import in.afckstechnologies.mail.afckstrainer.Activity.NotifyUserDetails;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

/**
 * Created by Ashok Kumawat on 12/20/2017.
 */

public class CallReceivers extends PhonecallReceiver {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String mobile_no = "";
    JSONObject jsonCenterObj, jsonobject, jsonLeadObj;
    JSONArray jsonarray;
    String centerResponse = "", userResponse = "";
    boolean status;
    String user_id = "";

    @Override
    protected void onIncomingCallStarted(final Context ctx, String number, Date start) {
        // Toast.makeText(ctx,"Incoming Call"+ number, Toast.LENGTH_LONG).show();

       /* context = ctx;
        mobile_no = number;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        if (preferences.getString("trainer_user_id", "").equals("RS") || preferences.getString("trainer_user_id", "").equals("AT") || preferences.getString("trainer_user_id", "").equals("AK")) {


          *//*  final Intent intent = new Intent(context, NotifyUserDetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("phone_no", number);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(intent);
                }
            }, 2000);
*//*
            if (AppStatus.getInstance(context).isOnline()) {
                new availableStudent().execute();
            } else {


            }
        }*/
//        MyCus/*tomDialog dialog   =   new MyCustomDialog(context);
//        dialog.*/show();
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        // Toast.makeText(ctx,"Bye Bye"+ number,Toast.LENGTH_LONG).show();
        context = ctx;
        mobile_no = number;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        if (preferences.getString("trainer_user_id", "").equals("RS") || preferences.getString("trainer_user_id", "").equals("AT") || preferences.getString("trainer_user_id", "").equals("AK")) {


          /*  final Intent intent = new Intent(context, NotifyUserDetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("phone_no", number);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(intent);
                }
            }, 2000);
*/
            if (AppStatus.getInstance(context).isOnline()) {
              //  new availableStudent().execute();
            } else {


            }
        }
    }

    private class availableStudent extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            // mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            //    mProgressDialog.setTitle("Please Wait...");
            //   // Set progressdialog message
            //  mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("mobile_no", mobile_no.substring(3, 13));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            userResponse = serviceAccess.SendHttpPost(Config.URL_AVAILABLESTUDENT, jsonLeadObj);
            Log.i("resp", "userResponse" + userResponse);

            if (userResponse.compareTo("") != 0) {
                if (isJSONValid(userResponse)) {


                    try {


                        JSONObject jsonObject = new JSONObject(userResponse);
                        status = jsonObject.getBoolean("status");
                        if (status) {
                            user_id = jsonObject.getString("user_id");
                            prefEditor.putString("user_id", jsonObject.getString("user_id"));
                            prefEditor.putString("student_mob_sms", mobile_no.substring(3, 13));
                            prefEditor.commit();
                        }


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (userResponse.compareTo("") != 0) {
                if (status) {
                    //  Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
                    // mListener.messageReceived(user_id);
                    final Intent intent = new Intent(context, NotifyUserDetails.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("phone_no", mobile_no);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                        }
                    }, 2000);
                } else {
                    //  Toast.makeText(context, "Not Done", Toast.LENGTH_LONG).show();
                }

                // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                // mProgressDialog.dismiss();
            }
        }
    }

    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
