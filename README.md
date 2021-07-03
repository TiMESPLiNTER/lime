# Lime - An HTTP micro framework for Java

Lime is heavily inspired by the popular micro framework Slim for PHP.

## Get started

This example app configuration will create an app instance handling `GET` requests for route `/test/{id}` using Java's 
built-in HTTP server.

```java
public class MyApp
{
    final private static int APP_PORT = 8989;
    
    public static void main(String[] args)
    {
        // Create a dependency container instance
        Container container = new Container();

        // Create a response factory instance
        ResponseFactoryInterface responseFactory = new ResponseFactory();
        
        // Instantiate the app
        App app = new App(container);

        // Add default routing middleware to route incoming requests
        app.addDefaultRoutingMiddleware();

        app.get("/test/{id}", request -> {
            int id = Integer.parseInt((String) request.getAttribute("id"));

            var response = responseFactory.create();

            response.setStatusCode(200).setHeader("Content-Type", "text/plain");
            response.getBody().write("This is the response for id: " + id);

            return response;
        });
        
        try {
            // Create a Java built-in HTTP server instance,
            // listen on port 8989 and connect it to the app instance
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(MyApp.APP_PORT), 0);

            JavaHttpServerBridge.attach(httpServer, app);

            httpServer.start();
        } catch (IOException e) {
            System.err.println("Could not create HTTP server: " + e.getMessage());
        }
    }
}
```

Requests to `GET http://localhost:8989/test/42` will now be answered with:

```
HTTP/1.1 200 OK
Date: Sat, 03 Jul 2021 08:55:24 GMT
Content-length: 32
Content-Type: text/plain

This is the response for id: 42
```

## Middleware support

## Dependency injection container
