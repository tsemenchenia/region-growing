package com.lts.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;

@UtilityClass
public class ImageUtil {

    public BufferedImage clone(BufferedImage source) {
        ColorModel colorModel = source.getColorModel();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
    }

    public BufferedImage colorRegions(BufferedImage source, List<List<Point>> regions) {
        BufferedImage image = clone(source);
        Color regionColor = ColorUtil.generateRandomColor();

        for (List<Point> region : regions) {
            for (Point pixel : region) {
                image.setRGB(pixel.x, pixel.y, regionColor.getRGB());
            }
        }
        return image;
    }

}
