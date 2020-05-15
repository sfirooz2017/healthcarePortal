package edu.nyit.healthcareportal.ui.orders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.nyit.healthcareportal.AdminOrderAdapter;
import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;

public class PatientOrdersFragment extends Fragment {
    private RecyclerView PatientrecyclerView;
    private RecyclerView.Adapter PatientmAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView searchView;
    GlobalData data = GlobalData.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_orders, container, false);


        searchView = root.findViewById(R.id.searchViewPatientOrders);
        PatientrecyclerView = root.findViewById(R.id.orderRecyclerPatient);
        layoutManager = new LinearLayoutManager(getContext());

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

        for (int x = 0; x < data.getUsers().get(0).getOrders().size(); x++) {
            if (data.getUsers().get(0).getOrders().get(x).getContains().contains(s) || data.getUsers().get(0).getOrders().get(x).getNumber().contains(s) || data.getUsers().get(0).getOrders().get(x).getLeftCenter().contains(s)|| s.equals(""))
                temp.add(data.getUsers().get(0).getOrders().get(x));
        }
        PatientmAdapter = new Orderadapter((ArrayList<Orders>) temp);
        PatientrecyclerView.setAdapter(PatientmAdapter);
        PatientrecyclerView.setLayoutManager(layoutManager);


    }



}