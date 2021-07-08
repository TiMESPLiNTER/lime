package timesplinter.lime.unit.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.container.Container;
import timesplinter.lime.container.ServiceDefinitionInterface;
import timesplinter.lime.container.ServiceProviderInterface;

public class ContainerTest {
    @Test
    public void testSetAndHasAndGetService() {
        var serviceId = "myService";
        var serviceObject = Math.random();
        var container = new Container();

        container.set(serviceId, () -> serviceObject);

        Assertions.assertTrue(container.has(serviceId));
        Assertions.assertTrue(container.get(serviceId) instanceof Double);
        Assertions.assertSame(container.get(serviceId), container.get(serviceId));
    }

    @Test
    public void testProtectPreservesServiceDefinition() {
        var serviceId = "myService";
        ServiceDefinitionInterface serviceObject = () -> "foo";
        var container = new Container();

        container.set(serviceId, container.protect(serviceObject));

        Assertions.assertSame(serviceObject, container.get(serviceId));
    }

    @Test
    public void testExtendExtendsServiceDefinition() {
        var serviceId = "myService";
        var serviceObject = 42;
        var container = new Container();

        container.set(serviceId, () -> serviceObject);

        container.extend(serviceId, (prev) -> ((int) prev) + 8);

        Assertions.assertEquals(50, container.get(serviceId));
    }

    @Test
    public void testRegisterCallsServiceProviderAndRegistersServices() {
        var serviceProviderMock = Mockito.mock(ServiceProviderInterface.class);
        var container = new Container();

        var returnedContainer = container.register(serviceProviderMock);

        Assertions.assertSame(container, returnedContainer);
        Mockito.verify(serviceProviderMock, Mockito.times(1)).register(container);
    }

    @Test
    public void testPrecompileInitiatesService() {
        var serviceObjectMock = Mockito.mock(ServiceDefinitionInterface.class);
        var serviceId = "myService";
        var container = new Container();

        Mockito.when(serviceObjectMock.define()).thenReturn("foo");
        container.set(serviceId, serviceObjectMock);

        container.precompile();

        Mockito.verify(serviceObjectMock, Mockito.times(1)).define();
    }
}
