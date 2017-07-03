package ec.medinamobile.bakify.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class StepVideoFragment extends Fragment {


    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.step_video_frame)
    FrameLayout videoFrame;
    private Step mStep;

    public StepVideoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_video_activity, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setupActionBar();
    }

    private void setupActionBar() {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setActionbarTitle(){
        if (mStep.getServerId()>0) {
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


    }
}
