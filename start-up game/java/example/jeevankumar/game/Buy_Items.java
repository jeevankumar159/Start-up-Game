package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buy_Items extends AppCompatActivity {
    ListView Buyitemslistview;
    ArrayList<Buy_items_details>buyitemslist=new ArrayList<Buy_items_details>();
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth uAuth;
    private DatabaseReference mref;
    private DatabaseReference uref;
    static String companytype;SeekBar seekBar1;TextView seekbar1value;
    static int seek1value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__items);
        Intent i=getIntent();
        companytype=i.getStringExtra("companytype");
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("CompanyList");
        Buyitemslistview=(ListView)findViewById(R.id.buyitemslistxml);
        final buyitemAdapter adapter=new buyitemAdapter(this,R.layout.buy_list,buyitemslist);
        Buyitemslistview.setAdapter(adapter);
        seekBar1=(SeekBar)findViewById(R.id.seekBar_quantiytobuy);
        seekbar1value=(TextView)findViewById(R.id.textView_seekbar1value);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbar1value.setText(String.valueOf(i));
                seek1value=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mref.child(companytype).child("Buyable").child("Agri").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey().toString();
                String cost=dataSnapshot.getValue().toString();
               Buy_items_details b=new Buy_items_details(value,cost);
                buyitemslist.add(b);
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
    }
    public static String getcompany(){
        return companytype;
    }
    public static int getseek1value(){
        return seek1value;
    }
}
