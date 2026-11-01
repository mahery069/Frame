#!/bin/bash

# Script de déploiement pour l'application de test Sprint 11

echo "Nettoyage..."
rm -rf build

echo "Compilation des contrôleurs..."
mkdir -p build/WEB-INF/classes
mkdir -p build/WEB-INF/lib
mkdir -p build/WEB-INF/views

# Copier le framework JAR
cp ../build/mini-framework.jar build/WEB-INF/lib/

# Compiler les contrôleurs
javac -cp "build/WEB-INF/lib/mini-framework.jar:/usr/share/tomcat/lib/servlet-api.jar" \
      -d build/WEB-INF/classes \
      src/main/java/test/*.java

# Copier les vues
cp -r src/main/webapp/WEB-INF/views/* build/WEB-INF/views/

# Copier web.xml
cp src/main/xml/web.xml build/WEB-INF/

# Créer le WAR
cd build
jar cvf Sprint11Test.war *
cd ..

echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."
