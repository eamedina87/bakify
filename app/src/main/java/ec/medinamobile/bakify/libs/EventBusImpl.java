package ec.medinamobile.bakify.libs;

/**
 * Created by Erick on 9/1/2016.
 */
public class EventBusImpl implements EventBus {
    private org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder{
        private static final EventBusImpl INSTANCE = new EventBusImpl();
    }

    public static EventBusImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public EventBusImpl() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
