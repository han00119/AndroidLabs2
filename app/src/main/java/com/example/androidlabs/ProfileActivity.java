package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME, "In function: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        EditText et1 = findViewById(R.id.et1);
        TextView tv3 = findViewById(R.id.tv3);
        EditText et2 = findViewById(R.id.et2);
        TextView tv4 = findViewById(R.id.tv4);
        //ImageButton im = findViewById(R.id.ib);

        TextView tv5 = findViewById(R.id.tv5);
        mImageButton=(ImageButton)findViewById(R.id.ib);
        mImageButton.setOnClickListener(click ->  dispatchTakePictureIntent());
        Intent fromMain = getIntent();
        //fromMain.getStringExtra("EMAIL");
        et2.setText(fromMain.getStringExtra("EMAIL"));

    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME, "In function: onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(ACTIVITY_NAME, "In function: onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "In function: onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "In function: onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME, "In function: onDestroy");
        super.onDestroy();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ACTIVITY_NAME, "In function: onActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }



}