package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Create_Company extends AppCompatActivity {
    private FirebaseDatabase database;
    DatabaseReference mRef;
    DatabaseReference companyreference;
    private ListView companylistview;
    String companydescription;
    private ArrayList<String> COMPANYLIST=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__company);
        database=FirebaseDatabase.getInstance();
        mRef=database.getReference("Companies");
        companyreference=database.getReference("CompanyList");
        companylistview=(ListView)findViewById(R.id.companylistinxml);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,COMPANYLIST);
        companylistview.setAdapter(arrayAdapter);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               String value=dataSnapshot.getValue(String.class);
                COMPANYLIST.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                COMPANYLIST.remove(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       companylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               TextView t=(TextView)view;
               final String Companynametostart=t.getText().toString();
                DatabaseReference companytostart=database.getReference("CompanyList").child(Companynametostart);
               companytostart.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       companydescription=dataSnapshot.child("Description").getValue().toString();
                       Long n= (Long) dataSnapshot.child("Cost").getValue();
                       Intent startcompanyintent=new Intent(getApplicationContext(),Companydescription.class);
                       startcompanyintent.putExtra("Companytostart",Companynametostart);
                       startcompanyintent.putExtra("Companycost",n);
                       startcompanyintent.putExtra("CompanyDescription",companydescription);
                       startActivity(startcompanyintent);
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }
       });
    }
}
