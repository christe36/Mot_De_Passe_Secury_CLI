# Générateur de Mot de Passe Sécurisé — CLI & Docker

Projet réalisé par **Eboule Miezan Christ Romuald Emmanuel**  

---

## Description

Application Java 21 en ligne de commande (CLI) permettant de générer
des mots de passe robustes et sécurisés. La force de chaque mot de passe
est validée par un microservice Docker utilisant l'algorithme **zxcvbn**

---

## Prérequis

- Java 21 JDK
- Maven 3.9+
- Docker Desktop

---

## Lancement

### 1. Construire et démarrer le conteneur Docker

```bash
docker build -t password-validator .
docker run -d -p 3000:3000 --name validator-service password-validator
```

### 2. Compiler et lancer l'application

```bash
mvn clean package
java -jar target/motDePasseSecurCLI.jar
```

---

## Exempledd d'application

Longueur du mot de passe : 16

Avec des majuscules ? (true/false) : true
Avec des chiffres ? (true/false) : true
Avec des symboles ? (true/false) : true
Combien de mots de passe voulez-vous générer ? : 3

RÉSULTATS AVEC VALIDATION DOCKER

%T51SuDqtv&T]g | Force : Fort
!Np5ONQX&*oEMwU@ | Force : Très fort
BCU5j_LIZ#_c)IC6 | Force : Fort

---
