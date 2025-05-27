package com.example.easyconnect;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

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

        SharedDataModel.getInstance().getLocations().clear();
        SharedDataModel.getInstance().getContacts().clear();
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        dbHelper.loadAllLocationsToSharedModel();
        dbHelper.loadAllContactsToSharedModel();
        reloadContactList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadContactList(); // refresh when user comes back
    }


    private void reloadContactList() {
        LinearLayout linearLayout = findViewById(R.id.listOfContacts);
        linearLayout.removeAllViews(); // clear previous views

        List<String> allContacts = SharedDataModel.getInstance().getContacts();
        for (String contact : allContacts) {
            addDynamicButtonForContact(contact);
        }
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

    public void editContact(View view) {
        // Get the selected contact's information
        String contactName = ((Button) view).getText().toString(); // You can extract the contact name from the button text
        String[] contactDetails = contactName.split(" - "); // Split the name and number (assuming it's formatted as "name - number")

        // Get the EditText views for name and number
        EditText txtContactNameEdit = findViewById(R.id.txtContactNameEdit);
        EditText txtContactNumberEdit = findViewById(R.id.txtContactNumberEdit);

        // Set the contact's name and number in the EditText fields
        if (contactDetails.length == 2) {
            txtContactNameEdit.setText(contactDetails[0]);
            txtContactNumberEdit.setText(contactDetails[1]);
        }

        // Show the contact edit layout and hide the contact list
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

    public void toCall(View view) {
        List<String> allContacts = SharedDataModel.getInstance().getContacts();

        if (allContacts.isEmpty()) {
            // No contacts available to call
            return;
        }

        // Get a random contact
        int randomIndex = (int) (Math.random() * allContacts.size());
        String randomContact = allContacts.get(randomIndex);

        // Extract the name and number
        String[] contactDetails = randomContact.split(" - ");
        String contactName = contactDetails[0];
        String contactNumber = contactDetails[1];

        // Start the phone call
        startPhoneCall(contactNumber);
    }

    private void startPhoneCall(String phoneNumber) {
        // Check for permission to call
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return;
            }
        }

        // Create an intent to make a phone call
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(android.net.Uri.parse("tel:" + phoneNumber));

        try {
            startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Call failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ContactActivity", "Error making call", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the call
                List<String> allContacts = SharedDataModel.getInstance().getContacts();
                if (!allContacts.isEmpty()) {
                    // Get the first contact for testing (you can adjust as needed)
                    String randomContact = allContacts.get(0);
                    String[] contactDetails = randomContact.split(" - ");
                    String contactNumber = contactDetails[1];
                    startPhoneCall(contactNumber);
                }
            } else {
                // Permission denied, show an error message
                Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void toLocationActivity(View view) {
        Intent intent = new Intent(ContactActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addContact(View view) {
        EditText addContactName = findViewById(R.id.txtAddContactName);
        EditText addContactNumber = findViewById(R.id.txtAddContactNumber);

        String name = addContactName.getText().toString().trim();
        String number = addContactNumber.getText().toString().trim();

        if (!name.isEmpty() && !number.isEmpty()) {
            String contactEntry = name + " - " + number;

            // Add to SharedDataModel so it persists while app is open
            MyDatabaseHelper myDB = new MyDatabaseHelper(ContactActivity.this);
            myDB.addContact(contactEntry);

            // Then display it
            addDynamicButtonForContact(contactEntry);

            // Clear input
            addContactName.setText("");
            addContactNumber.setText("");
        }

        ConstraintLayout clContactInput = findViewById(R.id.clContactInput);
        ScrollView svContactList = findViewById(R.id.svContactList);

        clContactInput.setVisibility(View.GONE);
        svContactList.setVisibility(View.VISIBLE);
    }

}