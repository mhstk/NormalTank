import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Collision {
    public static boolean   isIntersectingTowLine(Point a, Point b, Point c, Point d) {
        float denominator = ((b.x - a.x) * (d.y - c.y)) - ((b.y - a.y) * (d.x - c.x));
        float numerator1 = ((a.y - c.y) * (d.x - c.x)) - ((a.x - c.x) * (d.y - c.y));
        float numerator2 = ((a.y - c.y) * (b.x - a.x)) - ((a.x - c.x) * (b.y - a.y));

        // Detect coincident lines (has a problem, read below)
//        System.out.println("1 : "+numerator1 +"     2 : "+ numerator2 + "   ");
        if (denominator == 0) return ((numerator1 == 0) && (numerator2 == 0));

        float r = numerator1 / denominator;
        float s = numerator2 / denominator;

        return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);

    }

    public static boolean intersectALineAndAShape(Point a , Point b , Point l , Point m , Point n , Point o){
        if(isIntersectingTowLine(a,b,l,m)){
            System.out.println("wwwwwwwwwwwwwwwwwwww1");
            return true ;
        }
        if(isIntersectingTowLine(a,b,m,n)){
            System.out.println("wwwwwwwwwwwwwwwwwwww2");
            return true ;
        }
        if(isIntersectingTowLine(a,b,n,o)){
            System.out.println("wwwwwwwwwwwwwwwwwwww3");
            return true ;
        }
        if(isIntersectingTowLine(a,b,o,l)){
            System.out.println("wwwwwwwwwwwwwwwwwwww4");
            return true ;
        }
        else {
            return false;
        }
    }

    public static boolean intersectTowShape (Point a , Point b , Point c , Point d , Point l , Point m , Point n , Point o){
        if(intersectALineAndAShape(a,b,l,m,n,o)){
            System.out.println("1");
            return true ;
        }
        if(intersectALineAndAShape(b,c,l,m,n,o)){
            System.out.println("2");
            return true ;
        }
        if(intersectALineAndAShape(c,d,l,m,n,o)){
            System.out.println("3");
            return true ;
        }
        if(intersectALineAndAShape(d,a,l,m,n,o)){
            System.out.println("4");
            return true ;
        }
        else {
            return false;
        }
    }

    public static Point rotatePoint(Point point , double angel , Point origin){
        point.x -= origin.x ;
        point.y -= origin.y ;
        Point nPoint = new Point((int)(point.x * Math.cos(angel) - point.y * Math.sin(angel)) , (int)(point.x * Math.sin(angel) + point.y * Math.cos(angel))) ;
        nPoint.x += origin.x ;
        nPoint.y += origin.y ;
        return  nPoint ;
    }

    public static boolean intersect(Shape shape1 , Shape shape2 , double angel1 , double angel2 , Graphics2D g2d){
        Point a = new Point(shape1.getBounds().x  , shape1.getBounds().y  );
        Point b = new Point(a.x + shape1.getBounds().width , a.y);
        Point d = new Point(a.x , a.y + shape1.getBounds().height  );
        Point c = new Point(a.x + shape1.getBounds().width  , a.y + shape1.getBounds().height );

        b = rotatePoint(b,angel1,new Point(a.x+shape1.getBounds().width/2 , a.y+shape1.getBounds().height/2)) ;
        c = rotatePoint(c,angel1,new Point(a.x+shape1.getBounds().width/2 , a.y+shape1.getBounds().height/2)) ;
        d = rotatePoint(d,angel1,new Point(a.x+shape1.getBounds().width/2 , a.y+shape1.getBounds().height/2)) ;
        a = rotatePoint(a,angel1,new Point(a.x+shape1.getBounds().width/2 , a.y+shape1.getBounds().height/2)) ;


        Point l = new Point(shape2.getBounds().x  , shape2.getBounds().y);
        Point m = new Point(l.x + shape2.getBounds().width , l.y);
        Point n = new Point(l.x , l.y + shape2.getBounds().height );
        Point o = new Point(l.x + shape2.getBounds().width  , l.y + shape2.getBounds().height );

        m = rotatePoint(m,angel2,new Point(l.x+shape2.getBounds().width/2 , l.y+shape2.getBounds().height/2)) ;
        n = rotatePoint(n,angel2,new Point(l.x+shape2.getBounds().width/2 , l.y+shape2.getBounds().height/2)) ;
        o = rotatePoint(o,angel2,new Point(l.x+shape2.getBounds().width/2 , l.y+shape2.getBounds().height/2)) ;
        l = rotatePoint(l,angel2,new Point(l.x+shape2.getBounds().width/2 , l.y+shape2.getBounds().height/2)) ;

        g2d.setColor(Color.GREEN);
        g2d.fillOval(a.x,a.y,5,5);
        g2d.setColor(Color.GREEN);
        g2d.fillOval(b.x,b.y,5,5);
//        g2d.fillOval(c.x,c.y,5,5);
//        g2d.fillOval(d.x,d.y,5,5);
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(l.x,l.y,5,5);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(m.x,m.y,5,5);
//        g2d.setColor(Color.WHITE);
//        g2d.fillOval(n.x,n.y,5,5);
//        g2d.fillOval(o.x,o.y,5,5);
//        g2d.setColor(Color.BLACK);

        if(isIntersectingTowLine(a,b,l,m)){
            System.out.println("dcvrev");
        }


        if(intersectTowShape(a,b,c,d,l,m,n,o)){
            return true ;
        }else {
            return false;
        }

    }
}

