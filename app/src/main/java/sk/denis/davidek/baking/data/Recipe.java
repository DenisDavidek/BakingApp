package sk.denis.davidek.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by denis on 08.06.2017.
 */

public class Recipe implements Parcelable {

    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> recipeSteps;


    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps) {
        this.name = name;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        recipeSteps = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(recipeSteps);
    }
}
