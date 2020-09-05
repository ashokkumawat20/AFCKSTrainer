package in.afckstechnologies.mail.afckstrainer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import in.afckstechnologies.mail.afckstrainer.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewImage extends AppCompatActivity {
    // Declare Variable
    TextView text;
    ImageView imageview;
    PhotoViewAttacher photoViewAttacher ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        // Retrieve data from MainActivity on GridView item click
        Intent i = getIntent();

        // Get the position
        int position = i.getExtras().getInt("position");

        // Get String arrays FilePathStrings
        String[] filepath = i.getStringArrayExtra("filepath");

        // Get String arrays FileNameStrings
        String[] filename = i.getStringArrayExtra("filename");

        // Locate the TextView in view_image.xml
        text = (TextView) findViewById(R.id.imagetext);

        // Load the text into the TextView followed by the position
        text.setText(filename[position]);

        // Locate the ImageView in view_image.xml
        imageview = (ImageView) findViewById(R.id.full_image_view);

        // Decode the filepath with BitmapFactory followed by the position
       // Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);
        //

        BitmapFactory.Options options = new BitmapFactory.Options();
        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;
        final Bitmap bitmap = BitmapFactory.decodeFile(filepath[position], options);
        //
        // Set the decoded bitmap into ImageView
        imageview.setImageBitmap(bitmap);
        photoViewAttacher = new PhotoViewAttacher(imageview);
        photoViewAttacher.update();
    }
}
