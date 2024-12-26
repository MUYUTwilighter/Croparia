package cool.muyucloud.data.crop;

public enum CropType {
    ANIMAL,
    CROP,
    ELEMENTAL,
    FOOD,
    MONSTER,
    NATURE;

    public String getModelName() {
        return this.name().toLowerCase();
    }
}
