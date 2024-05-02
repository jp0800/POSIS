package proj.inue.posis;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import proj.inue.posis.recyclerview.PAddCategoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItem;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.MockDatabase;
import proj.inue.posis.utils.SQLiteDatabase;

public class PAddProductActivity extends AppCompatActivity {

    String[] courses;
    String selectedSpinnerItem;
    private ActivityResultLauncher<String> imagePickerLauncher;
    Bitmap bitmap;
    SQLiteDatabase db;

    PViewInventoryItem item;
    int adapterPosition = -1;

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
        /* Setup Variables */
        File directory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        db = MainActivity.sqLiteDatabase;
        if (MockDatabase.categoryList.isEmpty()) MockDatabase.initAddCategoryItems(db);

        courses = MockDatabase.categoryList.stream()
                .map(PAddCategoryItem::getCategoryName)
                .toArray(String[]::new);

        /* Initialization */
        ImageView back = findViewById(R.id.pap_back);
        ImageView image = findViewById(R.id.pap_product_image);

        TextView activityTitle = findViewById(R.id.pap_activity_title);
        Spinner spin = findViewById(R.id.pap_category);

        Button add = findViewById(R.id.pap_add_product);

        EditText[] editTexts = {
                findViewById(R.id.pap_product_name),
                findViewById(R.id.pap_price),
                findViewById(R.id.pap_quantity),
                findViewById(R.id.pap_barcode),
                findViewById(R.id.pap_capital)
        };

        /* Data Bindings */
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(ad);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            try {
                                // Convert the selected image URI to a bitmap
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);

                                // Display the selected image in the ImageView
                                image.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        // Set an OnClickListener to trigger image selection
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerLauncher.launch("image/*");
            }
        });

        /* Listeners */
        back.setOnClickListener(e -> finish());

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), courses[position], Toast.LENGTH_LONG).show();
                selectedSpinnerItem = courses[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        add.setOnClickListener(e -> {
            String name = Helper.getTrimmedString(editTexts[0]),
                    price = Helper.getTrimmedString(editTexts[1]),
                    quantity = Helper.getTrimmedString(editTexts[2]),
                    barcode = Helper.getTrimmedString(editTexts[3]),
                    capital = Helper.getTrimmedString(editTexts[4]),
                    category = selectedSpinnerItem;

            boolean nameIsNotEmpty = !name.isEmpty(),
                    priceIsNotEmpty = !price.isEmpty(),
                    quantityIsNotEmpty = !quantity.isEmpty(),
                    barcodeIsNotEmpty = !barcode.isEmpty(),
                    capitalIsNotEmpty = !capital.isEmpty(),
                    bitmapIsNotEmpty = bitmap != null;

            String error = "Field should not be empty";
            if (!bitmapIsNotEmpty)
                Toast.makeText(this, "Please select an image.", Toast.LENGTH_SHORT).show();
            if (!nameIsNotEmpty) editTexts[0].setError(error);
            if (!priceIsNotEmpty) editTexts[1].setError(error);
            if (!quantityIsNotEmpty) editTexts[2].setError(error);
            if (!barcodeIsNotEmpty) editTexts[3].setError(error);
            if (!capitalIsNotEmpty) editTexts[4].setError(error);

            if (bitmapIsNotEmpty
                    && nameIsNotEmpty
                    && priceIsNotEmpty
                    && quantityIsNotEmpty
                    && barcodeIsNotEmpty
                    && capitalIsNotEmpty) {

                // Insert
                try {
                    String imagePath = Helper.saveImage(this, bitmap);

                    ContentValues values = new ContentValues();
                    values.put("image", imagePath);
                    values.put("name", name);
                    values.put("category", category);
                    values.put("price", price);
                    values.put("quantity", quantity);
                    values.put("barcode", barcode);
                    values.put("capital", capital);
                    System.out.println("POTATATATATATATATATATATATATATA");
                    System.out.println(activityTitle.getText().toString());
                    System.out.println(!activityTitle.getText().toString().equals("Edit Product"));
                    if (!activityTitle.getText().toString().equals("Edit Product")) {

                        long row = db.getWritableDatabase().insert("ProductList", null, values);

                        MockDatabase.inventoryList.add(new PViewInventoryItem(
                                Integer.parseInt(String.valueOf(row)),
                                name,
                                category,
                                Helper.INVENTORY_LABELS,
                                new String[]{
                                        price, quantity, capital, "0", barcode, "500", "0"
                                }, imagePath)
                        );
                        for (EditText et : editTexts) et.getEditableText().clear();
                    } else {
                        String whereClause = "id = ?";
                        String[] whereArgs = {String.valueOf(item.getId())};

                        Helper.deleteImageFromFileSystem(item.getImage());

                        db.getWritableDatabase().update("ProductList", values, whereClause, whereArgs);

                        MockDatabase.inventoryList.set(adapterPosition, new PViewInventoryItem(
                                item.getId(),
                                name,
                                category,
                                Helper.INVENTORY_LABELS,
                                new String[]{
                                        price, quantity, capital, "0", barcode, "500", "0"
                                }, imagePath));
                        finish();
                    }

                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Log.e("ERROR", Objects.requireNonNull(ex.getLocalizedMessage()));
                }
            }
        });

        /* This is received data from the View Inventory */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            activityTitle.setText(bundle.getString("activityTitle", "Add Product"));
            item = new Gson().fromJson(bundle.getString("itemDataJson"), PViewInventoryItem.class);
            adapterPosition = bundle.getInt("adapterPosition");
            boolean found = Arrays.asList(courses).contains(item.getCategory());

            if (!found) {
                String[] newArray = new String[courses.length + 1];
                System.arraycopy(courses, 0, newArray, 0, courses.length);
                newArray[newArray.length - 1] = item.getCategory();
                courses = newArray;

                ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spin.setAdapter(ad);
            }

            int position = Arrays.asList(courses).indexOf(item.getCategory());
            bitmap = Helper.loadImageFromFileSystem(item.getImage());
            image.setImageBitmap(bitmap);
            editTexts[0].setText(item.getTitle());

            selectedSpinnerItem = item.getCategory();
            spin.setSelection(position);

            editTexts[1].setText(item.getContent()[0]);
            editTexts[2].setText(item.getContent()[1]);
            editTexts[3].setText(item.getContent()[4]);
            editTexts[4].setText(item.getContent()[2]);


        }
    }
}