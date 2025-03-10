package com.example.easyconnect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

        Intent intent = getIntent();
        String mapName = intent.getStringExtra(LocationActivity.LOCATION_NAME);

        addLocation(mapName);
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
        //location.setOnClickListener(v -> toLocation(v));

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

}