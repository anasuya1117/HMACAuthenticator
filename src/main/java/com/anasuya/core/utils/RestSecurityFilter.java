package com.anasuya.core.utils;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;


@Service
public class RestSecurityFilter {
	 
    // Enable Multi-Read for PUT and POST requests
    private static final Set<String> METHOD_HAS_CONTENT = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {
        private static final long serialVersionUID = 1L; 
        { add("PUT"); add("POST"); }
    };
    
	public boolean authenticate(String secretKey,String authCredentials, HttpServletRequest request) {
    if (null == authCredentials)

        return false;

    boolean hasContent = METHOD_HAS_CONTENT.contains(request.getMethod());
    
    //checking content type
    String contentType = request.getContentType();
    
    //checking date
    String timestamp = request.getHeader("Date");
    
    //checking uri
    String uripath = hasContent ?request.getRequestURI() : request.getRequestURI().concat("?").concat(request.getQueryString());

    //creating string to sign
    StringBuilder toSign = new StringBuilder();
    toSign.append(contentType).append(",").append(",").append(uripath).append(",").append(timestamp);
    System.out.println("String to sign " + toSign.toString());
    boolean authenticationStatus = false;
     
    String hmac = HmacSha1Signature.calculateHMAC(toSign.toString(),secretKey);
    if(authCredentials.equals(hmac))
        authenticationStatus = true;

    return authenticationStatus;
	}

}
