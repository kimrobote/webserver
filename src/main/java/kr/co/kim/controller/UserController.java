package kr.co.kim.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController implements IController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public byte[] handleRequest(String method, String url, String requestBoby) throws Exception {
        String paths = url.split("?")[0];

        if("form.html".equals(paths)) {
            return getSingUpForm();
        }

        return "No Found".getBytes();
    }

    private byte[] getSingUpForm() throws IOException {
        String filePath = "./webapp/user/form.html";
        File file = new File(filePath);
        log.info(file.getAbsolutePath());

        Path path = Paths.get(filePath);
        if(Files.exists(path)) {
            return Files.readAllBytes(path);
        }

        return "No Found".getBytes();
    }
    
}
