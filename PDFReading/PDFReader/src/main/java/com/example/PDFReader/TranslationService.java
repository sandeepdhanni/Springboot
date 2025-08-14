package com.example.PDFReader;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



@Service
public class TranslationService {

    private static final String API_URL = "https://lingva.ml/api/v1/ar/en/";

    public String translateArabicToEnglish(String arabicText) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.getForObject(API_URL + arabicText, Map.class);
            return (String) response.get("translation");
        } catch (Exception e) {
            throw new RuntimeException("Translation failed", e);
        }
    }
}



