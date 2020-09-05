package in.afckstechnologies.mail.afckstrainer.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender1;
import in.afckstechnologies.mail.afckstrainer.Models.CompanyDAO;
import in.afckstechnologies.mail.afckstrainer.Models.LocationDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeUsersNameDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TechnologyNameDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AndroidMultiPartEntity;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


public class StudentFeesEntryView extends DialogFragment {
    // LogCat tag
    private static final String TAG = StudentFeesEntryView.class.getSimpleName();
    Button placeBtn;
    private TextView nameEdtTxt;
    private EditText feesEdtTxt, duefeesEdtTxt, refundEdtTxt, ChequeNoEdtTxt, BankNameEdtTxt, IssuerNameEdtTxt, RefNOEdtTxt, oldBatchNoEdtTxt, PaytmNoEdtTxt;

    private Spinner spinnerFeesMode, spinnerTechnologyMode, spinnerBranch;
    Context context;
    SharedPreferences preferences;
    Editor prefEditor;
    String loginResponse = "", cashBackAmountResponse = "", centerResponse = "";
    JSONObject jsonObj, jsonObjCash, jsonCompany;
    Boolean status;
    String msg = "";
    boolean click = true;
    int count = 0;
    View registerView;
    String ReceivedBy = "";
    String ReceivedBy_id = "";
    private static FeesListener mListener;
    private static final String username = "info@afcks.com";
    private static String password = "at!@#123";
    GMailSender1 sender;
    String emailid = "";
    // String subject = "Receipt";
    String subject = "";
    String message = "";
    String fileName = "";
    String pathName = "";
    private BaseFont bfBold;
    RadioGroup radioGroup;
    int pos;
    String accountType = "Non Corporate";
    String strName = "";
    String code = "";
    LinearLayout companyLayout, eft, cheque, paytm, refund, hideForDueFees, oldStudent, CashDeposit;
    ImageView add_comapny;
    AutoCompleteTextView SearchCompany;
    String newTextCompany = "";
    String companyResponse = "";
    public List<CompanyDAO> companyArrayList;
    public ArrayAdapter<CompanyDAO> aAdapter;
    CompanyDAO companyDAO;
    String state_id = "", company_id = "";
    Double feesAmount = 0.0;
    Double cashbackAmount = 0.0;
    TextView Totalfees;
    Double IGST = 0.0, SGST = 0.0, CGST = 0.0, Rate = 0.0, MOS = 0.0;


    String corporate_id = "";

    String comapny_name = "", company_address = "", company_state = "", company_city = "", company_gstno;

    int batch_fees;
    String expiry_date = "";
    String cashBackAmount = "";

    String casBackExpiryDate = "";


    String selectedPath1 = "NONE";
    int statusCode;

    private JSONObject jsonReqObj;
    JSONArray jsonArray, jsonarray;
    String addExpenditureResponse = "";
    private ProgressBar progressBar;
    long totalSize = 0;

    String value_image1 = "";

    ArrayList<Uri> receiptUri = new ArrayList<Uri>();

    String cashbackamount = "";
    Button availCashback;

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;

    String availCashbackResponse = "";

    static String sms_user = "";
    static String sms_pass = "";
    ArrayList<RequestChangeUsersNameDAO> userslist;
    ArrayList<TechnologyNameDAO> technologylist;
    String spinnerSelected = "", technologySelected = "";
    String reverseFlag = "";
    String dueFees = "", sendingmail = "", smsSent = "", dueFees1 = "";
    ImageView noncorporateButton, corporateButton, mos, moscorporate;
    String remarks = "";
    private static EditText datePickerPayDate, datePickerChequeDate, datePickerDateofReceipt, datePickerDepositDate, datePickerPaymentDate, datePickerNextPayDate;
    String pay_date = "", cheque_date = "", date_of_receipt = "", center_id = "", case_deposit_date = "", paytm_date = "", next_pay_date = "";
    ArrayList<LocationDAO> locationlist;
    String refundTxt = "", check_no = "", bank_name = "", issuer_name = "", ref_no = "", old_batch_no = "", paytm_no = "", next_pay_comments = "";
    int unableflag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_student_fees, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Add your mail Id and Password
        sender = new GMailSender1(username, password);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();
        eft = (LinearLayout) registerView.findViewById(R.id.eft);
        cheque = (LinearLayout) registerView.findViewById(R.id.cheque);
        paytm = (LinearLayout) registerView.findViewById(R.id.paytm);
        refund = (LinearLayout) registerView.findViewById(R.id.refund);
        hideForDueFees = (LinearLayout) registerView.findViewById(R.id.hideForDueFees);
        CashDeposit = (LinearLayout) registerView.findViewById(R.id.CashDeposit);
        oldStudent = (LinearLayout) registerView.findViewById(R.id.oldStudent);
        nameEdtTxt = (TextView) registerView.findViewById(R.id.nameEdtTxt);
        feesEdtTxt = (EditText) registerView.findViewById(R.id.feesEdtTxt);
        spinnerBranch = (Spinner) registerView.findViewById(R.id.spinnerBranch);
        companyLayout = (LinearLayout) registerView.findViewById(R.id.companyLayout);
        add_comapny = (ImageView) registerView.findViewById(R.id.add_comapny);
        availCashback = (Button) registerView.findViewById(R.id.availCashback);
        spinnerFeesMode = (Spinner) registerView.findViewById(R.id.spinnerFeesMode);
        spinnerTechnologyMode = (Spinner) registerView.findViewById(R.id.spinnerTechnologyMode);
        duefeesEdtTxt = (EditText) registerView.findViewById(R.id.duefeesEdtTxt);
        datePickerPayDate = (EditText) registerView.findViewById(R.id.datePickerPayDate);
        datePickerChequeDate = (EditText) registerView.findViewById(R.id.datePickerChequeDate);
        datePickerDateofReceipt = (EditText) registerView.findViewById(R.id.datePickerDateofReceipt);
        datePickerDepositDate = (EditText) registerView.findViewById(R.id.datePickerDepositDate);
        datePickerPaymentDate = (EditText) registerView.findViewById(R.id.datePickerPaymentDate);
        datePickerNextPayDate = (EditText) registerView.findViewById(R.id.datePickerNextPayDate);
        refundEdtTxt = (EditText) registerView.findViewById(R.id.refundEdtTxt);
        ChequeNoEdtTxt = (EditText) registerView.findViewById(R.id.ChequeNoEdtTxt);
        BankNameEdtTxt = (EditText) registerView.findViewById(R.id.BankNameEdtTxt);
        IssuerNameEdtTxt = (EditText) registerView.findViewById(R.id.IssuerNameEdtTxt);
        RefNOEdtTxt = (EditText) registerView.findViewById(R.id.RefNOEdtTxt);
        oldBatchNoEdtTxt = (EditText) registerView.findViewById(R.id.oldBatchNoEdtTxt);
        PaytmNoEdtTxt = (EditText) registerView.findViewById(R.id.PaytmNoEdtTxt);
        noncorporateButton = (ImageView) registerView.findViewById(R.id.noncorporateButton);
        corporateButton = (ImageView) registerView.findViewById(R.id.corporateButton);
        mos = (ImageView) registerView.findViewById(R.id.mos);
        moscorporate = (ImageView) registerView.findViewById(R.id.moscorporate);


