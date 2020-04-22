package edu.nyit.healthcareportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private String user;
    GlobalData data = GlobalData.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        user = user.replace(".", "_");


        new FirebaseDatabaseHelper("email").checkUser(user, new FirebaseDatabaseHelper.DataStatus() {
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
            public void DataIsChecked(boolean check) {
                data.setAdmin(check);
                orderNum();
                load();

            }


        });
    }
    public void orderNum()
    {
      new FirebaseDatabaseHelper("orderNumber").getOrderNumber(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {

            }

            @Override
            public void OrderIsLoaded(List<Orders> orders, List<String> keys) { }
            @Override
            public void UserIsLoaded(List<Users> users, List<String> keys) { }
            @Override
            public void DataIsInserted() { }
            @Override
            public void OrderNumberLoaded(int num) {
                data.setOrderNum(num);
            }
            @Override
            public void DataIsChecked(boolean check) { }
        });
    }

        public void load()
        {

            if (data.getAdmin()) {

                new FirebaseDatabaseHelper("email").getUsers(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {

                    }

                    @Override
                    public void OrderIsLoaded(List<Orders> orders, List<String> keys) {

                    }

                    @Override
                    public void UserIsLoaded(List<Users> users, List<String> keys) {

                        data.setUsers(users);
                        loadUsers();

                    }

                    @Override
                    public void DataIsInserted() {

                    }
                    @Override
                    public void OrderNumberLoaded(int num) { }

                    @Override
                    public void DataIsChecked(boolean check) {

                    }
                });

            }
            else
                {
                    Users newUser = new Users(user);
                data.getUsers().add(newUser);
            }
            new FirebaseDatabaseHelper("email/" + user + "/orders").getOrders(new FirebaseDatabaseHelper.DataStatus() {


                @Override
                public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) { }

                @Override
                public void OrderIsLoaded(List<Orders> orders, List<String> keys) {
                    data.getUsers().get(0).setOrders(orders);
                }
                @Override
                public void UserIsLoaded(List<Users> users, List<String> keys) { }
                @Override
                public void DataIsInserted() { }
                @Override
                public void OrderNumberLoaded(int num) { }
                @Override
                public void DataIsChecked(boolean check) {
                }});
            new FirebaseDatabaseHelper("email/" + user + "/prescriptions").getPrescriptions(new FirebaseDatabaseHelper.DataStatus() {


                @Override
                public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {
                    data.getUsers().get(0).setPrescriptions(prescriptions);
                    finished();
                }
                @Override
                public void OrderIsLoaded(List<Orders> orders, List<String> keys) { }
                @Override
                public void UserIsLoaded(List<Users> users, List<String> keys) { }
                @Override
                public void DataIsInserted() { }
                @Override
                public void OrderNumberLoaded(int num) { }
                @Override
                public void DataIsChecked(boolean check) {
                }}); }

public void loadUsers()
            {
                for (int x = 0; x < data.getUsers().size(); x++) {
                    final int finalX = x;
                    new FirebaseDatabaseHelper("email/" + data.getUsers().get(x).getEmail() + "/orders").getOrders(new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {

                        }

                        @Override
                        public void OrderIsLoaded(List<Orders> orders, List<String> keys) {
                            data.getUsers().get(finalX).setOrders(orders);
                        }
                        @Override
                        public void UserIsLoaded(List<Users> users, List<String> keys) { }
                        @Override
                        public void DataIsInserted() { }
                        @Override
                        public void OrderNumberLoaded(int num) { }
                        @Override
                        public void DataIsChecked(boolean check) { }
                    });
                    new FirebaseDatabaseHelper("email/" + data.getUsers().get(x).getEmail() + "/prescriptions").getPrescriptions(new FirebaseDatabaseHelper.DataStatus() {


                        @Override
                        public void PrescriptionIsLoaded(List<Prescriptions> prescriptions, List<String> keys) {
                            data.getUsers().get(finalX).setPrescriptions(prescriptions);
                            finished();
                        }
                        @Override
                        public void OrderIsLoaded(List<Orders> orders, List<String> keys) { }
                        @Override
                        public void UserIsLoaded(List<Users> users, List<String> keys) { }
                        @Override
                        public void DataIsInserted() { }
                        @Override
                        public void OrderNumberLoaded(int num) { }
                        @Override
                        public void DataIsChecked(boolean check) {
                        }
                    });


                }



            }
            public void finished()
            {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent);


            }



        }



