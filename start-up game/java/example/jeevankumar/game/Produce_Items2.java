package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Produce_Items2 extends AppCompatActivity {
    ListView ingredientslistview;
    ArrayList<String> ingredients=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    DatabaseReference userref;
    String userid=Manage_Company.getuserid();
    String companytype=Manage_Company2.getCompanytype();
    TextView TotalcostValue;
    TextView EnergyValue;
    TextView MultiplierValue; Long totalcostvalue;Long Energy_Value;
    Button produce;
    long ingcost;String ing_name;int qtyavl;String product_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce__items2);
        ingredientslistview=(ListView)findViewById(R.id.ingredients_listview);
        final ArrayAdapter ingredientsadapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ingredients);
        Intent i=getIntent();
        product_name=i.getStringExtra("product_name");
        ingredientslistview.setAdapter(ingredientsadapter);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mref=firebaseDatabase.getReference("CompanyList").child(companytype);
        mref.child("Produce").child(product_name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue().toString();
                ingredients.add(value);
                ingredientsadapter.notifyDataSetChanged();
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
        userref=firebaseDatabase.getReference("Users").child(userid).child("CompaniesOwned").child(companytype);
        TotalcostValue=(TextView)findViewById(R.id.textView_TotalCostValue);
        totalcostvalue=Long.parseLong(TotalcostValue.getText().toString());

        EnergyValue=(TextView)findViewById(R.id.textView_EnergyValue);
        Energy_Value=Long.parseLong(EnergyValue.getText().toString());
        ingredientslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView ingname=(TextView)view;
                ing_name=ingname.getText().toString();
                userref.child("ItemsOwned").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            qtyavl = Integer.parseInt(dataSnapshot.child(ing_name).getValue().toString());
                        }catch (Exception n){
                            qtyavl=0;
                        }

                        if (qtyavl>0) {
                            mref.child("Buyable").child("Agri").child(ing_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ingcost = Long.parseLong(dataSnapshot.getValue().toString());
                                    qtyavl--;
                                    totalcostvalue+=ingcost;
                                    Energy_Value+=ingcost/10;
                                    TotalcostValue.setText(totalcostvalue.toString());
                                    EnergyValue.setText(Energy_Value.toString());
                                    userref.child("ItemsOwned").child(ing_name).setValue(qtyavl);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Item Available of this kind",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    public void onproducebuttonclick(View view){
        if(Energy_Value>0){produce=(Button)findViewById(R.id.button_toproduceproduct);
        userref.child("Itemstosell").child(product_name).setValue(Energy_Value);
        Toast.makeText(getApplicationContext(),"Item Produced",Toast.LENGTH_LONG).show();
        Energy_Value= Long.valueOf(0);
        totalcostvalue=Long.valueOf(0);
        EnergyValue.setText(Energy_Value.toString());
        TotalcostValue.setText(totalcostvalue.toString());
        }
        else{
            Toast.makeText(getApplicationContext(),"This ain't Big Bang",Toast.LENGTH_LONG).show();
        }
    }
}
