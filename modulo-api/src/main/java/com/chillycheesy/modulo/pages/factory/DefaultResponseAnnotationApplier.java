package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.ResourcePage;
import com.chillycheesy.modulo.pages.response.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DefaultResponseAnnotationApplier implements ResponseAnnotationApplier {

    @Override
    public boolean applyResponseBodyAnnotation(ResponseBody responseBody, HttpServletResponse response, Page page, Object result) throws IOException {
        final String resultString = result.toString();
        try (
            final OutputStream outputStream = response.getOutputStream();
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)
        ) {
            bufferedOutputStream.write(resultString.getBytes());
            bufferedOutputStream.flush();
        }
        return true;
    }

    @Override
    public boolean applyResponseResourceAnnotation(ResponseResource responseResource, Page page, HttpServletRequest request, HttpServletResponse response, Object result) throws IOException {
        if (page instanceof final ResourcePage resourcePage) {
            final String resourcePath = result.toString();
            resourcePage.setResourcePath(resourcePath);
        }
        final ResourceResponse resourceResponse = new ResourceResponse();
        return resourceResponse.response(page, request, response);
    }

}
