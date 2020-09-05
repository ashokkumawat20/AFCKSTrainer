package in.afckstechnologies.mail.afckstrainer.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import in.afckstechnologies.mail.afckstrainer.Activity.StudentList;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.CompanyDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


public class StudentCorporateRegView extends DialogFragment {

    Button placeBtn;
    private TextView nameEdtTxt;
    Context context;
    SharedPreferences preferences;
    Editor prefEditor;
    DatePickerDialog dp = null;

    Boolean status;
    int user_id;
    boolean click = true;
    int count = 0;

    //8-3-2017
    RadioGroup radioGroup;
    private RadioButton radioButton;
    View registerView;
    int pos;
    int pos1;

    ImageView add_comapny;

    ImageView readContact;
    final static int PICK_CONTACT = 0;
    ProgressDialog mProgressDialog;

    JSONObject jsonCenterObj, jsonobject, jsonLeadObj, jsonCompany;
    JSONArray jsonarray;
    String centerResponse = "";
    String phoneNumber = "", emailAddress = "";
    public static SmsListener mListener;

    AutoCompleteTextView SearchCompany;
    String newTextCompany = "";
    String companyResponse = "", centerListResponse = "";

    public List<CompanyDAO> companyArrayList;
    public ArrayAdapter<CompanyDAO> aAdapter;
    CompanyDAO companyDAO;
    String company_id = "", message = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_student_corporate_reg, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);


        add_comapny = (ImageView) registerView.findViewById(R.id.add_comapny);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();
        companyArrayList = new ArrayList<CompanyDAO>();
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        SearchCompany = (AutoCompleteTextView) registerView.findViewById(R.id.SearchCompany);
        SearchCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextCompany = s.toString();
                getCompanySelect(newTextCompany);


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//8-3-2017
        add_comapny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                CompanyDetailsEntryView companyDetailsEntryView = new CompanyDetailsEntryView();
                companyDetailsEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "companyDetailsEntryView");


            }
        });


        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new OnKeyListener() {

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

        placeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (AppStatus.getInstance(context).isOnline()) {
                    // dismiss();
                    if (!company_id.equals("")) {
                        //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                        new updateCorporate().execute();

                    } else {
                        Toast.makeText(context, "Please select company name", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }


            }
        });
        return registerView;
    }
    //


    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        //Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 1) {
                            // update();
                            company_id="";

                            dismiss();
                        }
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

    public void getCompanySelect(final String channelPartnerSelect) {

        jsonCompany = new JSONObject() {
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

                Log.i("json", "json" + jsonCompany);
                //SEND RESPONSE
                companyResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCOMPANY, jsonCompany);
                Log.i("resp", "loginResponse" + companyResponse);


                try {
                    JSONArray callArrayList = new JSONArray(companyResponse);
                    companyArrayList.clear();

                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        companyDAO = new CompanyDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        companyArrayList.add(new CompanyDAO(json_data.getString("id"), json_data.getString("company_name"), json_data.getString("state_id"), json_data.getString("gst_no"), json_data.getString("address"), json_data.getString("state_name"), json_data.getString("city_name")));
                    }
                } catch (
                        JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<CompanyDAO>(getActivity(), R.layout.item, companyArrayList);
                        SearchCompany.setAdapter(aAdapter);

                        SearchCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                CompanyDAO student = (CompanyDAO) parent.getAdapter().getItem(i);

                                company_id = student.getId();
                                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
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

    private class updateCorporate extends AsyncTask<Void, Void, Void> {
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
                        put("student_batch_cat", preferences.getString("corporate_type", ""));
                        put("company_id", company_id);
                        put("id", preferences.getString("id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATECORPORATESTUDENT, jsonLeadObj);
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
                // Close the progressdialog
                mListener.messageReceived(message);
                dismiss();
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