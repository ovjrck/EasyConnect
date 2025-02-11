package com.example.easyconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addContact(View view) {
        ConstraintLayout clContactInput = findViewById(R.id.clContactInput);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactInput.setVisibility(View.VISIBLE);
        svContactList.setVisibility(View.GONE);
    }

    public void cancelContact(View view){
        ConstraintLayout clContactInput = findViewById(R.id.clContactInput);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactInput.setVisibility(View.GONE);
        svContactList.setVisibility(View.VISIBLE);
    }

    public void editContact(View view){
        ConstraintLayout clContactEdit = findViewById(R.id.clContactEdit);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactEdit.setVisibility(View.VISIBLE);
        svContactList.setVisibility(View.GONE);
    }

    public void cancelEdit(View view){
        ConstraintLayout clContactEdit = findViewById(R.id.clContactEdit);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactEdit.setVisibility(View.GONE);
        svContactList.setVisibility(View.VISIBLE);
    }

    public void toCall(View view){
        Intent intent = new Intent(ContactActivity.this, CallingActivity.class);
        startActivity(intent);
    }
}