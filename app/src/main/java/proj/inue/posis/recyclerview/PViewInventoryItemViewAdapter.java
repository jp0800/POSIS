package proj.inue.posis.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proj.inue.posis.PAddProductActivity;
import proj.inue.posis.R;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.SQLiteDatabase;

public class PViewInventoryItemViewAdapter extends RecyclerView.Adapter<PViewInventoryItemViewAdapter.ItemViewHolder> {

    Context context;
    ArrayList<PViewInventoryItem> PViewInventoryItems;
    SQLiteDatabase db;

    public PViewInventoryItemViewAdapter(Context context, ArrayList<PViewInventoryItem> PViewInventoryItems, SQLiteDatabase db) {
        this.context = context;
        this.PViewInventoryItems = PViewInventoryItems;
        this.db = db;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_pview_inventory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textViews.get(0).setText(PViewInventoryItems.get(position).getTitle());
        holder.textViews.get(1).setText(PViewInventoryItems.get(position).getCategory());
        holder.textViews.get(2).setText(PViewInventoryItems.get(position).getLabelString());
        holder.textViews.get(3).setText(PViewInventoryItems.get(position).getContentString());

        holder.imageViews.get(0).setImageBitmap(Helper.loadImageFromFileSystem(PViewInventoryItems.get(position).getImage()));
        holder.imageViews.get(1).setImageResource(R.drawable.baseline_edit_square_24);
        holder.imageViews.get(2).setImageResource(R.drawable.baseline_delete_24);

        holder.imageViews.get(1).setOnClickListener(e -> {
            Intent intent = new Intent(context, PAddProductActivity.class);
            intent.putExtra("itemDataJson", PViewInventoryItems.get(position).toJson());
            intent.putExtra("activityTitle", "Edit Product");
            intent.putExtra("adapterPosition", position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });

        holder.imageViews.get(2).setOnClickListener(e -> {
            Helper.openAlertYesNo(
                    context,
                    "POSIS: Warning",
                    String.format("Are you sure you want to remove %s?", PViewInventoryItems.get(position).getTitle()),
                    (dialog, which) -> {
                        int adapterPosition = holder.getAdapterPosition();

                        Helper.deleteImageFromFileSystem(PViewInventoryItems.get(position).getImage());
                        Helper.deleteSingleById(db, "ProductList", PViewInventoryItems.get(adapterPosition).getId());
                        Toast.makeText(context, String.format("Successfully Deleted: %s", PViewInventoryItems.get(position).getTitle()), Toast.LENGTH_SHORT).show();

                        PViewInventoryItems.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                    },
                    null);

        });
    }

    @Override
    public int getItemCount() {
        return PViewInventoryItems.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ArrayList<TextView> textViews = new ArrayList<>(4);
        ArrayList<ImageView> imageViews = new ArrayList<>(3);

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViews.add(itemView.findViewById(R.id.rvvii_title));
            textViews.add(itemView.findViewById(R.id.rvvii_category));
            textViews.add(itemView.findViewById(R.id.rvvii_label));
            textViews.add(itemView.findViewById(R.id.rvvii_content));

            imageViews.add(itemView.findViewById(R.id.rvvii_image));
            imageViews.add(itemView.findViewById(R.id.rvvii_edit));
            imageViews.add(itemView.findViewById(R.id.rvvii_delete));
        }
    }

}
