package sk.denis.davidek.baking.adapters;

/**
 * Created by denis on 09.06.2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sk.denis.davidek.baking.interfaces.OnItemClickListener;
import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.data.RecipeStep;

/**
 * Created by denis on 09.06.2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private ArrayList<RecipeStep> recipeSteps;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecipeStepAdapter(ArrayList<RecipeStep> recipeSteps, Context context, OnItemClickListener clickListener) {
        this.recipeSteps = recipeSteps;
        this.context = context;
        this.onItemClickListener = clickListener;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View ingredientsView = layoutInflater.inflate(R.layout.recipe_step_item, parent, shouldAttachToRoot);

        RecipeStepViewHolder recipeStepViewHolder = new RecipeStepViewHolder(ingredientsView);

        return recipeStepViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {


        String shortStepDescription = recipeSteps.get(position).getShortStepDescription();
        holder.recipeStepTextView.setText(shortStepDescription);


    }

    @Override
    public int getItemCount() {
        if (recipeSteps != null) return recipeSteps.size();
        return 0;
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView recipeStepTextView;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            recipeStepTextView = (TextView) itemView.findViewById(R.id.tv_recipestep_shortdescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onItemClickListener.onClick(position);
        }
    }
}

