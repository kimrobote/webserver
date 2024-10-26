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
import kr.co.kim.helper.HttpStatusEnum;
import kr.co.kim.helper.RequestParser;
import kr.co.kim.model.ResponseData;
import kr.co.kim.model.User;
import kr.co.kim.service.UserService;

public class UserController implements IController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService = new UserService();

    @Override
    public ResponseData handleRequest(RequestParser request) throws Exception {
        String[] paths = request.getHeaders().get(HeaderCodes.PATH).split("\\?");

        if ("/user/form.html".equals(paths[0])) {
            return getSingUpForm();
        }

        ResponseData respData = new ResponseData();

        if ("/user/create".equals(paths[0])) {
            User user = null;
            if (request.getHeaders().get(HeaderCodes.METHOD).equals(HttpMethodCode.GET)) {
                user = userService.createUser(paths[1]);
            }

            if (request.getHeaders().get(HeaderCodes.METHOD).equals(HttpMethodCode.POST)) {
                user = userService.createUser(request.getBody());
            }

            if(user != null) {
                respData.setStatus(HttpStatusEnum.HTTP302);
                respData.setRedirectUrl("/index.html");
                return respData;
            }
        }

        respData.setStatus(HttpStatusEnum.HTTP404);
        return respData;
    }

    private ResponseData getSingUpForm() throws IOException {
        String filePath = "./webapp/user/form.html";
        File file = new File(filePath);
        log.info(file.getAbsolutePath());

        ResponseData respData = new ResponseData();

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            respData.setStatus(HttpStatusEnum.HTTP200);
            respData.setBody(Files.readAllBytes(path));
            return respData;
        }

        respData.setStatus(HttpStatusEnum.HTTP404);
        return respData;
    }

}
