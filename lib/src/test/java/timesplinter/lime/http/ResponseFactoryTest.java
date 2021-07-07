package timesplinter.lime.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseFactoryTest {
    @Test
    public void testCreateReturnsNewResponseInstancesOnEachCall() {
        var responseFactory = new ResponseFactory();

        var response1 = responseFactory.create();
        var response2 = responseFactory.create();

        Assertions.assertNotSame(response1, response2);

        Assertions.assertTrue(response1 instanceof ResponseInterface);
        Assertions.assertTrue(response2 instanceof ResponseInterface);
    }
}
