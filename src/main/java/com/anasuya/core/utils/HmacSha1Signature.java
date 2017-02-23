package com.anasuya.core.utils;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class HmacSha1Signature {
	public static String calculateHMAC(String data,String secret)
	{
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            String result = new String(Base64.encodeBase64(rawHmac));
            return result;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
	}
}
