package example.jeevankumar.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Market extends AppCompatActivity {
    ListView Sellitemslistview;String userid=Manage_Company.getuserid();
    ArrayList<sell_items_details> sell_items_detailsArrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference sref;
    String companytype=Manage_Company2.getCompanytype();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Sellitemslistview=(ListView)findViewById(R.id.marketitemlistview);
        final sellitemAdapter adapter=new sellitemAdapter(this,R.layout.itemsonsale,sell_items_detailsArrayList);
        firebaseDatabase=FirebaseDatabase.getInstance();
        sref=firebaseDatabase.getReference("Market");
        sref.child(companytype).child(userid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String itemname=dataSnapshot.getKey().toString();
                String itemenergy=dataSnapshot.child("Energy").getValue().toString();
                String itemcost=dataSnapshot.child("Sellingcost").getValue().toString();
                sell_items_details sell=new sell_items_details(itemname,itemenergy,itemcost,userid);
                sell_items_detailsArrayList.add(sell);
                adapter.notifyDataSetChanged();
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
        Sellitemslistview.setAdapter(adapter);
    }
}
