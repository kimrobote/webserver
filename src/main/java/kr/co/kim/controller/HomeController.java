package kr.co.kim.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kim.helper.RequestParser;

public class HomeController implements IController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Override
    public byte[] handleRequest(RequestParser request) throws Exception {
        String filePath = "./webapp/index.html";
        File file = new File(filePath);
        log.info(file.getAbsolutePath());

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }

        return "No Found".getBytes();
    }
}
