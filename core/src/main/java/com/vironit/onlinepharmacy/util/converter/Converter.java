package com.vironit.onlinepharmacy.util.converter;

/**
 * Converts source object to target object.
 *
 * @param <T> target object
 * @param <S> source object
 */
@FunctionalInterface
public interface Converter<T, S> {
    /**
     * Converts source object to target object.
     *
     * @param source function argument
     * @return target object
     */
    T convert(S source);
}
