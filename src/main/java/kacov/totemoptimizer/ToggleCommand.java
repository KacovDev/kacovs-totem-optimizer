package kacov.totemoptimizer;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ToggleCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                literal("totemoptimizer")
                        .then(literal("toggle")
                                .executes(ctx -> {
                                    TotemOptimizer.enabled = !TotemOptimizer.enabled;

                                    boolean state = TotemOptimizer.enabled;
                                    ctx.getSource().sendFeedback(
                                            Text.literal("TotemOptimizer is now: ")
                                                    .append(state ? Text.literal("§aENABLED")
                                                                  : Text.literal("§cDISABLED"))
                                    );

                                    return 1;
                                })
                        )
        );
    }
}
