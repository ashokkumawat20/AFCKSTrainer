package in.afckstechnologies.mail.afckstrainer.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.afckstechnologies.mail.afckstrainer.Models.LocationDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

/**
 * Created by Ashok Kumawat on 11/29/2017.
 */

public class DisplayEmployeeLeaveOptionsView extends DialogFragment {

    Button leaveForToday, leaveForFuture, closeBtn;
    private EditText commentEdtTxt;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String loginResponse = "";

    String message;
    int user_id;
    boolean click = true;

    JSONObject jsonObj, jsonObject;
    Boolean status;
    int count = 0;
    View registerView;
    private JSONObject jsonLeadObj;
    ProgressDialog mProgressDialog;
    JSONArray jsonArray;
    public static SmsListener mListener;
    String todayLeaveResponse = "";
    ArrayList<LocationDAO> locationlist;
    String centerResponse = "",msg="";
    ArrayList<String> strings;
    String center_id = "";
    Spinner spinnerCustom;
    private EditText logout;
    String logout_date = "";
    String logouttime = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View registerView = inflater.inflate(R.layout.dialog_emp_leave_options, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        commentEdtTxt = (EditText) registerView.findViewById(R.id.commentEdtTxt);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();
        closeBtn = (Button) registerView.findViewById(R.id.closeBtn);
        leaveForToday = (Button) registerView.findViewById(R.id.leaveForToday);
        leaveForFuture = (Button) registerView.findViewById(R.id.leaveForFuture);
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

        closeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                dismiss();


            }
        });
        leaveForToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ApplyEmployeeLeaveTodayView applyEmployeeLeaveTodayView = new ApplyEmployeeLeaveTodayView();
                applyEmployeeLeaveTodayView.show(getActivity().getSupportFragmentManager(), "applyEmployeeLeaveTodayView");
            }
        });
        leaveForFuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ApplyEmployeeLeaveFutureView applyEmployeeLeaveFutureView = new ApplyEmployeeLeaveFutureView();
                applyEmployeeLeaveFutureView.show(getActivity().getSupportFragmentManager(), "applyEmployeeLeaveFutureView");
            }
        });

        return registerView;
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