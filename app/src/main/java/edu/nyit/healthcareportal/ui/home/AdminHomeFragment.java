package edu.nyit.healthcareportal.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.regex.Pattern;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;

public class AdminHomeFragment extends Fragment {

    String email, prescription, refill, dosage;
    String email2, prescription2, refill2, dosage2;
    private FirebaseAuth mAuth;
    Context context = getContext();

    EditText nPrescription;
    EditText nEmail;
    EditText nRefill;
    EditText nDosage;
    Button nSubmit;

    EditText mPrescription, mEmail, mRefill, mDosage;
    Button mSubmit;

    GlobalData data = GlobalData.getInstance();
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS; //Built in email address Regex



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //super.onCreateView(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_admin_home, container, false);

        // setContentView(R.layout ADD
        nPrescription = (EditText) root.findViewById(R.id.inputMedication);
        nEmail = (EditText) root.findViewById(R.id.inputEmail);
        nRefill = (EditText) root.findViewById(R.id.inputRefillDate);
        nDosage = (EditText) root.findViewById(R.id.inputDosage);
        nSubmit = (Button) root.findViewById(R.id.buttonHome);

        // set2 UPDATE

        mPrescription = (EditText) root.findViewById(R.id.inputMedication2);
        mEmail = (EditText) root.findViewById(R.id.inputEmail2);
        mRefill = (EditText) root.findViewById(R.id.inputRefilDate2);
        mDosage = (EditText) root.findViewById(R.id.inputDosage2);
        mSubmit = (Button) root.findViewById(R.id.buttonConfirm);


// RefillOrder

        // calling second button
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email2 = mEmail.getText().toString();
                prescription2 = mPrescription.getText().toString();
                refill2 = mRefill.getText().toString();
                dosage2 = mDosage.getText().toString();


                if (TextUtils.isEmpty(email2)) {
                    mEmail.setError("Email required");
                    return;
                } else if (TextUtils.isEmpty(prescription2)) {
                    mPrescription.setError("Medication required");
                    return;

                } else if (!EMAIL_PATTERN.matcher(email2).matches()) {
                    mEmail.setError("Invalid email address");
                    return;
                } else if (TextUtils.isEmpty(dosage2)) {
                    mDosage.setError("dosage required");
                    return;
                } else if (TextUtils.isEmpty(refill2)) {
                    mRefill.setError("refill date required");
                    return;
                }

                email2 =  email2.replace(".", "_");
                boolean valid = false;
                boolean valid2 = false;
                int x;
                for(x = 0; x < data.getUsers().size();x++)

                {
                    if(data.getUsers().get(x).getEmail().equals(email2)) {
                        valid = true;
                        break;
                    }

                }
                for(int y = 0; y < data.getUsers().get(x).getPrescriptions().size();y++)

                {
                    if(data.getUsers().get(x).getPrescriptions().get(y).equals(prescription2))
                        valid2 = true;

                }


                if (valid && valid2){

                    refillOrder(email2, prescription2, refill2, dosage2);


                } else {
                    Toast.makeText(AdminHomeFragment.this.getContext(), "invalid user or prescription", Toast.LENGTH_SHORT).show();

                }


            } // ends onClick1



        });  // ends setOnclick1


        /// start 1-- update


        // calling 1st button

        //(updates  Dosage

        nSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = nEmail.getText().toString();
                prescription = nPrescription.getText().toString();
                dosage = nDosage.getText().toString();
                refill = nRefill.getText().toString();


                //int i2 = data.getUsers().get(i).getPrescriptions().indexOf("med");(this is for changing refill or dosge)

                if (TextUtils.isEmpty(email)) {
                    nEmail.setError("Email required");
                    return;
                } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                    nEmail.setError("Invalid email address");
                    return;
                } else if (TextUtils.isEmpty(prescription)) {
                    nPrescription.setError("Prescription required");
                    return;
                }
                email =  email.replace(".", "_");
                boolean valid = false;
                for(int x = 0; x < data.getUsers().size();x++)

                {
                    if(data.getUsers().get(x).getEmail().equals(email))
                        valid = true;

                }
                if (!valid)      // updates refill
                    Toast.makeText(AdminHomeFragment.this.getContext(), "Invalid User", Toast.LENGTH_SHORT).show();
                else {
                    if (!TextUtils.isEmpty(refill)) {
                        new FirebaseDatabaseHelper("email/" + email + "/prescriptions/" + prescription + "/" + "refill").updateData(refill);
                    }
                    if (!TextUtils.isEmpty(dosage)) {
                        new FirebaseDatabaseHelper("email/" + email + "/prescriptions/" + prescription + "/" + "dosage").updateData(dosage);
                    }




                }


            }  // ends Onclick2
        });  // set onClick2

    return root;

    };





    public void refillOrder(String email, String prescription, String refill, String dosage) {
      //  Prescriptions p = new Prescriptions(prescription, refill,dosage); //name, refill, dosage
        Prescriptions p = new Prescriptions("medicinename", "1/2/3", "10 mg");
        new FirebaseDatabaseHelper("email/" + email + "/prescriptions").addPrescription(p, new FirebaseDatabaseHelper.DataStatus() {

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




    }  // End refillOrderMethod










}; // end class

/**



private  void  showTast(String text){


    Toast.makeText(AdminHomeFragment.this,text,Toast.LENGTH_SHORT.show());


        //View root = inflater.inflate(R.layout.admin_home, container, false);

        return root;

    }  // onCreateView

}  // end AdminHomeFragment

**/

