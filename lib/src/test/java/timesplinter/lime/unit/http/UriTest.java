package timesplinter.lime.unit.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.Uri;

import java.net.URI;

public class UriTest {
    @Test
    public void testGetters() {
        var host = "localhost";
        var scheme = "http";
        var port = 80;
        var path = "/foo/bar";
        var query = "hello=world&world=hello";
        var urlStr = scheme + "://" + host + ":" + port + path + query;

        var netUriMock = Mockito.mock(URI.class);
        Mockito.when(netUriMock.getScheme()).thenReturn(scheme);
        Mockito.when(netUriMock.getHost()).thenReturn(host);
        Mockito.when(netUriMock.getPort()).thenReturn(port);
        Mockito.when(netUriMock.getPath()).thenReturn(path);
        Mockito.when(netUriMock.getQuery()).thenReturn(query);
        Mockito.when(netUriMock.toString()).thenReturn(urlStr);

        var uri = new Uri(netUriMock);

        Assertions.assertEquals(host, uri.getHost());
        Assertions.assertEquals(scheme, uri.getScheme());
        Assertions.assertEquals(port, uri.getPort());
        Assertions.assertEquals(path, uri.getPath());
        Assertions.assertEquals(query, uri.getQuery());
        Assertions.assertEquals(urlStr, uri.toString());
    }
}
