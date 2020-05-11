package edu.nyit.healthcareportal;

public class Prescriptions {


    private String name;
    private String refill;
    private String dose;
    public Prescriptions()
    {}


    public Prescriptions(String name, String refill, String dose) {
        this.name = name;
        this.refill = refill;
        this.dose = dose;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }



    public String getRefill() {
        return refill;
    }

    public void setRefill(String refill) {
        this.refill = refill;
    }


}
