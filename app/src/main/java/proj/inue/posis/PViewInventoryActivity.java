package proj.inue.posis;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import proj.inue.posis.recyclerview.Item;
import proj.inue.posis.recyclerview.ItemViewAdapter;
import proj.inue.posis.utils.Helper;

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

        /* Setup Mock Variables */
        String jsonMock = Helper.stringsToJson(
                new String[]{
                        "title", "category", "label", "content", "image", "edit", "delete"
                }, new String[]{
                        "Something",
                        "lol",
                        "Price:\nQuantity:\nCapital:\nTotal Price:\nBarcode:\nItem Left:\nItem Purchased:",
                        "P 235.00\n5000 pcs\nP 1,175.00\nP 2,350.00\n 0 705632 441947\n2500\n2500",
                        String.valueOf(R.drawable.as_logo),
                        String.valueOf(R.drawable.baseline_edit_square_24),
                        String.valueOf(R.drawable.baseline_delete_24)
                }
        );

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Gson().fromJson(jsonMock, Item.class));
        items.add(new Gson().fromJson(jsonMock, Item.class));
        items.add(new Gson().fromJson(jsonMock, Item.class));
        items.add(new Gson().fromJson(jsonMock, Item.class));

        /* Initialization */
        TextView totalItems = findViewById(R.id.pvi_number_of_items_textview);
        RecyclerView rv = findViewById(R.id.pvi_recycler_view);

        /* Data Bindings */
        totalItems.setText(String.format("%s %s", getResources().getString(R.string.pvi_number_of_items_textview), items.size()));


        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new ItemViewAdapter(getApplicationContext(), items)); // Add the database object
    }
}