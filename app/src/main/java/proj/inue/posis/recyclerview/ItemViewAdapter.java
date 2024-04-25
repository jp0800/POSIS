package proj.inue.posis.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import proj.inue.posis.R;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    Context context;
    List<Item> items;

    public ItemViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textViews.get(0).setText(items.get(position).getTitle());
        holder.textViews.get(1).setText(items.get(position).getCategory());
        holder.textViews.get(2).setText(items.get(position).getLabel());
        holder.textViews.get(3).setText(items.get(position).getContent());

        holder.imageViews.get(0).setImageResource(items.get(position).getImage());
        holder.imageViews.get(1).setImageResource(items.get(position).getEdit());
        holder.imageViews.get(2).setImageResource(items.get(position).getDelete());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
