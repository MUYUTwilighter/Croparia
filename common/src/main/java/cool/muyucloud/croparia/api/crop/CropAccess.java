package cool.muyucloud.croparia.api.crop;

public interface CropAccess<C extends AbstractCrop> {
    C getCrop();

    @SuppressWarnings("unchecked")
    static <C extends AbstractCrop> C tryGet(CropAccess<?> access) {
        AbstractCrop crop = access.getCrop();
        try {
            return (C) crop;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
