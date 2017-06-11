package sk.denis.davidek.baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import sk.denis.davidek.baking.data.RecipeStep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepMediaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepMediaFragment extends Fragment implements ExoPlayer.EventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;

    private TextView erroNoUrlTextView;

    private ArrayList<RecipeStep> recipeSteps;
    private int currentRecipeStepsIndex;

    public RecipeStepMediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeStepMediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepMediaFragment newInstance(String param1, String param2) {
        RecipeStepMediaFragment fragment = new RecipeStepMediaFragment();
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

    public long test;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_step_media, container, false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(getString(R.string.key_intent_recipeSteps)) &&
                    (savedInstanceState.containsKey(getString(R.string.key_intent_recipeStepsIndex)))) {
                recipeSteps = savedInstanceState.getParcelableArrayList(getString(R.string.key_intent_recipeSteps));
               currentRecipeStepsIndex = savedInstanceState.getInt(getString(R.string.key_intent_recipeStepsIndex));
            test = savedInstanceState.getLong("test");
            }

        }
        exoPlayerView = (SimpleExoPlayerView) fragmentView.findViewById(R.id.exo_playerView);
        erroNoUrlTextView = (TextView) fragmentView.findViewById(R.id.tv_error_no_url);

      //  if (!mediaUrl.isEmpty()) {
        if (recipeSteps != null)
            if (!recipeSteps.get(currentRecipeStepsIndex).getVideoUrl().isEmpty())
            initializePlayer();
            else{
          //      Toast.makeText(getContext(),"empty url", Toast.LENGTH_SHORT).show();
        showErrorMessage();
           }


        return fragmentView;
    }

public void showErrorMessage() {
    erroNoUrlTextView.setVisibility(View.VISIBLE);
    exoPlayerView.setVisibility(View.GONE);
}

    private void initializePlayer(){
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "Baking");
          //  MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"),new DefaultDataSourceFactory(getContext(),userAgent),null,null);

           /* MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"),
                    new DefaultDataSourceFactory(getContext(),userAgent), new DefaultExtractorsFactory(), null, null);*/

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(recipeSteps.get(currentRecipeStepsIndex).getVideoUrl()),
                    new DefaultDataSourceFactory(getContext(),userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            if (test >0)
                exoPlayer.seekTo(test);
            exoPlayer.setPlayWhenReady(true);

        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps){
        this.recipeSteps = recipeSteps;
    }

    public void setCurrentRecipeStepsIndex (int currentRecipeStepsIndex) {
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
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
      //  Toast.makeText(getContext(),"ondestroyview called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
       // on destroyview?
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(getString(R.string.key_intent_recipeSteps),recipeSteps);
        outState.putInt(getString(R.string.key_intent_recipeStepsIndex),currentRecipeStepsIndex);
        outState.putLong("test", exoPlayer.getCurrentPosition());
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

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
