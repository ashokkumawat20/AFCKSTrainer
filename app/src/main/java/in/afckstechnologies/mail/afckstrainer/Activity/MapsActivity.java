package in.afckstechnologies.mail.afckstrainer.Activity;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import in.afckstechnologies.mail.afckstrainer.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double Lat;
    Double Lng;
    String address1;
    String address2;
    private Circle mCircle;
    private Marker mMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent=getIntent();
        Lat=Double.parseDouble(intent.getStringExtra("Lat"));
        Lng=Double.parseDouble(intent.getStringExtra("Lng"));
        address1="";
        address2=intent.getStringExtra("address2");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Changing map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Showing / hiding your current location
        googleMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(Lat, Lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title(address)).snippet(dateTime1);*/
       /* mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        MarkerOptions marker = new MarkerOptions().position(new LatLng(Lat, Lng)).title(address1).snippet(address2);
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(Lat, Lng)).zoom(15).build();
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        /*try {
            //test outside
            double mLatitude = Lat;
            double mLongitude = Lng;


            //test inside
            //double mLatitude = 37.7795516;
            //double mLongitude = -122.39292;


            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), 15));

            MarkerOptions options = new MarkerOptions();

            // Setting the position of the marker

            options.position(new LatLng(mLatitude, mLongitude));

            //googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            LatLng latLng = new LatLng(mLatitude, mLongitude);
            drawMarkerWithCircle(latLng);


            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    float[] distance = new float[2];

                        *//*
                        Location.distanceBetween( mMarker.getPosition().latitude, mMarker.getPosition().longitude,
                                mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
                                *//*

                    Location.distanceBetween( location.getLatitude(), location.getLongitude(),
                            mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);

                    if( distance[0] > mCircle.getRadius()  ){
                        Toast.makeText(getBaseContext(), "Outside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Inside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius() , Toast.LENGTH_LONG).show();
                    }

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }

    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 30.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMarker = mMap.addMarker(markerOptions);
    }
}
