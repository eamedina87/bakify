package ec.medinamobile.bakify.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Supertel on 3/7/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private Step[] mSteps;
    private OnStepItemClickListener mListener;

    public StepsAdapter(Step[] steps, OnStepItemClickListener listener){
        mSteps = steps;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mSteps[position];
        holder.position = position;
        holder.description.setText(step.getShortDescription());
        if (step.getVideoURL()!=null && !TextUtils.isEmpty(step.getVideoURL())){
            holder.videoIcon.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (mSteps==null) return 0;
        return mSteps.length;
    }

    public void swapSteps(Step[] steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_item_description)
        TextView description;
        @BindView(R.id.step_item_video_icon)
        ImageView videoIcon;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onStepClicked(mSteps[position]);
                }
            });
        }
    }
}
