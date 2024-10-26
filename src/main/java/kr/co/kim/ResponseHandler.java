package kr.co.kim;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import kr.co.kim.model.ResponseData;

public class ResponseHandler {
    
    /**
     * send response result to client
     * @param out
     * @param respData
     * @throws IOException
     */
    public void sendResponse(OutputStream out, ResponseData respData) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        List<String> header = createHeader(respData);

        writeHeader(dos, header);

        writeBody(dos, respData);

        dos.flush();
    }

    private List<String> createHeader(ResponseData respData) {
        ArrayList<String> header = new ArrayList<>();
        int lengthOfBodyContent = respData.getContentLength();

        switch (respData.getStatus()) {
            case HTTP200:
                header.add("HTTP/1.1 200 OK \r\n");
                header.add("Content-Type: text/html;charset=utf-8\r\n");
                header.add("Content-Length: " + lengthOfBodyContent + "\r\n");
                break;
            case HTTP302:
                header.add("HTTP/1.1 302 Found \r\n");
                header.add("Location: " + respData.getRedirectUrl() + "\r\n");
                break;
            default:
                 header.add("HTTP/1.1 404 Not Found \r\n");
                 header.add("Content-Type: text/html;charset=utf-8\r\n");
                break;
        }

        return header;
    }

    /**
     * writer header info to reponse OutputStream
     * @param dos
     * @param header
     * @throws IOException 
     */
    private void writeHeader(DataOutputStream dos, List<String> header) throws IOException {
        for (String headerValue : header) {
            dos.writeBytes(headerValue);
        }
        dos.writeBytes("\r\n");
    }

    /**
     * writer body to response OUtputStream
     * @param dos
     * @throws IOException 
     */
    private void writeBody(DataOutputStream dos, ResponseData respData) throws IOException {
        if(respData.getContentLength() > 0) {
            dos.write(respData.getBody(), 0, respData.getContentLength());
            dos.writeBytes("\r\n");
        }
    }

}
