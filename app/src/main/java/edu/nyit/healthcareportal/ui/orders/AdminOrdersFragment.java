package edu.nyit.healthcareportal.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.nyit.healthcareportal.AdminOrderAdapter;
import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;


public class AdminOrdersFragment extends Fragment {


    private RecyclerView.Adapter AdminmAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public ArrayList<Orders> ordersList = new ArrayList<Orders>();

    GlobalData data = GlobalData.getInstance();
    TextView textView;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        textView = root.findViewById(R.id.adminOrdersView);

     for (int x =0 ;x<data.getUsers().size();x++)
    for (int y=0;y<data.getUsers().get(x).getOrders().size();y++){

        ordersList.add(data.getUsers().get(x).getOrders().get(y));

    }

        RecyclerView adminrecyclerView = root.findViewById(R.id.orderRecyclerAdmin);

        layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.Adapter adminmAdapter = new AdminOrderAdapter((ordersList));
        adminrecyclerView.setAdapter(adminmAdapter);
        adminrecyclerView.setLayoutManager(layoutManager);





/**
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





} // end of class



/// end  of paste

**/



//change refill of prescription
        int index; //index of user in data.getUsers array
        index = 1;

        String orderNumber = "1234"; //order to be updated
        String update = "06/07/20"; //new value
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(index).getEmail() + "/orders/" + orderNumber + "/arrived").updateData(update);



        return root;
   }


}