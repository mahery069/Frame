#!/bin/bash

FRAMEWORK_NAME="mini-framework"
FRAMEWORK_SRC="src/main/java"

BUILD_DIR="build"
FRAMEWORK_BUILD="$BUILD_DIR/classes"
FRAMEWORK_JAR="$BUILD_DIR/$FRAMEWORK_NAME.jar"

SERVLET_API_JAR="/home/harison/Documents/hh/tomcat-10.0.16/lib/servlet-api.jar"

echo "Nettoyage..."
rm -rf "$BUILD_DIR"
mkdir -p "$FRAMEWORK_BUILD"

echo "Compilation du framework..."

find "$FRAMEWORK_SRC" -name "*.java" > sources.txt

javac -cp "$SERVLET_API_JAR" \
      -d "$FRAMEWORK_BUILD" \
      @sources.txt

if [ $? -ne 0 ]; then
    echo "Erreur de compilation"
    exit 1
fi

echo "Création du JAR..."

jar -cf "$FRAMEWORK_JAR" -C "$FRAMEWORK_BUILD" .

echo "Framework exporté : $FRAMEWORK_JAR"