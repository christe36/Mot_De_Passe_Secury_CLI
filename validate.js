// validate.js — Micro-service Node.js de validation de mots de passe
// Rôle : Reçoit un mot de passe via HTTP GET, appelle zxcvbn, renvoie le score JSON.
// Pourquoi ce code : Matérialise la séparation exigée — l'évaluation se fait ici, pas en Java.

const express = require('express');
const zxcvbn  = require('zxcvbn');

const app  = express();
const PORT = 3000;

/**
 * Route GET /score?password=<mot_de_passe>
 * Retourne un JSON { score: 0-4 } où :
 *   0 = Très faible, 1 = Faible, 2 = Moyen, 3 = Fort, 4 = Très fort
 */
app.get('/score', (req, res) => {
    const password = req.query.password || '';

    // zxcvbn analyse l'entropie, les mots du dictionnaire, les séquences connues
    const result = zxcvbn(password);

    // On renvoie uniquement le score numérique — Java s'occupe de l'affichage
    res.json({ score: result.score });
});

app.listen(PORT, () => {
    console.log(`Validateur zxcvbn prêt sur le port ${PORT}`);
});
