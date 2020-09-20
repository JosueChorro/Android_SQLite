package com.example.ejercicio1_guia5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DetalleArticulos extends AppCompatActivity {

    private TextView tcodigo, tdescripcion, tprecio;
    private TextView tv_codigo1 , tv_descripcion1, tv_precio1, tv_fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulos);

        tcodigo = (TextView)findViewById(R.id.tcodigos);
        tdescripcion = (TextView)findViewById(R.id.tdescr);
        tprecio = (TextView)findViewById(R.id.tprecioo);

        tv_codigo1 = (TextView)findViewById(R.id.tcode);
        tv_descripcion1 = (TextView)findViewById(R.id.tdes);
        tv_precio1 = (TextView)findViewById(R.id.tprecio);
        tv_fecha = (TextView)findViewById(R.id.tfecha);

        Bundle objeto = getIntent().getExtras();
        Dto dto = null;
        if(objeto != null){
            dto = (Dto)objeto.getSerializable("articulo");
            tcodigo.setText("" + dto.getCode());
            tdescripcion.setText(dto.getDes());
            tprecio.setText(String.valueOf(dto.getPrecio()));

            tv_codigo1.setText(""+dto.getCode());
            tv_descripcion1.setText(dto.getDes());
            tv_precio1.setText(String.valueOf(dto.getPrecio()));
            tv_fecha.setText("Fecha de creacién: "+ getDateTime());
        }
}

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);

    }
}