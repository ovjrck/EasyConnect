package com.example.easyconnect;

import java.util.ArrayList;
import java.util.List;

public class SharedDataModel {
    private static SharedDataModel instance;

    private final List<String> locations = new ArrayList<>();
    private final List<String> contacts = new ArrayList<>();

    private SharedDataModel() { }

    public static SharedDataModel getInstance() {
        if (instance == null) {
            instance = new SharedDataModel();
        }
        return instance;
    }

    // Locations
    public void addLocation(String location) {
        locations.add(location);
    }

    public List<String> getLocations() {
        return locations;
    }

    // Contacts (optional for future use)
    public void addContact(String contact) {
        contacts.add(contact);
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void clearAllData() {
        locations.clear();
        contacts.clear();
    }
}
