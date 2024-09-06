package kr.co.kim;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8081;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if(args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // I create server socket.
        // Web Server use 8080 port for default
        try(ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {}", port);

             // Server wait until client connect
             Socket connection;
             while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
             }
        }

            
    }
}
