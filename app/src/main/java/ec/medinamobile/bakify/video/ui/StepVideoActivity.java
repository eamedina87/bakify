package ec.medinamobile.bakify.video.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
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

public class StepVideoActivity extends AppCompatActivity implements StepVideoView {

    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.step_video)
    SimpleExoPlayerView videoFrame;
    private Step mStep;
    private SimpleExoPlayer player;
    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step_video);
        ButterKnife.bind(this);
        getIntentData();
        setupActionBar();
        setupVideo();
    }

    private void setupActionBar() {
        if (mStep.getServerId()>0) {
            getSupportActionBar().setTitle(String.format(getString(R.string.step_video_title_number), mStep.getServerId()));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null && intent.hasExtra(Constants.INTENT_EXTRA_STEP)){
            mStep = intent.getParcelableExtra(Constants.INTENT_EXTRA_STEP);
            description.setText(mStep.getDescription());
        }
    }

    private void setupVideo() {
        String videoUrl = mStep.getVideoURL();
        if (videoUrl==null || TextUtils.isEmpty(videoUrl)){
            videoFrame.setVisibility(View.GONE);
            return;
        }

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        mPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        videoFrame.setPlayer(mPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"), (TransferListener<? super DataSource>) bandwidthMeter);

        //DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
        //        Util.getUserAgent(mContext, getString(R.string.app_name)));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mStep.getVideoURL()),
                dataSourceFactory, extractorsFactory, null, null);

        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null){
            player.release();
            player = null;
        };
    }

}
