package proj.inue.posis.recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import proj.inue.posis.R;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.SQLiteDatabase;

public class PAddCategoryItemViewAdapter extends RecyclerView.Adapter<PAddCategoryItemViewAdapter.ItemViewHolder> {

    Context context;
    List<PAddCategoryItem> PAddCategoryItems;
    SQLiteDatabase db;


    public PAddCategoryItemViewAdapter(Context context, List<PAddCategoryItem> PAddCategoryItems, SQLiteDatabase db) {
        this.context = context;
        this.PAddCategoryItems = PAddCategoryItems;
        this.db = db;
    }

    @NonNull
    @Override
    public PAddCategoryItemViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PAddCategoryItemViewAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_padd_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PAddCategoryItemViewAdapter.ItemViewHolder holder, int position) {
        final int[] imageIcons = {
                R.drawable.baseline_edit_square_24,
                R.drawable.baseline_delete_24,
                R.drawable.baseline_save_24,
                R.drawable.baseline_cancel_24
        };

        String categoryName = PAddCategoryItems.get(position).getCategoryName();
        holder.categoryName.setText(categoryName);

        holder.imageViews.get(0).setImageResource(imageIcons[0]);
        holder.imageViews.get(1).setImageResource(imageIcons[1]);

        holder.imageViews.get(0).setOnClickListener(e -> {
            if (holder.imageViews.get(0).getTag().equals("edit")) {
                Helper.setEditTextEnabled(context, holder.categoryName, true);

                holder.imageViews.get(0).setTag("cancel");
                holder.imageViews.get(0).setImageResource(imageIcons[3]);
                holder.imageViews.get(1).setImageResource(imageIcons[2]);
            } else {
                Helper.openAlertYesNo(
                        context, "Are you sure?",
                        "This will discard your changes.",
                        (dialog, which) -> {
                            holder.categoryName.setText(categoryName);
                            holder.categoryName.setError(null);

                            Helper.setEditTextEnabled(context, holder.categoryName, false);

                            holder.imageViews.get(0).setTag("edit");
                            holder.imageViews.get(0).setImageResource(imageIcons[0]);
                            holder.imageViews.get(1).setImageResource(imageIcons[1]);
                        }, null
                );

            }
        });

        holder.imageViews.get(1).setOnClickListener(e -> {
            if (holder.imageViews.get(0).getTag().equals("edit")) {

                Helper.openAlertYesNo(
                        context,
                        "POSIS: Warning",
                        String.format("Are you sure you want to remove %s?", categoryName),
                        (dialog, which) -> {
                            int adapterPosition = holder.getAdapterPosition();

                            Helper.deleteSingleById(db, "CategoryList", PAddCategoryItems.get(adapterPosition).getId());
                            Toast.makeText(context, String.format("Successfully Deleted: %s", categoryName), Toast.LENGTH_SHORT).show();

                            PAddCategoryItems.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                        },
                        null
                );
            } else {
                String newCategoryName = Helper.getTrimmedString(holder.categoryName);
                boolean categoryNameIsValid = !newCategoryName.isEmpty();

                if (!categoryNameIsValid) {
                    holder.categoryName.setError("Field must not be Empty");
                    return;
                }

                ContentValues values = new ContentValues();
                values.put("name", newCategoryName);

                String whereClause = "id = ?";
                String[] whereArgs = {String.valueOf(PAddCategoryItems.get(position).getId())};

                db.getWritableDatabase().update("CategoryList", values, whereClause, whereArgs);
                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();

                holder.categoryName.setText(newCategoryName);

                holder.imageViews.get(0).setTag("edit");
                holder.imageViews.get(0).setImageResource(imageIcons[0]);
                holder.imageViews.get(1).setImageResource(imageIcons[1]);

                PAddCategoryItems.get(position).setCategoryName(newCategoryName);

                Helper.setEditTextEnabled(context, holder.categoryName, false);
                notifyItemChanged(position);
            }

        });
    }

    @Override
    public int getItemCount() {
        return PAddCategoryItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        EditText categoryName;
        ArrayList<ImageView> imageViews = new ArrayList<>(3);

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.rvaci_category_name);

            imageViews.add(itemView.findViewById(R.id.rvaci_edit));
            imageViews.add(itemView.findViewById(R.id.rvaci_delete));
        }
    }

}
