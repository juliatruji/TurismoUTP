package com.tdlzgroup.turismoutp.ui.detallePaquete;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.tdlzgroup.turismoutp.MapaActivity;
import com.tdlzgroup.turismoutp.ModelPaquete;
import com.tdlzgroup.turismoutp.Principal;
import com.tdlzgroup.turismoutp.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetallePaqueteFragment extends Fragment {
    private RadioGroup opciones;
    private RadioButton opcion1;
    private RadioButton opcion2;
    private TextView precioAdulto;
    private TextView precioNino;

    private ModelPaquete objPaquete;
    private MaterialButton btnMapa;
    private MaterialButton btnComprar;
    private int cantAdultosInt;
    private int cantNinosInt;
    private int tempPrecio1;
    private int tempPrecio2;
    private String tempOpcion;
    private EditText cantAdultos;
    private EditText cantNinos;
    private TextView precioTotal;
    private int precioTotalInt;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detalle_paquete, container, false);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((Principal) getActivity()).setSupportActionBar(toolbar);
        ((Principal) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Principal) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
        ((Principal) getActivity()).getSupportActionBar().show();

        ImageView imgItem = root.findViewById(R.id.item_imagen);
        RatingBar ratingBar = root.findViewById(R.id.item_rating);
        TextView votos = root.findViewById(R.id.item_votos);
        TextView descripcion = root.findViewById(R.id.item_descripcion);

        precioAdulto = root.findViewById(R.id.item_precio_adulto);
        precioNino = root.findViewById(R.id.item_precio_nino);

        btnMapa = root.findViewById(R.id.item_btn_mapa);
        btnComprar = root.findViewById(R.id.item_btn_comprar);

        opciones = root.findViewById(R.id.item_opciones);
        opcion1 = root.findViewById(R.id.item_opcion1);
        opcion2 = root.findViewById(R.id.item_opcion2);

        cantAdultos = root.findViewById(R.id.item_cantidad_adultos);
        cantNinos = root.findViewById(R.id.item_cantidad_ninos);
        precioTotal = root.findViewById(R.id.item_precio_total);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        if ( bundle == null) {
            return root;
        }

        objPaquete = (ModelPaquete) bundle.getSerializable("objPaquete");
        toolbar.setTitle(objPaquete.getNombre());

        Glide.with(getContext()).load(objPaquete.getUrl()).into(imgItem);
        descripcion.setText(objPaquete.getDescripcion());
        ratingBar.setRating((float) objPaquete.getPuntaje());
        votos.setText("("+objPaquete.getVotos()+")");

        opcion1.setText(objPaquete.getOpciones().get(0).getNombre());
        opcion2.setText(objPaquete.getOpciones().get(1).getNombre());
        tempOpcion = objPaquete.getOpciones().get(0).getNombre();
        opcion1.setChecked(true);

        precioAdulto.setText("S/ "+objPaquete.getOpciones().get(0).getPrecioAdulto());
        precioNino.setText("S/ "+objPaquete.getOpciones().get(0).getPrecioNino());

        precioTotalInt = 0;
        tempPrecio1 = objPaquete.getOpciones().get(0).getPrecioAdulto();
        tempPrecio2 = objPaquete.getOpciones().get(0).getPrecioNino();

        opciones.setOnCheckedChangeListener((group, checkedId) -> {
            cambiarPrecios(objPaquete);
            calcularTotal();
        });

        cantAdultos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                calcularTotal();
            }
        });
        cantNinos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                calcularTotal();
            }
        });

        btnMapa.setOnClickListener(vc -> {
            Intent activityfragmapa = new Intent(vc.getContext(), MapaActivity.class);
            activityfragmapa.putExtra("latitud", objPaquete.getLatitud());
            activityfragmapa.putExtra("longitud", objPaquete.getLongitud());
            activityfragmapa.putExtra("nombre", objPaquete.getNombre());
            vc.getContext().startActivity(activityfragmapa);
        });

        btnComprar.setOnClickListener(v2 -> {
            if (precioTotalInt == 0) {
                Toast.makeText(getContext(), "Debe haber mínimo 1 persona", Toast.LENGTH_SHORT).show();
                return;
            }
            String txtmensaje = "• Lugar: "+ objPaquete.getNombre() +"\n"+ "• Adultos: "+ cantAdultosInt +"\n"+ "• Niños: "+
                    cantNinosInt +"\n"+ "• Opción: "+tempOpcion +"\n"+ "• Total: S/ "+ precioTotalInt + "\n\n" +"Ingrese su Tarjeta de Crédito";

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Completar compra");
            alertDialog.setMessage(txtmensaje);
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);
            alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> confirmarCompra(root));
            alertDialog.setNegativeButton(android.R.string.no, null);
            alertDialog.show();
        });

        return root;
    }

    private int checkOpciones(RadioGroup opciones) {
        if (opciones.getCheckedRadioButtonId() == opcion1.getId())
            return 0;
        else 
            return 1;

    }
    private void cambiarPrecios(ModelPaquete objPaquete){

        int pos=checkOpciones(opciones);

            precioAdulto.setText("S/ "+objPaquete.getOpciones().get(pos).getPrecioAdulto());
            precioNino.setText("S/ "+objPaquete.getOpciones().get(pos).getPrecioNino());
            tempPrecio1 = objPaquete.getOpciones().get(pos).getPrecioAdulto();
            tempPrecio2 = objPaquete.getOpciones().get(pos).getPrecioNino();
            tempOpcion = objPaquete.getOpciones().get(pos).getNombre();

    }
    private void calcularTotal(){
        String contenidoAdulto = cantAdultos.getText().toString();
        String contenidoNino = cantNinos.getText().toString();

        cantAdultosInt = 0;
        cantNinosInt = 0;

        if (!contenidoAdulto.equals(""))
            cantAdultosInt = Integer.parseInt(contenidoAdulto);
        if (!contenidoNino.equals(""))
            cantNinosInt = Integer.parseInt(contenidoNino);

        precioTotalInt = (tempPrecio1 * cantAdultosInt) + (tempPrecio2 * cantNinosInt);
        precioTotal.setText("Precio Total: S/ "+precioTotalInt);
    }

    private void confirmarCompra(View viewroot) {
        Calendar calendarFecha = Calendar.getInstance();
        GeoPoint puntomapa = new GeoPoint(objPaquete.getLatitud(), objPaquete.getLongitud());
        Timestamp fechaViaje = new Timestamp(calendarFecha.getTime());
        Timestamp fechaCompra = new Timestamp(calendarFecha.getTime());

        Map<String, Object> nuevacompra = new HashMap<>();

        nuevacompra.put("idUsuario", Principal.userID);
        nuevacompra.put("nombre", objPaquete.getNombre());
        nuevacompra.put("descripcion", objPaquete.getDescripcion());
        nuevacompra.put("url", objPaquete.getUrl());
        nuevacompra.put("mapa", puntomapa);
        nuevacompra.put("opcion", tempOpcion);
        nuevacompra.put("fechaViaje", fechaViaje);
        nuevacompra.put("fechaCompra", fechaCompra);
        nuevacompra.put("cantAdulto", cantAdultosInt);
        nuevacompra.put("cantNino", cantNinosInt);
        nuevacompra.put("precioTotal", precioTotalInt);

        db.collection("compra").add(nuevacompra)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(getContext(), "Operación exitosa.", Toast.LENGTH_LONG).show();
                Navigation.findNavController(viewroot).navigate(R.id.navigation_lista_compra);
            })
            .addOnFailureListener(e -> Toast.makeText(getContext(), "Ocurrió un error.", Toast.LENGTH_LONG).show());
    }
}