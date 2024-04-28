package proj.inue.posis.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import proj.inue.posis.R;

public class PAddCategoryItemViewAdapter extends RecyclerView.Adapter<PAddCategoryItemViewAdapter.ItemViewHolder> {

    Context context;
    List<PAddCategoryItem> PAddCategoryItems;

    public PAddCategoryItemViewAdapter(Context context, List<PAddCategoryItem> PAddCategoryItems) {
        this.context = context;
        this.PAddCategoryItems = PAddCategoryItems;
    }

    @NonNull
    @Override
    public PAddCategoryItemViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PAddCategoryItemViewAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_padd_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PAddCategoryItemViewAdapter.ItemViewHolder holder, int position) {
        holder.categoryName.setText(PAddCategoryItems.get(position).categoryName);

        holder.imageViews.get(0).setImageResource(PAddCategoryItems.get(position).getEdit());
        holder.imageViews.get(1).setImageResource(PAddCategoryItems.get(position).getDelete());

        holder.imageViews.get(1).setOnClickListener(e -> {
            int adapterPosition = holder.getAdapterPosition();
            PAddCategoryItems.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
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
