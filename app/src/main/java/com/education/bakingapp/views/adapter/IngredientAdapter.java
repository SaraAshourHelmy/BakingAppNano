package com.education.bakingapp.views.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Ingredient;

import java.util.ArrayList;

/**
 * Created by Sara on 7/17/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private ArrayList<Ingredient> mIngredients;
    private Context mContext;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        mContext = context;
    }

    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.adapter_recipe, parent, false);
        IngredientHolder holder = new IngredientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {

        Ingredient ingredient = mIngredients.get(position);
        String txt = ingredient.getQuantity() + " " + ingredient.getMeasure() + " of "
                + ingredient.getIngredient();
        holder.tv_recipeName.setText(txt);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        TextView tv_recipeName;

        public IngredientHolder(View itemView) {
            super(itemView);
            tv_recipeName = (TextView) itemView.findViewById(R.id.tv_recipe);
            itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rose));
        }
    }
}
