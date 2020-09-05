package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeCreateListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.TemplateListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.DepartmentDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeUsersNameDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TemplatesContactDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.ConnectivityReceiver;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;
import in.afckstechnologies.mail.afckstrainer.Utils.DatabaseHelper;
import in.afckstechnologies.mail.afckstrainer.Utils.MyApplicatio;
import in.afckstechnologies.mail.afckstrainer.Utils.VersionChecker;
import in.afckstechnologies.mail.afckstrainer.View.NotificationView;
import in.afckstechnologies.mail.afckstrainer.View.StudentDiscontinueEntryView;
import in.afckstechnologies.mail.afckstrainer.View.TrainerDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.UpdateRequestAssignNewUserView;

public class RequestChangeDisplayActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private FloatingActionButton fab;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonObjNotification, jsonObj, jsonObj1;
    JSONArray jsonArray;
    List<RequestChangeListDAO> data;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String displayRequestChangeResponse = "", unduResponse = "", verifyMobileDeviceIdResponse = "", mobileDeviceId = "", getRoleusersResponse = "";
    RequestChangeCreateListAdpter requestChangeListAdpter;
    private RecyclerView mrequestchangeList;
    String user_id = "0", userName = "";
    Spinner usersName;
    String usersResponse = "";
    ArrayList<RequestChangeUsersNameDAO> userslist;
    RequestChangeUsersNameDAO requestChangeUsersNameDAO;
    LinearLayout usersLayout, priRefreshBtn, statusRefreshBtn;

    RadioGroup radioGroup, radioGroupStatus;
    private RadioButton prilow, primedium, prihigh, done, working, notstart;
    int pos;
    int pri_status_id = 0;
    int status = 0;
    Button refreshBtn;
    ImageView serach_hide, clear;
    public EditText search;

    String request_id = "";
    RelativeLayout footer;
    Button undoTicket, addTicketBtn;
    TextView textValue;
    // Splash screen timer
    private static int SPLASH_TIME_OUT;
    String sharedata = "";
    boolean status1;
    private String latestVersion = "";
