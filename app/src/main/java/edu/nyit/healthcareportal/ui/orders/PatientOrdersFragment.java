package edu.nyit.healthcareportal.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;

public class PatientOrdersFragment extends Fragment {

TextView textView;
GlobalData data = GlobalData.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_orders, container, false);


        String s = "";

        //check if data loads
            for(int x = 0; x< data.getUsers().get(0).getOrders().size(); x++)
            {
                s = s + data.getUsers().get(x).getPrescriptions().get(x).getName();
            }
        textView = root.findViewById(R.id.medsView);
        textView.setText(s);

        //user index is always 0
        //create method to calculate transit dates-- use data.getDate() to get current date



        return root;
    }
    public void makeAnOrder()
    {
        String obj = "fruit";
        //to make a new order, you must increment the order number so each number has a unique oder number. then pass new order number when making new order
        String num = String.valueOf(data.getOrderNum() + 1);
        new FirebaseDatabaseHelper("orderNumber/").updateData(num);
        Orders order = new Orders("06/01/20", obj, "06/02/20", "06/03/20", num);
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(0).getEmail() + "/orders").addOrder(order, new FirebaseDatabaseHelper.DataStatus() {

            @Override
            public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {
            }

            @Override
            public void OrderIsLoaded(List<Orders> orders, List<String> keys) {
            }

            @Override
            public void UserIsLoaded(List<Users> users, List<String> keys) {
            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void OrderNumberLoaded(int num) {
            }

            @Override
            public void DataIsChecked(boolean check) {
            }
        });
    }


}
