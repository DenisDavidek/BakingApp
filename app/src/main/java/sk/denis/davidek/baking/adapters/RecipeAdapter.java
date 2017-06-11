package sk.denis.davidek.baking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sk.denis.davidek.baking.interfaces.OnItemClickListener;
import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.data.Recipe;

/**
 * Created by denis on 09.06.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(ArrayList<Recipe> recipes, Context context, OnItemClickListener onItemClickListener) {
        this.recipes = recipes;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View recipeView = inflater.inflate(R.layout.recipes_item, parent, shouldAttachToRoot);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(recipeView);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String recipeName = recipes.get(position).getName();
        holder.recipeNameTextView.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (recipes != null && !recipes.isEmpty()) return recipes.size();
        return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView recipeNameTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onItemClickListener.onClick(position);
        }
    }
}
