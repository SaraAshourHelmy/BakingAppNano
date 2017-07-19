package com.education.bakingapp.views.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.models.Step;

/**
 * Created by Sara on 7/17/2017.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.DetailsHolder> {

    private OnItemClickListener onItemClickListener;
    private Step[] mSteps;
    private Context mContext;
    private int selectedPosition;

    public RecipeDetailsAdapter(Context context, Step[] steps,
                                OnItemClickListener onItemClickListener) {
        mSteps = steps;
        mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public DetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.adapter_recipe, parent, false);
        DetailsHolder holder = new DetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailsHolder holder, int position) {
        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rose));
        else
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        switch (position) {
            case 0:
                holder.tv_recipeName.setText(mContext.getString(R.string.ingredient));
                holder.tv_recipeName.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                break;
            default:
                holder.tv_recipeName.setText(mSteps[position - 1].getShortDescription());
                holder.tv_recipeName.setTextColor(ContextCompat.getColor(mContext, R.color.purple));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.length + 1;
    }

    class DetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_recipeName;
        View itemView;

        public DetailsHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_recipeName = (TextView) itemView.findViewById(R.id.tv_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectedPosition = getAdapterPosition();
            onItemClickListener.onItemClick(selectedPosition);
            notifyDataSetChanged();

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
