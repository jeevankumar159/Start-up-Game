package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Manage_Company extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
     private DatabaseReference companymanagereference;
    private ListView managecompanylistview;
    private ArrayList<String> MANAGECOMPANYLIST=new ArrayList<String>();
    static String userid;
    String s="";
    static String companynametomanage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__company);
        managecompanylistview=(ListView)findViewById(R.id.managecompanylistinxml);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        companymanagereference=database.getReference("Users");
        userid=mAuth.getUid();
        final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,MANAGECOMPANYLIST);
        managecompanylistview.setAdapter(arrayAdapter);
        companymanagereference.child(userid).child("CompaniesOwned").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String coname=data.child("companyname").getValue().toString();
                    MANAGECOMPANYLIST.add(coname);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        managecompanylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView c=(TextView) view;
                companynametomanage=c.getText().toString();
                companymanagereference=database.getReference("Users").child(userid).child("CompaniesOwned");
                companymanagereference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                           if(data.child("companyname").getValue().toString().equals(companynametomanage)){
                            s=data.getKey().toString();
                               Intent managecompany2=new Intent(Manage_Company.this,Manage_Company2.class);
                               managecompany2.putExtra("companynametomanage",companynametomanage);
                               managecompany2.putExtra("parentcompanyname",s);
                               startActivity(managecompany2);
                           }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }public static String getuserid(){
        return userid;
    }
}
