package example.jeevankumar.game;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import java.util.ArrayList;
public class buyitemAdapter extends ArrayAdapter<Buy_items_details> {
    private Context mcontext;private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth uAuth;
    private DatabaseReference uref;
    private String itemname;private long itemcost;private long companymoney;private int qty;
    private  int mResource;private String companytype=Buy_Items.getcompany();
    private int qtobuy;

    public buyitemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Buy_items_details> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String ItemName=getItem(position).getItem_name();
        String cost=getItem(position).getCost();
        firebaseDatabase=FirebaseDatabase.getInstance();
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView =inflater.inflate(mResource,parent,false);
        final TextView item_name=(TextView) convertView.findViewById(R.id.textView_buyingitem);
        final TextView cost_item=(TextView)convertView.findViewById(R.id.textView_itemcost);
        Button buy=(Button)convertView.findViewById(R.id.button_buy);
        item_name.setText(ItemName);
        uAuth= FirebaseAuth.getInstance();
        String userid=uAuth.getUid().toString();
        uref=firebaseDatabase.getReference("Users").child(userid);
        cost_item.setText(cost);
        uref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String s=dataSnapshot.child("CompaniesOwned").child(companytype).child("cash").getValue().toString();
                companymoney=Long.parseLong(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemname=item_name.getText().toString();
                itemcost=Long.parseLong(cost_item.getText().toString());
                qtobuy=Buy_Items.getseek1value();
                if(companymoney>itemcost*qtobuy){
                    uref.child("CompaniesOwned").child(companytype).child("ItemsOwned").child(itemname).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                String s=dataSnapshot.getValue().toString();
                                qty=Integer.parseInt(s);
                                qty=qty+qtobuy;companymoney=companymoney-itemcost*qtobuy;
                                uref.child("CompaniesOwned").child(companytype).child("cash").setValue(companymoney);
                            }catch (Exception n){
                                qty=qtobuy;
                            }finally {
                                uref.child("CompaniesOwned").child(companytype).child("ItemsOwned").child(ItemName).setValue(qty);
                                Toast.makeText(getContext(),"Item bought ",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else Toast.makeText(getContext(),"The Item cost is greater than the Cash in Company",Toast.LENGTH_LONG).show();
            }

        });
        return convertView;
    }
}