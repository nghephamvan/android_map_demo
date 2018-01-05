package pvnghe.demomap;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYGON_BETA = Arrays.asList(DOT, GAP, DASH, GAP);

    private GoogleMap mMap;
    private PolygonOptions polygonOptions;
    private Polygon polygon;
    private Polyline polyline;
    private ArrayList<LatLng> fieldPoligons;
    private Button btn_draw_poligon;
    private ImageView iv_current_position;
    private SupportMapFragment mapFragment;

    private boolean isDrawField = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        fieldPoligons = new ArrayList<LatLng>();

        btn_draw_poligon = findViewById(R.id.btn_draw_poligon);
        iv_current_position = findViewById(R.id.iv_current_position);

        btn_draw_poligon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_draw_poligon.getText().toString().equals(getString(R.string.done))) {
                    isDrawField = false;
                    btn_draw_poligon.setText(R.string.draw_poligon);
                } else {
                    isDrawField = true;
                    btn_draw_poligon.setText(R.string.done);
                }
            }
        });

        iv_current_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng vietnamLatLng = new LatLng(10.852980, 106.626994);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(vietnamLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vietnamLatLng, 18));
            }
        });

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
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng currentLaLatLng) {
                if (currentLaLatLng != null && isDrawField) {
                    createFieldPoint(currentLaLatLng);
                }
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng vietnamLatLng = new LatLng(10.852980, 106.626994);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vietnamLatLng));

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
                .title("Green field")
                .anchor(0.5f, 0.5f)
                .draggable(true)
                .position(vietnamLatLng)
                .snippet("Author: Ame");
        mMap.addMarker(markerOptions);


        /*// Instantiates a new Polygon object and adds points to define a rectangle
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
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);*/
    }

    private void createFieldPoint(LatLng currentLaLatLng) {
        if (fieldPoligons == null) {
            fieldPoligons = new ArrayList<LatLng>();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_point))
                .draggable(true)
                .anchor(0.5f, 0.5f)
                .position(currentLaLatLng);

        fieldPoligons.add(currentLaLatLng);
        if (fieldPoligons.size() > 1) {
            // get 2 last Latlng
            int oldPosition = fieldPoligons.size() - 2;
            LatLng startPoint = fieldPoligons.get(oldPosition);
            LatLng endPoint = fieldPoligons.get(fieldPoligons.size() - 1);

            // create marker middle of 2 points
            createFieldMidPoint(startPoint, endPoint);
            if (polyline == null) {
                polyline = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(startPoint, endPoint)
                        .color(Color.GREEN)
                        .geodesic(true));
            }

            if (fieldPoligons.size() > 2) {
                createFieldMidPoint(fieldPoligons.get(0), fieldPoligons.get(fieldPoligons.size() - 1));

                // draw polygon map
                createFieldArea(fieldPoligons);

                if (polyline != null) {
                    // Clear all lines
                    polyline.remove();
                }
            }
        }

        mMap.addMarker(markerOptions);
    }

    private void createFieldArea(ArrayList<LatLng> fieldPoligons) {
        // Instantiates a new Polygon object and adds points to define a rectangle
        if (polygon == null) {
            polygon = mMap.addPolygon(new PolygonOptions()
                    .addAll(fieldPoligons)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(100, 51, 255, 51)));
        } else {
            polygon.remove();
            polygon = mMap.addPolygon(new PolygonOptions()
                    .addAll(fieldPoligons)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(100, 51, 255, 51)));
        }

        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
    }

    private void createFieldMidPoint(LatLng startPoint, LatLng endPoint) {
        LatLng midPoint = getMiddleLatLng(startPoint, endPoint);

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mid_point))
                .draggable(true)
                .anchor(0.5f, 0.5f)
                .position(midPoint);

        Marker marker = mMap.addMarker(markerOptions);
    }

    @NonNull
    private LatLng getMiddleLatLng(LatLng startPoint, LatLng endPoint) {
        double midLat = (startPoint.latitude + endPoint.latitude) / 2;
        double midlon = (startPoint.longitude + endPoint.longitude) / 2;
        return new LatLng(midLat, midlon);
    }
}
