package proj.inue.posis.utils;

import com.google.gson.Gson;

import java.util.ArrayList;

import proj.inue.posis.R;
import proj.inue.posis.recyclerview.PAddCategoryItem;
import proj.inue.posis.recyclerview.PViewInventoryItem;

public class MockDatabase {
    public static ArrayList<PViewInventoryItem> inventoryList = new ArrayList<>();
    public static ArrayList<PAddCategoryItem> categoryList = new ArrayList<>();

    public static void initInventoryItems(){
        /* Setup Mock Variables */
        String jsonMock = Helper.stringsToJson(
                new String[]{
                        "title", "category", "label", "content", "image", "edit", "delete"
                }, new String[]{
                        "Something",
                        "lol",
                        "['PriceQuantity','Capital','Total Price','Barcode','Item Left','Item','Purchased']",
                        "['P 235.00','5000 pcs','P 1,175.00 ','P 2,350.00', '0 705632 441947', '2500', '2500']",
                        String.valueOf(R.drawable.as_logo),
                        String.valueOf(R.drawable.baseline_edit_square_24),
                        String.valueOf(R.drawable.baseline_delete_24)
                }
        );

        inventoryList.add(new Gson().fromJson(jsonMock, PViewInventoryItem.class));
        inventoryList.add(new Gson().fromJson(jsonMock, PViewInventoryItem.class));
        inventoryList.add(new Gson().fromJson(jsonMock, PViewInventoryItem.class));
        inventoryList.add(new Gson().fromJson(jsonMock, PViewInventoryItem.class));
    }

    public static void initAddCategoryItems(){
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

        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
        categoryList.add(new Gson().fromJson(jsonMock, PAddCategoryItem.class));
    }
}
