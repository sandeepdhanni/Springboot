package com.example.ReadingPath;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @GetMapping("/list")
    public List<String> listFiles(@RequestParam String path) {
//        File folder = new File(path);
        File folder = new File(path.replace("\\", "/"));

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory path: " + path);
            return List.of("Invalid directory path");
        }

        List<String> fileNames = Arrays.stream(folder.listFiles())
                .map(File::getName)
                .collect(Collectors.toList());

        fileNames.forEach(System.out::println);
        return fileNames;
    }




    //THIS API WILL RETURN THE PATH FILES NAMES WHICH MATCH IN THE SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFiles(
            @RequestParam String searchText,
            @RequestParam String directoryPath) {

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.badRequest().body(List.of("Invalid directory path"));
        }

        // Split the search text into words
        Set<String> searchWords = Arrays.stream(searchText.split("\\s+"))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        // Find matching files
        List<String> matchingFilePaths = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(File::isFile)
                .filter(file -> searchWords.stream().anyMatch(word -> file.getName().toLowerCase().contains(word)))
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        return ResponseEntity.ok(matchingFilePaths);
    }
}
