package pvnghe.demomap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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

        // Add a marker in Sydney and move the camera
        LatLng vietnamLatLng = new LatLng(10.852980, 106.626994);

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
                .title("Ame")
                .draggable(true)
                .position(vietnamLatLng)
                .snippet("Aki");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vietnamLatLng));

        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(10.852980, 106.626994),
                        new LatLng(10.854287, 106.626383),
                        new LatLng(10.855014, 106.627885),
                        new LatLng(10.855435, 106.627906),
                        new LatLng(10.855467, 106.628925),
                        new LatLng(10.853770, 106.628668))
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(100,51, 255, 51));

        // Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);
    }
}
