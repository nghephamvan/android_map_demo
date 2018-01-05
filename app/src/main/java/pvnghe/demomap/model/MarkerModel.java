package pvnghe.demomap.model;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by pvnghe on 1/5/18.
 */

public class MarkerModel {

    private Marker marker;
    private boolean isPoint;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public void setPoint(boolean point) {
        isPoint = point;
    }

    public MarkerModel() {

    }

    public MarkerModel(Marker marker, boolean isPoint) {
        this.marker = marker;
        this.isPoint = isPoint;
    }
}
