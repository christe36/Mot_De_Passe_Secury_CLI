package org.example;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DockerValidator {
    private static final String DOCKER_URL = "http://localhost:3000/score?password=";

    public String validateScore(String password) {
        try {
            // Encodage du mot de passe pour éviter les problèmes avec les caractères spéciaux dans l'URL
            String encoded = URLEncoder.encode(password, StandardCharsets.UTF_8);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DOCKER_URL + encoded))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // On analyse la réponse JSON du conteneur
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            int score = json.get("score").getAsInt();

            return convertScoreToText(score);
        } catch (Exception e) {
            return "Erreur (Docker non lancé ?)";
        }
    }

    private String convertScoreToText(int score) {
        return switch (score) {
            case 0 -> "Très faible";
            case 1 -> "Faible";
            case 2 -> "Moyen";
            case 3 -> "Fort";
            case 4 -> "Très fort";
            default -> "Inconnu";
        };
    }
}