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

import java.util.HashMap;

public class RegisterFragment extends Fragment {

    SwitchCompat toggle;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    NavController navController;
    TextInputLayout etname, etemail, etpassword;
    String name, email, password;
    Button signUp;



    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etemail = view.findViewById(R.id.emailfield);
        etname = view.findViewById(R.id.nameField);
        etpassword = view.findViewById(R.id.passwordfield);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        signUp = view.findViewById(R.id.signUpButton);


        toggle = view.findViewById(R.id.gotosignInSwitch);
        navController  = Navigation.findNavController(view);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getEditText().getText().toString();
                password = etpassword.getEditText().getText().toString();
                email = etemail.getEditText().getText().toString();

                if (name.isEmpty()) {


                    etname.setError("Enter name");
                } else if (email.isEmpty()) {

                    etemail.setError("Enter Email");


                } else if (password.isEmpty() || password.length() < 6) {

                    etpassword.setError("Password length must be more than 6");

                } else {

                    CreateUsers(name, email, password);

                }



            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {

                    /// if user has an account
                    /// take back the user to login fragment.

                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                }

            }
        });



    }


    // creating a user for us

    private void CreateUsers(String name, String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = auth.getCurrentUser();

                    String userid = user.getUid();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("username", name);
                    hashMap.put("email", email);
                    hashMap.put("userid", userid);


                    firestore.collection("Users").document().set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });


                    Toast.makeText(getContext(), "Registered", Toast.LENGTH_SHORT).show();


                    // if the user is registered already
                    // take the user to product list

                    navController.navigate(R.id.action_registerFragment_to_productsFragment);



                }

            }
        });






    }


}