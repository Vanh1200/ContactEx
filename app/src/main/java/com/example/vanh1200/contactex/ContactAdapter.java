package com.example.vanh1200.contactex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private OnClickListener mListener;
    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contact, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mContacts.get(i));
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextName;
        private TextView mTextPhone;
        private ImageView mImageCall;
        private ImageView mImageStar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            mTextName = itemView.findViewById(R.id.text_name);
            mTextPhone = itemView.findViewById(R.id.text_phone);
            mImageStar = itemView.findViewById(R.id.image_star);
            mImageCall = itemView.findViewById(R.id.image_call);
        }

        public void bindData(Contact contact) {
            if (!contact.getFavorite()) {
                mImageStar.setImageResource(R.drawable.gray_star);
            } else {
                mImageStar.setImageResource(R.drawable.yellow_star);
            }
            mTextName.setText(contact.getName());
            mTextPhone.setText(contact.getPhone());
            mImageCall.setOnClickListener(this);
            itemView.setOnClickListener(this);
            mImageStar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_call:
                    mListener.onClickCall(getAdapterPosition());
                    break;
                case R.id.image_star:
                    mListener.onClickFavorite(getAdapterPosition());
                    break;
                default:
                    mListener.onClickItem(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnClickListener {
        void onClickCall(int position);

        void onClickItem(int position);

        void onClickFavorite(int position);
    }
}
