package proj.inue.posis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import proj.inue.posis.recyclerview.PViewInventoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItemViewAdapter;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.MockDatabase;

public class PViewInventoryActivity extends AppCompatActivity {

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
        if(MockDatabase.inventoryList.isEmpty()) MockDatabase.initInventoryItems();

        /* Initialization */
        TextView totalItems = findViewById(R.id.pvi_number_of_items_textview);
        RecyclerView rv = findViewById(R.id.pvi_recycler_view);

        /* Data Bindings */
        totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview), MockDatabase.inventoryList.size()));

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PViewInventoryItemViewAdapter(getApplicationContext(), MockDatabase.inventoryList)); // Add the database object

    }
}