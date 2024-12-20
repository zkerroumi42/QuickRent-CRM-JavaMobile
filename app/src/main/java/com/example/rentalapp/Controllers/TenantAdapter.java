package com.example.rentalapp.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalapp.Entities.Tenant;
import com.example.rentalapp.R;

import java.util.List;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.TenantViewHolder> {

    private Context context;
    private List<Tenant> tenantList;
    private OnTenantActionListener onCallListener;
    private OnTenantActionListener onMsgmailListener;
    private OnTenantActionListener onMoreListener;
    private OnTenantActionListener onDeleteListener;

    public interface OnTenantActionListener {
        void onAction(int position);
    }

    public TenantAdapter(List<Tenant> tenantList,
                         OnTenantActionListener onCallListener,
                         OnTenantActionListener onMsgmailListener,
                         OnTenantActionListener onMoreListener,
                         OnTenantActionListener onDeleteListener) {
        this.tenantList = tenantList;
        this.onCallListener = onCallListener;
        this.onMsgmailListener = onMsgmailListener;
        this.onMoreListener = onMoreListener;
        this.onDeleteListener = onDeleteListener;
    }

    public void updateList(List<Tenant> newList) {
        tenantList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_card, parent, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        Tenant tenant = tenantList.get(position);
        holder.tenantName.setText(tenant.getName());
        holder.tenantPhone.setText(tenant.getPhone());
        holder.tenantEmail.setText(tenant.getEmail());

        holder.btnCall.setOnClickListener(v -> onCallListener.onAction(position));
        holder.btnMsgmail.setOnClickListener(v -> onMsgmailListener.onAction(position));
        holder.btnMore.setOnClickListener(v -> onMoreListener.onAction(position));
        holder.btnDelete.setOnClickListener(v -> onDeleteListener.onAction(position));
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    static class TenantViewHolder extends RecyclerView.ViewHolder {
        TextView tenantName, tenantPhone, tenantEmail;
        ImageButton btnCall, btnMsgmail, btnMore, btnDelete;

        public TenantViewHolder(@NonNull View view) {
            super(view);
            tenantName = view.findViewById(R.id.tenant_name);
            tenantPhone = view.findViewById(R.id.tenant_phone);
            tenantEmail = view.findViewById(R.id.tenant_email);
            btnCall = view.findViewById(R.id.btn_call);
            btnMsgmail = view.findViewById(R.id.btn_message);
            btnMore = view.findViewById(R.id.btn_more);
            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }
}
