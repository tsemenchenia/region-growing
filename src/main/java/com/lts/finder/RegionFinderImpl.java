package com.lts.finder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class RegionFinderImpl implements RegionFinder {

    private static final int MAX_COLOR_DIFFERENCE_DEFAULT_VALUE = 80;
    private static final int MIN_REGION_SIZE_DEFAULT_VALUE = 50;

    private final int maxColorDifference;
    private final int minRegionSize;

    public RegionFinderImpl() {
        this(MAX_COLOR_DIFFERENCE_DEFAULT_VALUE, MIN_REGION_SIZE_DEFAULT_VALUE);
    }

    public RegionFinderImpl(int maxColorDiff, int minRegionSize) {
        this.maxColorDifference = maxColorDiff;
        this.minRegionSize = minRegionSize;
    }

    public boolean match(Color c1, Color c2) {
        if (c2.getRed() - c1.getRed() > maxColorDifference) {
            return false;
        } else if (c2.getRed() - c1.getRed() < -maxColorDifference) {
            return false;
        } else if (c2.getBlue() - c1.getBlue() > maxColorDifference) {
            return false;
        } else if (c2.getBlue() - c1.getBlue() < -maxColorDifference) {
            return false;
        } else if (c2.getGreen() - c1.getGreen() > maxColorDifference) {
            return false;
        } else if (c2.getGreen() - c1.getGreen() < -maxColorDifference) {
            return false;
        } else {
            return true;
        }
    }

    public List<List<Point>> findRegions(BufferedImage image, Color color) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        boolean[][] visitedPoints = new boolean[imageWidth][imageHeight];

        var regions = new ArrayList<List<Point>>();
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                if (!visitedPoints[i][j]) {
                    Queue<Point> pointsToCheck = new LinkedList<>();
                    pointsToCheck.add(new Point(i, j));

                    List<Point> region = new ArrayList<>();
                    while (!pointsToCheck.isEmpty()) {
                        Point checkPoint = pointsToCheck.peek();

                        int checkPointX = (int) checkPoint.getX();
                        int checkPointY = (int) checkPoint.getY();
                        visitedPoints[checkPointX][checkPointY] = true;

                        region.add(checkPoint);

                        for (int x = Math.max(0, checkPointX - 1); x < Math.min(imageWidth, checkPointX + 2); x++) {
                            for (int y = Math.max(0, checkPointY - 1); y < Math.min(imageHeight, checkPointY + 2); y++) {

                                Color neighborColor = new Color(image.getRGB(x, y));
                                if (!visitedPoints[x][y] && match(color, neighborColor)) {
                                    pointsToCheck.add(new Point(x, y));
                                }

                                visitedPoints[x][y] = true;
                            }
                        }
                        pointsToCheck.remove();
                    }

                    if (region.size() > minRegionSize) {
                        regions.add(region);
                    }
                }
            }
        }

        return regions;
    }

}
