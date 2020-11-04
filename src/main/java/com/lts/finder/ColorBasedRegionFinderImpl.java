package com.lts.finder;

import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ColorBasedRegionFinderImpl implements RegionFinder {

    private static final Color DEFAULT_REGION_COLOR = new Color(255, 255, 255);
    private static final int MAX_COLOR_DIFFERENCE_DEFAULT_VALUE = 80;
    private static final int MIN_REGION_SIZE_DEFAULT_VALUE = 50;

    @Setter
    private int maxColorDifference;

    @Setter
    private int minRegionSize;

    @Setter
    private Color regionColor;

    public ColorBasedRegionFinderImpl() {
        this(MAX_COLOR_DIFFERENCE_DEFAULT_VALUE, MIN_REGION_SIZE_DEFAULT_VALUE);
    }

    public ColorBasedRegionFinderImpl(int maxColorDiff, int minRegionSize) {
        this.regionColor = DEFAULT_REGION_COLOR;
        this.maxColorDifference = maxColorDiff;
        this.minRegionSize = minRegionSize;
    }

    public List<List<Point>> findRegions(BufferedImage image) {
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
                                if (!visitedPoints[x][y] && isMatched(neighborColor)) {
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

    private boolean isMatched(Color neighbourPointColor) {
        if (neighbourPointColor.getRed() - regionColor.getRed() > maxColorDifference) {
            return false;
        }
        if (neighbourPointColor.getRed() - regionColor.getRed() < -maxColorDifference) {
            return false;
        }
        if (neighbourPointColor.getBlue() - regionColor.getBlue() > maxColorDifference) {
            return false;
        }
        if (neighbourPointColor.getBlue() - regionColor.getBlue() < -maxColorDifference) {
            return false;
        }
        if (neighbourPointColor.getGreen() - regionColor.getGreen() > maxColorDifference) {
            return false;
        }
        return neighbourPointColor.getGreen() - regionColor.getGreen() >= -maxColorDifference;
    }

}
