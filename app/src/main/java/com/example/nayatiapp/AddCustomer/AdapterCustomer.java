package com.example.nayatiapp.AddCustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.nayatiapp.R;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyViewHolder> implements Filterable {

    List<Customer> customer, customerFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilterCustomer filter;

    public AdapterCustomer(List<Customer> pets, Context context, AdapterCustomer.RecyclerViewClickListener listener) {
        this.customer = pets;
        this.customerFilter = pets;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public AdapterCustomer.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final AdapterCustomer.MyViewHolder holder, int position) {

        holder.mName.setText(customer.get(position).getName());
        holder.mType.setText(customer.get(position).getUser() + " / "
                + customer.get(position).getCity());
        holder.mDate.setText(customer.get(position).getTanggal());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        Glide.with(context)
                .load(customer.get(position).getPicture())
                .apply(requestOptions)
                .into(holder.mPicture);

        final Boolean love = customer.get(position).getLove();

        if (love){
            holder.mLove.setImageResource(R.drawable.likeon);
        } else {
            holder.mLove.setImageResource(R.drawable.likeof);
        }

    }

    @Override
    public int getItemCount() {

        return customer.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterCustomer((ArrayList<Customer>) customerFilter,this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterCustomer.RecyclerViewClickListener mListener;
        private CircleImageView mPicture;
        private ImageView mLove;
        private TextView mName, mType, mDate;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, AdapterCustomer.RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mName = itemView.findViewById(R.id.name);
            mType = itemView.findViewById(R.id.type);
            mLove = itemView.findViewById(R.id.love);
            mDate = itemView.findViewById(R.id.date);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
            mLove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                case R.id.love:
                    mListener.onLoveClick(mLove, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
        void onLoveClick(View view, int position);
    }
}
