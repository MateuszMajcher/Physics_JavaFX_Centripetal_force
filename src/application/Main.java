package application;
	
import java.util.logging.Logger;

import com.sun.javafx.geom.Vec2d;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

@SuppressWarnings("restriction")
public class Main extends Application {
	private static final Logger logger = Logger.getLogger(Main.class.getName()); 
	private Stage rootStage;
	
	private GraphicsContext gc;
	
	private Fly fly;
	
	private Point2D center;
	private double mass = 900000;
	private double g = 100;
	private double Cs = 1;
	private double omega;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.rootStage = primaryStage;
			BorderPane root = new BorderPane();
			HBox hbox = addBottomControlPanel();
			Canvas canvas = addCanvas();
			root.setTop(canvas);
			root.setBottom(hbox);
			reset(canvas, Color.WHITE);
			drawDisc(canvas, 200);
			
			fly = new Fly(10, 20, Color.WHEAT, mass, 0.0, -120, center.getX() + 50, center.getY());
			fly.setAngle(
					-fly.getVelocity().lenght()/(fly.getPosition().subtract(center).lenght()));
			omega = fly.getAngle();
			logger.info(Double.toString(omega));
			fly.draw(gc);
			Scene scene = new Scene(root,500,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			startAnimation();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private HBox addBottomControlPanel() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,2,15,12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");
		
		Button buttonStart = new Button("Start");
		buttonStart.setPrefSize(100, 20);
		Button buttonReset = new Button("Reset");
		buttonReset.setPrefSize(100, 20);
		hbox.getChildren().addAll(buttonStart, buttonReset);
		
		return hbox;
	}
	
	private Canvas addCanvas() {
		Canvas canvas = new Canvas();
		canvas.setWidth(500);
		canvas.setHeight(400);
		System.out.print(canvas.getHeight());
		gc = canvas.getGraphicsContext2D();
		return canvas;
	}
	
	private void drawDisc(Canvas canvas, int size) {
		gc.setFill(Color.BLACK);
		gc.setLineWidth(150);
		gc.strokeOval((canvas.getWidth()-size)/2, (canvas.getHeight()-size)/2, size, size);
		center = new Point2D((int)canvas.getWidth()/2, (int)canvas.getHeight()/2);
	}
	
	public void reset(Canvas canvas, Color color) {
		gc.setFill(color);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public void updateScene() {
		moveObject(fly);
	}
	
	private void startAnimation() {
		final LongProperty lastUpdateTime = new SimpleLongProperty(0);
		final AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if (lastUpdateTime.get() > 0) {
					long elapsedTime = now - lastUpdateTime.get();
					updateScene();
				}
				lastUpdateTime.set(now);
			}
		};
		timer.start();
	}
	
	private static class Fly {
		private int width = 20;
		private int height = 20;
		private Color color;
		private Double mass = 1.0;
		
		//pozycja
		private int x = 0;
		private int y = 0;
		
		//przyspieszenie
		private final double xVelocity;
		private final double yVelocity;
		
		//kat
		private double angle;
	

		public Fly(int width, int height, Color color, Double mass, double xVelocity, double yVelocity, int x, int y) {
			this.width = width;
			this.height = height;
			this.color = color;
			this.mass = mass;
			this.xVelocity = xVelocity;
			this.yVelocity = yVelocity;
			this.x = x;
			this.y = y;
			this.angle = 0;
		}
		
		public Point2D getPosition() {
			return new Point2D(x, y);
		}
		
		
		public void setPosition(Point2D v) {
			this.x = v.getX();
			this.y = v.getY();
		}
		
		public Point2D getVelocity() {
			return new Point2D((int)xVelocity, (int)yVelocity);
		}
		
		
		public double getAngle() {
			return angle;
		}

		public void setAngle(double angle) {
			this.angle = angle;
		}
		
		
		
		public void draw(GraphicsContext gc) {
			gc.setFill(color);
			gc.fillRect(x, y, width, height);
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
