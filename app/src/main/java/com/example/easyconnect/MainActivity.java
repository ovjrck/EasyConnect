package com.example.easyconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    }

    public void addLocation(View view) {
        ConstraintLayout clInput = findViewById(R.id.clInput);
        ScrollView svContacts = findViewById(R.id.svContacts);

        clInput.setVisibility(View.VISIBLE);
        svContacts.setVisibility(View.GONE);
    }

    public void cancelLocation(View view){
        Log.d("DEBUG", "Cancel button clicked");
        ConstraintLayout clInput = findViewById(R.id.clInput);
        ScrollView svContacts = findViewById(R.id.svContacts);

        clInput.setVisibility(View.GONE);
        svContacts.setVisibility(View.VISIBLE);
    }

    public void toLocation(View view){
        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(intent);
    }
}