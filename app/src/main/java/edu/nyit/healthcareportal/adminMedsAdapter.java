package edu.nyit.healthcareportal;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminMedsAdapter extends RecyclerView.Adapter<adminMedsAdapter.ExampleViewHolder2> {

  private ArrayList<Prescriptions> prescriptions;
    public GlobalData data = GlobalData.getInstance();

public static class  ExampleViewHolder2 extends RecyclerView.ViewHolder{




    public TextView madminmedsMedication;
    public TextView madminmedsEmail;
    public TextView madminmedsRefill;
    public TextView madminmedsDosage;



    public ExampleViewHolder2(@NonNull View itemView) {
        super(itemView);

        madminmedsMedication = itemView.findViewById(R.id.admintestPatientMedication);
        madminmedsEmail = itemView.findViewById(R.id.admintestPatientEmail);
        madminmedsRefill = itemView.findViewById(R.id.refill);
        madminmedsDosage = itemView.findViewById(R.id.dosage);


    }
}

public adminMedsAdapter(ArrayList<Prescriptions> prescriptions){
    this.prescriptions =prescriptions;
    getItemCount();
}

    @NonNull
    @Override
    public ExampleViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminmeds, parent, false);
        adminMedsAdapter.ExampleViewHolder2 evh = new adminMedsAdapter.ExampleViewHolder2(v);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder2 holder, int position) {
        Prescriptions medicationList = prescriptions.get(position);

        holder.madminmedsMedication.setText(medicationList.getName());
        holder.madminmedsDosage.setText(medicationList.getDose());
        holder.madminmedsRefill.setText(medicationList.getRefill());
       // holder.madminmedsEmail.setText(data.getUsers().get(position).getEmail());




    }

    @Override
    public int getItemCount() {
    Log.d("shan", prescriptions.size() + "");
    return prescriptions.size();
    }
}
