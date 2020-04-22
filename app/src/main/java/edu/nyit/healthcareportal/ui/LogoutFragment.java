package edu.nyit.healthcareportal.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.LoadingActivity;
import edu.nyit.healthcareportal.MainActivity;
import edu.nyit.healthcareportal.R;


public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);


        FirebaseAuth.getInstance().signOut();
        GlobalData.getInstance().getUsers().clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent);

        return root;
    }
}
