package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.util.ResourceLocationArgument;
import cool.muyucloud.croparia.util.text.Texts;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.resources.ResourceLocation;

import static cool.muyucloud.croparia.api.core.command.DumpCommand.dump;
import static cool.muyucloud.croparia.api.core.command.DumpCommand.dumpAll;

public class DumpCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP =
        LiteralArgumentBuilder.literal("dump");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, ResourceLocation> CROP =
        RequiredArgumentBuilder.argument("id", ResourceLocationArgument.id());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        CROP.executes(context -> {
            ResourceLocation id = ResourceLocationArgument.getId(context, "crop");
            return dump(id, Texts.success(context.getSource()), Texts.failure(context.getSource()), true);
        });
        DUMP.requires(s -> s.hasPermission(2));
        DUMP.executes(context -> dumpAll(Texts.success(context.getSource()), true));
        DUMP.then(CROP);
        return DUMP;
    }
}
