package com.chillycheesy.modulo.pages.response;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ParameterResponse implements ResponseHandler {

    private final Function<RequestParameters, String> response;

    public ParameterResponse(Function<RequestParameters, String> response) {
        this.response = response;
    }

    @Override
    public boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Function<String, String> responseFunction = (body) -> {
            final RequestParameters requestParameters = new RequestParameters(request, body);
            return this.response.apply(requestParameters);
        };
        final SimpleResponse simpleResponse = new SimpleResponse(responseFunction);
        return simpleResponse.response(page, request, response);
    }
}
