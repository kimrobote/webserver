package kr.co.kim.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kim.helper.HeaderCodes;
import kr.co.kim.helper.HttpMethodCode;
import kr.co.kim.helper.RequestParser;
import kr.co.kim.service.UserService;

public class UserController implements IController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService = new UserService();

    @Override
    public byte[] handleRequest(RequestParser request) throws Exception {
        String[] paths = request.getHeaders().get(HeaderCodes.PATH).split("?");

        if ("user/form.html".equals(paths[0])) {
            return getSingUpForm();
        }

        if ("user/create".equals(paths[0])) {
            if (request.getHeaders().get(HeaderCodes.METHOD).equals(HttpMethodCode.GET)) {
                userService.createUser(paths[1]);
            }

            if (request.getHeaders().get(HeaderCodes.METHOD).equals(HttpMethodCode.POST)) {
                userService.createUser(request.getBody());
            }
        }

        return "No Found".getBytes();
    }

    private byte[] getSingUpForm() throws IOException {
        String filePath = "./webapp/user/form.html";
        File file = new File(filePath);
        log.info(file.getAbsolutePath());

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }

        return "No Found".getBytes();
    }

}
