#!/usr/bin/env sh

set -eu

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$CLASSPATH" ]; then
  echo "Fehler: gradle/wrapper/gradle-wrapper.jar fehlt."
  echo "Bitte erst einmal mit folgendem Befehl erzeugen:"
  echo "  gradle wrapper"
  echo "oder"
  echo "  ./gradlew wrapper"
  exit 1
fi

JAVA_CMD=${JAVA_HOME:+$JAVA_HOME/bin/java}
if [ -z "${JAVA_CMD:-}" ]; then
  JAVA_CMD=java
fi

exec "$JAVA_CMD" \
  -classpath "$CLASSPATH" \
  org.gradle.wrapper.GradleWrapperMain "$@"
