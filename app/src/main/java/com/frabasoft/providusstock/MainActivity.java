package com.frabasoft.providusstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaCartuchos;
import com.frabasoft.providusstock.Clases.ConexionInternet;
import com.frabasoft.providusstock.Fragmentos.ListadoCartuchos;
import com.frabasoft.providusstock.Fragmentos.ListadoToners;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCartuchos = findViewById(R.id.btnCartuchos);
        Button btnToners = findViewById(R.id.btnToners);

        if(ConexionInternet.estaConectado(MainActivity.this)){
            cargarFragmentCartuchos();
        }else{
            Toast.makeText(MainActivity.this, "Conéctate a una red.", Toast.LENGTH_SHORT).show();
        }

        btnToners.setOnClickListener(v -> {
            if(ConexionInternet.estaConectado(MainActivity.this)){
                cargarFragmentToners();
            }else{
                Toast.makeText(MainActivity.this, "Conéctate a una red.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCartuchos.setOnClickListener(v -> {
            if(ConexionInternet.estaConectado(MainActivity.this)){
                cargarFragmentCartuchos();
            }else{
                Toast.makeText(MainActivity.this, "Conéctate a una red.", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onBackPressed() {
        Fragment fragmentActual = getSupportFragmentManager().findFragmentById(R.id.FrameLayoutListado);
        if(fragmentActual instanceof ListadoToners){
            cargarFragmentCartuchos();
        }else if(fragmentActual instanceof ListadoCartuchos){
            MainActivity.this.finish();
            super.onBackPressed();
        }
    }
}