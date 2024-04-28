package proj.inue.posis;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import proj.inue.posis.recyclerview.PAddCategoryItemViewAdapter;
import proj.inue.posis.utils.MockDatabase;

public class PAddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_padd_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (MockDatabase.categoryList.isEmpty()) MockDatabase.initAddCategoryItems();

        /* Initialization */
        ImageView back = findViewById(R.id.pac_back);
        RecyclerView rv = findViewById(R.id.pac_recycler_view);

        /* Data Bindings */
        back.setOnClickListener(e -> finish());
        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PAddCategoryItemViewAdapter(getApplicationContext(), MockDatabase.categoryList)); // Add the database object


    }
}