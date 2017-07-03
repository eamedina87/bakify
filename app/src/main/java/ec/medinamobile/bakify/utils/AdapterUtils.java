package ec.medinamobile.bakify.utils;

import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Ingredient;

/**
 * Created by Erick on 3/7/17.
 */

public class AdapterUtils {

    public static String getStringFromFloat(float number){
        int i = (int) number;
        return number == i ? String.valueOf(i) : String.valueOf(number);
    }

    public static int getImageResourceForMeasure(String measure) {
        if (measure.equals(Ingredient.MEASURE_CUP)){
            return R.drawable.ic_measure_cup;
        } else if (measure.equals(Ingredient.MEASURE_GRAM)){
            return R.drawable.ic_measure_gram;
        } else if (measure.equals(Ingredient.MEASURE_KILOGRAM)){
            return R.drawable.ic_measure_kg;
        } else if (measure.equals(Ingredient.MEASURE_OUNCE)){
            return R.drawable.ic_measure_ounce;
        } else if (measure.equals(Ingredient.MEASURE_TABLESPOON)){
            return R.drawable.ic_measure_tablespoon;
        } else if (measure.equals(Ingredient.MEASURE_TEASPOON)){
            return R.drawable.ic_measure_teaspoon;
        } else if (measure.equals(Ingredient.MEASURE_UNIT)){
            return R.drawable.ic_measure_unit;
        }
        return 0;
    }
}
