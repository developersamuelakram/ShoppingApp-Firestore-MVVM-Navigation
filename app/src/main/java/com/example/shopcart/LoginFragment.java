package com.example.shopcart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopcart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginFragment extends Fragment {

    SwitchCompat toggle;
    NavController navController;


    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextInputLayout  etemail, etpassword;
    String  email, password;
    Button signIn;

    public LoginFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        toggle = view.findViewById(R.id.gotosignUpSwitch);
        navController  = Navigation.findNavController(view);


        etemail = view.findViewById(R.id.emailfieldSignIn);
        etpassword = view.findViewById(R.id.passwordfieldSignIn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        signIn = view.findViewById(R.id.signInButton);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etemail.getEditText().getText().toString();
                password = etpassword.getEditText().getText().toString();


                if (email.isEmpty()) {

                    etemail.setError("Enter Email");


                } else  if (password.isEmpty() || password.length() < 6) {

                    etpassword.setError("Password length must be more than 6");

                } else {


                    SignInUser(email, password);



                }





            }
        });



        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {

                    navController.navigate(R.id.action_loginFragment_to_registerFragment);
                }

            }
        });




    }


    // this is helping us to sign in the user


    private void SignInUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    // login and take me to product fragment.

                    navController.navigate(R.id.action_loginFragment_to_productsFragment);
                    Toast.makeText(getContext(), "Signed In", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // it should directly take us to product fragment
    // if you have signed in once.

    @Override
    public void onStart() {

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser!=null) {

            navController.navigate(R.id.action_loginFragment_to_productsFragment);



        }
        super.onStart();
    }
}