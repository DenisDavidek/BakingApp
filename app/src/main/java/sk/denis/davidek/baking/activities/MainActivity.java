package sk.denis.davidek.baking.activities;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.RecipeDetailActivity;
import sk.denis.davidek.baking.adapters.RecipeAdapter;
import sk.denis.davidek.baking.data.Ingredient;
import sk.denis.davidek.baking.data.Recipe;
import sk.denis.davidek.baking.data.RecipeStep;
import sk.denis.davidek.baking.databinding.ActivityMainBinding;
import sk.denis.davidek.baking.interfaces.OnItemClickListener;
import sk.denis.davidek.baking.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        OnItemClickListener {

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int RECIPES_GET_LOADER_ID = 78;

    private RecyclerView recipesRecyclerView;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<RecipeStep> recipeSteps = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView errorNoConnectionTextView;

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        recipesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipesRecyclerView.setLayoutManager(layoutManager);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorNoConnectionTextView = (TextView) findViewById(R.id.tv_error_no_internet_connection);

        if (NetworkUtils.checkInternetConnection(this)) {
            getRecipesData();
        } else {
            showErrorMessage();
        }

    }

    public void showErrorMessage() {

        recipesRecyclerView.setVisibility(View.INVISIBLE);
        errorNoConnectionTextView.setVisibility(View.VISIBLE);
    }

    public void showRecipesView() {
        recipesRecyclerView.setVisibility(View.VISIBLE);
        errorNoConnectionTextView.setVisibility(View.INVISIBLE);

    }


    private void getRecipesData() {

        Loader<ArrayList<Recipe>> getRecipesLoader = getSupportLoaderManager().getLoader(RECIPES_GET_LOADER_ID);

        if (getRecipesLoader == null) {
            getSupportLoaderManager().initLoader(RECIPES_GET_LOADER_ID, null, this);
        } else {
            getSupportLoaderManager().restartLoader(RECIPES_GET_LOADER_ID, null, this);
        }

    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            private ArrayList<Recipe> localRecipes = new ArrayList<>();
            private ArrayList<Ingredient> ingredients = new ArrayList<>();

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {

                URL recipesUrl = null;
                try {
                    recipesUrl = new URL(RECIPES_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                String returnedString = NetworkUtils.getResponseFromHttp(recipesUrl);

                if (!returnedString.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(returnedString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject childObject = jsonArray.getJSONObject(i);
                            String recipeName = childObject.getString("name");

                            ingredients = getIngredients(childObject);
                            recipeSteps = getRecipeSteps(childObject);

                            localRecipes.add(new Recipe(recipeName, ingredients, recipeSteps));


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                return localRecipes;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data != null) {
            showRecipesView();
            recipes = data;
            RecipeAdapter recipeAdapter = new RecipeAdapter(data, this, this);
            recipesRecyclerView.setAdapter(recipeAdapter);
        } else
            showErrorMessage();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
        //  Toast.makeText(getApplicationContext(),"loadreset called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recipes != null && !recipes.isEmpty())
            recipes.clear();

    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        ingredients = recipes.get(position).getIngredients();

        intent.putParcelableArrayListExtra(getString(R.string.key_intent_ingredients), ingredients);
        intent.putParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps), recipes.get(position).getRecipeSteps());
        intent.putExtra(getString(R.string.key_intent_recipeName), recipes.get(position).getName());

        startActivity(intent);
    }


    public ArrayList<Ingredient> getIngredients(JSONObject parentObject) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        JSONArray ingredientsArray;

        try {
            ingredientsArray = parentObject.getJSONArray("ingredients");

            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject childObject = ingredientsArray.getJSONObject(j);

                int quantity = childObject.getInt("quantity");
                String measure = childObject.getString("measure");
                String ingredient = childObject.getString("ingredient");

                ingredients.add(new Ingredient(quantity, measure, ingredient));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredients;
    }


    public ArrayList<RecipeStep> getRecipeSteps(JSONObject parentObject) {

        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
        JSONArray recipeStepsArray;

        try {
            recipeStepsArray = parentObject.getJSONArray("steps");

            for (int j = 0; j < recipeStepsArray.length(); j++) {
                JSONObject childObject = recipeStepsArray.getJSONObject(j);

                String shortDescription = childObject.optString("shortDescription", "");
                String description = childObject.optString("description", "");
                String videoUrl = childObject.optString("videoURL", "");
                String thumbnailURL = childObject.optString("thumbnailURL", "");

                recipeSteps.add(new RecipeStep(shortDescription, description, videoUrl, thumbnailURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeSteps;
    }
}
