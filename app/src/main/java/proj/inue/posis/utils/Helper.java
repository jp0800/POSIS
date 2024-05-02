package proj.inue.posis.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Helper {

    public static final String[] INVENTORY_LABELS = new String[]{
            "Price","Quantity", "Capital", "Total Price", "Barcode", "Item Left", "Item Purchased"
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

    public static String saveImage(Context context,Bitmap bitmap) {
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
}
