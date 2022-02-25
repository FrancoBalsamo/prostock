package com.frabasoft.providusstock.Fragmentos;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaCartuchos;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
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

        listadoCartuchosSQLite();
        adaptador = new RecyclerViewListaCartuchos(getActivity(), listaCartuchos);

        alerta.setOnClickListener(v -> {

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
}