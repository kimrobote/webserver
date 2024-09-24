package kr.co.kim;

import org.junit.jupiter.api.BeforeEach;

public class RequestHandlerTest {
    private RequestHandler requestHandler;

    @BeforeEach
    public void setupRequestHandler() {
        requestHandler = new RequestHandler(null);
    }

}
