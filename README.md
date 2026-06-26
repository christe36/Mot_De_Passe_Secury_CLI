# Lancement

### 1. Construire l'image Docker

```bash
docker build -t password-validator .
```

### 2. Démarrer le conteneur

```bash
docker run -d -p 3000:3000 --name validator-service password-validator
```

### 3. Compiler et lancer

```bash
mvn clean package
java -jar target/motDePasseSecurCLI.jar
```

### ⚡ Tout en une seule commande

```bash
docker start validator-service && mvn clean package && java -jar target/motDePasseSecurCLI.jar
```

---

## Exemple d'utilisation
GÉNÉRATEUR DE MOT DE PASSE SÉCURISÉ

Longueur du mot de passe : 16

Avec des majuscules ? (true(t)/false(f)) : t

Avec des minuscules ? (true(t)/false(f)) : t

Avec des chiffres ? (true(t)/false(f)) : t

Avec des symboles ? (true(t)/false(f)) : t

Combien de mots de passe ? : 3
RÉSULTATS AVEC VALIDATION DOCKER

)#_swVRz31r1s@K | Force : Très fort
wiLEiOE3T]yT(9# | Force : Très fort
#%vhHDl69%8DPx! | Force : Fort

Merci d'avoir utilisé l'outil de sécurité !

### Arrêter le conteneur

```bash
docker stop validator-service
docker rm validator-service
```

---

# Documentation Technique

## 1. Analyse Fonctionnelle

### 1.1 Principe global

**Mot De Passe Secury CLI** génère des mots de passe sécurisés et évalue
leur robustesse via un conteneur Docker séparé.

Le point clé : **l'évaluation ne se fait pas en Java** mais dans un
conteneur Docker. Les deux parties communiquent via HTTP :

+-------------------+      requête HTTP GET      +---------------------+

|   Programme Java  |  ---------------------->>  |  Conteneur Docker   |

| (génération + CLI)|  <<--------------------    |  (service zxcvbn)   |

+-------------------+    réponse JSON {score}    +---------------------+

**Flux d'exécution :**

1. L'utilisateur saisit les paramètres (longueur, types, nombre)
2. `mdpGenerateur.java` génère un mot de passe sécurisé
3. `DockerValidator.java` l'envoie au conteneur via HTTP GET
4. `validate.js` appelle zxcvbn et retourne `{ "score": 0-4 }`
5. Java affiche le mot de passe et son niveau de force

### 1.2 Niveaux de force

| Score | Niveau      | Description                    |
|-------|-------------|--------------------------------|
| 0     | Très faible | Cassable immédiatement         |
| 1     | Faible      | Peu de complexité              |
| 2     | Moyen       | Complexité acceptable          |
| 3     | Fort        | Bonne complexité               |
| 4     | Très fort   | Excellente complexité          |

---

## 2. Analyse Technique

### 2.1 Structure du projet
Mot_De_Passe_Secury_CLI/

├── src/main/java/org/example/

│   ├── Main.java              # Interface CLI (saisie utilisateur)

│   ├── mdpGenerateur.java     # Génération via SecureRandom

│   └── DockerValidator.java   # Client HTTP vers Docker

├── validate.js                # Micro-service Node.js + zxcvbn

├── Dockerfile                 # Image Docker Node.js Alpine

├── pom.xml                    # Build Maven Java 21

├── .gitignore

└── README.md

### 2.2 Rôle de chaque fichier

**`mdpGenerateur.java`** — Le générateur  
Génère un mot de passe aléatoire avec `SecureRandom` (cryptographiquement
sûr). Garantit au moins un caractère de chaque type demandé, puis mélange
avec `Collections.shuffle()` pour éviter tout motif prévisible.

**`DockerValidator.java`** — Le client HTTP  
Envoie le mot de passe au conteneur via HTTP GET et récupère le score JSON.
Ne contient **aucune logique d'évaluation** — c'est la preuve que
l'évaluation est bien externe. Les caractères spéciaux sont encodés avec
`URLEncoder` pour éviter les erreurs dans l'URL.

**`Main.java`** — L'interface CLI  
Orchestre l'application : saisie des paramètres, appel du générateur
et du validateur, affichage des résultats. Inclut une validation des
saisies pour éviter tout plantage du programme.

**`validate.js`** — Le micro-service Node.js  
Tourne dans le conteneur Docker. Expose une API HTTP sur le port 3000.
Reçoit le mot de passe, appelle `zxcvbn(password)` et retourne
`{ "score": 0-4 }`. **C'est ici que se fait l'évaluation, pas en Java.**

**`Dockerfile`**  
Construit l'image Docker : Node.js Alpine + dépendances npm
(express, zxcvbn) + validate.js. Permet de recréer l'environnement
identiquement sur n'importe quelle machine.

**`pom.xml`**  
Configure Java 21, intègre Gson pour le parsing JSON, et produit
un JAR exécutable via `maven-assembly-plugin`.

### 2.3 Technologies

| Composant     | Technologie    | Rôle                              |
|---------------|----------------|-----------------------------------|
| Langage       | Java 21        | Génération + interface CLI        |
| Build         | Maven 3.9+     | Compilation + packaging JAR       |
| Parsing JSON  | Gson 2.10.1    | Lecture réponse Docker            |
| Conteneur     | Docker         | Isolement du service d'évaluation |
| Image Docker  | Node.js Alpine | Légèreté du conteneur             |
| Évaluation    | zxcvbn         | Calcul du score de robustesse     |
| Serveur HTTP  | Express.js     | API HTTP dans le conteneur        |

---

## 3. Guide d'Installation

### Étape 1 — Cloner le projet

```bash
git clone https://github.com/christe36/Mot_De_Passe_Secury_CLI.git
cd Mot_De_Passe_Secury_CLI
```

### Étape 2 — Construire l'image Docker

```bash
docker build -t password-validator .
```

### Étape 3 — Lancer le conteneur

```bash
docker run -d -p 3000:3000 --name validator-service password-validator
```

Tester le micro-service :

```bash
curl "http://localhost:3000/score?password=MonTest123!"
# Résultat attendu : {"score":3}
```

### Étape 4 — Compiler

```bash
mvn clean package
```

### Étape 5 — Lancer

```bash
java -jar target/motDePasseSecurCLI.jar
```

---
