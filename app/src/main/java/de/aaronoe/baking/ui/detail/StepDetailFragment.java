package de.aaronoe.baking.ui.detail;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Step;

import static android.view.View.GONE;

/**
 * Created by private on 8/5/17.
 *
 */

@EFragment(R.layout.step_detail_fragment)
public class StepDetailFragment extends Fragment  {

    @ViewById(R.id.detail_step_tv)
    TextView numberTv;
    @ViewById(R.id.detail_step_description_tv)
    TextView nameTv;
    @ViewById(R.id.detail_step_video_view)
    SimpleExoPlayerView mExoPlayerView;

    SimpleExoPlayer mExoPlayer;
    boolean isTablet;

    @InstanceState
    long videoTimestamp;

    Step mStep;

    @AfterViews
    void init() {
        numberTv.setText(getActivity().getString(R.string.step_number, mStep.getId()));
        nameTv.setText(mStep.getDescription());
        if (mStep.getVideoURL() == null || TextUtils.isEmpty(mStep.getVideoURL())) {
            mExoPlayerView.setVisibility(GONE);
        } else {
            initializePlayer(Uri.parse(mStep.getVideoURL()));
        }
    }

    public void setmStep(@FragmentArg Step step) {
        this.mStep = step;
    }

    public void setIsTablet(@FragmentArg boolean isTablet) {
        this.isTablet = isTablet;
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector();
            DefaultLoadControl defaultLoadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), defaultTrackSelector, defaultLoadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(),
                    null, null);
            mExoPlayer.prepare(mediaSource);
            // If this is the tablet then set to play here because setUserVisibileHint is not called
            mExoPlayer.seekTo(videoTimestamp);
            if (isTablet) mExoPlayer.setPlayWhenReady(true);
            mExoPlayerView.hideController();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer == null) return;
        videoTimestamp = mExoPlayer.getCurrentPosition();
    }

    private void releasePlayer() {
        if (mExoPlayer == null) return;
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mExoPlayer != null) {

            if (!isVisibleToUser) {
                videoTimestamp = mExoPlayer.getCurrentPosition();
            } else {
                mExoPlayer.seekTo(videoTimestamp);
            }

            mExoPlayer.setPlayWhenReady(isVisibleToUser);

        }
    }

}
