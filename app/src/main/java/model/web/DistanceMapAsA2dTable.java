package model.web;

import java.awt.*;

/**
 * Helper table to keep track of which nodes have been visited by search algorithms
 */
public class DistanceMapAsA2dTable implements DistanceMap {

    private double[][] visitedStatusMap;
    private int width;

    public DistanceMapAsA2dTable(int height, int width) {
        this.width = width;
        visitedStatusMap = new double[height][width];
        for (int y = 0; y < visitedStatusMap.length; y++) {
            for (int x = 0; x < visitedStatusMap[0].length; x++) {
                visitedStatusMap[y][x] = Integer.MAX_VALUE;
            }
        }

    }

    @Override
    public double getDistance(Point p) {
        return visitedStatusMap[p.y][p.x];
    }

    @Override
    public void setDistance(Point p, double weight) {
        visitedStatusMap[p.y][p.x] = weight;

    }
}
