package timesplinter.lime.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ResponseTest {
    @Test
    public void testResponseSetAndGetStatusCode() {
        var httpOutputStream = Mockito.mock(HttpOutputStreamInterface.class);

        var response = new Response(httpOutputStream);

        response.setStatusCode(200);

        Assertions.assertEquals(200, response.getStatusCode());
    }
}
