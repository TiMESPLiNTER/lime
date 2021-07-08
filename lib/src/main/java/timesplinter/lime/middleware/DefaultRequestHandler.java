package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;

public class DefaultRequestHandler implements RequestHandlerInterface {
    @Override
    public ResponseInterface handle(RequestInterface request) throws Exception {
        var requestHandler = request.getAttribute(RequestHandlerContext.REQUEST_HANDLER);

        if (!(requestHandler instanceof RequestHandlerInterface)) {
            throw new RuntimeException(
                "Attribute '" + RequestHandlerContext.REQUEST_HANDLER + "' missing in request's attributes"
            );
        }

        return ((RequestHandlerInterface) requestHandler).handle(request);
    }
}
