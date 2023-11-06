package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etF, etN, etA, etAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etF.findViewById(R.id.etF);
        etN.findViewById(R.id.etN);
        etA.findViewById(R.id.etA);
        etAc.findViewById(R.id.etAc);
    }

    public void agregar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String fecha = etF.getText().toString();
        String nombre = etN.getText().toString();
        String apellido = etA.getText().toString();
        String actividad = etAc.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("fecha", fecha);
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("actividad", actividad);
        bd.insert("agenda", null, registro);
        bd.close();
        etF.setText("");
        etN.setText("");
        etA.setText("");
        etAc.setText("");
        Toast.makeText(this, "Se cargaron los datos de la agenda",
                Toast.LENGTH_SHORT).show();
    }

    public void consultarPorFecha(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String fecha = etF.getText().toString();
        Cursor fila = bd.rawQuery(
                "select nombre, apellido, actividad from agenda where fecha = ?", new String[]{fecha});
        if (fila.moveToFirst()) {
            etN.setText(fila.getString(0));
            etA.setText(fila.getString(1));
            etAc.setText(fila.getString(2));
        } else
            Toast.makeText(this, "No existen datos para esta fecha",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }
}