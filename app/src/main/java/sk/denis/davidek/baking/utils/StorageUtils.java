package sk.denis.davidek.baking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.data.Ingredient;

/**
 * Created by denis on 12.06.2017.
 */

public class StorageUtils {

    private SharedPreferences sharedPreferences;
    private Context context;

    public StorageUtils(Context context) {
        this.context = context;
    }

    public void storeIngredients(ArrayList<Ingredient> arrayList, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(context.getString(R.string.key_sharedpreferences_recipeIngredients), json);
        editor.apply();
    }

    public ArrayList<Ingredient> loadIngredients() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(context.getString(R.string.key_sharedpreferences_recipeIngredients), null);
        Type type = new TypeToken<ArrayList<Ingredient>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
