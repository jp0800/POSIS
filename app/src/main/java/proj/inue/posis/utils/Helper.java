package proj.inue.posis.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj.inue.posis.PViewInventoryActivity;
import proj.inue.posis.recyclerview.PPointOfSaleItem;
import proj.inue.posis.recyclerview.PViewInventoryItem;

public class Helper {

    public static final String[] INVENTORY_LABELS = new String[]{
            "Price", "Quantity", "Capital", "Total Price", "Barcode", "Item Left", "Item Purchased"
    };
    public static final String[] POS_LABELS = new String[]{
            "Item Left", "Price"
    };

    public static String stringsToJson(String[] keys, String[] values) {
        StringBuilder out = new StringBuilder("{");

        for (int i = 0; i < keys.length; i++) {
            if (values[i].charAt(0) == '[')
                out.append(String.format("'%s':%s", keys[i], values[i]));
            else out.append(String.format("'%s':'%s'", keys[i], values[i]));
            if (i < keys.length - 1) out.append(",");
        }
        out.append("}");

        return out.toString();
    }

    public static String getArrayToString(String[] array, char separator, char prefix, char suffix) {
        StringBuilder out = new StringBuilder();
        for (String item : array) {
            out.append(String.format("%c%s%c%c", prefix, item, separator, suffix));
        }
        return out.toString();
    }

    public static void openAlertYesNo(Context context, String title, String message, DialogInterface.OnClickListener actionYes, DialogInterface.OnClickListener actionNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", actionYes);
        builder.setNegativeButton("No", actionNo); // No action on click

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String getTrimmedString(TextView view) {
        return view.getEditableText().toString().trim();
    }

    public static Bitmap loadImageFromFileSystem(String imagePath) {
        return BitmapFactory.decodeFile(imagePath);
    }

//    public static Bitmap loadImageFromFileSystem(String imagePath) {
//        // Create a mock Bitmap
//        int width = 100; // Width of the mock Bitmap
//        int height = 100; // Height of the mock Bitmap
//        Bitmap.Config config = Bitmap.Config.ARGB_8888; // Bitmap configuration
//        Bitmap mockBitmap = Bitmap.createBitmap(width, height, config);
//
//        // You can optionally draw something on the mock Bitmap if neede
//        // d
//        // For example:
//        Canvas canvas = new Canvas(mockBitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawRect(0, 0, width, height, paint);
//
//        return mockBitmap;
//    }

    public static String saveImage(Context context, Bitmap bitmap) {
        File directory = context.getFilesDir();
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";

        File imageFile = new File(directory, fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteImageFromFileSystem(String imagePath) {
        // Create a File object representing the image file
        File imageFile = new File(imagePath);

        // Check if the image file exists
        if (imageFile.exists()) {
            // Delete the image file
            return imageFile.delete();
        }

        // Return false if the image file does not exist
        return false;
    }

    public static void setEditTextEnabled(Context context, EditText et, boolean isEnabled) {
        et.setEnabled(isEnabled);
        if (isEnabled) {
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        } else {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }

    }

    public static void deleteSingleById(SQLiteDatabase db, String table, long id) {
        db.getWritableDatabase().delete(table, "id = ?", new String[]{String.valueOf(id)});
    }

    public static ArrayList<PViewInventoryItem> filterProducts(SQLiteDatabase db, String searchValue, boolean filterByName, boolean filterByPrice, boolean filterByQuantity, boolean ascendingOrder) {
        ArrayList<PViewInventoryItem> productList = new ArrayList<>();

        int MAX_PRICE = 99999, MIN_PRICE = 1;
        int MAX_QUANTITY = 99999, MIN_QUANTITY = 1;

        String isAscendingOrder = ascendingOrder ? "ASC" : "DESC";

        String query = "SELECT * FROM ProductList WHERE ";
            query += String.format("name LIKE '%%%s%%'", searchValue);
        if (filterByName) {
            query += String.format(" ORDER BY name %s", isAscendingOrder); // Replace column_name with the actual column name
        }
        if (filterByPrice) {
//            query += String.format(" AND price >= %d AND price <= %d", MIN_PRICE, MAX_PRICE);
            query += String.format(" ORDER BY price %s", isAscendingOrder); // Replace column_name with the actual column name

        }
        if (filterByQuantity) {
//            query += String.format(" AND quantity >= %d AND quantity <= %d", MIN_QUANTITY, MAX_QUANTITY);
            query += String.format(" ORDER BY quantity %s", !ascendingOrder ? "ASC" : "DESC"); // Replace column_name with the actual column name
        }
//        if (ascendingOrder) {
//        } else {
//            query += " ORDER BY column_name DESC"; // Replace column_name with the actual column name
//        }

        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        // Similar code to fetch data and populate productList
        while (cursor.moveToNext()) {

            String[] contents = new String[]{
                    String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("price"))),
                    String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))),
                    String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("capital"))),
                    "0",
                    String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("barcode"))),
                    "500",
                    "0"
            };

            PViewInventoryItem item = new PViewInventoryItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("category")),
                    Helper.INVENTORY_LABELS,
                    contents,
                    cursor.getString(cursor.getColumnIndexOrThrow("image"))
            );

            productList.add(item);
        }
        return productList;
    }

    public static float computeTotalItems(ArrayList<PPointOfSaleItem> items){
        float sum = 0;

        if(items.size() == 1){
            PPointOfSaleItem item = items.get(0);
            return item.getMultiplier() * Float.parseFloat(item.getContent()[1]);
        }
        for (PPointOfSaleItem item : items) {
            String[] content = item.getContent();
            float multiplier = item.getMultiplier();
            float price = Float.parseFloat(content[1]);

            sum += (multiplier * price);
        }

        return sum;
    }
}
