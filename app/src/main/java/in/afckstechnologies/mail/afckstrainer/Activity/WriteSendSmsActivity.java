package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.afckstechnologies.mail.afckstrainer.Adapter.GridViewAdapter;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender1;
import in.afckstechnologies.mail.afckstrainer.Models.TemplatesDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Utility;
import in.afckstechnologies.mail.afckstrainer.Utils.Utils;

public class WriteSendSmsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    GMailSender sender;
    GMailSender1 sender1;
    String start_date = "";
    String course_name = "";
    String notes = "";
    String timings = "";
    String frequency = "";
    String duration = "";
    String branch_name = "";
    String fees = "";

    EditText msgData1;
    ImageView msg_img, mail_img, whatsapp, addTemplate;
    ArrayList<String> studentMobileNoArrayList;
    ArrayList<String> nameArrayList;
    ArrayList<String> studentMailIdArrayList;
    ArrayList<String> studentMessageArrayList;
    String[] ary;
    String mail_id;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    String templatesResponse = "";
    ArrayList<TemplatesDAO> templatesDAOArrayList;
    private static String username = ""; //"info@afcks.com";
    private static String password = "";//"at!@#123";
    String title = "";
    String emailid = "";
    String subject = "";
    String message = "";
    public File mediaFile;
    String sms_type = "";

    String m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11;
    ImageView clear;
    String temp_id = "";
    String teamplateDeleteResponse = "";
    boolean status;
    String fileName = "";
    static String sms_user = "";
    static String sms_pass = "";
    int count = 1;
    int m_count = 0;
    private ProgressDialog dialog;
    ImageView defaulticon, attachicon;
