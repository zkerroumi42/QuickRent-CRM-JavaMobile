package com.example.rentalapp.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalapp.Entities.Property;
import com.example.rentalapp.R;

import java.util.List;
public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private Context context;
    public PropertyAdapter(Context context, List<Property> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
    }
    private List<Property> propertyList;
    private OnPropertyActionListener onEditListener;
    private OnPropertyActionListener onArchiveListener;
    private OnPropertyActionListener onMoreListener;
    private OnPropertyActionListener onDeleteListener;
    public interface OnPropertyActionListener {
        void onAction(int position);
    }

    public PropertyAdapter(List<Property> propertyList,
                           OnPropertyActionListener onEditListener,
                           OnPropertyActionListener onArchiveListener
                           ,OnPropertyActionListener onMoreListener,OnPropertyActionListener onDeleteListener) {
        this.propertyList = propertyList;
        this.onEditListener = onEditListener;
        this.onArchiveListener = onArchiveListener;
        this.onMoreListener = onMoreListener;
        this.onDeleteListener = onDeleteListener;

    }

    public void updateList(List<Property> newList) {
        propertyList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_card, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property property = propertyList.get(position);
        holder.propertyName.setText(property.getName());
        holder.propertyDescription.setText(property.getDescription());

        holder.btnEdit.setOnClickListener(v -> onEditListener.onAction(position));
        holder.btnArchive.setOnClickListener(v -> onArchiveListener.onAction(position));
        holder.btnMore.setOnClickListener(v -> onMoreListener.onAction(position));
        holder.btnDelete.setOnClickListener(v -> onDeleteListener.onAction(position));
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName, propertyDescription;
        ImageButton btnEdit, btnArchive, btnMore,btnDelete;
        public PropertyViewHolder(@NonNull View view) {
            super(view);
            propertyName = view.findViewById(R.id.property_name);
            propertyDescription = view.findViewById(R.id.property_description);
            btnEdit = view.findViewById(R.id.btn_edit);
            btnArchive = view.findViewById(R.id.btn_archive);
            btnMore = view.findViewById(R.id.btn_more);
            btnDelete = view.findViewById(R.id.btn_delete);

        }
    }
}
