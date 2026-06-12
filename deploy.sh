#!/bin/bash

# ================================================================
#  BUILD & DEPLOY — Mini-Framework
APP_NAME="Platzao"
FRAMEWORK_NAME="mini-framework"

# Sources
FRAMEWORK_SRC="src/main/java"
APP_SRC="src/main/java"
WEB_DIR="src/main/webapp"
WEB_XML="src/main/xml"

# Build
BUILD_DIR="build"
FRAMEWORK_BUILD="$BUILD_DIR/framework-classes"
APP_BUILD="$BUILD_DIR/app-classes"
FRAMEWORK_JAR="$BUILD_DIR/$FRAMEWORK_NAME.jar"
WAR_STAGING="$BUILD_DIR/war"

# Tomcat
LIB_DIR="/home/mahery/Documents/tomcat-10.0.16/lib"
TOMCAT_WEBAPPS="/home/mahery/Documents/tomcat-10.0.16/webapps"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

RED='\033[0;31m'; GREEN='\033[0;32m'
CYAN='\033[0;36m'; YELLOW='\033[1;33m'; NC='\033[0m'

step() { echo -e "\n${CYAN}▶ $1${NC}"; }
ok()   { echo -e "${GREEN}  ✔ $1${NC}"; }
err()  { echo -e "${RED}  ✘ $1${NC}"; exit 1; }
warn() { echo -e "${YELLOW}  ⚠ $1${NC}"; }

# ================================================================
# 0. Nettoyage
# ================================================================
step "Nettoyage..."
rm -rf "$BUILD_DIR"
mkdir -p "$FRAMEWORK_BUILD"
mkdir -p "$APP_BUILD"
mkdir -p "$WAR_STAGING/WEB-INF/classes"
mkdir -p "$WAR_STAGING/WEB-INF/lib"
ok "Répertoires créés"

# ================================================================
# 1. Compilation du FRAMEWORK
# ================================================================
step "Compilation du Framework ($FRAMEWORK_NAME)..."

find "$FRAMEWORK_SRC" -name "*.java" > "$BUILD_DIR/framework-sources.txt"
[ -s "$BUILD_DIR/framework-sources.txt" ] || err "Aucun .java trouvé dans $FRAMEWORK_SRC"

javac -cp "$SERVLET_API_JAR" \
      -d  "$FRAMEWORK_BUILD" \
      @"$BUILD_DIR/framework-sources.txt" || err "Échec compilation framework"
ok "Framework compilé"

# ================================================================
# 2. Export du FRAMEWORK en JAR
# ================================================================
step "Export → $FRAMEWORK_JAR"

jar -cf "$FRAMEWORK_JAR" -C "$FRAMEWORK_BUILD" . || err "Échec création JAR"
ok "JAR créé : $FRAMEWORK_JAR  ($(du -h "$FRAMEWORK_JAR" | cut -f1))"
