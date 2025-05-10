package com.example.easyconnect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        List<String> allLocations = SharedDataModel.getInstance().getLocations();
        for (String mapName : allLocations) {
            addLocation(mapName);
        }

        reloadLocationList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadLocationList();
    }

    private void reloadLocationList() {
        LinearLayout linearLayout = findViewById(R.id.listOfLocations);
        linearLayout.removeAllViews(); // clear previous views

        List<String> allLocations = SharedDataModel.getInstance().getLocations();
        for (String mapName : allLocations) {
            addLocation(mapName);
        }
    }


    //dynamic adding layout to list of locations
    public void addLocation(String mapName) {
        LinearLayout linearLayout = findViewById(R.id.listOfLocations);

        Button location = new Button(this);
        location.setText(mapName);
        location.setTextSize(24);
        location.setTextColor(Color.BLACK);
        location.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F8F7F7")));

        //set layout width and height
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dpToPx(337), // Convert dp to px
                dpToPx(70)
        );
        layoutParams.setMargins(dpToPx(0), dpToPx(10), dpToPx(0), dpToPx(10));
        location.setLayoutParams(layoutParams);

        location.setBackgroundResource(R.drawable.buttonstyle);

        //on click function
        location.setOnClickListener(v -> toContact(v, location));

        if(!location.getText().equals("")){
            linearLayout.addView(location);
        }
        //adding the button the layout
    }

    //Helper Method to Convert dp to px
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    public void cancelLocation(View view){
        Log.d("DEBUG", "Cancel button clicked");
        ConstraintLayout clInput = findViewById(R.id.inputContainer);
        ScrollView svContacts = findViewById(R.id.svContacts);

        clInput.setVisibility(View.GONE);
        svContacts.setVisibility(View.VISIBLE);
    }

    public void toLocation(View view){
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    public static final String LOCATION_NAME = "com.example.easyconnect.MESSAGE";
    public void toContact(View view, Button b){
        String locationName = b.getText().toString();
        List<String> contacts = getContactsForLocation(locationName); // Retrieve the contacts for this location

        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        intent.putExtra(LOCATION_NAME, locationName);  // Pass the location name
        intent.putStringArrayListExtra("CONTACTS_LIST", new ArrayList<>(contacts));  // Pass the contacts for this location
        startActivity(intent);
    }

    private List<String> getContactsForLocation(String location) {
        // Replace this with your actual logic to get contacts for the location
        List<String> contacts = new ArrayList<>();
        // Example: contacts.add(location + " Contact 1");
        return contacts;
    }

}