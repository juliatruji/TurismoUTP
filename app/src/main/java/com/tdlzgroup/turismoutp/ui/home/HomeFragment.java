package com.tdlzgroup.turismoutp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tdlzgroup.turismoutp.MainActivity;
import com.tdlzgroup.turismoutp.MapaActivity;
import com.tdlzgroup.turismoutp.Principal;
import com.tdlzgroup.turismoutp.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = root.findViewById(R.id.item_nombre);
        ImageView imagen = root.findViewById(R.id.item_imagen);
        MaterialButton btnCerrarSesion = root.findViewById(R.id.item_btn_cerrar_sesion);

        Toolbar toolbar = root.findViewById(R.id.toolbar);

        toolbar.setTitle("Turismo UTP");
        textView.setText(Principal.userNombre);
        Glide.with(getContext()).load(Principal.userURL).into(imagen);

        btnCerrarSesion.setOnClickListener(vc -> {
           AuthUI.getInstance()
                    .signOut(getContext())
                    .addOnCompleteListener(task -> {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }).addOnFailureListener(e -> {
    });
});

        return root;
    }
}