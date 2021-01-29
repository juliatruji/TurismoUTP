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
import com.tdlzgroup.turismoutp.CompraFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyCompraRecyclerViewAdapter extends RecyclerView.Adapter<MyCompraRecyclerViewAdapter.ViewHolder> {

    private final List<ModelCompra> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;

    public MyCompraRecyclerViewAdapter(Context context, List<ModelCompra> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_compra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(context).load(mValues.get(position).getUrl()).into(holder.mImagen);
        holder.mNombre.setText(mValues.get(position).getNombre());
        holder.mPrecioTotal.setText("Total: S/ "+mValues.get(position).getPrecioTotal());
        holder.mOpcion.setText(mValues.get(position).getOpcion());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objCompra", mValues.get(position));
                    Navigation.findNavController(v).navigate(R.id.navigation_compra_detalle, bundle);
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
        public final TextView mPrecioTotal;
        public final TextView mOpcion;
        public ModelCompra mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImagen = view.findViewById(R.id.item_imagen);
            mNombre = view.findViewById(R.id.item_nombre);
            mPrecioTotal = view.findViewById(R.id.item_precio_total);
            mOpcion = view.findViewById(R.id.item_opcion);
        }

    }
}
