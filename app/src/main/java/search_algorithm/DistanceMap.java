package search_algorithm;

import java.awt.*;

/**
 * Often in search algorithms there is a table that helps keep tracking of distances. This is the distance table that offers it.
 * Helper table to keep track of which nodes have been visited by search algorithms
 * Holds an array containing array to mimic 2d
 */
public interface DistanceMap {

    /**
     *
     * @param p point of interest
     * @return distance at that point
     */
    double getDistance(Point p);

    /**
     *
     * @param p point where distance is set
     * @param weight distance value
     */
    void setDistance(Point p, double weight);
}
