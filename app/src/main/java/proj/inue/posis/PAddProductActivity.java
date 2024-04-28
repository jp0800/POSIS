package proj.inue.posis;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PAddProductActivity extends AppCompatActivity {

    String[] courses = {"C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_padd_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView activityTitle = findViewById(R.id.pap_activity_title);
        Spinner spin = findViewById(R.id.pap_category);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                                courses[position],
                                Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> ad
                = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            activityTitle.setText(bundle.getString("activityTitle", "Add Product"));
            System.out.println(">>>>>>>>>>>>>>>>>>");
            System.out.println(bundle.getString("itemDataJson"));
        }
    }
}