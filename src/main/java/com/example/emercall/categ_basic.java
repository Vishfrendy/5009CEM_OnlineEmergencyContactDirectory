package com.example.emercall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class categ_basic extends AppCompatActivity {
    GridView gridBasic;
    MyAdapter myAdapter;
    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> num = new ArrayList<>();

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categ_basic);

        gridBasic = findViewById(R.id.gridBasic);
        myAdapter = new MyAdapter();
        gridBasic.setAdapter(myAdapter);

        //To change toolbar title
        this.setTitle("IMPORTANT HELPLINES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Text
        text.add("Police & Ambulance");
        text.add("Fire Brigade");
        text.add("Civil Defense");
        text.add("National Poison Centre");

        //Numbers
        num.add("999");
        num.add("994");
        num.add("991");
        num.add("04-657-0099");

        gridBasic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeCall(position);
            }
        });

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return text.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_categories,null);
            ImageView itemIcon = v.findViewById(R.id.itemIcon);
            TextView itemName = v.findViewById(R.id.itemName);
            TextView itemNo = v.findViewById(R.id.itemNum);

            itemIcon.setBackgroundColor(getResources().getColor(R.color.PinkCard));
            itemName.setBackgroundColor(getResources().getColor(R.color.PinkCard));
            itemNo.setBackgroundColor(getResources().getColor(R.color.PinkCard));
            itemName.setText(text.get(position));
            itemNo.setText(num.get(position));
            itemIcon.setImageResource(R.drawable.primary);
            return v;
        }
    }

    //Code for making a checking permission and making phone call from the app
    void makeCall(int position)
    {
        String no = num.get(position);

        //code for checking permission for making phone calls
        if(no.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(categ_basic.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                //Code for checking manifest permission
                ActivityCompat.requestPermissions(categ_basic.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else{

                //Code for making call
                String dial =  "tel:" + no;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(categ_basic.this,"Error!\nNumber no exists",Toast.LENGTH_LONG).show();
        }
    }


    //To check id permission is already granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{
                Toast.makeText(categ_basic.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
