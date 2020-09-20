package com.example.ejercicio1_guia5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class ListViewArticulos extends AppCompatActivity {
    ListView listViewPersonas;
    ArrayAdapter adaptador;
    SearchView searchView;
    ListView IistView;
    ArrayList<String> list;
    ArrayAdapter adapter;

    String[] version = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo", "GingerBread","HoneyComb", "IceCream Sandwich", "Jelly Bean","Kitkat","Lolipop","Marshmallow","Nought","Oreo"};
    base conexion = new base(this);
    Dto datos = new Dto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_articulos);

        listViewPersonas= (ListView) findViewById(R.id.listpersonas);
        searchView = (SearchView) findViewById(R.id.search);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consultaListaArticulos1());
        listViewPersonas.setAdapter(adaptador);

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

             @Override
             public boolean onQueryTextSubmit(String s) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {
                 String text = s;
                 adaptador.getFilter().filter(text);
                 return false;
             }
         });

         listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 String informacion="Codigo: "+ conexion.consultaListaArticulos().get(i).getCode()+"\n";
                 informacion+="Descripcién: "+ conexion.consultaListaArticulos().get(i).getDes()+"\n";
                 informacion+="Precio: "+ conexion.consultaListaArticulos().get(i).getPrecio();

                 Dto articulos = conexion.consultaListaArticulos().get(i);
                 Intent intent = new Intent(ListViewArticulos.this, DetalleArticulos.class);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("articulo", (Serializable) articulos);
                 intent.putExtras(bundle);
                 startActivity(intent);
             }
        });
    }
}