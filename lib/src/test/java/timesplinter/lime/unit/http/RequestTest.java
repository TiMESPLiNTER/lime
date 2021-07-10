package timesplinter.lime.unit.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.HttpInputStreamInterface;
import timesplinter.lime.http.Request;
import timesplinter.lime.http.UriInterface;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RequestTest {
    @Test
    public void testConstructorAndGetters() {
        var uri = Mockito.mock(UriInterface.class);
        var body = Mockito.mock(HttpInputStreamInterface.class);
        var method = "GET";
        var headers = new HashMap<String, List<String>>();

        var request = new Request(method, uri, headers, body);

        Assertions.assertEquals(method, request.getMethod());
        Assertions.assertSame(uri, request.getUri());
        Assertions.assertSame(body, request.getBody());
    }

    @Test
    public void testSetAndGetHeaders() {
        var uri = Mockito.mock(UriInterface.class);
        var body = Mockito.mock(HttpInputStreamInterface.class);
        var headerName = "Content-Type";
        var headerValue = "text/plain";
        var headers = new HashMap<String, List<String>>();

        headers.put(headerName, Collections.singletonList(headerValue));

        var request = new Request("GET", uri, headers, body);

        Assertions.assertSame(headers, request.getHeaders());

        var contentTypeHeader = request.getHeader(headerName);

        Assertions.assertNotNull(contentTypeHeader);
        Assertions.assertEquals(1, contentTypeHeader.size());
        Assertions.assertEquals(headerValue, contentTypeHeader.get(0));
    }

    @Test
    public void testSetAndGetAttributes() {
        var uri = Mockito.mock(UriInterface.class);
        var body = Mockito.mock(HttpInputStreamInterface.class);
        var headers = new HashMap<String, List<String>>();
        var attributeName = "foo";
        var attributeValue = "bar";

        var request = new Request("GET", uri, headers, body);

        request.setAttribute(attributeName, attributeValue);

        Assertions.assertSame(attributeValue, request.getAttribute(attributeName));
    }
}
