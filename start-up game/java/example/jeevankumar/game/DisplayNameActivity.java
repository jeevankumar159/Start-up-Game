package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayNameActivity extends AppCompatActivity {
    EditText displayname;
    Button activate;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    int coins=50000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_name);
        displayname=(EditText)findViewById(R.id.editText_displayname);
        activate=(Button)findViewById(R.id.button_activate);
        mAuth=FirebaseAuth.getInstance();
    }
    public void onactivate(View view){
        String DisplayName=displayname.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String userID=mAuth.getCurrentUser().getUid();
        User user=new User(DisplayName,coins,0);
        mDatabase.child("Users").child(userID).setValue(user);
        Toast.makeText(getApplicationContext(),"User Activated",Toast.LENGTH_LONG).show();
        startActivity(new Intent(DisplayNameActivity.this,Game_Main.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth.getCurrentUser()!=null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser.getDisplayName()!=null) {
                Intent directtogame = new Intent(DisplayNameActivity.this, Game_Main.class);
                startActivity(directtogame);
            }
        }
    }
}
