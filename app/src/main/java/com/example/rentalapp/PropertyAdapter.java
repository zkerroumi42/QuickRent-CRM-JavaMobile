package com.example.rentalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private Context context;
    public PropertyAdapter(Context context, List<PropertyItem> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
    }
    private List<PropertyItem> propertyList;
    private OnPropertyActionListener onEditListener;
    private OnPropertyActionListener onArchiveListener;
    private OnPropertyActionListener onMoreListener;

    public interface OnPropertyActionListener {
        void onAction(int position);
    }

    public PropertyAdapter(List<PropertyItem> propertyList,
                           OnPropertyActionListener onEditListener,
                           OnPropertyActionListener onArchiveListener,
                           OnPropertyActionListener onMoreListener) {
        this.propertyList = propertyList;
        this.onEditListener = onEditListener;
        this.onArchiveListener = onArchiveListener;
        this.onMoreListener = onMoreListener;
    }

    public void updateList(List<PropertyItem> newList) {
        propertyList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_layout, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        PropertyItem property = propertyList.get(position);
        holder.propertyName.setText(property.getName());
        holder.propertyDescription.setText(property.getDescription());

        holder.btnEdit.setOnClickListener(v -> onEditListener.onAction(position));
        holder.btnArchive.setOnClickListener(v -> onArchiveListener.onAction(position));
        //holder.btnMore.setOnClickListener(v -> onMoreListener.onAction(position));
        holder.itemView.setOnClickListener(v -> {
            PropertyDetailsDialogFragment dialogFragment =
                    PropertyDetailsDialogFragment.newInstance(property.getName(), property.getDescription());
        });


    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName, propertyDescription;
        ImageButton btnEdit, btnArchive, btnMore;

        public PropertyViewHolder(@NonNull View view) {
            super(view);
            propertyName = view.findViewById(R.id.property_name);
            propertyDescription = view.findViewById(R.id.property_description);
            btnEdit = view.findViewById(R.id.btn_edit);
            btnArchive = view.findViewById(R.id.btn_archive);
            btnMore = view.findViewById(R.id.btn_more);
        }
    }
}
