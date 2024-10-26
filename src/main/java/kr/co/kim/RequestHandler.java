package kr.co.kim;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kim.controller.HomeController;
import kr.co.kim.controller.IController;
import kr.co.kim.controller.UserController;
import kr.co.kim.helper.RequestParser;
import kr.co.kim.model.ResponseData;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect | Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            // The handling of the user's request can be implemented here
            RequestParser parser = new RequestParser();
            parser.parse(in);

            ResponseData respData = createResponseBody(parser);
            ResponseHandler respHanlder = new ResponseHandler();
            respHanlder.sendResponse(out, respData);

        } catch (Exception e) {
            log.error("error", e);
        }
    }

    private ResponseData createResponseBody(RequestParser request) throws Exception {
        if (request.getHeaders().size() == 0) {
            return new ResponseData();
        }

        // int pathIndex = url.indexOf(url, 8);
        String allPath = request.getHeaders().get("Path");
        String[] paths = allPath.split("/");
        IController controller;

        if (paths.length > 2 && "user".equals(paths[1])) {
            controller = new UserController();
        } else {
            controller = new HomeController();
        }

        return controller.handleRequest(request);
    }
}
