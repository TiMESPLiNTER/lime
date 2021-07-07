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

            httpServer.setExecutor(null);
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

The dependency injection container allows you to define services lazily. This means they're only instantiated if and 
when they're used for the first time during runtime.

### Example
```java
var container = new Container();

// Service definition
container.set("myService", () -> {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    return simpleDateFormat.format(new Date());
});

// Service retrieval
var myService = (String) container.get("myService");
```

### Pre-compiling
There's also the option to precompile the container with the method `Container::precompile` and instantiate all services
at once instead of on the fly with their first usage. This might be useful if you anyway know that all defined services 
will be used at some point during a long-running process.

## Routing
### Placeholders
```java
// Match any value (without "/")
app.get("/test/{id}", request -> { /* ... */ });

// Match only numbers
app.get("/test/{id:\\d+}", request -> { /* ... */ });

// Match only something that starts with "foo"
app.get("/test/{id: foo.+}", request -> { /* ... */ })
```

Route matching is case-insensitive.

### Groups
```java
app.group("/user", routeCollector -> {
    routeCollector.get("", request -> { /* ... */ });
    routeCollector.post("", request -> { /* ... */ });
    routeCollector.get("/{id: \\d+}", request -> { /* ... */ });
}).add(new MyMiddleware());
```

This defines three routes:

* `GET /user`
* `POST /user`
* `GET /user/42`

and adds `MyMiddleware` to all three of them.

As you can see, groups can also have middlewares appended which only affect that very group.

## Build
```bash
$ gradle build
```

This will produce two jars (`lime-x.y.z.jar` and `lime-x.y.z-sources.jar`) in `./build/libs`.

## Test
```bash
$ gradle test
```
