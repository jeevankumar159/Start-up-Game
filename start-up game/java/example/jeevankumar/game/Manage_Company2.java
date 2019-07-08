package example.jeevankumar.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Manage_Company2 extends AppCompatActivity {
    Button buybutton;
    Button producebutton;
    Button Sellbutton;
    Button FindInvestors;
    Button Marketbutton;
    TextView name;
    static String companytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__company2);
        name=(TextView)findViewById(R.id.textView3);
        Intent i=getIntent();
        companytype=i.getStringExtra("parentcompanyname");
        name.setText(companytype);
    }
    public void onbuybuttonclick(View view){
        buybutton=(Button)findViewById(R.id.button_buyitems);
        Intent buyitemsintent=new Intent(Manage_Company2.this,Buy_Items.class);
        buyitemsintent.putExtra("companytype",companytype);
        startActivity(buyitemsintent);
    }
    public void onproducebuttonclick(View view){
        producebutton=(Button)findViewById(R.id.button_produceitems);
        startActivity(new Intent(Manage_Company2.this,Produce_Items1.class));
    }
    public void onsellbuttonclick(View view){
        Sellbutton=(Button)findViewById(R.id.button_sellitems);
        startActivity(new Intent(Manage_Company2.this,Sell_Items1.class));
    }
    public void onmarketbuttonclick(View view){
        Marketbutton=(Button)findViewById(R.id.button_market);
        startActivity(new Intent(Manage_Company2.this,Market.class));
    }
    public static String getCompanytype(){
        return companytype;
}
}
