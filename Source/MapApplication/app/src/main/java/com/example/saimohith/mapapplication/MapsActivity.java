package com.example.saimohith.mapapplication;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DismissOverlayView;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class MapsActivity extends WearableActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    private static final LatLng CurrentLocation = new LatLng(39.033733, -94.576204);


    private DismissOverlayView mDismiss;

    /**
     * @see #onMapReady(com.google.android.gms.maps.GoogleMap)
     */
    private GoogleMap mGMap;

    private MapFragment mMapFrag;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_maps);

        setAmbientEnabled();
        final FrameLayout topFrame = (FrameLayout) findViewById(R.id.root_container);
        final FrameLayout mapFrame = (FrameLayout) findViewById(R.id.map_container);

        topFrame.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                insets = topFrame.onApplyWindowInsets(insets);

                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mapFrame.getLayoutParams();

                params.setMargins(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                mapFrame.setLayoutParams(params);

                return insets;
            }
        });

        mDismiss = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismiss.setIntroText(R.string.intro_text);
        mDismiss.showIntroIfNecessary();
        mMapFrag = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMapFrag.getMapAsync(this);

    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        mMapFrag.onEnterAmbient(ambientDetails);
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        mMapFrag.onExitAmbient();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGMap = googleMap;

        mGMap.setOnMapLongClickListener(this);

        mGMap.addMarker(new MarkerOptions().position(CurrentLocation)
                .title("Your Current Location"));

        mGMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 13));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mDismiss.show();
    }
}
