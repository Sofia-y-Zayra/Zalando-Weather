package org.ulpgc.dacd.model.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class ImageUtils {
    public static String imageToBase64(String imageUrl) {
        try {
            InputStream in = new URL(imageUrl).openStream();
            byte[] bytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
