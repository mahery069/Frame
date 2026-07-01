#!/bin/bash

# Définition des variables
APP_NAME="app"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
WEB_XML="src/main/xml"
BUILD_DIR="build"
LIB_DIR="/home/harison/Documents/tomcat-10.0.16/lib"
TOMCAT_WEBAPPS="/home/harison/Documents/tomcat-10.0.16/webapps"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"
FRAMEWORK_JAR="lib/mini-framework.jar"      # JAR du framework copié dans lib/

# Nettoyage et création du répertoire temporaire
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib             # dossier pour les JARs embarqués

# Compilation des fichiers Java avec le JAR des Servlets + framework
find $SRC_DIR -name "*.java" > sources.txt
javac -cp "$SERVLET_API_JAR:$FRAMEWORK_JAR" -d $BUILD_DIR/WEB-INF/classes @sources.txt

# Copier le framework JAR dans WEB-INF/lib (Tomcat le charge automatiquement)
cp $FRAMEWORK_JAR $BUILD_DIR/WEB-INF/lib/

# Copier les fichiers web (web.xml, JSP, etc.)
cp -r $WEB_DIR/* $BUILD_DIR/
cp -r $WEB_XML/* $BUILD_DIR/WEB-INF

# Générer le fichier .war dans le dossier build
cd $BUILD_DIR || exit
jar -cvf $APP_NAME.war *
cd ..

# Déploiement dans Tomcat
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo ""
echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."
echo ""