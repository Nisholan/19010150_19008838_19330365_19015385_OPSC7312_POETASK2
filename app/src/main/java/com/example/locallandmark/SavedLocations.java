package com.example.locallandmark;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SavedLocations extends AppCompatActivity {

    ListView lv_savedLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_locations);

        lv_savedLocations = findViewById(R.id.lv_wayPoints);

        Map map = (Map)getApplicationContext();
        List<Location> savedLocations = map.getMyLocations();

        lv_savedLocations.setAdapter(new ArrayAdapter<Location>( this, android.R.layout.simple_list_item_1, savedLocations));
    }
}