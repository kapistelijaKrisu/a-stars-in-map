package model.web;

import java.awt.*;

public interface DistanceMap {

    double getDistance(Point p);

    void setDistance(Point p, double weight);
}
