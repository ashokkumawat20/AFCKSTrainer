package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.OnGoingBatchesListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.PreBatchDetailsListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsReceiver;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AndroidMultiPartEntity;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.ActualBatchTimingsView;
import in.afckstechnologies.mail.afckstrainer.View.BatchModifyView;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.MultipleCommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.RegistrationView;
import in.afckstechnologies.mail.afckstrainer.View.StatsBatchStudentsView;
import in.afckstechnologies.mail.afckstrainer.View.StudentCorporateRegView;
import in.afckstechnologies.mail.afckstrainer.View.StudentDiscontinueEntryView;
import in.afckstechnologies.mail.afckstrainer.View.StudentFeesEntryView;
import in.afckstechnologies.mail.afckstrainer.View.StudentTransferFeesEntryView;

import static android.graphics.Color.*;
import static in.afckstechnologies.mail.afckstrainer.R.id.first_name;


public class StudentList extends AppCompatActivity {

    // LogCat tag
    private static final String TAG = StudentList.class.getSimpleName();
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    AutoCompleteTextView autoCompleteTextViewBatch;
    String studentResponse = "";
    public List<BatchDAO> batchArrayList;
    public ArrayAdapter<BatchDAO> aAdapter;
    public ArrayAdapter<StudentListDAO> studentListDAOArrayAdapter;
    public BatchDAO batchDAO;
    String batch_id = "";
    private JSONObject jsonSchedule, jsonObj;
    private JSONObject jsonLeadObj, jsonLeadObj1, jsonObject;
    ProgressDialog mProgressDialog;
    String studentListResponse = "";
    JSONArray jsonArray;
    List<StudentsDAO> data;
    StudentListAdpter studentListAdpter;
    private RecyclerView mstudentList;
    private FloatingActionButton fab;
    String newTextBatch, newTextStudent;
    AutoCompleteTextView autoCompleteTextViewStudent;
    ImageView add_student, clear, clear_batch, edit_batch, info_batch, ne_batch;
    public List<StudentListDAO> studentArrayList;
    String student_id = "", pendingTaskResponse = "";
    public StudentListDAO studentListDAO;
    String addStudentRespone = "", attendanceListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    //sendind data to next activity
    Button sendData, batchEnd, batchEnded;
    ImageView batchAttendance;
    ArrayList<String> studentMailIdArrayList;
    ArrayList<String> studentMobileNoArrayList;
    ArrayList<String> nameArrayList;
    ArrayList<String> verifyIdArrayList;
    ArrayList<String> genderArrayList;
    ArrayList<String> studentNameArrayList;
    ArrayList<String> studentUserIdArrayList;
    ArrayList<String> studentBatchIdArrayList;
    String start_date = "";
    String course_name = "";
    String notes = "";
    String timings = "";
    String frequency = "";
    String duration = "";
    String branch_name = "";
    String fees = "", batch_code = "";

    RelativeLayout footer;


    String flag = "";

    String refno = "";
    String student_name = "";

    private static final String username = "info@afcks.com";
    private static String password = "at!@#123";
    GMailSender sender;
    String emailid = "";
    String subject = "Letter of completion";
    String mail_message = "";

    String fileName = "";

    ArrayList<Uri> lettersUri = new ArrayList<Uri>();
    int statusCode;
    long totalSize = 0;
    String completion_status = "";

    //

    String attendenceStudentId = "";
    String attendenceBatchId = "";
    String attendencePresent = "";

    int temp = 0, temp_size;
    CheckBox chkAll;
    Button sendingLetter;
    //
    int Status_id = 1;
    RadioGroup radioGroup;
    private RadioButton radioButton;
    int pos;
    int pos1;
    int pa = 0;

    String letterid = "", studentLetterResponse = "";
    String verifycode = "";
    String gender = "";
    RadioButton normal, corporate;

