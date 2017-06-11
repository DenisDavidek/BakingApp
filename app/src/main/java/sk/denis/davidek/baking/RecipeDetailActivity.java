package sk.denis.davidek.baking;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;

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
            isTwoPane= true;

            if (savedInstanceState == null) {

                RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                recipeStepInstructionFragment.setRecipeSteps(recipeSteps);
              //  recipeStepInstructionFragment.setRecipeInstruction(recipeSteps.get(0).getStepDescription());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                        .commit();




                RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
              recipeStepMediaFragment.setRecipeSteps(recipeSteps);
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                        .commit();
            }
        }
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(int position) {

        if (isTwoPane) {
            RecipeStepInstructionFragment recipeStepInstructionFragment1 = new RecipeStepInstructionFragment();
            recipeStepInstructionFragment1.setRecipeSteps(recipeSteps);
           // recipeStepInstructionFragment1.setRecipeInstruction(recipeSteps.get(1).getStepDescription());
            recipeStepInstructionFragment1.setCurrentRecipeStepsIndex(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_instruction_container, recipeStepInstructionFragment1)
                    .commit();


            RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
            recipeStepMediaFragment.setRecipeSteps(recipeSteps);
            recipeStepMediaFragment.setCurrentRecipeStepsIndex(position);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction()
                    .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                    .commit();

        } else {
         //   Toast.makeText(this, " positionBLABLABLA " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(getString(R.string.key_intent_recipeSteps), recipeSteps);
            intent.putExtra(getString(R.string.key_intent_recipeStepsIndex), position);
            startActivity(intent);
        }
    }
}
