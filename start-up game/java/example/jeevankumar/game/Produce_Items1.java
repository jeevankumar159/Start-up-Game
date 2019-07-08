package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Produce_Items1 extends AppCompatActivity {
    ListView productlistview;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;String companytype=Manage_Company2.getCompanytype();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce__items1);
        firebaseDatabase=FirebaseDatabase.getInstance();
        productlistview=(ListView)findViewById(R.id.productlistview);
        final ArrayList<Product_details> product_details_arraylist=new ArrayList<>();
        mref=firebaseDatabase.getReference("CompanyList");
        final ProductlistAdapter productlistAdapter=new ProductlistAdapter(this,R.layout.product_adapter,product_details_arraylist);
        mref.child(companytype).child("Produce").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey().toString();
                Product_details p=new Product_details(value);
                product_details_arraylist.add(p);
                productlistAdapter.notifyDataSetChanged();
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
        productlistview.setAdapter(productlistAdapter);
        productlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView product=(TextView)view.findViewById(R.id.textView_product_names);
                String product_name=product.getText().toString();
                Intent produceitem2intent=new Intent(Produce_Items1.this,Produce_Items2.class);
                produceitem2intent.putExtra("product_name",product_name);
                startActivity(produceitem2intent);
            }
        });
    }
}
