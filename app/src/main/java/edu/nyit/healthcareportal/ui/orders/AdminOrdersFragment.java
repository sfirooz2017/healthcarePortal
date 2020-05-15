package edu.nyit.healthcareportal.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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
import edu.nyit.healthcareportal.adminMedsAdapter;


public class AdminOrdersFragment extends Fragment {



    private RecyclerView.LayoutManager layoutManager;


    public ArrayList<Orders> ordersList = new ArrayList<Orders>();

    GlobalData data = GlobalData.getInstance();
    RecyclerView adminRecyclerView;
    TextView searchView;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        searchView = root.findViewById(R.id.searchViewAdminOrders);

        for (int x =0 ;x<data.getUsers().size();x++)
            for (int y=0;y<data.getUsers().get(x).getOrders().size();y++){

                ordersList.add(data.getUsers().get(x).getOrders().get(y));

            }


        adminRecyclerView = root.findViewById(R.id.orderRecyclerAdmin);
        layoutManager = new LinearLayoutManager(getContext());
        adminRecyclerView.setLayoutManager(layoutManager);

        search("");

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setFocusableInTouchMode(true);
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                String search = s.toString().toLowerCase();
                search = search + "";
                search(search);

            }
        });


        return root;
    }
    private void search(final String s) {

        List<Orders> temp = new ArrayList<>();
        temp.clear();

        for (int x = 0; x < ordersList.size(); x++) {
            if (ordersList.get(x).getContains().contains(s) || ordersList.get(x).getUser().contains(s) || ordersList.get(x).getNumber().contains(s) || ordersList.get(x).getLeftCenter().contains(s)|| s.equals(""))
                temp.add(ordersList.get(x));
        }
        RecyclerView.Adapter aAdapter = new AdminOrderAdapter((ArrayList<Orders>) temp);
        adminRecyclerView.setAdapter(aAdapter);
        adminRecyclerView.setLayoutManager(layoutManager);


    }



}