package lk.ijse.crop_monitoring_systembackend.util;

import java.awt.*;
import java.util.Base64;

public class AppUtil {
    public static String toBase64Pic(byte[] pic) {
        return Base64.getEncoder().encodeToString(pic);
    }

    public static Point parseLocation(String location) {
        String[] coordinates = location.split(",");
        if (coordinates.length == 2) {
            double latitude = Double.parseDouble(coordinates[0]);
            double longitude = Double.parseDouble(coordinates[1]);
            return new Point((int) latitude, (int) longitude);
        }
        throw new IllegalArgumentException("Invalid location format");
    }
}
