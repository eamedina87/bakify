package ec.medinamobile.bakify.detail.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) mRecipe = getArguments().getParcelable(Constants.INTENT_EXTRA_RECIPE);
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
        StepsAdapter stepsAdapter = new StepsAdapter(mRecipe.getSteps(), this);
        stepsList.setAdapter(stepsAdapter);
        RecyclerView.LayoutManager ingredientsManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        stepsList.setLayoutManager(ingredientsManager);
    }

    private void setupIngredientsRecyclerView() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        ingredientsList.setAdapter(ingredientsAdapter);
        RecyclerView.LayoutManager ingredientsManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        ingredientsList.setLayoutManager(ingredientsManager);
    }

    public void setIngredients(Ingredient[] ingredients){
        if (ingredients!=null && ingredients.length>0){
            mRecipe.setIngredients(ingredients);
            ingredientsTitle.setText(String.format(getString(R.string.ingredients_title),ingredients.length));
            setupIngredientsRecyclerView();
        } else {
            //Show EmptyDisplay
        }
    }

    public void setSteps(Step[] steps) {
        if (steps!=null && steps.length>0){
            mRecipe.setSteps(steps);
            stepsTitle.setText(String.format(getString(R.string.steps_title),steps.length));
            setupStepsRecyclerView();
        } else {
            //Show EmptyDisplay
        }
    }

    @Override
    public void onStepClicked(Step step) {
        mCallbacks.onStepClicked(step);
    }
}
