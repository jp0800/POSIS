package proj.inue.posis.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proj.inue.posis.Home;
import proj.inue.posis.PAddProductActivity;
import proj.inue.posis.R;
import proj.inue.posis.utils.MockDatabase;

public class PViewInventoryItemViewAdapter extends RecyclerView.Adapter<PViewInventoryItemViewAdapter.ItemViewHolder> {

    Context context;
    ArrayList<PViewInventoryItem> PViewInventoryItems;

    public PViewInventoryItemViewAdapter(Context context, ArrayList<PViewInventoryItem> PViewInventoryItems) {
        this.context = context;
        this.PViewInventoryItems = PViewInventoryItems;
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

        holder.imageViews.get(0).setImageResource(PViewInventoryItems.get(position).getImage());
        holder.imageViews.get(1).setImageResource(PViewInventoryItems.get(position).getEdit());
        holder.imageViews.get(2).setImageResource(PViewInventoryItems.get(position).getDelete());

        holder.imageViews.get(1).setOnClickListener(e->{
            Intent intent = new Intent(context, PAddProductActivity.class);
            intent.putExtra("itemDataJson", PViewInventoryItems.get(position).toJson());
            intent.putExtra("activityTitle", "Edit Product");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });

        holder.imageViews.get(2).setOnClickListener(e -> {
            int adapterPosition = holder.getAdapterPosition();
            PViewInventoryItems.remove(adapterPosition);
            MockDatabase.inventoryList = PViewInventoryItems;

            notifyItemRemoved(adapterPosition);
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
