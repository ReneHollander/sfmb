Willkommen beim offiziellen YouTube-Battle Plugin
-------------------------------------------------

### Download
**Latest Build**: None Yet
## Informationen

### Autoren
* EXSolo:
	- Website: [exsoloscript.com](http://exsoloscript.com/)
	- YouTube: [EXSoloScript](https://www.youtube.com/user/EXSoloScript)
	- Skype: exsoloyt

* Rene8888:
	- Website: [rene8888.at](http://rene8888.at/)
	- YouTube: [Rene8004](https://www.youtube.com/user/Rene8004/)
	- Skype: simpsons_fan2
	- Twitter: [Rene8004](https://twitter.com/Rene8004)

### Den Server konfigurieren
#### Installation:
Verwendet wird Craftbukkit Version `1.8.4-R0.1-SNAPSHOT`. Nähere Informationen wie du Bukkit 1.8 bekommst findest du hier: [Bukkit, CraftBukkit & Spigot 1.8 - Server Instructions](http://www.spigotmc.org/threads/bukkit-craftbukkit-spigot-1-8.36598/). Das Plugin wird wie jedes andere Plugin installiert. Am sichersten ist es, wenn vorher alle anderen Plugins wie Permission System, World Edit, Essentials usw entfernt werden, denn onst kann es zu Problemen kommen!  
Dann kann der Server schon gestartet werden. Die Konfiguration erfolgt nun INGAME:

1. Zuerst muss der Spawn Punkt mit dem Kommando `/battle spawn` gesetzt werden.
2. Durch einen Fehler in der momentanen Version von WorldEdit, können wir den Border nicht automatisch generieren. Als Border Material wird Bedrock empfohlen.
3. Damit jeder Spieler im Richtigen Team ist, muss jeder Spieler folgendes Kommando ausführen: `/battle join <farbe>`. Folgende Team Farben sind verfügbar: `white, yellow, green, cyan, purple, blue, red, black`
4. Sind alle Spieler in den Richtigen Teams, so kann das Spiel mit `/battle start` gestartet werden. Ab jetzt läuft das Spiel.

#### Befehle im Spielverlauf
Um eine neue Wolle zu erhalten, wird das Kommando `/battle life` verwendet. Mit dem Befehl `/battle pause` kann das Spiel angehalten werden. Alle Timer (Wolle platzieren, Invincibility, ...) werden pausiert. Nun kann der Server auch gestoppt werden um das Spiel zu einem beliebigen Zeitpunkt wieder weiter zu führen. Dafür wird das Kommando  `/battle resume` verwendet. Nach einem 5 Sekunden Countdown werden alle Timer wieder gestartet und das Spiel läuft weiter. Achtung! Wenn das Spiel pausiert ist kann man sich frei bewegen! Auch Inventar, Leben und Hunger speichern wir nicht!

## Spielregeln
TODO

## Regeln zur Nutzung
Solltest du das Plugin verwenden und Videos darüber machen, musst du folgenden Link in der Beschreibung platzieren: `https://github.com/Rene8888/sfmb`. Diesen Link muss jeder Mitspieler in der Beschreibung verlinken! Änderungen am Code (wie in der Lizenz beschrieben) müssen Öffentlich zugänglich sein.

## Arbeitsumgebung aufsetzen
### Setup für IntelliJ
- Workspace erzeugen
	- Windows: CMD "gradlew.bat idea"
	- Linux/OSX: Terminal "./gradlew idea"
- Bukkit Dependencies installieren:
	- Windows: CMD "gradlew.bat buildBukkit"
	- Linux/OSX: Terminal "./gradlew buildBukkit"
- Projekt in IntelliJ öffnen
- Arbeiten!

Es sind folgende Run Configurations vorhanden:
- Build Plugin: Erzeugt Plugin Jar und platziert es im lokalen Test Server. Bei Code Änderung kann auch während ein Dev Server läuft das Plugin neu kompiliert und in den `plugins/` Ordner automatisch verschoben werden. Im Spiel kann man mit `/reload` das Plugin neu laden.
- Start Dev Server: Startet einen lokalen Bukkit Server zum testen. Dieser Testserver kann wenn der Debug Funktion (grüner Käfer neben Play Button) auch Debugt werden! Breakpoints, Code HotSwap ist dann verfügbar!

### Setup für Eclipse
TODO

##Mitwirken
####Pull Requests
Du hättest gerne eine Änderung vorgenommen? Oder hast du einen Bug gefunden den du selber fixen kannst? Nice!  
Damit das gut Funktioniert, bitte ich dich, den Eclipse Code Formatter zu verwenden! Beim Line Wrapping in den Einstellungen bitte die Maximale Zeichenlänge auf 300 Zeichen stellen!  
Auch bitte ich dich, alles gut zu testen und zu dokumentieren! Jetzt kannst du deinen Pull Request ausführen. Wenn alles passt wird der Request nach maximal 1-2 Tagen angenommen. Die Änderung wird dann bei der nächsten Version im Download inkludiert. Sollte es sich um einen Major Bugfix handeln, wird sofort ein neuer Download bereitgestellt.

####Issues
Solltest du einen Fehler finden, kannst du ihn ganz einfach reporten:

1. Navigiere zur [Issue Page](https://github.com/Rene8888/smfb/issues)
2. Überprüfe ob das Problem nicht bereits reportet wurde
3. Erstelle ein neues Problem (`New Issue` Button)
4. Überlege dir einen sinnvollen Titel
5. Eine Ausführliche Beschreibung und/oder Crash Reports über PasteBin sind ein muss!
6. Damit dein Problem eingeordnet werden kann sollte ein passendes Label ausgewählt werden!
	1. Schwerer Fehler der sofort gefixt werden muss: `major bug`
	2. Fehler der das Spiel kaum beeinträchtigt aber gefixt werden sollte: `bug`
	3. Leichter Fehler wie falsche Anzeige usw: `minor bug`
7. Abwarten bis sich jemand Meldet...

##Lizenz
Verwendet wird die General Public License in der Version 3. Eine Kopie der Lizenz befindet sich im Repository.
