package com.allo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.allo.simpletodo.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * Main Acivity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvItems)
    ListView lvItems;

    @BindView(R.id.tvEmptyMessage)
    TextView tvEmptyMessage;

    List<Item> items;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        lvItems.setEmptyView(tvEmptyMessage);

        reloadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_new:
                addItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EditItemActivity.EDIT_ITEM_REQUEST_CODE) {
            reloadData();
        }
    }

    private void reloadData() {
        items = new Select().from(Item.class).execute();
        if (itemsAdapter != null) {
            itemsAdapter.notifyDataSetChanged(items);
        } else {
            itemsAdapter = new ItemsAdapter(this, items);
            lvItems.setAdapter(itemsAdapter);
        }
    }

    private void addItem() {
        Intent intent = new Intent(this, EditItemActivity.class);
        startActivityForResult(intent, EditItemActivity.EDIT_ITEM_REQUEST_CODE);
    }

    @OnItemClick(R.id.lvItems)
    public void onListItemClick(View view, int position) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(EditItemActivity.EDIT_ITEM_ID, items.get(position).getId());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view,
                EditItemActivity.EDIT_ITEM_DESCRIPTION);
        ActivityCompat.startActivityForResult(this, intent, EditItemActivity.EDIT_ITEM_REQUEST_CODE, options.toBundle());
    }

    @OnItemLongClick(R.id.lvItems)
    public boolean onListItemLongClick(int position) {
        items.get(position).delete();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();

        return true;
    }
}
