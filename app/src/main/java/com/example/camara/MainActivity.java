package com.example.camara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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