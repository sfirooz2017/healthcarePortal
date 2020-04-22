package edu.nyit.healthcareportal.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.nyit.healthcareportal.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Button send;
    EditText email;
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS; //Built in email address Regex



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.emailText);
        send = findViewById(R.id.sendButton);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = email.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    email.setError("Email required");
                    return;

                } else if (!EMAIL_PATTERN.matcher(user).matches()) {
                    email.setError("Invalid email address");
                }

                mAuth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Forgot Password email sent to: " + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
