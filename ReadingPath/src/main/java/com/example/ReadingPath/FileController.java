package com.example.ReadingPath;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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
}
