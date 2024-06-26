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

    public interface OnItemClickListener {
        void onItemChanged(int position, PViewInventoryItem newValue);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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
        ArrayList<TextView> textViews = holder.textViews;
        ArrayList<ImageView> imageViews = holder.imageViews;

        PViewInventoryItem item = PViewInventoryItems.get(position);

        /* Data Binding */
        textViews.get(0).setText(item.getTitle());
        textViews.get(1).setText(item.getCategory());
        textViews.get(2).setText(item.getLabelString());
        textViews.get(3).setText(item.getContentString());

        imageViews.get(0).setImageBitmap(Helper.loadImageFromFileSystem(item.getImage()));
        imageViews.get(1).setImageResource(R.drawable.baseline_edit_square_24);
        imageViews.get(2).setImageResource(R.drawable.baseline_delete_24);

        /* Listeners */
        imageViews.get(1).setOnClickListener(e -> {
            Intent intent = new Intent(context, PAddProductActivity.class);
            intent.putExtra("itemDataJson", item.toJson());
            intent.putExtra("activityTitle", "Edit Product");
            intent.putExtra("adapterPosition", position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });

        imageViews.get(2).setOnClickListener(e -> {
            Helper.openAlertYesNo(
                    context,
                    "POSIS: Warning",
                    String.format("Are you sure you want to remove %s?", item.getTitle()),
                    (dialog, which) -> {
                        int adapterPosition = holder.getAdapterPosition();

                        Helper.deleteImageFromFileSystem(item.getImage());
                        Helper.deleteSingleById(db, "ProductList", item.getId());
                        Toast.makeText(context, String.format("Successfully Deleted: %s", item.getTitle()), Toast.LENGTH_SHORT).show();

                        mListener.onItemChanged(position, item);
                        PViewInventoryItems.remove(position);
                        notifyItemRemoved(position);
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
