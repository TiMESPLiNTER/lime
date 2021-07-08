package timesplinter.lime.unit.middleware;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.http.UriInterface;
import timesplinter.lime.http.exception.HttpMethodNotAllowedException;
import timesplinter.lime.http.exception.HttpNotFoundException;
import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.RequestHandlerContext;
import timesplinter.lime.middleware.RoutingMiddleware;
import timesplinter.lime.router.CompiledRouteInterface;
import timesplinter.lime.router.MethodNotAllowedRoutingException;
import timesplinter.lime.router.NotFoundRoutingException;
import timesplinter.lime.router.RequestHandlerInterface;
import timesplinter.lime.router.RouteContext;
import timesplinter.lime.router.RouteInterface;
import timesplinter.lime.router.RouterInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoutingMiddlewareTest {
    @Test
    public void testSuccessfulMatching() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";
        var param1Value = "foo";
        var matcherMock = Mockito.mock(Matcher.class);
        var patternMock = Mockito.mock(Pattern.class);
        var uriMock = Mockito.mock(UriInterface.class);
        var middlewareDispatcher = Mockito.mock(MiddlewareDispatcher.class);
        var routeRequestHandler = Mockito.mock(RequestHandlerInterface.class);
        var nextRequestHandler = Mockito.mock(RequestHandlerInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var routerMock = Mockito.mock(RouterInterface.class);
        var routeDefinitionMock = Mockito.mock(RouteInterface.class);
        var compiledRoute = Mockito.mock(CompiledRouteInterface.class);
        var routingMiddleware = new RoutingMiddleware(routerMock);

        Mockito.when(requestMock.getMethod()).thenReturn(httpMethod);
        Mockito.when(requestMock.getUri()).thenReturn(uriMock);
        Mockito.when(uriMock.getPath()).thenReturn(httpPath);
        Mockito.when(routerMock.match(httpMethod, httpPath)).thenReturn(compiledRoute);
        Mockito.when(compiledRoute.getParameterNames()).thenReturn(new String[]{"param1"});
        Mockito.when(compiledRoute.getCompiledPattern()).thenReturn(patternMock);
        Mockito.when(compiledRoute.getMiddlewareDispatcher(nextRequestHandler)).thenReturn(middlewareDispatcher);
        Mockito.when(middlewareDispatcher.handle(requestMock)).thenReturn(responseMock);
        Mockito.when(patternMock.matcher(httpPath)).thenReturn(matcherMock);
        Mockito.when(matcherMock.find()).thenReturn(true);
        Mockito.when(matcherMock.groupCount()).thenReturn(1);
        Mockito.when(matcherMock.group(1)).thenReturn(param1Value);
        Mockito.when(compiledRoute.getRouteDefinition()).thenReturn(routeDefinitionMock);
        Mockito.when(routeDefinitionMock.getHandler()).thenReturn(routeRequestHandler);

        var response = routingMiddleware.process(requestMock, nextRequestHandler);

        Assertions.assertSame(responseMock, response);
        Mockito.verify(middlewareDispatcher, Mockito.times(1)).handle(requestMock);
        Mockito.verify(requestMock, Mockito.times(1)).setAttribute("param1", param1Value);
        Mockito.verify(requestMock, Mockito.times(1)).setAttribute(RouteContext.ROUTE, compiledRoute);
        Mockito.verify(requestMock, Mockito.times(1)).setAttribute(
            RequestHandlerContext.REQUEST_HANDLER,
            routeRequestHandler
        );
    }

    @Test
    public void testNotFoundThrowsHttpNotFoundException() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";
        var uriMock = Mockito.mock(UriInterface.class);
        var nextRequestHandler = Mockito.mock(RequestHandlerInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);
        var routerMock = Mockito.mock(RouterInterface.class);
        var routingMiddleware = new RoutingMiddleware(routerMock);

        Mockito.when(requestMock.getMethod()).thenReturn(httpMethod);
        Mockito.when(requestMock.getUri()).thenReturn(uriMock);
        Mockito.when(uriMock.getPath()).thenReturn(httpPath);
        Mockito.when(routerMock.match(httpMethod, httpPath)).thenThrow(new NotFoundRoutingException());

        Assertions.assertThrows(
            HttpNotFoundException.class,
            () -> routingMiddleware.process(requestMock, nextRequestHandler)
        );
    }

    @Test
    public void testMethodNotAllowedThrowsHttpMethodNotAllowedException() throws Exception {
        var httpMethod = "GET";
        var httpPath = "/hello/world";
        var uriMock = Mockito.mock(UriInterface.class);
        var nextRequestHandler = Mockito.mock(RequestHandlerInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);
        var routerMock = Mockito.mock(RouterInterface.class);
        var routingMiddleware = new RoutingMiddleware(routerMock);

        Mockito.when(requestMock.getMethod()).thenReturn(httpMethod);
        Mockito.when(requestMock.getUri()).thenReturn(uriMock);
        Mockito.when(uriMock.getPath()).thenReturn(httpPath);
        Mockito.when(routerMock.match(httpMethod, httpPath)).thenThrow(new MethodNotAllowedRoutingException());

        Assertions.assertThrows(
            HttpMethodNotAllowedException.class,
            () -> routingMiddleware.process(requestMock, nextRequestHandler)
        );
    }
}
