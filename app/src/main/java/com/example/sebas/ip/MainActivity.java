package com.example.sebas.ip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvIp = findViewById(R.id.tvIp);
        TextView tvConexion = findViewById(R.id.tvConexion);
        init(tvIp, tvConexion);
    }
    //isnetworkavailable
    public void init(TextView tv1, TextView tv2){
        String red = "No hay conexión a internet";
        Runtime rt = Runtime.getRuntime();
        Process proceso;
        String cadena = "ifconfig";
        String ip ="Su dirección ip es: ";
        try {
            proceso = rt.exec(cadena);
            InputStream is = proceso.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(isr);
            String linea;
            while ((linea = bf.readLine()) != null ){
//                System.out.println("Linea: " + linea);
                if (linea.contains("addr:192.168")){
                    ip += linea.substring(20, 33);
                }
                if (linea.contains("addr:10")){
                    ip += linea.substring(20, 33);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNetworkAvailable()){
            red = "Si dispones de conexión a internet";
        }
        tv1.setText(ip);
        tv2.setText(red);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
