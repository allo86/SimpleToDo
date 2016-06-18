package com.allo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.allo.simpletodo.utils.ValidationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditItemActivity extends AppCompatActivity {

    public static final int EDIT_ITEM_REQUEST_CODE = 123;

    public static final String EDIT_ITEM_TEXT = "EDIT_ITEM_TEXT";
    public static final String EDIT_ITEM_POSITION = "EDIT_ITEM_POSITION";

    @BindView(R.id.etEditItem)
    EditText etEditItem;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        ButterKnife.bind(this);

        etEditItem.append(getIntent().getStringExtra(EDIT_ITEM_TEXT));
        position = getIntent().getIntExtra(EDIT_ITEM_POSITION, -1);
    }

    @OnClick(R.id.btnSaveItem)
    public void onSaveItem() {
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
        Intent data = new Intent();
        data.putExtra(EDIT_ITEM_TEXT, etEditItem.getText().toString());
        data.putExtra(EDIT_ITEM_POSITION, position);
        setResult(RESULT_OK, data);
        finish();
    }
}
