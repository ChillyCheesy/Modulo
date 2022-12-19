package com.chillycheesy.modulo.pages.response;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.function.Function;

public class SimpleResponse implements ResponseHandler {

    private final Function<String, String> response;

    public SimpleResponse(Function<String, String> response) {
        this.response = response;
    }

    public SimpleResponse(String response) {
        this((body) -> response);
    }

    @Override
    public boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (
            final InputStream inputStream = request.getInputStream();
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final OutputStream outputStream = response.getOutputStream();
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)
        ) {
            final StringBuilder stringBuilder = new StringBuilder();
            bufferedReader.lines().forEach(stringBuilder::append);
            final String requestBody = stringBuilder.toString();
            final String resultBody = this.response.apply(requestBody);
            bufferedWriter.write(resultBody);
            return true;
        }
    }

}
