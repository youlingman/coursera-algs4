import java.util.Arrays;

public class Fast {
    private static void printLine(Point[] points, Point current, int head, int count) {
        Point[] printPoints = new Point[count + 1];
        printPoints[0] = current;
        for (int i = 0; i < count; i++)
            printPoints[i + 1] = points[i + head];
        Quick.sort(printPoints);
        if (printPoints[0] != current)
            return;
        StdOut.print(printPoints[0]);
        for (int i = 1; i <= count; i++)
            StdOut.print(" -> " + printPoints[i]);
        StdOut.println();
        printPoints[0].drawTo(printPoints[count]);
    }
    
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
        
        // copy points to work around
        Point[] pointsBack = new Point[pointNumber];
        for (int i = 0; i < pointNumber; i++)
            pointsBack[i] = points[i];
        
        // sort and print
        for (int i = 0; i < pointNumber; i++) {
            Point current = pointsBack[i];
            //sort by slope to i
            Arrays.sort(points, 0, points.length, current.SLOPE_ORDER);
            //for (int j = 0; j < pointNumber; j++)
            //    StdOut.print(current.slopeTo(points[j]) + ",");
            //StdOut.println();
            int count = 1;
            for (int j = 2; j < pointNumber; j++) {
                if (current.slopeTo(points[j]) == current.slopeTo(points[j-1]))
                    count++;
                else {
                    if (count >= 3)
                        printLine(points, current, j - count, count);
                    count = 1;
                }
            }
            if (count >= 3)
                printLine(points, current, pointNumber - count, count);
        }
    }
}