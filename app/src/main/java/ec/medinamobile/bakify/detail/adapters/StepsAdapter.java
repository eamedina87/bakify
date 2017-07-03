package ec.medinamobile.bakify.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Step;

/**
 * Created by Supertel on 3/7/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private final Step[] mSteps;

    public StepsAdapter(Step[] steps){
        mSteps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mSteps[position];
        holder.description.setText(step.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps==null) return 0;
        return mSteps.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_item_description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
