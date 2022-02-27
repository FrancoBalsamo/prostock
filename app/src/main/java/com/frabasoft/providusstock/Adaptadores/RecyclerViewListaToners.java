package com.frabasoft.providusstock.Adaptadores;

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
import com.frabasoft.providusstock.Actividades.Editar.EditarToners;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.R;
import java.util.ArrayList;

public class RecyclerViewListaToners extends RecyclerView.Adapter<RecyclerViewListaToners.ViewHolder> {
    ArrayList<Toners> tonersArrayList;
    Context context;

    public RecyclerViewListaToners(Context context, ArrayList<Toners> tonersArrayList){
        this.tonersArrayList = tonersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewListaToners.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.vista_diseno_toner, viewGroup, false);
        return new RecyclerViewListaToners.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewListaToners.ViewHolder viewHolder, int position){
        viewHolder.setData(tonersArrayList.get(position));
    }

    @Override
    public int getItemCount(){return tonersArrayList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView cardView;
        public TextView idToner;
        public TextView modeloColor;
        public TextView fechaModificacion;
        public ImageView ivToner;
        Toners toners;

        public ViewHolder(View v){
            super(v);

            v.setOnClickListener(this);
            cardView = v.findViewById(R.id.cardViewToner);
            idToner = v.findViewById(R.id.idToner);
            modeloColor = v.findViewById(R.id.tvModeloColorToner);
            fechaModificacion = v.findViewById(R.id.tvFechaModificacionToner);
            ivToner = v.findViewById(R.id.ivTonerImagen);
        }

        public void setData(Toners toners){
            this.toners = toners;
            String dato = toners.getModelo() + " " + toners.getColor() + " - Cantidad: " + toners.getCantidad();
            String idT = String.valueOf(toners.getIdToner());
            String fechaMod = "Última modificación: " + toners.getFechaModificacion();

            idToner.setText(idT);
            modeloColor.setText(dato);
            fechaModificacion.setText(fechaMod);

            if(toners.getModelo().equals("AOX5195")){
                ivToner.setImageResource(R.drawable.biznegro);
            }
            if(toners.getModelo().equals("AOX5295")){
                ivToner.setImageResource(R.drawable.bizam);
            }
            if(toners.getModelo().equals("AOX5395")){
                ivToner.setImageResource(R.drawable.bizmag);
            }
            if(toners.getModelo().equals("AOX5495")){
                ivToner.setImageResource(R.drawable.bizcy);
            }
            if(toners.getModelo().equals("CE410A")){
                ivToner.setImageResource(R.drawable.hptrescerocinconegr);
            }
            if(toners.getModelo().equals("CE411A")){
                ivToner.setImageResource(R.drawable.hptrescerocincocy);
            }
            if(toners.getModelo().equals("CE412A")){
                ivToner.setImageResource(R.drawable.hptrescerocinco);
            }
            if(toners.getModelo().equals("CE413A")){
                ivToner.setImageResource(R.drawable.hptrescerocincomag);
            }
            if(toners.getModelo().equals("CE435")){
                ivToner.setImageResource(R.drawable.cecuatrotrescinco);
            }
            if(toners.getModelo().equals("CF217A")){
                ivToner.setImageResource(R.drawable.cfdosunosiete);
            }
            if(toners.getModelo().equals("CF248A")){
                ivToner.setImageResource(R.drawable.cfdoscuatroocho);
            }
            if(toners.getModelo().equals("CF283")){
                ivToner.setImageResource(R.drawable.cfdosochotres);
            }
            if(toners.getModelo().equals("GPR-39")){
                ivToner.setImageResource(R.drawable.gpr);
            }
            if(toners.getModelo().equals("LASAMD111")){
                ivToner.setImageResource(R.drawable.lasamdunounouno);
            }
            if(toners.getModelo().equals("MLTD101S")){
                ivToner.setImageResource(R.drawable.mltdunocerouno);
            }
            if(toners.getModelo().equals("MLTD111SCOMPV2")){
                ivToner.setImageResource(R.drawable.mltdunounouno);
            }
            if(toners.getModelo().equals("QT650")){
                ivToner.setImageResource(R.drawable.qtseiscincocero);
            }
            if(toners.getModelo().equals("TN360")){
                ivToner.setImageResource(R.drawable.tntresseiscero);
            }
            if(toners.getModelo().equals("TN450")){
                ivToner.setImageResource(R.drawable.tncuatrocincocero);
            }
            if(toners.getModelo().equals("TN580")){
                ivToner.setImageResource(R.drawable.tncincoohcocero);
            }
            if(toners.getModelo().equals("TN750")){
                ivToner.setImageResource(R.drawable.tnsietecincocero);
            }
            if(toners.getModelo().equals("TN890")){
                ivToner.setImageResource(R.drawable.tnochonuevecero);
            }
            if(toners.getModelo().equals("TN3499")){
                ivToner.setImageResource(R.drawable.tntresmil);
            }
            if(toners.getModelo().equals("W1103A")){
                ivToner.setImageResource(R.drawable.wunouno);
            }
            if(toners.getModelo().equals("Q2612A")){
                ivToner.setImageResource(R.drawable.qdosseis);
            }
        }

        @Override
        public void onClick(View view){
            try{
                Intent abrirEditar = new Intent(context, EditarToners.class);
                int itemPosition = getLayoutPosition();
                abrirEditar.putExtra("position", String.valueOf(itemPosition));
                abrirEditar.putExtra("id", String.valueOf(toners.getIdToner()));
                abrirEditar.putExtra("cantidad", String.valueOf(toners.getCantidad()));
                context.startActivity(abrirEditar);
            }catch (Exception e){
                Log.d("ADTToners", "onClick: "+ e.getMessage());
            }
        }
    }
}