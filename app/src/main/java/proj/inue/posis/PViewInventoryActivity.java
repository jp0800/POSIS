package proj.inue.posis;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proj.inue.posis.recyclerview.PViewInventoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItemViewAdapter;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.MockDatabase;
import proj.inue.posis.utils.SQLiteDatabase;

public class PViewInventoryActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pview_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Temporary Database */
        db = MainActivity.sqLiteDatabase;
        if (MockDatabase.inventoryList.isEmpty()) MockDatabase.initInventoryItems(db);

        /* Initialization */
        RadioButton[] filterOptions = {
                findViewById(R.id.pvi_filter_by_name),
                findViewById(R.id.pvi_filter_by_price),
                findViewById(R.id.pvi_filter_by_purchased),
                findViewById(R.id.pvi_filter_by_quantity),
                findViewById(R.id.pvi_order_asc),
                findViewById(R.id.pvi_order_desc)
        };
        ImageView back = findViewById(R.id.pvi_back);
        ImageView filter = findViewById(R.id.pvi_filter_imageview);

        EditText searchBar = findViewById(R.id.pvi_search_edittext);
        ImageView searchButton = findViewById(R.id.pvi_search_button);

        ConstraintLayout filterContainer = findViewById(R.id.pvi_filter_container);

        TextView totalItems = findViewById(R.id.pvi_number_of_items_textview);
        RecyclerView rv = findViewById(R.id.pvi_recycler_view);

        /* Data Bindings */
        totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview), MockDatabase.inventoryList.size()));

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PViewInventoryItemViewAdapter(this, MockDatabase.inventoryList, db)); // Add the database object

        /* Listeners */
        back.setOnClickListener(e -> finish());
        filter.setOnClickListener(e -> {
            showHideFilterOptions(filterContainer, false);
        });

        searchButton.setOnClickListener(e -> {
            performSearch(searchBar, totalItems, rv, filterOptions);
            showHideFilterOptions(filterContainer, true);
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(searchBar, totalItems, rv, filterOptions);
                    showHideFilterOptions(filterContainer, true);
                    return true;
                }
                return false;
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    rv.setAdapter(new PViewInventoryItemViewAdapter(PViewInventoryActivity.this, MockDatabase.inventoryList, db)); // Add the database object
                    totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview),  MockDatabase.inventoryList.size()));
                    showHideFilterOptions(filterContainer, true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView rv = findViewById(R.id.pvi_recycler_view);
        rv.setAdapter(new PViewInventoryItemViewAdapter(this, MockDatabase.inventoryList, db));
    }

    private void performSearch(EditText searchBar, TextView totalItems, RecyclerView rv, RadioButton[] filterOptions) {
        String searchValue = searchBar.getEditableText().toString().trim();
        boolean searchValueIsNotEmpty = !searchValue.isEmpty();

        if (!searchValueIsNotEmpty) {
            searchBar.setError("Field must not be empty.");
            return;
        }
        ArrayList<PViewInventoryItem> result = Helper.filterProducts(db, searchValue, filterOptions[0].isChecked(), filterOptions[1].isChecked(), filterOptions[3].isChecked(), filterOptions[4].isChecked());
        rv.setAdapter(new PViewInventoryItemViewAdapter(this, result, db));
        totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview), result.size()));
    }

    private void showHideFilterOptions(ConstraintLayout filterContainer, boolean forceHide){
        if(forceHide){
            filterContainer.setVisibility(View.GONE);
            return;
        }
        int visibility = filterContainer.getVisibility();
        filterContainer.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}