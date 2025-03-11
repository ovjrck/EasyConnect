package com.example.easyconnect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

        String locName = getIntent().getStringExtra(MainActivity.LOCATION_NAME);
        TextView locationName = findViewById(R.id.lblLocationName);
        locationName.setText(locName);
    }

    //dynamic adding layout to list of locations
    public void addDynamicButtonForContact(String contactName) {
        LinearLayout listOfContacts = findViewById(R.id.listOfContacts);

        Button contact = new Button(this);
        contact.setText(contactName);
        contact.setTextSize(24);
        contact.setTextColor(Color.BLACK);
        contact.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F8F7F7")));

        //set layout width and height
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dpToPx(337), // Convert dp to px
                dpToPx(70)
        );
        layoutParams.setMargins(dpToPx(0), dpToPx(10), dpToPx(0), dpToPx(10));
        contact.setLayoutParams(layoutParams);

        contact.setBackgroundResource(R.drawable.buttonstyle);

        //on click function
        contact.setOnClickListener(v -> editContact(v));

        if(!contact.getText().equals("")){
            listOfContacts.addView(contact);
        }
        //adding the button the layout
    }

    //Helper Method to Convert dp to px
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    public void showContactInput(View view) {
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

    public void toLocationActivity(View view) {
        Intent intent = new Intent(ContactActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addContact(View view){
        EditText addContactName = findViewById(R.id.txtAddContactName);
        EditText addContactNumber = findViewById(R.id.txtAddContactNumber);

        addDynamicButtonForContact(addContactName.getText().toString());

        ConstraintLayout clContactInput = findViewById(R.id.clContactInput);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactInput.setVisibility(View.GONE);
        svContactList.setVisibility(View.VISIBLE);
    }
}