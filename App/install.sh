#!/bin/bash

SERVER_DIR="ApacheLike"

echo "Mise à jour du système..."
sudo apt update -y


JAVA_VERSION=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
REQUIRED_JDK_VERSION="21"

echo "Vérification de la version de Java..."

if [[ -z "$JAVA_VERSION" ]]; then
    echo "Java n'est pas installé, installation de JDK 21..."
    sudo apt install openjdk-21-jdk -y
elif [[ "$JAVA_VERSION" < "$REQUIRED_JDK_VERSION" ]]; then
    echo "La version actuelle de Java ($JAVA_VERSION) est inférieure à la version requise ($REQUIRED_JDK_VERSION), mise à jour..."
    sudo apt install openjdk-21-jdk -y
else
    echo "Java $JAVA_VERSION est déjà installé, version suffisante."
fi

echo "Création des répertoires pour votre serveur..."

sudo mkdir -p /opt/$SERVER_DIR

sudo mkdir -p /opt/$SERVER_DIR/bin
sudo mkdir -p /opt/$SERVER_DIR/htdocs
sudo mkdir -p /opt/$SERVER_DIR/conf

echo "Copie des fichiers vers /opt/$SERVER_DIR..."

cp -r ./bin/* /opt/$SERVER_DIR/bin/
cp -r ./htdocs/* /opt/$SERVER_DIR/htdocs/
cp -r ./conf/* /opt/$SERVER_DIR/conf/

echo "Version de Java installée :"
java -version

echo "Structure des répertoires de votre serveur :"
ls -l /opt/$SERVER_DIR