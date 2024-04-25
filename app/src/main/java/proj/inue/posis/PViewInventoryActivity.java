package proj.inue.posis;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proj.inue.posis.recyclerview.Item;
import proj.inue.posis.recyclerview.ItemViewAdapter;

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

        RecyclerView rv = findViewById(R.id.pvi_recycler_view);
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Something", "lol", "Price:\nQuantity:\nCapital:\nTotal Price:\nBarcode:\nItem Left:\nItem Purchased:", "P 235.00\n5000 pcs\nP 1,175.00\nP 2,350.00\n 0 705632 441947\n2500\n2500",
                R.drawable.as_logo,R.drawable.baseline_edit_square_24,R.drawable.baseline_delete_24));

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new ItemViewAdapter(getApplicationContext(),items));
    }
}