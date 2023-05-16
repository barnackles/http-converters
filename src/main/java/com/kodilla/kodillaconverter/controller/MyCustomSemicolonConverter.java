package com.kodilla.kodillaconverter.controller;

import com.kodilla.kodillaconverter.domain.MyCustomClass;
import com.kodilla.kodillaconverter.domain.MySemicolonCustomClass;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;

public class MyCustomSemicolonConverter implements HttpMessageConverter<Object> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.kodillaconverter.domain.MySemicolonCustomClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.kodillaconverter.domain.MySemicolonCustomClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.ALL);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        inputMessage.getBody(),
                        StandardCharsets.UTF_8))) {
            int c = 0;

            while ((c = reader.read()) != -1)
                builder.append((char) c);
        }

        String[] fields = builder.toString().split(";");
        try {
            int num = Integer.parseInt(fields[2]);
            return new
                    MySemicolonCustomClass(fields[0], fields[1], num);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return new MySemicolonCustomClass("Invalid", "Input", -1);
    }

    @Override
    public void write(
            Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

    }
}
