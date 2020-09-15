package com.example.camara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private ImageView imagen;
    private final String NOMBRE = "informacion.txt";
    private EditText editorTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazamos el componente vista texto que es un Multiline con la var. local editorTexto
        this.editorTexto = (EditText) findViewById(R.id.texto);
        // Recogemos la lista de ficheros de la aplicación en un array de String
        String[] archivos = fileList();
        // Verificamos si ya existe el archivo en el que guardamos nuestra informacion
        if (archivoExiste(archivos, NOMBRE)){
            String textoFichero = leerFile(NOMBRE);
            editorTexto.setText(textoFichero);
        }
    }

    private boolean archivoExiste(String[] archivos, String s) {
        for (int i=0; i<archivos.length;i++){
            if (s.equals(archivos[i])){
                return true;
            }
        }
        return false;
    }

    private String leerFile(String file){
        String textoCompleto="";
        try {
            InputStreamReader fichero = new InputStreamReader(openFileInput(file));
            BufferedReader lecturaArchivo = new BufferedReader(fichero);
            String linea = lecturaArchivo.readLine();
            while (linea != null){
                textoCompleto += linea;
                textoCompleto += "\n";
                linea = lecturaArchivo.readLine();
            }
            lecturaArchivo.close();
            fichero.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textoCompleto;
    }

    private boolean escribirFile(String contenido){
        OutputStreamWriter fichero;
        boolean result;
        try {
            fichero = new OutputStreamWriter(openFileOutput(NOMBRE, Activity.MODE_PRIVATE));
            fichero.write(contenido);
            fichero.flush();
            result = true;

        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public void guardar(View view){
        boolean seGuardo;
        String message;
        seGuardo = escribirFile(editorTexto.getText().toString());
        if (seGuardo)
            message = "Archivo guardado correctamente";
        else
            message = "No se pudo guardar el archivo";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void sacarFoto(View view){
        Intent foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(foto,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap captura = (Bitmap) data.getExtras().get("data");
        imagen = (ImageView) findViewById(R.id.foto);
        imagen.setImageBitmap(captura);
    }
}