package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.http.exception.HttpException;
import timesplinter.lime.router.RequestHandlerInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorMiddleware implements MiddlewareInterface
{
    final private ResponseFactoryInterface responseFactory;

    final private boolean displayErrorDetails;

    public ErrorMiddleware(ResponseFactoryInterface responseFactory, boolean displayErrorDetails)
    {
        this.responseFactory = responseFactory;
        this.displayErrorDetails = displayErrorDetails;
    }

    @Override
    public ResponseInterface process(RequestInterface request, RequestHandlerInterface next) throws IOException
    {
        try {
            return next.handle(request);
        } catch (Throwable e) {
            var response = this.responseFactory.create();
            var responseBody = response.getBody();
            var errorMessage = this.displayErrorDetails ? this.getTraceAsString(e) : "Internal server error";
            var statusCode = e instanceof HttpException ? ((HttpException) e).getStatusCode() : 500;

            response.setStatusCode(statusCode).setHeader("Content-Type", "text/plain");
            responseBody.write(errorMessage);

            return response;
        }
    }

    private String getTraceAsString(Throwable e)
    {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));

        return writer.toString();
    }
}
