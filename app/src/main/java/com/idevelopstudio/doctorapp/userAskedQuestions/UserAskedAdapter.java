package com.idevelopstudio.doctorapp.userAskedQuestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.ListItemUserAskedQueryBinding;
import com.idevelopstudio.doctorapp.models.UserQuery;

class UserAskedAdapter extends PagedListAdapter<UserQuery, UserAskedAdapter.UserQueryViewHolder> {

    private Context context;

    protected UserAskedAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public UserQueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemUserAskedQueryBinding binding = ListItemUserAskedQueryBinding.inflate(layoutInflater, parent, false);
        return new UserQueryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserQueryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static DiffUtil.ItemCallback<UserQuery> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserQuery>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserQuery oldItem, @NonNull UserQuery newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserQuery oldItem, @NonNull UserQuery newItem) {
            return oldItem.getQueryID() == newItem.getQueryID();
        }
    };

    class UserQueryViewHolder extends RecyclerView.ViewHolder {
        private ListItemUserAskedQueryBinding binding;
        public UserQueryViewHolder(ListItemUserAskedQueryBinding binding) {
            super(binding.getRoot());
            this.binding =  binding;
        }

        void bind(UserQuery item){
            binding.setUserQuery(item);
        }

    }


}
