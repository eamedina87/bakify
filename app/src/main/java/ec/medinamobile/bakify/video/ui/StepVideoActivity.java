package ec.medinamobile.bakify.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
    @BindView(R.id.step_video_frame)
    FrameLayout videoFrame;
    private Step mStep;

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


    }
}