//

    AutoCompleteTextView SearchDepart;
    String newTextDepart = "", departResponse = "", myNotificationCountResponse = "", notification_count = "";
    JSONObject jsonSchedule;
    DepartmentDAO departmentDAO;
    public ArrayAdapter<DepartmentDAO> aAdapter;
    public List<DepartmentDAO> departArrayList;
    String depart_id = "0";
    ImageView clear_text, pendding;
    //Edit lead
    HashMap<String, String> edit_LeadLOCList;
    //Keys
    Set keys_Loc;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;

    TextView pendingNotification;

    //offline database
    //database helper object
    private DatabaseHelper db;
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "in.afckstechnologies.mail.afckstrainer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_change_display);
        registerReceiver(new ConnectivityReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        //initializing views and objects
        db = new DatabaseHelper(this);
        edit_LeadLOCList = new HashMap<String, String>();
        prefEditor.putString("RequestChange", "RequestChangeDisplay");
        prefEditor.commit();
        mrequestchangeList = (RecyclerView) findViewById(R.id.requestChangeList);
        usersLayout = (LinearLayout) findViewById(R.id.usersLayout);
        usersName = (Spinner) findViewById(R.id.spinnerUsers);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        addTicketBtn = (Button) findViewById(R.id.addTicketBtn);
        priRefreshBtn = (LinearLayout) findViewById(R.id.priRefreshBtn);
        statusRefreshBtn = (LinearLayout) findViewById(R.id.statusRefreshBtn);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        pendding = (ImageView) findViewById(R.id.pendding);
        clear = (ImageView) findViewById(R.id.clear);
        search = (EditText) findViewById(R.id.search);
        footer = (RelativeLayout) findViewById(R.id.footer);
        undoTicket = (Button) findViewById(R.id.undoTicket);
        textValue = (TextView) findViewById(R.id.textValue);
        SearchDepart = (AutoCompleteTextView) findViewById(R.id.SearchDepart);
        clear_text = (ImageView) findViewById(R.id.clear_text);
        departArrayList = new ArrayList<DepartmentDAO>();
        userslist = new ArrayList<>();
        requestChangeUsersNameDAO = new RequestChangeUsersNameDAO();
        pendingNotification = (TextView) findViewById(R.id.pendingNotification);
       /* SearchDepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextDepart = s.toString();
                getDepartSelect(newTextDepart);


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

        SearchDepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(SearchDepart.getText())) {
                        clear.setVisibility(View.VISIBLE);
                        getDepartSelect(SearchDepart.getText().toString());

                    } else {

                    }
                }
                return false;
            }
        });


       /* fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestChangeDisplayActivity.this, RequestChangeActivity.class);
                startActivity(intent);

            }
        });*/


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                sharedata = intent.getExtras().getString(Intent.EXTRA_TEXT);
                //  tv.setText(intent.getExtras().getString(Intent.EXTRA_TEXT));
                // Toast.makeText(getApplicationContext(), intent.getExtras().getString(Intent.EXTRA_TEXT), Toast.LENGTH_SHORT).show();
            } else if (type.startsWith("image/")) {
                // Toast.makeText(getApplicationContext(), "Bye", Toast.LENGTH_SHORT).show();
            }

        } else {
            // Handle other intents, such as being started from the home screen
        }
        addTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!depart_id.equals("0")) {
                    if (!user_id.equals("0")) {


                        String dataIWant = userName.split("-")[1];
                        Intent intent = new Intent(RequestChangeDisplayActivity.this, RequestChangeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("depart_id", depart_id);
                        intent.putExtra("user_name", dataIWant);
                        intent.putExtra("share_data", sharedata);
                        startActivity(intent);
                    } else {
                        if (userslist.size() > 2) {
                            Toast.makeText(getApplication(), "Assign Any User", Toast.LENGTH_SHORT).show();
                            Random rand = new Random();
                            int n = rand.nextInt(userslist.size());
                            requestChangeUsersNameDAO = userslist.get(n);
                            //  Toast.makeText(getActivity(), trainersDAO.getFirst_name(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RequestChangeDisplayActivity.this, RequestChangeActivity.class);
                            intent.putExtra("user_name", requestChangeUsersNameDAO.getUser_name().split("-")[1]);
                            intent.putExtra("user_id", requestChangeUsersNameDAO.getId());
                            intent.putExtra("depart_id", depart_id);
                            intent.putExtra("share_data", sharedata);
                            startActivity(intent);
                        } else {
                            requestChangeUsersNameDAO = userslist.get(1);
                            //  Toast.makeText(getActivity(), trainersDAO.getFirst_name(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RequestChangeDisplayActivity.this, RequestChangeActivity.class);
                            intent.putExtra("user_id", requestChangeUsersNameDAO.getId());
                            intent.putExtra("user_name", requestChangeUsersNameDAO.getUser_name().split("-")[1]);
                            intent.putExtra("depart_id", depart_id);
                            intent.putExtra("share_data", sharedata);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getApplication(), "Please select department", Toast.LENGTH_SHORT).show();

                }


            }
        });
        usersName = (Spinner) findViewById(R.id.spinnerUsers);//|| preferences.getString("trainer_user_id", "").equals("AK")

        /*if(preferences.getString("trainer_user_id", "").equals("RS") || preferences.getString("trainer_user_id", "").equals("AK") ) {
            usersLayout.setVisibility(View.VISIBLE);
            new initUsersSpinner().execute();
        }
        else
        {
            user_id=preferences.getString("trainer_user_id", "");
            new getRequestChangeList().execute();
        }*/
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        prilow = (RadioButton) findViewById(R.id.prilow);
        primedium = (RadioButton) findViewById(R.id.primedium);
        prihigh = (RadioButton) findViewById(R.id.prihigh);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroup.indexOfChild(findViewById(checkedId));
                switch (pos) {
                    case 1:

                        pri_status_id = 2;

                        prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        primedium.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        //  Toast.makeText(getApplicationContext(), "1" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;
                    case 2:

                        pri_status_id = 1;
                        primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prilow.setBackgroundColor(Color.parseColor("#4CAF50"));
                        // Toast.makeText(getApplicationContext(), "2" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;


                    default:
                        //The default selection is RadioButton 1
                        pri_status_id = 3;
                        primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prihigh.setBackgroundColor(Color.parseColor("#F44336"));
                        // Toast.makeText(getApplicationContext(), "3" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;
                }
            }
        });

        radioGroupStatus = (RadioGroup) findViewById(R.id.radioGroupStatus);
        notstart = (RadioButton) findViewById(R.id.notstart);
        working = (RadioButton) findViewById(R.id.working);
        done = (RadioButton) findViewById(R.id.done);
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroupStatus.indexOfChild(findViewById(checkedId));
                switch (pos) {
                    case 1:

                        status = 2;

                        notstart.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        done.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        working.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        //  Toast.makeText(getApplicationContext(), "1" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;
                    case 2:

                        status = 1;
                        working.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        done.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        notstart.setBackgroundColor(Color.parseColor("#F44336"));
                        //  Toast.makeText(getApplicationContext(), "2" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;


                    default:
                        //The default selection is RadioButton 1
                        status = 3;
                        working.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        notstart.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        done.setBackgroundColor(Color.parseColor("#4CAF50"));
                        //  Toast.makeText(getApplicationContext(), "3" + pri_status_id, Toast.LENGTH_SHORT).show();
                        new getRequestChangeList().execute();
                        break;
                }
            }
        });

        UpdateRequestAssignNewUserView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getRequestChangeList().execute();
            }
        });
        RequestChangeCreateListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getRequestChangeList().execute();
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 0;
                pri_status_id = 0;
                prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));

                working.setBackgroundColor(Color.parseColor("#809E9E9E"));
                notstart.setBackgroundColor(Color.parseColor("#809E9E9E"));
                done.setBackgroundColor(Color.parseColor("#809E9E9E"));
                SearchDepart.setText("");
                //  new getRequestChangeList().execute();
                new initUsersSpinner().execute();

            }
        });
        priRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pri_status_id = 0;
                prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
                new getRequestChangeList().execute();
            }
        });
        statusRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 0;
                working.setBackgroundColor(Color.parseColor("#809E9E9E"));
                notstart.setBackgroundColor(Color.parseColor("#809E9E9E"));
                done.setBackgroundColor(Color.parseColor("#809E9E9E"));
                new getRequestChangeList().execute();
            }
        });

        serach_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                clear.setVisibility(View.VISIBLE);
                serach_hide.setVisibility(View.GONE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                serach_hide.setVisibility(View.VISIBLE);
                clear.setVisibility(View.GONE);

            }
        });
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDepart.setText("");
                depart_id = "0";
                user_id = "0";
                userslist.clear();
                edit_LeadLOCList.clear();
                clear_text.setVisibility(View.GONE);
                new initUsersSpinner().execute();
                //new getRequestChangeList().execute();
            }
        });
        addTextListener();
        //  new getRequestChangeList().execute();
        pendding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    NotificationView notificationView = new NotificationView();
                    notificationView.show(getSupportFragmentManager(), "studentFeesEntryView");


                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });



        checkConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

            verifyMobileDeviceId();
            getUserRoles();
            new getRequestChangeList().execute();

            //   forceUpdate();
        } else {

            //Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

        MyApplicatio.getInstance().setConnectivityListener(this);

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


        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;

        }

        Snackbar snackbar = Snackbar.make(priRefreshBtn, message, Snackbar.LENGTH_LONG);
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

    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<RequestChangeListDAO> filteredList = new ArrayList<RequestChangeListDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String subject = data.get(i).getAssign_user_name().toLowerCase();
                            String msg_txt = data.get(i).getRequest_body().toLowerCase();
                            String msg_txt1 = data.get(i).getRequest_subject().toLowerCase();
                            String msg_txt2 = data.get(i).getCreate_user_name().toLowerCase();
                            String msg_txt3 = data.get(i).getDate_time().toLowerCase();
                            String msg_txt4 = data.get(i).getStatus().toLowerCase();
                            if (subject.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt1.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt2.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt3.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt4.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                        }
                    }
                }

                mrequestchangeList.setLayoutManager(new LinearLayoutManager(RequestChangeDisplayActivity.this));
                requestChangeListAdpter = new RequestChangeCreateListAdpter(RequestChangeDisplayActivity.this, filteredList);
                mrequestchangeList.setAdapter(requestChangeListAdpter);
                requestChangeListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    public void getDepartSelect(final String channelPartnerSelect) {
        jsonSchedule = new JSONObject() {
            {
                try {
                    put("Prefixtext", channelPartnerSelect);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("json exception", "json exception" + e);
                }
            }
        };


        Thread objectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();

                Log.i("json", "json" + jsonSchedule);
                //SEND RESPONSE
                // studentResponse = serviceAccess.SendHttpPost(baseURL, jsonSchedule);
                // departResponse = serviceAccess.SendHttpPost(Config.URL_GETALLDEPARTMENTBYPREFIX, jsonSchedule);
                departArrayList.clear();
                Cursor cursor = db.getNames(channelPartnerSelect);
                if (cursor.moveToFirst()) {
                    do {
                        /*Name name = new Name(
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                        );
                        names.add(name);*/

                        departmentDAO = new DepartmentDAO();
                        departArrayList.add(new DepartmentDAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPT_NAME))));
                    } while (cursor.moveToNext());
                }
                Log.i("resp", "loginResponse" + departResponse);


                try {
                    JSONArray callArrayList = new JSONArray(departResponse);
                    departArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        departmentDAO = new DepartmentDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        departArrayList.add(new DepartmentDAO(json_data.getString("id"), json_data.getString("dept_name")));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<DepartmentDAO>(getApplicationContext(), R.layout.item, departArrayList);
                        SearchDepart.setAdapter(aAdapter);

                        SearchDepart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                DepartmentDAO student = (DepartmentDAO) parent.getAdapter().getItem(i);
                                depart_id = student.getId();
                                clear_text.setVisibility(View.VISIBLE);
                                new initUsersSpinner().execute();
                                // prefEditor.putString("depart_id", depart_id);
                                //   prefEditor.commit();
                                //  TrainerDetailsView trainerDetailsView = new TrainerDetailsView();
                                // trainerDetailsView.show(getSupportFragmentManager(), "trainerDetailsView");
                                //Toast.makeText(getApplicationContext(), depart_id, Toast.LENGTH_LONG).show();
                                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            }
                        });
                        aAdapter.notifyDataSetChanged();

                    }
                });


            }
        });

        objectThread.start();

    }

    private class initUsersSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("user_id", preferences.getString("trainer_user_id", ""));
                        put("depart_id", depart_id);
                        //  put("user_id", "RS");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            usersResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTUSERNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + usersResponse);

            if (usersResponse.compareTo("") != 0) {
                if (isJSONValid(usersResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                edit_LeadLOCList.clear();
                                JSONArray LeadSourceJsonObj = new JSONArray(usersResponse);
                                if (LeadSourceJsonObj.length() > 0) {
                                    userslist.add(new RequestChangeUsersNameDAO("0", "Assign Any User", "", ""));
                                }
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    userslist.add(new RequestChangeUsersNameDAO(json_data.getString("id"), json_data.getString("first_name"), "", ""));
                                }
                                if (userslist.size() == 2) {
                                    requestChangeUsersNameDAO = userslist.get(1);

                                    edit_LeadLOCList.put(requestChangeUsersNameDAO.getId(), requestChangeUsersNameDAO.getUser_name());
                                    keys_Loc = edit_LeadLOCList.keySet();
                                }
                                jsonArray = new JSONArray(usersResponse);

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
                            //Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
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
            if (userslist.size() > 0) {
                String key_loc = "";
                String value_loc = "";
                if (userslist.size() == 2) {
                    if (!keys_Loc.isEmpty()) {
                        for (Iterator i = keys_Loc.iterator(); i.hasNext(); ) {
                            key_loc = (String) i.next();
                            value_loc = (String) edit_LeadLOCList.get(key_loc);
                            Log.d("keys ", "" + key_loc + " = " + value_loc);
                        }

                    }
                }
                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<RequestChangeUsersNameDAO> adapter = new ArrayAdapter<RequestChangeUsersNameDAO>(RequestChangeDisplayActivity.this, android.R.layout.simple_spinner_dropdown_item, userslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                usersName.setAdapter(adapter);
                selectSpinnerItemByValue(usersName, value_loc);
                usersName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        RequestChangeUsersNameDAO LeadSource = (RequestChangeUsersNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        user_id = LeadSource.getId();
                        userName = LeadSource.getUser_name();
                        if (user_id.equals("0")) {
                            status = 0;
                            pri_status_id = 0;
                            prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                            prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                            primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));

                            working.setBackgroundColor(Color.parseColor("#809E9E9E"));
                            notstart.setBackgroundColor(Color.parseColor("#809E9E9E"));
                            done.setBackgroundColor(Color.parseColor("#809E9E9E"));

                        }
                        new getRequestChangeList().execute();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                //  mProgressDialog.dismiss();
            } else {
                ArrayAdapter<RequestChangeUsersNameDAO> adapter = new ArrayAdapter<RequestChangeUsersNameDAO>(RequestChangeDisplayActivity.this, android.R.layout.simple_spinner_dropdown_item, userslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                usersName.setAdapter(adapter);
                // Close the progressdialog
                // mProgressDialog.dismiss();
            }
        }
    }

    //
    private class getRequestChangeList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(RequestChangeDisplayActivity.this);
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
                        put("user_id", user_id);
                        put("depart_id", Integer.parseInt(depart_id));
                        put("trainer_user_id", preferences.getString("trainer_user_id", ""));
                        // put("trainer_user_id", "RS");
                        put("status", status);
                        put("ticket_priority_status", pri_status_id);
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

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            displayRequestChangeResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTCHANGEBYUSERID, jsonLeadObj);
            Log.i("resp", "displayRequestChangeResponse" + displayRequestChangeResponse);

            Log.i("json", "json" + jsonObjNotification);
            myNotificationCountResponse = serviceAccess.SendHttpPost(Config.URL_CHECKMYPENDINGNOTIFICATIONS, jsonObjNotification);
            Log.i("resp", "myNotificationCountResponse" + myNotificationCountResponse);

            if (displayRequestChangeResponse.compareTo("") != 0) {
                if (isJSONValid(displayRequestChangeResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseRequestChangeList(displayRequestChangeResponse);
                                jsonArray = new JSONArray(displayRequestChangeResponse);

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
                            Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
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
            if (displayRequestChangeResponse.compareTo("") != 0) {
                requestChangeListAdpter = new RequestChangeCreateListAdpter(RequestChangeDisplayActivity.this, data);
                mrequestchangeList.setAdapter(requestChangeListAdpter);
                mrequestchangeList.setLayoutManager(new LinearLayoutManager(RequestChangeDisplayActivity.this));
                // requestChangeListAdpter.notifyDataSetChanged();
                if (preferences.getInt("position", 0) != 0) {
                    //  mrequestchangeList.smoothScrollToPosition(preferences.getInt("position", 0));
                }
                prefEditor.putInt("position", 0);
                prefEditor.commit();
                mrequestchangeList.setHasFixedSize(true);
                setUpItemTouchHelper();
                setUpAnimationDecoratorHelper();
                mProgressDialog.dismiss();
            }

            if (myNotificationCountResponse.compareTo("") != 0) {
                if (!notification_count.equals("0")) {
                    pendingNotification.setText(notification_count);
                }
                setBadge(getApplicationContext(), Integer.parseInt(notification_count));
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                setBadge(getApplicationContext(), Integer.parseInt("0"));
                pendingNotification.setText("");
                mProgressDialog.dismiss();
            }


            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.parseColor("#E6E6DC"));
                xMark = ContextCompat.getDrawable(RequestChangeDisplayActivity.this, R.drawable.ic_delete_black_24dp);
                xMark.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) RequestChangeDisplayActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                RequestChangeCreateListAdpter testAdapter = (RequestChangeCreateListAdpter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                String name = data.get(viewHolder.getAdapterPosition()).getRequest_subject();
                request_id = data.get(viewHolder.getAdapterPosition()).getId();
                final RequestChangeListDAO deletedItem = data.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();
                final RequestChangeCreateListAdpter adapter = (RequestChangeCreateListAdpter) mrequestchangeList.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                    // showing snack bar with Undo option
                   /* Snackbar snackbar = Snackbar.make(, name + " removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                           // adapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();*/
                    footer.setVisibility(View.VISIBLE);
                    textValue.setText(name);
                    SPLASH_TIME_OUT = 60000;

                    undoTicket.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Toast.makeText(getApplicationContext(), request_id, Toast.LENGTH_SHORT).show();
                            adapter.restoreItem(deletedItem, deletedIndex);
                            footer.setVisibility(View.GONE);
                            SPLASH_TIME_OUT = 0;
                            new undoRequest().execute();

                        }
                    });

                    new Handler().postDelayed(new Runnable() {

                        /*
                         * Showing splash screen with a timer. This will be useful when you
                         * want to show case your app logo / company
                         */

                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            footer.setVisibility(View.GONE);
                        }
                    }, SPLASH_TIME_OUT);

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mrequestchangeList);
    }

    private void setUpAnimationDecoratorHelper() {
        mrequestchangeList.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
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
    private void selectSpinnerItemByValue(Spinner spinner, String value) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private class undoRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //   mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            // mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("request_id", request_id);
                        put("ticket_delete_status", "1");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            //  centerListResponse = serviceAccess.SendHttpPost(Config.URL_DELETEREQUESTTICKET, jsonLeadObj);
            unduResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREQUESTTICKETDELETESTATUS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + unduResponse);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

        }
    }

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
    //


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
                                        status1 = jObject.getBoolean("status");

                                        if (status1) {
                                            mobileDeviceId = jObject.getString("mobile_deviceid");
                                            prefEditor.putString("email_id", jObject.getString("email_id"));
                                            prefEditor.putString("trainer_user_name", jObject.getString("trainer_name"));
                                            prefEditor.commit();
                                            if (!preferences.getString("deviceId", "").equals(mobileDeviceId)) {
                                                finish();
                                                prefEditor.putString("trainer_user_id", "");
                                                prefEditor.commit();
                                                Intent intent = new Intent(RequestChangeDisplayActivity.this, TrainerVerfiyActivity.class);
                                                startActivity(intent);
                                            } else {

                                            }

                                        } else {

                                            finish();
                                            prefEditor.putString("trainer_user_id", "");
                                            prefEditor.commit();
                                            Intent intent = new Intent(RequestChangeDisplayActivity.this, TrainerVerfiyActivity.class);
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
                                        status1 = jObject.getBoolean("status");

                                        if (status1) {
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

        new ForceUpdateAsync(currentVersion, RequestChangeDisplayActivity.this).execute();

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

