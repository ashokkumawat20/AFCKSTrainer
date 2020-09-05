package in.afckstechnologies.mail.afckstrainer.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
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

import in.afckstechnologies.mail.afckstrainer.Activity.StudentList;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CompanyDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AndroidMultiPartEntity;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


public class StudentTransferFeesEntryView extends DialogFragment {
    // LogCat tag
    private static final String TAG = StudentTransferFeesEntryView.class.getSimpleName();
    AutoCompleteTextView autoCompleteTextViewBatch;
    Button placeBtn, proposalBtn;
    private TextView nameEdtTxt;
    private EditText trfeesEdtTxt, ttrfeesEdtTxt;
    private EditText remarksEdtTxt;
    private Spinner spnrUserType;
    Context context;
    SharedPreferences preferences;
    Editor prefEditor;
    String loginResponse = "", cashBackAmountResponse = "";
    JSONObject jsonObj, jsonObjCash, jsonCompany, jsonSchedule;
    Boolean status;
    String msg = "";
    boolean click = true;
    int count = 0;
    View registerView;
    String ReceivedBy = "";
    private static FeesListener mListener;
    private static final String username = "info@afcks.com";
    private static String password = "at!@#123";
    GMailSender sender;
    String emailid = "";
    String subject = "Receipt";
    String message = "";
    String fileName = "";
    String pathName = "";
    private BaseFont bfBold;
    RadioGroup radioGroup;
    int pos;
    String accountType = "Non Corporate";
    String strName = "";
    String code = "";
    LinearLayout companyLayout;
    ImageView add_comapny;
    AutoCompleteTextView SearchCompany;
    String newTextCompany = "";
    String companyResponse = "";
    public List<CompanyDAO> companyArrayList;
    public ArrayAdapter<CompanyDAO> aAdapter;
    public ArrayAdapter<BatchDAO> bAdapter;

    CompanyDAO companyDAO;
    String state_id = "", company_id = "";
    Double feesAmount = 0.0;
    Double cashbackAmount = 0.0;
    TextView Totalfees;
    Double IGST = 0.0, SGST = 0.0, CGST = 0.0, Rate = 0.0, MOS = 0.0;

