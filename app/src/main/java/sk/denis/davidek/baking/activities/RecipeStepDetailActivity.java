package sk.denis.davidek.baking.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.fragments.RecipeStepInstructionFragment;
import sk.denis.davidek.baking.fragments.RecipeStepMediaFragment;
import sk.denis.davidek.baking.data.RecipeStep;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private ArrayList<RecipeStep> recipeSteps;
    private int currentRecipeStepsIndex;

    private FloatingActionButton floatingActionButtonBackwards;
    private FloatingActionButton floatingActionButtonForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        floatingActionButtonBackwards = (FloatingActionButton) findViewById(R.id.fab_backwards);
        floatingActionButtonForward = (FloatingActionButton) findViewById(R.id.fab_forward);

        Intent intent = getIntent();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {


            floatingActionButtonBackwards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recipeSteps != null) {
                        if (currentRecipeStepsIndex > 0) {
                            currentRecipeStepsIndex--;

                        } else {
                            currentRecipeStepsIndex = recipeSteps.size() - 1;
                        }
                        // Toast.makeText(getApplicationContext(),"Backwards currentRecipeStepsIndex :" + currentRecipeStepsIndex,Toast.LENGTH_SHORT).show();

                        RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                        recipeStepInstructionFragment.setRecipeSteps(recipeSteps);
                        recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                                .commit();


                        RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                        recipeStepMediaFragment.setRecipeSteps(recipeSteps);
                        recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                                .commit();

                    }
                }
            });


            floatingActionButtonForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recipeSteps != null) {

                        if (currentRecipeStepsIndex < recipeSteps.size() - 1) {
                            currentRecipeStepsIndex++;
                        } else {
                            currentRecipeStepsIndex = 0;
                        }

                        RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                        recipeStepInstructionFragment.setRecipeSteps(recipeSteps);
                        recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                                .commit();


                        RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                        recipeStepMediaFragment.setRecipeSteps(recipeSteps);
                        recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                                .commit();

                    }

                }
            });
        }

        if (intent.hasExtra(getString(R.string.key_intent_recipeSteps)) &&
                (intent.hasExtra(getString(R.string.key_intent_recipeStepsIndex)))) {
            recipeSteps = intent.getParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps));
            currentRecipeStepsIndex = intent.getIntExtra(getString(R.string.key_intent_recipeStepsIndex), 0);
        }

        if (savedInstanceState == null) {

            RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
            recipeStepInstructionFragment.setRecipeSteps(recipeSteps);
            recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                    .commit();


            RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
            recipeStepMediaFragment.setRecipeSteps(recipeSteps);
            recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_step_media_container, recipeStepMediaFragment)
                    .commit();
        }
    }

}
