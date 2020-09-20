package com.example.ejercicio1_guia5;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText tcod, tdes, tpre;
    private Button btnguardar, btnconsultar1, btnconsultar2, btnact, btneli;
    private TextView tresul;

    boolean inputEt = false;
    boolean inputEd = false;
    boolean input1 = false;
    int resulinsert = 0;

    //Modal ventanas = new Modal();
    base conexion = new base(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_salir)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));
        toolbar.setTitleTextColor(getResources().getColor(R.color.letra));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("CRUD SQLite-2020");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.letra));
        toolbar.setTitle("Jc_aries");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //confirmacion();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //ventanas.Search(MainActivity.this);
            }
        });

        tcod = (EditText) findViewById(R.id.txtcodigo);
        tdes = (EditText) findViewById(R.id.txtdescripcion);
        tpre = (EditText) findViewById(R.id.txtprecio);
        btnguardar = (Button) findViewById(R.id.btn_guardar);
        btnconsultar1 = (Button) findViewById(R.id.btn_con1);
        btnconsultar2 = (Button) findViewById(R.id.btn_con2);
        btneli = (Button) findViewById(R.id.btn_eliminar);
        btnact = (Button) findViewById(R.id.btn_actualizar);
//tv resultado —— (TextView) findViewByld(R.id.tv resultado)
        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
                if (senal.equals("1")) {
                    tcod.setText(codigo);
                    tdes.setText(descripcion);
                    tpre.setText(precio);
                }
            }
        } catch (Exception ex) {

        }
    }

    private void confirmacion() {
        String mensaje = "¿Realmente desea salir?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_salir);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*if (id == R.id.action_limpiar) {
            tcod.setText(null);
            tdes.setText(null);
            tpre.setText(null);
            return true;
        } else if (id == R.id.action_listaArticulos) {
            Intent spinnerActivity = new Intent(MainActivity.this, ConsultaSpinner.class);
            startActivity(spinnerActivity);
            return true;
        } else if (id == R.id.action_listaArticulos1) {
            Intent IistViewActivity = new Intent(MainActivity.this, ListViewArticulos.class);
            startActivity(IistViewActivity);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void alta(View view) {
        if (tcod.getText().toString().length() == 0) {
            tcod.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (tdes.getText().toString().length() == 0) {
            tdes.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }
        if (tpre.getText().toString().length() == 0) {
            tpre.setError("Campo obligatorio");
            input1 = false;
        } else {
            input1 = true;
        }
        if (inputEt && inputEd && input1) {
            try {
                datos.setCode(Integer.parseInt(tcod.getText().toString()));
                datos.setDes(tdes.getText().toString());
                datos.setPrecio(Double.parseDouble(tpre.getText().toString()));
                if (conexion.insert(datos)) {
                    Toast.makeText(this, "Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro\n" +
                            " Cédigo: " + tcod.getText().toString(), Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
            } catch (Exception e) {
                Toast.makeText(this, "ERROR. Ya existe.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void mensaje(String mensaje) {
        Toast.makeText(this, "" + mensaje, Toast.LENGTH_SHORT).show();
    }


    public void limpiarDatos() {
        tcod.setText(null);
        tdes.setText(null);
        tpre.setText(null);
        tcod.requestFocus();
    }

    public void consulcode(View view) {
        if (tcod.getText().toString().length() == 0) {
            tcod.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt) {
            String codigo = tcod.getText().toString();
            datos.setCode(Integer.parseInt(codigo));
            if (conexion.consultaArticulo(datos)) {
                tdes.setText(datos.getDes());
                tpre.setText("" + datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un articulo con dicho cédigo", Toast.LENGTH_SHORT).show();
            }
            limpiarDatos();
        } else {
            Toast.makeText(this, "Ingrese el cédigo del articulo a buscar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void consuldes(View view) {
        if (tdes.getText().toString().length() == 0) {
            tdes.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }
        if (inputEd) {
            String descripcion = tdes.getText().toString();
            datos.setDes(descripcion);
            if (conexion.consuItarDescripcion(datos)) {
                tcod.setText("" + datos.getCode());
                tdes.setText(datos.getDes());
                tpre.setText("" + datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un articulo con dicha descripcién", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        } else {
            Toast.makeText(this, "Ingrese la descripcién del articulo a buscar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar(View view) {
        if (tcod.getText().toString().length() == 0) {
            tcod.setError("campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt) {
            String cod = tcod.getText().toString();
            String descripcion = tdes.getText().toString();
            double precio = Double.parseDouble(tpre.getText().toString());
            datos.setCode(Integer.parseInt(cod));
            datos.setDes(descripcion);
            datos.setPrecio(precio);
            if (conexion.modificar(datos)) {
                Toast.makeText(this, "Registro Modificado Correctamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void eliminar(View view) {
        if (tcod.getText().toString().length() == 0) {
            tcod.setError("campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt) {
            String cod = tcod.getText().toString();
            datos.setCode(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this, datos)) {
                limpiarDatos();
            } else {
                Toast.makeText(this, "No existe un articulo con dicho cédigo.", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }
}