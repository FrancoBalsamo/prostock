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
import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaToners;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
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

        listadoSQLiteToners();
        adaptador = new RecyclerViewListaToners(getActivity(), listaToners);

        alerta.setOnClickListener(v -> {
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
}