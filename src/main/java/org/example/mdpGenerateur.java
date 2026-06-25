package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public class mdpGenerateur {
        // Définition des types de caractères
        private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String DIGITS = "0123456789";
        private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}";
        private final SecureRandom random = new SecureRandom();

         // Génère un mot de passe robuste selon les paramètres choisis.

        public String generate(int length, boolean includeUpper, boolean includeDigits, boolean includeSymbols) {
            StringBuilder characterPool = new StringBuilder(LOWER);
            List<Character> passwordChars = new ArrayList<>();
            // On s'assure d'avoir au moins un caractère de chaque type demandé
            if (includeUpper) {
                characterPool.append(UPPER);
                passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
            }
            if (includeDigits) {
                characterPool.append(DIGITS);
                passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
            }
            if (includeSymbols) {
                characterPool.append(SYMBOLS);
                passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
            }

            // On complète le reste de la longueur demandée
            for (int i = passwordChars.size(); i < length; i++) {
                passwordChars.add(characterPool.charAt(random.nextInt(characterPool.length())));
            }
            //Le mélange pour que les premiers caractères ne soient pas prévisibles
            Collections.shuffle(passwordChars);
            StringBuilder finalPassword = new StringBuilder();
            for (char c : passwordChars) {
                finalPassword.append(c);
            }
            return finalPassword.toString();
        }
    }

