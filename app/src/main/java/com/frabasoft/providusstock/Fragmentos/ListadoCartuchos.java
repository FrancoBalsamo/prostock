package com.frabasoft.providusstock.Fragmentos;

import android.app.AlertDialog;
import android.content.Context;
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

import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaCartuchos;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ListadoCartuchos extends Fragment {
    TextView alerta;
    LinearLayout linearAlerta;
    RecyclerView recyclerView;
    RecyclerViewListaCartuchos adaptador;
    View view;
    ArrayList<Cartuchos> listaCartuchos = new ArrayList<>();

    public ListadoCartuchos() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listado, container, false);

        alerta = view.findViewById(R.id.tvAlertaCantidadCartuchos);
        linearAlerta = view.findViewById(R.id.linearAlertaCartucho);
        recyclerView = view.findViewById(R.id.rvCartuchos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        alerta.setText(getString(R.string.detalle));

        listadoCartuchosSQLite();
        adaptador = new RecyclerViewListaCartuchos(getActivity(), listaCartuchos);

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
        listadoCartuchosSQLite();
    }

    public void listadoCartuchosSQLite(){
        listaCartuchos.clear();
        DBAdapter dbAdapter = new DBAdapter(getContext());
        dbAdapter.abrirDB();

        Cursor cursor = dbAdapter.traerTodosCartuchos();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String modelo = cursor.getString(1);
            String color = cursor.getString(2);
            int cantidad = cursor.getInt(3);
            String fechaModificacion = cursor.getString(4);

            Cartuchos cartuchos = new Cartuchos(id, modelo, color, cantidad, fechaModificacion);
            listaCartuchos.add(cartuchos);
        }
        if(!(listaCartuchos.size() < 1)){
            recyclerView.setAdapter(adaptador);
        }
        dbAdapter.cerrarDB();
    }

    private void listadoStockMinimo(){
        StringBuilder mensaje = new StringBuilder();
        if(listaCartuchos.size() >= 1){
            for(int i = 0; i<  listaCartuchos.size(); i++){
                if(listaCartuchos.get(i).getCantidad() <= 4){
                    mensaje.append(listaCartuchos.get(i).getModelo())
                            .append(" ")
                            .append(listaCartuchos.get(i).getColor())
                            .append(" ").append(listaCartuchos.get(i).getCantidad())
                            .append("\n");
                }
            }
        }
        new AlertDialog.Builder(getContext())
                .setTitle("¡Atención!")
                .setMessage(mensaje.toString())
                .setPositiveButton("Enviar", (dialogInterface, i) -> {
                    StringBuilder enviarPedido = new StringBuilder();
                    if(listaCartuchos.size() >= 1){
                        for(int val = 0; val<  listaCartuchos.size(); val++){
                            if(listaCartuchos.get(val).getCantidad() <= 4){
                                String inicioMensaje = "René, estaría necesitando los siguientes cartuchos: \n";
                                enviarPedido.append(listaCartuchos.get(val).getModelo())
                                        .append(" ")
                                        .append(listaCartuchos.get(val).getColor())
                                        .append(" ").append(6 - listaCartuchos.get(val).getCantidad())
                                        .append("\n");
                                abrirWhatsapp(requireContext(), inicioMensaje + enviarPedido);
                            }
                        }
                    }
                })
                .show();
    }

    private void abrirWhatsapp(Context context, String pedido){
        PackageManager packageManager = context.getPackageManager();
        try{
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent abrir = new Intent(Intent.ACTION_VIEW);
            String url;
            try {
                url = "https://api.whatsapp.com/send?phone=543515443150&text=" + URLEncoder.encode(pedido, "UTF-8");

                abrir.setPackage("com.whatsapp");
                abrir.setData(Uri.parse(url));
                startActivity(abrir);
            } catch (UnsupportedEncodingException e) {
                Log.d("AbrirChat", "abrirChatEvolusoft: " + e.getMessage());
                e.printStackTrace();
            }
        }catch (PackageManager.NameNotFoundException notFoundException){
            Log.d("WhatsAppCart", "instalacionWhatsApp: " + notFoundException.getMessage());
        }
    }
}