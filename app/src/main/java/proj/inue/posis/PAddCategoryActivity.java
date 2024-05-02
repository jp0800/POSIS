package proj.inue.posis;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import proj.inue.posis.recyclerview.PAddCategoryItem;
import proj.inue.posis.recyclerview.PAddCategoryItemViewAdapter;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.MockDatabase;
import proj.inue.posis.utils.SQLiteDatabase;

public class PAddCategoryActivity extends AppCompatActivity {

    SQLiteDatabase db;

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

        db = MainActivity.sqLiteDatabase;
        if (MockDatabase.categoryList.isEmpty()) MockDatabase.initAddCategoryItems(db);

        /* Initialization */
        ImageView back = findViewById(R.id.pac_back);
        ImageView add = findViewById(R.id.pac_add_button);
        EditText newCategory = findViewById(R.id.pac_new_category_edittext);

        RecyclerView rv = findViewById(R.id.pac_recycler_view);

        /* Data Bindings */
        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(new PAddCategoryItemViewAdapter(this, MockDatabase.categoryList, db)); // Add the database object

        /* Listeners */
        back.setOnClickListener(e -> finish());

        add.setOnClickListener(e -> {
            String newCategoryName = Helper.getTrimmedString(newCategory);
            boolean categoryNameIsValid = !newCategoryName.isEmpty();

            if (!categoryNameIsValid) {
                newCategory.setError("Field must not be empty");
                return;
            }

            try {
                ContentValues values = new ContentValues();
                values.put("name", newCategoryName);
                long row = db.getWritableDatabase().insert("CategoryList", null, values);

                MockDatabase.categoryList.add(new PAddCategoryItem(newCategoryName, row));
                Objects.requireNonNull(rv.getAdapter()).notifyItemInserted(MockDatabase.categoryList.size() - 1);
            } catch (Exception ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getLocalizedMessage()));
            }finally {
                newCategory.getText().clear();
            }
        });


    }
}