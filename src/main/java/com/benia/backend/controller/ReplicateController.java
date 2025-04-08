package com.benia.backend.controller;

//CODIGO TEMPORARIAMENTE DESATIVADO, CODIGO PARA GERAÇÃO DE IMAGENS DA IA.

/*import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replicate")
@CrossOrigin(origins = "http://localhost:3000") 
public class ReplicateController {

    private static final String REPLICATE_API_TOKEN = System.getenv("// key da minha autoria e protegido, nao está disponivel para outras pessoas usarem. - jhordan"); 

    @PostMapping("/gerar-imagem-replicada")
    public ResponseEntity<?> gerarImagem(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");

        try {
            HttpClient client = HttpClient.newHttpClient();

            String requestBody = """
                {
                    "version": "db21e45fb98b0a28d0ef5c546f2780c19cb2b25d778d8e144452c0ffdfb8f168",
                    "input": { "prompt": "%s" }
                }
            """.formatted(prompt);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.replicate.com/v1/predictions"))
                .header("Authorization", "Token " + REPLICATE_API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String getUrl = JsonPath.read(response.body(), "$.urls.get");

            // Espera o resultado da imagem
            String imagemUrl = aguardarResultado(client, getUrl);

            return ResponseEntity.ok(Map.of("url", imagemUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao gerar imagem: " + e.getMessage()));
        }
    }

    private String aguardarResultado(HttpClient client, String url) throws Exception {
        while (true) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Token " + REPLICATE_API_TOKEN)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String status = JsonPath.read(response.body(), "$.status");

            if ("succeeded".equals(status)) {
                return JsonPath.read(response.body(), "$.output[0]");
            } else if ("failed".equals(status)) {
                throw new RuntimeException("A geração da imagem falhou.");
            }

            Thread.sleep(2000); // espera 2 segundos antes de tentar de novo
        }
    }
}
*/