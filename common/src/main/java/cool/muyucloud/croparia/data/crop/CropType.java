package cool.muyucloud.croparia.data.crop;

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
