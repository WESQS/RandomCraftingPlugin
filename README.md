# RandomCraftingPlugin

Ein Spigot Minecraft Plugin, das serverweit zufällige Crafting-Rezepte generiert.

## Features
- Globale zufällige Crafting-Rezepte
- Neue zufällige Rezepte beim Start
- `/randomcraft` zum sofortigen Neu-Generieren
- Automatische Neu-Generierung alle 10 Minuten

## Installation
1. Baue das Plugin mit Maven:
   ```bash
   mvn clean package
   ```
2. Lege die erzeugte Datei aus `target/` in den Ordner `plugins/` deines Spigot-Servers.
3. Starte den Server neu.

## Befehl
- `/randomcraft` — generiert neue globale Zufallsrezepte

## Hinweise
Das Plugin entfernt beim Aktivieren alle vorhandenen Crafting-Rezepte und ersetzt sie durch neue, zufällig generierte Rezepte.
