package com.lts.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.security.SecureRandom;

@UtilityClass
public class ColorUtil {

    @SneakyThrows
    public Color generateRandomColor() {
        var random = SecureRandom.getInstanceStrong();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return new Color(red, green, blue);
    }

}
