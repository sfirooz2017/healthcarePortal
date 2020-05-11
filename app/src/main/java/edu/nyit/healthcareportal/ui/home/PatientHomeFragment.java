package edu.nyit.healthcareportal.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;

public class PatientHomeFragment extends Fragment {
    GlobalData data = GlobalData.getInstance();





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final TextView orderfield;
        Button placeorder;
        orderfield = root.findViewById(R.id.neworder);
        placeorder = root.findViewById(R.id.orderplace);



        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order = orderfield.getText().toString().trim();
                if (TextUtils.isEmpty(order)) {
                    orderfield.setError("Order is required");
                    return;}
                else{

                    makeAnOrder(order);



                }





            }
        });



        Toast.makeText(getContext(), "Order has been placed!", Toast.LENGTH_SHORT).show();


        return root;
    }

        public String UpdateDay(int day, int month, int inc){

            String m = "";
            String d ="";
            if(day + inc> 30){

                day = inc;
                d = day + "";
                m = month + "";
                if(month<12 && month>8){
                    month++;
                    m = month + "";


                }
                else if(month ==12){
                   m = "01";
                }
                else{
                    month = month++;
                    m = "0" + month;
            }



            }else if(month<10){
                day = day + inc;
                d = day + "";
                m = "0"+month;



            }
            return (d + "/" + m);





        }



        public void makeAnOrder(String o)
        {
            String date = data.getDate();
            String[] temperory = date.split("/");
            int day = Integer.parseInt(temperory[0]);
            int month = Integer.parseInt(temperory[1]);
            int year = Integer.parseInt(temperory[2]);
            String leftcenter = UpdateDay(day, month, 1)+"/"+temperory[2];
            String inTransit = UpdateDay(day, month, 2)+"/"+ temperory[2];
            String arrived = UpdateDay(day, month, 3) +"/"+ temperory[2];





            //to make a new order, you must increment the order number so each number has a unique oder number. then pass new order number when making new order
            String num = String.valueOf(data.getOrderNum() + 1);
            new FirebaseDatabaseHelper("orderNumber/").updateData(num);
            Orders order = new Orders(arrived, o, leftcenter, inTransit, num);
            GlobalData.getInstance().getUsers().get(0).getOrders().add(order);
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
