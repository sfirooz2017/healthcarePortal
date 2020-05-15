package edu.nyit.healthcareportal.ui.medication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;
import edu.nyit.healthcareportal.adminMedsAdapter;
import edu.nyit.healthcareportal.ui.orders.Orderadapter;

public class PatientMedicationFragment extends Fragment {
    private RecyclerView PatientrecyclerView;
    private RecyclerView.Adapter PatientmAdapter;
    private RecyclerView.LayoutManager layoutManager;


    TextView searchView;


    GlobalData data = GlobalData.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medication, container, false);

        searchView = root.findViewById(R.id.searchViewPatientMeds);
        searchView.setFocusable(true);
        searchView.requestFocus();





        PatientrecyclerView = root.findViewById(R.id.medRecylerPatient);
        layoutManager = new LinearLayoutManager(getContext());
        // PATIENT IS ALWAYS INDEX 0
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

        if (data.getUsers().get(0).getPrescriptions().size() != 0)
            for (int x = 0; x < data.getUsers().get(0).getPrescriptions().size(); x++) {
                if (data.getUsers().get(0).getPrescriptions().get(x).getName().contains(s) || data.getUsers().get(0).getPrescriptions().get(x).getRefill().contains(s) || s.equals(""))
                    temp.add(data.getUsers().get(0).getPrescriptions().get(x));
            }
        PatientmAdapter = new patientMedAdapter((ArrayList<Prescriptions>) temp);
        PatientrecyclerView.setAdapter(PatientmAdapter);
        PatientrecyclerView.setLayoutManager(layoutManager);

    }



}