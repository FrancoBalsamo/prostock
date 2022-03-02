package com.frabasoft.providusstock.Actividades.Añadir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.util.Calendar;

public class AnadirToners extends AppCompatActivity {
    EditText modelo, color, cantidad;
    Button guardar;
    DBAdapter dbAdapter;
    private String formatoHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_toners);
        modelo = findViewById(R.id.etAnadirModeloToner);
        color = findViewById(R.id.etAnadirColorToner);
        cantidad = findViewById(R.id.etAnadirCantidadToner);
        dbAdapter = new DBAdapter(AnadirToners.this);
        fechaActual();

        guardar = findViewById(R.id.btnAnadirToner);
        guardar.setOnClickListener(view -> {
            Toners toners = new Toners();
            toners.setModelo(modelo.getText().toString());
            toners.setColor(color.getText().toString());
            toners.setCantidad(Integer.parseInt(cantidad.getText().toString()));
            toners.setFechaModificacion(formatoHoy);

            if(dbAdapter.validarInsertToners(modelo.getText().toString(), color.getText().toString())){
                dbAdapter.insertarToners(toners);
            }
            finish();
        });
    }

    private void fechaActual(){
        Calendar fechaHoy = Calendar.getInstance();
        int mesActual = fechaHoy.get(Calendar.MONTH) + 1;
        if(fechaHoy.get(Calendar.DAY_OF_MONTH) < 10){
            if(mesActual < 10){
                formatoHoy = "FECHA: " + "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + "0" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                        + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND);
            }else{
                formatoHoy = "FECHA: " + "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                        + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND); //hora del día
            }
        }else{
            if(mesActual < 10){
                formatoHoy = "FECHA: " + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + "0" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                        + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND);
            }else{
                formatoHoy ="FECHA: " + fechaHoy.get(Calendar.DAY_OF_MONTH) + "/" + mesActual + "/" + fechaHoy.get(Calendar.YEAR)
                        + "\nHORA: " + fechaHoy.get(Calendar.HOUR_OF_DAY) + ":" + fechaHoy.get(Calendar.MINUTE) + ":" + fechaHoy.get(Calendar.SECOND);
            }
        }
    }
}