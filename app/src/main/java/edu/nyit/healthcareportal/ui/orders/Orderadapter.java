package edu.nyit.healthcareportal.ui.orders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.Orders;
import edu.nyit.healthcareportal.R;

public class Orderadapter extends RecyclerView.Adapter<Orderadapter.orderViewHolder> {
    private  ArrayList<Orders> orders;
    public GlobalData data = GlobalData.getInstance();



        public static class orderViewHolder extends RecyclerView.ViewHolder{
        public TextView mOrderNumber;
        public TextView mOrderContent;
        public TextView mOrderTransit;
        public TextView mOrderArrival;
        public TextView mOrderLeftCenter;



        public orderViewHolder(@NonNull View itemView) {

            super(itemView);
            mOrderNumber = itemView.findViewById(R.id.ordernumber);
            mOrderContent = itemView.findViewById(R.id.ordercontent);
            mOrderTransit = itemView.findViewById(R.id.transit);
            mOrderArrival = itemView.findViewById(R.id.arrivedate);
            mOrderLeftCenter = itemView.findViewById(R.id.leftcenter);


        }
    }
    public Orderadapter(ArrayList<Orders> orders){
        this.orders = orders;
        getItemCount();

    }




    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exampleitem, parent, false);
        orderViewHolder evh = new orderViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        Orders currentorder = orders.get(position);
        holder.mOrderNumber.setText("Order Number:"+currentorder.getNumber());
        holder.mOrderContent.setText(currentorder.getContains());
        holder.mOrderTransit.setText(currentorder.getInTransit());
        holder.mOrderArrival.setText(currentorder.getArrived());
        holder.mOrderLeftCenter.setText(currentorder.getLeftCenter());
        Log.d("arda", currentorder.getNumber()+"this isnt working");

    }

    @Override
    public int getItemCount() {

        return orders.size();

    }
        }
