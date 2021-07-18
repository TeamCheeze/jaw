package io.github.dolphin2410.jaw.reflection;

import io.github.dolphin2410.jaw.util.collection.IndexedList;
import io.github.dolphin2410.jaw.util.collection.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class works like Method, but like Constructor accessor, it has the ability to auto infer superclass and interfaces.
 * @param <T> This is the class that you want to invoke the method from
 * @author dolphin2410
 */
public class MethodAccessor<T> {
    private final Class<T> clazz;
    private final Object instance;
    private final String methodName;
    public MethodAccessor(Class<T> clazz, String methodName) {
        this.methodName = methodName;
        this.instance = null;
        this.clazz = clazz;
    }
    public MethodAccessor(T obj, String methodName) {
        @SuppressWarnings("unchecked") Class<T> clazz = (Class<T>) obj.getClass();
        this.methodName = methodName;
        this.clazz = clazz;
        this.instance = obj;
    }
    public Object invoke(@Nullable Object... parameters) throws ReflectionException {
        try {
            ArrayList<Class<?>> classList = new ArrayList<>();
            for (Object obj : parameters) {
                classList.add(obj == null ? null : obj.getClass());
            }
            Method selectedMethod = null;
            AtomicInteger passedCount = new AtomicInteger(0);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getParameterCount() != classList.size()) {
                    continue;
                }
                for (Pair<Integer, Class<?>> it : IndexedList.of(method.getParameterTypes())) {
                    if(classList.get(it.getFirst()) == null || it.getSecond().isAssignableFrom(classList.get(it.getFirst()))){
                        passedCount.getAndIncrement();
                    }
                }
                if(passedCount.get() == method.getParameterTypes().length && method.getName().equals(this.methodName)) {
                    selectedMethod = method;
                    break;
                }
            }
            if(selectedMethod == null) {
                System.out.println("Types: ");
                for (Class<?> aClass : classList) {
                    System.out.println(aClass);
                }
                throw new NoSuchMethodException("No such method with such name and type was found: " + methodName);
            }
            selectedMethod.setAccessible(true);
            return selectedMethod.invoke(instance, parameters);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException("There was an error while getting a value using reflection.", e);
        }
    }
    public MethodAccessor<T> setDeclaringClass(@NotNull Class<?> clazz) {
        new FieldAccessor<>(this, "clazz").set(clazz);
        return this;
    }
}