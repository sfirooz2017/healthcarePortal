package edu.nyit.healthcareportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ExampleViewHolder> {

private ArrayList<Orders> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{



        public TextView mOrderNumberA;
        public TextView mOrderContentA;
        public TextView mOrderTransitA;
        public TextView mOrderArrivalA;
        public TextView mOrderLeftCenterA;



        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

           mOrderNumberA=itemView.findViewById(R.id.ordernumberA);
           mOrderContentA=itemView.findViewById(R.id.ordercontentA);
           mOrderTransitA=itemView.findViewById(R.id.intransitA);
           mOrderArrivalA=itemView.findViewById(R.id.arrivedateA);
           mOrderLeftCenterA=itemView.findViewById(R.id.leftcenterA);


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
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Orders allOrders = mExampleList.get(position);
        holder.mOrderNumberA.setText("Order Number:"+allOrders.getNumber());
        holder.mOrderContentA.setText(allOrders.getContains());
        holder.mOrderTransitA.setText(allOrders.getInTransit());
        holder.mOrderArrivalA.setText(allOrders.getArrived());
        holder.mOrderLeftCenterA.setText(allOrders.getLeftCenter());

    }

    @Override
    public int getItemCount() {
       return mExampleList.size();
    }

    // end of ExampleViewHolder

} // end AdminOrderAdapter
