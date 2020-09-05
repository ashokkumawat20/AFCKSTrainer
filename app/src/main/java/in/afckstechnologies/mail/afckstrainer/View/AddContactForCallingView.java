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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

/**
 * Created by Ashok Kumawat on 11/29/2017.
 */

public class AddContactForCallingView extends DialogFragment {

    Button placeBtn, zeroFeesBtn,updateBtn;
    private EditText notesEdtTxt;

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";


    private EditText dpickerStart,dpickerEnd;
    private EditText AddEdtTxt;
    private TextView dateEdtTxt, titleTxt, header;


    private Spinner spnrUserType;
    private Calendar myCalendar;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    DatePickerDialog dp = null;
    String loginResponse = "";
    JSONObject jsonObj;
    String device_id;
    Boolean status;
    String message;
    int user_id;
    boolean click = true;

    String dateStart="",dateEnd="";
    String notes="";
    String datezero;
    String flag="";
    String id="";
    private static SmsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View registerView = inflater.inflate(R.layout.dialog_savecontact_forcalling_seat, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        cal.add(Calendar.DATE, 182);  // number of days to add
        String dt = format.format(cal.getTime());  // dt is now the new date
        notesEdtTxt = (EditText) registerView.findViewById(R.id.notesEdtTxt);
        // nameEdtTxt.setTypeface(tf);
        dpickerStart = (EditText) registerView.findViewById(R.id.datePickerCalStart);
        dpickerEnd = (EditText) registerView.findViewById(R.id.datePickerCalEnd);



        titleTxt = (TextView) registerView.findViewById(R.id.titleTxt);
        // titleTxt.setTypeface(tf);
        header = (TextView) registerView.findViewById(R.id.header);
        // header.setTypeface(tf);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();

        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        updateBtn = (Button) registerView.findViewById(R.id.updateBtn);
        flag=preferences.getString("flag","");
        if(flag.equals("add"))
        {
            dateStart = date;
            dateEnd=dt;
            dpickerStart.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date));
            updateBtn.setVisibility(View.GONE);
            placeBtn.setVisibility(View.VISIBLE);
        }
        if(flag.equals("update"))
        {
            updateBtn.setVisibility(View.VISIBLE);
            notesEdtTxt.setText(preferences.getString("notes",""));
            dpickerStart.setText(preferences.getString("startDate",""));
            dpickerEnd.setText(preferences.getString("endDate",""));
            id=preferences.getString("id","");
            placeBtn.setVisibility(View.GONE);
        }

        dpickerStart.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("d MMM yyyy", Locale.US);

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        dpickerStart.setText(dateFormatter.format(mcurrentDate.getTime()));
                        dateStart = format.format(mcurrentDate.getTime());
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(format.parse(dateStart));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 182);  // number of days to add
                        String dt = format.format(c.getTime());  // dt is now the new date
                        dateEnd=dt;
                        dpickerEnd.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", dt));

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        dpickerEnd.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("d MMM yyyy", Locale.US);

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        dpickerEnd.setText(dateFormatter.format(mcurrentDate.getTime()));
                        dateEnd = format.format(mcurrentDate.getTime());

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.add("Select Status Type");
        dataAdapter.add("Out of Coverage");
        dataAdapter.add("Ringing");
        dataAdapter.add("Switched Off");
        dataAdapter.add("No Service");
        dataAdapter.add("Busy/Call Later");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrUserType = (Spinner) registerView.findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);
        spnrUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                //   LocationDAO LeadSource = (LocationDAO) parent.getSelectedItem();
                String spinnerSelected = spnrUserType.getSelectedItem().toString();
                notes = notesEdtTxt.getText().toString();
                // Toast.makeText(getActivity(), spinnerSelected, Toast.LENGTH_SHORT).show();
                if(!spinnerSelected.equals("Select Status Type") &&!notes.equals(""))
                {
                    notesEdtTxt.append(","+spinnerSelected);
                }
                if(!spinnerSelected.equals("Select Status Type") &&notes.equals(""))
                {
                    notesEdtTxt.append(spinnerSelected);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

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
                    Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });

        placeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                notes = notesEdtTxt.getText().toString();
                String date = dpickerStart.getText().toString();


                if (validate(notes, date)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        new sendCallForUser().execute();
                    } else {

                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes = notesEdtTxt.getText().toString();
                String date = dpickerStart.getText().toString();
                String datee = dpickerEnd.getText().toString();
                if(dateStart.equals("")) {
                    dateStart=formateDateFromstring("dd-MMM-yyyy", "yyyy-MM-dd", date);
                }
                if(dateEnd.equals("")) {
                    dateEnd = formateDateFromstring("dd-MMM-yyyy", "yyyy-MM-dd", datee);
                }
                if (validate(notes, date)) {
                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        new sendCallForUpdateUser().execute();
                    } else {

                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }
            }
        });

        return registerView;
    }

    public boolean validate(String notes, String date) {
        boolean isValidate = false;
        if (notes.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter comments.", Toast.LENGTH_LONG).show();
            isValidate = false;

        }
        else if (date.trim().equals("")) {
            Toast.makeText(getActivity(), "Please select start date.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }


    //
    private class sendCallForUser extends AsyncTask<Void, Void, Void> {
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

            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar c = Calendar.getInstance();
            try {
                c.setTime(format.parse(dateStart));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, -1);  // number of days to add
            String dt = format.format(c.getTime());  // dt is now the new date
            final String   newDate=dt;
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("mobile_no", preferences.getString("sc_mobile_no", ""));
                        put("first_name", preferences.getString("sc_first_name", ""));
                        put("last_name", preferences.getString("sc_last_name", ""));
                        put("email_id", preferences.getString("sc_email_id", ""));
                        put("start_date", newDate);
                        put("end_date", dateEnd);
                        put("notes", notes);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_ADDUSERCONTACTDETAILS, jsonLeadObj);
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
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                dismiss();
                // Close the progressdialog
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
    private class sendCallForUpdateUser extends AsyncTask<Void, Void, Void> {
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
                        put("id", id);
                        put("start_date", dateStart);
                        put("end_date", dateEnd);
                        put("notes", notes);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEUSERCALLCOMMENTDETAILS, jsonLeadObj);
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
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                dismiss();
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
    public static void bindListener(SmsListener listener) {
        mListener = listener;
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
        // prefEditor.putString("user_id", "");
        // prefEditor.putString("batch_id", "");
        //   prefEditor.commit();
        dismiss();
    }

    //
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }


}