package com.education.bakingapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;

import java.util.ArrayList;

/**
 * Created by Sara on 7/17/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private ArrayList<Recipe> mLstRecipes;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(ArrayList<Recipe> lstRecipes, OnItemClickListener onItemClickListener) {

        mLstRecipes = lstRecipes;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.adapter_recipe, parent, false);
        RecipeHolder holder = new RecipeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {

        holder.tv_recipeName.setText(mLstRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mLstRecipes.size();
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_recipeName;

        public RecipeHolder(View itemView) {
            super(itemView);
            tv_recipeName = (TextView) itemView.findViewById(R.id.tv_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onItemClickListener.onItemClick(mLstRecipes.get(position));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }
}
