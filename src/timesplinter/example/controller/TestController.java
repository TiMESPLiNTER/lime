package timesplinter.example.controller;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;

import java.io.IOException;

public class TestController implements RequestHandlerInterface
{
    final private ResponseFactoryInterface responseFactory;

    final private String containerBazValue;

    public TestController(ResponseFactoryInterface responseFactory, String containerBazValue)
    {
        System.out.println("Hello, I'm a lazy controller");

        this.responseFactory = responseFactory;
        this.containerBazValue = containerBazValue;
    }

    @Override
    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        String param1 = (String) request.getAttribute("param1");

        var response = this.responseFactory.create();

        response.setStatusCode(200).setHeader("Content-Type", "text/plain");
        response.getBody().write("hello world. Param1: " + param1 + "\n");
        response.getBody().write("Something from the container: " + this.containerBazValue);

        return response;
    }
}
