package proj.inue.posis;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import proj.inue.posis.recyclerview.PPointOfSaleItem;
import proj.inue.posis.recyclerview.PPointOfSaleItemViewAdapter;
import proj.inue.posis.recyclerview.PViewInventoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItemViewAdapter;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.MockDatabase;
import proj.inue.posis.utils.SQLiteDatabase;

public class PPointOfSaleActivity extends AppCompatActivity {

    SQLiteDatabase db;

    float totalSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ppoint_of_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Initialize Database */
        db = MainActivity.sqLiteDatabase;
        if (MockDatabase.inventoryList.isEmpty()) MockDatabase.initInventoryItems(db);

        /* Initialize Components */
        ImageView back = findViewById(R.id.ppos_back);

        EditText searchBar = findViewById(R.id.ppos_search_edittext);
        ImageView searchButton = findViewById(R.id.ppos_search_button);

        EditText cashBar = findViewById(R.id.ppos_cash_edittext);

        TextView posTotal = findViewById(R.id.ppos_total);
        TextView posChange = findViewById(R.id.ppos_change);

        RecyclerView rv = findViewById(R.id.ppos_recycler_view);

        /* Data Bindings */
        rv.setLayoutManager(new LinearLayoutManager((this)));

        PPointOfSaleItemViewAdapter adapter = new PPointOfSaleItemViewAdapter(this, MockDatabase.pointOfSaleList, db);
        rv.setAdapter(adapter); // Add the database object

        totalSum = Helper.computeTotalItems(MockDatabase.pointOfSaleList);
        DecimalFormat floatFormatter = new DecimalFormat("#.##");
        posTotal.setText(floatFormatter.format(totalSum));

        /* Listeners */
        back.setOnClickListener(e -> finish());

        adapter.setOnItemClickListener((position, newValue) -> {
            totalSum = Helper.computeTotalItems(MockDatabase.pointOfSaleList);
            String newTotal = floatFormatter.format(totalSum);
            posTotal.setText(newTotal);

            if (cashBar.getEditableText().toString().isEmpty()) return;
            float difference = Float.parseFloat(cashBar.getEditableText().toString()) - totalSum;
            posChange.setText(floatFormatter.format(difference));
            if (difference < 0) cashBar.setError("Not enough money");

        });

        searchButton.setOnClickListener(e -> {
            Toast.makeText(this, "Feature not yet available", Toast.LENGTH_SHORT).show();
        });

        cashBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                float difference = Float.parseFloat(s.toString()) - totalSum;
                posChange.setText(floatFormatter.format(difference));
                if (difference < 0) cashBar.setError("Not enough money");
            }
        });

    }
}