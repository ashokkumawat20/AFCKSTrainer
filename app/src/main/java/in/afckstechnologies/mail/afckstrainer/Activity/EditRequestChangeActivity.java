package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import in.afckstechnologies.mail.afckstrainer.Adapter.GridViewAdapter;
import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeAttachListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeAttachListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeNameDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeUsersNameDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;
import in.afckstechnologies.mail.afckstrainer.Utils.TextUtils;
import in.afckstechnologies.mail.afckstrainer.Utils.Utility;
import in.afckstechnologies.mail.afckstrainer.View.StudentFeesEntryView;

import static android.graphics.BitmapFactory.decodeFile;

public class EditRequestChangeActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    Spinner usersName;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    private JSONObject jsonLeadObj, jsonLeadObj1, jsonLeadObjReq;
    String requestTypeResponse = "", usersResponse = "";
    String requestTypeId = "";
    String userNameId = "";
    JSONArray jsonArray;
    ArrayList<RequestChangeNameDAO> requestchangelist;
    ArrayList<RequestChangeUsersNameDAO> userslist;
    ImageView requestTypeAddName;
    Button sendData, chooseImage, chooseFormCameraImage, copyData;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView grid;
    GridViewAdapter adapter;
    File file;

    int count = 0;
    private ProgressDialog dialog;
    MultipartEntity entity;
    public ArrayList<String> map = new ArrayList<String>();
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    String addRequestChangeResponse = "", message = "", id = "", addRequestAttachResponse = "";
    boolean status;
    EditText subjectEdtTxt, descEdtTxt, expectedDate, expectedTime;
    String subject = "", desc = "";
    Context context;
    private static final int PICK_FROM_CAMERA = 2;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    RadioGroup radioGroup;
    private RadioButton prilow, primedium, prihigh;
    int pos;
    int pri_status_id = 1;
    TextView nameUsers;
    String request_id = "";
    HashMap<String, String> edit_LeadLOCList;
    //Keys
    Set keys_Loc;
    String user_name = "";
    String edate = "";
    String etime = "";

    private SimpleDateFormat dateFormatter;
    String displayRequestChangeAttachResponse = "";
    RequestChangeAttachListAdpter requestChangeAttachListAdpter;
    private RecyclerView mrequestchangeattachList;
    List<RequestChangeAttachListDAO> data;
    //new camera images
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri, fileUri1; // file url to store image/video

    ArrayList<Uri> imageUri = new ArrayList<Uri>();
    ArrayList<String> imageName = new ArrayList<String>();
    ArrayList<String> pathList;
    static String fileName = "";

    static File destination;
    String department_id = "";
    // LogCat tag
    private static final String TAG = EditRequestChangeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request_change);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        usersName = (Spinner) findViewById(R.id.spinnerUsers);
        subjectEdtTxt = (EditText) findViewById(R.id.subjectEdtTxt);
        descEdtTxt = (EditText) findViewById(R.id.descEdtTxt);
        sendData = (Button) findViewById(R.id.sendData);
        chooseImage = (Button) findViewById(R.id.chooseImage);
        copyData = (Button) findViewById(R.id.copyData);
        nameUsers = (TextView) findViewById(R.id.nameUsers);
        expectedDate = (EditText) findViewById(R.id.expectedDate);
        expectedTime = (EditText) findViewById(R.id.expectedTime);
        mrequestchangeattachList = (RecyclerView) findViewById(R.id.requestAttchChangeList);
        new getRequestChangeAttachList().execute();
        Intent intent = getIntent();
        // nameUsers.setText(intent.getStringExtra("Assign_to_user_id"));
        userNameId = intent.getStringExtra("Assign_to_user_id");
        subjectEdtTxt.setText(intent.getStringExtra("subject").replace("<b>", "").replace("</b>", "").replace("<u>", "").replace("</u>", "").replace("<i>", "").replace("</i>", "").replace("<br>", "\n"));
        descEdtTxt.setText(intent.getStringExtra("body").replace("<b>", "").replace("</b>", "").replace("<u>", "").replace("</u>", "").replace("<i>", "").replace("</i>", "").replace("<br>", "\n"));
        request_id = intent.getStringExtra("r_id");
        user_name = intent.getStringExtra("user_name");
        edate = intent.getStringExtra("expected_date");
        etime = intent.getStringExtra("expected_time");
        department_id = intent.getStringExtra("department_id");
        if (!edate.equals("null")) {
            if (!edate.equals("0000-00-00")) {

                expectedDate.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", edate));
            }
        }
        if (!etime.equals("null")) {
            if (!etime.equals("00:00:00")) {
                expectedTime.setText(etime);
            }
        }
        new initUsersSpinner().execute();
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);*/
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                selectImage();
            }
        });
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(),""+pri_status_id,Toast.LENGTH_SHORT).show();
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    subject = subjectEdtTxt.getText().toString();
                    desc = descEdtTxt.getText().toString();
                    if (validate(userNameId, subject)) {
                        // new ImageUploadTask().execute(count + "", "pk" + count + ".jpg");
                        new addRequestChangeDataDetails().execute();

                    } else {

                    }

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }


            }
        });
        copyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descEdtTxt.setText(subjectEdtTxt.getText().toString().trim());
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        prilow = (RadioButton) findViewById(R.id.prilow);
        primedium = (RadioButton) findViewById(R.id.primedium);
        prihigh = (RadioButton) findViewById(R.id.prihigh);
        pri_status_id = Integer.parseInt(intent.getStringExtra("priority_status"));
        if (intent.getStringExtra("priority_status").equals("1")) {
            prilow.setChecked(true);
            primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
            prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
            prilow.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        if (intent.getStringExtra("priority_status").equals("2")) {
            primedium.setChecked(true);
            prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
            prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
            primedium.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        if (intent.getStringExtra("priority_status").equals("3")) {
            prihigh.setChecked(true);

            primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
            prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
            prihigh.setBackgroundColor(Color.parseColor("#F44336"));
        }
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
                        //  Toast.makeText(getApplicationContext(), "1"+pri_status_id,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        pri_status_id = 1;
                        primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prihigh.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prilow.setBackgroundColor(Color.parseColor("#4CAF50"));
                        //  Toast.makeText(getApplicationContext(), "2"+pri_status_id, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:

                        pri_status_id = 3;
                        //  Toast.makeText(getApplicationContext(), "3"+pri_status_id, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //The default selection is RadioButton 1
                        pri_status_id = 3;
                        primedium.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prilow.setBackgroundColor(Color.parseColor("#809E9E9E"));
                        prihigh.setBackgroundColor(Color.parseColor("#F44336"));
                        // Toast.makeText(getApplicationContext(), "3"+pri_status_id,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        expectedDate.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                DatePickerDialog mDatePicker = new DatePickerDialog(EditRequestChangeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        expectedDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        edate = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        expectedTime.setOnClickListener(new View.OnClickListener() {
            private int mHours, mMinutes;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentTime = Calendar.getInstance();
                mHours = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                mMinutes = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(EditRequestChangeActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        String output = String.format("%02d:%02d", hourOfDay, minute);
                        Log.d("output", output);
                        etime = output;
                        expectedTime.setText("" + hourOfDay + ":" + minute);
                    }
                }, mHours, mMinutes, false);
                mTimePickerDialog.setTitle("Select Time");
                mTimePickerDialog.show();
            }
        });

        RequestChangeAttachListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getRequestChangeAttachList().execute();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }


    }

    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditRequestChangeActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditRequestChangeActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
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

    private void cameraIntent() {
        //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // startActivityForResult(intent, REQUEST_CAMERA);
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // start the image capture Intent
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();
                    mArrayUri.add(mImageUri);

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);

                    cursor.close();
                    Log.v("LOG_TAG", "imageEncoded" + imageEncoded);
                    Log.v("LOG_TAG", "Selected Images" + mImageUri);
                    // Create a String array for FilePathStrings
                    FilePathStrings = new String[1];
                    // Create a String array for FileNameStrings
                    FileNameStrings = new String[1];

                    File myFile = new File(mImageUri.getPath());

                    Log.d("LOG_TAG", "imageToUpload" + getPathFromUri(EditRequestChangeActivity.this, mImageUri));

                    imagesEncodedList.add(getPathFromUri(EditRequestChangeActivity.this, mImageUri));

                    // Get the path of the image file
                    // FilePathStrings[i] = myFile.getAbsolutePath();
                    // FilePathStrings[0] = getRealPathFromURI(mImageUri);
                    FilePathStrings[0] = getPathFromUri(EditRequestChangeActivity.this, mImageUri);
                    // Get the name image file
                    FileNameStrings[0] = getFileName(mImageUri);

                    // Locate the GridView in gridview_main.xml
                    grid = (GridView) findViewById(R.id.gridview);
                    // Pass String arrays to LazyAdapter Class
                    adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
                    // Set the LazyAdapter to the GridView
                    grid.setAdapter(adapter);
                    // Capture gridview item click
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            Intent i = new Intent(EditRequestChangeActivity.this, ViewImage.class);
                            // Pass String arrays FilePathStrings
                            i.putExtra("filepath", FilePathStrings);
                            // Pass String arrays FileNameStrings
                            i.putExtra("filename", FileNameStrings);
                            // Pass click position
                            i.putExtra("position", position);
                            startActivity(i);
                        }

                    });

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(getPathFromUri(EditRequestChangeActivity.this, uri));
                            Log.v("LOG_TAG", "imageEncoded" + imageEncoded);
                            cursor.close();

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        // Create a String array for FilePathStrings
                        FilePathStrings = new String[mArrayUri.size()];
                        // Create a String array for FileNameStrings
                        FileNameStrings = new String[mArrayUri.size()];
                        for (int i = 0; i < mArrayUri.size(); i++) {
                            File myFile = new File(mArrayUri.get(i).getPath());

                            Log.d("LOG_TAG", "imageToUpload" + imagesEncodedList.get(i));
                            // Get the path of the image file
                            // FilePathStrings[i] = myFile.getAbsolutePath();
                            FilePathStrings[i] = getPathFromUri(EditRequestChangeActivity.this, mArrayUri.get(i));
                            // Get the name image file
                            FileNameStrings[i] = getFileName(mArrayUri.get(i));
                        }
                        // Locate the GridView in gridview_main.xml
                        grid = (GridView) findViewById(R.id.gridview);
                        // Pass String arrays to LazyAdapter Class
                        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
                        // Set the LazyAdapter to the GridView
                        grid.setAdapter(adapter);
                        // Capture gridview item click
                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {

                                Intent i = new Intent(EditRequestChangeActivity.this, ViewImage.class);
                                // Pass String arrays FilePathStrings
                                i.putExtra("filepath", FilePathStrings);
                                // Pass String arrays FileNameStrings
                                i.putExtra("filename", FileNameStrings);
                                // Pass click position
                                i.putExtra("position", position);
                                startActivity(i);
                            }

                        });

                    }
                }
            } else if (requestCode == REQUEST_CAMERA) {
                // onCaptureImageResult(data);

                if (resultCode == RESULT_OK) {

                    // successfully captured the image
                    // launching upload activity
                    launchUploadActivity(true);


                } else if (resultCode == RESULT_CANCELED) {

                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
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

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        // Internal sdcard location
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "AFCKS");
        // Create the storage directory if it does not exist
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.d("TAG", "Oops! Failed create " + " directory");
                //return null;
            }
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File destination = new File(folder.getPath(), fileName);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("thumbnail", "json" + thumbnail);

        Uri mImageUri = data.getData();
        mArrayUri.add(Uri.fromFile(destination));
        Log.i("mImageUri", "json" + mImageUri);
        imagesEncodedList = new ArrayList<String>();
        FilePathStrings = new String[1];
        // Create a String array for FileNameStrings
        FileNameStrings = new String[1];

        imagesEncodedList.add("" + destination);// imagesEncodedList.add(destination);


        // Get the path of the image file
        // FilePathStrings[i] = myFile.getAbsolutePath();
        // FilePathStrings[0] = getRealPathFromURI(mImageUri);
        FilePathStrings[0] = "" + destination;
        // Get the name image file
        FileNameStrings[0] = fileName;

        // Locate the GridView in gridview_main.xml
        grid = (GridView) findViewById(R.id.gridview);
        // Pass String arrays to LazyAdapter Class
        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
        // Set the LazyAdapter to the GridView
        grid.setAdapter(adapter);
        // Capture gridview item click
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(EditRequestChangeActivity.this, ViewImage.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", FilePathStrings);
                // Pass String arrays FileNameStrings
                i.putExtra("filename", FileNameStrings);
                // Pass click position
                i.putExtra("position", position);
                startActivity(i);
            }

        });

        // imageView1.setImageBitmap(thumbnail);
    }


    //
    private class initUsersSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(EditRequestChangeActivity.this);
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

            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("user_id", preferences.getString("trainer_user_id", ""));
                        put("depart_id", department_id);
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

                                userslist = new ArrayList<>();
                                userslist.add(new RequestChangeUsersNameDAO("0", "Select Assign To User", "0","0"));
                                JSONArray LeadSourceJsonObj = new JSONArray(usersResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    userslist.add(new RequestChangeUsersNameDAO(json_data.getString("id"), json_data.getString("first_name"), "",""));

                                }
                                edit_LeadLOCList = new HashMap<String, String>();
                                edit_LeadLOCList.put(userNameId, user_name);
                                keys_Loc = edit_LeadLOCList.keySet();
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
            if (usersResponse.compareTo("") != 0) {
                String key_loc = "";
                String value_loc = "";
                if (!keys_Loc.isEmpty()) {
                    for (Iterator i = keys_Loc.iterator(); i.hasNext(); ) {
                        key_loc = (String) i.next();
                        value_loc = (String) edit_LeadLOCList.get(key_loc);
                        Log.d("keys ", "" + key_loc + " = " + value_loc);
                    }

                }
                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<RequestChangeUsersNameDAO> adapter = new ArrayAdapter<RequestChangeUsersNameDAO>(EditRequestChangeActivity.this, android.R.layout.simple_spinner_dropdown_item, userslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                usersName.setAdapter(adapter);
                selectSpinnerItemByValue(usersName, value_loc);
                usersName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        RequestChangeUsersNameDAO LeadSource = (RequestChangeUsersNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        userNameId = LeadSource.getId();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    private void selectSpinnerItemByValue(Spinner spinner, String value) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public boolean validate(String userNameId, String subject) {
        boolean isValidate = false;
        if (userNameId.equals("0")) {
            Toast.makeText(getApplicationContext(), "Please select assign to user .", Toast.LENGTH_LONG).show();
        } else if (subject.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter subject.", Toast.LENGTH_LONG).show();
        } else {
            isValidate = true;
        }

        return isValidate;
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

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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

    private class addRequestChangeDataDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(EditRequestChangeActivity.this);
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


            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("assign_to_user_id", userNameId);
                        put("request_subject", TextUtils.styleText(subject));
                        put("request_body", TextUtils.styleText(desc));
                        put("request_id", request_id);
                        put("expected_date", edate);
                        put("expected_time", etime);
                        put("ticket_priority_status", pri_status_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addRequestChangeResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREUSERCHANGEDETAILS, jsonLeadObj);
            Log.i("resp", "addRequestChangeResponse" + addRequestChangeResponse);


            if (addRequestChangeResponse.compareTo("") != 0) {
                if (isJSONValid(addRequestChangeResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(addRequestChangeResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        id = jObject.getString("id");
                        jsonArray = new JSONArray(addRequestChangeResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(EditRequestChangeActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(EditRequestChangeActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(EditRequestChangeActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                if (mArrayUri.size() > 0) {
                    for (int i = 0; i < imagesEncodedList.size(); i++) {
                        map.add(imagesEncodedList.get(i).toString());
                    }
                    new ImageUploadTask().execute(count + "", getFileName(mArrayUri.get(count)));
                } else {
                    finish();
                    Intent intent = new Intent(EditRequestChangeActivity.this, RequestChangeDisplayActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(EditRequestChangeActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    class ImageUploadTask extends AsyncTask<String, Void, String> {

        String sResponse = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(EditRequestChangeActivity.this, "Uploading",
                    "Please wait...", true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String url = "http://afckstechnologies.in/RequestCahngeTicketDocuments/uploadReusetChangeFiles.php";
                int i = Integer.parseInt(params[0]);
                Bitmap bitmap = decodeFile(map.get(i));
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(url);
                entity = new MultipartEntity();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();

                entity.addPart("user_id", new StringBody("199"));
                entity.addPart("club_id", new StringBody("10"));
                entity.addPart("club_image", new ByteArrayBody(data, "image/jpeg", params[1]));

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost, localContext);
                sResponse = EntityUtils.getContentCharSet(response.getEntity());

                System.out.println("sResponse : " + sResponse);
            } catch (Exception e) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Log.e(e.getClass().getName(), e.getMessage(), e);

            }

            jsonLeadObjReq = new JSONObject() {
                {
                    try {

                        put("path", getFileName(mArrayUri.get(count)));
                        put("reqest_change_id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObjReq);
            addRequestAttachResponse = serviceAccess.SendHttpPost(Config.URL_ADDREQUESTATTACHMENT, jsonLeadObjReq);
            Log.i("resp", "addRequestAttachResponse" + addRequestAttachResponse);
            return sResponse;
        }

        @Override
        protected void onPostExecute(String sResponse) {
            try {
                if (dialog.isShowing())
                    dialog.dismiss();

                if (sResponse != null) {
                    //  Toast.makeText(getApplicationContext(), sResponse + " Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                    count++;
                    if (count < map.size()) {
                        // new ImageUploadTask().execute(count + "", "hm" + count + ".jpg");

                        new ImageUploadTask().execute(count + "", getFileName(mArrayUri.get(count)));

                    } else {
                        finish();
                        Intent intent = new Intent(EditRequestChangeActivity.this, RequestChangeDisplayActivity.class);
                        startActivity(intent);
                    }

                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }

        }
    }

    private class getRequestChangeAttachList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(EditRequestChangeActivity.this);
            // Set progressdialog title
            // mProgressDialog.setTitle("Please Wait...");
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
                        put("user_id", preferences.getString("assign_to_user_id", ""));
                        put("reqest_change_id", preferences.getString("reqest_change_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            displayRequestChangeAttachResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTCHANGEATTACHBYUSERID, jsonLeadObj);
            Log.i("resp", "displayRequestChangeResponse" + displayRequestChangeAttachResponse);
            data = new ArrayList<>();
            if (displayRequestChangeAttachResponse.compareTo("") != 0) {
                if (isJSONValid(displayRequestChangeAttachResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseRequestChangeAttachList(displayRequestChangeAttachResponse);
                                jsonArray = new JSONArray(displayRequestChangeAttachResponse);

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
            if (data.size() > 0) {
                requestChangeAttachListAdpter = new RequestChangeAttachListAdpter(EditRequestChangeActivity.this, data);
                mrequestchangeattachList.setAdapter(requestChangeAttachListAdpter);
                mrequestchangeattachList.setLayoutManager(new LinearLayoutManager(EditRequestChangeActivity.this));
                requestChangeAttachListAdpter.notifyDataSetChanged();
                // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                // mProgressDialog.dismiss();
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

    //new camera images


    private void launchUploadActivity(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            mArrayUri.add(Uri.fromFile(destination));
            imagesEncodedList = new ArrayList<String>();
            FilePathStrings = new String[1];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[1];

            imagesEncodedList.add("" + destination);// imagesEncodedList.add(destination);


            // Get the path of the image file
            // FilePathStrings[i] = myFile.getAbsolutePath();
            // FilePathStrings[0] = getRealPathFromURI(mImageUri);
            FilePathStrings[0] = "" + destination;
            // Get the name image file
            FileNameStrings[0] = fileName;

            // Locate the GridView in gridview_main.xml
            grid = (GridView) findViewById(R.id.gridview);
            // Pass String arrays to LazyAdapter Class
            adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
            // Set the LazyAdapter to the GridView
            grid.setAdapter(adapter);
            // Capture gridview item click
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent i = new Intent(EditRequestChangeActivity.this, ViewImage.class);
                    // Pass String arrays FilePathStrings
                    i.putExtra("filepath", FilePathStrings);
                    // Pass String arrays FileNameStrings
                    i.putExtra("filename", FileNameStrings);
                    // Pass click position
                    i.putExtra("position", position);
                    startActivity(i);
                }

            });


        } else {

        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {

       /* Uri intentUri;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 24) {
            intentUri = Uri.parse(getOutputMediaFile(type).toString());
        }
        else{
            intentUri = Uri.fromFile(getOutputMediaFile(type));
        }
*/

        // return intentUri;
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        //  File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME);

        // Internal sdcard location
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "AFCKS");
        // Create the storage directory if it does not exist
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.d(TAG, "Oops! Failed create " + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create the storage directory if it does not exist
       /* if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "+ Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
*/
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;


        if (type == MEDIA_TYPE_IMAGE) {
            // mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            fileName = System.currentTimeMillis() + ".jpg";
            mediaFile = new File(folder.getPath() + File.separator + fileName);
            destination = new File(folder.getPath(), fileName);

        } else if (type == MEDIA_TYPE_VIDEO) {
            // mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
            mediaFile = new File(folder.getPath() + File.separator + "VID_" + timeStamp + ".mp4");

        } else {
            return null;
        }

        return mediaFile;
    }


}
