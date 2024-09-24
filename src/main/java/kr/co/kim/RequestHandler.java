package kr.co.kim;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.kim.controller.HomeController;
import kr.co.kim.controller.IController;
import kr.co.kim.controller.UserController;
import kr.co.kim.helper.HeaderCodes;
import kr.co.kim.helper.RequestParser;

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

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = createResponseBody(parser.getHeaders());
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (Exception e) {
            log.error("error", e);
        }
    }

    private byte[] createResponseBody(Map<String, String> headers) throws Exception {
        if (headers.size() == 0) {
            return "".getBytes();
        }

        // int pathIndex = url.indexOf(url, 8);
        String allPath = headers.get("Path");
        String[] paths = allPath.split("/");
        IController controller;

        if (paths.length > 2 && paths[1] == "user") {
            controller = new UserController();
        } else {
            controller = new HomeController();
        }

        return controller.handleRequest(headers.get(HeaderCodes.METHOD), allPath, allPath);
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
