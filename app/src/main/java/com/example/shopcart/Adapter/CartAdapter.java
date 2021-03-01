package com.example.shopcart.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.shopcart.Model.Cart;
import com.example.shopcart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {


    List<Cart> cartList;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userid;
    String documentid;



    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartliststyle, parent, false);
        return new CartHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        holder.title.setText(cartList.get(position).getTitle());
        holder.price.setText("Total Price is: " + cartList.get(position).getPrice() + " For the items: " + cartList.get(position).getQuantity());

        Glide.with(holder.itemView.getContext()).load(cartList.get(position).getImageUrl()).centerCrop().into(holder.imageView);



    }

    @Override
    public int getItemCount() {

        if (cartList == null) {

            return 0;


        } else {
            return cartList.size();
        }
    }

    class CartHolder extends ViewHolder {


        TextView title, price;
        CircleImageView imageView;
        ImageButton deleteItem;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cartproducttitle);
            price = itemView.findViewById(R.id.cartdesc);
            imageView = itemView.findViewById(R.id.cartmainimage);
            deleteItem = itemView.findViewById(R.id.deleteitem);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title = cartList.get(getAdapterPosition()).getTitle();
                    userid = firebaseAuth.getCurrentUser().getUid();


                    documentid = cartList.get(getAdapterPosition()).getProductid();



                    // makes the values zero // if deleted
                    firestore.collection("Products").document(documentid).update("quantity", 0).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });


                    firestore.collection("Cart" + userid).document(title).delete();


                    cartList.remove(cartList.get(getAdapterPosition()));
                    notifyItemRemoved(getAdapterPosition());

                }

            });



        }
    }





}