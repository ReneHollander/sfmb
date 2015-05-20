package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.BattleCommand;
import at.er.sfmb.plugin.player.BattlePlayer;

public class BattleCommandReset implements BattleCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        BattlePlugin.instance().resetGame();
        player.sendMessage(BattlePlugin.prefix() + "The Battle was reloaded and the save file reset!");
        return true;
    }
}