// image

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_send_sms);
        // Add your mail Id and Password
        sender = new GMailSender(username, password);
        sender1 = new GMailSender1(username, password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sms_user = preferences.getString("sms_username", "");
        sms_pass = preferences.getString("sms_password", "");

        username = preferences.getString("mail_username", "");
        password = preferences.getString("mail_password", "");

        msgData1 = (EditText) findViewById(R.id.msgData);
        // msgData1.setText(msgData);

        mail_img = (ImageView) findViewById(R.id.mail_img);
        msg_img = (ImageView) findViewById(R.id.msg_img);
        defaulticon = (ImageView) findViewById(R.id.defaulticon);
        attachicon = (ImageView) findViewById(R.id.attachicon);
        whatsapp = (ImageView) findViewById(R.id.msg_whatsaap);
        clear = (ImageView) findViewById(R.id.clear);
        if (preferences.getString("trainer_user_id", "").equals("RS")) {
            clear.setVisibility(View.VISIBLE);
        }
        if (preferences.getString("trainer_user_id", "").equals("AK")) {
            clear.setVisibility(View.VISIBLE);
        }
        msg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("BUNDLE");
                studentMobileNoArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST");
                nameArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST1");
                studentMailIdArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST2");
                start_date = intent.getStringExtra("start_date");
                course_name = intent.getStringExtra("course_name");
                notes = intent.getStringExtra("notes");
                timings = intent.getStringExtra("timings");
                frequency = intent.getStringExtra("frequency");
                duration = intent.getStringExtra("duration");
                branch_name = intent.getStringExtra("branch_name");
                fees = intent.getStringExtra("fees");
                final CharSequence[] items = {" AFCKST ", " Mobile SMS "};
                AlertDialog dialog = new AlertDialog.Builder(WriteSendSmsActivity.this)
                        .setTitle("Select The SMS Mode")
                        .setCancelable(false)
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {


                                switch (item) {
                                    case 0:
                                        // Your code when first option seletced
                                        sms_type = "AFCKST";
                                        break;
                                    case 1:
                                        // Your code when 2nd  option seletced
                                        sms_type = "Mobile_SMS";
                                        break;


                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here
                                Log.d("sms_type", sms_type);
                                if (!sms_type.equals("")) {
                                    //sms sending
                                    if (sms_type.equals("AFCKST")) {
                                        for (int i = 0; i < studentMobileNoArrayList.size(); i++) {
                                            String message = "";
                                            String tempMobileNumber = studentMobileNoArrayList.get(i).toString();
                                            String userName = nameArrayList.get(i).toString();
                                            // message = "Hi" + " " + userName + System.getProperty("line.separator") + msgData;
                                            // message = msgData1.getText().toString().replace("[first_name]", userName).replace("[course_name]", course_name).replace("[start_date]", start_date).replace("[timings]", timings);
                                            boolean fristName = msgData1.getText().toString().contains("[first_name]");
                                            boolean courseName = msgData1.getText().toString().contains("[course_name]");
                                            boolean startDate = msgData1.getText().toString().contains("[start_date]");
                                            boolean note = msgData1.getText().toString().contains("[notes]");
                                            boolean timings1 = msgData1.getText().toString().contains("[timings]");
                                            boolean branch = msgData1.getText().toString().contains("[branch_name]");
                                            boolean frequency1 = msgData1.getText().toString().contains("[frequency]");
                                            boolean duration1 = msgData1.getText().toString().contains("[duration]");

                                            if (fristName) {
                                                m1 = msgData1.getText().toString().replace("[first_name]", userName);
                                                message = m1;
                                            }
                                            if (courseName) {
                                                m2 = message.replace("[course_name]", course_name);
                                                message = m2;
                                            }
                                            if (startDate) {
                                                m3 = message.replace("[start_date]", start_date);
                                                message = m3;
                                            }
                                            if (note) {
                                                m4 = message.replace("[notes]", notes);
                                                message = m4;
                                            }
                                            if (branch) {
                                                m5 = message.replace("[branch_name]", branch_name);
                                                message = m5;
                                            }
                                            if (frequency1) {
                                                m6 = message.replace("[frequency]", frequency);
                                                message = m6;
                                            }
                                            if (duration1) {
                                                m7 = message.replace("[duration]", duration);
                                                message = m7;
                                            }
                                            if (timings1) {
                                                m8 = message.replace("[timings]", timings);
                                                message = m8;
                                            }
                                            Log.d("msg", tempMobileNumber + "  " + message);
                                            System.out.println("message--->" + message);
                                            String result = sendSms1(tempMobileNumber, message);
                                            System.out.println("result :" + result);
                                        }
                                    } else if (sms_type.equals("Mobile_SMS")) {

                                        for (int i = 0; i < studentMobileNoArrayList.size(); i++) {
                                            String message = "";
                                            String tempMobileNumber = studentMobileNoArrayList.get(i).toString();
                                            String userName = nameArrayList.get(i).toString();
                                            // message = "Hi" + " " + userName + System.getProperty("line.separator") + msgData;
                                            // message = msgData1.getText().toString().replace("[first_name]", userName).replace("[course_name]", course_name).replace("[start_date]", start_date).replace("[timings]", timings);
                                            boolean fristName = msgData1.getText().toString().contains("[first_name]");
                                            boolean courseName = msgData1.getText().toString().contains("[course_name]");
                                            boolean startDate = msgData1.getText().toString().contains("[start_date]");
                                            boolean note = msgData1.getText().toString().contains("[notes]");
                                            boolean timings1 = msgData1.getText().toString().contains("[timings]");
                                            boolean branch = msgData1.getText().toString().contains("[branch_name]");
                                            boolean frequency1 = msgData1.getText().toString().contains("[frequency]");
                                            boolean duration1 = msgData1.getText().toString().contains("[duration]");

                                            if (fristName) {
                                                m1 = msgData1.getText().toString().replace("[first_name]", userName);
                                                message = m1;
                                            }
                                            if (courseName) {
                                                m2 = message.replace("[course_name]", course_name);
                                                message = m2;
                                            }
                                            if (startDate) {
                                                m3 = message.replace("[start_date]", start_date);
                                                message = m3;
                                            }
                                            if (note) {
                                                m4 = message.replace("[notes]", notes);
                                                message = m4;
                                            }
                                            if (branch) {
                                                m5 = message.replace("[branch_name]", branch_name);
                                                message = m5;
                                            }
                                            if (frequency1) {
                                                m6 = message.replace("[frequency]", frequency);
                                                message = m6;
                                            }
                                            if (duration1) {
                                                m7 = message.replace("[duration]", duration);
                                                message = m7;
                                            }
                                            if (timings1) {
                                                m8 = message.replace("[timings]", timings);
                                                message = m8;
                                            }
                                            Log.d("msg", tempMobileNumber + "  " + message);
                                            System.out.println("message--->" + message);
                                            try {
                                                Thread.sleep(2000);
                                                sendSMS(tempMobileNumber, message);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }


                                        }

                                    }

                                } else {
                                    Toast.makeText(WriteSendSmsActivity.this, "Please select SMS Mode!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel
                            }
                        }).create();
                dialog.show();


            }
        });

        mail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("BUNDLE");
                studentMobileNoArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST");
                nameArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST1");
                studentMailIdArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST2");
                studentMessageArrayList = new ArrayList<>();
                start_date = intent.getStringExtra("start_date");
                course_name = intent.getStringExtra("course_name");
                notes = intent.getStringExtra("notes");
                timings = intent.getStringExtra("timings");
                frequency = intent.getStringExtra("frequency");
                duration = intent.getStringExtra("duration");
                branch_name = intent.getStringExtra("branch_name");
                fees = intent.getStringExtra("fees");
                subject = course_name + " " + "Batch is starting from" + " " + start_date + " " + "at" + " " + branch_name;

                if (studentMailIdArrayList.size() > 0) {
                    mail_id = Utils.convertArrayListToStringWithComma(studentMailIdArrayList);
                    ary = mail_id.split(",");

                }

                //
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WriteSendSmsActivity.this);
                alertDialog.setTitle("Email Subject");
                alertDialog.setMessage("Enter Email Subject");

                final EditText input = new EditText(WriteSendSmsActivity.this);
                input.setText(course_name + " Batch starting from " + start_date + ".");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                subject = input.getText().toString().trim();
                                if (!subject.equals("")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteSendSmsActivity.this);
                                    builder.setMessage("Do you want to send Mail ?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    for (int i = 0; i < studentMailIdArrayList.size(); i++) {

                                                        emailid = studentMailIdArrayList.get(i).toString();
                                                        String userName = nameArrayList.get(i).toString();
                                                        // message = "Hi" + " " + userName + System.getProperty("line.separator") + msgData;
                                                        //  message = msgData1.getText().toString().replace("[first_name]", userName).replace("[course_name]", course_name).replace("[start_date]", start_date).replace("[timings]", timings);
                                                        boolean fristName = msgData1.getText().toString().contains("[first_name]");
                                                        boolean courseName = msgData1.getText().toString().contains("[course_name]");
                                                        boolean startDate = msgData1.getText().toString().contains("[start_date]");
                                                        boolean note = msgData1.getText().toString().contains("[notes]");
                                                        boolean timings1 = msgData1.getText().toString().contains("[timings]");
                                                        boolean branch = msgData1.getText().toString().contains("[branch_name]");
                                                        boolean frequency1 = msgData1.getText().toString().contains("[frequency]");
                                                        boolean duration1 = msgData1.getText().toString().contains("[duration]");

                                                        if (fristName) {
                                                            m1 = msgData1.getText().toString().replace("[first_name]", userName);
                                                            message = m1;
                                                        }
                                                        if (courseName) {
                                                            m2 = message.replace("[course_name]", course_name);
                                                            message = m2;
                                                        }
                                                        if (startDate) {
                                                            m3 = message.replace("[start_date]", start_date);
                                                            message = m3;
                                                        }
                                                        if (note) {
                                                            m4 = message.replace("[notes]", notes);
                                                            message = m4;
                                                        }
                                                        if (branch) {
                                                            m5 = message.replace("[branch_name]", branch_name);
                                                            message = m5;
                                                        }
                                                        if (frequency1) {
                                                            m6 = message.replace("[frequency]", frequency);
                                                            message = m6;
                                                        }
                                                        if (duration1) {
                                                            m7 = message.replace("[duration]", duration);
                                                            message = m7;
                                                        }
                                                        if (timings1) {
                                                            m8 = message.replace("[timings]", timings);
                                                            message = m8;
                                                        }
                                                        System.out.println("message--->" + message);
                                                        studentMessageArrayList.add(message);

                                                    }

                                                    new SendingMailsTask().execute(m_count + "", studentMessageArrayList.get(m_count));

                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //  Action for 'NO' Button
                                                    dialog.cancel();
                                                }
                                            });

                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle("Sending Mail");
                                    alert.show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please enter subject title", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();


            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("BUNDLE");
                studentMobileNoArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST");
                nameArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST1");
                studentMailIdArrayList = (ArrayList<String>) args.getSerializable("ARRAYLIST2");
                start_date = intent.getStringExtra("start_date");
                course_name = intent.getStringExtra("course_name");
                notes = intent.getStringExtra("notes");
                timings = intent.getStringExtra("timings");
                frequency = intent.getStringExtra("frequency");
                duration = intent.getStringExtra("duration");
                branch_name = intent.getStringExtra("branch_name");
                fees = intent.getStringExtra("fees");

                String smsNumber = "7057326842";
                String smsText = "helo";
                PackageManager pm = getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    // message = msgData1.getText().toString().replace("[first_name]", "").replace("[course_name]", course_name).replace("[start_date]", start_date).replace("[timings]", timings);
                    boolean fristName = msgData1.getText().toString().contains("[first_name]");
                    boolean courseName = msgData1.getText().toString().contains("[course_name]");
                    boolean startDate = msgData1.getText().toString().contains("[start_date]");
                    boolean note = msgData1.getText().toString().contains("[notes]");
                    boolean timings1 = msgData1.getText().toString().contains("[timings]");
                    boolean branch = msgData1.getText().toString().contains("[branch_name]");
                    boolean frequency1 = msgData1.getText().toString().contains("[frequency]");
                    boolean duration1 = msgData1.getText().toString().contains("[duration]");

                    if (fristName) {
                        m1 = msgData1.getText().toString().replace("[first_name]", "");
                        message = m1;
                    }
                    if (courseName) {
                        m2 = message.replace("[course_name]", course_name);
                        message = m2;
                    }
                    if (startDate) {
                        m3 = message.replace("[start_date]", start_date);
                        message = m3;
                    }
                    if (note) {
                        m4 = message.replace("[notes]", notes);
                        message = m4;
                    }
                    if (branch) {
                        m5 = message.replace("[branch_name]", branch_name);
                        message = m5;
                    }
                    if (frequency1) {
                        m6 = message.replace("[frequency]", frequency);
                        message = m6;
                    }
                    if (duration1) {
                        m7 = message.replace("[duration]", duration);
                        message = m7;
                    }
                    if (timings1) {
                        m8 = message.replace("[timings]", timings);
                        message = m8;
                    }
                    Log.d("message", message);
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(WriteSendSmsActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }
            }


        });


        //show template

        // initTemplatesSpinner();
        new initTemplatesSpinner().execute();
        addTemplate = (ImageView) findViewById(R.id.addTemplate);
        addTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WriteSendSmsActivity.this);
                alertDialog.setTitle("Template Title");
                alertDialog.setMessage("Enter Title");

                final EditText input = new EditText(WriteSendSmsActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                title = input.getText().toString();
                                Intent intent = new Intent(WriteSendSmsActivity.this, AddTemplatesActivity.class);
                                intent.putExtra("title", title);
                                startActivity(intent);


                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        defaulticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("template_txt_copy", msgData1.getText().toString().trim());
                prefEditor.commit();
                Toast.makeText(getApplicationContext(), "Set Default Template Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        attachicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                selectImage();
            }
        });

        attachicon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!preferences.getString("attach_file_path", "").equals("")) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    ImageView image = new ImageView(WriteSendSmsActivity.this);
                    // image.setImageResource(R.drawable.logo_afcks);

                    File imgFile = new File(preferences.getString("attach_file_path", ""));
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        image.setImageBitmap(myBitmap);

                    }
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(WriteSendSmsActivity.this).
                                    setMessage("Do you want to remove this image").
                                    setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            prefEditor.putString("attach_file_path", "");
                                            prefEditor.commit();
                                        }
                                    }).
                                    setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .
                                            setView(image);
                    builder.create().show();
                } else {
                    Toast.makeText(getApplicationContext(), "Don't have any attachment", Toast.LENGTH_SHORT).show();
                }
                return true;
            }


        });
        AddTemplatesActivity.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new initTemplatesSpinner().execute();
            }
        });

    }


    //show Templates

    private class initTemplatesSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(WriteSendSmsActivity.this);
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
                        //  put("user_id", "" + preferences.getInt("user_id", 0));
                        // put("branch_id", flag);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            templatesResponse = serviceAccess.SendHttpPost(Config.URL_VIEWTEMPLATE, jsonLeadObj);
            Log.i("resp", "leadListResponse" + templatesResponse);

            if (templatesResponse.compareTo("") != 0) {
                if (isJSONValid(templatesResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                templatesDAOArrayList = new ArrayList<>();


                                JSONArray LeadSourceJsonObj = new JSONArray(templatesResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    templatesDAOArrayList.add(new TemplatesDAO(json_data.getString("ID"), json_data.getString("Template_Text")));

                                }

                                jsonArray = new JSONArray(templatesResponse);

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
            if (templatesResponse.compareTo("") != 0) {


                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerTemplates);
                ArrayAdapter<TemplatesDAO> adapter = new ArrayAdapter<TemplatesDAO>(WriteSendSmsActivity.this, R.layout.multiline_spinner_dropdown_item, templatesDAOArrayList);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        TemplatesDAO LeadSource = (TemplatesDAO) parent.getSelectedItem();
                        //Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getID() + ",  Source Name : " + LeadSource.getTemplate_Text(), Toast.LENGTH_SHORT).show();
                        temp_id = LeadSource.getID();
                        msgData1.setText(LeadSource.getTemplate_Text());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), temp_id, Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(WriteSendSmsActivity.this);
                        builder.setMessage("Do you want to delete Template ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new deleteUser().execute();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });

                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Deleting Template");
                        alert.show();

                    }
                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    //

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
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


    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(WriteSendSmsActivity.this);
            pDialog.setMessage("Sending mail...");
            pDialog.setTitle("Please wait...");
            pDialog.show();

        }


        @Override
        protected Void doInBackground(Void... mApi) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Toast.makeText(getApplicationContext(), "Sending mail  " + (count++) + "/" + studentMailIdArrayList.size(), Toast.LENGTH_SHORT).show();
                        sender = new GMailSender(username, password);
                        sender.sendMail(subject, message, username, emailid, fileName);
                        // sender1.sendMail(subject, message, username, emailid);

               /* if(preferences.getString("mail_a_flag","").equals("send_details")) {
                    sender1.sendMail(subject, message, username, emailid);
                }
*/
                    } catch (Exception ex) {

                    }

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("result", "" + result);
            pDialog.cancel();
            // Toast.makeText(getApplicationContext(),"Sending mail "+(count++)+"/"+ studentMailIdArrayList.size(), Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_SHORT).show();
        }


    }

    private class deleteUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(WriteSendSmsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Deleting User...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("template_id", temp_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            teamplateDeleteResponse = serviceAccess.SendHttpPost(Config.URL_DELETE_TEMPLATE, jsonLeadObj);
            Log.i("resp", "teamplateDeleteResponse" + teamplateDeleteResponse);
            if (teamplateDeleteResponse.compareTo("") != 0) {
                if (isJSONValid(teamplateDeleteResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(teamplateDeleteResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(teamplateDeleteResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                new initTemplatesSpinner().execute();

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    class SendingMailsTask extends AsyncTask<String, Void, String> {

        String sResponse = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(WriteSendSmsActivity.this, "Sending mail " + (count++) + " / " + studentMailIdArrayList.size(), "Please wait...", true);
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                //  Toast.makeText(getApplicationContext(), "Sending mail  " + (count++) + "/"+ studentMailIdArrayList.get(Integer.parseInt(params[0]))+ studentMailIdArrayList.size(), Toast.LENGTH_SHORT).show();
                sender = new GMailSender(username, password);
                sResponse = sender.sendMail(subject, studentMessageArrayList.get(Integer.parseInt(params[0])), username, studentMailIdArrayList.get(Integer.parseInt(params[0])), fileName);
                //sResponse="success";

            } catch (Exception ex) {
                sResponse = "null";
            }


            return sResponse;
        }

        @Override
        protected void onPostExecute(String sResponse) {
            try {
                if (dialog.isShowing())
                    dialog.dismiss();

                if (sResponse != null) {
                    //  Toast.makeText(getApplicationContext(), sResponse + " Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                    m_count++;
                    if (m_count < studentMessageArrayList.size()) {


                        new SendingMailsTask().execute(m_count + "", studentMessageArrayList.get(m_count));

                    } else {
                        dialog.dismiss();
                    }

                } else {
                    finish();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
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
//image process

    private void selectImage() {
        final CharSequence[] items = {"Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(WriteSendSmsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(WriteSendSmsActivity.this);

                if (items[item].equals("Choose from Library")) {
                    if (result)
                        galleryIntent();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                if (data.getData() != null) {

                    Uri mImageUri = data.getData();
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);

                    cursor.close();
                    Log.v("LOG_TAG", "imageEncoded" + imageEncoded);
                    Log.v("LOG_TAG", "Selected Images" + mImageUri);


                    File myFile = new File(mImageUri.getPath());
                    prefEditor.putString("attach_file_path", getPathFromUri(WriteSendSmsActivity.this, mImageUri));
                    prefEditor.commit();
                    Toast.makeText(getApplicationContext(), "Set image successfully", Toast.LENGTH_SHORT).show();
                  /*  Log.d("LOG_TAG", "imageToUpload" + getPathFromUri(WriteSendSmsActivity.this, mImageUri));
                    String file_name = getFileName(mImageUri);
                    String file_path = getPathFromUri(WriteSendSmsActivity.this, mImageUri);*/


                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Exception", "" + e);
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
