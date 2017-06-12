package sk.denis.davidek.baking.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sk.denis.davidek.baking.R;
import sk.denis.davidek.baking.data.RecipeStep;
import sk.denis.davidek.baking.databinding.FragmentRecipeStepInstructionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepInstructionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepInstructionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepInstructionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<RecipeStep> recipeSteps;
    private int currentRecipeStepsIndex;

    private FragmentRecipeStepInstructionBinding mBinding;

    public RecipeStepInstructionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeStepInstructionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepInstructionFragment newInstance(String param1, String param2) {
        RecipeStepInstructionFragment fragment = new RecipeStepInstructionFragment();
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

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_instruction, container, false);
        View fragmentView = mBinding.getRoot();


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(getString(R.string.key_intent_recipeSteps)) &&
                    (savedInstanceState.containsKey(getString(R.string.key_intent_recipeStepsIndex)))) {
                recipeSteps = savedInstanceState.getParcelableArrayList(getString(R.string.key_intent_recipeSteps));
                currentRecipeStepsIndex = savedInstanceState.getInt(getString(R.string.key_intent_recipeStepsIndex));
            }

        }

        if (recipeSteps != null) {

            String recipeInstruction = recipeSteps.get(currentRecipeStepsIndex).getStepDescription();
            mBinding.tvRecipeStepInstruction.setText(recipeInstruction);
        }

        return fragmentView;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public void setCurrentRecipeStepsIndex(int currentRecipeStepsIndex) {
        this.currentRecipeStepsIndex = currentRecipeStepsIndex;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.key_intent_recipeSteps), recipeSteps);
        outState.putInt(getString(R.string.key_intent_recipeStepsIndex), currentRecipeStepsIndex);
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
    }
}
