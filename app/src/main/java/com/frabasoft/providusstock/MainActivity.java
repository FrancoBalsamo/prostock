package com.frabasoft.providusstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import com.frabasoft.providusstock.Actividades.Añadir.AnadirCartuchos;
import com.frabasoft.providusstock.Actividades.Añadir.AnadirToners;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.Fragmentos.ListadoCartuchos;
import com.frabasoft.providusstock.Fragmentos.ListadoToners;
import com.frabasoft.providusstock.Fragmentos.NoData;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Cartuchos> listaCartuchos = new ArrayList<>();
    ArrayList<Toners> listaToners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCartuchos = findViewById(R.id.btnCartuchos);
        Button btnToners = findViewById(R.id.btnToners);

        if(!listadoCartuchosSQLite()){
            cargarFragmentCartuchos();
        }else{
            cargarFragmentNoData();
        }

        btnCartuchos.setOnClickListener(v -> {
            if(!listadoCartuchosSQLite()){
                cargarFragmentCartuchos();
            }else{
                cargarFragmentNoData();
            }
        });

        btnToners.setOnClickListener(v -> {
            if(!listadoSQLiteToners()){
                cargarFragmentToners();
            }else{
                cargarFragmentNoData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragmentActual = getSupportFragmentManager().findFragmentById(R.id.FrameLayoutListado);
        if(fragmentActual instanceof ListadoCartuchos){
            Intent intent = new Intent(MainActivity.this, AnadirCartuchos.class);
            startActivity(intent);
        }else if(fragmentActual instanceof ListadoToners){
            Intent intent = new Intent(MainActivity.this, AnadirToners.class);
            startActivity(intent);
        }
        return true;
    }

    private void cargarFragmentCartuchos(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutListado, new ListadoCartuchos());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void cargarFragmentToners() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutListado, new ListadoToners());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void cargarFragmentNoData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutListado, new NoData());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean listadoCartuchosSQLite(){
        listaCartuchos.clear();
        DBAdapter dbAdapter = new DBAdapter(MainActivity.this);
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
        if(listaCartuchos.size() <1){
            return true;
        }
        dbAdapter.cerrarDB();
        return false;
    }

    private boolean listadoSQLiteToners() {
        listaToners.clear();
        DBAdapter dbAdapter = new DBAdapter(MainActivity.this);
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
        if(listaToners.size() <1){
            return true;
        }
        dbAdapter.cerrarDB();
        return false;
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentActual = getSupportFragmentManager().findFragmentById(R.id.FrameLayoutListado);
        if(fragmentActual instanceof ListadoToners || fragmentActual instanceof NoData){
            cargarFragmentCartuchos();
        }else if(fragmentActual instanceof ListadoCartuchos){
            MainActivity.this.finish();
            super.onBackPressed();
        }
    }
}