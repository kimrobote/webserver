package kr.co.kim.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);
    private Map<String, String> headers;
    private String body;

    public RequestParser() {
        headers = new HashMap<>();
    }

    /**
     * This method return header information
     * 
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * This method return request body
     * 
     * @return
     */
    public String getBody() {
        return body;
    }

    public void parse(InputStream in) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));

        // create header info
        headers = parseHeader(buf);

        body = "";
        if (headers.containsKey(HeaderCodes.CONTENT_LENGTH) == false) {
            return;
        }

        int contentLength = Integer.parseInt(headers.get(HeaderCodes.CONTENT_LENGTH));
        if (contentLength > 1) {
            char[] bodyBuf = new char[contentLength];
            buf.read(bodyBuf);
            body = new String(bodyBuf, 0, contentLength);
        }
    }

    /**
     * Create Header Info
     * 
     * @param buf
     * @return
     * @throws IOException
     */
    private Map<String, String> parseHeader(BufferedReader buf) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        boolean isFirst = true;

        while (true) {
            String rowData = buf.readLine();
            log.info(rowData);

            if ("".equals(rowData) || rowData == null) {
                break;
            }

            String[] header = rowData.split(":");
            if (header.length == 1 && isFirst) {
                String[] firstHeaderDatas = header[0].split(" ");
                headerMap.put(HeaderCodes.METHOD, firstHeaderDatas[0]);
                headerMap.put(HeaderCodes.PATH, firstHeaderDatas[1]);
                headerMap.put(HeaderCodes.PROTOCOL, firstHeaderDatas[2]);
                isFirst = false;
            } else if (header.length == 2) {
                headerMap.put(header[0].trim(), header[1].trim());
            }
        }

        return headerMap;
    }

}
