package timesplinter.example.serviceProvider;

import timesplinter.example.controller.TestController;
import timesplinter.lime.container.Container;
import timesplinter.lime.container.ServiceProviderInterface;
import timesplinter.lime.http.ResponseFactoryInterface;

final public class ControllerServiceProvider implements ServiceProviderInterface
{
    @Override
    public void register(Container container)
    {
        container.set(TestController.class.getName(), () -> new TestController(
            (ResponseFactoryInterface) container.get(ResponseFactoryInterface.class.getName())
        ));
    }
}
