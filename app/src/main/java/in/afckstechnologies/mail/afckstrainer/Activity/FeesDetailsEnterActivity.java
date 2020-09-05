package in.afckstechnologies.mail.afckstrainer.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

public class FeesDetailsEnterActivity extends AppCompatActivity {
    EditText userNameEdtTxt,mobileNo,datePickerCal,amountEdtTxt,feemodeEdtTxt,receivedByEdtTxt,noteyEdtTxt;
    Button placeBtn;
    boolean click = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details_enter);
        userNameEdtTxt=(EditText)findViewById(R.id.userNameEdtTxt);
        mobileNo=(EditText)findViewById(R.id.mobileNo);
        datePickerCal=(EditText)findViewById(R.id.datePickerCal);
        amountEdtTxt=(EditText)findViewById(R.id.amountEdtTxt);
        feemodeEdtTxt=(EditText)findViewById(R.id.feemodeEdtTxt);
        receivedByEdtTxt=(EditText)findViewById(R.id.receivedByEdtTxt);
        noteyEdtTxt=(EditText)findViewById(R.id.noteyEdtTxt);
        placeBtn=(Button)findViewById(R.id.placeBtn);
        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name = userNameEdtTxt.getText().toString();
                String phoneno = mobileNo.getText().toString();
                String fees = amountEdtTxt.getText().toString();

                String feemode = feemodeEdtTxt.getText().toString().trim();
                String reciveBy = receivedByEdtTxt.getText().toString();
                String note = noteyEdtTxt.getText().toString();

                if (validate(name ,phoneno,fees, feemode, reciveBy)) {

                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            //sendData(name, phoneno, emailid, lastname, gender);
                            click = false;
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }


                } else {

                }
            }
        });


    }

    private boolean validate(String name, String phoneno, String fees,  String feemode, String reciveBy) {
        boolean isValidate = false;
        if (name.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter user name", Toast.LENGTH_LONG).show();
        }
        else if (phoneno.trim().compareTo("") == 0 || phoneno.length() != 10) {
            Toast.makeText(getApplicationContext(), "Please enter a 10 digit valid Mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;
        }
        else if (fees.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter  fees amount", Toast.LENGTH_LONG).show();
            isValidate = false;

        }
        else if (feemode.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter fees mode ", Toast.LENGTH_LONG).show();
            isValidate = false;

        }
        else if (reciveBy.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter  Received By", Toast.LENGTH_LONG).show();
            isValidate = false;

        }
        else {
            isValidate = true;
        }
        return isValidate;
    }
}
