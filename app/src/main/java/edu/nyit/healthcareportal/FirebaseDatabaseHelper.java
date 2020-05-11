package edu.nyit.healthcareportal;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mreference;
    private List<Prescriptions> prescriptions = new ArrayList<>();
    private List<Orders> orders = new ArrayList<>();
    private List<Users> users = new ArrayList<>();

    public interface DataStatus{
        void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys);
        void OrderIsLoaded(List<Orders> orders, List<String> keys);
        void UserIsLoaded(List<Users> users, List <String> keys);
        void DataIsInserted();
        void OrderNumberLoaded(int num);
        void DataIsChecked(boolean check);
    }

    public FirebaseDatabaseHelper(String path) {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mreference = mDatabase.getReference(path);
    }
    public void checkUser(final String email, final DataStatus dataStatus)
    {
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            boolean admin = true;
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    if(keyNode.getKey().equals(email)){
                        admin = false;
                    }

                }
                //dataStatus.DataIsUpdated();
                dataStatus.DataIsChecked(admin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public void getOrderNumber(final DataStatus dataStatus)
    {
        final String[] num = new String[1];
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            num[0] = dataSnapshot.getValue().toString();
                dataStatus.OrderNumberLoaded(Integer.parseInt(num[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { } });
    }

    public void getPrescriptions(final String email, final DataStatus dataStatus)
    {
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescriptions.clear();

                List<String> keys = new ArrayList<>();
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    final Prescriptions prescription = keyNode.getValue(Prescriptions.class);
                    prescription.setUser(email);
                    prescriptions.add(prescription);
                }
                dataStatus.PrescriptionIsLoaded(prescriptions, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }


    public void addPrescription(Prescriptions prescription, final DataStatus dataStatus)
    {

        mreference.child(prescription.getName()).setValue(prescription)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "data inserted");
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void getUsers(final DataStatus dataStatus)
    {
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                List<String> keys = new ArrayList<>();
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    String email = keyNode.getKey();
                    Users user = new Users(email);


                   users.add(user);
                }

                dataStatus.UserIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void updateData(String s){
        mreference.setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "data updated");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void getOrders(final String email, final DataStatus dataStatus)
    {
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();

                List<String> keys = new ArrayList<>();
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    final Orders order = keyNode.getValue(Orders.class);
                    order.setUser(email);
                    orders.add(order);
                }
                dataStatus.OrderIsLoaded(orders, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addOrder(Orders order, final DataStatus dataStatus)
    {
        mreference.child(order.getNumber()).setValue(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "data inserted");
                        dataStatus.DataIsInserted();
                    }
                });

    }
    public void removeData(String k)
    {
        mreference.child(k).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "data deleted");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "could not delete");
                    }
                });
    }

    }