        sms_user = preferences.getString("sms_username", "");
        sms_pass = preferences.getString("sms_password", "");
        cashbackamount = preferences.getString("cashBackAmount", "");
        if (!cashbackamount.equals("")) {
            //  feesEdtTxt.setText(cashbackamount);
            availCashback.setVisibility(View.VISIBLE);
            availCashback.append("Rs. " + cashbackamount);
        }
        nameEdtTxt.setText(preferences.getString("student_name", ""));
        emailid = preferences.getString("mail_id", "");
        corporate_id = preferences.getString("corporate", "");
        expiry_date = preferences.getString("expiry_date", "");
        company_id = preferences.getString("company_id", "");
        batch_fees = Integer.parseInt(preferences.getString("batch_fees", ""));
        getCompanySelect(company_id);
        if (corporate_id.equals("1")) {
            noncorporateButton.setVisibility(View.VISIBLE);
            corporateButton.setVisibility(View.GONE);
            mos.setVisibility(View.GONE);
            moscorporate.setVisibility(View.GONE);
        }
        if (corporate_id.equals("2")) {
            noncorporateButton.setVisibility(View.GONE);
            corporateButton.setVisibility(View.VISIBLE);
            mos.setVisibility(View.GONE);
            moscorporate.setVisibility(View.GONE);
        }
        if (corporate_id.equals("3")) {
            noncorporateButton.setVisibility(View.VISIBLE);
            corporateButton.setVisibility(View.GONE);
            mos.setVisibility(View.GONE);
            moscorporate.setVisibility(View.GONE);
        }
        if (corporate_id.equals("4")) {
            noncorporateButton.setVisibility(View.VISIBLE);
            corporateButton.setVisibility(View.GONE);
            mos.setVisibility(View.GONE);
            moscorporate.setVisibility(View.GONE);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd hh:mm:ss
        Calendar cal = Calendar.getInstance();
        pay_date = format.format(cal.getTime());
        cheque_date = format.format(cal.getTime());
        date_of_receipt = format.format(cal.getTime());
        case_deposit_date = format.format(cal.getTime());
        paytm_date = format.format(cal.getTime());
        String newDate = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", pay_date);
        datePickerPayDate.setText(newDate);
        datePickerChequeDate.setText(newDate);
        datePickerDateofReceipt.setText(newDate);
        datePickerDepositDate.setText(newDate);
        datePickerPaymentDate.setText(newDate);
        datePickerPayDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerPayDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        pay_date = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        datePickerChequeDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerChequeDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        cheque_date = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        datePickerDateofReceipt.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerDateofReceipt.setText(dateFormatter.format(mcurrentDate.getTime()));
                        date_of_receipt = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        datePickerDepositDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerDepositDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        case_deposit_date = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        datePickerPaymentDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerPaymentDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        paytm_date = format.format(mcurrentDate.getTime());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        datePickerNextPayDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerNextPayDate.setText(dateFormatter.format(mcurrentDate.getTime()));
                        next_pay_date = format.format(mcurrentDate.getTime());
                        next_pay_comments = ", Next payment on " + dateFormatter.format(mcurrentDate.getTime());
                        unableflag = 0;
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        companyArrayList = new ArrayList<CompanyDAO>();
        Totalfees = (TextView) registerView.findViewById(R.id.Totalfees);
        SearchCompany = (AutoCompleteTextView) registerView.findViewById(R.id.SearchCompany);
        SearchCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextCompany = s.toString();
                // getCompanySelect(newTextCompany);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        CheckBox not = (CheckBox) registerView.findViewById(R.id.chkSelected);
        CheckBox notm = (CheckBox) registerView.findViewById(R.id.chkSelectedm);
        CheckBox chkSelectedReverse = (CheckBox) registerView.findViewById(R.id.chkSelectedReverse);

        if (preferences.getString("current_send_sms", "").equals("sendsms")) {
            not.setChecked(true);
            prefEditor.putString("current_send_sms", "sendsms");
            prefEditor.commit();

        } else if (preferences.getString("current_send_sms", "").equals("notsendsms")) {
            not.setChecked(false);
            prefEditor.putString("current_send_sms", "notsendsms");
            prefEditor.commit();
        }


        if (preferences.getString("send_mail", "").equals("sendmail")) {
            notm.setChecked(true);
            prefEditor.putString("current_send_mail", "sendmail");
            prefEditor.commit();
        } else if (preferences.getString("send_mail", "").equals("notsendmail")) {
            notm.setChecked(false);
            prefEditor.putString("current_send_mail", "notsendmail");
            prefEditor.commit();
        }


        not.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    Log.d("True", "Value");
                    prefEditor.putString("current_send_sms", "sendsms");
                    prefEditor.commit();

                } else {
                    Log.d("False", "Value");
                    prefEditor.putString("current_send_sms", "notsendsms");
                    prefEditor.commit();
                }
            }
        });
        notm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    Log.d("True", "Value");
                    prefEditor.putString("current_send_mail", "sendmail");
                    prefEditor.commit();

                } else {
                    Log.d("False", "Value");
                    prefEditor.putString("current_send_mail", "notsendmail");
                    prefEditor.commit();
                }
            }
        });
        chkSelectedReverse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    reverseFlag = "1";
                    String value = feesEdtTxt.getText().toString().trim();
                    feesEdtTxt.setText("-" + value);

                } else {
                    reverseFlag = "";
                    String value = feesEdtTxt.getText().toString().trim().replace("-", "");
                    feesEdtTxt.setText("" + value);
                }
            }
        });
        add_comapny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                CompanyDetailsEntryView companyDetailsEntryView = new CompanyDetailsEntryView();
                companyDetailsEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "companyDetailsEntryView");


            }
        });

        feesEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && !s.toString().equals("-")) {

                    feesAmount = Double.parseDouble((s.toString().replace("--", "-")));
                    // getGSTDetails((feesAmount + cashbackAmount));
                } else {

                    Totalfees.setText("");
                    Totalfees.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        duefeesEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {

                    if (!s.toString().equals("0")) {
                        dueFees1 = "Fees Due Rs " + s;
                        dueFees = "" + s;
                        unableflag = 1;
                        datePickerNextPayDate.setVisibility(View.VISIBLE);
                    } else {


                        dueFees1 = "No Fees Due.";
                        dueFees = "";
                        unableflag = 0;
                        datePickerNextPayDate.setVisibility(View.GONE);
                    }

                } else {

                    dueFees = "";
                    unableflag = 0;
                    datePickerNextPayDate.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        RefNOEdtTxt.setFilters(new InputFilter[]{filter});

        userslist = new ArrayList<>();
        userslist.add(new RequestChangeUsersNameDAO("0", "Select Fee Type", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Cash", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Refund", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Cheque", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "EFT", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Old Student", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Cash Deposit", "0", "0"));
        userslist.add(new RequestChangeUsersNameDAO("0", "Paytm", "0", "0"));


        // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
        ArrayAdapter<RequestChangeUsersNameDAO> adapter = new ArrayAdapter<RequestChangeUsersNameDAO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, userslist);
        // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
        spinnerFeesMode.setAdapter(adapter);
        spinnerFeesMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                RequestChangeUsersNameDAO LeadSource = (RequestChangeUsersNameDAO) parent.getSelectedItem();
                // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                spinnerSelected = LeadSource.getUser_name();
                if (spinnerSelected.equals("Refund")) {
                    feesEdtTxt.setText("-" + feesEdtTxt.getText().toString());
                    refund.setVisibility(View.VISIBLE);
                    eft.setVisibility(View.GONE);
                    cheque.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                    oldStudent.setVisibility(View.GONE);
                    CashDeposit.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.GONE);

                } else if (reverseFlag.equals("1")) {

                } else if (spinnerSelected.equals("Cash")) {
                    eft.setVisibility(View.GONE);
                    cheque.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    oldStudent.setVisibility(View.GONE);
                    CashDeposit.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));

                } else if (spinnerSelected.equals("EFT")) {
                    eft.setVisibility(View.VISIBLE);
                    cheque.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    oldStudent.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));


                } else if (spinnerSelected.equals("Cheque")) {
                    cheque.setVisibility(View.VISIBLE);
                    eft.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    oldStudent.setVisibility(View.GONE);
                    CashDeposit.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));

                } else if (spinnerSelected.equals("Paytm")) {
                    paytm.setVisibility(View.VISIBLE);
                    cheque.setVisibility(View.GONE);
                    eft.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    oldStudent.setVisibility(View.GONE);
                    CashDeposit.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));

                } else if (spinnerSelected.equals("Old Student")) {
                    oldStudent.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.GONE);
                    cheque.setVisibility(View.GONE);
                    eft.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    CashDeposit.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));
                } else if (spinnerSelected.equals("Cash Deposit")) {
                    CashDeposit.setVisibility(View.VISIBLE);
                    oldStudent.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                    cheque.setVisibility(View.GONE);
                    eft.setVisibility(View.GONE);
                    refund.setVisibility(View.GONE);
                    duefeesEdtTxt.setVisibility(View.VISIBLE);
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));
                    new initBranchSpinner().execute();
                } else {
                    feesEdtTxt.setText(feesEdtTxt.getText().toString().replace("-", ""));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        technologylist = new ArrayList<>();
        technologylist.add(new TechnologyNameDAO("0", "Select Technology", "0"));
        technologylist.add(new TechnologyNameDAO("0", "NEFT", "0"));
        technologylist.add(new TechnologyNameDAO("0", "IMPS", "0"));

        // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
        ArrayAdapter<TechnologyNameDAO> adapter1 = new ArrayAdapter<TechnologyNameDAO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, technologylist);
        // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
        spinnerTechnologyMode.setAdapter(adapter1);
        spinnerTechnologyMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                TechnologyNameDAO LeadSource = (TechnologyNameDAO) parent.getSelectedItem();
                // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                technologySelected = LeadSource.getUser_name();

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

                String name = nameEdtTxt.getText().toString();
                String fees = feesEdtTxt.getText().toString();

                dueFees = duefeesEdtTxt.getText().toString();
                // Toast.makeText(getActivity(), accountType,Toast.LENGTH_SHORT).show();
                if (AppStatus.getInstance(context).isOnline()) {
                    if (validate(spinnerSelected)) {
                        if (spinnerSelected.equals("Cash")) {
                            remarks = dueFees1 + next_pay_comments;
                            if (validatecash(spinnerSelected, fees, dueFees, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }
                        } else if (spinnerSelected.equals("Refund")) {
                            //  Toast.makeText(context, "Refund", Toast.LENGTH_LONG).show();
                            refundTxt = refundEdtTxt.getText().toString().trim();
                            remarks = "Fees of Rs  " + fees.replace("-", "") + " received on " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date_of_receipt) + " Reason: " + refundTxt;
                            if (validaterefund(spinnerSelected, fees, date_of_receipt, refundTxt)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }

                        } else if (spinnerSelected.equals("Cheque")) {
                            //  Toast.makeText(context, "Refund", Toast.LENGTH_LONG).show();
                            check_no = ChequeNoEdtTxt.getText().toString().trim();
                            bank_name = BankNameEdtTxt.getText().toString().trim();
                            issuer_name = IssuerNameEdtTxt.getText().toString().trim();
                            remarks = check_no + " of " + bank_name + " on " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", cheque_date) + " from Account of " + issuer_name + ", " + dueFees1 + next_pay_comments;
                            if (validateCheque(spinnerSelected, fees, dueFees, check_no, bank_name, cheque_date, issuer_name, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }

                        } else if (spinnerSelected.equals("EFT")) {
                            //  Toast.makeText(context, "Refund", Toast.LENGTH_LONG).show();
                            ref_no = RefNOEdtTxt.getText().toString().trim();
                            remarks = "Ref No: " + ref_no + " on " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", pay_date) + " using " + technologySelected + ", " + dueFees1 + next_pay_comments;
                            if (validateEFT(spinnerSelected, fees, dueFees, ref_no, technologySelected, pay_date, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }
                        if (spinnerSelected.equals("Old Student")) {
                            old_batch_no = oldBatchNoEdtTxt.getText().toString().trim();
                            remarks = "Old Student of BatchNo: " + old_batch_no + ", " + dueFees + next_pay_comments;
                            if (validateoldStudent(spinnerSelected, fees, dueFees, old_batch_no, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }

                        if (spinnerSelected.equals("Cash Deposit")) {
                            remarks = "Cash Deposited in " + center_id + " on " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", case_deposit_date) + ", " + dueFees + next_pay_comments;
                            if (validatecaseDeposit(spinnerSelected, fees, dueFees, case_deposit_date, center_id, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }

                        if (spinnerSelected.equals("Paytm")) {
                            paytm_no = PaytmNoEdtTxt.getText().toString().trim();
                            remarks = "Paytm: " + paytm_no + " on " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", paytm_date) + ", " + dueFees + next_pay_comments;
                            if (validatePaytm(spinnerSelected, fees, dueFees, paytm_no, paytm_date, unableflag)) {
                                // dismiss();
                                if (click) {
                                    //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                    sendData(name, fees, remarks, spinnerSelected);
                                    click = false;
                                } else {
                                    Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    }
                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return registerView;
    }

    public boolean validate(String selected) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validatecash(String selected, String fees, String dueFees, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validaterefund(String selected, String fees, String date_of_receipt, String refundTxt) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("-") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (date_of_receipt.equals("")) {
            Toast.makeText(getActivity(), "Please select  date of receipt", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (refundTxt.equals("")) {
            Toast.makeText(getActivity(), "Please Enter  Reason for Refund", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validateCheque(String selected, String fees, String dueFees, String check_no, String bank_name, String cheque_date, String issuer_name, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else if (check_no.equals("")) {
            Toast.makeText(getActivity(), "Please enter check no.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (bank_name.equals("")) {
            Toast.makeText(getActivity(), "Please enter bank name.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (cheque_date.equals("")) {
            Toast.makeText(getActivity(), "Please select  cheque date", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (issuer_name.equals("")) {
            Toast.makeText(getActivity(), "Please Enter  issuer name", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validateEFT(String selected, String fees, String dueFees, String ref_no, String technologySelected, String pay_date, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else if (ref_no.equals("")) {
            Toast.makeText(getActivity(), "Please enter reference no.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (technologySelected.equals("Select Technology")) {
            Toast.makeText(getActivity(), "Please select technology.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (pay_date.equals("")) {
            Toast.makeText(getActivity(), "Please select  payment date", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validateoldStudent(String selected, String fees, String dueFees, String old_batch_no, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else if (old_batch_no.equals("")) {
            Toast.makeText(getActivity(), "Please enter old batch no.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validatecaseDeposit(String selected, String fees, String dueFees, String case_deposit_date, String center_id, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else if (case_deposit_date.equals("")) {
            Toast.makeText(getActivity(), "Please select case deposit date", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (center_id.equals("Select Branch")) {
            Toast.makeText(getActivity(), "Please select branch.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public boolean validatePaytm(String selected, String fees, String dueFees, String paytm_no, String paytm_date, int unableflag) {
        boolean isValidate = false;
        if (selected.equals("Select Fee Type")) {
            Toast.makeText(getActivity(), "Please select Fee Mode.", Toast.LENGTH_LONG).show();
        } else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (dueFees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Fees Due", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (unableflag == 1) {
            if (next_pay_date.equals("")) {
                Toast.makeText(getActivity(), "Please select next payment date!", Toast.LENGTH_LONG).show();
                isValidate = false;
            }

        } else if (paytm_no.equals("")) {
            Toast.makeText(getActivity(), "Please enter Wallet Txn ID", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (paytm_no.length() < 11) {
            Toast.makeText(getActivity(), "Please enter valid Wallet Txn ID", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (paytm_date.equals("Select Branch")) {
            Toast.makeText(getActivity(), "Please select Payment Date.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    public void sendData(final String name, final String fees, final String remarks, final String spinnerSelected) {
        if (spinnerSelected.equals("Cash")) {
            ReceivedBy = preferences.getString("trainer_user_name", "");
            ReceivedBy_id = preferences.getString("trainer_user_id", "");
            String str = name;
            String[] splited = str.split(" ");
            strName = splited[0];

        } else if (spinnerSelected.equals("Cheque")) {
            ReceivedBy = "Raza Saghar ";
            ReceivedBy_id = "RS";
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];
        } else if (spinnerSelected.equals("Refund")) {
            ReceivedBy = "Raza Saghar ";
            ReceivedBy_id = "RS";
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];


        } else if (spinnerSelected.equals("EFT")) {
            ReceivedBy = "Raza Saghar ";
            ReceivedBy_id = "RS";
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];

        } else if (spinnerSelected.equals("Old Student")) {
            ReceivedBy = "Discount ";
            ReceivedBy_id = "Discount ";
        } else if (spinnerSelected.equals("Discount")) {
            ReceivedBy = "Discount ";
            ReceivedBy_id = "Discount ";
        } else if (spinnerSelected.equals("Cash Deposit")) {
            ReceivedBy = "Raza Saghar";
            ReceivedBy_id = "RS";
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];


        } else if (spinnerSelected.equals("Paytm")) {
            ReceivedBy = "Raza Saghar";
            ReceivedBy_id = "RS";
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];


        } else if (spinnerSelected.equals("Paytm Self")) {
            ReceivedBy = preferences.getString("trainer_user_name", "");
            ReceivedBy_id = preferences.getString("trainer_user_id", "");
            String str = name;
            String[] splited = str.split(" ");
            String strName = splited[0];


        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        jsonObjCash = new JSONObject() {
            {
                try {
                    put("batch_no", preferences.getString("batch_id", ""));
                    put("user_id", preferences.getString("id_fees", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if (spinnerSelected.equals("Refund")) {
            jsonObj = new JSONObject() {
                {
                    try {
                        put("ReceivedBy", ReceivedBy);
                        put("UserName", preferences.getString("trainer_user_name", ""));
                        put("ReceivedBy_id", ReceivedBy_id);
                        put("UserName_id", preferences.getString("trainer_user_id", ""));
                        put("BatchNo", preferences.getString("batch_id", ""));
                        put("MobileNo", preferences.getString("mobile_no", ""));
                        put("user_id", preferences.getString("id_fees", ""));
                        put("Fees", feesAmount);
                        put("Note", remarks);
                        put("FeeMode", spinnerSelected);
                        put("students_name", preferences.getString("student_name", ""));
                        put("DateTimeOfEntry", date);
                        put("next_pay_date", "");
                        put("dueFees", "");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        } else {
            jsonObj = new JSONObject() {
                {
                    try {
                        put("ReceivedBy_id", ReceivedBy_id);
                        put("UserName_id", preferences.getString("trainer_user_id", ""));
                        put("ReceivedBy", ReceivedBy);
                        put("BatchNo", preferences.getString("batch_id", ""));
                        put("MobileNo", preferences.getString("mobile_no", ""));
                        put("UserName", preferences.getString("trainer_user_name", ""));
                        put("user_id", preferences.getString("id_fees", ""));
                        put("Fees", feesAmount);
                        put("Note", remarks);
                        put("FeeMode", spinnerSelected);
                        put("DateTimeOfEntry", date);
                        put("next_pay_date", next_pay_date);
                        put("dueFees", dueFees);
                        put("students_name", preferences.getString("student_name", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();

                Log.i("jsonObj", "jsonObj" + jsonObj);
                loginResponse = serviceAccess.SendHttpPost(Config.URL_ADD_STUDENT_FEESDETAILS, jsonObj);
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
                                        code = jObject.getString("fees_id");

                                        if (status) {
                                            if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                smsSent = "Yes";
                                            } else {
                                                smsSent = "No";
                                            }
                                            subject = "FeesDetails: R" + preferences.getString("batch_code", "").substring(0, 7) + " " + preferences.getString("student_name", "").replace("Mr. ", "").replace("Ms. ", "");
                                            sendingmail = "Batch Name : " + preferences.getString("batch_code", "").substring(0, 7) + System.getProperty("line.separator")
                                                    + "Student Name : " + preferences.getString("student_name", "") + System.getProperty("line.separator")
                                                    + "Fees  Paid : " + feesAmount + System.getProperty("line.separator")
                                                    + "Fees  Due : " + dueFees1 + System.getProperty("line.separator")
                                                    + "Fees Note : " + remarks + System.getProperty("line.separator")
                                                    + "Received By : " + ReceivedBy + System.getProperty("line.separator")
                                                    + "Entered By : " + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                    + "SMS Sent : " + smsSent + System.getProperty("line.separator")
                                                    + "FeeMode : " + spinnerSelected + System.getProperty("line.separator")
                                                    + "Date Time : " + date + System.getProperty("line.separator");
                                            if (spinnerSelected.equals("Cash")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " for " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " in Cash." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                Log.d("message---->", message);

                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }

                                                /*if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            } else if (spinnerSelected.equals("Cheque")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " recorded for " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " which is paid via Cheque. Receipt is Subject to Realization of Cheque." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }
                                                //receipt....................

                                               /* if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            } else if (spinnerSelected.equals("EFT")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " recorded for " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " which is paid via EFT. Receipt is Subject to Realization of EFT." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }
                                                //receipt....................

                                                /*if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            } else if (spinnerSelected.equals("Refund")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " refunded for  " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " in EFT." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }


                                            } else if (spinnerSelected.equals("Cash Deposit")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " recorded for  " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " which is paid via Cash Deposit." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }

                                               /* if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            } else if (spinnerSelected.equals("Paytm")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " for " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " in Paytm." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }

                                                /*if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            } else if (spinnerSelected.equals("Paytm Self")) {
                                                String str = name.replace("Mr. ", "").replace("Mr. ", "").replace("Ms. ", "");
                                                String[] splited = str.split(" ");
                                                String strName = splited[0];
                                                message = "Hi " + strName + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Received Rs. " + (feesAmount + cashbackAmount) + " for " + preferences.getString("course_name", "") + " Batch No. " + preferences.getString("batch_code", "").substring(0, 7) + " in Paytm." + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Note:" + remarks + System.getProperty("line.separator") + System.getProperty("line.separator")
                                                        + "Regards" + System.getProperty("line.separator")
                                                        + preferences.getString("trainer_name", "") + System.getProperty("line.separator")
                                                        + preferences.getString("phone_no", "");
                                                if (preferences.getString("current_send_sms", "").equals("sendsms")) {
                                                    String result = sendSms1(preferences.getString("mobile_no", ""), message);
                                                    Log.d("sent sms---->", result);
                                                }

                                                /*if (corporate_id.equals("1")) {

                                                    createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);

                                                }
                                                if (corporate_id.equals("2")) {

                                                    createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                }
                                                if (corporate_id.equals("3")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }

                                                }
                                                if (corporate_id.equals("4")) {
                                                    if (spinnerSelectedFeeStatus.equals("PP")) {
                                                        createandDisplayCorporatePdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    } else {
                                                        createandDisplayCorporateMOSPdf(strName, spinnerSelected, pdfAmount, spinnerSelectedFeeStatus, code);
                                                    }
                                                }*/

                                            }

                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                            new MyAsyncClass().execute();
                                            mListener.messageReceived(msg);
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

    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayCorporatePdf(String strName, String spinnerSelected, String fees, String spinnerSelectedFeeStatus, String code) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date);
        Double final_amount = (feesAmount + cashbackAmount);
        String in_word_feess = convert(final_amount.intValue());
        String fee_status = "";
        String gender = "";
        if (spinnerSelectedFeeStatus.equals("FP")) {
            fee_status = "Full";
        } else if (spinnerSelectedFeeStatus.equals("PP")) {
            fee_status = "Part";
        }

        if (preferences.getString("gender", "").equals("Male")) {
            gender = "Mr. ";
        } else if (preferences.getString("gender", "").equals("Female")) {
            gender = "Ms. ";
        }

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            prefEditor.putString("attachName", strName + code + ".pdf");
            prefEditor.commit();
            fileName = strName + code + ".pdf";
            File file = new File(dir, strName + code + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter docWriter = PdfWriter.getInstance(doc, fOut);
            receiptUri.add(Uri.fromFile(file));
            //open the document
            doc.open();
            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getActivity().getAssets().open("logo_afcks.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(30);

            String address = "AFCKS TECHNOLOGIES \n\n" +
                    "G 25,2nd Floor Shantikunj Bldg,\n\n" +
                    "Sadhu Vasvani Road\n\n" +
                    "Pune 01\n\n" +
                    "Phone 9762118718\n\n" +
                    "mohammed.raza@afcks.com\n";
            Paragraph address_p1 = new Paragraph(address);
            Font address_paraFont = new Font(Font.FontFamily.COURIER, Font.BOLD);
            address_p1.setAlignment(Paragraph.ALIGN_CENTER);
            address_p1.setSpacingBefore(1f);
            address_p1.setFont(address_paraFont);
            //  doc.add(companyLogo);
            //list all the products sold to the customer
            float[] columnWidths = {3f, 3.5f, 3.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);
            PdfPCell cell = new PdfPCell(companyLogo, true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell.setPaddingLeft(10f);
            cell.setPaddingRight(0f);
            cell.setPaddingBottom(30f);

            cell = new PdfPCell(new Phrase(address_p1));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("GST No: 27BJWPS7747G1Z7\n\n" + "No: " + code + "\n\n\n" + "Dt: " + date_after));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);

            table.setHeaderRows(1);
            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, doc.leftMargin(), 650, docWriter.getDirectContent());
            //list all the products sold to the customer
            float[] columnWidths1 = {7f};
            PdfPTable table1 = new PdfPTable(columnWidths1);
            // set table width a percentage of the page width
            table1.setTotalWidth(500f);
            PdfPCell cell1 = new PdfPCell(new Phrase("\n\n" + "Received with thanks from: " + gender + strName + "\n\n" +
                    "The sum of Rs. " + in_word_feess + " Only /-" + "\n\n" +
                    "By " + spinnerSelected + " in " + fee_status + " payment on A/C of " + preferences.getString("course_name", "") + "\n\nRef Batch No: " + preferences.getString("batch_code", "").substring(0, 7) + "\n\n" + "Class conducted By: Mr. " + preferences.getString("trainer_name", " ")));
            cell1.setVerticalAlignment(Element.ALIGN_LEFT);
            cell1.setPaddingLeft(10f);
            cell.setPaddingBottom(5f);
            table1.addCell(cell1);
            table1.setHeaderRows(1);
            table1.writeSelectedRows(0, -1, doc.leftMargin(), 500, docWriter.getDirectContent());
            String s1 = "\nRs. " + final_amount + " / -" + "\n\n" +
                    "Mobile No:-" + preferences.getString("mobile_no", "") + "\n\n";
            Paragraph p1 = new Paragraph(s1);
            Font paraFont = new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);

            p1.setFont(paraFont);

            float[] columnWidths2 = {7f};
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setTotalWidth(500f);
            PdfPCell cell2 = new PdfPCell(new Phrase(p1));
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setPaddingLeft(10f);
            table2.addCell(cell2);
            table2.setHeaderRows(1);
            table2.writeSelectedRows(0, -1, doc.leftMargin(), 180, docWriter.getDirectContent());


            //String value_note="*** Note This is Electronic Receipt ";
            String value_note = "\n\n\n\n\n\n\n\nReceipt";
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 52);
            f1.setColor(BaseColor.BLUE);
            Font f2 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2.setColor(BaseColor.RED);
            selector.addFont(f1);
            selector.addFont(f2);

            Phrase ph = selector.process(value_note);
            Paragraph note = new Paragraph(ph);
            note.setAlignment(Paragraph.ALIGN_CENTER);


            float[] columnWidths3 = {7f};
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setTotalWidth(500f);
            PdfPCell cell3 = new PdfPCell(new Phrase("\n\n\n\n"));
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell3.setPaddingLeft(10f);
            table3.addCell(cell3);
            table3.setHeaderRows(1);
            table3.writeSelectedRows(0, -1, doc.leftMargin(), 702, docWriter.getDirectContent());


            float[] columnWidths4 = {5f, 2f};
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setTotalWidth(500f);
            PdfPCell cell4 = new PdfPCell(new Phrase("\nClient Details\n\n" + "Company: " + comapny_name + "\n\n" + "GST No: " + company_gstno + "\n\n" + "Address: " + company_address + "\n\n" + "City: " + company_city + "\n\n" + "State: " + company_state + "\n\n"));
            cell4.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell4.setPaddingLeft(10f);
            table4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("\n" + "IGST: " + IGST + "\n\n" + "SGST: " + SGST + "\n\n" + "CGST: " + CGST + "\n\nGross Value: " + fees + "\n\n" + "Total Tax: " + (IGST + CGST + SGST) + "\n\n" + "Invoice Value: " + final_amount + "\n\n\n\n"));
            cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell4.setPaddingLeft(10f);
            table4.addCell(cell4);

            table4.setHeaderRows(1);
            table4.writeSelectedRows(0, -1, doc.leftMargin(), 364, docWriter.getDirectContent());


            String value_note2 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*** Note this is authicated Electronic Receipt therefore no signature and stamp needed  ";
            Paragraph note2 = new Paragraph(value_note2);
            Font noteparaFont2 = new Font(Font.FontFamily.COURIER);
            note2.setAlignment(Paragraph.ALIGN_LEFT);
            note2.setFont(noteparaFont2);
            Font font = FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE);
// Blue
            font.setColor(0, 0, 255);
// We need a Chunk in order to have a font's style
            Chunk chunk = new Chunk("www.afckstechnologies.in/verify", font);
            Anchor anchor = new Anchor(chunk);
            anchor.setReference("www.afckstechnologies.in/verify");
            String value_link = "" + anchor;
            FontSelector selector_link = new FontSelector();
            Font f1_link = FontFactory.getFont(FontFactory.TIMES, 12);
            f1_link.setStyle(Font.UNDERLINE);
            f1_link.setColor(BaseColor.BLACK);
            selector_link.addFont(f1_link);
            Phrase ph_link = selector_link.process(value_link);


            Paragraph _verify = new Paragraph();
            _verify.add("To Verify kindly visit ");
            _verify.add(anchor);
            _verify.add(" and enter Ref No. " + code);
            Font _verifyparaFont2 = new Font(Font.FontFamily.COURIER);
            _verify.setAlignment(Paragraph.ALIGN_BOTTOM);
            _verify.setFont(_verifyparaFont2);
            //add paragraph to document
            doc.add(note);
            doc.add(note2);
            doc.add(_verify);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        // viewPdf("newFile.pdf", "Dir");
        Log.d("send mail", preferences.getString("current_send_mail", ""));

        if (preferences.getString("current_send_mail", "").equals("sendmail")) {
            prefEditor.putString("current_send_mail", "notsendmail");
            prefEditor.commit();
            // new MyAsyncClass().execute();
        } else {
            // new UploadFileToServer().execute();
        }
    }


    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf(String strName, String spinnerSelected, String fees, String spinnerSelectedFeeStatus, String code) {
        // Random random = new Random();
        // id = String.format("%04d", random.nextInt(10000));
        //  String code = "T"+id;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date);

        Double final_amount = (feesAmount + cashbackAmount);
        String in_word_feess = convert(final_amount.intValue());

        String fee_status = "";
        String gender = "";
        if (spinnerSelectedFeeStatus.equals("FP")) {
            fee_status = "Full";
        } else if (spinnerSelectedFeeStatus.equals("PP")) {
            fee_status = "Part";
        }

        if (preferences.getString("gender", "").equals("Male")) {
            gender = "Mr. ";
        } else if (preferences.getString("gender", "").equals("Female")) {
            gender = "Ms. ";
        }

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            prefEditor.putString("attachName", strName + code + ".pdf");
            prefEditor.commit();
            fileName = strName + code + ".pdf";
            File file = new File(dir, strName + code + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter docWriter = PdfWriter.getInstance(doc, fOut);
            receiptUri.add(Uri.fromFile(file));
            //open the document
            doc.open();
            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getActivity().getAssets().open("logo_afcks.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(30);

            String address = "AFCKS TECHNOLOGIES \n\n" +
                    "G 25,2nd Floor Shantikunj Bldg,\n\n" +
                    "Sadhu Vasvani Road\n\n" +
                    "Pune 01\n\n" +
                    "Phone 9762118718\n\n" +
                    "mohammed.raza@afcks.com\n\n";
            Paragraph address_p1 = new Paragraph(address);
            Font address_paraFont = new Font(Font.FontFamily.COURIER, Font.BOLD);
            address_p1.setAlignment(Paragraph.ALIGN_CENTER);
            address_p1.setSpacingBefore(1f);
            address_p1.setFont(address_paraFont);
            //  doc.add(companyLogo);
            //list all the products sold to the customer
            float[] columnWidths = {3f, 4f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);
            PdfPCell cell = new PdfPCell(companyLogo, true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell.setPaddingLeft(10f);
            cell.setPaddingRight(10f);
            cell.setPaddingBottom(30f);

            cell = new PdfPCell(new Phrase(address_p1));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("No: " + code + "\n\n" + "Dt: " + date_after));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            table.setHeaderRows(1);
            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, doc.leftMargin(), 648, docWriter.getDirectContent());
            //list all the products sold to the customer
            float[] columnWidths1 = {7f};
            PdfPTable table1 = new PdfPTable(columnWidths1);
            // set table width a percentage of the page width
            table1.setTotalWidth(500f);
            PdfPCell cell1 = new PdfPCell(new Phrase("\n" + "Received with thanks from: " + gender + strName + "\n\n" +
                    "The Sum of Rs. " + in_word_feess + " Only /-" + "\n\n" +
                    "By " + spinnerSelected + " in " + fee_status + " payment on A/C of " + preferences.getString("course_name", "") + "\n\nRef Batch No: " + preferences.getString("batch_code", "").substring(0, 7) + "\n\n" + "Class conducted By: Mr. " + preferences.getString("trainer_name", "") + "\n"));
            cell1.setVerticalAlignment(Element.ALIGN_LEFT);
            cell1.setPaddingLeft(10f);
            table1.addCell(cell1);
            table1.setHeaderRows(1);
            table1.writeSelectedRows(0, -1, doc.leftMargin(), 498, docWriter.getDirectContent());
            String s1 = "\nRs. " + final_amount + " / -" + "\n\n" +
                    "Mobile No:-" + preferences.getString("mobile_no", "") + "\n\n";
            Paragraph p1 = new Paragraph(s1);
            Font paraFont = new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);

            p1.setFont(paraFont);

            float[] columnWidths2 = {7f};
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setTotalWidth(500f);
            PdfPCell cell2 = new PdfPCell(new Phrase(p1));
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setPaddingLeft(10f);
            table2.addCell(cell2);
            table2.setHeaderRows(1);
            table2.writeSelectedRows(0, -1, doc.leftMargin(), 374, docWriter.getDirectContent());


            //String value_note="*** Note This is Electronic Receipt ";
            String value_note = "\n\n\n\n\n\n\n\nReceipt";
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 50);
            f1.setColor(BaseColor.BLUE);
            Font f2 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2.setColor(BaseColor.RED);
            selector.addFont(f1);
            selector.addFont(f2);

            Phrase ph = selector.process(value_note);
            Paragraph note = new Paragraph(ph);
            note.setAlignment(Paragraph.ALIGN_CENTER);


            float[] columnWidths3 = {7f};
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setTotalWidth(500f);
            PdfPCell cell3 = new PdfPCell(new Phrase("\n\n\n\n"));
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell3.setPaddingLeft(10f);
            table3.addCell(cell3);
            table3.setHeaderRows(1);
            table3.writeSelectedRows(0, -1, doc.leftMargin(), 700, docWriter.getDirectContent());

            String value_note2 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*** Note this is authicated Electronic Receipt therefore no signature and stamp needed  ";
            Paragraph note2 = new Paragraph(value_note2);
            Font noteparaFont2 = new Font(Font.FontFamily.COURIER);
            note2.setAlignment(Paragraph.ALIGN_LEFT);
            note2.setFont(noteparaFont2);
            Font font = FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE);
// Blue
            font.setColor(0, 0, 255);
// We need a Chunk in order to have a font's style
            Chunk chunk = new Chunk("www.afckstechnologies.in/verify", font);
            Anchor anchor = new Anchor(chunk);
            anchor.setReference("www.afckstechnologies.in/verify");
            String value_link = "" + anchor;
            FontSelector selector_link = new FontSelector();
            Font f1_link = FontFactory.getFont(FontFactory.TIMES, 12);
            f1_link.setStyle(Font.UNDERLINE);
            f1_link.setColor(BaseColor.BLACK);
            selector_link.addFont(f1_link);
            Phrase ph_link = selector_link.process(value_link);


            Paragraph _verify = new Paragraph();
            _verify.add("To Verify kindly visit ");
            _verify.add(anchor);
            _verify.add(" and enter Ref No. " + code);
            Font _verifyparaFont2 = new Font(Font.FontFamily.COURIER);
            _verify.setAlignment(Paragraph.ALIGN_BOTTOM);
            _verify.setFont(_verifyparaFont2);
            //add paragraph to document
            doc.add(note);
            doc.add(note2);
            doc.add(_verify);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        // viewPdf("newFile.pdf", "Dir");

        if (preferences.getString("current_send_mail", "").equals("sendmail")) {
            prefEditor.putString("current_send_mail", "notsendmail");
            prefEditor.commit();
            // new MyAsyncClass().execute();
        } else {
            //  new UploadFileToServer().execute();
        }
    }
    // Method for creating a pdf file from text, saving it then opening it for display
    //mos n with corporate...........

    public void createandDisplayCorporateMOSPdf(String strName, String spinnerSelected, String fees, String spinnerSelectedFeeStatus, String code) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date);

        String expiryDate = formateDateFromstring("yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy", casBackExpiryDate);
        Double final_amount = (feesAmount + cashbackAmount);
        String in_word_feess = convert(final_amount.intValue());
        String fee_status = "";
        String gender = "";
        if (spinnerSelectedFeeStatus.equals("FP")) {
            fee_status = "Full";
        } else if (spinnerSelectedFeeStatus.equals("PP")) {
            fee_status = "Part";
        }

        if (preferences.getString("gender", "").equals("Male")) {
            gender = "Mr. ";
        } else if (preferences.getString("gender", "").equals("Female")) {
            gender = "Ms. ";
        }

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            prefEditor.putString("attachName", strName + code + ".pdf");
            prefEditor.commit();
            fileName = strName + code + ".pdf";
            File file = new File(dir, strName + code + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter docWriter = PdfWriter.getInstance(doc, fOut);
            receiptUri.add(Uri.fromFile(file));
            //open the document
            doc.open();
            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getActivity().getAssets().open("logo_afcks.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(30);

            String address = "AFCKS TECHNOLOGIES \n\n" +
                    "G 25,2nd Floor Shantikunj Bldg,\n\n" +
                    "Sadhu Vasvani Road\n\n" +
                    "Pune 01\n\n" +
                    "Phone 9762118718\n\n" +
                    "mohammed.raza@afcks.com\n";
            Paragraph address_p1 = new Paragraph(address);
            Font address_paraFont = new Font(Font.FontFamily.COURIER, Font.BOLD);
            address_p1.setAlignment(Paragraph.ALIGN_CENTER);
            address_p1.setSpacingBefore(1f);
            address_p1.setFont(address_paraFont);
            //  doc.add(companyLogo);
            //list all the products sold to the customer
            float[] columnWidths = {3f, 3.5f, 3.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);
            PdfPCell cell = new PdfPCell(companyLogo, true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell.setPaddingLeft(10f);
            cell.setPaddingRight(0f);
            cell.setPaddingBottom(30f);

            cell = new PdfPCell(new Phrase(address_p1));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("GST No: 27BJWPS7747G1Z7\n\n" + "No: " + code + "\n\n\n" + "Dt: " + date_after));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);

            table.setHeaderRows(1);
            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, doc.leftMargin(), 670, docWriter.getDirectContent());
            //list all the products sold to the customer
            float[] columnWidths1 = {7f};
            PdfPTable table1 = new PdfPTable(columnWidths1);
            // set table width a percentage of the page width
            table1.setTotalWidth(500f);
            PdfPCell cell1 = new PdfPCell(new Phrase("\n\n" + "Received with thanks from: " + gender + strName + "\n\n" +
                    "The sum of Rs. " + in_word_feess + " Only /-" + "\n\n" +
                    "By " + spinnerSelected + " in " + fee_status + " payment on A/C of " + preferences.getString("course_name", "") + " & Microsoft Certification #." + "\n\nRef Batch No: " + preferences.getString("batch_code", "").substring(0, 7) + "\n\n" + "Class conducted By: Mr. " + preferences.getString("trainer_name", " ")));
            cell1.setVerticalAlignment(Element.ALIGN_LEFT);
            cell1.setPaddingLeft(10f);
            cell.setPaddingBottom(5f);
            table1.addCell(cell1);
            table1.setHeaderRows(1);
            table1.writeSelectedRows(0, -1, doc.leftMargin(), 520, docWriter.getDirectContent());
            String s1 = "\nRs. " + final_amount + " / -" + "\n\n" +
                    "Mobile No:-" + preferences.getString("mobile_no", "") + "\n\n";
            Paragraph p1 = new Paragraph(s1);
            Font paraFont = new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);

            p1.setFont(paraFont);

            float[] columnWidths2 = {7f};
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setTotalWidth(500f);
            PdfPCell cell2 = new PdfPCell(new Phrase(p1));
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setPaddingLeft(10f);
            table2.addCell(cell2);
            table2.setHeaderRows(1);
            table2.writeSelectedRows(0, -1, doc.leftMargin(), 188, docWriter.getDirectContent());


            //String value_note="*** Note This is Electronic Receipt ";
            String value_note = "\n\n\n\n\n\n\nReceipt";
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 52);
            f1.setColor(BaseColor.BLUE);
            Font f2 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2.setColor(BaseColor.RED);
            selector.addFont(f1);
            selector.addFont(f2);

            Phrase ph = selector.process(value_note);
            Paragraph note = new Paragraph(ph);
            note.setAlignment(Paragraph.ALIGN_CENTER);


            float[] columnWidths3 = {7f};
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setTotalWidth(500f);
            PdfPCell cell3 = new PdfPCell(new Phrase("\n\n\n\n"));
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell3.setPaddingLeft(10f);
            table3.addCell(cell3);
            table3.setHeaderRows(1);
            table3.writeSelectedRows(0, -1, doc.leftMargin(), 722, docWriter.getDirectContent());


            float[] columnWidths4 = {5f, 2f};
            PdfPTable table4 = new PdfPTable(columnWidths4);
            // set table width a percentage of the page width
            table4.setTotalWidth(500f);
            PdfPCell cell4 = new PdfPCell(new Phrase("\nClient Details\n\n" + "Company: " + comapny_name + "\n\n" + "GST No: " + company_gstno + "\n\n" + "Address: " + company_address + "\n\n" + "City: " + company_city + "\n\n" + "State: " + company_state + "\n\n"));
            cell4.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell4.setPaddingLeft(10f);
            table4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("\n" + "IGST: " + IGST + "\n\n" + "SGST: " + SGST + "\n\n" + "CGST: " + CGST + "\n\nGross Value: " + (Double.parseDouble(fees) + MOS) + "\n\n" + "Total Tax: " + (IGST + CGST + SGST) + "\n\n" + "Invoice Value: " + final_amount + "\n\n\n\n"));
            cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell4.setPaddingLeft(10f);
            table4.addCell(cell4);

            table4.setHeaderRows(1);
            table4.writeSelectedRows(0, -1, doc.leftMargin(), 372, docWriter.getDirectContent());


            String value_note2 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*** Note this is authicated Electronic Receipt therefore no signature and stamp needed.\n # Microsoft Certification exam voucher valid till " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", expiry_date) + ".\n"
                    + " # If you do another course before " + expiryDate + "  then you will get Rs. " + cashBackAmount + " cashback.";
            Paragraph note2 = new Paragraph(value_note2);
            Font noteparaFont2 = new Font(Font.FontFamily.COURIER);
            note2.setAlignment(Paragraph.ALIGN_LEFT);
            note2.setFont(noteparaFont2);

            Font font = FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE);
// Blue
            font.setColor(0, 0, 255);
// We need a Chunk in order to have a font's style
            Chunk chunk = new Chunk("www.afckstechnologies.in/verify", font);
            Anchor anchor = new Anchor(chunk);
            anchor.setReference("www.afckstechnologies.in/verify");
            String value_link = "" + anchor;
            FontSelector selector_link = new FontSelector();
            Font f1_link = FontFactory.getFont(FontFactory.TIMES, 12);
            f1_link.setStyle(Font.UNDERLINE);
            f1_link.setColor(BaseColor.BLACK);
            selector_link.addFont(f1_link);
            Phrase ph_link = selector_link.process(value_link);


            Paragraph _verify = new Paragraph();
            _verify.add(" To Verify kindly visit ");
            _verify.add(anchor);
            _verify.add(" and enter Ref No. " + code);
            Font _verifyparaFont2 = new Font(Font.FontFamily.COURIER);
            _verify.setAlignment(Paragraph.ALIGN_BOTTOM);
            _verify.setFont(_verifyparaFont2);
            //add paragraph to document
            doc.add(note);
            doc.add(note2);
            doc.add(_verify);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        // viewPdf("newFile.pdf", "Dir");

        if (preferences.getString("current_send_mail", "").equals("sendmail")) {
            prefEditor.putString("current_send_mail", "notsendmail");
            prefEditor.commit();
            //  new MyAsyncClass().execute();
        } else {
            /// new UploadFileToServer().execute();
        }
    }


    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayMOSPdf(String strName, String spinnerSelected, String fees, String spinnerSelectedFeeStatus, String code) {
        // Random random = new Random();
        // id = String.format("%04d", random.nextInt(10000));
        //  String code = "T"+id;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date);
        String expiryDate = formateDateFromstring("yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy", casBackExpiryDate);
        Double final_amount = (feesAmount + cashbackAmount);
        String in_word_feess = convert(final_amount.intValue());

        String fee_status = "";
        String gender = "";
        if (spinnerSelectedFeeStatus.equals("FP")) {
            fee_status = "Full";
        } else if (spinnerSelectedFeeStatus.equals("PP")) {
            fee_status = "Part";
        }

        if (preferences.getString("gender", "").equals("Male")) {
            gender = "Mr. ";
        } else if (preferences.getString("gender", "").equals("Female")) {
            gender = "Ms. ";
        }

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            prefEditor.putString("attachName", strName + code + ".pdf");
            prefEditor.commit();
            fileName = strName + code + ".pdf";
            File file = new File(dir, strName + code + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter docWriter = PdfWriter.getInstance(doc, fOut);
            receiptUri.add(Uri.fromFile(file));
            //open the document
            doc.open();
            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getActivity().getAssets().open("logo_afcks.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(30);

            String address = "AFCKS TECHNOLOGIES \n\n" +
                    "G 25,2nd Floor Shantikunj Bldg,\n\n" +
                    "Sadhu Vasvani Road\n\n" +
                    "Pune 01\n\n" +
                    "Phone 9762118718\n\n" +
                    "mohammed.raza@afcks.com\n\n";
            Paragraph address_p1 = new Paragraph(address);
            Font address_paraFont = new Font(Font.FontFamily.COURIER, Font.BOLD);
            address_p1.setAlignment(Paragraph.ALIGN_CENTER);
            address_p1.setSpacingBefore(1f);
            address_p1.setFont(address_paraFont);
            //  doc.add(companyLogo);
            //list all the products sold to the customer
            float[] columnWidths = {3f, 4f, 3f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);
            PdfPCell cell = new PdfPCell(companyLogo, true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell.setPaddingLeft(10f);
            cell.setPaddingRight(10f);
            cell.setPaddingBottom(30f);

            cell = new PdfPCell(new Phrase(address_p1));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("No: " + code + "\n\n" + "Dt: " + date_after));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            table.setHeaderRows(1);
            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, doc.leftMargin(), 648, docWriter.getDirectContent());
            //list all the products sold to the customer
            float[] columnWidths1 = {7f};
            PdfPTable table1 = new PdfPTable(columnWidths1);
            // set table width a percentage of the page width
            table1.setTotalWidth(500f);
            PdfPCell cell1 = new PdfPCell(new Phrase("\n" + "Received with thanks from: " + gender + strName + "\n\n" +
                    "The Sum of Rs. " + in_word_feess + " Only /-" + "\n\n" +
                    "By " + spinnerSelected + " in " + fee_status + " payment on A/C of " + preferences.getString("course_name", "") + " & Microsoft Certification #." + "\n\nRef Batch No: " + preferences.getString("batch_code", "").substring(0, 7) + ".\n\n" + "Class conducted By: Mr. " + preferences.getString("trainer_name", "") + ".\n"));
            cell1.setVerticalAlignment(Element.ALIGN_LEFT);
            cell1.setPaddingLeft(10f);
            cell1.setPaddingBottom(5f);
            table1.addCell(cell1);
            table1.setHeaderRows(1);
            table1.writeSelectedRows(0, -1, doc.leftMargin(), 498, docWriter.getDirectContent());
            String s1 = "\nRs. " + final_amount + " / -" + "\n\n" +
                    "Mobile No:-" + preferences.getString("mobile_no", "") + "\n\n";
            Paragraph p1 = new Paragraph(s1);
            Font paraFont = new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);

            p1.setFont(paraFont);

            float[] columnWidths2 = {7f};
            PdfPTable table2 = new PdfPTable(columnWidths2);
            // set table width a percentage of the page width
            table2.setTotalWidth(500f);
            PdfPCell cell2 = new PdfPCell(new Phrase(p1));
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setPaddingLeft(10f);
            table2.addCell(cell2);
            table2.setHeaderRows(1);
            table2.writeSelectedRows(0, -1, doc.leftMargin(), 358, docWriter.getDirectContent());


            //String value_note="*** Note This is Electronic Receipt ";
            String value_note = "\n\n\n\n\n\n\n\nReceipt";
            FontSelector selector = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 50);
            f1.setColor(BaseColor.BLUE);
            Font f2 = FontFactory.getFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f2.setColor(BaseColor.RED);
            selector.addFont(f1);
            selector.addFont(f2);

            Phrase ph = selector.process(value_note);
            Paragraph note = new Paragraph(ph);
            note.setAlignment(Paragraph.ALIGN_CENTER);


            float[] columnWidths3 = {7f};
            PdfPTable table3 = new PdfPTable(columnWidths3);
            // set table width a percentage of the page width
            table3.setTotalWidth(500f);
            PdfPCell cell3 = new PdfPCell(new Phrase("\n\n\n\n"));
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            // cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell3.setPaddingLeft(10f);
            table3.addCell(cell3);
            table3.setHeaderRows(1);
            table3.writeSelectedRows(0, -1, doc.leftMargin(), 700, docWriter.getDirectContent());

            String value_note2 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*** Note this is authicated Electronic Receipt therefore no signature and stamp needed. \n # Microsoft Certification exam voucher valid till " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", expiry_date) + ".\n"
                    + " # If you do another course before " + expiryDate + "  then you will get Rs. " + cashBackAmount + " cashback.";
            Paragraph note2 = new Paragraph(value_note2);
            Font noteparaFont2 = new Font(Font.FontFamily.COURIER);
            note2.setAlignment(Paragraph.ALIGN_LEFT);
            note2.setFont(noteparaFont2);

            Font font = FontFactory.getFont(FontFactory.TIMES, 12, Font.UNDERLINE);
// Blue
            font.setColor(0, 0, 255);
// We need a Chunk in order to have a font's style
            Chunk chunk = new Chunk("www.afckstechnologies.in/verify", font);
            Anchor anchor = new Anchor(chunk);
            anchor.setReference("www.afckstechnologies.in/verify");
            String value_link = "" + anchor;
            FontSelector selector_link = new FontSelector();
            Font f1_link = FontFactory.getFont(FontFactory.TIMES, 12);
            f1_link.setStyle(Font.UNDERLINE);
            f1_link.setColor(BaseColor.BLACK);
            selector_link.addFont(f1_link);
            Phrase ph_link = selector_link.process(value_link);


            Paragraph _verify = new Paragraph();
            _verify.add(" To Verify kindly visit ");
            _verify.add(anchor);
            _verify.add(" and enter Ref No. " + code);
            Font _verifyparaFont2 = new Font(Font.FontFamily.COURIER);
            _verify.setAlignment(Paragraph.ALIGN_BOTTOM);
            _verify.setFont(_verifyparaFont2);

            //add paragraph to document
            doc.add(note);
            doc.add(note2);
            doc.add(_verify);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        // viewPdf("newFile.pdf", "Dir");

        if (preferences.getString("current_send_mail", "").equals("sendmail")) {
            prefEditor.putString("current_send_mail", "notsendmail");
            prefEditor.commit();
            // new MyAsyncClass().execute();
        } else {
            //  new UploadFileToServer().execute();
        }
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


    public static String sendSms1(String tempMobileNumber, String message) {
        String sResult = null;
        try {
// Construct data
            //String phonenumbers = "9657816221";
            String data = "user=" + URLEncoder.encode(sms_user, "UTF-8");
            data += "&password=" + URLEncoder.encode(sms_pass, "UTF-8");
            data += "&message=" + URLEncoder.encode(message, "UTF-8");
            data += "&sender=" + URLEncoder.encode("AFCKST", "UTF-8");
            data += "&mobile=" + URLEncoder.encode(tempMobileNumber, "UTF-8");
            data += "&type=" + URLEncoder.encode("3", "UTF-8");
// Send data
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?" + data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
// Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1 = "";
            while ((line = rd.readLine()) != null) {
// Process line...
                sResult1 = sResult1 + line + " ";
            }
            wr.close();
            rd.close();
            return sResult1;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    public static void bindListener(FeesListener listener) {
        mListener = listener;
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Toast.makeText(getActivity(), "mail id is"+preferences.getString("sendingmailid", ""), Toast.LENGTH_SHORT).show();
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, sendingmail, username, preferences.getString("sendingmailid", ""));
                if (!preferences.getString("sendingmailid", "").equals("mohammed.raza@afcks.com")) {
                    sender.sendMail(subject, sendingmail, username, "mohammed.raza@afcks.com");
                }

            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();

            // Toast.makeText(getActivity(), "Email send", Toast.LENGTH_LONG).show();
            //  new UploadFileToServer().execute();

        }


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

    //

    private static final String[] tensNames = {
            "",
            " Ten",
            " Twenty",
            " Thirty",
            " Forty",
            " Fifty",
            " Sixty",
            " Seventy",
            " Eighty",
            " Ninety"
    };

    private static final String[] numNames = {
            "",
            " One",
            " Two",
            " Three",
            " Four",
            " Five",
            " Six",
            " Seven",
            " Eight",
            " Nine",
            " Ten",
            " Eleven",
            " Twelve",
            " Thirteen",
            " Fourteen",
            " Fifteen",
            " Sixteen",
            " Seventeen",
            " Eighteen",
            " Nineteen"
    };

    // private EnglishNumberToWords() {}

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " Hundred" + soFar;
    }


    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "Zero";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions)
                        + " Billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions)
                        + " Billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions)
                        + " Million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions)
                        + " Million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "One Thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " Thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }


    public void getCompanySelect(final String channelPartnerSelect) {

        jsonCompany = new JSONObject() {
            {
                try {
                    put("company_id", channelPartnerSelect);
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
                companyResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCOMPANYBYID, jsonCompany);
                Log.i("resp", "loginResponse" + companyResponse);


                try {
                    JSONArray callArrayList = new JSONArray(companyResponse);


                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {

                        JSONObject json_data = callArrayList.getJSONObject(i);
                        comapny_name = json_data.getString("company_name");
                        company_address = json_data.getString("address");
                        company_city = json_data.getString("city_name");
                        company_state = json_data.getString("state_name");
                        state_id = json_data.getString("state_id");
                        company_gstno = json_data.getString("gst_no");

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

                                state_id = student.getState_id();
                                company_id = student.getId();
                                comapny_name = student.getCompany_name();
                                company_address = student.getAddress();
                                company_state = student.getState_name();
                                company_city = student.getCity_name();
                                company_gstno = student.getGst_no();

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

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(getActivity());
            // pDialog.setMessage("Please wait...");
            //  pDialog.show();
            // setting progress bar to zero
            // progressBar.setProgress(0);

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //   progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            // progressBar.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
/*            Bitmap bm;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            bm = BitmapFactory.decodeFile(imageUri.get(0).getPath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            byte[] data = bos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(data, File.separator + "IMG_" + timeStamp + ".jpg");*/

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.URL_UPLOAD_RECEIPT_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        //   publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(receiptUri.get(0).getPath());

                // Adding file data to http body
                entity.addPart("uploadedfile1", new FileBody(sourceFile));
                //entity.addPart("uploadedfile1", bab);
                // Extra parameters if you want to pass to server
                // entity.addPart("website", new StringBody("www.androidhive.info"));
                //entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "Response from server: " + result);
            if (statusCode == 200) {
                // pDialog.cancel();
            }


        }

    }

    //
    private class initBranchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
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
                        //  put("user_id", "" + preferences.getInt("user_id", 0));
                        // put("branch_id", flag);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_CENTER, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerResponse);

            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                locationlist = new ArrayList<>();
                                locationlist.add(new LocationDAO("0", "Select Branch"));

                                JSONArray LeadSourceJsonObj = new JSONArray(centerResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    locationlist.add(new LocationDAO(json_data.getString("id"), json_data.getString("branch_name")));

                                }

                                jsonArray = new JSONArray(centerResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (centerResponse.compareTo("") != 0) {


                ArrayAdapter<LocationDAO> adapter = new ArrayAdapter<LocationDAO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, locationlist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                spinnerBranch.setAdapter(adapter);
                spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        LocationDAO LeadSource = (LocationDAO) parent.getSelectedItem();
                        //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                        center_id = LeadSource.getBranch_name();

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
}