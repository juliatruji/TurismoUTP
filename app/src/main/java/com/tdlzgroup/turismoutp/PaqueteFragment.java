package com.tdlzgroup.turismoutp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaqueteFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private List<ModelPaquete> milista;
    private FirebaseFirestore db;

    public PaqueteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paquete_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Paquetes Turísticos");
        Context context = view.getContext();

        db = FirebaseFirestore.getInstance();

        milista = new ArrayList<>();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        LlenaMILista();
        return view;
    }

    private void LlenaMILista() {
        db.collection("paquete").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()){
                            Toast.makeText(getContext(), "No hay Paquetes", Toast.LENGTH_SHORT).show();
                        }
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ModelPaquete paquete = document.toObject(ModelPaquete.class);
                            paquete.setIdPaquete(document.getId());
                            paquete.setLatitud(document.getGeoPoint("mapa").getLatitude());
                            paquete.setLongitud(document.getGeoPoint("mapa").getLongitude());
                            milista.add(paquete);
                        }
                        recyclerView.setAdapter(new MyPaqueteRecyclerViewAdapter(getContext(), milista, mListener));
                    }
                    else {
                        Log.w("Error", "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ModelPaquete item);
    }
}
