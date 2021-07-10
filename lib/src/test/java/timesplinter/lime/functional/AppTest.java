package timesplinter.lime.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.App;
import timesplinter.lime.container.Container;
import timesplinter.lime.http.HttpInputStreamInterface;
import timesplinter.lime.http.Request;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.http.UriInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AppTest {
    @Test
    public void testSuccessfulRequest() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";

        var uriMock = Mockito.mock(UriInterface.class);
        var inputStream = Mockito.mock(HttpInputStreamInterface.class);

        Mockito.when(uriMock.getPath()).thenReturn(httpPath);

        var responseFactory = new ResponseFactory();
        var request = new Request(httpMethod, uriMock, new HashMap<>(), inputStream);
        var app = this.createApp();

        app.get(httpPath, (req) -> {
            var response = responseFactory.create().setStatusCode(200).setHeader("Content-Type", "text/plain");

            response.getBody().write("hello world");

            return response;
        });

        var response = app.handle(request);
        var responseBody = this.getBodyAsString(response);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("hello world", responseBody);
        Assertions.assertNotNull(response.getHeaders().get("Content-Type"));
        Assertions.assertEquals(1, response.getHeaders().get("Content-Type").size());
        Assertions.assertEquals("text/plain", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    public void testExceptionInRequestHandlerGetsHandledByErrorMiddleware() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";

        var uriMock = Mockito.mock(UriInterface.class);
        var inputStream = Mockito.mock(HttpInputStreamInterface.class);

        Mockito.when(uriMock.getPath()).thenReturn(httpPath);

        var request = new Request(httpMethod, uriMock, new HashMap<>(), inputStream);
        var app = this.createApp();

        app.get(httpPath, (req) -> {
            throw new RuntimeException("foo");
        });

        var response = app.handle(request);
        var responseBody = this.getBodyAsString(response);

        Assertions.assertEquals(500, response.getStatusCode());
        Assertions.assertTrue(responseBody.startsWith(
            "java.lang.RuntimeException: foo\n\tat timesplinter.lime.functional.AppTest"
        ));
        Assertions.assertNotNull(response.getHeaders().get("Content-Type"));
        Assertions.assertEquals(1, response.getHeaders().get("Content-Type").size());
        Assertions.assertEquals("text/plain", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    public void testUnsupportedMethodReturns405WithDefaultRoutingMiddleware() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";

        var uriMock = Mockito.mock(UriInterface.class);
        var inputStream = Mockito.mock(HttpInputStreamInterface.class);

        Mockito.when(uriMock.getPath()).thenReturn(httpPath);

        var request = new Request(httpMethod, uriMock, new HashMap<>(), inputStream);
        var app = this.createApp();

        app.post(httpPath, (req) -> Mockito.mock(ResponseInterface.class));

        var response = app.handle(request);
        var responseBody = this.getBodyAsString(response);

        Assertions.assertEquals(405, response.getStatusCode());
        Assertions.assertTrue(responseBody.startsWith(
            "timesplinter.lime.http.exception.HttpMethodNotAllowedException: \n" +
            "\tat timesplinter.lime.middleware.RoutingMiddleware.process"
        ));
    }

    @Test
    public void testRouteNotFoundReturns404WithDefaultRoutingMiddleware() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";

        var uriMock = Mockito.mock(UriInterface.class);
        var inputStream = Mockito.mock(HttpInputStreamInterface.class);

        Mockito.when(uriMock.getPath()).thenReturn(httpPath);

        var request = new Request(httpMethod, uriMock, new HashMap<>(), inputStream);
        var app = this.createApp();

        var response = app.handle(request);
        var responseBody = this.getBodyAsString(response);

        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertTrue(responseBody.startsWith(
                "timesplinter.lime.http.exception.HttpNotFoundException: \n" +
                        "\tat timesplinter.lime.middleware.RoutingMiddleware.process"
        ));
    }

    @Test
    public void testGroupAndMiddlewareSupport() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";

        var uriMock = Mockito.mock(UriInterface.class);
        var inputStream = Mockito.mock(HttpInputStreamInterface.class);

        Mockito.when(uriMock.getPath()).thenReturn("/foo" + httpPath);

        var responseFactory = new ResponseFactory();
        var request = new Request(httpMethod, uriMock, new HashMap<>(), inputStream);
        var app = this.createApp();

        app.group("/foo", (routeCollector) -> {
            routeCollector.get(httpPath, (req) -> {
                var response = responseFactory.create().setStatusCode(200).setHeader("Content-Type", "text/plain");

                response.getBody().write("hello world");

                return response;
            });
        }).addMiddleware((req, next) -> {
            var response = next.handle(req);

            response.getBody().write("!!middleware!!");

            return response;
        });

        var response = app.handle(request);
        var responseBody = this.getBodyAsString(response);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("hello world!!middleware!!", responseBody);
        Assertions.assertNotNull(response.getHeaders().get("Content-Type"));
        Assertions.assertEquals(1, response.getHeaders().get("Content-Type").size());
        Assertions.assertEquals("text/plain", response.getHeaders().get("Content-Type").get(0));
    }

    private App createApp() {
        var container = new Container();
        var app = new App(container);

        app.addDefaultRoutingMiddleware();
        app.addDefaultErrorMiddleware(true);

        return app;
    }

    private String getBodyAsString(ResponseInterface response) throws IOException {
        var byteStream = new ByteArrayOutputStream();

        response.getBody().transferTo(byteStream);

        return byteStream.toString();
    }
}
