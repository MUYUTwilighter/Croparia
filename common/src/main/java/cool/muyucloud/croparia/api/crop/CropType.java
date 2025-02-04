package cool.muyucloud.croparia.api.crop;

public enum CropType {
    ANIMAL,
    CROP,
    FOOD,
    MONSTER,
    NATURE;

    public String getModelName() {
        return this.name().toLowerCase();
    }
}
