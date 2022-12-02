package com.example.emercall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class about_section extends AppCompatActivity {
TextView copyright;
String copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_section);

        this.setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //this function will open the link in mobile default browser
    public void click(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
