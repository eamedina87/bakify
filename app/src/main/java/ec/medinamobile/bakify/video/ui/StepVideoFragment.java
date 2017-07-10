package ec.medinamobile.bakify.video.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.utils.Constants;

/**
 * Created by Erick on 3/7/17.
 */

public class StepVideoFragment extends Fragment {


    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.step_video)
    SimpleExoPlayerView videoFrame;
    private Step mStep;
    private SimpleExoPlayer mPlayer;
    private Context mContext;

    public StepVideoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_video, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments()!=null){
            mStep = getArguments().getParcelable(Constants.BUNDLE_STEP);
            if (mStep!=null){
                setStep(mStep);
            }
        }

    }

    private void setActionbarTitle(){
        if (mStep.getServerId()>0) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            getActivity().getActionBar().setTitle(String.format(getString(R.string.step_video_title_number), mStep.getServerId()));
        }
    }

    public void setStep(Step step) {
        mStep = step;
        description.setText(mStep.getDescription());
        setActionbarTitle();
        setupVideo();
    }

    private void setupVideo() {
        String videoUrl = mStep.getVideoURL();
        if (videoUrl==null || TextUtils.isEmpty(videoUrl)){
            videoFrame.setVisibility(View.GONE);
            return;
        } else {
            videoFrame.setVisibility(View.VISIBLE);
        }

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        videoFrame.setPlayer(mPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "yourApplicationName"), (TransferListener<? super DataSource>) bandwidthMeter);

        //DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
        //        Util.getUserAgent(mContext, getString(R.string.app_name)));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mStep.getVideoURL()),
                dataSourceFactory, extractorsFactory, null, null);

        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);

    }
}
