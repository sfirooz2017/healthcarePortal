package edu.nyit.healthcareportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ExampleViewHolder> {

private ArrayList<Orders> mExampleList;
GlobalData data = GlobalData.getInstance();

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{



        public TextView mOrderNumberA;
        public TextView mOrderContentA;
        public TextView mOrderTransitA;
        public TextView mOrderArrivalA;
        public TextView mOrderLeftCenterA;
        public TextView mPatientEmail;
        public Button mButtonArrived;



        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

           mOrderNumberA=itemView.findViewById(R.id.ordernumberA);
           mOrderContentA=itemView.findViewById(R.id.ordercontentA);
           mOrderTransitA=itemView.findViewById(R.id.intransitA);
           mOrderArrivalA=itemView.findViewById(R.id.arrivedateA);
           mOrderLeftCenterA=itemView.findViewById(R.id.leftcenterA);
           mPatientEmail=itemView.findViewById(R.id.patientEmailOrder);
           mButtonArrived=itemView.findViewById(R.id.arrivedButton);


        }
    }


    public AdminOrderAdapter(ArrayList<Orders> exampleList) {

           this.mExampleList=exampleList;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admintest, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;


    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {
        final Orders allOrders = mExampleList.get(position);
        holder.mOrderNumberA.setText("Order Number:"+allOrders.getNumber());
        holder.mOrderContentA.setText(allOrders.getContains());
        holder.mOrderTransitA.setText(allOrders.getInTransit());
        holder.mOrderArrivalA.setText(allOrders.getArrived());
        holder.mOrderLeftCenterA.setText(allOrders.getLeftCenter());
        holder.mPatientEmail.setText(allOrders.getUser());

        holder.mButtonArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String num = String.valueOf(data.getOrderNum() + 1);
                allOrders.setArrived(data.getDate());
                new FirebaseDatabaseHelper("email/" + allOrders.getUser() + "/orders/" + allOrders.getNumber() + "/arrived").updateData(data.getDate());


            }
        });

    }

    @Override
    public int getItemCount() {
       return mExampleList.size();
    }

    // end of ExampleViewHolder

} // end AdminOrderAdapter
