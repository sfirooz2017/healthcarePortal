package edu.nyit.healthcareportal;

import java.util.List;

public class Users {
    private String role;

    private String email;
    private List<Prescriptions> prescriptions;
    private List<Orders> orders;

    public Users()
    {}


public Users(String email){
    this.email = email;
}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public List<Prescriptions> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescriptions> prescriptions) {
        this.prescriptions = prescriptions;
    }
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

}
