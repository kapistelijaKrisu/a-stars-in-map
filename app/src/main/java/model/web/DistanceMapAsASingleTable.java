package model.web;

import java.awt.Point;

/**
 * Helper table to keep track of which nodes have been visited by search algorithms
 */
public class DistanceMapAsASingleTable implements DistanceMap {

    private double[] visitedStatusMap;
    private int width;

    public DistanceMapAsASingleTable(int height, int width) {
        this.width = width;
        visitedStatusMap = new double[height * width];
        for (int i = 0; i < visitedStatusMap.length; i++) {
            visitedStatusMap[i] = Integer.MAX_VALUE;
        }
    }

    @Override
    public double getDistance(Point p) {
        return visitedStatusMap[p.y * width + p.x];
    }

    @Override
    public void setDistance(Point p, double weight) {
        visitedStatusMap[p.y * width + p.x] = weight;
    }
}
