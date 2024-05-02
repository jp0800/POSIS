package proj.inue.posis;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import proj.inue.posis.recyclerview.PViewInventoryItemViewAdapter;
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
        ImageView back = findViewById(R.id.pvi_back);
        ImageView filter = findViewById(R.id.pvi_filter_imageview);

        ConstraintLayout filterContainer = findViewById(R.id.pvi_filter_container);

        TextView totalItems = findViewById(R.id.pvi_number_of_items_textview);
        RecyclerView rv = findViewById(R.id.pvi_recycler_view);

        /* Data Bindings */
        back.setOnClickListener(e -> finish());
        filter.setOnClickListener(e->{
            int visibility = filterContainer.getVisibility();
            filterContainer.setVisibility(visibility == View.VISIBLE? View.GONE : View.VISIBLE);
        });
        totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview), MockDatabase.inventoryList.size()));

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PViewInventoryItemViewAdapter(this, MockDatabase.inventoryList, db)); // Add the database object
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView rv = findViewById(R.id.pvi_recycler_view);
        rv.setAdapter(new PViewInventoryItemViewAdapter(this, MockDatabase.inventoryList, db));
    }
}