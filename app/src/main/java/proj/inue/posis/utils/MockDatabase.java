package proj.inue.posis.utils;

import android.database.Cursor;

import java.util.ArrayList;

import proj.inue.posis.R;
import proj.inue.posis.recyclerview.PAddCategoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItem;

/**
 * This will be renamed to CacheDatabase
 */
public class MockDatabase {
    public static ArrayList<PViewInventoryItem> inventoryList = new ArrayList<>();
    public static ArrayList<PAddCategoryItem> categoryList = new ArrayList<>();

    public static void initInventoryItems(SQLiteDatabase db) {

        try (Cursor cursor = db.getReadableDatabase().query("ProductList", null, null, null, null, null, null)) {
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

                MockDatabase.inventoryList.add(item);
            }
        }
    }

    public static void initAddCategoryItems(SQLiteDatabase db) {
        try (Cursor cursor = db.getReadableDatabase().query("CategoryList", null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                PAddCategoryItem item = new PAddCategoryItem(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")
                        ));
                MockDatabase.categoryList.add(item);
            }
        }
//
//        /* Setup Mock Variables */
//        String jsonMock = Helper.stringsToJson(
//                new String[]{
//                        "id","categoryName"
//                }, new String[]{
//                        String.valueOf(0),
//                        "Something",
//                }
//        );
//
//        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
//        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
//        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
//        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
    }
}
