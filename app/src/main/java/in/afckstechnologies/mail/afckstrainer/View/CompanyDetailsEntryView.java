package in.afckstechnologies.mail.afckstrainer.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Activity.StudentList;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CityDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StateDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


public class CompanyDetailsEntryView extends DialogFragment {

    Button placeBtn;
    private TextView companyNameEdtTxt;
    private EditText gstNoEdtTxt;
    private EditText addressEdtTxt;

    AutoCompleteTextView SearchState, SearchCity;


    Context context;
    SharedPreferences preferences;
    Editor prefEditor;

    String loginResponse = "";
    String stateResponse = "";
    String cityResponse = "";
    JSONObject jsonObj, jsonState, jsonCity;

    Boolean status;
    String msg = "";

    boolean click = true;
    int count = 0;

    View registerView;
    String ReceivedBy = "";
    private static FeesListener mListener;
    String state_id = "";
    String city_id = "";
    String newTextState = "", newTextCity = "";
    public List<StateDAO> stateArrayList;
    public ArrayAdapter<StateDAO> aAdapter;
    StateDAO stateDAO;

    public List<CityDAO> cityArrayList;
    public ArrayAdapter<CityDAO> cityAdapter;
    CityDAO cityDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_company_details, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.TOP | Gravity.TOP);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Add your mail Id and Password
        stateArrayList = new ArrayList<StateDAO>();
        cityArrayList = new ArrayList<CityDAO>();
        companyNameEdtTxt = (TextView) registerView.findViewById(R.id.companyNameEdtTxt);
        gstNoEdtTxt = (EditText) registerView.findViewById(R.id.gstNoEdtTxt);
        addressEdtTxt = (EditText) registerView.findViewById(R.id.addressEdtTxt);
        SearchState = (AutoCompleteTextView) registerView.findViewById(R.id.SearchState);
        SearchCity = (AutoCompleteTextView) registerView.findViewById(R.id.SearchCity);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();

        SearchState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextState = s.toString();
                getStateSelect(newTextState);
                if (newTextState.equals("")) {
                    state_id = "";
                    city_id = "";
                    SearchCity.setText("");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        SearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextCity = s.toString();
                getCitySelect(newTextCity);
                if (newTextCity.equals("")) {
                    city_id = "";
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);

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

                String c_name = companyNameEdtTxt.getText().toString();
                String gst_no = gstNoEdtTxt.getText().toString();
                String address = addressEdtTxt.getText().toString();


                if (validate(c_name, gst_no, state_id, city_id, address)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData(c_name, gst_no, state_id, city_id, address);
                            click = false;
                        } else {
                            Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
        });
        return registerView;
    }


    public boolean validate(String c_name, String gst_no, String state_id, String city_id, String address) {
        boolean isValidate = false;
        if (c_name.equals("")) {
            Toast.makeText(getActivity(), "Please Company Name", Toast.LENGTH_LONG).show();
        } else if (gst_no.equals("")) {
            Toast.makeText(getActivity(), "Please GST No.", Toast.LENGTH_LONG).show();
        } else if (state_id.equals("")) {
            Toast.makeText(getActivity(), "Please Select State", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (city_id.equals("")) {
            Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (address.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Address", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }


    public void sendData(final String c_name, final String gst_no, final String state_id, final String city_id, final String address) {

        jsonObj = new JSONObject() {
            {
                try {
                    put("company_name", c_name);
                    put("gst_no", gst_no);
                    put("state_id", state_id);
                    put("city_id", city_id);
                    put("address", address);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                Log.i("jsonObj", "jsonObj" + jsonObj);
                loginResponse = serviceAccess.SendHttpPost(Config.URL_ADD_COMPANY_DETAILS, jsonObj);
                Log.i("loginResponse", "loginResponse" + loginResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (loginResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(loginResponse);
                                        status = jObject.getBoolean("status");
                                        msg = jObject.getString("message");

                                        if (status) {
                                            dismiss();
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


    public static void bindListener(FeesListener listener) {
        mListener = listener;
    }

    public void getStateSelect(final String channelPartnerSelect) {

        jsonState = new JSONObject() {
            {
                try {
                    put("Prefixtext", channelPartnerSelect);
                    put("country_id", "105");

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

                Log.i("json", "json" + jsonState);
                //SEND RESPONSE
                stateResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTATES, jsonState);
                Log.i("resp", "loginResponse" + stateResponse);


                try {
                    JSONArray callArrayList = new JSONArray(stateResponse);
                    stateArrayList.clear();

                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        stateDAO = new StateDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        stateArrayList.add(new StateDAO(json_data.getString("state_id"), json_data.getString("state_name"), json_data.getString("country_id")));
                    }
                } catch (
                        JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<StateDAO>(getActivity(), R.layout.item, stateArrayList);
                        SearchState.setAdapter(aAdapter);

                        SearchState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                StateDAO student = (StateDAO) parent.getAdapter().getItem(i);

                                state_id = student.getState_id();

                                getCitySelect("$");

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

    public void getCitySelect(final String channelPartnerSelect) {

        jsonCity = new JSONObject() {
            {
                try {
                    put("Prefixtext", channelPartnerSelect);
                    put("state_id", state_id);

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

                Log.i("json", "json" + jsonCity);
                //SEND RESPONSE
                cityResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCITY, jsonCity);
                Log.i("resp", "loginResponse" + cityResponse);


                try {
                    JSONArray callArrayList = new JSONArray(cityResponse);
                    cityArrayList.clear();
                    for (int i = 0; i < callArrayList.length(); i++) {
                        cityDAO = new CityDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        cityArrayList.add(new CityDAO(json_data.getString("city_id"), json_data.getString("city_name"), json_data.getString("state_id")));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        cityAdapter = new ArrayAdapter<CityDAO>(getActivity(), R.layout.item, cityArrayList);
                        SearchCity.setAdapter(cityAdapter);
                        SearchCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                CityDAO student = (CityDAO) parent.getAdapter().getItem(i);
                                city_id = student.getCity_id();
                                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            }
                        });
                        cityAdapter.notifyDataSetChanged();

                    }
                });


            }
        });

        objectThread.start();

    }

}