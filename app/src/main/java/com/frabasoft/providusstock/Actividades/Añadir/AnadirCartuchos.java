package com.frabasoft.providusstock.Actividades.Añadir;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.R;
import com.frabasoft.providusstock.SQLite.DBAdapter;
import java.util.Calendar;

public class AnadirCartuchos extends AppCompatActivity {
    EditText modelo, color, cantidad;
    Button guardar;
    DBAdapter dbAdapter;
    private String formatoHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cartuchos);

        modelo = findViewById(R.id.etAnadirModelo);
        color = findViewById(R.id.etAnadirColor);
        cantidad = findViewById(R.id.etAnadirCantidad);
        dbAdapter = new DBAdapter(AnadirCartuchos.this);
        fechaActual();

        guardar = findViewById(R.id.btnAnadirCartuchos);
        guardar.setOnClickListener(view -> {
            Cartuchos cartuchos = new Cartuchos();
            cartuchos.setModelo(modelo.getText().toString());
            cartuchos.setColor(color.getText().toString());
            cartuchos.setCantidad(Integer.parseInt(cantidad.getText().toString()));
            cartuchos.setFechaModificacion(formatoHoy);

            if(dbAdapter.validarInsertCartuchos(modelo.getText().toString(), color.getText().toString())){
                dbAdapter.insertarCartuchos(cartuchos);
                finish();
            }
        });
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