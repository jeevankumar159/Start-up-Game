package example.jeevankumar.game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class ProductlistAdapter extends ArrayAdapter<Product_details> {
    private Context mcontext;
    private int mresource;
    public ProductlistAdapter(@NonNull Context context, int resource, @NonNull List<Product_details> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       String productname=getItem(position).getName();
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresource,parent,false);
        TextView product_textview=convertView.findViewById(R.id.textView_product_names);
        product_textview.setText(productname);
        return convertView;
    }
}
