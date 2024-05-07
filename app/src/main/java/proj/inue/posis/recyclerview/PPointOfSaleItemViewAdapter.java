package proj.inue.posis.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proj.inue.posis.R;
import proj.inue.posis.utils.Helper;
import proj.inue.posis.utils.SQLiteDatabase;

public class PPointOfSaleItemViewAdapter extends RecyclerView.Adapter<PPointOfSaleItemViewAdapter.ItemViewHolder> {

    Context context;
    ArrayList<PPointOfSaleItem> PPointOfSaleItems;
    SQLiteDatabase db;

    public interface OnItemClickListener {
        void onItemChanged(int position, PPointOfSaleItem newValue);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public PPointOfSaleItemViewAdapter(Context context, ArrayList<PPointOfSaleItem> PPointOfSaleItems, SQLiteDatabase db) {
        this.context = context;
        this.PPointOfSaleItems = PPointOfSaleItems;
        this.db = db;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PPointOfSaleItemViewAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_ppoint_of_sale_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ArrayList<TextView> textViews = holder.textViews;
        ArrayList<ImageView> imageViews = holder.imageViews;

        PPointOfSaleItem item = PPointOfSaleItems.get(position);
        int adapterPosition = holder.getAdapterPosition();

        /* Data Binding */
        textViews.get(0).setText(item.getCategory());
        textViews.get(1).setText(item.getTitle());
        textViews.get(2).setText(item.getLabelString());
        textViews.get(3).setText(item.getContentString());
        textViews.get(4).setText(String.valueOf(item.getMultiplier()));

        imageViews.get(0).setImageBitmap(Helper.loadImageFromFileSystem(item.getImage()));

        imageViews.get(1).setOnClickListener(e -> {
            if (item.getMultiplier() <= 1) return;

            item.setMultiplier(item.getMultiplier() - 1);

            textViews.get(4).setText(String.valueOf(item.getMultiplier()));

            mListener.onItemChanged(position, item);
            notifyItemChanged(position);
        });

        imageViews.get(2).setOnClickListener(e -> {
            if (item.getMultiplier() >= Integer.parseInt(item.getContent()[0])) return;

            item.setMultiplier(item.getMultiplier() + 1);

            textViews.get(4).setText(String.valueOf(item.getMultiplier()));

            mListener.onItemChanged(position, item);
            notifyItemChanged(position);
        });

        imageViews.get(3).setOnClickListener(e -> {
            Helper.openAlertYesNo(
                    context,
                    "POSIS: Warning",
                    String.format("Are you sure you want to remove %s?", item.getTitle()),
                    (dialog, which) -> {
                        Toast.makeText(context, String.format("Successfully Deleted: %s", item.getTitle()), Toast.LENGTH_SHORT).show();

                        PPointOfSaleItems.remove(position);

                        mListener.onItemChanged(position,null);
                        notifyItemRemoved(position);
                    },
                    null);

        });
    }

    @Override
    public int getItemCount() {
        return PPointOfSaleItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ArrayList<TextView> textViews = new ArrayList<>(5);
        ArrayList<ImageView> imageViews = new ArrayList<>(4);

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViews.add(itemView.findViewById(R.id.rvposi_category));
            textViews.add(itemView.findViewById(R.id.rvposi_title));
            textViews.add(itemView.findViewById(R.id.rvposi_label));
            textViews.add(itemView.findViewById(R.id.rvposi_content));
            textViews.add(itemView.findViewById(R.id.rvposi_multiplier));

            imageViews.add(itemView.findViewById(R.id.rvposi_image));
            imageViews.add(itemView.findViewById(R.id.rvposi_decrement));
            imageViews.add(itemView.findViewById(R.id.rvposi_increment));
            imageViews.add(itemView.findViewById(R.id.rvposi_action_button));
        }
    }
}
