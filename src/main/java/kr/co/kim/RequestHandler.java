package kr.co.kim;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect | Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            // The handling of the user's request can be implemented here
            readRequestHeaderInfo(in);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World Good".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
            
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void readRequestHeaderInfo(InputStream in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));
        while(true) {
            String rowData = buf.readLine();
            log.info(rowData);

            String[] header = rowData.split(":");
            if(header.length == 0) {
            } else {
                headers.put(header[0].trim(), header[1].trim());
            }

            

            if("".equals(rowData) || rowData == null) {
                break;
            }
        }
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

    private void response200Header(DataOutputStream dos, int  lengthOfBodyContent) {
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
