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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Create_User extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private Button CreateUser;private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user);
        Email=(EditText)findViewById(R.id.editText_email);
        Password=(EditText)findViewById(R.id.editText_password);
        CreateUser=(Button)findViewById(R.id.button_createuser);
        mAuth=FirebaseAuth.getInstance();


    }
    public void registeruser(View view){
        String EMAIL=Email.getText().toString().trim();
        String PASSWORD=Password.getText().toString().trim();
        if(EMAIL.isEmpty()){
            Email.setError("Cannot be Empty");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
            Email.setError("Enter a Valid Email");
            Email.requestFocus();
            return;
        }
        if(PASSWORD.isEmpty()){
            Password.setError("Cannot be Empty");
            Password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Created user Successfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Create_User.this,DisplayNameActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
