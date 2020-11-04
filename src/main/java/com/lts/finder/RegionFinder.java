package com.lts.finder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface RegionFinder {

    boolean colorMatch(Color c1, Color c2);

    List<List<Point>> findRegions(BufferedImage image, Color color);

}