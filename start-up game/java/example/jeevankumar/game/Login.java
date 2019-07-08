package example.jeevankumar.game;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText LoginEmail;
    EditText LoginPassword;
    Button LoginButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void onlogintogame(View view){
        LoginEmail=(EditText)findViewById(R.id.editText_loginemail);
        LoginPassword=(EditText)findViewById(R.id.editText_loginpassword);
        String LOGINEMAIL=LoginEmail.getText().toString().trim();
        String LOGINPASSWORD=LoginPassword.getText().toString().trim();
        mAuth=FirebaseAuth.getInstance();
        if(LOGINEMAIL.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email Cannot be Empty",Toast.LENGTH_LONG).show();
            LoginEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(LOGINEMAIL).matches()){
            LoginEmail.setError("Enter a Valid Email");
            LoginEmail.requestFocus();
            return;
        }
        if(LOGINPASSWORD.isEmpty()){
            LoginPassword.setError("Cannot be Empty");
            LoginPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(LOGINEMAIL, LOGINPASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"Login Succesful",Toast.LENGTH_LONG).show();
                            Intent Logintodisplayuser=new Intent(Login.this,DisplayNameActivity.class);
                            startActivity(Logintodisplayuser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
}
