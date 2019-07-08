package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Companydescription extends AppCompatActivity {
    TextView COMPANYDES;
    EditText usercompanyname;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference itemsRef;
    private FirebaseAuth mAuth;
    long companycost;String userid;
    int noofcompanies;
    String companynametostart;
    long coins;
    boolean flag=true;
    String usercompname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydescription);
        Intent intent=getIntent();
        String companydesc=intent.getStringExtra("CompanyDescription");
        companynametostart=intent.getStringExtra("Companytostart");
        companycost=intent.getLongExtra("Companycost",0);
        COMPANYDES=(TextView)findViewById(R.id.textView_companydes);
        COMPANYDES.setText(companydesc);
        usercompanyname=(EditText) findViewById(R.id.editText_usercompname);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("Users");
        itemsRef=firebaseDatabase.getReference("CompanyList").child(companynametostart);
        mAuth=FirebaseAuth.getInstance();
        userid=mAuth.getUid();
        myRef.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               coins=Long.parseLong(dataSnapshot.child("coins").getValue().toString());
               noofcompanies=Integer.parseInt(dataSnapshot.child("noofcompanies").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child(userid).child("CompaniesOwned").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(companynametostart).exists()){
                    Toast.makeText(getApplicationContext(),"You already Own a company of this type",Toast.LENGTH_LONG).show();
                    flag=false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void oncreatecompany(View view){
        if(coins>=companycost && flag==true ){
            itemsRef=firebaseDatabase.getReference("CompanyList").child(companynametostart);
            double moneyleft=coins-companycost;

            noofcompanies=noofcompanies+1;
            myRef=firebaseDatabase.getReference("Users").child(userid);
            myRef.child("coins").setValue(moneyleft);
            myRef.child("noofcompanies").setValue(noofcompanies);
            usercompname=usercompanyname.getText().toString();
            CompanyDetails cd=new CompanyDetails(usercompname,0,0,0,companycost);
            myRef.child("CompaniesOwned").child(companynametostart).setValue(cd);
            Toast.makeText(getApplicationContext(),"Successfully Created Company",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Unable to Start Company",Toast.LENGTH_LONG).show();
        }
    }
}
