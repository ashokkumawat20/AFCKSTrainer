package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.Activity.Activity_Location_Details;
import in.afckstechnologies.mail.afckstrainer.Activity.Activity_User_Profile;
import in.afckstechnologies.mail.afckstrainer.Activity.SMSSendingActivity;
import in.afckstechnologies.mail.afckstrainer.Fragments.TabsFragmentActivity;
import in.afckstechnologies.mail.afckstrainer.ImageUtils.ImageLoader;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.BuzzViewDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.AttendanceDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.FeesDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.StudentCorporateRegView;
import in.afckstechnologies.mail.afckstrainer.View.StudentDiscontinueDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.StudentDiscontinueEntryView;
import in.afckstechnologies.mail.afckstrainer.View.StudentFeesEntryView;
import in.afckstechnologies.mail.afckstrainer.View.StudentTransferFeesEntryView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by admin on 3/18/2017.
 */

public class BuzzListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<BuzzViewDAO> data;
    BuzzViewDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    String task_id = "";
    int number = 1;
    int posr;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "", corporate_type = "", addStudentRespone = "";

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray, jsonarray;
    String centerListResponse = "", feesstatusResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    int flag = 1;

    String attendenceStudentId = "";
    String attendenceBatchId = "";
    String attendencePresent = "";
    static FeesListener mListener;

    String user_id = "";
    String batch_id = "";
    String fees_status = "";
    boolean f_status;
    String discontinue_reason = "";
    String UserID = "";
    String BatchID = "";
    String Remarks = "";
    String buzz_id = "";
    String edate = "";
    String etime = "";

    String rs = "", rb = "";
    PhotoViewAttacher photoViewAttacher;
    ImageLoader imageLoader;

    // create constructor to innitilize context and data sent from MainActivity
    public BuzzListAdpter(Context context, List<BuzzViewDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        imageLoader = new ImageLoader(context);
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_buzz_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.name.setText(current.getCreate_user_name());
        myHolder.name.setTag(position);
        myHolder.buzzCount.setText("Buzz count => "+current.getBuzz_count());
        myHolder.buzzCount.setTag(position);

        myHolder.buzzSnooze.setTag(position);
        myHolder.buzzShare.setTag(position);
        myHolder.remarksEdtTxt.setTag(position);
        myHolder.expectedDate.setTag(position);
        myHolder.expectedTime.setTag(position);
        myHolder.notstart.setTag(position);
        myHolder.pendding.setTag(position);
        myHolder.complete.setTag(position);
        myHolder.seqNol.setTag(position);
        myHolder.seqNom.setTag(position);
        myHolder.seqNoh.setTag(position);
        myHolder.taskInfo.setTag(position);
        myHolder.withmoney.setTag(position);
        myHolder.notmoney.setTag(position);
        myHolder.imageView.setTag(position);
        rs = current.getRequest_subject();
        if (!current.getRequest_body().equals("")) {

            //  rb="<a> <font color='white'>:-</font></a>"+current.getRequest_body();

            rb = "<a>:-<font color='black'> " + current.getRequest_body() + "</font> </a>";
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String str = "<a>" + rs + "  " + rb + "</a>";
            myHolder.taskInfo.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
            myHolder.taskInfo.setTag(position);
        } else {


            //String str = "<a>"+rs+" <font color='black'> "+rb+"</font> </a>";
            String str = "<a>" + rs + "  " + rb + "</a>";
            myHolder.taskInfo.setText(Html.fromHtml(str));
            myHolder.taskInfo.setTag(position);
        }
        if (current.getTicket_priority_status().equals("1")) {
            myHolder.seqNom.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.GONE);
            myHolder.seqNol.setVisibility(View.VISIBLE);
            myHolder.seqNol.setText(current.getNumbers());

        }
        if (current.getTicket_priority_status().equals("2")) {
            myHolder.seqNol.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.GONE);
            myHolder.seqNom.setVisibility(View.VISIBLE);
            myHolder.seqNom.setText(current.getNumbers());

        }
        if (current.getTicket_priority_status().equals("3")) {
            myHolder.seqNol.setVisibility(View.GONE);
            myHolder.seqNom.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.VISIBLE);
            myHolder.seqNoh.setText(current.getNumbers());

        }
        if (current.getStatus().equals("1")) {
            myHolder.notstart.setVisibility(View.VISIBLE);
            myHolder.pendding.setVisibility(View.GONE);
            myHolder.complete.setVisibility(View.GONE);
        }
        if (current.getStatus().equals("2")) {
            myHolder.pendding.setVisibility(View.VISIBLE);
            myHolder.complete.setVisibility(View.GONE);
            myHolder.notstart.setVisibility(View.GONE);
        }
        if (current.getStatus().equals("3")) {
            myHolder.pendding.setVisibility(View.GONE);
            myHolder.complete.setVisibility(View.VISIBLE);
            myHolder.notstart.setVisibility(View.GONE);
        }

        if (current.getMoney_status().equals("0")) {
            myHolder.withmoney.setVisibility(View.GONE);
            myHolder.notmoney.setVisibility(View.VISIBLE);
        }

        if (current.getMoney_status().equals("1")) {
            myHolder.withmoney.setVisibility(View.VISIBLE);
            myHolder.notmoney.setVisibility(View.GONE);
        }

        if (!current.getPath().equals("null")) {
            myHolder.imageView.setVisibility(View.VISIBLE);
            imageLoader.DisplayImage(current.getPath(), myHolder.imageView);
            photoViewAttacher = new PhotoViewAttacher(myHolder.imageView);
            photoViewAttacher.update();
        } else {
            myHolder.imageView.setVisibility(View.GONE);
        }
        myHolder.buzzSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size());

                // Toast.makeText(context,"id"+current.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        myHolder.buzzShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Remarks = myHolder.remarksEdtTxt.getText().toString();
                if (validate(edate, etime, Remarks)) {
                    // Toast.makeText(context,Remarks,Toast.LENGTH_SHORT).show();
                    buzz_id = current.getId();
                    task_id = current.getTask_id();
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, data.size());
                    new updateShare().execute();
                }
            }
        });
        myHolder.expectedDate.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        myHolder.expectedDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        edate = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        myHolder.expectedTime.setOnClickListener(new View.OnClickListener() {
            private int mHours, mMinutes;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentTime = Calendar.getInstance();
                mHours = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                mMinutes = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        String output = String.format("%02d:%02d", hourOfDay, minute);
                        Log.d("output", output);
                        etime = output;
                        myHolder.expectedTime.setText("" + hourOfDay + ":" + minute);
                    }
                }, mHours, mMinutes, false);
                mTimePickerDialog.setTitle("Select Time");
                mTimePickerDialog.show();
            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, seqNol, seqNom, seqNoh, taskInfo,buzzCount;
        ImageView pendding, complete, imageView, whatsapp, notstart, withmoney, notmoney;
        Button buzzSnooze, buzzShare;
        EditText remarksEdtTxt, expectedDate, expectedTime;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            taskInfo = (TextView) itemView.findViewById(R.id.taskInfo);
            buzzCount= (TextView) itemView.findViewById(R.id.buzzCount);
            buzzSnooze = (Button) itemView.findViewById(R.id.buzzSnooze);
            buzzShare = (Button) itemView.findViewById(R.id.buzzShare);
            remarksEdtTxt = (EditText) itemView.findViewById(R.id.remarksEdtTxt);
            expectedDate = (EditText) itemView.findViewById(R.id.expectedDate);
            expectedTime = (EditText) itemView.findViewById(R.id.expectedTime);

            seqNol = (TextView) itemView.findViewById(R.id.seqNol);
            seqNom = (TextView) itemView.findViewById(R.id.seqNom);
            seqNoh = (TextView) itemView.findViewById(R.id.seqNoh);
            notstart = (ImageView) itemView.findViewById(R.id.notstart);
            pendding = (ImageView) itemView.findViewById(R.id.pendding);
            complete = (ImageView) itemView.findViewById(R.id.complete);

            withmoney = (ImageView) itemView.findViewById(R.id.withmoney);
            notmoney = (ImageView) itemView.findViewById(R.id.notmoney);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

    // method to access in activity after updating selection
    public List<BuzzViewDAO> getSservicelist() {
        return data;
    }

    public boolean validate(String edate, String etime, String remarks) {

        boolean isValidate = false;
        if (edate.equals("")) {
            Toast.makeText(context, "Please select expected Date.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (etime.equals("")) {
            Toast.makeText(context, "Please select expected Time.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (remarks.equals("")) {
            Toast.makeText(context, "Please enter  remarks", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    //
    private class deleteDisContinue extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("BatchID", BatchID);
                        put("UserID", UserID);
                        put("discontinue_reason", discontinue_reason);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_DISCONTINUEBATCHSTUDENT, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);
            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

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
            if (status) {
                //  removeAt(ID);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mListener.messageReceived(message);
                mProgressDialog.dismiss();


            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
    //


    private class updateShare extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("expected_date_time", edate + " " + etime);
                        put("remarks", Remarks);
                        put("id", buzz_id);
                        put("task_id", task_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEBUZZSHARE, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");

                        jsonarray = new JSONArray(centerListResponse);

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
            if (status) {
                //  removeAt(ID);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                mProgressDialog.dismiss();


            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }


    //
    private class deleteNotes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("notes_id", "0");
                        put("id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_DISCONTINUENOTESSTUDENT, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

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
            if (status) {
                //  removeAt(ID);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                mListener.messageReceived(message);

            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //
    private class addNotes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("notes_id", "1");
                        put("id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_DISCONTINUENOTESSTUDENT, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

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
            if (status) {
                //  removeAt(ID);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                mListener.messageReceived(message);


            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
    //

    private class continueStudent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("UserID", user_id);

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


                    Toast.makeText(context, "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                mListener.messageReceived(message);
                // Close the progressdialog
                mProgressDialog.dismiss();

            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
        }
    }

    public static void bindListener(FeesListener listener) {
        mListener = listener;
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
