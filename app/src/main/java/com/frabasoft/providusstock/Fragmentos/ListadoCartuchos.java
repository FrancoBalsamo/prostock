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
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Clases.ConexionInternet;
import com.frabasoft.providusstock.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListadoCartuchos extends Fragment {
    TextView alerta;
    LinearLayout linearAlerta;
    ArrayList<Cartuchos> cartuchosArrayList;
    RecyclerView recyclerView;
    ArrayList<Cartuchos> arrayList;
    RecyclerViewListaCartuchos adaptador;
    View view;

    public ListadoCartuchos() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listado, container, false);

        cartuchosArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();

        alerta = view.findViewById(R.id.tvAlertaCantidadCartuchos);
        linearAlerta = view.findViewById(R.id.linearAlertaCartucho);
        recyclerView = view.findViewById(R.id.rvCartuchos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        cargarAlertaCartucho();
        listadoCartuchos();

        alerta.setOnClickListener(v -> {
            if (ConexionInternet.estaConectado(view.getContext())) {
                alertCartuchos();
            }else{
                Toast.makeText(getContext(), "Conéctate a una red.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void listadoCartuchos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frabasoft.com.ar/pstock/select_listado_cartuchos.php",
                response -> {
                    try {
                        arrayList.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            int id = jsonObject1.getInt("idCartucho");
                            String modelo = jsonObject1.getString("modelo");
                            String color = jsonObject1.getString("color");
                            String fec = jsonObject1.getString("fechaModificacion");
                            int cantidad = jsonObject1.getInt("cantidad");
                            arrayList.add(new Cartuchos(id, modelo, color, fec, cantidad));
                        }
                        adaptador = new RecyclerViewListaCartuchos(getActivity(), arrayList, ListadoCartuchos.this);
                        recyclerView.setAdapter(adaptador);
                    } catch (JSONException e) {
                        Log.d("Listado", "listadoCartuchos: " + e.getMessage());
                    }
                }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void cargarAlertaCartucho(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frabasoft.com.ar/pstock/alerta_cantidad_cartuchos.php",
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
                                cartuchosArrayList.add(new Cartuchos(modelo, color, cantidad));
                            }
                        } else {
                            linearAlerta.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Log.d("MainAcExc", "cargarAlertaCartucho: " + e.getMessage());
                    }
                }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }

    private void alertCartuchos(){
        StringBuilder sb = new StringBuilder();
        AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
        alertDialog.setTitle("¡Atención!");
        for(int c = 0; c < cartuchosArrayList.size(); c++){
            sb.append(cartuchosArrayList.get(c).getModelo()).append(" ").append(cartuchosArrayList.get(c).getColor()).append(" - Cantidad: ").append(cartuchosArrayList.get(c).getCantidad()).append("\n");
        }
        alertDialog.setMessage("Se deben reponer los siguientes cartuchos: \n\n" + sb);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cerrar", (dialog, which) -> alertDialog.dismiss());
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
                        //adaptador.notifyDataSetChanged();
                        listadoCartuchos();
                        adaptador.notifyItemChanged(position);
                    }else{
                        Toast.makeText(getActivity(), "No actualizado.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}