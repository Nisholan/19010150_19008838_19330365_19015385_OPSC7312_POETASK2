package com.example.locallandmark;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class Map extends Application {

    private static Map singleton;
    private List<Location> myLocations;

    public List<Location> getMyLocations() {
        return myLocations;
    }

    public void setMyLocations(List<Location> myLocations) {
        this.myLocations = myLocations;
    }

    public Map getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();
    }
}
