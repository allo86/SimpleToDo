package com.allo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.allo.simpletodo.utils.ValidationException;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EditItemActivity.EDIT_ITEM_REQUEST_CODE) {
            String item = data.getExtras().getString(EditItemActivity.EDIT_ITEM_TEXT);
            int position = data.getExtras().getInt(EditItemActivity.EDIT_ITEM_POSITION);
            if (position == -1) {
                // New item
                items.add(item);
            } else {
                // Existing item
                items.set(position, item);
            }
            itemsAdapter.notifyDataSetChanged();

            writeItems();
        }
    }

    private void loadData() {
        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);
    }

    @OnClick(R.id.btnAddItem)
    public void onAddItem() {
        try {
            validateItem();

            addItem();
        } catch (ValidationException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void validateItem() throws ValidationException {
        String item = etNewItem.getText().toString();
        if ("".equals(item.trim())) {
            throw new ValidationException(getString(R.string.edit_item_empty_validation));
        }
    }

    private void addItem() {
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");

        writeItems();
    }

    @OnItemClick(R.id.lvItems)
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EditItemActivity.class);

        intent.putExtra(EditItemActivity.EDIT_ITEM_POSITION, position);
        intent.putExtra(EditItemActivity.EDIT_ITEM_TEXT, items.get(position));

        startActivityForResult(intent, EditItemActivity.EDIT_ITEM_REQUEST_CODE);
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
