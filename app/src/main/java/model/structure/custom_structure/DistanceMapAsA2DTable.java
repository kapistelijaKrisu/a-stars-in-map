package model.structure.custom_structure;

import model.structure.structure_interface.DistanceMap;

import java.awt.*;

/**
 * Often in search algorithms there is a table that helps keep tracking of distances. This is the distance table that offers it.
 * Helper table to keep track of which nodes have been visited by search algorithms
 * Holds array in array to mimic 2d.
 * To keep it lightweight no error checks are done.
 */
public class DistanceMapAsA2DTable implements DistanceMap {

    private double[][] visitedStatusMap;

    /**
     * Creates a double type array and instantiates all their values to integer.maxvalue
     * To keep it lightweight no error checks are done.
     *
     * @param height height of map
     * @param width  width of map
     */
    public DistanceMapAsA2DTable(int height, int width) {
        visitedStatusMap = new double[height][width];
        for (int y = 0; y < visitedStatusMap.length; y++) {
            for (int x = 0; x < visitedStatusMap[0].length; x++) {
                visitedStatusMap[y][x] = Integer.MAX_VALUE;
            }
        }

    }

    /**
     * To keep it lightweight no error checks are done.
     *
     * @param p point of interest
     * @return distance at that point
     */
    @Override
    public double getDistance(Point p) {
        return visitedStatusMap[p.y][p.x];
    }

    /**
     * To keep it lightweight no error checks are done.
     *
     * @param p      point where distance is set
     * @param weight distance value
     */
    @Override
    public void setDistance(Point p, double weight) {
        visitedStatusMap[p.y][p.x] = weight;

    }
}
