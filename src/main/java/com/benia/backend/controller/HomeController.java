package com.benia.backend.controller;

import com.benia.backend.dto.MensagemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Endpoint para conversar com FastAPI
    @PostMapping("/mensagem")
    public ResponseEntity<Map<String, String>> receberMensagem(@RequestBody MensagemDTO body) {
        String mensagem = body.getMessage();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("message", mensagem);

        try {
            String pythonUrl = "http://localhost:8000/chat";
            Map<String, String> pythonResponse = restTemplate.postForObject(pythonUrl, requestBody, Map.class);

            String resposta = pythonResponse != null ? pythonResponse.get("response") : "Sem resposta do Python.";

            Map<String, String> respostaFinal = new HashMap<>();
            respostaFinal.put("resposta", resposta);
            return ResponseEntity.ok(respostaFinal);

        } catch (RestClientException e) {
            e.printStackTrace();
            Map<String, String> erro = new HashMap<>();
            erro.put("resposta", "Erro ao falar com o Python: " + e.getMessage());
            return ResponseEntity.status(500).body(erro);
        }
    }
}
