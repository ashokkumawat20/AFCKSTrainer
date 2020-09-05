package in.afckstechnologies.mail.afckstrainer.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.ConnectivityReceiver;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;
import in.afckstechnologies.mail.afckstrainer.Utils.GPSTracker;
import in.afckstechnologies.mail.afckstrainer.Utils.MyApplicatio;
import in.afckstechnologies.mail.afckstrainer.Utils.VersionChecker;
import in.afckstechnologies.mail.afckstrainer.View.NotificationView;
import in.afckstechnologies.mail.afckstrainer.View.RegistrationView;

public class AFCKSTrainerDashBoardActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    LinearLayout takeEnterStudentDetails, takeStudentFeedback, takeSendDetalis, takeShowDemand, takeChangeUserPreferences, takeBookingStatus, takeTemplates, takeFixedAssests, takeStudentFromContacts, takeCallList, takeUserFeedbacks, takeSendcall, takeChangeUserReconciliation, takeViewAccessories, takeRequestType, penddingTicket, takeAttendance, showeEmployeeAttendance, takeGetVerifyCode, MyNotification, takeBankingDetails, takeCampaignDetalis,takeEnquiriesDetalis;
    String verifyMobileDeviceIdResponse = "";
    boolean status;
    String mobileDeviceId = "";
    TextView lead_text_view;
    ImageView settingimg;
    ImageView btnClosePopup;
    LinearLayout show1, show2, show3, show5, show6, show7, show8, show9, show10, show11, show12, show13, show14, show15, show16, show17, show18, show19, show20, show21, show22,show23;
    ProgressDialog mProgressDialog;
    String trainerResponse = "", callbackCountResponse = "", feedbackCountResponse = "", pendingTaskResponse = "";
    String phoneno = "", smspassResponse = "", temLocationResponse = "", myNotificationCountResponse = "", dueFeesSMSResponse = "";

    String user_id = "", getRoleusersResponse = "";
    String msg = "", centerResponse = "";
    private JSONObject jsonObj, jsonObject, jsonLeadObj, jsonObj1, jsonLeadObj1, jsonObjNotification;
    JSONArray jsonArray;

    TextView callbackcount, feedbackcount, pendingNotification;
    String callBackCount = "";
    String feedbackCount = "0";
    String notification_count = "";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    String center_id = "";
    String task_count = "", verifycode = "";
    TextView pendingTask;
    String dName = "";
    AlertDialog alertDialog1;
    CharSequence[] values = {" Pune Camp ", " Pimpri "};

    String first_name = "";
    String last_name = "";
    String email_id = "";
    String mobile_no = "";
    private String latestVersion = "";

    String st_name = "", batch_id = "", course_name = "", next_due_date = "", st_mobile = "", da = "";
    String sms = "";
    static String sms_user = "";
    static String sms_pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afckstrainer_dash_board);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sms_user = preferences.getString("sms_username", "");
        sms_pass = preferences.getString("sms_password", "");
        phoneno = preferences.getString("phone_no", "");
        settingimg = (ImageView) findViewById(R.id.settingimg);
        takeEnterStudentDetails = (LinearLayout) findViewById(R.id.takeEnterStudentDetails);
        takeTemplates = (LinearLayout) findViewById(R.id.takeTemplates);
        takeStudentFeedback = (LinearLayout) findViewById(R.id.takeStudentFeedback);
        takeRequestType = (LinearLayout) findViewById(R.id.takeRequestType);
        penddingTicket = (LinearLayout) findViewById(R.id.penddingTicket);
        takeSendDetalis = (LinearLayout) findViewById(R.id.takeSendDetalis);
        takeShowDemand = (LinearLayout) findViewById(R.id.takeShowDemand);
        takeChangeUserPreferences = (LinearLayout) findViewById(R.id.takeChangeUserPreferences);
        takeBookingStatus = (LinearLayout) findViewById(R.id.takeBookingStatus);
        takeTemplates = (LinearLayout) findViewById(R.id.takeTemplates);
        takeFixedAssests = (LinearLayout) findViewById(R.id.takeFixedAssests);
        takeCallList = (LinearLayout) findViewById(R.id.takeCallList);
        callbackcount = (TextView) findViewById(R.id.callbackcount);
        feedbackcount = (TextView) findViewById(R.id.feedbackcount);
        pendingTask = (TextView) findViewById(R.id.pendingTask);
        pendingNotification = (TextView) findViewById(R.id.pendingNotification);
        takeUserFeedbacks = (LinearLayout) findViewById(R.id.takeUserFeedbacks);
        takeSendcall = (LinearLayout) findViewById(R.id.takeSendcall);
        takeGetVerifyCode = (LinearLayout) findViewById(R.id.takeGetVerifyCode);
        takeChangeUserReconciliation = (LinearLayout) findViewById(R.id.takeChangeUserReconciliation);
        takeViewAccessories = (LinearLayout) findViewById(R.id.takeViewAccessories);
        takeAttendance = (LinearLayout) findViewById(R.id.takeAttendance);
        showeEmployeeAttendance = (LinearLayout) findViewById(R.id.showeEmployeeAttendance);
        MyNotification = (LinearLayout) findViewById(R.id.MyNotification);
        takeBankingDetails = (LinearLayout) findViewById(R.id.takeBankingDetails);
        takeCampaignDetalis = (LinearLayout) findViewById(R.id.takeCampaignDetalis);
        takeEnquiriesDetalis = (LinearLayout) findViewById(R.id.takeEnquiriesDetalis);
        show1 = (LinearLayout) findViewById(R.id.show1);
        show2 = (LinearLayout) findViewById(R.id.show2);
        show3 = (LinearLayout) findViewById(R.id.show3);

        show5 = (LinearLayout) findViewById(R.id.show5);
        show6 = (LinearLayout) findViewById(R.id.show6);
        show7 = (LinearLayout) findViewById(R.id.show7);
        show8 = (LinearLayout) findViewById(R.id.show8);
        show9 = (LinearLayout) findViewById(R.id.show9);
        show10 = (LinearLayout) findViewById(R.id.show10);
        show11 = (LinearLayout) findViewById(R.id.show11);
        show12 = (LinearLayout) findViewById(R.id.show12);
        show13 = (LinearLayout) findViewById(R.id.show13);
        show14 = (LinearLayout) findViewById(R.id.show14);
        show15 = (LinearLayout) findViewById(R.id.show15);
        show16 = (LinearLayout) findViewById(R.id.show16);
        show17 = (LinearLayout) findViewById(R.id.show17);
        show18 = (LinearLayout) findViewById(R.id.show18);
        show19 = (LinearLayout) findViewById(R.id.show19);
        show20 = (LinearLayout) findViewById(R.id.show20);
        show21 = (LinearLayout) findViewById(R.id.show21);
        show22 = (LinearLayout) findViewById(R.id.show22);
        show23= (LinearLayout) findViewById(R.id.show23);
        takeSendDetalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), StudentsListActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeChangeUserPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(getApplicationContext(), SearchStudentEditPreActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeShowDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), ShowDemandActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeBookingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), BookingStatusBatchWiseActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeUserFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), UserFeedbacksDisplayActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeCallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), CallingUserListActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeSendcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), SendCallActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeViewAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), ViewAccessoriesActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeFixedAssests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), FixedAssetsActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeChangeUserReconciliation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), ReconActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeEnterStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), StudentList.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeTemplates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    prefEditor.putString("temp_type_sms", "2");
                    prefEditor.commit();
                    Intent intent = new Intent(getApplicationContext(), TemplateDisplayActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeStudentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), StudentFeedbackActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeRequestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    prefEditor.putString("flag", "0");
                    prefEditor.commit();
                    Intent intent = new Intent(getApplicationContext(), RequestChangeDisplayActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        penddingTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    prefEditor.putString("flag", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(getApplicationContext(), RequestPendingDisplayActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });


        settingimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });

        takeGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    new getVerifyCode().execute();


                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeChangeUserPreferences.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    // new availableStudent().execute();
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    LastCall();
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        MyNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    NotificationView notificationView = new NotificationView();
                    notificationView.show(getSupportFragmentManager(), "studentFeesEntryView");


                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeBankingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, BankingDetailsActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeCampaignDetalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, CampaignActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeEnquiriesDetalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, EnquiriesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkConnection();


    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
            verifyMobileDeviceId();
            new initCallbackCount().execute();
            new smspassAvailable().execute();
            new dueFeesAvailable().execute();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            show1.setVisibility(View.GONE);
            show2.setVisibility(View.GONE);
            show3.setVisibility(View.GONE);
            show5.setVisibility(View.GONE);
            show7.setVisibility(View.GONE);
            show8.setVisibility(View.GONE);
            show9.setVisibility(View.GONE);
            show10.setVisibility(View.GONE);
            show11.setVisibility(View.GONE);
            show12.setVisibility(View.GONE);
            show13.setVisibility(View.GONE);
            show15.setVisibility(View.GONE);
            show17.setVisibility(View.GONE);
            show18.setVisibility(View.GONE);
            show19.setVisibility(View.GONE);
            show20.setVisibility(View.GONE);
            show22.setVisibility(View.GONE);
        }

        Snackbar snackbar = Snackbar.make(MyNotification, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
//count show on app icon

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

            verifyMobileDeviceId();
            getUserRoles();
            // forceUpdate();
        } else {

            //Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }
        // register connection status listener
        //  MyApplicatio.getInstance().setConnectivityListener(this);

    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

            verifyMobileDeviceId();
            getUserRoles();

            //   forceUpdate();
        } else {

            //Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

        // register connection status listener
        MyApplicatio.getInstance().setConnectivityListener(this);

    }


    public void forceUpdate() {
        //  int playStoreVersionCode = FirebaseRemoteConfig.getInstance().getString("android_latest_version_code");
        VersionChecker versionChecker = new VersionChecker();
        try {
            latestVersion = versionChecker.execute().get();
            /*if (latestVersion.length() > 0) {
                latestVersion = latestVersion.substring(50, 58);
                latestVersion = latestVersion.trim();
            }*/


            Log.d("versoncode", "" + latestVersion);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //  String currentVersion = packageInfo.versionName;
        String currentVersion = packageInfo.versionName;

        new ForceUpdateAsync(currentVersion, AFCKSTrainerDashBoardActivity.this).execute();

    }

    public class ForceUpdateAsync extends AsyncTask<String, String, JSONObject> {


        private String currentVersion;
        private Context context;

        public ForceUpdateAsync(String currentVersion, Context context) {
            this.currentVersion = currentVersion;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            /*try {
                latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }*/

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!latestVersion.equals("")) {
                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        // Toast.makeText(context,"update is available.",Toast.LENGTH_LONG).show();

                        if (!((Activity) context).isFinishing()) {
                            showForceUpdateDialog();
                        }


                    }
                } else {
                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                        // AppUpdater appUpdater = new AppUpdater((Activity) context);
                        //  appUpdater.start();
                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            super.onPostExecute(jsonObject);
        }

        public void showForceUpdateDialog() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));

            alertDialogBuilder.setTitle(context.getString(R.string.youAreNotUpdatedTitle));
            alertDialogBuilder.setMessage(context.getString(R.string.youAreNotUpdatedMessage) + " " + latestVersion + context.getString(R.string.youAreNotUpdatedMessage1));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                    dialog.cancel();
                }
            });
            alertDialogBuilder.show();
        }
    }


    private PopupWindow pwindo;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) AFCKSTrainerDashBoardActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup, (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 350, 400, true);
            pwindo.showAtLocation(layout, Gravity.TOP | Gravity.RIGHT, 15, 55);
            btnClosePopup = (ImageView) layout.findViewById(R.id.close);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

            CheckBox not = (CheckBox) layout.findViewById(R.id.chkSelected);
            CheckBox notm = (CheckBox) layout.findViewById(R.id.chkSelectedm);
            if (preferences.getString("send_sms", "").equals("sendsms")) {
                not.setChecked(true);
            } else if (preferences.getString("send_sms", "").equals("notsendsms")) {
                not.setChecked(false);
            }
            if (preferences.getString("send_mail", "").equals("sendmail")) {
                notm.setChecked(true);
            } else if (preferences.getString("send_mail", "").equals("notsendmail")) {
                notm.setChecked(false);
            }
            not.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {

                        Log.d("True", "Value");
                        prefEditor.putString("send_sms", "sendsms");
                        prefEditor.commit();

                    } else {
                        Log.d("False", "Value");
                        prefEditor.putString("send_sms", "notsendsms");
                        prefEditor.commit();
                    }
                }
            });

            notm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {

                        Log.d("True", "Value");
                        prefEditor.putString("send_mail", "sendmail");
                        prefEditor.commit();

                    } else {
                        Log.d("False", "Value");
                        prefEditor.putString("send_mail", "notsendmail");
                        prefEditor.commit();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };


    public void verifyMobileDeviceId() {


        jsonObj = new JSONObject() {
            {
                try {
                    put("user_id", preferences.getString("trainer_user_id", ""));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                verifyMobileDeviceIdResponse = serviceAccess.SendHttpPost(Config.URL_AVAILABLE_MOBILE_DEVICEID, jsonObj);
                Log.i("loginResponse", "verifyMobileDeviceIdResponse" + verifyMobileDeviceIdResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (verifyMobileDeviceIdResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(verifyMobileDeviceIdResponse);
                                        status = jObject.getBoolean("status");

                                        if (status) {
                                            mobileDeviceId = jObject.getString("mobile_deviceid");
                                            prefEditor.putString("email_id", jObject.getString("email_id"));
                                            prefEditor.putString("trainer_user_name", jObject.getString("trainer_name"));
                                            prefEditor.commit();
                                            if (!preferences.getString("deviceId", "").equals(mobileDeviceId)) {
                                                finish();
                                                prefEditor.putString("trainer_user_id", "");
                                                prefEditor.commit();
                                                Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, TrainerVerfiyActivity.class);
                                                startActivity(intent);
                                            } else {
                                                new dashboardAvailable().execute();
                                            }

                                        } else {

                                            finish();
                                            prefEditor.putString("trainer_user_id", "");
                                            prefEditor.commit();
                                            Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, TrainerVerfiyActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    public void getUserRoles() {


        jsonObj1 = new JSONObject() {
            {
                try {
                    put("user_id", preferences.getString("trainer_user_id", ""));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                getRoleusersResponse = serviceAccess.SendHttpPost(Config.URL_GETAVAILABLEUSERROLES, jsonObj1);
                Log.i("getRoleusersResponse", getRoleusersResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (getRoleusersResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(getRoleusersResponse);
                                        status = jObject.getBoolean("status");

                                        if (status) {
                                            forceUpdate();
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }
//

    private class dashboardAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            // mProgressDialog = new ProgressDialog(AFCKSTrainerDashBoardActivity.this);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //  mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
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
                //  Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                show19.setVisibility(View.VISIBLE);
                show20.setVisibility(View.VISIBLE);
                try {
                    JSONArray introJsonArray = jsonObject.getJSONArray("user_id");
                    for (int i = 0; i < introJsonArray.length(); i++) {
                        JSONObject introJsonObject = introJsonArray.getJSONObject(i);
                        if (introJsonObject.getString("send_details_status").equals("1")) {
                            show1.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("show_demand_status").equals("1")) {
                            show2.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("booking_batch_status").equals("1")) {
                            show3.setVisibility(View.VISIBLE);
                        }

                        if (introJsonObject.getString("change_user_pre_status").equals("1")) {
                            show5.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("fees_recon_status").equals("1")) {
                            // show6.setVisibility(View.VISIBLE);
                        }

                        if (introJsonObject.getString("user_feedbacks_status").equals("1")) {
                            show7.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("send_call_status").equals("1")) {
                            show8.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("callback_status").equals("1")) {
                            show9.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("fixed_assets_status").equals("1")) {
                            show10.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("view_accessories_status").equals("1")) {
                            show11.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("enter_studentd_details_status").equals("1")) {
                            show12.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("templates_status").equals("1")) {
                            show13.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("studentd_feedback").equals("1")) {
                            show14.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("request_ticket_status").equals("1")) {
                            show15.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("pending_ticket_status").equals("1")) {
                            //show16.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("attendance_status").equals("1")) {
                            show17.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("show_attendance_admin").equals("1")) {
                            show18.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("banking_details").equals("1")) {
                            show21.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("campaign_status").equals("1")) {
                            show22.setVisibility(View.VISIBLE);
                        }
                        if (introJsonObject.getString("enquiries_status").equals("1")) {
                            show23.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Close the progressdialog
                //mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                // mProgressDialog.dismiss();

            }
        }
    }


    private class smspassAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
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
            Log.i("resp", "smspassResponse" + smspassResponse);


            if (smspassResponse.compareTo("") != 0) {
                if (isJSONValid(smspassResponse)) {


                    try {

                        JSONArray introJsonArray = new JSONArray(smspassResponse);

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
            //  mProgressDialog.dismiss();

        }
    }


    private class getVerifyCode extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AFCKSTrainerDashBoardActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObj = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            pendingTaskResponse = serviceAccess.SendHttpPost(Config.URL_GETVERIFYCODEFORWEB, jsonObj);
            Log.i("resp", "pendingTaskResponse" + pendingTaskResponse);


            if (pendingTaskResponse.compareTo("") != 0) {
                if (isJSONValid(pendingTaskResponse)) {


                    try {

                        jsonObject = new JSONObject(pendingTaskResponse);
                        msg = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        verifycode = jsonObject.getString("id");

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


                AlertDialog.Builder builder = new AlertDialog.Builder(AFCKSTrainerDashBoardActivity.this);
                builder.setMessage("Verify Code is " + verifycode)
                        .setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });


                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually

                alert.show();
                mProgressDialog.dismiss();

            } else {
                mProgressDialog.dismiss();
            }
            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }
//

    public String LastCall() {

        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return TODO;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


        sb.append("Call Details :");

        int i = 0;
        while (managedCursor.moveToNext()) {
            if (i < 1) {
                String uName = managedCursor.getString(name);
                // String emailAddress = managedCursor.getString(managedCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;


                int dircode = Integer.parseInt(callType);
                switch (dircode) {

                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }

                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration
                        + " \nUser Details " + getContactDetails(phNumber));
                sb.append("\n----------------------------------");
                i++;

            }

        }
        managedCursor.close();
        return sb.toString();
    }

    public String getContactDetails(String phoneNumber1) {
        String searchNumber = phoneNumber1;
        String searchNumber1 = phoneNumber1.replace(" ", "");
        StringBuffer sb = new StringBuffer();
        // Cursor c =  getContentResolver().query(contactData, null, null, null, null);
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(searchNumber));
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        if (c.moveToNext()) {

            String phoneNumber = "", emailAddress = "";
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false";

            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            // Find Email Addresses
            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();


            if (!name.equals("")) {


                email_id = emailAddress;
                if (!name.equals("")) {
                    String columns[] = name.split(" ");

                    first_name = columns[0];
                    String name1 = columns[1];
                    String columns2[] = name1.split(",");
                    last_name = columns2[0];
                }

                if (searchNumber1.length() == 10) {

                    mobile_no = searchNumber1;

                }
                if (searchNumber1.length() == 11) {
                    //Toast.makeText(context, "" + current.getMobileNumber().substring(3, 13), Toast.LENGTH_SHORT).show();
                    mobile_no = searchNumber1.substring(1, 11);

                }
                if (searchNumber1.length() == 13) {
                    //Toast.makeText(context, "" + current.getMobileNumber().substring(3, 13), Toast.LENGTH_SHORT).show();
                    mobile_no = searchNumber1.substring(3, 13);

                }
                Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
                prefEditor.putString("emil_id", emailAddress);
                prefEditor.putString("student_name", first_name);
                prefEditor.putString("student_last_name", last_name);
                prefEditor.putString("st_first_name", first_name);
                prefEditor.putString("st_last_name", last_name);
                prefEditor.putString("st_mobile_no", mobile_no);
                prefEditor.commit();
                new availableStudent().execute();

            } else {
                Toast.makeText(getApplicationContext(), "Please save last calling number then try again!", Toast.LENGTH_SHORT).show();
            }


// add elements to al, including duplicates


        }
        c.close();
        return sb.toString();
    }

    private class availableStudent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AFCKSTrainerDashBoardActivity.this);
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

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("mobile_no", mobile_no);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_AVAILABLESTUDENT, jsonLeadObj);
            Log.i("resp", "centerResponse" + centerResponse);

            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {


                    try {


                        JSONObject jsonObject = new JSONObject(centerResponse);
                        status = jsonObject.getBoolean("status");
                        if (status) {
                            user_id = jsonObject.getString("user_id");
                            prefEditor.putString("user_id", jsonObject.getString("user_id"));
                            prefEditor.putString("student_mob_sms", mobile_no);
                            prefEditor.commit();
                        }


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(AFCKSTrainerDashBoardActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(AFCKSTrainerDashBoardActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (centerResponse.compareTo("") != 0) {
                if (status) {

                    preferences.edit().remove("st_mobile_no").commit();
                    preferences.edit().remove("emil_id").commit();
                    preferences.edit().remove("student_last_name").commit();
                    // Toast.makeText(context, "User_id is set "+user_id, Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    prefEditor.putString("user_id", user_id);
                    prefEditor.commit();
                    finish();
                    prefEditor.putString("flag_edit_pre", "2");
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(AFCKSTrainerDashBoardActivity.this, DisplayStudentEditPreActivity.class);
                    startActivity(intent);

                    // mListener.messageReceived(user_id);
                } else {
                    RegistrationView studentFeesEntryView = new RegistrationView();
                    studentFeesEntryView.show(getSupportFragmentManager(), "studentFeesEntryView");

                }

                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    //
    private class initCallbackCount extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AFCKSTrainerDashBoardActivity.this);
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


            jsonLeadObj = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            jsonObj = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            jsonLeadObj1 = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            jsonObjNotification = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            callbackCountResponse = serviceAccess.SendHttpPost(Config.URL_GETCOUNTCALLBACK, jsonLeadObj);
            Log.i("resp", "leadListResponse" + callbackCountResponse);

            Log.i("json", "json" + jsonObj);
            pendingTaskResponse = serviceAccess.SendHttpPost(Config.URL_CHECKEMPLOYEEPENDINGTASK, jsonObj);
            Log.i("resp", "pendingTaskResponse" + pendingTaskResponse);

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj1);
            feedbackCountResponse = serviceAccess.SendHttpPost(Config.URL_GETCOUNTFEEDBACK, jsonLeadObj1);
            Log.i("resp", "leadListResponse" + feedbackCountResponse);

            Log.i("json", "json" + jsonLeadObj1);
            myNotificationCountResponse = serviceAccess.SendHttpPost(Config.URL_CHECKMYPENDINGNOTIFICATIONS, jsonObjNotification);
            Log.i("resp", "myNotificationCountResponse" + myNotificationCountResponse);

            if (callbackCountResponse.compareTo("") != 0) {
                if (isJSONValid(callbackCountResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                JSONObject jsonObject = new JSONObject(callbackCountResponse);
                                callBackCount = jsonObject.getString("totalcallback");


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                }
                if (isJSONValid(pendingTaskResponse)) {


                    try {

                        jsonObject = new JSONObject(pendingTaskResponse);
                        msg = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        task_count = jsonObject.getString("id");

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
                if (isJSONValid(feedbackCountResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                JSONObject jsonObject = new JSONObject(feedbackCountResponse);
                                feedbackCount = jsonObject.getString("totalfeedback");

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                }
                if (isJSONValid(myNotificationCountResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                JSONObject jsonObject = new JSONObject(myNotificationCountResponse);
                                notification_count = jsonObject.getString("notification_count");

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (callbackCountResponse.compareTo("") != 0) {
                callbackcount.setText(callBackCount);

                mProgressDialog.dismiss();

            }
            if (status) {
                pendingTask.setText(task_count);
                mProgressDialog.dismiss();
            }
            if (feedbackCountResponse.compareTo("") != 0) {
                feedbackcount.setText(feedbackCount);
                mProgressDialog.dismiss();


            }
            if (myNotificationCountResponse.compareTo("") != 0) {
                pendingNotification.setText(notification_count);
                setBadge(getApplicationContext(), Integer.parseInt(notification_count));
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                pendingTask.setText("0");
                setBadge(getApplicationContext(), Integer.parseInt("0"));
                mProgressDialog.dismiss();
            }

        }
    }

    //
    private class initFeedbackCount extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
         /*   mProgressDialog = new ProgressDialog(AFCKSTrainerDashBoardActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj1 = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj1);
            feedbackCountResponse = serviceAccess.SendHttpPost(Config.URL_GETCOUNTFEEDBACK, jsonLeadObj1);
            Log.i("resp", "leadListResponse" + feedbackCountResponse);

            if (feedbackCountResponse.compareTo("") != 0) {
                if (isJSONValid(feedbackCountResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                JSONObject jsonObject = new JSONObject(feedbackCountResponse);
                                feedbackCount = jsonObject.getString("totalfeedback");


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (feedbackCountResponse.compareTo("") != 0) {
                feedbackcount.setText(feedbackCount);
                //  mProgressDialog.dismiss();


            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();
            }
        }
    }

    private class dueFeesAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
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
            Log.i("json", "json" + jsonLeadObj1);
            dueFeesSMSResponse = serviceAccess.SendHttpPost(Config.URL_GETSENDINGPENDINGSMSDETAILS, jsonObj);
            Log.i("resp", "dueFeesSMSResponse" + dueFeesSMSResponse);


            if (dueFeesSMSResponse.compareTo("") != 0) {
                if (isJSONValid(dueFeesSMSResponse)) {
                    JSONArray leadJsonObj = null;
                    try {
                        leadJsonObj = new JSONArray(dueFeesSMSResponse);
                        for (int i = 0; i < leadJsonObj.length(); i++) {
                            JSONObject json_data = leadJsonObj.getJSONObject(i);
                            st_name = json_data.getString("first_name");
                            batch_id = json_data.getString("BatchID");
                            course_name = json_data.getString("course_name");
                            next_due_date = json_data.getString("next_due_date");
                            st_mobile = json_data.getString("mobile_no");
                            if (!json_data.getString("due_amount").equals("null")) {
                                da = json_data.getString("due_amount");
                                sms = "Hi " + st_name + System.getProperty("line.separator") + System.getProperty("line.separator")
                                        + "Your fees is due for Batch " + batch_id + ", " + course_name + System.getProperty("line.separator")
                                        + "Would appreciate if you can pay your due fees before " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", next_due_date) + "." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                        + "Regards" + System.getProperty("line.separator")
                                        + "Raza" + System.getProperty("line.separator")
                                        + "9762118718";
                            } else {

                            }
                        }


                    } catch (JSONException e) {
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
            if (!sms.equals("")) {
                //  Toast.makeText(getApplicationContext(), sms, Toast.LENGTH_SHORT).show();
                String result = sendSms1(st_mobile, sms);
                sms = "";
                new dueFeesAvailable().execute();
            }

        }
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

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

    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
