package sk.denis.davidek.baking.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.fragments.RecipeDetailFragment;
import sk.denis.davidek.baking.fragments.RecipeStepInstructionFragment;
import sk.denis.davidek.baking.fragments.RecipeStepMediaFragment;
import sk.denis.davidek.baking.data.RecipeStep;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    private ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
    private String recipeName;

    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.key_intent_recipeSteps)) && intent.hasExtra(getString(R.string.key_intent_recipeName))) {
            recipeSteps = intent.getParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps));
            recipeName = intent.getStringExtra(getString(R.string.key_intent_recipeName));

            setTitle(recipeName);
        }

        if (findViewById(R.id.rl_recipe_step_instruction) != null) {
            isTwoPane = true;

            if (savedInstanceState == null) {

                RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                recipeStepInstructionFragment.setRecipeSteps(recipeSteps);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                        .commit();


                RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                recipeStepMediaFragment.setRecipeSteps(recipeSteps);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(int position) {

        if (isTwoPane) {
            RecipeStepInstructionFragment newRecipeStepInstructionFragment = new RecipeStepInstructionFragment();
            newRecipeStepInstructionFragment.setRecipeSteps(recipeSteps);
            newRecipeStepInstructionFragment.setCurrentRecipeStepsIndex(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_instruction_container, newRecipeStepInstructionFragment)
                    .commit();


            RecipeStepMediaFragment newRecipeStepMediaFragment = new RecipeStepMediaFragment();
            newRecipeStepMediaFragment.setRecipeSteps(recipeSteps);
            newRecipeStepMediaFragment.setCurrentRecipeStepsIndex(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_media_container, newRecipeStepMediaFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(getString(R.string.key_intent_recipeSteps), recipeSteps);
            intent.putExtra(getString(R.string.key_intent_recipeStepsIndex), position);
            startActivity(intent);
        }
    }
}
