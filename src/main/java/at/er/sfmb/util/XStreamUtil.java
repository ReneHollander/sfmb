package at.er.sfmb.util;

import at.er.sfmb.plugin.Game;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.plugin.team.TeamColor;
import at.er.sfmb.plugin.timer.timeables.InvincibilityTimer;
import at.er.sfmb.plugin.timer.timeables.RemindTimer;
import at.er.sfmb.plugin.timer.timeables.WoolPlaceTimer;
import at.er.sfmb.util.timer.Timeable;
import com.thoughtworks.xstream.XStream;

public class XStreamUtil {

    public static XStream createXStream() {
        XStream xstream = new XStream();
        xstream.setMode(XStream.ID_REFERENCES);

        xstream.alias("game", Game.class);
        xstream.alias("battlePlayer", BattlePlayer.class);
        xstream.alias("teamColor", TeamColor.class);
        xstream.alias("team", Team.class);
        xstream.alias("woolPlaceTimer", WoolPlaceTimer.class);
        xstream.alias("invincibilityTimer", InvincibilityTimer.class);
        xstream.alias("remindTimer", RemindTimer.class);

        xstream.useAttributeFor(BattlePlayer.class, "uuid");
        xstream.useAttributeFor(BattlePlayer.class, "lastValidName");

        xstream.useAttributeFor(Team.class, "teamColor");

        xstream.useAttributeFor(Timeable.class, "timeScale");
        xstream.useAttributeFor(Timeable.class, "every");

        xstream.useAttributeFor(SerializableLocation.class, "world");
        xstream.useAttributeFor(SerializableLocation.class, "uuid");
        xstream.useAttributeFor(SerializableLocation.class, "x");
        xstream.useAttributeFor(SerializableLocation.class, "y");
        xstream.useAttributeFor(SerializableLocation.class, "z");
        xstream.useAttributeFor(SerializableLocation.class, "yaw");
        xstream.useAttributeFor(SerializableLocation.class, "pitch");

        return xstream;
    }

}
