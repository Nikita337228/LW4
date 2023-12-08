package com.example.lw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class hello extends AppCompatActivity {
    ArrayAdapter<String> mTextAdapter;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> mArrList;
    Set<String> remSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        EditText mEditText = findViewById(R.id.TextEdit);
        Button AddButton = findViewById(R.id.AddButton);
        Button DelButton = findViewById(R.id.DeleteButton);
        ListView listView = findViewById(R.id.ViewList);

        mArrList = new ArrayList<>();
        remSet = new HashSet<>();

        mTextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrList);
        listView.setAdapter(mTextAdapter);

        loadData();

        Bundle arg = getIntent().getExtras();
        if (arg != null) {
            String str = arg.getString("hello");
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }

        int i = 0;
        while (sharedPref.contains("row" + i)) {
            mTextAdapter.add(sharedPref.getString("row" + i, ""));
            i++;
        }

        mTextAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tmp = mTextAdapter.getItem(i);
                if (listView.isItemChecked(i)) {
                    view.setBackgroundColor(Color.YELLOW);
                    remSet.add(tmp);
                } else {
                    view.setBackgroundColor(0xFFFFFFF);
                    remSet.remove(tmp);
                }
            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = String.valueOf(mEditText.getText());
                mTextAdapter.add(tmp);
                mEditText.setText("");
                mTextAdapter.notifyDataSetChanged();
            }
        });

        DelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String item : remSet) {
                    mTextAdapter.remove(item);
                }
                remSet.clear();
                mTextAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("OTLADKA", "onPause");
        saveData();
    }

    public void loadData() {
        int size = sharedPref.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            mArrList.add(sharedPref.getString("" + i, ""));
        }
        mTextAdapter.notifyDataSetChanged();
    }

    public void saveData() {
        editor.putInt("size", mArrList.size());

        for (int i = 0; i < mArrList.size(); i++) {
            editor.putString("" + i, mArrList.get(i));
        }

        editor.apply();
    }
}
