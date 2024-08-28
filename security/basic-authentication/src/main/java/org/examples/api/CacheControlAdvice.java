package org.examples.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Duration;

//@ControllerAdvice
public class CacheControlAdvice extends ResponseEntityExceptionHandler {

    @ModelAttribute
    public void setCacheHeaders(ServletWebRequest request, HttpServletResponse response) {
        // Define Cache-Control policy
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofMinutes(10)).mustRevalidate();
        response.setHeader("Cache-Control","max-age=600, must-revalidate");
        // Set Cache-Control header for all responses

        // Optional: Implement ETag or Last-Modified logic here if needed
        // String eTag = generateETagBasedOnResource(request);
        // responseEntityBuilder.eTag(eTag);

        // Example: Setting ETag based on request URI (this is just a placeholder logic)
        /*String eTag = Integer.toHexString(request.getRequestURI().hashCode());
        responseEntityBuilder.eTag(eTag);*/
    }
/*
    // Optional: Utility method to generate ETag (you can modify this according to your resource logic)
    private String generateETagBasedOnResource(HttpServletRequest request) {
        // This method should generate an ETag based on your resource data
        // For simplicity, we're just using the hash of the request URI
        return Integer.toHexString(request.getRequestURI().hashCode());
    }*/
}
