package in.afckstechnologies.mail.afckstrainer.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


/**
 * Created by Ashok on 4/29/2017.
 */

public class AccessoryBrandNameEntryView extends DialogFragment {

    Button placeBtn;
    private EditText brandNameEdtTxt;

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String brandNameResponse = "";
    JSONObject jsonObj;
    Boolean status;
    String message;
    String user_id;
    boolean click = true;
    int count = 0;
    View registerView;



    String brandName = "";

    public static SmsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_accessory_brandname_entry, null);

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


        brandNameEdtTxt = (EditText) registerView.findViewById(R.id.brandNameEdtTxt);

        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);

        //
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

                brandName= brandNameEdtTxt.getText().toString();


                if (validate(brandName)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData();
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


    public boolean validate( String brandName) {
        boolean isValidate = false;
        if (brandName.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please Enter Brand Name .", Toast.LENGTH_LONG).show();
        }

        else {
            isValidate = true;
        }

        return isValidate;
    }


    public void sendData() {

        jsonObj = new JSONObject() {
            {
                try {

                    put("brand_name",brandName);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();

                Log.i("loginResponse", "jsonObj" + jsonObj);
                 brandNameResponse = serviceAccess.SendHttpPost(Config.URL_ADDABRANDNAME, jsonObj);
                 Log.i("brandNameResponse",brandNameResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (brandNameResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(brandNameResponse);
                                        status = jObject.getBoolean("status");
                                        message = jObject.getString("message");


                                        if (status) {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                            update();
                                            dismiss();

                                        } else {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        // Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 1) {
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

    private void update() {

        mListener.messageReceived(message);

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

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}