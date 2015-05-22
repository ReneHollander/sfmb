package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;

public class BattleCommandReset implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        BattlePlugin.instance().resetGame();
        player.sendMessage(BattlePlugin.prefix() + "The Battle was reloaded and the save file reset!");
        return true;
    }
}
