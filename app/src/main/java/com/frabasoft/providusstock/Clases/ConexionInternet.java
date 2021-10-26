package com.frabasoft.providusstock.Clases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class ConexionInternet {

    public static boolean estaConectado(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if(capabilities != null){
                    if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                        return true;
                    }else{
                        Toast.makeText(context, "No estás conectado a ninguna red.", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Network[] networks = connectivityManager.getAllNetworks();
                if(networks != null){
                    for(Network network : networks){
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                        if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                            return true;
                        } else {
                            Toast.makeText(context, "No estás conectado a ninguna red.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
        return false;
    }
}
