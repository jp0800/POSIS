package proj.inue.posis.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Statement;

public class SQLiteDatabase extends SQLiteOpenHelper {


    public SQLiteDatabase(Context context) {
        super(context, "POSIS_DB.db", null, 1);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.beginTransaction();
        try {

            String queryCreateProductList = "CREATE TABLE ProductList (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "category TEXT," +
                    "price FLOAT," +
                    "quantity INTEGER," +
                    "barcode LONG," +
                    "capital FLOAT," +
                    "image TEXT" +
                    ");";

            String queryCreateCategoryList = "CREATE TABLE CategoryList (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT);";

            db.execSQL(queryCreateProductList);
            db.execSQL(queryCreateCategoryList);

            createMockItems(db);
            db.setTransactionSuccessful();
            Log.d("Database", "Tables created successfully.");

        } catch (SQLException e) {
            Log.e("Database", "Error creating tables: " + e.getMessage());
        } finally {
            db.endTransaction();
        }


    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ProductList");
        db.execSQL("DROP TABLE IF EXISTS CategoryList");

        onCreate(db);
    }

    void createMockItems(android.database.sqlite.SQLiteDatabase db) {


//        String[] mockCategoryNames = {"Category 1", "Category 2", "Category 3"};
//
//        // Loop through mock category names and insert them into the table
//        for (String categoryName : mockCategoryNames) {
//            String insertQuery = "INSERT INTO CategoryList (name) VALUES ('" + categoryName + "');";
//
//            // Execute the INSERT query
//            // Note: You should use a SQLiteDatabase instance to execute this query
//            // Example: db.execSQL(insertQuery);
//            // Ensure that you handle exceptions and close resources properly
//            db.execSQL(insertQuery);
//
//        }
//
//        String[] mockProductNames = {"Product 1", "Product 2", "Product 3"};
//        String[] mockCategories = {"Category A", "Category B", "Category C"};
//        float[] mockPrices = {10.99f, 20.49f, 15.99f};
//        int[] mockQuantities = {100, 50, 200};
//        long[] mockBarcodes = {1234567890123L, 9876543210987L, 5555555555555L};
//        float[] mockCapitals = {5.99f, 10.49f, 8.99f};
//        String[] mockImages = {"/data/user/0/proj.inue.posis/files/image1.jpg", "/data/user/0/proj.inue.posis/files/image2.jpg", "/data/user/0/proj.inue.posis/files/image3.jpg"};
//
//        // Loop through mock data arrays and insert mock items into the table
//        for (int i = 0; i < mockProductNames.length; i++) {
//            String insertQuery = "INSERT INTO ProductList (name, category, price, quantity, barcode, capital, image) VALUES ('" +
//                    mockProductNames[i] + "', '" +
//                    mockCategories[i] + "', " +
//                    mockPrices[i] + ", " +
//                    mockQuantities[i] + ", " +
//                    mockBarcodes[i] + ", " +
//
//                    mockCapitals[i] + ", '" +
//                    mockImages[i] + "');";
//
//            // Execute the INSERT query
//            // Note: You should use a SQLiteDatabase instance to execute this query
//            // Example:
//            // Ensure that you handle exceptions and close resources properly
//            db.execSQL(insertQuery);
//            db.execSQL(insertQuery);
//            db.execSQL(insertQuery);

        // Mock data for ProductList table
        String[] names = {"Product A", "Product B", "Product C", "Product D", "Product E", "Product F", "Product G", "Product H", "Product I", "Product J"};
        String[] categories = {"Category 1", "Category 2", "Category 3", "Category 1", "Category 2", "Category 3", "Category 1", "Category 2", "Category 3", "Category 1"};
        float[] prices = {10.99f, 15.49f, 20.79f, 8.99f, 12.59f, 18.99f, 9.99f, 14.29f, 22.99f, 17.99f};
        int[] quantities = {100, 50, 80, 120, 70, 90, 110, 60, 85, 75};
        long[] barcodes = {1234567890123L, 2345678901234L, 3456789012345L, 4567890123456L, 5678901234567L, 6789012345678L, 7890123456789L, 8901234567890L, 9012345678901L, 1234567890123L};
        float[] capitals = {8.99f, 12.49f, 18.29f, 7.99f, 10.59f, 15.99f, 8.99f, 13.29f, 21.99f, 16.99f};
        String[] images = {"image1.jpg", "image2.jpg", "image3.jpg", "image4.jpg", "image5.jpg", "image6.jpg", "image7.jpg", "image8.jpg", "image9.jpg", "image10.jpg"};

        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ProductList (name, category, price, quantity, barcode, capital, image) VALUES ");

// Build SQL insert statements
        for (int i = 0; i < names.length; i++) {
            insertQuery.append(String.format("('%s', '%s', %.2f, %d, %d, %.2f, '%s')",
                    names[i], categories[i], prices[i], quantities[i], barcodes[i], capitals[i], images[i]));

            if (i < names.length - 1) {
                insertQuery.append(", ");
            }
        }

// Execute the SQL insert statements against your SQLite database
        try {
            db.execSQL(insertQuery.toString());

            System.out.println("Mock data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting mock data: " + e.getMessage());
        }


    }
    /*
     * TODO: Add 3 Tables * Product, Category, Earnings
     * - Product
     *    - ID
     *    - Name
     *    - Category (this will not cascade)
     *    - Price
     *    - Quantity
     *    - Barcode (Typed)
     *    - Capital
     * - Category
     *    - ID
     *    - Name
     * - Earnings
     *    - ID
     *    - TransactionID (YYYYMMDD<ID><CategoryFirstLetter>)
     *    -
     *    - Capital
     *    - Earned
     */
}
