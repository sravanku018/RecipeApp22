package com.example.subramanyam.recipeapp.userinterface;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;
import com.example.subramanyam.recipeapp.data.StepsItems;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.subramanyam.recipeapp.userinterface.RecipeDescriptionActivity.SELECTED_RECIPES;
import static com.example.subramanyam.recipeapp.userinterface.RecipeItemActivity.SELECTED_INDEX;
import static com.example.subramanyam.recipeapp.userinterface.RecipeItemActivity.SELECTED_STEPS;


public class StepDetailsFragmnet extends Fragment {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    ArrayList<StepsItems> steps = new ArrayList<>();
    private int selectedIndex;

    ArrayList<RecipeItem> recipe;
    String recipeName;
    private long currentPlayerPosition = 0;
    public final static String PLAYER_POSITION = "currentpostion";

    private int resumeWindow;
    private long resumePosition;
    Uri mediaUri;


    public boolean isPlayerPlaying() {
        return isPlayerPlaying;
    }

    public void setPlayerPlaying(boolean playerPlaying) {
        isPlayerPlaying = playerPlaying;
    }



    private boolean isPlayerPlaying = true;
    private boolean isVideoPlaying;

    private static final String PLAYBACK_POSITION = "playback_position";

    private static final String VIDEO_PLAYSTATE= "video_playstate";



    public StepDetailsFragmnet() {

    }

    private ListItemClickListener itemClickListener;


    public interface ListItemClickListener {

        void onListItemClick(List<StepsItems> allSteps, int Index, String recipeName);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView;


        bandwidthMeter = new DefaultBandwidthMeter();


        itemClickListener = (RecipeDescriptionActivity) getActivity();


        recipe = new ArrayList<>();


        if (savedInstanceState != null) {


            steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);

            recipeName = savedInstanceState.getString("Title");
            currentPlayerPosition = savedInstanceState.getLong("PLAYER_POSITION", C.TIME_UNSET);
            isVideoPlaying=savedInstanceState.getBoolean(VIDEO_PLAYSTATE,true);


        } else {


            steps = getArguments().getParcelableArrayList(SELECTED_STEPS);

            if (steps != null) {

                steps = getArguments().getParcelableArrayList(SELECTED_STEPS);

                selectedIndex = getArguments().getInt(SELECTED_INDEX);

                recipeName = getArguments().getString("Title");

            } else {

                recipe = getArguments().getParcelableArrayList(SELECTED_RECIPES);

                //casting List to ArrayList


                steps = (ArrayList<StepsItems>) recipe.get(0).getSteps();

                selectedIndex = 0;

            }


        }
        View view = inflater.inflate(R.layout.fragment_step_details_fragmnet, container, false);


        textView = view.findViewById(R.id.recipe_step_detail_text);

        textView.setText(steps.get(selectedIndex).getDescription());

        textView.setVisibility(View.VISIBLE);


        simpleExoPlayerView = view.findViewById(R.id.playerView);

        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);


        String videoURL = steps.get(selectedIndex).getVideoURL();


        if (view.findViewWithTag("sw600dp-port-recipe_step_detail") != null) {

            recipeName = ((RecipeDescriptionActivity) getActivity()).recipeName;

            ((RecipeDescriptionActivity) getActivity()).getSupportActionBar().setTitle(recipeName);

        }


        String imageUrl = steps.get(selectedIndex).getThumbnailURL();

        if (!imageUrl.equals("")) {

            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();

            ImageView thumbImage = view.findViewById(R.id.thumbImage);

            Picasso.with(getContext()).load(builtUri).into(thumbImage);

        }


        if (!videoURL.isEmpty()) {


            mediaUri = Uri.parse(steps.get(selectedIndex).getVideoURL());
            initializePlayer(mediaUri);


            if (view.findViewWithTag("sw600dp-land-recipe_step_detail") != null) {

                getActivity().findViewById(R.id.fragment_container2).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));

                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            } else if (isInLandscapeMode(getContext())) {


                textView.setVisibility(View.GONE);


            }

        } else {


            simpleExoPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_foreground));

            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));

        }


        Button mPrevStep = (Button) view.findViewById(R.id.previousStep);

        Button mNextstep = (Button) view.findViewById(R.id.nextStep);


        mPrevStep.setOnClickListener(view12 -> {

            if (steps.get(selectedIndex).getId() > 0) {

                if (player != null) {

                    player.stop();


                }

                itemClickListener.onListItemClick(steps, steps.get(selectedIndex).getId() - 1, recipeName);


            } else {

                Toast.makeText(getActivity(), "You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();


            }

        });


        mNextstep.setOnClickListener(view1 -> {


            int lastIndex = steps.size() - 1;

            if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {

                if (player != null) {

                    player.stop();


                }


                itemClickListener.onListItemClick(steps, steps.get(selectedIndex).getId() + 1, recipeName);

            } else {


                Toast.makeText(getContext(), "You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();


            }

        });


        return view;
    }

    private void initializePlayer(Uri mediaUri) {
        setPlayerPlaying(isVideoPlaying);
        if (mediaUri != null && simpleExoPlayerView != null && player == null )
        {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);


            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            simpleExoPlayerView.setPlayer(player);


            DefaultBandwidthMeter bandwidthMeasure = new DefaultBandwidthMeter();

            // Produces DataSource instances through which media data is loaded.

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),

                    Util.getUserAgent(getActivity(), "RecipeApp"), bandwidthMeasure);

            // Produces Extractor instances for parsing the media data.

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, dataSourceFactory, extractorsFactory, null, null);

            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;

            if (currentPlayerPosition != 0) {
                player.seekTo(currentPlayerPosition);
            }

            player.prepare(mediaSource, false, false);
           player.setPlayWhenReady(isPlayerPlaying());
        }




       goToForeground();
    }


    @Override

    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putInt(SELECTED_INDEX, selectedIndex);
        currentState.putParcelableArrayList(SELECTED_STEPS, steps);
        currentState.putString("Title", recipeName);


        currentState.putLong("PLAYER_POSITION", currentPlayerPosition);
        currentState.putBoolean(VIDEO_PLAYSTATE,isPlayerPlaying());



        super.onSaveInstanceState(currentState);

    }


    public boolean isInLandscapeMode(Context context) {

        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);

    }



    public void onResume() {

        super.onResume();

            {

                initializePlayer(mediaUri);
            }


    }

    @Override
    public void onPause() {

        super.onPause();
        backgroundState();
        releasePlayer();

    }


    private void releasePlayer() {
        if (player != null) {

           currentPlayerPosition=player.getCurrentPosition();

           // updateResumePosition();
            player.stop();
            player.release();



        }
        player = null;
    }

     public void backgroundState()
     {
         isPlayerPlaying = player.getPlayWhenReady();

         player.setPlayWhenReady(false);
     }
    public void goToForeground() {

        if (player != null) {

            player.setPlayWhenReady(isPlayerPlaying);

        }

    }



}
