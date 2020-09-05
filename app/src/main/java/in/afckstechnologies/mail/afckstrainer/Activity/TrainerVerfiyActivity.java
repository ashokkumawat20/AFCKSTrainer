package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import in.afckstechnologies.mail.afckstrainer.FirebaseNotification.SharedPrefManager;
import in.afckstechnologies.mail.afckstrainer.R;

import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsReceiver;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


public class TrainerVerfiyActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    EditText verify_mobile_no, verify_code_no;
    Button sendCodeButton, submitCodeButton;
    LinearLayout verifyCodeLayout;
    String id;
    TimerTask task;
    TextView timer;
    int time = 60;
    Timer t;
    ProgressDialog mProgressDialog;
    private JSONObject jsonObj, jsonObject;
    JSONArray jsonArray;
    String trainerResponse = "", smspassResponse = "";
    String phoneno = "";
    Boolean status;
    String user_id = "";
    String msg = "";
    String deviceId = "";
    static String sms_user = "";
    static String sms_pass = "";

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_verfiy);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        verify_mobile_no = (EditText) findViewById(R.id.verify_mobile_no);
        sendCodeButton = (Button) findViewById(R.id.sendCodeButton);
        verify_code_no = (EditText) findViewById(R.id.verify_code_no);
        submitCodeButton = (Button) findViewById(R.id.submitCodeButton);
        verifyCodeLayout = (LinearLayout) findViewById(R.id.verifyCodeLayout);
        timer = (TextView) findViewById(R.id.timer);
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            new smspassAvailable().execute();


        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("oneplus".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"));
            } else if ("asus".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.autostart.AutoStartActivity"));
            }
            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
        }


        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno = verify_mobile_no.getText().toString().trim();
                deviceId = getDeviceId();
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    if (validate(phoneno)) {
                        sms_user = preferences.getString("sms_username", "");
                        sms_pass = preferences.getString("sms_password", "");
                        new userAvailable().execute();

                    }
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }


            }
        });

        submitCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    String enter_code = verify_code_no.getText().toString().trim();
                    if (id.equals(enter_code)) {
                        sendCodeButton.setVisibility(View.GONE);
                        // Toast.makeText(getApplicationContext(), "User Verify ", Toast.LENGTH_LONG).show();
                        new submitData().execute();


                    } else {
                        sendCodeButton.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Code does not match!", Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                String companyFlag = messageText.substring(17);
                Log.d("companyFlag--->", companyFlag);
                //  Toast.makeText(TrainerVerfiyActivity.this,"Message: "+companyFlag,Toast.LENGTH_LONG).show();
                verify_code_no.setText(companyFlag);
                String enter_code = verify_code_no.getText().toString().trim();
                if (id.equals(enter_code)) {
                    new submitData().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Code does not match!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public String getDeviceId() {

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("close_activity", false)) {
            this.finish();

        }
    }

    public void startTimer() {
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView tv1 = (TextView) findViewById(R.id.timer);
                        tv1.setText(time + "");
                        if (time > 0)
                            time -= 1;
                        else {
                            timer.setText("Welcome");
                            sendCodeButton.setVisibility(View.VISIBLE);
                            timer.setVisibility(View.GONE);
                            verifyCodeLayout.setVisibility(View.GONE);
                            t.cancel();
                            t.purge();
                        }
                    }
                });
            }
        };
        t.scheduleAtFixedRate(task, 0, 1000);
    }
    //


    public static String sendSms1(String tempMobileNumber, String message) {
        String sResult = null;
        try {
// Construct data
            //String phonenumbers = "9657816221";
            String data = "user=" + URLEncoder.encode(sms_user, "UTF-8");
            data += "&password=" + URLEncoder.encode(sms_pass, "UTF-8");
            data += "&message=" + URLEncoder.encode(message, "UTF-8");
            data += "&sender=" + URLEncoder.encode("AFCKST", "UTF-8");
            data += "&mobile=" + URLEncoder.encode(tempMobileNumber, "UTF-8");
            data += "&type=" + URLEncoder.encode("3", "UTF-8");
// Send data
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?" + data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
// Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1 = "";
            while ((line = rd.readLine()) != null) {
// Process line...
                sResult1 = sResult1 + line + " ";
            }
            wr.close();
            rd.close();
            return sResult1;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }


    public boolean validate(String phoneno) {
        boolean isValidate = false;
        if (phoneno.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (phoneno.trim().compareTo("") == 0 || phoneno.length() != 10) {
            Toast.makeText(getApplicationContext(), "Please enter a 10 digit valid Mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else {
            isValidate = true;
        }
        return isValidate;
    }


//

    private class userAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObj = new JSONObject() {
                {
                    try {

                        put("mobile_no", phoneno);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            trainerResponse = serviceAccess.SendHttpPost(Config.URL_USER_AVAILABLE, jsonObj);
            Log.i("resp", "centerListResponse" + trainerResponse);


            if (trainerResponse.compareTo("") != 0) {
                if (isJSONValid(trainerResponse)) {
                    try {
                        jsonObject = new JSONObject(trainerResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                try {
                    JSONArray introJsonArray = jsonObject.getJSONArray("user_id");
                    for (int i = 0; i < introJsonArray.length(); i++) {
                        JSONObject introJsonObject = introJsonArray.getJSONObject(i);
                        prefEditor.putString("trainer_user_id", introJsonObject.getString("id"));
                        prefEditor.putString("phone_no", phoneno);
                        prefEditor.putString("deviceId", deviceId);
                        prefEditor.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                id = String.format("%06d", random.nextInt(1000000));
                String message = "AFCKS Admin code " + id;
                // Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                Log.d("Random no---->", id);
                verifyCodeLayout.setVisibility(View.VISIBLE);
                String result = sendSms1(phoneno, message);//  semd verification code
                timer.setVisibility(View.VISIBLE);
                time = 300;
                startTimer();
                sendCodeButton.setVisibility(View.GONE);

                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();

            }
        }
    }

//

    private class smspassAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObj = new JSONObject() {
                {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            smspassResponse = serviceAccess.SendHttpPost(Config.URL_GETUSERNAMEPASSSMS, jsonObj);
            Log.i("resp", "centerListResponse" + smspassResponse);


            if (smspassResponse.compareTo("") != 0) {
                if (isJSONValid(smspassResponse)) {


                    try {

                        jsonObject = new JSONObject(smspassResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {


            try {
                JSONArray introJsonArray = new JSONArray(smspassResponse);
                for (int i = 0; i < introJsonArray.length(); i++) {
                    JSONObject introJsonObject = introJsonArray.getJSONObject(i);
                    if (introJsonObject.getString("type").equals("sms")) {
                        prefEditor.putString("sms_username", introJsonObject.getString("sms_id"));
                        prefEditor.putString("sms_password", introJsonObject.getString("password"));
                        prefEditor.commit();
                    }

                    if (introJsonObject.getString("type").equals("AFCKS_email")) {
                        prefEditor.putString("mail_username", introJsonObject.getString("sms_id"));
                        prefEditor.putString("mail_password", introJsonObject.getString("password"));
                        prefEditor.commit();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            final String date = format.format(cal.getTime());
            final String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            jsonObj = new JSONObject() {
                {
                    try {
                        put("id", preferences.getString("trainer_user_id", ""));
                        put("fcm_id", token);
                        put("mobile_deviceid", deviceId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonObj);
            trainerResponse = serviceAccess.SendHttpPost(Config.URL_UPDATE_TRAINER_DETAILS, jsonObj);
            Log.i("resp", "centerListResponse" + trainerResponse);


            if (trainerResponse.compareTo("") != 0) {
                if (isJSONValid(trainerResponse)) {


                    try {

                        jsonObject = new JSONObject(trainerResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


                    Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                mProgressDialog.dismiss();
                try {
                    prefEditor.putString("trainer_user_name", jsonObject.getString("trainer_name"));
                    prefEditor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(TrainerVerfiyActivity.this, AFCKSTrainerDashBoardActivity.class);
                startActivity(intent);
                // Close the progressdialog

            } else {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
        }
    }

    //
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
