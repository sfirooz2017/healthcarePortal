package edu.nyit.healthcareportal.ui.medication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;
import edu.nyit.healthcareportal.adminMedsAdapter;


public class AdminMedicationFragment extends Fragment {

    private RecyclerView aRecyclerView;
    private RecyclerView.Adapter aAdapter;
    private RecyclerView.LayoutManager  aLayoutManager;

    GlobalData data = GlobalData.getInstance();
 public ArrayList<Prescriptions> prescriptionsList = new ArrayList<Prescriptions>();

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_medication, container, false);


        String s = "";

        for (int x = 0; x < data.getUsers().size(); x++)
            for (int y = 0; y < data.getUsers().get(x).getPrescriptions().size(); y++) {
                prescriptionsList.add(data.getUsers().get(x).getPrescriptions().get(y));

                ;
                //this is a test to see if all prescriptions laod properly
            }

        aRecyclerView = root.findViewById(R.id.adminmedsRecyclerView);
        aLayoutManager = new LinearLayoutManager(getContext());
        aAdapter = new adminMedsAdapter((ArrayList<Prescriptions>) prescriptionsList);
        aRecyclerView.setAdapter(aAdapter);
        aRecyclerView.setLayoutManager(aLayoutManager);

        return root;


    } };
//add prescription. create method to calculate next refill date- like 1 month after current date-- get current through data.getDate()

    /** public void refillOrder() {
        Prescriptions p = new Prescriptions("fake prescription", "07/16/20", "30 mg"); //name, refill, dosage

        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(i).getEmail() + "/prescriptions").addPrescription(p, new FirebaseDatabaseHelper.DataStatus() {

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

//delete prescription


        public void removePrescription()
        {
            String r = "NAME of prescription being removed";
            new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(1).getEmail() + "/prescriptions").removeData(r);

        }
//change refill of prescription
        public void changePrescription() {
        String med = "vicodin"; //prescription to be refilled
        String field = "refill"; //field to be updated. will either be refill or dose
        String update = "06/07/20"; //new value
     int i = data.getUsers().indexOf("user");
     if (i == -1)
     {}
         //error toast message
         //else, youre ngoinna call the firebase method
         int i2 = data.getUsers().get(i).getPrescriptions().indexOf(med);
     // (i2 == -1) error message user doesnt have that prescription. if they do, call upadte
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(i).getEmail() + "/prescriptions/" + med + "/" + field).updateData(update);

    }
    }**/

