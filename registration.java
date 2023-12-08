package com.example.lw1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {
    private String TAG = "Жизненный цикл";
    EditText loginText;
    EditText passwordText;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        Button Enter = findViewById(R.id.Enterbutton);
        loginText = findViewById(R.id.editTextEmailAddress);
        passwordText = findViewById(R.id.editTextPassword);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Intent openIntent = new Intent(this, hello.class);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                editor.putString("login", loginText.getText().toString());
                editor.putString("password", passwordText.getText().toString());
                editor.apply();
                openIntent.putExtra("hello", "Hello from FirstActivity");
                startActivity(openIntent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        try {
            loginText.setText(sharedPref.getString("login", ""));
            passwordText.setText(sharedPref.getString("password", ""));
        }
        catch (RuntimeException ex){
            ex.fillInStackTrace();
        }
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStart()");
    }
}
