package com.example.musica.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
