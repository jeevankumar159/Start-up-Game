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
import java.util.ArrayList;
import static example.jeevankumar.game.Manage_Company.userid;

public class Sell_Items1 extends AppCompatActivity {
    ListView SellitemListview;
    ArrayList<String> sellitem_Arraylist=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    String companytype=Manage_Company2.getCompanytype();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell__items1);
        SellitemListview=(ListView)findViewById(R.id.sellitems_listview);
        final ArrayAdapter sellitemadapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,sellitem_Arraylist);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("Users").child(userid).child("CompaniesOwned").child(companytype);
        mref.child("Itemstosell").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey().toString();
                sellitem_Arraylist.add(value);
                sellitemadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SellitemListview.setAdapter(sellitemadapter);
        SellitemListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t=(TextView)view;
                String s=t.getText().toString();
                Intent sellitemintent=new Intent(Sell_Items1.this,Sell_Items2.class);
                sellitemintent.putExtra("productname",s);
                startActivity(sellitemintent);
            }
        });
    }

}
