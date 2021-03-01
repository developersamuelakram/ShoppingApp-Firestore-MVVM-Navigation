package com.example.shopcart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.shopcart.Model.Product;
import com.example.shopcart.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {


    List<Product> productList;
    OnClickedProducct interfaceclickedonProduct;

    public ProductsAdapter(OnClickedProducct interfaceclickedonProduct) {
        this.interfaceclickedonProduct = interfaceclickedonProduct;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productstyleslist, parent, false);
        return  new ProductHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.titeofproduct.setText(productList.get(position).getTitle());
        holder.descriptionofproduct.setText(productList.get(position).getDescription());

        Glide.with(holder.itemView.getContext()).load(productList.get(position).getImageUrl()).centerCrop().into(holder.circleImageView);



    }

    @Override
    public int getItemCount() {



        if (productList == null) {

            return 0;

        } else {

            return productList.size();
        }
    }

    class ProductHolder extends ViewHolder implements View.OnClickListener{


        TextView titeofproduct, descriptionofproduct;
        CircleImageView circleImageView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            titeofproduct = itemView.findViewById(R.id.proudcttitlte);
            descriptionofproduct = itemView.findViewById(R.id.productdescriptionmain);
            circleImageView = itemView.findViewById(R.id.productmainiamge);


            titeofproduct.setOnClickListener(this);
            descriptionofproduct.setOnClickListener(this);
            circleImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            interfaceclickedonProduct.OnProClicked(productList, getAdapterPosition());
        }
    }

    public interface OnClickedProducct{
        void OnProClicked(List<Product> productList, int position);


    }
}
