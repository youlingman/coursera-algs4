public class Brute {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new java.lang.IllegalArgumentException();
        }
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // read in points from file
        In in = new In(args[0]);
        int pointNumber = in.readInt();
        Point[] points = new Point[pointNumber];
        for (int i = 0; i < pointNumber; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        in.close();
        
        Quick.sort(points);
        
        // sort hthe plots below
        for (int i = 0; i < pointNumber - 3; i++) {
            Point p1 = points[i];
            for (int j = i + 1; j < pointNumber - 2; j++) {
                Point p2 = points[j];
                for (int k = j + 1; k < pointNumber - 1; k++) {
                    Point p3 = points[k];
                    for (int l = k + 1; l < pointNumber; l++) {
                        Point p4 = points[l];
                        if ((p1.slopeTo(p2) == p1.slopeTo(p3))
                                && (p1.slopeTo(p2) == p1.slopeTo(p4))) {
                            StdOut.println(p1 + " -> " + p2 
                                    + " -> " + p3 + " -> " + p4);
                            
                            p1.drawTo(p4);
                        }
                        
                    }
                }
            }
        }
    }
}