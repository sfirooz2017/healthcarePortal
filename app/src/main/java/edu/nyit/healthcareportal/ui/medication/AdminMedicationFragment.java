package edu.nyit.healthcareportal.ui.medication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;


public class AdminMedicationFragment extends Fragment {

    TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_medication, container, false);

       GlobalData data = GlobalData.getInstance();
       String s = "";
        for(int x = 0; x< data.getUsers().size(); x++)
        for(int y = 0; y< data.getUsers().get(x).getPrescriptions().size(); y++)
        {
            s = s + data.getUsers().get(x).getPrescriptions().get(y).getName();
            //this is a test to see if all prescriptions laod properly
        }
        textView = root.findViewById(R.id.adminMedsView);
        textView.setText(s);



//add prescription. create method to calculate next refill date- like 1 month after current date-- get current through data.getDate()
Prescriptions p = new Prescriptions("fake prescription", "07/16/20", "30 mg");
int index = 1; //index of user in data.getUsers array
    new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(index).getEmail() + "/prescriptions").addPrescription(p, new FirebaseDatabaseHelper.DataStatus() {

            @Override
            public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) { }
            @Override
            public void OrderIsLoaded(List<Orders> orders, List<String> keys) { }
            @Override
            public void UserIsLoaded(List<Users> users, List<String> keys) { }
            @Override
            public void DataIsInserted() { }
        @Override
        public void OrderNumberLoaded(int num) { }
            @Override
            public void DataIsChecked(boolean check) {}
        });

//delete prescription
            String r = "NAME of prescription being removed";
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(1).getEmail() + "/prescriptions").removeData(r);

//change refill of prescription
        String med = "vicodin"; //prescription to be refilled
        String field = "refill"; //field to be updated. will either be refill or dose
        String update = "06/07/20"; //new value
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(index).getEmail() + "/prescriptions/" + med + "/" + field).updateData(update);


        return root;
    }
}
