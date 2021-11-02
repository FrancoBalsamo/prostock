package com.frabasoft.providusstock.Fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaCartuchos;
import com.frabasoft.providusstock.Adaptadores.RecyclerViewListaToners;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Clases.ConexionInternet;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListadoToners extends Fragment {
    TextView alerta;
    LinearLayout linearAlerta;
    RecyclerView recyclerView;
    ArrayList<Toners> arrayList;
    ArrayList<Toners> tonersArrayList;
    String infoToners;
    RecyclerViewListaToners adaptador;

    public ListadoToners() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_toners, container, false);

        tonersArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();

        alerta = view.findViewById(R.id.tvAlertaCantidadToners);
        linearAlerta = view.findViewById(R.id.linearAlertaToners);
        recyclerView = view.findViewById(R.id.rvToners);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        cargarAlertaToners();
        listadoToners();

        alerta.setOnClickListener(v -> {
            if (ConexionInternet.estaConectado(getContext())) {
                alertToners();
            }else{
                Toast.makeText(getContext(), "Conéctate a una red.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void listadoToners() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frabasoft.com.ar/pstock/select_listado_toners.php",
                response -> {
                    try {
                        arrayList.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            int id = jsonObject1.getInt("idToner");
                            String modelo = jsonObject1.getString("modelo");
                            String color = jsonObject1.getString("color");
                            String fec = jsonObject1.getString("fechaModificacion");
                            int cantidad = jsonObject1.getInt("cantidad");
                            arrayList.add(new Toners(id, modelo, color, fec, cantidad));
                        }
                        adaptador = new RecyclerViewListaToners(getActivity(), arrayList);
                        recyclerView.setAdapter(adaptador);
                    } catch (JSONException e) {
                        Log.d("Listado", "listadoToners: " + e.getMessage());
                    }
                }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void cargarAlertaToners(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frabasoft.com.ar/pstock/alerta_cantidad_toners.php",
                response -> {
                    try {
                        if(response.contains("Hay.")){
                            alerta.setText(getString(R.string.atencion));
                            response = response.replace("Hay.", "");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String modelo = jsonObject1.getString("modelo");
                                String color = jsonObject1.getString("color");
                                int cantidad = jsonObject1.getInt("cantidad");
                                tonersArrayList.add(new Toners(modelo, color, cantidad));
                            }
                        } else {
                            linearAlerta.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Log.d("MainAcExc", "cargarAlertaToners: " + e.getMessage());
                    }
                }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void alertToners(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("¡Atención!");
        for(int c = 0; c < tonersArrayList.size(); c++){
            infoToners += tonersArrayList.get(c).getModelo() + " " + tonersArrayList.get(c).getColor() + " - Cantidad: " + tonersArrayList.get(c).getCantidad() + "\n";
            if(infoToners.contains("null")){
                infoToners = infoToners.replace("null", "");
            }
        }
        alertDialog.setMessage("Se deben reponer los siguientes toners: \n\n" + infoToners);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cerrar", (dialog, which) -> { alertDialog.dismiss(); tonersArrayList.clear();});
        alertDialog.show();
    }

    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.i("INTENTO", "onActivityResult() " + result.getResultCode());
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bundle bundle = data.getExtras();

                    boolean update = bundle.getBoolean("update");
                    int position = bundle.getInt("posicion");
                    int cantidad = bundle.getInt("cantidad");
                    String fe = bundle.getString("fec");

                    Cartuchos cartuchos = new Cartuchos();

                    cartuchos.setCantidad(cantidad);
                    cartuchos.setFechaModificacion(fe);

                    Log.i("INTENTO", "onActivityResult() actualizar? : " + update + "\n datos: Posición: " + position + " / Cantidad: " + cantidad + " / Fecha hoy: " + fe);

                    if(update){
                        listadoToners();
                        adaptador.notifyItemChanged(position);
                    }else{
                        Toast.makeText(getActivity(), "No actualizado.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}