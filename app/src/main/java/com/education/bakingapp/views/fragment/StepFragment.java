package com.education.bakingapp.views.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Step;
import com.education.bakingapp.utilities.NetWorkUtility;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    private static Step step;
    private int position;
    private SimpleExoPlayerView playerView;
    private ImageView imgV_step;
    private TextView tv_step;
    private SimpleExoPlayer player;
    boolean isLand, isTablet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLand = getResources().getBoolean(R.bool.isLand);
        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    private void receiveData() {

        if (!isTablet) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(DetailsRecipeFragment.EXTRA_STEP)) {
                step = intent.getParcelableExtra(DetailsRecipeFragment.EXTRA_STEP);
                position = intent.getIntExtra(DetailsRecipeFragment.EXTRA_POSITION, 0);
            }
        } else {
            Bundle bundle = getArguments();
            if (bundle != null && bundle.containsKey(DetailsRecipeFragment.EXTRA_STEP)) {
                step = bundle.getParcelable(DetailsRecipeFragment.EXTRA_STEP);
            }
        }
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView = (SimpleExoPlayerView) view.findViewById(R.id.player);
        imgV_step = (ImageView) view.findViewById(R.id.imgV_step);
        tv_step = (TextView) view.findViewById(R.id.tv_step);

        receiveData();
        setData();
    }

    private void setData() {
        if (NetWorkUtility.checkInternetConnection(getContext())) {

            if (step.getVideoURL().length() > 0) {
                setPlayer();
            } else if (step.getThumbnailURL().length() > 0) {

                setImage();
            }
            if (step.getDescription() != null && step.getDescription().length() > 0) {
                tv_step.setText(step.getDescription());
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void setPlayer() {
        if (isLand && !isTablet)
            tv_step.setVisibility(View.GONE);
        playerView.setVisibility(View.VISIBLE);
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());

            playerView.setPlayer(player);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                    new DefaultDataSourceFactory(getContext(), "BakingApp"),
                    new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void setImage() {
        imgV_step.setVisibility(View.VISIBLE);
        Picasso.with(getContext()).load(step.getThumbnailURL())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_error).into(imgV_step);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            if (step.getVideoURL().length() > 0)
                tv_step.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            tv_step.setVisibility(View.VISIBLE);
        }
    }
}
