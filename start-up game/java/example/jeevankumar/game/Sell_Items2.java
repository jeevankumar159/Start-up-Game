package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sell_Items2 extends AppCompatActivity {
    String productname;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    DatabaseReference sref;
    TextView productnametosell;
    TextView sellingitemcost;
    String userid=Manage_Company.getuserid();
    String companytype=Manage_Company2.getCompanytype();
    String Energy;
    Button Sellittocomputer;Long cash;Long computercost;
    EditText marketprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell__items2);
        Intent i=getIntent();
        productname=i.getStringExtra("productname");
        productnametosell=(TextView)findViewById(R.id.textView_productnameinsell);
        productnametosell.setText(productname);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("Users").child(userid).child("CompaniesOwned").child(companytype);
        sellingitemcost=(TextView)findViewById(R.id.textview_sellingitemcost);
        mref.child("Itemstosell").child(productname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Energy=dataSnapshot.getValue().toString();
                computercost=Long.parseLong(Energy);
                computercost*=10;
                sellingitemcost.setText(computercost.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onselltocomputer(View view){
        Sellittocomputer=(Button)findViewById(R.id.button_selltocomputer);
        mref.child("cash").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cashincompany=dataSnapshot.getValue().toString();
                cash=Long.parseLong(cashincompany);
                cash+=computercost;
                Toast.makeText(getApplicationContext(),"ItemSold",Toast.LENGTH_LONG).show();
                mref.child("Itemstosell").child(productname).removeValue();
                mref.child("cash").setValue(cash);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onselltomarket(View view){
        marketprice=(EditText)findViewById(R.id.editText_marketprice);
        Long market_price=Long.parseLong(marketprice.getText().toString());
        if(market_price>0){firebaseDatabase=FirebaseDatabase.getInstance();
        sref=firebaseDatabase.getReference();
        sref.child("Market").child(companytype).child(userid).child(productname).child("Energy").setValue(Energy);
        sref.child("Market").child(companytype).child(userid).child(productname).child("Sellingcost").setValue(market_price);
        Toast.makeText(getApplicationContext(),"Item added to Marekt",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"The Market price cannot be 0",Toast.LENGTH_SHORT).show();
        }
    }
}