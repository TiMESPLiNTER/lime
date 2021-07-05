package timesplinter.example.controller;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestController implements RequestHandlerInterface
{
    final private ResponseFactoryInterface responseFactory;

    public TestController(ResponseFactoryInterface responseFactory)
    {
        System.out.println("Hello, I'm a lazy controller");

        this.responseFactory = responseFactory;
    }

    @Override
    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        String param1 = (String) request.getAttribute("param1");

        var response = this.responseFactory.create();

        response.setStatusCode(200).setHeader("Content-Type", "text/plain");

        var responseBody =  response.getBody();

        responseBody.write("hello world. Param1: " + param1 + "\n\n");

        responseBody.write("Request URL: " + request.getUri().toString() + "\n\n");

        for (var header : request.getHeaders().entrySet()) {
            responseBody.write(
                "Header '" + header.getKey() + "', values: '" + String.join("', '", header.getValue()) + "'\n"
            );
        }

        return response;
    }
}
