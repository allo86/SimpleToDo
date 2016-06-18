package com.allo.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvItems)
    ListView lvItems;

    @BindView(R.id.etNewItem)
    EditText etNewItem;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        loadData();
    }

    private void loadData() {
        /*
        items = new ArrayList<>();
        items.add("First item");
        items.add("Second item");
        */
        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);
    }

    @OnClick(R.id.btnAddItem)
    public void onAddItem() {
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");

        writeItems();
    }

    @OnItemLongClick(R.id.lvItems)
    public boolean onListItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();

        writeItems();

        return true;
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException ex) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
