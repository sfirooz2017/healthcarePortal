package edu.nyit.healthcareportal.ui.medication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.adminMedsAdapter;


public class AdminMedicationFragment extends Fragment {

    private RecyclerView aRecyclerView;
    private RecyclerView.Adapter aAdapter;
    private RecyclerView.LayoutManager  aLayoutManager;

    GlobalData data = GlobalData.getInstance();
    public ArrayList<Prescriptions> prescriptionsList = new ArrayList<Prescriptions>();

    TextView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_medication, container, false);

        searchView = root.findViewById(R.id.searchViewAdminMeds);
        searchView.setFocusable(true);
        searchView.requestFocus();
        aRecyclerView = root.findViewById(R.id.adminmedsRecyclerView);
        aLayoutManager = new LinearLayoutManager(getContext());



        for (int x = 0; x < data.getUsers().size(); x++)
            for (int y = 0; y < data.getUsers().get(x).getPrescriptions().size(); y++) {
                prescriptionsList.add(data.getUsers().get(x).getPrescriptions().get(y));

                ;
                //this is a test to see if all prescriptions laod properly
            }
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

        List<Prescriptions> temp = new ArrayList<>();
        temp.clear();

        if (prescriptionsList.size() != 0)
            for (int x = 0; x < prescriptionsList.size(); x++) {
                if (prescriptionsList.get(x).getName().contains(s) || prescriptionsList.get(x).getUser().contains(s) || s.equals(""))
                    temp.add(prescriptionsList.get(x));
            }
        aAdapter = new adminMedsAdapter((ArrayList<Prescriptions>) temp);
        aRecyclerView.setAdapter(aAdapter);
        aRecyclerView.setLayoutManager(aLayoutManager);


    }


}
