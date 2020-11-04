package com.lts.finder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface RegionFinder {

    List<List<Point>> findRegions(BufferedImage image);

}