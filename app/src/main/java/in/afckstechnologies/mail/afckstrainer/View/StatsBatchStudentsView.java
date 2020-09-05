package in.afckstechnologies.mail.afckstrainer.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

/**
 * Created by Ashok Kumawat on 11/29/2017.
 */

public class StatsBatchStudentsView extends DialogFragment {


    Context context;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;


    String studentResponse = "";


    private JSONObject jsonSchedule;
    ProgressDialog mProgressDialog;

    JSONArray jsonArray;


    boolean status;
    String message = "";
    String msg = "";


    private TextView totalBatches, totalTime, studentsInBatch, activeStudents, discontinuedStudents, activePerc, presentPerc, totalFees;

    static SmsListener mListener;
    ImageView clearDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View registerView = inflater.inflate(R.layout.dialog_statsbatchstudents_batch_modify, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);


        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();


        totalBatches = (TextView) registerView.findViewById(R.id.totalBatches);
        totalTime = (TextView) registerView.findViewById(R.id.totalTime);
        studentsInBatch = (TextView) registerView.findViewById(R.id.studentsInBatch);
        activeStudents = (TextView) registerView.findViewById(R.id.activeStudents);
        discontinuedStudents = (TextView) registerView.findViewById(R.id.discontinuedStudents);
        activePerc = (TextView) registerView.findViewById(R.id.activePerc);
        presentPerc = (TextView) registerView.findViewById(R.id.presentPerc);
        totalFees = (TextView) registerView.findViewById(R.id.totalFees);
        getBatchSelect();

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Hide your keyboard here!!!
                    // Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });


        return registerView;
    }

    public void getBatchSelect() {

        jsonSchedule = new JSONObject() {
            {
                try {

                    put("batch_id", preferences.getString("batch_info", ""));
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

                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETSTATSBATCHSTUDENTS, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray callArrayList = new JSONArray(studentResponse);


                            for (int i = 0; i < callArrayList.length(); i++) {
                                JSONObject json_data = callArrayList.getJSONObject(i);
                                totalBatches.setText("Total Batches : "+json_data.getString("TotalBatches"));
                                totalTime.setText("Total Time : "+json_data.getString("TotalTime"));
                                studentsInBatch.setText("Students In Batch : "+json_data.getString("StudentsInBatch"));
                                activeStudents.setText("Active Students : "+json_data.getString("ActiveStudents"));
                                discontinuedStudents.setText("Discontinued Students : "+json_data.getString("DiscontinuedStudents"));
                                activePerc.setText("Active Percentage : "+json_data.getString("ActivePerc"));
                                presentPerc.setText("Present Percentage : "+json_data.getString("PresentPerc"));
                                if(!json_data.getString("TotalFees").equals("null")) {
                                    totalFees.setVisibility(View.VISIBLE);
                                    totalFees.setText("Total Fees : " + json_data.getString("TotalFees"));
                                }
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });


            }
        });

        objectThread.start();

    }


    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        update();

                        // Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        //Hide your keyboard here!!!!!!
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }

    private void update() {


        dismiss();
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
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