package com.example.ejercicio1_guia5;

import android.content.Context;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class base extends SQLiteOpenHelper {
    boolean estado = true;
    ArrayList<String>listaarticulos;
    ArrayList<Dto>articuloslist;

    public base(Context context) {
        super(context, "administracion.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulo(codigo integer not null primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists articulos");
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase bd(){
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd;
    }

    public boolean insert(Dto datos){
        boolean estado = true;
        int resul;
        try {
            int cod = datos.getCode();
            String des = datos.getDes();
            double precio = datos.getPrecio();
            Cursor fila = bd().rawQuery("select codigo from articulo where codigo="+ cod + "" , null);
            if (fila.moveToFirst() == true){
                estado = false;
            }else{
                String sql = "INSERT INTO articulos(codigo, descripcion, precio) VALUES\n" +
                        "("+String.valueOf(cod)+ "," + des + "," + String.valueOf(precio) + ");";
                bd().execSQL(sql);
                bd().close();
                estado = true;
            }
        }catch (Exception ex){
            estado = false;
            Log.e("error.", ex.toString());
        }
        return estado;
    }

    public boolean insertar(Dto datos){
        boolean estado = true;
        int resul;
        ContentValues registro = new ContentValues();
        try{
            registro.put("codigo", datos.getCode());
            registro.put("descripcion", datos.getDes());
            registro.put("precio", datos.getPrecio());
            Cursor fila = bd().rawQuery("select codigo from articulo where codigo="+ datos.getCode() + "" , null);
            if (fila.moveToFirst() == true){
                estado = false;
            }else{
                resul = (int)bd().insert("articulo", null, registro);
                if (resul>0){
                    estado = true;
                }else {
                    estado = false;
                }
            }
        }catch (Exception ex){
            estado = false;
            Log.e("error.", ex.toString());
        }
        return estado;
        }

        public boolean InsertRegistre(Dto datos){
            boolean estado = true;
            int resultado;
            try {
                int cod = datos.getCode();
                String des = datos.getDes();
                double precio = datos.getPrecio();

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String fecha1 = sdf.format(cal.getTime());

                Cursor fila = bd().rawQuery("select codigo from articulo where codigo="+ cod + "" , null);
                if (fila.moveToFirst() == true){
                    estado = false;
                }else{
                    String sql = "INSERT INTO articulos(codigo, descripcion, precio) VALUES\n" +
                            "(?,?,?)";
                    bd().execSQL(sql, new String[]{String.valueOf(cod), des, String.valueOf(precio)});
                    estado = true;
                }
            }catch (Exception ex){
                estado = false;
                Log.e("error.", ex.toString());
            }
            return estado;
        }

        public boolean consultaCodigo(Dto datos){
            boolean estado = true;
            int resultado;
            SQLiteDatabase bd = this.getWritableDatabase();
            try{
                int codigo = datos.getCode();
                Cursor fila = bd().rawQuery("select codigo from articulo where codigo="+ codigo + "" , null);
                if (fila.moveToFirst()){
                    datos.setCode(Integer.parseInt(fila.getString(0)));
                    datos.setDes(fila.getString(1));
                    datos.setPrecio(Double.parseDouble(fila.getString(2)));
                    estado = true;
                }else{
                    estado = false;
                }
                bd.close();
            }catch (Exception ex){
                estado = false;
                Log.e("error.", ex.toString());
            }
            return estado;
        }

    public boolean consultaArticulo(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getReadableDatabase();
        try{
            String[] para = {String.valueOf(datos.getCode())};
            String[] campos = {"codigo", "descripcion", "precio"};
            Cursor fila = bd.query("articulo", campos, "codigo=?", para, null, null, null);
            if (fila.moveToFirst()){
                datos.setCode(Integer.parseInt(fila.getString(0)));
                datos.setDes(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else{
                estado = false;
            }
            fila.close();
            bd.close();
        }catch (Exception ex){
            estado = false;
            Log.e("error.", ex.toString());
        }
        return estado;
    }

    public boolean consuItarDescripcion(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String descripcion = datos.getDes();
            Cursor fila = bd.rawQuery("select codigo, descripcion, precio from articulo where descripcion=" + descripcion + "", null);
            if (fila.moveToFirst()) {
                datos.setCode(Integer.parseInt(fila.getString(0)));
                datos.setDes(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
                bd.close();
            }
        } catch (Exception ex) {
            estado = false;
            Log.e("error.", ex.toString());
        }
        return estado;
    }

    public boolean bajaCodigo(final Context context, final Dto datos){
            estado = true;
            try{
                int codigo = datos.getCode();
                Cursor fila = bd().rawQuery("select * from articulo where codigo=" + codigo, null);
                if (fila.moveToFirst()) {
                    datos.setCode(Integer.parseInt(fila.getString(0)));
                    datos.setDes(fila.getString(1));
                    datos.setPrecio(Double.parseDouble(fila.getString(2)));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.ic_borrar);
                    builder.setTitle("Warning");
                    builder.setMessage("¿Esta seguro de borrar el registro? \nCédigo: " + datos.getCode()+"\nDescrpcién: "+datos.getDes());
                    builder.setCancelable(false);
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int codigo = datos.getCode();
                            int cant = bd().delete("articulo", "codigo=" + codigo, null);
                            if (cant > 0) {
                                estado = true;
                                Toast.makeText(context, "Registro eliminado satisfactoriamente.", Toast.LENGTH_SHORT).show();
                            } else {
                                estado = false;
                            }
                        }
                    });
                    bd().close();
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(context, "No hay resultados encontrados para la busqueda especificada.", Toast.LENGTH_SHORT).show();

                }
            }catch (Exception ex) {
                estado = false;
                Log.e("Error.", ex.toString());
            }
            return estado;
    }

    public boolean modificar(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            int codigo = datos.getCode();
            String descripcion = datos.getDes();
            double precio = datos.getPrecio();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            int cant = (int) bd.update("articuIos", registro, "codigo=" + codigo, null);
            bd.close();
            if(cant>0) {
                estado = true;
            } else{
                estado = false;
            }
        }catch (Exception ex){
            estado = false;
            Log.e("error.",ex.toString());
        }
        return estado;
    }

    public ArrayList<Dto> consultaListaArticulos(){
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();
        Dto articulos = null;
        articuloslist = new ArrayList<Dto>();
        try {
            Cursor fila = bd.rawQuery("select * from articulos",null);
            while (fila.moveToNext()) {
                articulos = new Dto();
                articulos.setCode(fila.getInt(0));
                articulos.setDes(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articuloslist.add(articulos);
                Log.i("codigo", String.valueOf(articulos.getCode()));
                Log.i("descripcion", articulos.getDes().toString());
                Log.i("precio", String.valueOf(articulos.getPrecio()));
            }
            obtenerListaArticulos();
        } catch (Exception ex) {
            return articuloslist;
        }
        return articuloslist;
    }

    public ArrayList<String> obtenerListaArticulos() {
        listaarticulos = new ArrayList<String>();
        listaarticulos.add("Seleccione");
        for (int i = 0; i < articuloslist.size(); i++) {
            listaarticulos.add(articuloslist.get(i).getCode() + " - " + articuloslist.get(i).getDes());
        }
        return listaarticulos;
    }

    public ArrayList<String> consultaListaArticulos1() {
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();
        Dto articulos = null;    //Creamos la instancia vacia.
        articuloslist = new ArrayList<Dto>();
        try {
            Cursor fila = bd.rawQuery("select * from articulos", null);
            while (fila.moveToNext()) {
                articulos = new Dto();
                articulos.setCode(fila.getInt(0));
                articulos.setDes(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articuloslist.add(articulos);
            }
            listaarticulos = new ArrayList<String>();
            for (int i = 0; i <= articuloslist.size(); i++) {
                listaarticulos.add(articuloslist.get(i).getCode() + " - " + articuloslist.get(i).getDes());
            }
        } catch (Exception e) {
        }
        return listaarticulos;
    }
}