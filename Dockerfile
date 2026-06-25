# Dockerfile — Micro-service de validation de mots de passe
# Image base : Node.js Alpine (légère)
# Le conteneur expose une API HTTP sur le port 3000

FROM node:alpine

# Répertoire de travail dans le conteneur
WORKDIR /app

# Installation des dépendances Node.js :
#   express : serveur HTTP léger
#   zxcvbn  : algorithme d'estimation de force de mot de passe
RUN npm install zxcvbn express

# Copie du micro-service dans le conteneur
# validate.js doit être dans le même dossier que ce Dockerfile
COPY validate.js server.js

# Port exposé par le micro-service
EXPOSE 3000

# Lancement du serveur au démarrage du conteneur
CMD ["node", "server.js"]
