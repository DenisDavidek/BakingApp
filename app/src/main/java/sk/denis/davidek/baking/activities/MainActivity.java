package sk.denis.davidek.baking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.adapters.RecipeAdapter;
import sk.denis.davidek.baking.data.Ingredient;
import sk.denis.davidek.baking.data.Recipe;
import sk.denis.davidek.baking.data.RecipeStep;
import sk.denis.davidek.baking.databinding.ActivityMainBinding;
import sk.denis.davidek.baking.interfaces.OnItemClickListener;
import sk.denis.davidek.baking.utils.LayoutUtils;
import sk.denis.davidek.baking.utils.NetworkUtils;
import sk.denis.davidek.baking.utils.StorageUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        OnItemClickListener {

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int RECIPES_GET_LOADER_ID = 23;

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<RecipeStep> recipeSteps = new ArrayList<>();

    private ActivityMainBinding mainBinding;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.rvRecipes.setHasFixedSize(true);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, LayoutUtils.calculateNoOfColumns(this));
            mainBinding.rvRecipes.setLayoutManager(layoutManager);

        } else {

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mainBinding.rvRecipes.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mainBinding.rvRecipes.getContext(),
                    layoutManager.getOrientation());
            mainBinding.rvRecipes.addItemDecoration(dividerItemDecoration);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (NetworkUtils.checkInternetConnection(this)) {
            getRecipesData();
        } else {
            showErrorMessage();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void showErrorMessage() {

        mainBinding.rvRecipes.setVisibility(View.INVISIBLE);
        mainBinding.tvErrorNoInternetConnection.setVisibility(View.VISIBLE);
    }

    public void showRecipesView() {
        mainBinding.rvRecipes.setVisibility(View.VISIBLE);
        mainBinding.tvErrorNoInternetConnection.setVisibility(View.INVISIBLE);

    }

    Loader<ArrayList<Recipe>> getRecipesLoader = getSupportLoaderManager().getLoader(RECIPES_GET_LOADER_ID);

    private void getRecipesData() {


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
                mainBinding.progressBar.setVisibility(View.VISIBLE);
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
        mainBinding.progressBar.setVisibility(View.INVISIBLE);
        if (data != null) {
            showRecipesView();
            recipes = data;
            RecipeAdapter recipeAdapter = new RecipeAdapter(data, this, this);
            mainBinding.rvRecipes.setAdapter(recipeAdapter);
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
    protected void onPause() {
        super.onPause();

    }

    private SharedPreferences preferences;

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        ingredients = recipes.get(position).getIngredients();

        intent.putParcelableArrayListExtra(getString(R.string.key_intent_ingredients), ingredients);
        intent.putParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps), recipes.get(position).getRecipeSteps());
        intent.putExtra(getString(R.string.key_intent_recipeName), recipes.get(position).getName());

        StorageUtils storageUtils = new StorageUtils(getApplicationContext());
        storageUtils.storeIngredients(ingredients, this);
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
