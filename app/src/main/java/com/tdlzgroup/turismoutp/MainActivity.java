package com.tdlzgroup.turismoutp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    private List<AuthUI.IdpConfig> providers;
    private static final int MY_REQUEST_CODE = 700; //codigo de respuesta del login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton button = findViewById(R.id.ingresar);

        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        user = FirebaseAuth.getInstance().getCurrentUser();

        button.setOnClickListener( v1 -> {
            if (user != null)
                getDatosUsuario(user);
            else
                showSignInOptions();
        });
    }

    private void getDatosUsuario(FirebaseUser user) {
        String userURL = "https://www.fourjay.org/myphoto/s/20/206383_user-png.png";
        String userID = user.getUid();
        String userNombre = user.getDisplayName();
        Intent intent = new Intent(this, Principal.class);
        intent.putExtra("id", userID);
        intent.putExtra("nombre", userNombre);
        intent.putExtra("url", userURL);
        startActivity(intent);
    }

    private void showSignInOptions() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyThemeFireAuth)
                .setIsSmartLockEnabled(false)
                .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                getDatosUsuario(user);
            } else {

            }
        }
    }
}
