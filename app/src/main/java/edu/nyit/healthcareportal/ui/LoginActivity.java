package edu.nyit.healthcareportal.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.nyit.healthcareportal.GlobalData;
import edu.nyit.healthcareportal.LoadingActivity;
import edu.nyit.healthcareportal.MainActivity;
import edu.nyit.healthcareportal.R;
import edu.nyit.healthcareportal.ui.medication.PatientMedicationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Button login;
    EditText email;
    EditText password;
    TextView forgotPassword;
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS; //Built in email address Regex



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
            startActivity(intent);
        }
        else
        {
            login = findViewById(R.id.loginButton);
            email = findViewById(R.id.emailText);
            password = findViewById(R.id.passwordText);
            forgotPassword = findViewById(R.id.fogotPasswordView);
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                }
            };
            login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             final String user = email.getText().toString().trim();
                                             String pword = password.getText().toString().trim();

                                             if (TextUtils.isEmpty(user)) {
                                                 email.setError("Email required");
                                                 return;

                                             } else if (!EMAIL_PATTERN.matcher(user).matches()) {
                                                 email.setError("Invalid email address");
                                             }
                                             if (TextUtils.isEmpty(pword)) {
                                                 password.setError("Password required");
                                                 return;
                                             }
                                             mAuth.signInWithEmailAndPassword(user, pword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                                     if (!task.isSuccessful())
                                                         Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                                     else {
                                                         Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
                                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack FLAG_ACTIVITY_CLEAR_TOP
                                                        startActivity(intent);
                                                     }

                                                 }
                                             });
                                         }
                                     });
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
