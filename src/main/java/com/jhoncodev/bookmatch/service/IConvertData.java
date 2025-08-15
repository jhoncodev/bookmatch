package com.jhoncodev.bookmatch.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> myClass);
}
