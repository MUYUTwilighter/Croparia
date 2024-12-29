package cool.muyucloud.data.crop;

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
