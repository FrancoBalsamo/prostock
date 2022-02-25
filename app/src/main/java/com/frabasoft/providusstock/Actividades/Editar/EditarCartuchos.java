package com.frabasoft.providusstock.Actividades.Editar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.util.Calendar;

public class EditarCartuchos extends AppCompatActivity {
    String position;
    private EditText etEditarCantidad;
    private final String mensajeVacio = "No puede estar vacío ni el valor debe ser 0 (cero)";
    String formatoHoy;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        String idC = getIntent().getStringExtra("id");
        position = getIntent().getStringExtra("position");
        int id = Integer.parseInt(idC);
        String cantidad = getIntent().getStringExtra("cantidad");

        etEditarCantidad = findViewById(R.id.etEditarCantidad);
        etEditarCantidad.setText(cantidad);
        Button btnEditar = findViewById(R.id.btnEditar);
        dbAdapter = new DBAdapter(EditarCartuchos.this);
        fechaActual();

        btnEditar.setOnClickListener(v -> {
            if(etEditarCantidad.getText().toString().length() == 0
                    || etEditarCantidad.getText().toString().equals("0")){
                Toast.makeText(EditarCartuchos.this, mensajeVacio, Toast.LENGTH_SHORT).show();
            }else{
                try{
                    editarCantidad(id);
                    finish();
                }catch (Exception e){
                    Log.e("Editar", "editarCantidad: " + e.getMessage());
                }
            }
        });
    }

    private void editarCantidad(int id){
        if(TextUtils.isEmpty(etEditarCantidad.getText().toString())){
            etEditarCantidad.setError("No puede estar vacío.");
            etEditarCantidad.requestFocus();
            return;
        }

        final int cant = Integer.parseInt(etEditarCantidad.getText().toString());
        try{
            Cartuchos cartuchos = new Cartuchos();
            cartuchos.setCantidad(cant);
            cartuchos.setFechaModificacion(formatoHoy);
            dbAdapter.editarCartuchos(cartuchos, String.valueOf(id));
        }catch(Exception e){
            Log.d("EDITAR", "editarCantidad: " + e.getMessage());
        }
    }

    private void fechaActual(){
        Calendar fechaHoy = Calendar.getInstance();
        int mesActual = fechaHoy.get(Calendar.MONTH) + 1;
        if(fechaHoy.get(Calendar.DAY_OF_MONTH) < 10){
            formatoHoy = "FECHA: " + "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + mesActual + fechaHoy.get(Calendar.YEAR)
                    + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND); //hora del día
        }else if(mesActual < 10){
            formatoHoy = "FECHA: " + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + "0" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                    + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND);
        }else{
            formatoHoy ="FECHA: " + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                    + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND);
        }
    }
}