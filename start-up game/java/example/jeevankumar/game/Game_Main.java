package example.jeevankumar.game;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class Game_Main extends AppCompatActivity {
    Button Logout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button Createcompanybutton;Button Managebutton;
    Button Investbutton;Button Settingsbutton;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game__main);
            mAuth=FirebaseAuth.getInstance();

            mAuthListener=new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser()==null){
                        startActivity(new Intent(Game_Main.this,Main.class));
                        Toast.makeText(getApplicationContext(),"Successfully Logged Out",Toast.LENGTH_LONG).show();
                    }
                }
            };
        }
        public void oncreatecompanybutton(View view){
            Createcompanybutton=(Button)findViewById(R.id.button_gotocreatecompany);
            startActivity(new Intent(Game_Main.this,Create_Company.class));
        }
        public void onmanagecompanybutton(View view){
            Managebutton=(Button)findViewById(R.id.button_manageCompanies);
            startActivity(new Intent(Game_Main.this,Manage_Company.class));
        }
        public void onlogout(View view){
            mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            mAuth.addAuthStateListener(mAuthListener);
        }

}