    String pdfAmount = "";
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
    String newTextBatch;
    static String sms_user = "";
    static String sms_pass = "";
    String flag = "", studentResponse = "", batch_id = "", tfees = "", ttfees;
    public List<BatchDAO> batchArrayList;
    BatchDAO batchDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_student_transfer_fees, null);

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
        sender = new GMailSender(username, password);
        nameEdtTxt = (TextView) registerView.findViewById(R.id.nameEdtTxt);
        trfeesEdtTxt = (EditText) registerView.findViewById(R.id.trfeesEdtTxt);
        ttrfeesEdtTxt = (EditText) registerView.findViewById(R.id.ttrfeesEdtTxt);
        remarksEdtTxt = (EditText) registerView.findViewById(R.id.remarksEdtTxt);
        companyLayout = (LinearLayout) registerView.findViewById(R.id.companyLayout);
        add_comapny = (ImageView) registerView.findViewById(R.id.add_comapny);
        availCashback = (Button) registerView.findViewById(R.id.availCashback);
        proposalBtn = (Button) registerView.findViewById(R.id.proposalBtn);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();
        batchArrayList = new ArrayList<BatchDAO>();
        sms_user = preferences.getString("sms_username", "");
        sms_pass = preferences.getString("sms_password", "");
        cashbackamount = preferences.getString("cashBackAmount", "");
        if (!cashbackamount.equals("")) {
            //  feesEdtTxt.setText(cashbackamount);
            availCashback.setVisibility(View.VISIBLE);
            availCashback.append("Rs. " + cashbackamount);
        }
        if (preferences.getString("trainer_user_id", "").equals("AK")) {
            proposalBtn.setVisibility(View.VISIBLE);
        }
        if (preferences.getString("trainer_user_id", "").equals("RS")) {
            proposalBtn.setVisibility(View.VISIBLE);
        }
        nameEdtTxt.setText(preferences.getString("student_name", ""));
        emailid = preferences.getString("mail_id", "");
        corporate_id = preferences.getString("corporate", "");
        expiry_date = preferences.getString("expiry_date", "");
        company_id = preferences.getString("company_id", "");
        batch_fees = Integer.parseInt(preferences.getString("batch_fees", ""));
        getCompanySelect(company_id);

        companyArrayList = new ArrayList<CompanyDAO>();
        Totalfees = (TextView) registerView.findViewById(R.id.Totalfees);
        autoCompleteTextViewBatch = (AutoCompleteTextView) registerView.findViewById(R.id.SearchBatch);
        autoCompleteTextViewBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextBatch = s.toString();
                getBatchSelect(newTextBatch);


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
        if (preferences.getString("send_sms", "").equals("sendsms")) {
            not.setChecked(true);
            prefEditor.putString("current_send_sms", "sendsms");
            prefEditor.commit();
        } else if (preferences.getString("send_sms", "").equals("notsendsms")) {
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
        not.setOnClickListener(new OnClickListener() {

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
        notm.setOnClickListener(new OnClickListener() {

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

        add_comapny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                CompanyDetailsEntryView companyDetailsEntryView = new CompanyDetailsEntryView();
                companyDetailsEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "companyDetailsEntryView");


            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.add("Select Fee Type");
        dataAdapter.add("Cash");
        dataAdapter.add("Refund");
        dataAdapter.add("Cheque");
        dataAdapter.add("EFT");
        dataAdapter.add("Old Student");
        dataAdapter.add("Discount");
        dataAdapter.add("Cash Deposit");
        dataAdapter.add("Paytm AFCKS");
        dataAdapter.add("Paytm Self");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrUserType = (Spinner) registerView.findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);


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
                tfees = trfeesEdtTxt.getText().toString();
                ttfees = ttrfeesEdtTxt.getText().toString();
                String remarks = remarksEdtTxt.getText().toString();
                String spinnerSelectedFeeStatus = "";
                //  String spinnerSelected = spnrUserType.getSelectedItem().toString();

                // Toast.makeText(getActivity(), accountType,Toast.LENGTH_SHORT).show();
                if (AppStatus.getInstance(context).isOnline()) {
                    if (validate(batch_id, tfees, ttfees, remarks)) {
                        // dismiss();
                        if (Integer.parseInt(tfees) >= Integer.parseInt(ttfees)) {
                            if (click) {
                                //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                sendData(name, spinnerSelectedFeeStatus, remarks);
                                click = false;
                            } else {
                                Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Do not allow transfer amount more then total amount", Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }

            }
        });

        proposalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdtTxt.getText().toString();
                tfees = trfeesEdtTxt.getText().toString();
                ttfees = ttrfeesEdtTxt.getText().toString();
                String remarks = remarksEdtTxt.getText().toString();
                String spinnerSelectedFeeStatus = "";
                //  String spinnerSelected = spnrUserType.getSelectedItem().toString();

                // Toast.makeText(getActivity(), accountType,Toast.LENGTH_SHORT).show();
                if (AppStatus.getInstance(context).isOnline()) {
                    if (validate(batch_id, tfees, ttfees, remarks)) {
                        // dismiss();
                        if (Integer.parseInt(tfees) >= Integer.parseInt(ttfees)) {
                            if (click) {
                              //  Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                                if (batch_id.equals("RSAE099")) {
                                    batch_id = " temporary Batch *" + batch_id + "*.";
                                } else {
                                    batch_id = " Batch *" + batch_id + "*.";
                                }
                                String sms = "Hi " + preferences.getString("trainer_name", "") + " ," + System.getProperty("line.separator") + System.getProperty("line.separator")
                                        + "*Proposal for transfer*" + System.getProperty("line.separator")+ System.getProperty("line.separator")
                                        + "*" + preferences.getString("student_name", "") + "*" + " need to be transferred from your Batch no " + "*" + preferences.getString("out_batch", "") + "*" + " to " + batch_id + System.getProperty("line.separator") + System.getProperty("line.separator")
                                        + "You have received *Rs " + tfees + "*" + " and I proposal transfer of *Rs " + ttfees + "* to other Batch, Reason: *" + remarks + "*.";

                                PackageManager packageManager = context.getPackageManager();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                try {
                                    String url = "https://api.whatsapp.com/send?phone=" + "91" + preferences.getString("trainer_mobile_no", "") + "&text=" + URLEncoder.encode(sms, "UTF-8");
                                    i.setPackage("com.whatsapp");
                                    i.setData(Uri.parse(url));
                                    if (i.resolveActivity(packageManager) != null) {
                                        context.startActivity(i);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                click = false;
                            } else {
                                Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Do not allow transfer amount more then total amount", Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return registerView;
    }


    public boolean validate(String batch_id, String tfees, String ttfees, String remarks) {
        boolean isValidate = false;
        if (batch_id.equals("")) {
            Toast.makeText(getActivity(), "Please select Batch.", Toast.LENGTH_LONG).show();
        } else if (tfees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Total Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (ttfees.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Transfer Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (remarks.equals("")) {
            Toast.makeText(getActivity(), "Please enter  Remarks", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }


    public void sendData(final String name, final String spinnerSelectedFeeStatus, final String remarks) {


        jsonObj = new JSONObject() {
            {
                try {
                    put("OutBatch", preferences.getString("out_batch", ""));
                    put("InBatch", batch_id);
                    put("TotalRec", tfees);
                    put("TransferAmt", ttfees);
                    put("TUserID", preferences.getString("user_id", ""));
                    put("Note", remarks);
                    put("MobileNo", preferences.getString("mobile_no", ""));
                    put("PayStatus", spinnerSelectedFeeStatus);
                    put("UserName", preferences.getString("trainer_name", ""));
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
                loginResponse = serviceAccess.SendHttpPost(Config.URL_ADDSTUDENTINBATCHTRANSFERFROMOLDBATCH, jsonObj);
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
                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
            new MyAsyncClass().execute();
        } else {
            new UploadFileToServer().execute();
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
            new MyAsyncClass().execute();
        } else {
            new UploadFileToServer().execute();
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
            new MyAsyncClass().execute();
        } else {
            new UploadFileToServer().execute();
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
            new MyAsyncClass().execute();
        } else {
            new UploadFileToServer().execute();
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
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, message, username, emailid, fileName);


            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();

            // Toast.makeText(getActivity(), "Email send", Toast.LENGTH_LONG).show();
            new UploadFileToServer().execute();

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

    private class updateAvailCashback extends AsyncTask<Void, Void, Void> {
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
                        put("user_id", preferences.getString("st_id_cashback", ""));
                        put("id", preferences.getString("cashBackid", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            availCashbackResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEUSERCASHBACKDETAILS, jsonLeadObj);
            Log.i("resp", "availCashbackResponse" + availCashbackResponse);


            if (availCashbackResponse.compareTo("") != 0) {
                if (isJSONValid(availCashbackResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(availCashbackResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonarray = new JSONArray(availCashbackResponse);

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
                prefEditor.putString("cashBackAmount", "");
                prefEditor.putString("availcashbackamount" + preferences.getString("mobile_no", ""), cashbackamount);
                prefEditor.commit();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                availCashback.setVisibility(View.GONE);
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

    public void getBatchSelect(final String channelPartnerSelect) {
        String value = "";
        String value1 = "";
        if (channelPartnerSelect.startsWith("$")) {
            value1 = "1";
            value = channelPartnerSelect.substring(1);
        } else {
            value1 = "2";
            value = channelPartnerSelect;

        }

        final String finalValue = value;
        flag = value1;
        jsonSchedule = new JSONObject() {
            {
                try {
                    put("Prefixtext", finalValue);

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
                String baseURL = "http://sales.pibm.net:86/service.svc/CallListService/GetChannelPartner";
                Log.i("json", "json" + jsonSchedule);
                //SEND RESPONSE
                // studentResponse = serviceAccess.SendHttpPost(baseURL, jsonSchedule);
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTTRANSFERBATCHTRAINERBYPREFIX, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentResponse);
                    batchArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        batchDAO = new BatchDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        batchArrayList.add(new BatchDAO(json_data.getString("id"), json_data.getString("course_id"), json_data.getString("branch_id"), json_data.getString("batch_code"), json_data.getString("start_date"), json_data.getString("timings"), json_data.getString("Notes"), json_data.getString("frequency"), json_data.getString("fees"), json_data.getString("duration"), json_data.getString("course_name"), json_data.getString("branch_name"), json_data.getString("batchtype"), json_data.getString("completion_status"), json_data.getString("batch_end_date"), json_data.getString("email_id"), json_data.getString("mobile_no"), json_data.getString("faculty_Name")));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        bAdapter = new ArrayAdapter<BatchDAO>(context, R.layout.item, batchArrayList);
                        autoCompleteTextViewBatch.setAdapter(bAdapter);

                        autoCompleteTextViewBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                BatchDAO student = (BatchDAO) parent.getAdapter().getItem(i);
                                String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", student.getStart_date());
                                //  Toast.makeText(getApplicationContext(), "Source ID: " +date_after+"   "+ LeadSource.getId() + ",  Source Name : " + LeadSource.getBatch_code(), Toast.LENGTH_SHORT).show();

                                batch_id = student.getId();
                                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            }
                        });
                        bAdapter.notifyDataSetChanged();

                    }
                });


            }
        });

        objectThread.start();

    }

}