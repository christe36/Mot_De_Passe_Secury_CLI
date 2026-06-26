package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mdpGenerateur gen = new mdpGenerateur();

        System.out.println("GÉNÉRATEUR DE MOT DE PASSE SÉCURISÉ");

        int longueur       = lireEntierPositif(scanner, "Longueur du mot de passe : ");
        boolean majuscules = lireBoolean(scanner, "Avec des majuscules ? (true(t)/false(f)) : ");
        boolean minuscules = lireBoolean(scanner, "Avec des minuscules ? (true(t)/false(f)) : ");
        boolean chiffres   = lireBoolean(scanner, "Avec des chiffres ? (true(t)/false(f)) : ");
        boolean symboles   = lireBoolean(scanner, "Avec des symboles ? (true(t)/false(f)) : ");
        int quantite       = lireEntierPositif(scanner, "Combien de mots de passe ? : ");

        System.out.println("\nRÉSULTATS AVEC VALIDATION DOCKER");
        DockerValidator validator = new DockerValidator();
        for (int i = 0; i < quantite; i++) {
            String mdp = gen.generate(longueur, majuscules, minuscules, chiffres, symboles);
            String scoreLabel = validator.validateScore(mdp);
            System.out.println((i + 1) + ". " + mdp + " | Force : " + scoreLabel);
        }

        System.out.println("\n Merci d'avoir utilisé l'outil de sécurité !");
        scanner.close();
    }

    /**
     * Redemande tant que l'utilisateur ne tape pas un entier positif.
     */
    private static int lireEntierPositif(Scanner scanner, String question) {
        while (true) {
            System.out.print(question);
            try {
                int val = Integer.parseInt(scanner.next().trim());
                if (val > 0) return val;
                System.out.println("❌ Entrez un nombre positif.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Nombre entier requis.");
            }
        }
    }

    /**
     * Redemande tant que l'utilisateur ne tape pas t/true ou f/false.
     */
    private static boolean lireBoolean(Scanner scanner, String question) {
        while (true) {
            System.out.print(question);
            String input = scanner.next().trim().toLowerCase();
            if (input.equals("t") || input.equals("true"))  return true;
            if (input.equals("f") || input.equals("false")) return false;
            System.out.println("Reponse invalide ! Tapez t ou f.");
        }
    }
}