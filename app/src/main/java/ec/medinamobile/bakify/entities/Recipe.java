package ec.medinamobile.bakify.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Erick on 30/6/17.
 */

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;
    private int servings;
    private String image;
    private int serverId;

    public Recipe(){

    }

    public Recipe(Parcel parcel) {
        setId(parcel.readInt());
        setName(parcel.readString());
        setIngredients((Ingredient[]) parcel.readArray(Ingredient.class.getClassLoader()));
        setSteps((Step[])parcel.readArray(Step.class.getClassLoader()));
        setServings(parcel.readInt());
        setImage(parcel.readString());
        setServerId(parcel.readInt());
    }


    public static Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeArray(getIngredients());
        parcel.writeArray(getSteps());
        parcel.writeInt(getServings());
        parcel.writeString(getImage());
        parcel.writeInt(getServerId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}

