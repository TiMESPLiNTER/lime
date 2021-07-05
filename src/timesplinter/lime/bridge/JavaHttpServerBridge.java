package timesplinter.lime.bridge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import timesplinter.lime.App;
import timesplinter.lime.http.Request;
import timesplinter.lime.http.RequestInterface;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JavaHttpServerBridge implements HttpHandler
{
    final private App app;

    public JavaHttpServerBridge(App app)
    {
        this.app = app;

    }

    public static void attach(HttpServer httpServer, App app)
    {
        var bridge = new JavaHttpServerBridge(app);
        httpServer.createContext("/", bridge);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        var request = this.createRequest(exchange);
        var response = this.app.handle(request);

        exchange.getResponseHeaders().putAll(response.getHeaders());

        var responseStream = response.getBody();

        exchange.sendResponseHeaders(response.getStatusCode(), responseStream.available());

        responseStream.transferTo(exchange.getResponseBody());

        responseStream.close();
        exchange.getResponseBody().close();
    }

    private RequestInterface createRequest(HttpExchange exchange)
    {
        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();

        return new Request(requestMethod, requestPath, Map.copyOf(exchange.getRequestHeaders()));
    }
}
