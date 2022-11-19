package com.digiboy.platform.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class ResourceUtil {

    private final ObjectMapper mapper;

    public ResourceUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T readApiModel(String resourceName, Class<T> type) throws IOException {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return mapper.readValue(in, type);
        }
    }

    public String prettyJson(Object value) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    public void writeJsonFile(Object value, File file) throws IOException {
        writeJsonObject(value, new FileOutputStream(file));
    }

    public void writeJsonFile(Object value, String name) throws IOException {
        writeJsonFile(value, new File("build/" + name + ".json"));
    }

    public void printOnConsole(Object o) throws IOException {
        writeJsonObject(o, System.out);
    }

    protected void writeJsonObject(Object value, OutputStream out) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, value);
    }

    public void writeAsFile(byte[] content, String filename) throws IOException {
        try (OutputStream out = new FileOutputStream(filename)) {
            out.write(content);
        }
    }

    public void writeAsFile(String content, String filename) throws IOException {
        try (OutputStream out = new FileOutputStream(filename)) {
            out.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
