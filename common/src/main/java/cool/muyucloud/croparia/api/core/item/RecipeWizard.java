package cool.muyucloud.croparia.api.core.item;

import com.google.common.collect.ImmutableList;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.util.RecipeWizardGenerator;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.JarJarEntry;
import cool.muyucloud.croparia.util.FileUtil;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeWizard extends Item {
    public static final ResourceLocation PACK_ID = CropariaIf.of("recipe_wizard");
    public static final OnLoadSupplier<Collection<RecipeWizardGenerator>> GENERATORS = OnLoadSupplier.of(() -> {
        for (JarJarEntry entry : PackHandler.getBuiltinGenerators().getOrDefault(PACK_ID, List.of())) {
            String name = entry.getEntry().getName();
            String prefix = "data-generators/%s/%s/".formatted(PACK_ID.getNamespace(), PACK_ID.getPath());
            String finalName = name.substring(prefix.length());
            entry.forInputStream(inputStream -> {
                try (FileOutputStream outputStream = new FileOutputStream(CropariaIf.CONFIG.getFilePath().resolve(finalName).toFile())) {
                    inputStream.transferTo(outputStream);
                    outputStream.flush();
                } catch (Throwable t) {
                    CropariaIf.LOGGER.error("Failed to move built-in recipe wizard template %s".formatted(name), t);
                }
            });
        }
        Collection<RecipeWizardGenerator> generators = new ArrayList<>();
        FileUtil.forFilesIn(
            CropariaIf.CONFIG.getFilePath().resolve("recipe_wizard/generators").toFile(),
            file -> RecipeWizardGenerator.read(file).ifPresent(generator -> {
                if (generator.isEnabled() && generator.isDependenciesAvailable()) {
                    generators.add(generator);
                }
            })
        );
        return ImmutableList.copyOf(generators);
    });

    public RecipeWizard(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide() || player == null || !player.isLocalPlayer() || context.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        BlockState target = context.getLevel().getBlockState(context.getClickedPos());
        for (RecipeWizardGenerator generator : GENERATORS.get()) {
            if (generator.matches(target)) {
                generator.handle(context);
                player.getCooldowns().addCooldown(context.getItemInHand(), 5);
                return InteractionResult.SUCCESS;
            }
        }
        Texts.overlay(player, Texts.translatable("overlay.croparia.recipe_wizard.error.no_match"));
        return InteractionResult.PASS;
    }
}
