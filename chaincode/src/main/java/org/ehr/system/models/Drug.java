package org.ehr.system.models;

public class Drug {
    private String drugName;
    private String dosage;

    public Drug(){}

    public Drug(String drugName, String dosage) {
        this.drugName = drugName;
        this.dosage = dosage;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
