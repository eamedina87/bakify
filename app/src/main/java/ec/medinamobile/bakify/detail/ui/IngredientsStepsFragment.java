package ec.medinamobile.bakify.detail.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.detail.adapters.IngredientsAdapter;
import ec.medinamobile.bakify.detail.adapters.OnStepItemClickListener;
import ec.medinamobile.bakify.detail.adapters.StepsAdapter;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.entities.Recipe;
import ec.medinamobile.bakify.entities.Step;
import ec.medinamobile.bakify.utils.Constants;

/**
 * Created by Erick on 3/7/17.
 */

public class IngredientsStepsFragment extends Fragment implements OnStepItemClickListener {

    @BindView(R.id.detail_ingredients_list)
    RecyclerView ingredientsList;
    @BindView(R.id.detail_steps_list)
    RecyclerView stepsList;
    @BindView(R.id.detail_ingredients_title)
    TextView ingredientsTitle;
    @BindView(R.id.detail_steps_title)
    TextView stepsTitle;
    private Recipe mRecipe;
    private OnIngredientsStepsFragmentCallbacks mCallbacks;
    private Context mContext;
    private StepsAdapter mStepsAdapter;
    private IngredientsAdapter mIngredientsAdapter;
    private LinearLayoutManager mIngredientsManager;
    private LinearLayoutManager mStepsManager;
    private Parcelable mStepState;
    private Parcelable mIngredientsState;

    public IngredientsStepsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.BUNDLE_RECIPE, mRecipe);
        mStepState = mStepsManager.onSaveInstanceState();
        outState.putParcelable(Constants.BUNDLE_STEPS_STATE, mStepState);
        mIngredientsState = mIngredientsManager.onSaveInstanceState();
        outState.putParcelable(Constants.BUNDLE_INGREDIENTS_STATE, mStepState);
        Log.d(this.getClass().getName(),"OnSaveInstanceState");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("IngredientsStepsFrag","onPause");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(this.getClass().getName(),"OnActivityCreated");
        if (savedInstanceState!=null) {
            if (savedInstanceState.get(Constants.BUNDLE_RECIPE)!=null) {
                mRecipe = savedInstanceState.getParcelable(Constants.BUNDLE_RECIPE);
                if (mRecipe.getIngredients() != null) {
                    setIngredients(mRecipe.getIngredients());
                }
                if (mRecipe.getSteps() != null) {
                    setSteps(mRecipe.getSteps());
                }
            }
            if (savedInstanceState.get(Constants.BUNDLE_INGREDIENTS_STATE)!=null &&
                    savedInstanceState.get(Constants.BUNDLE_STEPS_STATE)!=null){
                mStepState = savedInstanceState.getParcelable(Constants.BUNDLE_STEPS_STATE);
                mStepsManager.onRestoreInstanceState(mStepState);
                mIngredientsState = savedInstanceState.getParcelable(Constants.BUNDLE_INGREDIENTS_STATE);
                mIngredientsManager.onRestoreInstanceState(mIngredientsState);
            }

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(),"OnCreate");
        if (getArguments()!=null) {
            mRecipe = getArguments().getParcelable(Constants.INTENT_EXTRA_RECIPE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mContext = context;
            mCallbacks = (OnIngredientsStepsFragmentCallbacks) context;
        } catch (ClassCastException e){
            throw new ClassCastException("Activity must implement OnIngredientsStepsFragmentCallbacks!");
        }


    }

    private void setupStepsRecyclerView() {
        mStepsAdapter = new StepsAdapter(mRecipe.getSteps(), this);
        stepsList.setAdapter(mStepsAdapter);
        mStepsManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        stepsList.setLayoutManager(mStepsManager);
    }

    private void setupIngredientsRecyclerView() {
        mIngredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        ingredientsList.setAdapter(mIngredientsAdapter);
        mIngredientsManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        ingredientsList.setLayoutManager(mIngredientsManager);
    }

    public void setIngredients(Ingredient[] ingredients){
        if (ingredients!=null && ingredients.length>0){
            mRecipe.setIngredients(ingredients);
            ingredientsTitle.setText(String.format(getString(R.string.ingredients_title),ingredients.length));
            if (mIngredientsAdapter==null){
                setupIngredientsRecyclerView();
            } else {
                mIngredientsAdapter.swapIngredients(ingredients);
            }

        } else {
            //Show EmptyDisplay
        }
    }

    public void setSteps(Step[] steps) {
        if (steps!=null && steps.length>0){
            mRecipe.setSteps(steps);
            stepsTitle.setText(String.format(getString(R.string.steps_title),steps.length));
            if (mStepsAdapter ==null){
                setupStepsRecyclerView();
            } else {
                mStepsAdapter.swapSteps(steps);
            }

        } else {
            //Show EmptyDisplay
        }
    }

    @Override
    public void onStepClicked(Step step) {
        mCallbacks.onStepClicked(step);
    }
}
