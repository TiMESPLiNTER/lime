package timesplinter.example;

import com.sun.net.httpserver.HttpServer;
import timesplinter.example.controller.TestController;
import timesplinter.lime.App;
import timesplinter.lime.LazyRequestHandlerFactory;
import timesplinter.lime.bridge.JavaHttpServerBridge;
import timesplinter.lime.container.Container;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseFactoryInterface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyApp
{
    final private static int APP_PORT = 8989;

    public static void main(String[] args)
    {
        Container container = new Container();
        container.set("foo", c -> "bar");
        container.set("baz", c -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            return c.get("foo") + ", " + dtf.format(now);
        });
        container.set(TestController.class.getName(), c -> new TestController(
           (ResponseFactoryInterface) c.get(ResponseFactoryInterface.class.getName()),
           (String) c.get("baz")
        ));
        container.set(ResponseFactoryInterface.class.getName(), c -> new ResponseFactory());

        var responseFactory = (ResponseFactoryInterface) container.get(ResponseFactoryInterface.class.getName());

        var lazyRequestHandlerFactory = new LazyRequestHandlerFactory(container);
        
        App app = new App(container);

        app.addDefaultRoutingMiddleware();
        app.add((request, next) -> {
            System.out.println(request.getMethod() + " " + request.getPath());

            System.gc();

            long memoryBytesUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("Memory currently used: " + (memoryBytesUsed / 1024) + "KB");

            return next.handle(request);
        });

        app.get("/test/{id:\\d+}", request -> {
            int id = Integer.parseInt((String) request.getAttribute("id"));

            System.out.println("id: " + id);

            var response = responseFactory.create();

            response.setStatusCode(200).setHeader("Content-Type", "text/plain");
            response.getBody().write("This is the response");

            return response;
        });

        app.get("/foo/{param1}/baz", lazyRequestHandlerFactory.create(TestController.class.getName()));

        app.group("/group", routeCollector -> {
            routeCollector.get("/works", request -> {
                var response = responseFactory.create();

                response.setStatusCode(200).setHeader("Content-Type", "text/plain");
                response.getBody().write("The group stuff works!");

                return response;
            });
        }).add((request, next) -> {
            System.out.println("This is a middleware that applies to this group only");

            return next.handle(request);
        });

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(MyApp.APP_PORT), 0);

            JavaHttpServerBridge.attach(httpServer, app);

            httpServer.start();
        } catch (IOException e) {
            System.err.println("Could not create HTTP server: " + e.getMessage());
        }
    }
}
