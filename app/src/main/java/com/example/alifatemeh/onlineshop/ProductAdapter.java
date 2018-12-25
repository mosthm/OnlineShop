package com.example.alifatemeh.onlineshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alifatemeh.onlineshop.Models.MypreferenceManager;
import com.example.alifatemeh.onlineshop.Models.Products;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<Products> items;
    Intent intent = new Intent();

    private Context context;
    public ProductAdapter(List<Products> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.template_product,viewGroup,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.productname.setText(items.get(i).getName());
        viewHolder.productprice.setText(items.get(i).getPrice());
        viewHolder.productimage.setImageURI(Uri.parse(items.get(i).getPhotoUrl()));
        viewHolder.id.setText(items.get(i).getId());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("intent_ok");

//                Intent intent = new Intent(context,RoomMessageFragment.class);
                intent.putExtra("idProducts",items.get(i).getId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                MypreferenceManager.getInstance(context).putIdproduct(items.get(i).getId());
                Products products=new Products();
                products.setId(items.get(i).getId());
                Log.d("TAG", "Id_product : " + items.get(i).getId());
                Log.d("TAG", "Id_product  & Name: "+ items.get(i).getName() );
//                context.startActivity(intent);
//                RoomMessageFragment roomMessageFragment=new RoomMessageFragment();
//                getFragmentManager().beginTransaction()
//                        .add(R.id.fragment_container,roomMessageFragment)
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView productprice;
        public TextView productname;
        public ImageView productimage;
        public ViewHolder(View itemView){
            super(itemView);
            productname=itemView.findViewById(R.id.product_name);
            productprice =itemView.findViewById(R.id.product_price);
            productimage =itemView.findViewById(R.id.product_image);
//            onClick(itemView,id);

        }
    }
}
