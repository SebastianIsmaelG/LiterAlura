package com.aluracursos.literalura.services;

public interface IConvertData
{
    <T> T obtainData(String json, Class<T> tClass);
}
