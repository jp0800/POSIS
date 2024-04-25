package proj.inue.posis.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import proj.inue.posis.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
//    public ArrayList<String> string = new ArrayList<>(4);

    ArrayList<TextView> textViews = new ArrayList<>(4);
    ArrayList<ImageView> imageViews = new ArrayList<>(3);

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        textViews.add(itemView.findViewById(R.id.riv_title));
        textViews.add(itemView.findViewById(R.id.riv_category));
        textViews.add(itemView.findViewById(R.id.riv_label));
        textViews.add(itemView.findViewById(R.id.riv_content));

        imageViews.add(itemView.findViewById(R.id.riv_image));
        imageViews.add(itemView.findViewById(R.id.riv_edit));
        imageViews.add(itemView.findViewById(R.id.riv_delete));

//        System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>> %s",string.indexOf("Something"));

    }
}
