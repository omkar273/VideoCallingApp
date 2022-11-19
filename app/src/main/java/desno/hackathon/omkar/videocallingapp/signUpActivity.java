package desno.hackathon.omkar.videocallingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import desno.hackathon.omkar.videocallingapp.models.Users;

public class signUpActivity extends AppCompatActivity {
    EditText email, password;
    Button login_btn, register_btn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseFirestore.getInstance();

        register_btn.setOnClickListener(view -> {
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "acc craeted", Toast.LENGTH_SHORT).show();
                    Users users = new Users(email.getText().toString(), password.getText().toString());

                    database.collection("Users").document().set(users).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "datta added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("video", "onCreate: " + task.getException().getLocalizedMessage());
                    Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });


        login_btn.setOnClickListener(view -> {
            startActivity(new Intent(signUpActivity.this, LoginActivity.class));
        });

    }
}