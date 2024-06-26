package proj.inue.posis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private static final int
            B_VIEW_INVENTORY = 0,
            B_ADD_PRODUCT = 1,
            B_ADD_CATEGORY = 2,
            B_POS = 3,
            B_EARNINGS = 4,
            B_EXIT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Button> menuButtons = new ArrayList<>(6);

        menuButtons.add(findViewById(R.id.ah_view_inventory_button));
        menuButtons.add(findViewById(R.id.ah_add_product_button));
        menuButtons.add(findViewById(R.id.ah_add_category_button));
        menuButtons.add(findViewById(R.id.ah_pos_button));
        menuButtons.add(findViewById(R.id.ah_earnings_button));
        menuButtons.add(findViewById(R.id.ah_exit_button));

        menuButtons.get(B_VIEW_INVENTORY).setOnClickListener(e -> {
            Intent intent = new Intent(Home.this, PViewInventoryActivity.class);
            startActivity(intent);
        });
        menuButtons.get(B_ADD_PRODUCT).setOnClickListener(e -> {
            Intent intent = new Intent(Home.this, PAddProductActivity.class);
            startActivity(intent);
        });
        menuButtons.get(B_ADD_CATEGORY).setOnClickListener(e -> {
            Intent intent = new Intent(Home.this, PAddCategoryActivity.class);
            startActivity(intent);
        });
        menuButtons.get(B_POS).setOnClickListener(e -> {
            Intent intent = new Intent(Home.this, PPointOfSaleActivity.class);
            startActivity(intent);
        });
        menuButtons.get(B_EARNINGS).setOnClickListener(e -> {
            Intent intent = new Intent(Home.this, PEarningsActivity.class);
            startActivity(intent);
        });
        menuButtons.get(B_EXIT).setOnClickListener(e -> {
            finish();
        });
    }
}