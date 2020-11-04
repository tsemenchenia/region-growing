package com.lts;

import com.lts.finder.RegionFinder;
import com.lts.finder.RegionFinderImpl;
import com.lts.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Runner {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Runner.class.getClassLoader();
        URL resource = Objects.requireNonNull(classLoader.getResource("2.jpg"));
        BufferedImage sourceImage = ImageIO.read(new File(resource.getFile()));

        RegionFinder regionFinder = new RegionFinderImpl();
        List<List<Point>> regions = regionFinder.findRegions(sourceImage, new Color(255, 255, 255));

        BufferedImage coloredImage = ImageUtil.colorRegions(sourceImage, regions);

        File processedImageFile = new File("2-processed.jpg");
        ImageIO.write(coloredImage, "jpg", processedImageFile);
    }

}
