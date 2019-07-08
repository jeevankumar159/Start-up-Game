package example.jeevankumar.game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class sellitemAdapter extends ArrayAdapter<sell_items_details> {
    private Context mcontext;
    private int mresource;
    public sellitemAdapter(@NonNull Context context, int resource, @NonNull List<sell_items_details> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.mresource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView =inflater.inflate(mresource,parent,false);
        String marketitemname=getItem(position).getSellitemname();
        String marketitemenergy=getItem(position).getSellitemenergy();
        String marketitemcost=getItem(position).getSellitemcost();
        TextView sellitemname=(TextView)convertView.findViewById(R.id.textView_marketproductname);
        TextView sellitemenergy=(TextView)convertView.findViewById(R.id.textView_marketitemenergy);
        TextView sellitemcost=(TextView)convertView.findViewById(R.id.textView_marketitemcost);
        Button marketbuybutton=(Button)convertView.findViewById(R.id.button_marketbuy);
        sellitemname.setText(marketitemname);
        sellitemcost.setText(marketitemcost);
        sellitemenergy.setText(marketitemenergy);
        return convertView;
    }
}
