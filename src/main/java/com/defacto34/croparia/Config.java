package com.defacto34.croparia;

public class Config {
    private Boolean fruitUse = true;
    private Boolean postDataGen = true;

    public Boolean getFruitUse() {
        return fruitUse;
    }

    public void setFruitUse(Boolean fruitUse) {
        this.fruitUse = fruitUse;
    }

    public Boolean getPostDataGen() {
        return postDataGen;
    }

    public void setRecipePostInjector(Boolean recipePostInjector) {
        this.postDataGen = recipePostInjector;
    }
}
