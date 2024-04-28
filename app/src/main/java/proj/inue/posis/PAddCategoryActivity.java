package proj.inue.posis;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import proj.inue.posis.recyclerview.PAddCategoryItem;
import proj.inue.posis.recyclerview.PAddCategoryItemViewAdapter;
import proj.inue.posis.utils.Helper;

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

        /* Setup Mock Variables */
        String jsonMock = Helper.stringsToJson(
                new String[]{
                        "categoryName", "edit", "delete"
                }, new String[]{
                        "Something",
                        String.valueOf(R.drawable.baseline_edit_square_24),
                        String.valueOf(R.drawable.baseline_delete_24)
                }
        );

        ArrayList<PAddCategoryItem> PAddCategoryItems = new ArrayList<>();
        PAddCategoryItems.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        PAddCategoryItems.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        PAddCategoryItems.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        PAddCategoryItems.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));

        /* Initialization */
        RecyclerView rv = findViewById(R.id.pac_recycler_view);

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PAddCategoryItemViewAdapter(getApplicationContext(), PAddCategoryItems)); // Add the database object


    }
}