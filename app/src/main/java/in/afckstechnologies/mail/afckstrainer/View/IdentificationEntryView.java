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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


/**
 * Created by Ashok on 4/29/2017.
 */

public class IdentificationEntryView extends DialogFragment {

    Button placeBtn;
    private EditText identification1EdtTxt;
    private EditText identification2EdtTxt;
    private EditText identification3EdtTxt;
    private EditText quantityEdtTxt;
    TextView itemTxt, locationTxt, statusTxt;
    private EditText descEdtTxt;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String identificationResponse = "";
    JSONObject jsonObj;
    Boolean status;
    String message;
    String user_id;
    boolean click = true;
    int count = 0;
    View registerView;


    String identification1 = "";
    String identification2 = "";
    String identification3 = "";
    String quantity = "";
    private Spinner spnrUserType;

    String desc = "";
    String type = "";
    public static SmsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_identification_entry, null);

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

        identification1EdtTxt = (EditText) registerView.findViewById(R.id.identification1EdtTxt);
        identification2EdtTxt = (EditText) registerView.findViewById(R.id.identification2EdtTxt);
        identification3EdtTxt = (EditText) registerView.findViewById(R.id.identification3EdtTxt);
        quantityEdtTxt = (EditText) registerView.findViewById(R.id.quantityEdtTxt);

        itemTxt = (TextView) registerView.findViewById(R.id.itemTxt);
        locationTxt = (TextView) registerView.findViewById(R.id.locationTxt);
        statusTxt = (TextView) registerView.findViewById(R.id.statusTxt);

        itemTxt.setText(preferences.getString("itemName", ""));
        locationTxt.setText(preferences.getString("locationName", ""));
        statusTxt.setText(preferences.getString("statusName", ""));
        descEdtTxt = (EditText) registerView.findViewById(R.id.descEdtTxt);
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);

        //

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        dataAdapter.add("Choose Remarks");
        dataAdapter.add("Purchase");
        dataAdapter.add("Add");
        dataAdapter.add("Sale");
        dataAdapter.add("Scrap");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spnrUserType = (Spinner) registerView.findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);

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

                identification1 = identification1EdtTxt.getText().toString();
                identification2 = identification2EdtTxt.getText().toString();
                identification3 = identification3EdtTxt.getText().toString();
                quantity = quantityEdtTxt.getText().toString();
                desc = descEdtTxt.getText().toString();
                type = spnrUserType.getSelectedItem().toString();
                if (validate(identification1, quantity, type)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData(identification1, identification2, identification3);
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


    public boolean validate(String identification1, String quantity, String type) {
        boolean isValidate = false;
        if (identification1.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter identification name .", Toast.LENGTH_LONG).show();
        } else if (quantity.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter quantity.", Toast.LENGTH_LONG).show();
        } else if (type.equals("Choose Remarks")) {
            Toast.makeText(getActivity(), "Please choose remarks.", Toast.LENGTH_LONG).show();
        } else {
            isValidate = true;
        }

        return isValidate;
    }


    public void sendData(final String indentification1, final String indentification2, final String indentification3) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        //final String token = SharedPrefManager.getInstance(getActivity()).getDeviceToken();
        jsonObj = new JSONObject() {
            {
                try {
                    put("ItemNameID", preferences.getString("itemNameId", ""));
                    put("Identification", indentification1);
                    put("Identification2", indentification2);
                    put("Identification3", indentification3);
                    put("LocationID", preferences.getString("locationNameID", ""));
                    put("Qty", quantity);
                    put("StatusID", preferences.getString("statusNameID", ""));
                    put("UserID", preferences.getString("trainer_user_id", ""));
                    put("Remark", type);
                    put("ItemDesc", desc);

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
                identificationResponse = serviceAccess.SendHttpPost(Config.URL_ADDIDENTIFICATIONDETAILS, jsonObj);
                Log.i("identificationResponse", "identificationResponse" + identificationResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (identificationResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {
                                    if (isJSONValid(identificationResponse)) {
                                        try {

                                            JSONObject jObject = new JSONObject(identificationResponse);
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
                                    } else {
                                        Toast.makeText(getActivity(), "This Identification already exist!", Toast.LENGTH_LONG).show();
                                        click = true;
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