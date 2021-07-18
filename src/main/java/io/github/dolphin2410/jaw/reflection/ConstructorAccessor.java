package io.github.dolphin2410.jaw.reflection;

import io.github.dolphin2410.jaw.util.collection.IndexedList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This works like a Constructor object in java, but it has more features, like automatically making the constructor accessible, auto infer interface and superclass types.
 *
 * @param <T> This is the class that you want to create an instance of.
 * @author dolphin2410
 */
public class ConstructorAccessor<T> {
    private final Class<T> clazz;
    private final Object instance;
    public ConstructorAccessor(Class<T> clazz) {
        this.instance = null;
        this.clazz = clazz;
    }
    public T newInstance(Object... parameters) throws ReflectionException {
        try {
            ArrayList<Class<?>> classList = new ArrayList<>();
            for (Object obj : parameters) {
                classList.add(obj == null ? null : obj.getClass());
            }
            Constructor<T> selectedConstructor = null;
            AtomicInteger passedCount = new AtomicInteger(0);
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                IndexedList.of(constructor.getParameterTypes()).forEach((it)->{
                    if(classList.size() > it.getFirst() && (classList.get(it.getFirst()) == null || it.getSecond().isAssignableFrom(classList.get(it.getFirst())))){
                        passedCount.set(passedCount.get() + 1);
                    }
                });
                if(passedCount.get() == constructor.getParameterTypes().length) {
                    @SuppressWarnings("unchecked")
                    Constructor<T> newConstructor = (Constructor<T>) constructor;
                    selectedConstructor = newConstructor;
                    break;
                }
            }
            if(selectedConstructor == null) {
                throw new NoSuchMethodException("No such method with type was found");
            }
            selectedConstructor.setAccessible(true);
            return selectedConstructor.newInstance(parameters);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ReflectionException("There was an error while getting a value using reflection.", e);
        }
    }
}