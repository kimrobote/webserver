package kr.co.kim.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * This method return request body
     * @return
     */
    public String getBody() {
        return body;
    }

    public void parse(InputStream in) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));
        boolean isFirst = true;

        // create header info
        while(true) {
            String rowData = buf.readLine();
            log.info(rowData);

            String[] header = rowData.split(":");
            if(header.length == 1 && isFirst) {
                String[] firstHeaderDatas = header[0].split(" ");
                headers.put("Method", firstHeaderDatas[0]);
                headers.put("Path", firstHeaderDatas[1]);
                headers.put("Protocal", firstHeaderDatas[2]);
                isFirst = false;
            } else if(header.length == 2) {
                headers.put(header[0].trim(), header[1].trim());
            }

            if("".equals(rowData) || rowData == null) {
                break;
            }
        }
    }

}
