package ec.medinamobile.bakify.libs;

/**
 * Created by Erick on 9/1/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
