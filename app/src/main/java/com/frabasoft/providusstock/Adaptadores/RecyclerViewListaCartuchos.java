package com.frabasoft.providusstock.Adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Actividades.Editar.EditarCartuchos;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.util.ArrayList;

public class RecyclerViewListaCartuchos extends RecyclerView.Adapter<RecyclerViewListaCartuchos.ViewHolder> {
    ArrayList<Cartuchos> cartuchosArrayList;
    Context context;

    public RecyclerViewListaCartuchos(Context context, ArrayList<Cartuchos> cartuchosArrayList) {
        this.context = context;
        this.cartuchosArrayList = cartuchosArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewListaCartuchos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_diseno_cartucho, null);

        return new RecyclerViewListaCartuchos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewListaCartuchos.ViewHolder Vholder, int position) {
        Vholder.setData(cartuchosArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartuchosArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView idCartucho;
        public TextView modeloColor;
        public TextView fechaModificacion;
        public ImageView ivCartucho;
        Cartuchos cartuchos;

        public ViewHolder(View v ) {
            super(v);

            v.setOnClickListener(this);
            cardView = v.findViewById(R.id.cardViewCartucho);
            idCartucho = v.findViewById(R.id.idCartucho);
            modeloColor = v.findViewById(R.id.tvModeloColorCartucho);
            fechaModificacion = v.findViewById(R.id.tvFechaModificacionCartucho);
            ivCartucho = v.findViewById(R.id.ivCartuchoImagen);
        }

        public void setData(Cartuchos cartuchos) {
            this.cartuchos = cartuchos;
            String dato = cartuchos.getModelo() + " " + cartuchos.getColor() + " - Cantidad: " + cartuchos.getCantidad();
            String idC = String.valueOf(cartuchos.getIdCartucho());
            String fechaMod = "Última actualización: " + cartuchos.getFechaModificacion();
            idCartucho.setText(idC);
            modeloColor.setText(dato);
            fechaModificacion.setText(fechaMod);

            if(cartuchos.getModelo().equals("73")){
                ivCartucho.setImageResource(R.drawable.sietetresn);
            }
            if(cartuchos.getModelo().equals("90")){
                ivCartucho.setImageResource(R.drawable.nueveceron);
            }
            if(cartuchos.getModelo().equals("117")){
                ivCartucho.setImageResource(R.drawable.unounosiete);
            }
            if(cartuchos.getModelo().equals("133")){
                ivCartucho.setImageResource(R.drawable.unotrestres);
            }
            if(cartuchos.getModelo().equals("135")){
                ivCartucho.setImageResource(R.drawable.unotrescinco);
            }
            if(cartuchos.getModelo().equals("195")){
                ivCartucho.setImageResource(R.drawable.unonuevecinco);
            }
            if(cartuchos.getModelo().equals("196") ){
                ivCartucho.setImageResource(R.drawable.unonueveseis);
            }
            if(cartuchos.getModelo().equals("206")){
                ivCartucho.setImageResource(R.drawable.dosceroseis);
            }
            if(cartuchos.getModelo().equals("296")){
                ivCartucho.setImageResource(R.drawable.dosnueveseis);
            }
            if(cartuchos.getModelo().contains("544")){
                ivCartucho.setImageResource(R.drawable.tcincocuatrocuatro);
            }
            if(cartuchos.getModelo().equals("E2061")){
                ivCartucho.setImageResource(R.drawable.edosceroseisuno);
            }
            if(cartuchos.getModelo().equals("T6644")){
                ivCartucho.setImageResource(R.drawable.vertec);
            }
            if(cartuchos.getModelo().equals("T6643")){
                ivCartucho.setImageResource(R.drawable.vertec);
            }
            if(cartuchos.getModelo().equals("T6642")){
                ivCartucho.setImageResource(R.drawable.vertec);
            }
            if(cartuchos.getModelo().equals("T6641")){
                ivCartucho.setImageResource(R.drawable.vertec);
            }
        }

        @Override
        public void onClick(View view) {
            try{
                new AlertDialog.Builder(context)
                        .setTitle("¡Atención!")
                        .setMessage("¿Desea eliminar o modificar el item seleccionado?")
                        .setPositiveButton("Modificar", (dialogInterface, i) -> {
                            Intent abrirEditar = new Intent(context, EditarCartuchos.class);
                            int itemPosition = getLayoutPosition();
                            abrirEditar.putExtra("position", String.valueOf(itemPosition));
                            abrirEditar.putExtra("id", String.valueOf(cartuchos.getIdCartucho()));
                            abrirEditar.putExtra("cantidad", String.valueOf(cartuchos.getCantidad()));
                            context.startActivity(abrirEditar);
                        })
                        .setNegativeButton("Eliminar", (dialogInterface, i) -> {
                            int id = cartuchos.getIdCartucho();
                            new DBAdapter(context).eliminarCartucho(id);
                        })
                        .show();

            }catch (Exception e){
                Log.d("ADTCart", "onClick: " + e.getMessage());
            }
        }
    }
}