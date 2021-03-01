package com.example.shopcart.MVVM;

import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;

import com.example.shopcart.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    List<Product> productList = new ArrayList<>();

    OnProductInter interfaceprodcuts;

    public ProductRepo(OnProductInter interfaceprodcuts) {
        this.interfaceprodcuts = interfaceprodcuts;
    }

    public void GetAllPRO() {

        firestore.collection("Products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                productList.clear();

                for (DocumentSnapshot ds: value.getDocuments()) {

                    Product product = ds.toObject(Product.class);


                    productList.add(product);

                    interfaceprodcuts.Products(productList);



                }
            }
        });






    }


    public interface OnProductInter{
        void Products(List<Product> products);
    }
}
