package proj.inue.posis.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
                    "price FLOAT,"+
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
