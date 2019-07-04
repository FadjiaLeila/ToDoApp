package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

ArrayList<String> items;
ArrayAdapter<String> itemsAdapter;
ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        /*
       mock data
       items.add("Premier element ");
       items.add("Deuxieme element");
       */

        setupListViewListener();




    }

    public void onAddItem(View V) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItems);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(),"Element ajoute a la liste avec succes", Toast.LENGTH_SHORT).show();

    }

    private void setupListViewListener() {
        Log.i("Main Activity", "Setting up listener on ListView");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i("MainActivity", "Element enleve de la liste" + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();

                return true;
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Erreur, dans la lecture du fichier", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), Collections.singleton(items));
        } catch (IOException e) {
            Log.e("Main Activity", "Erreur, dans l'ecriture du fichier", e);
        }

    }
}
