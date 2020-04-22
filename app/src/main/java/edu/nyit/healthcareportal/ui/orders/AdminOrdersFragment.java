package edu.nyit.healthcareportal.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.nyit.healthcareportal.FirebaseDatabaseHelper;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.R;


public class AdminOrdersFragment extends Fragment {

    GlobalData data = GlobalData.getInstance();
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        textView = root.findViewById(R.id.adminOrdersView);

        String s = "";
        for(int x = 0; x< data.getUsers().size(); x++)
            for(int y = 0; y< data.getUsers().get(x).getOrders().size(); y++)
            {
                s = s + data.getUsers().get(x).getOrders().get(y).getNumber();
            }

            textView.setText(s);

//change refill of prescription
        int index = 1; //index of user in data.getUsers array
        String orderNumber = "1234"; //order to be updated
        String update = "06/07/20"; //new value
        new FirebaseDatabaseHelper("email/" + GlobalData.getInstance().getUsers().get(index).getEmail() + "/orders/" + orderNumber + "/arrived").updateData(update);



        return root;
    }

}
