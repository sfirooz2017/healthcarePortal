package edu.nyit.healthcareportal.ui.medication;
import java.lang.reflect.Array;
import java.time.*;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.Prescriptions;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.Users;
import edu.nyit.healthcareportal.ui.LoginActivity;
import edu.nyit.healthcareportal.ui.home.PatientHomeFragment;
import edu.nyit.healthcareportal.ui.orders.Orderadapter;

public class patientMedAdapter extends RecyclerView.Adapter<patientMedAdapter.PatMedViewHolder> {
    private ArrayList<Prescriptions> meds;
    public GlobalData data = GlobalData.getInstance();
    Context context;



    public static class PatMedViewHolder extends RecyclerView.ViewHolder{
        public Button makeorder;
        public TextView mName;
        public TextView mDose;
        public TextView mRefill;


        public PatMedViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.drugname);
            mDose = itemView.findViewById(R.id.dose);
            mRefill = itemView.findViewById(R.id.refill);
            makeorder= itemView.findViewById(R.id.button);
        }
    }

    public patientMedAdapter(ArrayList<Prescriptions> meds){
        this.meds = meds;
        getItemCount();
    }

    @NonNull
    @Override
    public PatMedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View y = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientmeditem, parent, false);
        PatMedViewHolder evh = new PatMedViewHolder(y);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PatMedViewHolder holder, int position) {
        final Prescriptions currentmed= meds.get(position);
        holder.mName.setText(currentmed.getName());
        holder.mDose.setText(currentmed.getDose());
        holder.mRefill.setText(currentmed.getRefill());
        Log.d("shan", currentmed.getName());
        if (!currentmed.getRefill().equals(data.getDate()))
        {
            Log.d("shan", data.getDate());
            holder.makeorder.setEnabled(false);
        }

       String refilldate = currentmed.getRefill();
        String currentDate = data.getDate();

        final String[] fragmenteddate = refilldate.split("/");
        String[] todaysDate = currentDate.split("/");

        //Present dates
        int currentDay = Integer.parseInt(todaysDate[0]);
        int currentMonth = Integer.parseInt(todaysDate[1]);

        //Delivery dates
        int deliveryDay = Integer.parseInt(fragmenteddate[0]);
        int deliveryMonth = Integer.parseInt(fragmenteddate[1]);

        if(currentMonth<deliveryMonth){
            holder.makeorder.setEnabled(false);
        }else if(currentMonth == deliveryMonth && currentDay > deliveryDay){
            holder.makeorder.setEnabled(true);

        }


        //holder.makeorder.setEnabled(false);
        // if currentmonth < refillmonth -- no refill
        //if currentmonth == refillmonth-- compared days. if currentday > refillday -- refill
        //if currentmonth > refill month -- automatic refill






        holder.makeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                {
                   String r =  currentmed.getRefill();
                   String current = data.getDate();

                   String[]calltemp =r.split("/");
                    String[] present =current.split("/");

                    int day = Integer.parseInt(present[0]);
                   int month = Integer.parseInt(present[1]);

                    String leftCenter = new PatientHomeFragment().UpdateDay(day, month, 1)+"/"+calltemp[2];
                    String inTransit = new PatientHomeFragment().UpdateDay(day, month, 2)+"/"+calltemp[2];
                    String arrived = new PatientHomeFragment().UpdateDay(day, month, 3)+"/"+calltemp[2];


                    int refilday = Integer.parseInt(calltemp[0]);
                    int refilmonth = Integer.parseInt(calltemp[1]);



                   if(refilmonth<12){
                       refilmonth++;


                   }
                   else{
                       refilmonth = 1;
                   }
                    String s;
                   if (refilmonth<10){
                       s =Integer.toString(refilmonth);
                       s= "0"+s;
                   }
                   else
                   {
                       s = refilmonth + "";
                   }



                    r = calltemp[0] +"/"+s+"/"+calltemp[2];
                   currentmed.setRefill(r);
                   new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(0).getEmail() + "/prescriptions/" + currentmed.getName() + "/" + "refill").updateData(r);
                    Toast.makeText(context, "Order has been placed!", Toast.LENGTH_SHORT).show();




                    Log.d("arda", r);






                   //modify r to get 1 month ahead
                    //to make a new order, you must increment the order number so each number has a unique oder number. then pass new order number when making new order
                    String num = String.valueOf(data.getOrderNum() + 1);
                    new FirebaseDatabaseHelper("orderNumber/").updateData(num);

                    Orders order = new Orders(arrived, currentmed.getName(), leftCenter, inTransit, num);
                    data.getUsers().get(0).getOrders().add(order);
                    new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(0).getEmail() + "/orders").addOrder(order, new FirebaseDatabaseHelper.DataStatus() {

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
            }
        );


    }

    @Override
    public int getItemCount() {
        Log.d("shan", meds.size() + "");
        return meds.size();
    }
}
