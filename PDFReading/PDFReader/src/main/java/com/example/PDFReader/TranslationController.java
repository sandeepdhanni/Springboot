package com.example.PDFReader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public String translateArabicToEnglish(@RequestBody TranslationRequest request) {
        return translationService.translateArabicToEnglish(request.getText());
    }

}

