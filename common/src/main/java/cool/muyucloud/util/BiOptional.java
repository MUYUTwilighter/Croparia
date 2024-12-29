package cool.muyucloud.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BiOptional<L, R> {
    @Nullable
    private final L left;
    @Nullable
    private final R right;

    public BiOptional(@Nullable L left, @Nullable R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @param ifLeft  left value handler, run if only left is not null
     * @param ifRight right value handler, run if only right is not null
     */
    public void ifEither(@NotNull Consumer<L> ifLeft, @NotNull Consumer<R> ifRight) {
        ifEither(ifLeft, ifRight, () -> {
        });
    }

    /**
     * @param ifLeft  left value handler, run if only left is not null
     * @param ifRight right value handler, run if only right is not null
     * @param orElse  run if both value are null or non-null
     */
    public void ifEither(@NotNull Consumer<L> ifLeft, @NotNull Consumer<R> ifRight, @NotNull Runnable orElse) {
        ifEither(ifLeft, ifRight, (l, r) -> orElse.run(), orElse);
    }


    /**
     * @param ifLeft    left value handler, run if only left is not null
     * @param ifRight   right value handler, run if only right is not null
     * @param ifBoth    run if only both value are not null
     * @param ifNeither run if both value are null or non-null
     */
    public void ifEither(Consumer<L> ifLeft, Consumer<R> ifRight, BiConsumer<L, R> ifBoth, Runnable ifNeither) {
        if (left != null && right != null) {
            ifBoth.accept(left, right);
            return;
        }
        if (left != null) {
            ifLeft.accept(left);
            return;
        }
        if (right != null) {
            ifRight.accept(right);
            return;
        }
        ifNeither.run();
    }

    /**
     * @param ifLeft  left value handler, run as long as left is not null
     * @param ifRight right value handler, run as long as right is not null
     */
    public void ifAny(@NotNull Consumer<L> ifLeft, @NotNull Consumer<R> ifRight) {
        if (left != null) ifLeft.accept(left);
        if (right != null) ifRight.accept(right);
    }

    /**
     * @param ifLeft    left value handler, run as long as left is not null
     * @param ifRight   right value handler, run as long as right is not null
     * @param ifNeither run if both value are null
     * @see #ifEither(Consumer, Consumer, Runnable)
     */
    public void ifAny(@NotNull Consumer<L> ifLeft, @NotNull Consumer<R> ifRight, @NotNull Runnable ifNeither) {
        if (left != null) {
            ifLeft.accept(left);
            return;
        }
        if (right != null) {
            ifRight.accept(right);
            return;
        }
        ifNeither.run();
    }

    public boolean isBoth() {
        return left != null && right != null;
    }

    public boolean isNeither() {
        return left == null && right == null;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    public boolean isLeft() {
        return left != null && right == null;
    }

    public boolean isRight() {
        return left == null && right != null;
    }

    public boolean isAny() {
        return left != null || right != null;
    }

    public void ifLeft(Consumer<L> ifLeft) {
        if (left != null) ifLeft.accept(left);
    }

    public void ifRight(Consumer<R> ifRight) {
        if (right != null) ifRight.accept(right);
    }

    public void ifBoth(BiConsumer<L, R> biConsumer) {
        if (left != null && right != null) biConsumer.accept(left, right);
    }

    public void ifNeither(Consumer<String> onError) {
        if (left == null && right == null) onError.accept("Both left and right are null");
    }

    public static <L, R> BiOptional<L, R> of(@Nullable L left, @Nullable R right) {
        return new BiOptional<>(left, right);
    }

    public static <L, R> BiOptional<L, R> ofEmpty() {
        return new BiOptional<>(null, null);
    }
}
