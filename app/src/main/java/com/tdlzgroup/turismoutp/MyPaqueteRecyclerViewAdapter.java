package com.tdlzgroup.turismoutp;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdlzgroup.turismoutp.PaqueteFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyPaqueteRecyclerViewAdapter extends RecyclerView.Adapter<MyPaqueteRecyclerViewAdapter.ViewHolder> {

    private final List<ModelPaquete> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;

    public MyPaqueteRecyclerViewAdapter(Context context, List<ModelPaquete> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_paquete, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        Glide.with(context).load(mValues.get(position).getUrl()).into(holder.mImagen);
        holder.mNombre.setText(mValues.get(position).getNombre());
        holder.mPrecioAdulto.setText("S/ "+mValues.get(position).getOpciones().get(0).getPrecioAdulto());
        holder.mPrecioNino.setText("S/ "+mValues.get(position).getOpciones().get(0).getPrecioNino());
        holder.mRating.setRating( (float) mValues.get(position).getPuntaje());
        holder.mVotos.setText("("+mValues.get(position).getVotos()+")");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objPaquete", mValues.get(position));
                    Navigation.findNavController(vista).navigate(R.id.navigation_paquete_detalle, bundle);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImagen;
        public final TextView mNombre;
        public final TextView mPrecioAdulto;
        public final TextView mPrecioNino;
        public final RatingBar mRating;
        public final TextView mVotos;
        public ModelPaquete mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImagen = view.findViewById(R.id.item_imagen);
            mNombre = view.findViewById(R.id.item_nombre);
            mPrecioAdulto = view.findViewById(R.id.item_precio_adulto);
            mPrecioNino = view.findViewById(R.id.item_precio_nino);
            mRating = view.findViewById(R.id.item_rating);
            mVotos = view.findViewById(R.id.item_votos);
        }

    }
}
