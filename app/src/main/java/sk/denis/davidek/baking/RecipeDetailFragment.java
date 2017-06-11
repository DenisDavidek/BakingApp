package sk.denis.davidek.baking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import sk.denis.davidek.baking.adapters.IngredientsAdapter;
import sk.denis.davidek.baking.adapters.RecipeStepAdapter;
import sk.denis.davidek.baking.data.Ingredient;
import sk.denis.davidek.baking.data.RecipeStep;
import sk.denis.davidek.baking.interfaces.OnItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements OnItemClickListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView recipeStepsRecyclerView;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<RecipeStep> recipeSteps = new ArrayList<>();

    private OnItemClickListener clickListener;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ingredientsRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_recipe_ingredients);
        ingredientsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(getString(R.string.key_intent_ingredients))) {
            ingredients = intent.getParcelableArrayListExtra(getString(R.string.key_intent_ingredients));
        }

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients, getContext());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        recipeStepsRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_recipe_steps);
        recipeStepsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recipeStepsRecyclerView.setLayoutManager(layoutManager1);

        if (intent.hasExtra(getString(R.string.key_intent_recipeSteps))) {
            recipeSteps = intent.getParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps));
        }

        RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(recipeSteps,getContext(),this);
        recipeStepsRecyclerView.setAdapter(recipeStepAdapter);



        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       try {
           mListener = (OnFragmentInteractionListener) context;
       } catch (ClassCastException e) {
           throw  new ClassCastException(context.toString() + " must implement OnItemClickListener");
       }

      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } *//*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onClick(int position) {
     //   Toast.makeText(getContext()," position " + position,Toast.LENGTH_SHORT).show();
        mListener.onClick(position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onClick(int position);
    }
}
