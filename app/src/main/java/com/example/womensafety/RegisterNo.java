package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterNo extends AppCompatActivity {
    TextView phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_no);
        phoneNo=findViewById(R.id.phoneNo);
    }

    public void saveNo(View view){
        String phoneNoString=phoneNo.getText().toString();
        if(phoneNoString.length()==10){
            SharedPreferences sharedPreferences=getSharedPreferences("PhoneNo",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("ENUM",phoneNoString);
            editor.apply();
            RegisterNo.this.finish();
        }else{
            Toast.makeText(this, "Enter valid number!", Toast.LENGTH_SHORT).show();
        }
    }
}