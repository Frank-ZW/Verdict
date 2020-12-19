package me.frankthedev.verdict.util.java;

import com.google.common.base.Joiner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionUtil {

    @SuppressWarnings("unused")
    private static <T> Constructor<T> constructor(Class<T> class_, Class<?> ... arrclass) {
        try {
            Constructor<T> constructor = class_.getDeclaredConstructor(arrclass);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException(noSuchMethodException);
        }
    }

    @SuppressWarnings("unused")
    private static <T> T fetchConstructor(Constructor<T> constructor, Object ... arrobject) {
        try {
            return constructor.newInstance(arrobject);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Field access(Class class_, String string) {
        try {
            Field field = class_.getDeclaredField(string);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException noSuchFieldException) {
            throw new IllegalArgumentException(class_.getSimpleName() + ":" + string, noSuchFieldException);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Field access(Class class_, String ... arrstring) {
        for (String string : arrstring) {
            try {
                Field field = class_.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {}
        }
        throw new IllegalArgumentException(class_.getSimpleName() + ":" + Joiner.on((String)",").join((Object[])arrstring));
    }

    @SuppressWarnings({ "unchecked"})
    public static <T> T fetch(Field field, Object object) {
        try {
            return (T)field.get(object);
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    @SuppressWarnings({ "rawtypes"})
    public static <T> void setLocalField(Class class_, Object object, String string, T t) {
        ReflectionUtil.set(ReflectionUtil.access(class_, string), object, t);
    }

    public static <T> void set(Field field, Object object, T t) {
        try {
            field.set(object, t);
        } catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T> T getLocalField(Class class_, Object object, String string) {
        return ReflectionUtil.fetch(ReflectionUtil.access(class_, string), object);
    }
}
