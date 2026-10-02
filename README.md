# RandomCraftingPlugin

Ein Paper/Spigot-Plugin für Minecraft 1.21.1, das serverweit zufällige Crafting-Rezepte generiert.

## Features
- Globale zufällige Crafting-Rezepte
- Neu generiert beim Start
- `/randomcraft` zum sofortigen Neu-Mischen
- Automatische Wiederholung alle 10 Minuten

## Voraussetzungen
- Java 21
- Gradle installiert, oder Gradle Wrapper verwenden
- Paper/Spigot Server 1.21.1

## Build mit Gradle
```bash
gradle build
```

Oder mit dem Wrapper:
```bash
./gradlew build
```

Die erzeugte JAR-Datei liegt dann hier:
```bash
build/libs/RandomCraftingPlugin.jar
```

## Installation
1. Kopiere die JAR in den `plugins/`-Ordner deines Servers
2. Starte den Server
3. Nutze `/randomcraft` für neue zufällige Rezeptliste

## Hinweis
Das Plugin entfernt beim Start alle vorhandenen Crafting-Rezepte und ersetzt sie durch zufällig generierte.
