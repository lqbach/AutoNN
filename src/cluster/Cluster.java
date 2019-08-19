package cluster;

import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private int dimension;
    private Point centroid;
    private double radius;

    public Cluster(ArrayList<Point> points){
        dimension = points.get(0).getDimension();
        for(Point point : points){
            if(point.getDimension() != dimension){
                throw new RuntimeException("Coordinates are not in the same dimension.");
            }
        }
        this.points = points;
        calculateCentroid();
        calculateMaxRadius();
    }

    public Cluster(ArrayList<Point> points, double radius){
        dimension = points.get(0).getDimension();
        for(Point point : points){
            if(point.getDimension() != dimension){
                throw new RuntimeException("Coordinates are not in the same dimension.");
            }
        }
        this.points = points;
        this.radius = radius;
        calculateCentroid();
    }

    public Cluster(Point point, double radius){
        dimension = point.getDimension();
        this.points = new ArrayList<Point>();
        this.points.add(point);
        this.radius = radius;
        calculateCentroid();
    }

    public double getRadius(){
        return radius;
    }

    public void addPoint(Point point){
        if(point.getDimension() != dimension){
            throw new RuntimeException("Coordinates are not in the same dimension.");
        }
        points.add(point);

        calculateCentroid();
        calculateMaxRadius();
    }

    public boolean inCluster(Point p){
        if(radius > p.calcDistance(centroid)){
            return true;
        }
        return false;
    }

    public boolean inCluster(Cluster c){
        if(this.radius > c.centroid.calcDistance(this.centroid) || c.radius > c.centroid.calcDistance(this.centroid)){
            return true;
        }
        return false;
    }

    public void combineCluster(Cluster c){
        points.addAll(c.getPoints());
        calculateCentroid();
        calculateMaxRadius(c.getRadius());
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public int getDimension(){
        return dimension;
    }

    private void calculateCentroid(){
        double [] centroidCoor = new double [dimension];
        for(Point point : points){
            for(int i = 0; i < dimension; i ++){
                centroidCoor[i] += point.getCoordinateAt(i);
            }
        }
        for(int i = 0; i < dimension; i ++){
            centroidCoor[i] /= dimension;
        }

        this.centroid = new Point(centroidCoor);
    }

    private void calculateMaxRadius(){
        double maxRadius = 0;
        for(Point point : points){
            double dist = centroid.calcDistance(point);
            if(dist >= maxRadius){
                maxRadius = dist;
            }
        }
        radius = maxRadius;
    }


    private void calculateMaxRadius(double radius){
        double maxRadius = 0;
        for(Point point : points){
            double dist = centroid.calcDistance(point);
            if(dist >= maxRadius){
                maxRadius = dist;
            }
        }
       this.radius = maxRadius + radius;
    }

}
