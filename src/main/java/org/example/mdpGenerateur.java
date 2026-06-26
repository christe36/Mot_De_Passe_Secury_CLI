package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mdpGenerateur {
    private static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS  = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}";
    private final SecureRandom random = new SecureRandom();

    /**
     * Génère un mot de passe selon les 4 types de caractères demandés par l'utilisateur.
     * Au moins un caractère de chaque type activé est garanti dans le résultat final.
     */
    public String generate(int length, boolean includeUpper, boolean includeLower,
                           boolean includeDigits, boolean includeSymbols) {
        StringBuilder pool = new StringBuilder();
        List<Character> passwordChars = new ArrayList<>();

        // On construit le pool et on garantit au moins un caractère par type activé
        if (includeUpper) {
            pool.append(UPPER);
            passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        }
        if (includeLower) {
            pool.append(LOWER);
            passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        }
        if (includeDigits) {
            pool.append(DIGITS);
            passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        if (includeSymbols) {
            pool.append(SYMBOLS);
            passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }

        // Si aucun type sélectionné, on utilise les minuscules par défaut
        if (pool.isEmpty()) {
            pool.append(LOWER);
        }

        // On complète jusqu'à la longueur demandée
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(pool.charAt(random.nextInt(pool.length())));
        }

        // Mélange pour éviter que les caractères obligatoires soient toujours en début
        Collections.shuffle(passwordChars);

        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) finalPassword.append(c);
        return finalPassword.toString();
    }
}