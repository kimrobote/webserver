package kr.co.kim.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kim.helper.HttpStatusEnum;
import kr.co.kim.helper.RequestParser;
import kr.co.kim.model.ResponseData;

public class HomeController implements IController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Override
    public ResponseData handleRequest(RequestParser request) throws Exception {
        String filePath = "./webapp/index.html";
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
