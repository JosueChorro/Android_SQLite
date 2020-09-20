
package com.example.ejercicio1_guia5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import  android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class ConsultaSpinner extends AppCompatActivity {
    private Spinner sp_options;
    private TextView tcod, tdescripcion, tprecio;

    base conexion = new base(this);
    Dto datos = new Dto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);
        sp_options = (Spinner)findViewById(R.id.sp_options);
        tcod = (TextView)findViewById(R.id.tcode);
        tdescripcion = (TextView)findViewById(R.id.tdes);
        tprecio = (TextView)findViewById(R.id.tprecio);

        conexion.consultaListaArticulos();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conexion.obtenerListaArticulos());
        sp_options.setAdapter(adaptador);

        sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    tcod.setText("Cédigo: " + conexion.consultaListaArticulos().get(position - 1).getCode());
                    tdescripcion.setText("Descripcién: " + conexion.consultaListaArticulos().get(position - 1).getDes());
                    tprecio.setText("Precio: " + String.valueOf(conexion.consultaListaArticulos().get(position - 1).getPrecio()));
                } else {
                    tcod.setText("Cédigo: ");
                    tdescripcion.setText("Descripcién: ");
                    tprecio.setText("Precio: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}