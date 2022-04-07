package com.frabasoft.providusstock.Fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaToners;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ListadoToners extends Fragment {
    TextView alerta;
    LinearLayout linearAlerta;
    RecyclerView recyclerView;
    RecyclerViewListaToners adaptador;
    View view;
    ArrayList<Toners> listaToners = new ArrayList<>();

    public ListadoToners() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listado_toners, container, false);

        alerta = view.findViewById(R.id.tvAlertaCantidadToners);
        linearAlerta = view.findViewById(R.id.linearAlertaToners);
        recyclerView = view.findViewById(R.id.rvToners);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        alerta.setText(getString(R.string.detalle));

        listadoSQLiteToners();
        adaptador = new RecyclerViewListaToners(getActivity(), listaToners);

        alerta.setOnClickListener(v -> {
            try {
                listadoStockMinimo();
            }catch (Exception e){
                Log.d("alerta", "listadoStockMinimo: " + e.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listadoSQLiteToners();
    }

    private void listadoSQLiteToners() {
        listaToners.clear();
        DBAdapter dbAdapter = new DBAdapter(getContext());
        dbAdapter.abrirDB();

        Cursor cursor = dbAdapter.traerTodosToners();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String modelo = cursor.getString(1);
            String color = cursor.getString(2);
            int cantidad = cursor.getInt(3);
            String fechaModificacion = cursor.getString(4);

            Toners toners = new Toners(id, modelo, color, cantidad, fechaModificacion);
            listaToners.add(toners);
        }
        if(!(listaToners.size() < 1)){
            recyclerView.setAdapter(adaptador);
        }
        dbAdapter.cerrarDB();
    }

    private void listadoStockMinimo(){
        StringBuilder mensaje = new StringBuilder();
        if(listaToners.size() >= 1){
            for(int i = 0; i<  listaToners.size(); i++){
                if(listaToners.get(i).getCantidad() <= 2){
                    mensaje.append(listaToners.get(i).getModelo()).append(" ").append(listaToners.get(i).getColor()).append(" ").append(listaToners.get(i).getCantidad()).append("\n");
                }
            }
        }
        new AlertDialog.Builder(getContext())
                .setTitle("¡Atención!")
                .setMessage(mensaje.toString())
                .setPositiveButton("MACROX", (dialogInterface, i) -> {
                    String inicioMensajeMacrox = "Estimados, me comunico de la firma providus, estaría necesitando los siguientes toners: \n";

                    StringBuilder enviarPedido = new StringBuilder();
                    if(listaToners.size() >= 1){
                        for(int val = 0; val <  listaToners.size(); val++){
                            if(listaToners.get(val).getModelo().equals("AOX5195")
                                    && listaToners.get(val).getModelo().equals("AOX5295")
                                    && listaToners.get(val).getModelo().equals("AOX5395")
                                    && listaToners.get(val).getModelo().equals("AOX5495")
                                    && listaToners.get(val).getModelo().equals("GPR-39")
                                    && listaToners.get(val).getModelo().equals("QT650")
                                    && listaToners.get(val).getModelo().equals("TN360")
                                    && listaToners.get(val).getModelo().equals("TN450")
                                    && listaToners.get(val).getModelo().equals("TN580")
                                    && listaToners.get(val).getModelo().equals("TN750")
                                    && listaToners.get(val).getModelo().equals("TN890")){//para macrox
                                if(listaToners.get(val).getCantidad() <= 4){
                                    enviarPedido.append(listaToners.get(val).getModelo())
                                            .append(" ")
                                            .append(listaToners.get(val).getColor())
                                            .append(" ").append(4 - listaToners.get(val).getCantidad())
                                            .append("\n");
                                    try {
                                        abrirWhatsapp(requireContext(), "543514033968", inicioMensajeMacrox + enviarPedido);
                                    }catch (Exception e){
                                        Log.d("Error", "macrox: " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                })
                .setNegativeButton("EVOLUSOFT", (dialogInterface, i) -> {
                    String inicioMensajeEvolusoft = "René, estaría necesitando los siguientes toners: \n";
                    StringBuilder enviarPedido = new StringBuilder();
                    if(listaToners.size() >= 1){
                        for(int val = 0; val<  listaToners.size(); val++){
                            if(listaToners.get(val).getModelo().equals("CE410A")
                                    && listaToners.get(val).getModelo().equals("CE411A")
                                    && listaToners.get(val).getModelo().equals("CE412A")
                                    && listaToners.get(val).getModelo().equals("CE413A")
                                    && listaToners.get(val).getModelo().equals("CE435")
                                    && listaToners.get(val).getModelo().equals("CF217A")
                                    && listaToners.get(val).getModelo().equals("CF248A")
                                    && listaToners.get(val).getModelo().equals("CF283")
                                    && listaToners.get(val).getModelo().equals("LASAMD111")
                                    && listaToners.get(val).getModelo().equals("MLTD101S")
                                    && listaToners.get(val).getModelo().equals("Q2612A")
                                    && listaToners.get(val).getModelo().equals("W1103A")
                                    && listaToners.get(val).getModelo().equals("W1105A")){//para EVOLUSOFT
                                if(listaToners.get(val).getCantidad() <= 4){
                                    enviarPedido.append(listaToners.get(val).getModelo())
                                            .append(" ")
                                            .append(listaToners.get(val).getColor())
                                            .append(" ").append(4 - listaToners.get(val).getCantidad())
                                            .append("\n");
                                    abrirWhatsapp(requireContext(), "543515443150", inicioMensajeEvolusoft + enviarPedido);
                                }
                            }
                        }
                    }
                })
                .show();
    }

    private void abrirWhatsapp(Context context, String numero, String pedido){
        PackageManager packageManager = context.getPackageManager();
        try{
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent abrir = new Intent(Intent.ACTION_VIEW);
            String url;
            try {
                url = "https://api.whatsapp.com/send?phone="+ numero + "&text=" + URLEncoder.encode(pedido, "UTF-8");

                abrir.setPackage("com.whatsapp");
                abrir.setData(Uri.parse(url));
                startActivity(abrir);
            } catch (UnsupportedEncodingException e) {
                Log.d("AbrirChat", "abrirChat: " + e.getMessage());
                e.printStackTrace();
            }
        }catch (PackageManager.NameNotFoundException notFoundException){
            Log.d("WhatsAppCart", "instalacionWhatsApp: " + notFoundException.getMessage());
        }
    }
}