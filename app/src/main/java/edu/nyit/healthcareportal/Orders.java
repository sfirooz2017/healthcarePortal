package edu.nyit.healthcareportal;

public class Orders
{
        private String arrived;
        private String contains;
        private String leftCenter;
        private String inTransit;
        private String number;

    public Orders(String arrived, String contains, String leftCenter, String inTransit, String number) {
        this.arrived = arrived;
        this.contains = contains;
        this.leftCenter = leftCenter;
        this.inTransit = inTransit;
        this.number = number;
    }
    public Orders()
    {}

    public String getArrived() {
        return arrived;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getLeftCenter() {
        return leftCenter;
    }

    public void setLeftCenter(String leftCenter) {
        this.leftCenter = leftCenter;
    }

    public String getInTransit() {
        return inTransit;
    }

    public void setInTransit(String inTransit) {
        this.inTransit = inTransit;
    }

    public String getNumber() {return number;}

    public void setNumber(String number){this.number = number;}




}
