package ec.medinamobile.bakify.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erick on 30/6/17.
 */

public class Ingredient implements Parcelable {

    public static final String MEASURE_CUP = "CUP";
    public static final String MEASURE_TABLESPOON = "TBLSP";
    public static final String MEASURE_TEASPOON = "TSP";
    public static final String MEASURE_KILOGRAM = "K";
    public static final String MEASURE_GRAM = "G";
    public static final String MEASURE_OUNCE = "OZ";
    public static final String MEASURE_UNIT = "UNIT";

    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(){

    }

    public Ingredient(Parcel parcel) {
        setQuantity(parcel.readInt());
        setMeasure(parcel.readString());
        setIngredient(parcel.readString());
    }

    public static Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getQuantity());
        parcel.writeString(getMeasure());
        parcel.writeString(getIngredient());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}
