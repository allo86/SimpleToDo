package com.allo.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.allo.simpletodo.model.Item;
import com.allo.simpletodo.utils.ValidationException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Edit Item Activity
 */
public class EditItemActivity extends AppCompatActivity {

    public static final int EDIT_ITEM_REQUEST_CODE = 123;

    public static final String EDIT_ITEM_ID = "EDIT_ITEM_ID";

    @BindView(R.id.etEditItem)
    EditText etEditItem;

    @BindView(R.id.spPriority)
    Spinner spPriority;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        ButterKnife.bind(this);

        initializeUI();

        if (getIntent().hasExtra(EDIT_ITEM_ID)) {
            item = new Select().from(Item.class).where("id = ?", getIntent().getLongExtra(EDIT_ITEM_ID, 0L)).executeSingle();
            etEditItem.append(item.getDescription());
            spPriority.setSelection(item.getPriority());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_save:
                performSaveItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeUI() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.priority_array));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(dataAdapter);
    }

    public void performSaveItem() {
        try {
            validateItem();

            saveItem();
        } catch (ValidationException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void validateItem() throws ValidationException {
        String item = etEditItem.getText().toString();
        if ("".equals(item.trim())) {
            throw new ValidationException(getString(R.string.edit_item_empty_validation));
        }
    }

    private void saveItem() {
        hideKeyboard();

        if (item == null) {
            item = new Item();
        }
        item.setDescription(etEditItem.getText().toString());
        item.setPriority(spPriority.getSelectedItemPosition());
        if (item != null) item.save();

        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etEditItem.getWindowToken(), 0);
    }
}
