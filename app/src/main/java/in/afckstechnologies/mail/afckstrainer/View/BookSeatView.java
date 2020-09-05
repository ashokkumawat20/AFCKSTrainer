package in.afckstechnologies.mail.afckstrainer.View;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;
import in.afckstechnologies.mail.afckstrainer.R;
/**
 * Created by Ashok Kumawat on 11/29/2017.
 */

public class BookSeatView extends DialogFragment {

    Button placeBtn, zeroFeesBtn;
    private EditText transactionidEdtTxt;

    private EditText amountEdtTxt;
    private EditText dpicker;
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

    String date;
    String datezero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View registerView = inflater.inflate(R.layout.dialog_book_seat, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        transactionidEdtTxt = (EditText) registerView.findViewById(R.id.transactionidEdtTxt);
        // nameEdtTxt.setTypeface(tf);
        dpicker = (EditText) registerView.findViewById(R.id.datePickerCal);
        //lastnameEdtTxt.setTypeface(tf);
        amountEdtTxt = (EditText) registerView.findViewById(R.id.amountEdtTxt);
        // phEdtTxt.setTypeface(tf);


        titleTxt = (TextView) registerView.findViewById(R.id.titleTxt);
        // titleTxt.setTypeface(tf);
        header = (TextView) registerView.findViewById(R.id.header);
        // header.setTypeface(tf);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();

        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        zeroFeesBtn = (Button) registerView.findViewById(R.id.zeroFeesBtn);
        dpicker.setOnClickListener(new View.OnClickListener() {
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
                        dpicker.setText(dateFormatter.format(mcurrentDate.getTime()));
                        date = format.format(mcurrentDate.getTime());

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
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

                String transaction = transactionidEdtTxt.getText().toString();
                String date = dpicker.getText().toString();
                String amount = amountEdtTxt.getText().toString();
                datezero = "";

                if (validate(transaction, date, amount)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {

                            sendData(transaction, date, amount);
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

        zeroFeesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String transaction = "Book seat with Zero fees";
                // String date = dpicker.getText().toString();
                String amount = "0";

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                datezero = df.format(c.getTime());

                if (validate(transaction, datezero, amount)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {

                            sendData(transaction, datezero, amount);
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

    public boolean validate(String transaction, String date, String amount) {
        boolean isValidate = false;
        if (transaction.trim().compareTo("") == 0 || date.trim().compareTo("") == 0 || amount.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter all the values.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }


    public void sendData(final String transaction, final String date1, final String amount) {

        if (datezero.equals("")) {
            // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // datezero = format.format(date1);

            datezero = formateDateFromstring("dd MMM yyyy", "yyyy-MM-dd", date1);
        }

        jsonObj = new JSONObject() {
            {
                try {
                    put("user_id", preferences.getString("user_id", ""));
                    put("batch_id", preferences.getString("batch_id", ""));
                    put("trans_id", transaction);
                    put("pay_date", datezero);
                    put("amount", amount);
                    put("status", 1);
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
                loginResponse = serviceAccess.SendHttpPost(Config.URL_BOOKING_BATCH, jsonObj);

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
                                        message= jObject.getString("message");
                                        if (status) {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                            dismiss();
                                            //Intent intent = new Intent(context, BookSeatActivity.class);
                                            //startActivity(intent);
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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


}