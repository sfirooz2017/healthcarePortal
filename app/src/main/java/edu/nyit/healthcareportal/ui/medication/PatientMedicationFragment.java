package edu.nyit.healthcareportal.ui.medication;

import android.os.Bundle;
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

import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;

public class PatientMedicationFragment extends Fragment {
    TextView textView;
    GlobalData data = GlobalData.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medication, container, false);

        String s = "";

        // PATIENT IS ALWAYS INDEX 0

        for(int x = 0; x< data.getUsers().get(0).getPrescriptions().size(); x++)
        {
            //check if user data loads
            s = s + data.getUsers().get(0).getPrescriptions().get(x).getName();
        }
        textView = root.findViewById(R.id.medsView);
        textView.setText(s);
        textView.setText(data.getUsers().get(0).getEmail());


//refill prescription--first check if refill is due. get current date from data.getDate()




        return root;
        }
        public void makeAnOrder()
        {

            String num = String.valueOf(data.getOrderNum() + 1);
            new FirebaseDatabaseHelper("orderNumber/").updateData(num);
            //ORDER prescription
            String prescription = "zolof";
            //to make a new order, you must increment the order number so each number has a unique oder number. then pass new order number when making new order
            Orders order = new Orders("06/01/20", prescription, "06/02/20", "06/03/20", "4567");
            new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(0).getEmail() + "/orders").addOrder(order, new FirebaseDatabaseHelper.DataStatus() {

                @Override
                public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) { }
                @Override
                public void OrderIsLoaded(List<Orders> orders, List<String> keys) { }
                @Override
                public void UserIsLoaded(List<Users> users, List<String> keys) { }
                @Override
                public void DataIsInserted() {
                    //on success is here
                }
                @Override
                public void OrderNumberLoaded(int num) { }
                @Override
                public void DataIsChecked(boolean check) {}
            });
        }


}
