package com.mykmovies.android.japaneselearners;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void openNumbersActivity(View view)
    {
        Intent i=new Intent(MainActivity.this,NumbersActivity.class);
        String message = "Numbers In Japanese";
        i.putExtra("numbers_header_key", message);
        startActivity(i);
    }
    public void openFamilyActivity(View view)
    {
        Intent i=new Intent(MainActivity.this,FamilyActivity.class);
        String message = "Family Details In Japanese";
        i.putExtra("family_header_key", message);
        startActivity(i);
    }
    public void openColorsActivity(View view)
    {
        Intent i=new Intent(MainActivity.this,ColorsActivity.class);
        String message = "Colors Names In Japanese";
        i.putExtra("colors_header_key", message);
        startActivity(i);
    }
    public void openPharsesActivity(View view)
    {
        Intent i=new Intent(MainActivity.this,PharsesActivity.class);
        String message = "Pharses In Japanese";
        i.putExtra("pharses_header_key", message);
        startActivity(i);
    }
}
