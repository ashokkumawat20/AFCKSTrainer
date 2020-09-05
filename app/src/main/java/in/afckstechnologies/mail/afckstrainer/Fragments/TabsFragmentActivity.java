package in.afckstechnologies.mail.afckstrainer.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstrainer.Activity.AFCKSTrainerDashBoardActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.Activity_DayPrefrence_Display;
import in.afckstechnologies.mail.afckstrainer.Activity.Activity_Location_Details;
import in.afckstechnologies.mail.afckstrainer.Activity.TemplateDisplayActivity;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.CommentsDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.MultipleCommentAddView;

public class TabsFragmentActivity extends FragmentActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    LinearLayout takeChangeHome, takeChangedayprefrence, takeChangeLocation, takeTemplate, takeCreateComment, takeBookSeat;
    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_fragment);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        takeCreateComment = (LinearLayout) findViewById(R.id.takeCreateComment);
        takeChangeHome = (LinearLayout) findViewById(R.id.takeChangeHome);
        takeChangeLocation = (LinearLayout) findViewById(R.id.takeChangeLocation);
        takeChangedayprefrence = (LinearLayout) findViewById(R.id.takeChangedayprefrence);
        takeTemplate = (LinearLayout) findViewById(R.id.takeTemplate);
        takeBookSeat = (LinearLayout) findViewById(R.id.takeBookSeat);
        takeBookSeat = (LinearLayout) findViewById(R.id.takeBookSeat);
        takeBookSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    //BookSeatView bookSeatView = new BookSeatView();
                    //bookSeatView.show(fragmentActivity.getSupportFragmentManager(), "registrationView");
                } else {

                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }

            }
        });

        takeCreateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MultipleCommentAddView commentAddView = new MultipleCommentAddView();
                commentAddView.show(getSupportFragmentManager(), "commentAddView");

            }
        });

        takeCreateComment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                prefEditor.putString("da", preferences.getString("st_first_name", "") + " " + preferences.getString("st_last_name", ""));
                prefEditor.commit();
                CommentsDetailsView commentAddView = new CommentsDetailsView();
                commentAddView.show(getSupportFragmentManager(), "commentAddView");
                return true;
            }
        });
        takeChangedayprefrence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    Intent intent = new Intent(TabsFragmentActivity.this, Activity_DayPrefrence_Display.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });


        takeChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    Intent intent = new Intent(TabsFragmentActivity.this, Activity_Location_Details.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });

        takeTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!preferences.getString("user_id", "").equals("")) {
                    Intent intent = new Intent(TabsFragmentActivity.this, TemplateDisplayActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }
            }
        });

        takeChangeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    finish();
                    Intent intent = new Intent(TabsFragmentActivity.this, AFCKSTrainerDashBoardActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Entry Level Courses", null),
                Fragment_Enter_Level_Courses.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Specialization Courses", null),
                Fragment_Specialization_Courses.class, null);
    }
}