    String FeesApplicable = "";

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_main_layout);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Add your mail Id and Password
        sender = new GMailSender(username, password);
        prefEditor.putString("mail_a_flag", "enter_student");
        prefEditor.commit();

        clear = (ImageView) findViewById(R.id.clear);
        clear_batch = (ImageView) findViewById(R.id.clear_batch);
        add_student = (ImageView) findViewById(R.id.add_student);
        edit_batch = (ImageView) findViewById(R.id.edit_batch);
        info_batch = (ImageView) findViewById(R.id.info_batch);
        ne_batch = (ImageView) findViewById(R.id.ne_batch);


        mstudentList = (RecyclerView) findViewById(R.id.studentsList);
        batchEnd = (Button) findViewById(R.id.batchEnd);
        batchAttendance = (ImageView) findViewById(R.id.batchAttendance);
        batchEnded = (Button) findViewById(R.id.batchEnded);
        sendingLetter = (Button) findViewById(R.id.sendingLetter);
        normal = (RadioButton) findViewById(R.id.normal);
        corporate = (RadioButton) findViewById(R.id.corporate);
        studentArrayList = new ArrayList<StudentListDAO>();
        batchArrayList = new ArrayList<BatchDAO>();
        autoCompleteTextViewStudent = (AutoCompleteTextView) findViewById(R.id.SearchStudent);
        footer = (RelativeLayout) findViewById(R.id.footer);
        autoCompleteTextViewBatch = (AutoCompleteTextView) findViewById(R.id.SearchBatch);
        if (preferences.getString("prebatchcall", "").equals("1")) {
            prefEditor.putString("prebatchcall", "0");
            prefEditor.commit();
            Intent intent = getIntent();
            autoCompleteTextViewBatch.setText(intent.getStringExtra("batch_id"));
            batch_id = intent.getStringExtra("batch_id");
            new getStudentList().execute();
        }

        if (preferences.getString("prebatchcall", "").equals("2")) {
            prefEditor.putString("prebatchcall", "0");
            prefEditor.commit();
            Intent intent = getIntent();
            autoCompleteTextViewBatch.setText(intent.getStringExtra("batch_id"));
            batch_id = intent.getStringExtra("batch_id");
            Status_id = 0;
            normal.setChecked(false);
            corporate.setChecked(true);
            new getStudentList().execute();
        }
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroup.indexOfChild(findViewById(checkedId));
                switch (pos) {
                    case 1:

                        Status_id = 0;
                        new getStudentList().execute();
                        // Toast.makeText(getApplicationContext(), "1"+Status_id,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        Status_id = 1;
                        //  Toast.makeText(getApplicationContext(), "2"+Status_id, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //The default selection is RadioButton 1
                        Status_id = 1;
                        new getStudentList().execute();
                        //   Toast.makeText(getApplicationContext(), "3"+Status_id,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        autoCompleteTextViewBatch.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

       autoCompleteTextViewBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              /* newTextBatch = s.toString();
                clear_batch.setVisibility(View.VISIBLE);
                edit_batch.setVisibility(View.VISIBLE);
                info_batch.setVisibility(View.GONE);
                ne_batch.setVisibility(View.GONE);
                getBatchSelect(newTextBatch);*/


            }

            @Override
            public void afterTextChanged(Editable s) {
                newTextBatch = s.toString();
                clear_batch.setVisibility(View.VISIBLE);
                edit_batch.setVisibility(View.VISIBLE);
                info_batch.setVisibility(View.GONE);
                ne_batch.setVisibility(View.GONE);
                getBatchSelect(newTextBatch);
            }
        });

        /*autoCompleteTextViewBatch.addTextChangedListener(new TextWatcher() {
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
                    if (!TextUtils.isEmpty(autoCompleteTextViewBatch.getText())) {
                        newTextBatch = autoCompleteTextViewBatch.getText().toString();
                        clear_batch.setVisibility(View.VISIBLE);
                        edit_batch.setVisibility(View.VISIBLE);
                        info_batch.setVisibility(View.GONE);
                        ne_batch.setVisibility(View.GONE);
                        getBatchSelect(newTextBatch);

                    }
                }
                return false;
            }
        });
*/
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefEditor.putString("student_name", newTextStudent);
                prefEditor.commit();
                RegistrationView registrationView = new RegistrationView();
                registrationView.show(getSupportFragmentManager(), "registrationView");

            }
        });


        autoCompleteTextViewStudent.addTextChangedListener(new TextWatcher() {
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
                    if (!TextUtils.isEmpty(autoCompleteTextViewStudent.getText())) {
                        clear.setVisibility(View.VISIBLE);
                        getchannelPartnerSelect(autoCompleteTextViewStudent.getText().toString());

                    } else {
                        clear.setVisibility(View.GONE);
                        add_student.setVisibility(View.GONE);
                        student_id = "";
                    }
                }
                return false;
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentArrayList.clear();
                autoCompleteTextViewStudent.setText("");
                clear.setVisibility(View.GONE);
                add_student.setVisibility(View.GONE);


            }
        });

        clear_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewBatch.setText("");
                clear_batch.setVisibility(View.GONE);
                edit_batch.setVisibility(View.GONE);
                info_batch.setVisibility(View.VISIBLE);
                ne_batch.setVisibility(View.VISIBLE);
                batch_id = "";
                footer.setVisibility(View.GONE);
                if (studentListResponse.compareTo("") != 0) {
                    data.clear(); // this list which you hava passed in Adapter for your listview
                    studentListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }


            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!batch_id.equals("")) {
                    if (!gender.equals("")) {
                       /* AlertDialog.Builder builder = new AlertDialog.Builder(StudentList.this);
                        builder.setMessage("Do you want to add student in current batch ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       if (!student_id.equals("")) {
                                            new submitData().execute();
                                            dialog.cancel();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please select Student", Toast.LENGTH_LONG).show();
                                             dialog.cancel();
                                        }

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
                        alert.setTitle("Adding Student");
                        alert.show();
                        */


//Dialog code
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StudentList.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_app_updates, null);
                        CheckBox mCheckBox = mView.findViewById(R.id.checkBox);
                        mCheckBox.setChecked(getDialogStatus());
                        mBuilder.setTitle("Add Guest Student");
                        mBuilder.setMessage("If below is checked Student will get fees reminder ");
                        mBuilder.setView(mView);
                        mBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (!student_id.equals("")) {
                                    new submitData().execute();
                                    if (getDialogStatus()) {
                                        FeesApplicable = "1";
                                    } else {
                                        FeesApplicable = "0";
                                    }
                                    dialogInterface.cancel();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please select Student", Toast.LENGTH_LONG).show();
                                    dialogInterface.cancel();
                                }

                            }
                        });
                        mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    storeDialogStatus(true);
                                } else {
                                    storeDialogStatus(false);
                                }
                            }
                        });
                        mDialog.show();
                        /*if(getDialogStatus()){
                            mDialog.hide();
                        }else{
                            mDialog.show();
                        }
*/

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Update Gender...", Toast.LENGTH_LONG).show();
                        prefEditor.putString("user_id", student_id);
                        prefEditor.putString("edit_u_p_flag", "fu");
                        prefEditor.commit();
                        Intent intent = new Intent(StudentList.this, Activity_User_Profile.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter Batch code ", Toast.LENGTH_LONG).show();
                }
            }
        });


        /** Getting reference to checkbox available in the main.xml layout */
        chkAll = (CheckBox) findViewById(R.id.chkAllSelected);
        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    List<StudentsDAO> list = ((StudentListAdpter) studentListAdpter).getSservicelist();
                    for (StudentsDAO workout : list) {
                        workout.setSelected(true);

                    }

                    ((StudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                } else {
                    List<StudentsDAO> list = ((StudentListAdpter) studentListAdpter).getSservicelist();
                    for (StudentsDAO workout : list) {
                        workout.setSelected(false);

                    }

                    ((StudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                }
            }
        });

        sendData = (Button) findViewById(R.id.sendData);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String data1 = "";
                String data2 = "";
                String data3 = "";
                studentMobileNoArrayList = new ArrayList<>();
                nameArrayList = new ArrayList<>();
                studentMailIdArrayList = new ArrayList<>();
                List<StudentsDAO> stList = ((StudentListAdpter) studentListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    StudentsDAO serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        data1 = serviceListDAO.getMobile_no().toString();
                        data2 = serviceListDAO.getFirst_name().toString();
                        data3 = serviceListDAO.getEmail_id().toString();
                        studentMobileNoArrayList.add(data1);
                        nameArrayList.add(data2);
                        studentMailIdArrayList.add(data3);
                    } else {
                        System.out.println("not selected");
                    }
                }

                if (studentMobileNoArrayList.size() > 0) {
                    Intent intent = new Intent(StudentList.this, WriteSendSmsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) studentMobileNoArrayList);
                    args.putSerializable("ARRAYLIST1", (Serializable) nameArrayList);
                    args.putSerializable("ARRAYLIST2", (Serializable) studentMailIdArrayList);
                    intent.putExtra("BUNDLE", args);
                    intent.putExtra("start_date", start_date);
                    intent.putExtra("course_name", course_name);
                    intent.putExtra("notes", notes);
                    intent.putExtra("timings", timings);
                    intent.putExtra("frequency", frequency);
                    intent.putExtra("duration", duration);
                    intent.putExtra("branch_name", branch_name);
                    intent.putExtra("fees", fees);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });

        batchEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentList.this);
                builder.setMessage("Do you want to close this batch ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    dialog.cancel();

                                    new upDateCompletionIdData().execute();


                                } catch (Exception ex) {
                                    Toast.makeText(StudentList.this, ex.toString(), Toast.LENGTH_LONG).show();
                                }
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
                alert.setTitle("Closing Btach");
                alert.show();

            }
        });


        batchAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data1 = "";
                String data2 = "";
                String data3 = "";
                String data4 = "";
                studentMobileNoArrayList = new ArrayList<>();
                nameArrayList = new ArrayList<>();
                studentUserIdArrayList = new ArrayList<>();
                studentBatchIdArrayList = new ArrayList<>();
                List<StudentsDAO> stList = ((StudentListAdpter) studentListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    StudentsDAO serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        data1 = serviceListDAO.getUser_id().toString();
                        data2 = serviceListDAO.getBatchid().toString();
                        studentMobileNoArrayList.add(data1);
                        nameArrayList.add(data2);

                    } else if (serviceListDAO.isSelected() == false) {
                        data3 = serviceListDAO.getUser_id().toString();
                        data4 = serviceListDAO.getBatchid().toString();
                        Log.d("Apsent", data3);
                        studentUserIdArrayList.add(data3);
                        studentBatchIdArrayList.add(data4);

                    } else {
                        System.out.println("not selected");
                    }
                }

                temp_size = studentUserIdArrayList.size() + studentMobileNoArrayList.size();
                if (studentMobileNoArrayList.size() > 0) {
                  /*  AlertDialog.Builder builder = new AlertDialog.Builder(StudentList.this);
                    builder.setMessage("Do you want to Mark Attendance this batch ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    try {
                                        dialog.cancel();
                                        if (!batch_id.equals("")) {
                                            prefEditor.putString("batch_class", batch_id);
                                            prefEditor.commit();
                                            ActualBatchTimingsView actualBatchTimingsView = new ActualBatchTimingsView();
                                            actualBatchTimingsView.show(getSupportFragmentManager(), "actualBatchTimingsView");
                                        } else {
                                            Toast.makeText(getApplication(), "Please select batch ", Toast.LENGTH_SHORT).show();
                                        }

                                       *//* for (int i = 0; i < studentMobileNoArrayList.size(); i++) {
                                            attendenceStudentId = studentMobileNoArrayList.get(i);
                                            attendenceBatchId = nameArrayList.get(i);
                                            attendencePresent = "P";
                                            new submitAttendanceData().execute();
                                            Thread.sleep(500);
                                        }

                                        for (int i = 0; i < studentUserIdArrayList.size(); i++) {
                                            temp++;
                                            attendenceStudentId = studentUserIdArrayList.get(i);
                                            attendenceBatchId = studentBatchIdArrayList.get(i);
                                            attendencePresent = "A";
                                            new submitAttendanceData().execute();
                                            Thread.sleep(500);
                                        }*//*


                                    } catch (Exception ex) {
                                        Toast.makeText(StudentList.this, ex.toString(), Toast.LENGTH_LONG).show();
                                    }
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
                    alert.setTitle("Taking Attendance");
                    alert.show();*/

                    if (!batch_id.equals("")) {
                        prefEditor.putString("batch_class", batch_id);
                        prefEditor.commit();

                        ActualBatchTimingsView actualBatchTimingsView = new ActualBatchTimingsView();
                        actualBatchTimingsView.show(getSupportFragmentManager(), "actualBatchTimingsView");

                    } else {
                        Toast.makeText(getApplication(), "Please select batch ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }

            }
        });

        batchAttendance.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //  Toast.makeText(getApplicationContext(), "Batch code inserted successfully !", Toast.LENGTH_LONG).show();
                if (!batch_id.equals("")) {

                    new getVerifyCode().execute();
                } else {
                    Toast.makeText(getApplication(), "Please select batch ", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        batchEnded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Batch already ended !", Toast.LENGTH_LONG).show();
            }
        });


        sendingLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = "";
                String data2 = "";
                String data3 = "";
                String data4 = "";
                String data5 = "";
                studentMailIdArrayList = new ArrayList<>();
                nameArrayList = new ArrayList<>();
                verifyIdArrayList = new ArrayList<>();
                genderArrayList = new ArrayList<>();
                studentNameArrayList = new ArrayList<>();
                List<StudentsDAO> stList = ((StudentListAdpter) studentListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    StudentsDAO serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        data1 = serviceListDAO.getEmail_id().toString();
                        data2 = serviceListDAO.getFirst_name().toString();
                        data4 = serviceListDAO.getGender().toString();
                        data5 = serviceListDAO.getStudents_Name().toString();
                        studentMailIdArrayList.add(data1);
                        nameArrayList.add(data2);
                        verifyIdArrayList.add(data3);
                        genderArrayList.add(data4);
                        studentNameArrayList.add(data5);

                    } else {
                        System.out.println("not selected");
                    }
                }


                if (studentNameArrayList.size() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentList.this);
                    builder.setMessage("Do you want to Send Completion Letter ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    try {
                                        dialog.cancel();

                                        for (int i = 0; i < nameArrayList.size(); i++) {

                                            if (!verifyIdArrayList.get(i).equals("null")) {
                                                createandDisplayCorporatePdf(nameArrayList.get(i), verifyIdArrayList.get(i), genderArrayList.get(i), studentNameArrayList.get(i), studentMailIdArrayList.get(i));
                                                Thread.sleep(5000);
                                            } else {
                                                System.out.println("not selected");
                                            }
                                        }


                                    } catch (Exception ex) {
                                        Toast.makeText(StudentList.this, ex.toString(), Toast.LENGTH_LONG).show();
                                    }
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
                    alert.setTitle("Sending Completion Letter");
                    alert.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        edit_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!batch_id.equals("")) {
                    prefEditor.putString("batch_mofiy", batch_id);
                    prefEditor.commit();
                    BatchModifyView batchModifyView = new BatchModifyView();
                    batchModifyView.show(getSupportFragmentManager(), "batchModifyView");
                } else {
                    Toast.makeText(getApplication(), "Please select batch ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        info_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //StatsBatchStudentsView statsBatchStudentsView = new StatsBatchStudentsView();
                //statsBatchStudentsView.show(getSupportFragmentManager(), "statsBatchStudentsView");
                Intent intent = new Intent(StudentList.this, OngoingBatchesActivity.class);
                startActivity(intent);

            }
        });
        StudentFeesEntryView.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });

        Activity_User_Profile.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                gender = messageText;
                new getStudentList().execute();
            }
        });
        StudentListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });

        StudentCorporateRegView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });
        StudentDiscontinueEntryView.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });
        RegistrationView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                //Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
                autoCompleteTextViewStudent.setText(messageText);
                getchannelPartnerSelect(messageText);

            }
        });

        CommentAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });
        StudentTransferFeesEntryView.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });

        OnGoingBatchesListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                autoCompleteTextViewBatch.setText(messageText);
                batch_id = messageText;
                new getStudentList().execute();
            }
        });
        PreBatchDetailsListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                autoCompleteTextViewBatch.setText(messageText);
                batch_id = messageText;
                new getStudentList().execute();
            }
        });

        MultipleCommentAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                // Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_SHORT).show();
                List<StudentsDAO> list = ((StudentListAdpter) studentListAdpter).getSservicelist();
                for (StudentsDAO workout : list) {
                    if(preferences.getString("user_id", "").equals(workout.getUser_id())) {
                        workout.setNotes(messageText);
                    }

                }
                ((StudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
            }
        });
        ActualBatchTimingsView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                try {


                    for (int i = 0; i < studentMobileNoArrayList.size(); i++) {

                        attendenceStudentId = studentMobileNoArrayList.get(i);
                        attendenceBatchId = nameArrayList.get(i);
                        attendencePresent = "P";
                        new submitAttendanceData().execute();
                        Thread.sleep(500);
                    }

                    for (int i = 0; i < studentUserIdArrayList.size(); i++) {

                        attendenceStudentId = studentUserIdArrayList.get(i);
                        attendenceBatchId = studentBatchIdArrayList.get(i);
                        attendencePresent = "A";
                        new submitAttendanceData().execute();
                        Thread.sleep(500);
                    }


                } catch (Exception ex) {
                    Toast.makeText(StudentList.this, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    public void getchannelPartnerSelect(final String channelPartnerSelect) {

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
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTSBYID1, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentResponse);
                    studentArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        studentListDAO = new StudentListDAO();
                        JSONObject cityJsonObject = callArrayList.getJSONObject(i);
                        studentArrayList.add(new StudentListDAO(cityJsonObject.getString("Details"), cityJsonObject.getString("id"), cityJsonObject.getString("first_name"), cityJsonObject.getString("gender"), "", ""));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        studentListDAOArrayAdapter = new ArrayAdapter<StudentListDAO>(getApplicationContext(), R.layout.item, studentArrayList);
                        autoCompleteTextViewStudent.setAdapter(studentListDAOArrayAdapter);
                        if (studentArrayList.size() < 40)
                            autoCompleteTextViewStudent.setThreshold(1);
                        else autoCompleteTextViewStudent.setThreshold(2);
                        studentListDAOArrayAdapter.notifyDataSetChanged();
                        autoCompleteTextViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                StudentListDAO student = (StudentListDAO) parent.getAdapter().getItem(i);
                                student_id = student.getId();
                                if (!student_id.equals("")) {

                                    add_student.setVisibility(View.VISIBLE);
                                }

                                gender = student.getLast_Name();
                                Log.d("Id---->", student_id);
                                prefEditor.putString("student_id", student_id);
                                prefEditor.commit();
                                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            }
                        });
                        studentListDAOArrayAdapter.notifyDataSetChanged();

                    }
                });


            }
        });

        objectThread.start();

    }

    public void getBatchSelect(final String channelPartnerSelect) {
        String value = "";
        String value1 = "";
        if (channelPartnerSelect.startsWith("$")) {
            value1 = "1";
            value = channelPartnerSelect.substring(1);
        } else {
            value1 = "2";
            value = channelPartnerSelect;

        }

        final String finalValue = value;
        flag = value1;
        jsonSchedule = new JSONObject() {
            {
                try {
                    put("Prefixtext", finalValue);
                    put("user_id", preferences.getString("trainer_user_id", ""));
                    //put("user_id", "RS");

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
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBATCHTRAINERBYPREFIX, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentResponse);
                    batchArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        batchDAO = new BatchDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        batchArrayList.add(new BatchDAO(json_data.getString("id"), json_data.getString("course_id"), json_data.getString("branch_id"), json_data.getString("batch_code"), json_data.getString("start_date"), json_data.getString("timings"), json_data.getString("Notes"), json_data.getString("frequency"), json_data.getString("fees"), json_data.getString("duration"), json_data.getString("course_name"), json_data.getString("branch_name"), json_data.getString("batchtype"), json_data.getString("completion_status"), json_data.getString("batch_end_date"), json_data.getString("email_id"), json_data.getString("mobile_no"), json_data.getString("faculty_Name")));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<BatchDAO>(getApplicationContext(), R.layout.item, batchArrayList);
                        autoCompleteTextViewBatch.setAdapter(aAdapter);

                        autoCompleteTextViewBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                BatchDAO student = (BatchDAO) parent.getAdapter().getItem(i);
                                String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", student.getStart_date());
                                //  Toast.makeText(getApplicationContext(), "Source ID: " +date_after+"   "+ LeadSource.getId() + ",  Source Name : " + LeadSource.getBatch_code(), Toast.LENGTH_SHORT).show();
                                start_date = date_after;
                                course_name = student.getCourse_name();
                                notes = student.getNotes();
                                timings = student.getTimings();
                                duration = student.getDuration();
                                fees = student.getFees();
                                frequency = student.getFrequency();
                                branch_name = student.getBranch_name();
                                batch_id = student.getId();
                                batch_code = student.getBatch_code();
                                completion_status = student.getCompletion_status();
                                Log.d("completion_status->", completion_status);
                                Log.d("trainer mail id->", student.getTrainer_mail_id());
                                new getStudentList().execute();
                                Log.d("Id---->", student.getId() + "" + student.getCourse_name());
                                prefEditor.putString("batch_id", batch_id);
                                prefEditor.putString("course_name", student.getCourse_name());
                                prefEditor.putString("batch_code", student.getBatch_code());
                                prefEditor.putString("batch_fees", student.getFees());
                                prefEditor.putString("sendingmailid", student.getTrainer_mail_id());
                                prefEditor.putString("trainer_mobile_no", student.getTrainer_mobile_no());
                                prefEditor.putString("trainer_name", student.getTrainer_name());
                                prefEditor.commit();
                                footer.setVisibility(View.VISIBLE);

                                if (completion_status.equals("1")) {
                                    // batchEnd.setVisibility(View.GONE);
                                    //  batchEnded.setVisibility(View.VISIBLE);

                                    batchAttendance.setVisibility(View.GONE);

                                }
                                if (completion_status.equals("0")) {
                                    //    batchEnd.setVisibility(View.VISIBLE);
                                    //    batchEnded.setVisibility(View.GONE);
                                    batchAttendance.setVisibility(View.VISIBLE);

                                }
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

    //
    private class getStudentList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(StudentList.this);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //    mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        //put("course_id", course_id);
                        put("Status", Status_id);
                        put("batch_id", batch_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            studentListResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_STUDENTS, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + studentListResponse);
            if (studentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseStudentList(studentListResponse);
                                jsonArray = new JSONArray(studentListResponse);

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
            if (studentListResponse.compareTo("") != 0) {
                studentListAdpter = new StudentListAdpter(StudentList.this, data);
                mstudentList.setAdapter(studentListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(StudentList.this));
                studentListAdpter.notifyDataSetChanged();
                // mstudentList.setHasFixedSize(true);
                // setUpItemTouchHelper();
                //setUpAnimationDecoratorHelper();
                //  mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //   mProgressDialog.dismiss();
            }
        }
    }

//

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentList.this);
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
                        put("BatchID", batch_id);
                        put("UserID", student_id);
                        put("FeesApplicable", FeesApplicable);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addStudentRespone = serviceAccess.SendHttpPost(Config.URL_ADD_STUDENT_INBATCH, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addStudentRespone);


            if (addStudentRespone.compareTo("") != 0) {
                if (isJSONValid(addStudentRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addStudentRespone);
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
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


                // Close the progressdialog
                mProgressDialog.dismiss();
                new getStudentList().execute();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
        }
    }
//

    private class upDateCompletionIdData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentList.this);
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
                        put("batch_code", batch_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addStudentRespone = serviceAccess.SendHttpPost(Config.URL_UPDATEBATCHEND, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addStudentRespone);


            if (addStudentRespone.compareTo("") != 0) {
                if (isJSONValid(addStudentRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addStudentRespone);
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
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                autoCompleteTextViewBatch.setText("");
                clear_batch.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                if (studentListResponse.compareTo("") != 0) {
                    data.clear(); // this list which you hava passed in Adapter for your listview
                    studentListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }

                // Close the progressdialog
                mProgressDialog.dismiss();
                //   getBatchSelect(batch_id);
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
        }
    }

    private class getVerifyCode extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentList.this);
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


                AlertDialog.Builder builder = new AlertDialog.Builder(StudentList.this);
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
                new updateBatchCodeData().execute();
            } else {

                Toast.makeText(getApplicationContext(), "Please enter your mobile no in website to login.", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

    private class updateBatchCodeData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentList.this);
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
                        put("current_batch", batch_id);
                        put("id", preferences.getString("trainer_user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addStudentRespone = serviceAccess.SendHttpPost(Config.URL_UPDATEBTACHCODE, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addStudentRespone);


            if (addStudentRespone.compareTo("") != 0) {
                if (isJSONValid(addStudentRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addStudentRespone);
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
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                //   getBatchSelect(batch_id);
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
        }
    }

    //creating completion leter
    public void createandDisplayCorporatePdf(String first_name, String letter_id, String sex, String student_name, String email) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String dayPre = getDayOfMonthSuffix(day);
        String date_after = formateDateFromstring("yyyy-MM-dd", " d'" + dayPre + "' MMM yyyy", date);
        String endMonthName = formateDateFromstring("yyyy-MM-dd", "MMM-yyyy", date);
        String startMonthName = formateDateFromstring("dd-MMM-yyyy", "MMM", start_date);
        String nameF = "";
        String nameL = "";
        letterid = letter_id;
        String fee_status = "";
        String gender = "";

        if (sex.equals("Male")) {
            gender = "Mr. ";
            nameF = "him";
            nameL = "his";
        } else if (sex.equals("Female")) {
            gender = "Ms. ";
            nameF = "her";
            nameL = "her";
        }

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            prefEditor.putString("attachName", first_name + letter_id + ".pdf");
            prefEditor.commit();
            fileName = first_name + letter_id + ".pdf";
            emailid = email;
            File file = new File(dir, first_name + letter_id + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter docWriter = PdfWriter.getInstance(doc, fOut);

            lettersUri.add(Uri.fromFile(file));
            //open the document
            doc.open();
            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getAssets().open("logo_afcks.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(400, 700);
            companyLogo.scalePercent(70);
            //String value_note="*** Note This is Electronic Receipt ";
            String value_note = "AFCKS TECHNOLOGIES";
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            f1.setStyle(Font.UNDERLINE);
            f1.setColor(BaseColor.BLACK);
            Font f2 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2.setColor(BaseColor.RED);
            selector.addFont(f1);
            selector.addFont(f2);
            Phrase ph = selector.process(value_note);
            Paragraph note = new Paragraph(ph);
            note.setAlignment(Paragraph.ALIGN_LEFT | Paragraph.ALIGN_TOP);


            String value_address = "G 25,2nd Floor, Shantikunj," + System.getProperty("line.separator") + "SadhuVasvani Road Pune -1" + System.getProperty("line.separator") + "Phone: 9762118718" + System.getProperty("line.separator") + "Email: mohammed.raza@afcks.com" + System.getProperty("line.separator");
            FontSelector selector_address = new FontSelector();
            Font f1_address = FontFactory.getFont(FontFactory.TIMES, 16);
            f1_address.setColor(BaseColor.BLACK);
            Font f2_address = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_address.setColor(BaseColor.RED);
            selector_address.addFont(f1_address);
            selector_address.addFont(f2_address);
            Phrase ph_address = selector_address.process(value_address);
            Paragraph note_address = new Paragraph(ph_address);
            note_address.setAlignment(Paragraph.ALIGN_LEFT | Paragraph.ALIGN_TOP);
            note_address.setLeading(25, 0);


            String value_date = "\n\nDate: " + date_after;
            FontSelector selector_date = new FontSelector();
            Font f1_date = FontFactory.getFont(FontFactory.TIMES, 19);
            f1_date.setColor(BaseColor.BLACK);
            Font f2_date = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_date.setColor(BaseColor.RED);
            selector_date.addFont(f1_date);
            selector_date.addFont(f2_date);
            Phrase ph_date = selector_date.process(value_date);
            Paragraph note_date = new Paragraph(ph_date);
            note_date.setAlignment(Paragraph.ALIGN_LEFT | Paragraph.ALIGN_TOP);

            String value_refno = "\nRef No: " + letter_id;
            FontSelector selector_refno = new FontSelector();
            Font f1_refno = FontFactory.getFont(FontFactory.TIMES, 19);
            f1_refno.setColor(BaseColor.BLACK);
            Font f2_refno = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_refno.setColor(BaseColor.RED);
            selector_refno.addFont(f1_refno);
            selector_refno.addFont(f2_refno);
            Phrase ph_refno = selector_date.process(value_refno);
            Paragraph note_refno = new Paragraph(ph_refno);
            note_refno.setAlignment(Paragraph.ALIGN_LEFT | Paragraph.ALIGN_TOP);

            String value_title = "\n\nTO WHOM SO EVER IT MAY CONCERN";
            FontSelector selector_title = new FontSelector();
            Font f1_title = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            f1_title.setStyle(Font.UNDERLINE);

            f1_title.setColor(BaseColor.BLACK);
            Font f2_title = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_title.setColor(BaseColor.RED);
            selector_title.addFont(f1_title);
            selector_title.addFont(f2_title);
            Phrase ph_title = selector_title.process(value_title);
            Paragraph note_title = new Paragraph(ph_title);
            note_title.setAlignment(Paragraph.ALIGN_CENTER);

            String value_title1 = "\nSUB: LETTER OF COMPLETION";
            FontSelector selector_title1 = new FontSelector();
            Font f1_title1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 20);
            f1_title1.setStyle(Font.UNDERLINE);
            f1_title1.setColor(BaseColor.BLACK);
            Font f2_title1 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_title1.setColor(BaseColor.RED);
            selector_title1.addFont(f1_title1);
            selector_title1.addFont(f2_title1);
            Phrase ph_title1 = selector_title1.process(value_title1);
            Paragraph note_title1 = new Paragraph(ph_title1);
            note_title1.setAlignment(Paragraph.ALIGN_CENTER);

            String value_dec = "\nWe are pleased to acknowledge that " + gender + student_name +
                    " is a student of AFCKS Technologies of Batch " + batch_id + ", who has succesfully completed " + course_name +
                    " having duration " + duration + " between " + startMonthName + " to " + endMonthName + " from our institute.";
            FontSelector selector_dec = new FontSelector();
            Font f1_dec = FontFactory.getFont(FontFactory.TIMES, 19);
            f1_dec.setColor(BaseColor.BLACK);

            selector_dec.addFont(f1_dec);
            Phrase ph_dec = selector_dec.process(value_dec);
            Paragraph note_dec = new Paragraph(ph_dec);
            note_dec.setAlignment(Paragraph.ALIGN_LEFT);
            note_dec.setLeading(30, 0);

            String value_wish = "\n\n\nWe wish " + nameF + " great success for " + nameL + " future endeavors.";
            FontSelector selector_wish = new FontSelector();
            Font f1_wish = FontFactory.getFont(FontFactory.TIMES, 20);
            f1_wish.setColor(BaseColor.BLACK);
            Font f2_wish = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_wish.setColor(BaseColor.RED);
            selector_wish.addFont(f1_wish);
            selector_wish.addFont(f2_wish);
            Phrase ph_wish = selector_wish.process(value_wish);
            Paragraph note_wish = new Paragraph(ph_wish);
            note_wish.setAlignment(Paragraph.ALIGN_LEFT);

            String value_bestregards = "\nBest Regards";
            FontSelector selector_bestregards = new FontSelector();
            Font f1_bestregards = FontFactory.getFont(FontFactory.TIMES, 20);
            f1_bestregards.setColor(BaseColor.BLACK);
            Font f2_bestregards = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_bestregards.setColor(BaseColor.RED);
            selector_bestregards.addFont(f1_bestregards);
            selector_bestregards.addFont(f2_bestregards);
            Phrase ph_bestregards = selector_bestregards.process(value_bestregards);
            Paragraph note_bestregards = new Paragraph(ph_bestregards);
            note_bestregards.setAlignment(Paragraph.ALIGN_LEFT);

            String value_forafcks = "\n\nfor AFCKS Technologies";
            FontSelector selector_forafcks = new FontSelector();
            Font f1_forafcks = FontFactory.getFont(FontFactory.TIMES, 20);
            f1_bestregards.setColor(BaseColor.BLACK);
            Font f2_forafcks = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_forafcks.setColor(BaseColor.RED);
            selector_forafcks.addFont(f1_forafcks);
            selector_forafcks.addFont(f2_forafcks);
            Phrase ph_forafcks = selector_bestregards.process(value_forafcks);
            Paragraph note_forafcks = new Paragraph(ph_forafcks);
            note_forafcks.setAlignment(Paragraph.ALIGN_LEFT);

            String value_proprietor = "\n\n\n\n\nProprietor ";
            FontSelector selector_proprietor = new FontSelector();
            Font f1_proprietor = FontFactory.getFont(FontFactory.TIMES, 20);
            f1_bestregards.setColor(BaseColor.BLACK);
            Font f2_proprietor = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_proprietor.setColor(BaseColor.RED);
            selector_proprietor.addFont(f1_proprietor);
            selector_proprietor.addFont(f2_proprietor);
            Phrase ph_proprietor = selector_bestregards.process(value_proprietor);
            Paragraph note_proprietor = new Paragraph(ph_proprietor);
            note_proprietor.setAlignment(Paragraph.ALIGN_LEFT);


            InputStream inputStreamSign = getAssets().open("sign.png");
            Bitmap bmpSign = BitmapFactory.decodeStream(inputStreamSign);
            ByteArrayOutputStream streamSign = new ByteArrayOutputStream();
            bmpSign.compress(Bitmap.CompressFormat.PNG, 100, streamSign);
            Image companySign = Image.getInstance(streamSign.toByteArray());
            companySign.setAbsolutePosition(10, 142);
            companySign.scalePercent(50);


            String value_note2 = "\n\n***Note this is authicated Electronic Letter of completion therefore no stamp needed ";
            Paragraph note2 = new Paragraph(value_note2);
            Font noteparaFont2 = new Font(Font.FontFamily.COURIER);
            note2.setAlignment(Paragraph.ALIGN_BOTTOM);
            note2.setFont(noteparaFont2);

            Font font = FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE);
// Blue
            font.setColor(0, 0, 255);
// We need a Chunk in order to have a font's style
            Chunk chunk = new Chunk("www.afckstechnologies.in/verify", font);
            Anchor anchor = new Anchor(chunk);
            anchor.setReference("www.afckstechnologies.in/verify");
            String value_link = "" + anchor;
            FontSelector selector_link = new FontSelector();
            Font f1_link = FontFactory.getFont(FontFactory.TIMES, 12);
            f1_link.setStyle(Font.UNDERLINE);
            f1_link.setColor(BaseColor.BLACK);
            selector_link.addFont(f1_link);
            Phrase ph_link = selector_link.process(value_link);


            Paragraph _verify = new Paragraph();
            _verify.add("To Verify kindly visit ");
            _verify.add(anchor);
            _verify.add(" and enter Ref No. " + letter_id);
            Font _verifyparaFont2 = new Font(Font.FontFamily.COURIER);
            _verify.setAlignment(Paragraph.ALIGN_BOTTOM);
            _verify.setFont(_verifyparaFont2);


            //add paragraph to document
            doc.add(companyLogo);
            doc.add(note);
            doc.add(note_address);
            doc.add(note_date);
            doc.add(note_refno);
            doc.add(note_title);
            doc.add(note_title1);
            doc.add(note_dec);
            doc.add(note_wish);
            doc.add(note_bestregards);
            doc.add(note_forafcks);
            doc.add(companySign);
            doc.add(note_proprietor);
            doc.add(note2);
            doc.add(_verify);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        // viewPdf("newFile.pdf", "Dir");

      /*  try {
            modify();
        } catch (DocumentException e) {
            e.printStackTrace();
        }*/
        // new MyAsyncClass().execute();
        new UploadFileToServer().execute();

    }

    private void modify() throws DocumentException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir/imagetopdf.pdf";
        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir/";
        try {
            PdfReader pdfReader = new PdfReader(path);

            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(path1 + "imagetopdfm.pdf"));
            String value_date = "has successfully completed the \"One Day Training for Parctical Application of Advanced Excel\"";
            FontSelector selector_date = new FontSelector();
            Font f1_date = FontFactory.getFont(FontFactory.HELVETICA, 20);
            f1_date.setColor(BaseColor.BLACK);
            Font f2_date = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2_date.setColor(BaseColor.RED);
            selector_date.addFont(f1_date);
            selector_date.addFont(f2_date);
            Phrase ph_date = selector_date.process(value_date);
            Paragraph note_date = new Paragraph(ph_date);
            note_date.setAlignment(Paragraph.ALIGN_LEFT | Paragraph.ALIGN_JUSTIFIED);
            note_date.setLeading(25, 0);

            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {

                PdfContentByte content = pdfStamper.getOverContent(i);
                // content.addImage(companyLogo);
                PdfTemplate xobject = content.createTemplate(500, 220);
                ColumnText column = new ColumnText(xobject);
                column.setSimpleColumn(new Rectangle(500, 220));
                column.addElement(note_date);
                // column.addElement(new Paragraph("has successfully completed the \"One Day Training for Parctical Application of Advanced Excel\""));
                column.go();
                content.addTemplate(xobject, 55, 15);

                //Text over the existing page
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                content.beginText();
                content.setFontAndSize(bf, 18);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "29 August 2017", 60, 130, 0);
                content.setFontAndSize(bf, 60);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Ashok Kumawat", 50, 270, 0);
                //  content.setFontAndSize(bf, 20);
                // content.setTextMatrix(100, 150);
                //content.showTextAligned(PdfContentByte.ALIGN_LEFT,"has successfully completed the \"One Day Training for Parctical Application of Advanced Excel\"",53,210,0);
                content.setFontAndSize(bf, 18);
                content.setColorFill(BaseColor.WHITE);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Code: " + i, 455, 16, 0);
                content.setFontAndSize(bf, 23);
                content.setColorFill(BaseColor.WHITE);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "CERTIFICATE OF PARTICIPATION", 75, 380, 0);
                content.endText();


            }

            pdfStamper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(StudentList.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, mail_message, username, emailid, fileName);


            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            new UploadFileToServer().execute();
            //  new UploadAFCKSNotesFileToServer().execute();
            // Toast.makeText(getActivity(), "Email send", Toast.LENGTH_LONG).show();
        }


    }


    //end ..........

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

    String getDayOfMonthSuffix(final int n) {
        //  checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
    //swaping

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(parseColor("#E6E6DC"));
                xMark = ContextCompat.getDrawable(StudentList.this, R.drawable.ic_delete_black_24dp);
                xMark.setColorFilter(BLACK, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) StudentList.this.getResources().getDimension(R.dimen.ic_clear_margin);
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
                StudentListAdpter testAdapter = (StudentListAdpter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                StudentListAdpter adapter = (StudentListAdpter) mstudentList.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
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
        mItemTouchHelper.attachToRecyclerView(mstudentList);
    }

    private void setUpAnimationDecoratorHelper() {
        mstudentList.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(RED);
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

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(getActivity());
            // pDialog.setMessage("Please wait...");
            //  pDialog.show();
            // setting progress bar to zero
            // progressBar.setProgress(0);

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //   progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            // progressBar.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
/*            Bitmap bm;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            bm = BitmapFactory.decodeFile(imageUri.get(0).getPath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            byte[] data = bos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(data, File.separator + "IMG_" + timeStamp + ".jpg");*/

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.URL_UPLOAD_LETTER_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        //   publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(lettersUri.get(0).getPath());

                // Adding file data to http body
                entity.addPart("uploadedfile1", new FileBody(sourceFile));
                //entity.addPart("uploadedfile1", bab);
                // Extra parameters if you want to pass to server
                // entity.addPart("website", new StringBody("www.androidhive.info"));
                //entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "Response from server: " + result);
            if (statusCode == 200) {
                // pDialog.cancel();
                new updateStudentLetterStatus().execute();
            }


        }

    }

    private class updateStudentLetterStatus extends AsyncTask<Void, Void, Void> {


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
                        put("LetterID", letterid);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            studentLetterResponse = serviceAccess.SendHttpPost(Config.URL_UPDATESTUDENTLETTERGENSTATUS, jsonLeadObj);
            Log.i("resp", "studentLetterResponse" + studentLetterResponse);

            if (studentLetterResponse.compareTo("") != 0) {
                if (isJSONValid(studentLetterResponse)) {


                    try {


                        JSONObject jsonObject = new JSONObject(studentLetterResponse);
                        status = jsonObject.getBoolean("status");
                        if (status) {

                        }


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
            if (studentLetterResponse.compareTo("") != 0) {


                // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                // mProgressDialog.dismiss();
            }
        }
    }

    private class UploadAFCKSNotesFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(getActivity());
            // pDialog.setMessage("Please wait...");
            //  pDialog.show();
            // setting progress bar to zero
            // progressBar.setProgress(0);

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //   progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            // progressBar.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.URL_UPLOAD_AFCKSNOTES_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        //   publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(lettersUri.get(0).getPath());

                // Adding file data to http body
                entity.addPart("user", new StringBody("YDAE013"));
                entity.addPart("AE", new FileBody(sourceFile));
                //entity.addPart("uploadedfile1", bab);
                // Extra parameters if you want to pass to server
                // entity.addPart("website", new StringBody("www.androidhive.info"));
                //entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "Response from server: " + result);
            if (statusCode == 200) {
                // pDialog.cancel();
            }


        }

    }

    //taking studenyts attendance

    private class submitAttendanceData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(StudentList.this);
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            final String date = format.format(cal.getTime());

            if (attendencePresent == "P") {
                pa = 1;
            }
            if (attendencePresent == "A") {
                pa = 0;
            }
            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("batch_id", attendenceBatchId);
                        put("user_id", attendenceStudentId);
                        put("batch_date", preferences.getString("check_mark_attendance_date", ""));
                        put("present", pa);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            attendanceListResponse = serviceAccess.SendHttpPost(Config.URL_TAKEATTENDENCEBYBTACH, jsonLeadObj);
            Log.i("resp", "attendanceListResponse" + attendanceListResponse);


            if (attendanceListResponse.compareTo("") != 0) {
                if (isJSONValid(attendanceListResponse)) {


                    try {

                        JSONObject jsonObject = new JSONObject(attendanceListResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


                    // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                //  Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (status) {
                Log.d("size of list", temp + "=" + temp_size);
                temp++;
                if (temp == temp_size) {
                    Toast.makeText(getApplicationContext(), " Attendance mark successfully", Toast.LENGTH_SHORT).show();
                    //  mProgressDialog.dismiss();
                    chkAll.setChecked(false);
                    new getStudentList().execute();


                    // status=false;
                }
               /* if (temp == temp_size) {
                    autoCompleteTextViewBatch.setText("");
                    chkAll.setChecked(false);
                    clear_batch.setVisibility(View.GONE);
                    footer.setVisibility(View.GONE);

                    if (studentListResponse.compareTo("") != 0) {
                        data.clear(); // this list which you hava passed in Adapter for your listview
                        studentListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                    }
                }*/
            } else {
                temp++;
                if (temp == temp_size) {
                    Toast.makeText(getApplicationContext(), "Already mark attendance", Toast.LENGTH_SHORT).show();
                    //  mProgressDialog.dismiss();
                    chkAll.setChecked(false);
                    new getStudentList().execute();
                }

                // Close the progressdialog


            }
        }
    }
}
