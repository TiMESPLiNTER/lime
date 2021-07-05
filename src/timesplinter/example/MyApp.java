package timesplinter.example;

import com.sun.net.httpserver.HttpServer;
import timesplinter.example.controller.TestController;
import timesplinter.example.serviceProvider.ControllerServiceProvider;
import timesplinter.example.serviceProvider.HttpServiceProvider;
import timesplinter.lime.App;
import timesplinter.lime.LazyRequestHandlerFactory;
import timesplinter.lime.bridge.JavaHttpServerBridge;
import timesplinter.lime.container.Container;
import timesplinter.lime.http.ResponseFactoryInterface;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyApp
{
    final private static int APP_PORT = 8989;

    public static void main(String[] args)
    {
        var container = new Container();

        container
            .register(new ControllerServiceProvider())
            .register(new HttpServiceProvider())
        ;

        var responseFactory = (ResponseFactoryInterface) container.get(ResponseFactoryInterface.class.getName());
        var lazyRequestHandlerFactory = new LazyRequestHandlerFactory(container);
        
        var app = new App(container);

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

            httpServer.setExecutor(null); // creates a default executor (Executors.newFixedThreadPool(5))
            httpServer.start();
        } catch (IOException e) {
            System.err.println("Could not create HTTP server: " + e.getMessage());
        }
    }
}
