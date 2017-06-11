package sk.denis.davidek.baking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.data.Ingredient;

/**
 * Created by denis on 09.06.2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientsAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View ingredientsView = layoutInflater.inflate(R.layout.ingredient_item, parent, shouldAttachToRoot);

        IngredientsViewHolder ingredientsViewHolder = new IngredientsViewHolder(ingredientsView);

        return ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {

        int quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();

        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.measureTextView.setText(measure);
        holder.ingredientTextView.setText(ingredient);

    }

    @Override
    public int getItemCount() {
        if (ingredients != null) return ingredients.size();
        return 0;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView quantityTextView;
        private TextView measureTextView;
        private TextView ingredientTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            quantityTextView = (TextView) itemView.findViewById(R.id.tv_quantity);
            measureTextView = (TextView) itemView.findViewById(R.id.tv_quantity_measure);
            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
