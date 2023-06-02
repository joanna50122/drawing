package drawings;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

//class Point {
//	private final int x;
//	private final int y;
//  
//  getters(for each component, you will get a getter with smae name)
//  public int x(){ return x;}
//  public int y(){ return y;}
//
//	Point(int x, int y) {
//		this.x = x;
//		this.y = y;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		return obj instanceof Point p && x == p.x && y == p.y;
//	}
//
//	
//	@Override
//	public int hashCode() {
//		//return x + 31 * y;
//		return Objects.hash(x,y);
//	}
//
//	@Override
//	public String toString() {
//		return "Point[x=" + x + ", y=" + y + "]";
//	}
//}

record Point(int x, int y) {}

abstract class Shape extends Object {
	abstract String toSVG(); // overriden method
}

class Circle extends Shape {
	Point center;
	int radius;
	Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	String toSVG() { // overriding method
		return "<circle x=\"" + this.center.x() + "\" y=\"" + this.center.y() +
				"\" r=\"" + this.radius + "\">";
	}
}

class Polygon extends Shape {
	Point[] vertices;
	Polygon(Point[] vertices) {
		this.vertices = vertices.clone();
	}
	
	@Override
	String toSVG() {
		String result = "<polygon points=\"";
		for (Point p : this.vertices) {
			result += p.x() + " " + p.y();
		}
		result += ">";
		return result;
	}
}

class Drawing {
	Shape[] shapes;
	Drawing(Shape[] shapes) { this.shapes = shapes.clone(); }
	//turn a drawing into SVG string
	String toSVG() {
		String result = "<svg xmlns=\"http://w3c.org/2000/SVG\"";
		for (Shape shape : shapes) {
			result += shape.toSVG(); // dynamically bound method call
			
//			if (shape instanceof Circle circle) { // pattern match expression
//				result += circle.toSVG();
//				} else if (shape instanceof Polygon polygon){
//				result += polygon.toSVG();
//			} else {
//				throw new AssertionError("Unexpected case");
//			}
		}
		result += "</svg>";
		foo(); // statically bound method call
		return result;
	}
	
	static int foo() {
		return 42;
		}
	}

class DrawingsTest {

	@Test
	void test() {
		Drawing myDrawing = new Drawing(new Shape[] {
			new Circle(new Point(10, 20), 5),
			new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)})
		});
		assertEquals("<svg xmlns=\"http://w3c.org/2000/SVG\"<circle x=\"10\" y=\"20\" r=\"5\"><polygon points=\"10 2020 2020 10></svg>",
				myDrawing.toSVG());
		
		assertNotEquals(new Point(10, 20), new Point(10,30)); 
		Object o1 = new Point(10, 20);
		Object o2 = new Point(10, 20);
		assertTrue(o1.equals(o2)); // dynamically bound method call
		
		// 'shape' is a polymorphic variable: it can point to objects of different types
		Shape shape = new Circle(new Point(10, 20), 5);
		shape = new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)});
	
		Object x = 10; // autoboxing
		assertTrue(x instanceof Integer);
		int i = (int)x; // auto-unboxing
		
		Object x2 = new int[] {10, 20, 30};
		Object[] objects = new Shape[] {
				new Circle(new Point(10, 20), 5),
				new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)})
		};
		//objects[0] = "Hello!";	// ArrayStoreException	
		
	}

}
