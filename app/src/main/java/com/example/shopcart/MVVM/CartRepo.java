package com.example.shopcart.MVVM;

import com.example.shopcart.Model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<Cart> cartList = new ArrayList<>();
    CartINTERFACEShit interfaceCart;

    public CartRepo(CartINTERFACEShit interfaceCart) {
        this.interfaceCart = interfaceCart;
    }

    public void getCartShit() {

        assert firebaseAuth!=null;
        userid = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("Cart"+userid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot value,  FirebaseFirestoreException error) {

                cartList.clear();


                for (DocumentSnapshot ds: value.getDocuments()) {

                    Cart cart = ds.toObject(Cart.class);

                    cartList.add(cart);

                    interfaceCart.CartlIST(cartList);



                }


            }


        });




    }


    public interface CartINTERFACEShit{
        void CartlIST(List<Cart> carts);
    }
}