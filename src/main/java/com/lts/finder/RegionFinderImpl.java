package com.lts.finder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RegionFinderImpl implements RegionFinder {

    private static final int maxColorDiff = 80;
    protected static final int minRegion = 50;

    public boolean colorMatch(Color c1, Color c2) {
        if (c2.getRed() - c1.getRed() > maxColorDiff) {
            return false;
        } else if (c2.getRed() - c1.getRed() < -maxColorDiff) {
            return false;
        } else if (c2.getBlue() - c1.getBlue() > maxColorDiff) {
            return false;
        } else if (c2.getBlue() - c1.getBlue() < -maxColorDiff) {
            return false;
        } else if (c2.getGreen() - c1.getGreen() > maxColorDiff) {
            return false;
        } else if (c2.getGreen() - c1.getGreen() < -maxColorDiff) {
            return false;
        } else {
            return true;
        }
    }

    public List<List<Point>> findRegions(BufferedImage image, Color color) {
        var regions = new ArrayList<List<Point>>();
        boolean[][] checkedPoints = new boolean[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) { //for all columns
            for (int j = 0; j < image.getHeight(); j++) { //and all rows
                if (!checkedPoints[i][j]) { //if we haven't checked the point yet
                    Point startPoint = new Point(i, j); //get that point
                    List<Point> toCheck = new ArrayList<>(); //create a list of points we need to visit
                    toCheck.add(startPoint); //add our start point to the arraylist of points we need to visit
                    List<Point> ourRegion = new ArrayList<>(); //create a region to hold our points
                    while (!toCheck.isEmpty()) { //while we still have points to check
                        Point checkPoint = toCheck.get(0); //get the first point we need to check
                        ourRegion.add(checkPoint); //add it to our region
                        checkedPoints[(int) checkPoint.getX()][(int) checkPoint.getY()] = true;


                        for (int x = Math.max(0, (int) checkPoint.getX() - 1); x < Math.min(image.getWidth(), checkPoint.getX() + 2); x++) { //for the neighboring columns
                            for (int y = Math.max(0, (int) checkPoint.getY() - 1); y < Math.min(image.getHeight(), checkPoint.getY() + 2); y++) { //and the neighboring rows
                                Color neighborColor = new Color(image.getRGB(x, y)); //get its color
                                if (colorMatch(color, neighborColor) && !checkedPoints[x][y]) { //if the color matches and the point is unchecked
                                    Point neighborPoint = new Point(x, y); //get that point
                                    toCheck.add(neighborPoint); //add that point to the points we need to check
                                }
                                checkedPoints[x][y] = true;
                            }
                        }
                        toCheck.remove(0); //remove the point were just were using as a center for neighbors
                    }
                    if (ourRegion.size() > minRegion) { //if it is large enough
                        regions.add(ourRegion); //add it to our array of regions
                    }
                }
            }
        }

        return regions;
    }

}
