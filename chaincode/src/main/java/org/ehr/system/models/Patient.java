package org.ehr.system.models;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String id;
    private String firstName;
    private String lastName;
    private String dateofbirth;
    private List<String> allergies;
    private List<Drug> prescriptions;

    public Patient() {
    }

    public Patient(String id, String firstName, String lastName, String dateofbirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofbirth = dateofbirth;
        this.prescriptions = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }


    public List<String> getAllergies() {
        return allergies;
    }

    public void addAllergy(String allergy) {
        this.allergies.add(allergy);
    }

    public List<Drug> getPrescriptions() {
        return prescriptions;
    }

    public void addPrescription(Drug drug) {
        this.prescriptions.add(drug);
    }
}
