package com.frabasoft.providusstock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.frabasoft.providusstock.Clases.ConexionInternet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Editar extends AppCompatActivity {
    private final boolean actualizar = true;
    String url;
    private EditText etEditarCantidad;
    private final String mensajeVacio = "No puede estar vacío ni el valor debe ser 0 (cero)";
    String fechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        url = getIntent().getStringExtra("url");
        String idC = getIntent().getStringExtra("id");
        int id = Integer.parseInt(idC);
        String cantidad = getIntent().getStringExtra("cantidad");
        traerFechaHoy();

        etEditarCantidad = findViewById(R.id.etEditarCantidad);
        etEditarCantidad.setText(cantidad);
        Button btnEditar = findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(v -> {
            if(etEditarCantidad.getText().toString().length() == 0
                    || etEditarCantidad.getText().toString().equals("0")){
                Toast.makeText(Editar.this, mensajeVacio, Toast.LENGTH_SHORT).show();
            }else{
                try{
                    if(ConexionInternet.estaConectado(Editar.this)){
                        editarCantidad(id);
                    }else{
                        Toast.makeText(Editar.this, "Conéctate a una red.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("Error", "Editar error: " + e.getMessage());
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                if (response.contains("Error")) {
                    Toast.makeText(Editar.this, "Lo sentimos, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Editar.this, "Modificado con éxito.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("update", actualizar);
                    setResult(Activity.RESULT_OK, intent);
                    Editar.this.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.e("onErrorEdit", "onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> parametros = new HashMap<>();
                parametros.put("cantidad", String.valueOf(cant));
                parametros.put("id", String.valueOf(id));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(stringRequest);
    }

    public void traerFechaHoy() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frabasoft.com.ar/pstock/select_now.php",
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            fechaHoy = jsonObject1.getString("now");
                        }
                    } catch (JSONException e) {
                        Log.d("Fecha", "Fecha hoy error: " + e.getMessage());
                    }
                }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(stringRequest);
    }
}