// Shape.java
public interface Shape {
    // Common method for all shapes
    void draw();
}

// Line.java
public class Line implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a line");
    }
}

// Circle.java
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

// Application.java
public class Application {
    public static void main(String[] args) {
        DrawingStrategy drawingStrategy = new DrawingStrategy();
        
        Shape line = new Line();
        drawingStrategy.setDrawingStrategy(new LineDrawingStrategy());
        drawingStrategy.drawShape(line);
        
        Shape circle = new Circle();
        drawingStrategy.setDrawingStrategy(new CircleDrawingStrategy());
        drawingStrategy.drawShape(circle);
    }
}

// DrawingStrategy.java
public class DrawingStrategy {
    private DrawingStrategyInterface drawingStrategy;

    public void setDrawingStrategy(DrawingStrategyInterface drawingStrategy) {
        this.drawingStrategy = drawingStrategy;
    }

    public void drawShape(Shape shape) {
        drawingStrategy.drawShape(shape);
    }
}

// DrawingStrategyInterface.java
public interface DrawingStrategyInterface {
    void drawShape(Shape shape);
}

// LineDrawingStrategy.java
public class LineDrawingStrategy implements DrawingStrategyInterface {
    @Override
    public void drawShape(Shape shape) {
        System.out.println("Using LineDrawingStrategy");
        shape.draw();
    }
}

// CircleDrawingStrategy.java
public class CircleDrawingStrategy implements DrawingStrategyInterface {
    @Override
    public void drawShape(Shape shape) {
        System.out.println("Using CircleDrawingStrategy");
        shape.draw();
    }
}
