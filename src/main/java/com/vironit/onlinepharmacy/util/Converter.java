package com.vironit.onlinepharmacy.util;

public interface Converter<T,S> {
    T convert (S s);
}
