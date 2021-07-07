package timesplinter.lime.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ResponseTest {
    @Test
    public void testSetAndGetStatusCode() {
        var httpOutputStream = Mockito.mock(HttpOutputStreamInterface.class);
        var response = new Response(httpOutputStream);

        response.setStatusCode(200);

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testGetBodyReturnsOutputStream() {
        var httpOutputStream = Mockito.mock(HttpOutputStreamInterface.class);
        var response = new Response(httpOutputStream);

        Assertions.assertSame(httpOutputStream, response.getBody());
    }

    @Test
    public void testSetAndGetHeader() {
        var httpOutputStream = Mockito.mock(HttpOutputStreamInterface.class);
        var response = new Response(httpOutputStream);
        var headerName = "Content-Type";
        var headerValue = "text/plain";

        response.setHeader(headerName, headerValue);

        var responseHeaders = response.getHeaders();
        
        Assertions.assertTrue(responseHeaders.containsKey(headerName));
        Assertions.assertEquals(1, responseHeaders.get(headerName).size());
        Assertions.assertEquals(headerValue, responseHeaders.get(headerName).get(0));
    }
}
