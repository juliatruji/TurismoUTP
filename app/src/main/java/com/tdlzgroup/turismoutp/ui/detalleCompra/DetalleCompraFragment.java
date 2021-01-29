package com.tdlzgroup.turismoutp.ui.detalleCompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdlzgroup.turismoutp.MapaActivity;
import com.tdlzgroup.turismoutp.ModelCompra;
import com.tdlzgroup.turismoutp.ModelPaquete;
import com.tdlzgroup.turismoutp.Principal;
import com.tdlzgroup.turismoutp.R;

public class DetalleCompraFragment extends Fragment {

    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detalle_compra, container, false);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((Principal) getActivity()).setSupportActionBar(toolbar);
        ((Principal) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Principal) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
        ((Principal) getActivity()).getSupportActionBar().show();

        MaterialButton btnMapa = root.findViewById(R.id.item_btn_mapa);
        MaterialButton btnEliminar = root.findViewById(R.id.item_btn_eliminar);

        ImageView imgItem = root.findViewById(R.id.item_imagen);
        TextView descripcion = root.findViewById(R.id.item_descripcion);
        TextView opcion = root.findViewById(R.id.item_opcion);
        TextView cantidad_adultos = root.findViewById(R.id.item_cantidad_adultos);
        TextView cantidad_ninos = root.findViewById(R.id.item_cantidad_ninos);
        TextView precio_total = root.findViewById(R.id.item_precio_total);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        if ( bundle == null) {
            return root;
        }

        ModelCompra objCompra = (ModelCompra) bundle.getSerializable("objCompra");
        toolbar.setTitle(objCompra.getNombre());

        Glide.with(getContext()).load(objCompra.getUrl()).into(imgItem);
        descripcion.setText(objCompra.getDescripcion());
        opcion.setText(objCompra.getOpcion());
        cantidad_adultos.setText(""+objCompra.getCantAdulto());
        cantidad_ninos.setText(""+objCompra.getCantNino());
        precio_total.setText("Pago Total: S/ "+objCompra.getPrecioTotal());

        btnMapa.setOnClickListener(v1 -> {
            Intent activityfragmapa = new Intent(getContext(), MapaActivity.class);
            activityfragmapa.putExtra("latitud", objCompra.getLatitud());
            activityfragmapa.putExtra("longitud", objCompra.getLongitud());
            activityfragmapa.putExtra("nombre", objCompra.getNombre());
            startActivity(activityfragmapa);
        });

        btnEliminar.setOnClickListener(v2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirmar acción")
            .setPositiveButton(android.R.string.yes, (dialog, id) ->
                    eliminarCompra(root, objCompra.getIdCompra()))
            .setNegativeButton(android.R.string.no, null);
            builder.show();
        });

        return root;
    }

    private void eliminarCompra(View viewroot, String idCompra) {
        db.collection("compra").document(idCompra).delete()
                .addOnSuccessListener(aVoid ->{
                    Toast.makeText(getContext(), "Compra Eliminada", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(viewroot).navigate(R.id.navigation_lista_compra);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Ocurrió un Error", Toast.LENGTH_SHORT).show());

    }
}