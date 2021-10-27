package com.frabasoft.providusstock.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Clases.ConexionInternet;
import com.frabasoft.providusstock.Editar;
import com.frabasoft.providusstock.Fragmentos.ListadoCartuchos;
import com.frabasoft.providusstock.R;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListaCartuchos extends RecyclerView.Adapter<RecyclerViewListaCartuchos.ViewHolder> {
    ArrayList<Cartuchos> mValues;
    Context mContext;
    String url = "http://frabasoft.com.ar/pstock/editar_cantidad_cartuchos.php";
    int itemPosition;
    ListadoCartuchos listadoCartuchos;

    public RecyclerViewListaCartuchos(Context context, ArrayList<Cartuchos> values, ListadoCartuchos listadoCartuchos) {
        mValues = values;
        mContext = context;
        this.listadoCartuchos = listadoCartuchos;
    }

    @NonNull
    @Override
    public RecyclerViewListaCartuchos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vista_diseno_cartucho, parent, false);
        return new RecyclerViewListaCartuchos.ViewHolder(view, listadoCartuchos);
    }

    @Override
    public void onBindViewHolder(RecyclerViewListaCartuchos.ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView idCartucho;
        public TextView modeloColor;
        public TextView tv_fecha_mod_usuario_mod;
        public ImageView iv_cartucho_img;
        public View layout;
        Cartuchos item;
        ListadoCartuchos listadoCartuchos;

        public ViewHolder(View v, ListadoCartuchos listadoCartuchos) {
            super(v);
            layout = v;
            this.listadoCartuchos = listadoCartuchos;

            v.setOnClickListener(this);
            cardView = v.findViewById(R.id.cardViewCartucho);
            idCartucho = v.findViewById(R.id.idCartucho);
            modeloColor = v.findViewById(R.id.tvModeloColorCartucho);
            tv_fecha_mod_usuario_mod = v.findViewById(R.id.tvFechaModificacionCartucho);
            iv_cartucho_img = v.findViewById(R.id.ivCartuchoImagen);
        }

        public void setData(Cartuchos item) {
            this.item = item;
            String dato = item.getModelo() + " " + item.getColor() + " - Cantidad: " + item.getCantidad();
            String idC = String.valueOf(item.getIdCartucho());
            String fec_us = "Última actualización: " + item.getFechaModificacion();
            idCartucho.setText(idC);
            modeloColor.setText(dato);
            tv_fecha_mod_usuario_mod.setText(fec_us);

            if(item.getModelo().contains("73")){
                iv_cartucho_img.setImageResource(R.drawable.sietetresn);
            }
            if(item.getModelo().contains("90")){
                iv_cartucho_img.setImageResource(R.drawable.nueveceron);
            }
            if(item.getModelo().contains("117")){
                iv_cartucho_img.setImageResource(R.drawable.unounosiete);
            }
            if(item.getModelo().contains("195")){
                iv_cartucho_img.setImageResource(R.drawable.unonuevecinco);
            }
            if(item.getModelo().contains("196")){
                iv_cartucho_img.setImageResource(R.drawable.unonueveseis);
            }
            if(item.getModelo().contains("206")){
                iv_cartucho_img.setImageResource(R.drawable.dosceroseis);
            }
            if(item.getModelo().contains("296")){
                iv_cartucho_img.setImageResource(R.drawable.dosnueveseis);
            }
            if(item.getModelo().contains("E2061")){
                iv_cartucho_img.setImageResource(R.drawable.edosceroseisuno);
            }
            if(item.getModelo().contains("135")){
                iv_cartucho_img.setImageResource(R.drawable.unotrescinco);
            }
            if(item.getModelo().contains("T6644")){
                iv_cartucho_img.setImageResource(R.drawable.vertec);
            }
            if(item.getModelo().contains("T6643")){
                iv_cartucho_img.setImageResource(R.drawable.vertec);
            }
            if(item.getModelo().contains("T6642")){
                iv_cartucho_img.setImageResource(R.drawable.vertec);
            }
            if(item.getModelo().contains("T6641")){
                iv_cartucho_img.setImageResource(R.drawable.vertec);
            }
            if(item.getModelo().contains("544")){
                iv_cartucho_img.setImageResource(R.drawable.tcincocuatrocuatro);
            }
            if(item.getModelo().contains("133")){
                iv_cartucho_img.setImageResource(R.drawable.unotrestres);
            }
        }

        @Override
        public void onClick(View view) {
            itemPosition = getLayoutPosition();
            if(ConexionInternet.estaConectado(mContext)){
                Intent abrirEditar = new Intent(mContext, Editar.class);
                int itemPosition = getLayoutPosition();
                abrirEditar.putExtra("position", String.valueOf(itemPosition));
                abrirEditar.putExtra("id", String.valueOf(item.getIdCartucho()));
                abrirEditar.putExtra("cantidad", String.valueOf(item.getCantidad()));
                abrirEditar.putExtra("url", url);
                listadoCartuchos.mStartForResult.launch(abrirEditar);
                //mContext.startActivity(abrirEditar);
            }else{
                Toast.makeText(mContext, "Conéctate a una red.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}