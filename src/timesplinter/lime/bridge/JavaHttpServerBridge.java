package timesplinter.lime.bridge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import timesplinter.lime.App;
import timesplinter.lime.http.Request;
import timesplinter.lime.http.RequestInterface;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

final public class JavaHttpServerBridge implements HttpHandler
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
        try {
            var request = this.createRequest(exchange);
            var response = this.app.handle(request);

            exchange.getResponseHeaders().putAll(response.getHeaders());

            var responseStream = response.getBody();

            exchange.sendResponseHeaders(response.getStatusCode(), responseStream.available());

            responseStream.transferTo(exchange.getResponseBody());

            responseStream.close();
            exchange.getResponseBody().close();
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

    private RequestInterface createRequest(HttpExchange exchange) throws URISyntaxException
    {
        return new Request(
            exchange.getRequestMethod(),
            this.createURI(exchange),
            Map.copyOf(exchange.getRequestHeaders())
        );
    }

    private URI createURI(HttpExchange exchange) throws URISyntaxException
    {
        List<String> hostHeader = exchange.getRequestHeaders().get("Host");

        String[] host = (null == hostHeader || 0 == hostHeader.size())
                ? new String[]{"", "80"}
                : this.getHost(hostHeader.get(0));

        return new URI(
            exchange.getHttpContext().getServer() instanceof HttpsServer ? "https" : "http",
            exchange.getRequestURI().getUserInfo(),
            host[0],
            Integer.parseInt(host[1]),
            exchange.getRequestURI().getPath(),
            exchange.getRequestURI().getQuery(),
            exchange.getRequestURI().getFragment()
        );
    }

    private String[] getHost(String hostHeader)
    {
        int lastIndexOf = hostHeader.lastIndexOf(":");

        if (-1 == lastIndexOf) {
            return new String[]{hostHeader, "80"};
        }

        return new String[]{hostHeader.substring(0, lastIndexOf), hostHeader.substring(lastIndexOf+1)};
    }
}
