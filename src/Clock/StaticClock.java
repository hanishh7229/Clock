package Clock;

import java.util.GregorianCalendar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
*
* @author Hanish
*/

public class StaticClock extends Application {
    GregorianCalendar calendar;             //GregorianCalendar object
    double hour;                            //The computer system hour time
    double minute;                          //The computer system minute time
    double second;                          //The computer system second time
    double clockWidth;                      //The frame background width (horizontal)
    double clockHeight;                     //The frame background height (vertical)
    double clockRadius;                     //The size of the clock (residing inside the frame background)
    double clockCenterX;                    //The center of the clock (x-coordinate of the frame midpoint)
    double clockCenterY;                    //The center of the clock (y-coordinate of the frame midpoint)
    Circle clock;                           //The clock face area
    Pane clockPane;                         //The root pane (container)
    private Timeline animation;
    private EventHandler<ActionEvent> secondUpdate;
    private EventHandler<ActionEvent> minuteUpdate;
    private EventHandler<ActionEvent> hourUpdate;

    public StaticClock() {
        //instantiate the calendar object
        calendar = new GregorianCalendar();
        setTime();            
        //setTime();
        
        //initialize the clockWidth to 250 pixels
        clockWidth = 250;

        //initialize the clockHeight to 250 pixels
        clockHeight = 250;

        //initialize the clockRadius to 50
        clockRadius = 50;

        //initialize the clockCenterX (midpoint of the clockWidth)
        clockCenterX = clockWidth / 2;

        //initialize the clockCenterY (midpoint of the clockHeight)
        clockCenterY = clockHeight / 2;

        //instantiate the clock object
        clock = new Circle(clockCenterX, clockCenterY, clockRadius);

        //instantiate the clockPane object
        clockPane = new Pane();
    }

    private void setTime() {
        calendar = new GregorianCalendar();

        //initialize the hour (use get method)
        hour = calendar.get(calendar.HOUR);

        //initialize the minute (use get method)
        minute = calendar.get(calendar.MINUTE);

        //initialize the second (use get me;thod)
        second = calendar.get(calendar.SECOND);
    }

    public void createClockFace() {

        //fill the clock face with BEIGE color
        clock.setFill(Color.BEIGE);

        //set the clock stroke with BLACK color
        clock.setStroke(Color.BLACK);

        //Create texts “3” “6” “9” and “12” and add them to the clock
        Text t3 = new Text(clockCenterX + clockRadius - 10, clockCenterY + 5, "3");
        Text t6 = new Text(clockCenterX - 5, clockCenterY + clockRadius - 5, "6");
        Text t9 = new Text(clockCenterX - clockRadius + 5, clockCenterY + 5, "9");
        Text t12 = new Text(clockCenterX - 5, clockCenterY - clockRadius + 15, "12");
        clockPane.getChildren().addAll(clock, t3, t6, t9, t12);

    }

    public void drawSecondHand() {

        //create a variable to store the length of the second hand 
        //(the length of the second hand is 80% of clock radius)
        double shandLength = clockRadius * 0.8;

        //in order to draw the second hand, we need two points. 
        //The first point (P1) is the center of the clock  i.e. clockCenterX, clockCenterY.
        //The second point (P2) is the other end of the second hand.
        // See the diagram below.                                  

        double secondX = clockCenterX + shandLength * Math.sin((second / 60) * 2 * Math.PI);
        double secondY = clockCenterY - shandLength * Math.cos((second / 60) * 2 * Math.PI);
        Line secondHand = new Line(clockCenterX, clockCenterY, secondX, secondY);
        secondHand.setStroke(Color.RED);//Calculate the P2 x-coordinate i.e. x1 + x2

        clockPane.getChildren().add(secondHand);

        secondUpdate = e -> {
            
            setTime();
            
            double secondNX = clockCenterX + shandLength * Math.sin((second / 60) * 2 * Math.PI);
            double secondNY = clockCenterY - shandLength * Math.cos((second / 60) * 2 * Math.PI);
            
            secondHand.setEndX(secondNX);
            secondHand.setEndY(secondNY);
            
        };

 

        //x1 is the clockCenterX, x2 = the length of the second hand * sin 
        //The value of angle  depends where the second hand is pointing.
        //Where the second hand is pointing is the number of seconds on the clock.
        //For example, if the second hand is pointing at 12 o’clock position
        //(upward vertically), this means the value of angle  is 0 (0/60).
        //If the second hand is pointing at 2 o’clock or at 10 seconds past,
        //the value of angle  is (10/ 60); therefore,
        //the x2 is Math.sin((second/60)* 2*Math.PI).
        //Calculate the P2 y-coordinate i.e. y1 – y2, where the length of y2 is
        //Math.cos((second/60)*2*Math.PI)
        //Remark: 2*Math.PI = 360o
        //Now, we have two points P1, and P2. We can use these two points
        //to draw a line which is the second hand.
        //Make this line the colour red and add it to the clockPane.
 
    }
    public void drawMinuteHand() {

        double mHandLength = clockRadius * 0.6;
        double minuteX = clockCenterX + mHandLength * Math.sin(((minute + (second / 60)) / 60) * 2 * Math.PI);
        double minuteY = clockCenterY - mHandLength * Math.cos(((minute + (second / 60)) / 60) * 2 * Math.PI);

        Line minuteHand = new Line(clockCenterX, clockCenterY, minuteX, minuteY);

        minuteHand.setStroke(Color.BLUE);//Calculate the P2 x-coordinate i.e. x1 + x2
        minuteHand.setStrokeWidth(2);

        clockPane.getChildren().add(minuteHand);

        minuteUpdate = e -> {
            setTime();

            double minuteNX = clockCenterX + mHandLength * Math.sin(((minute + (second / 60)) / 60) * 2 * Math.PI);
            double minuteNY = clockCenterY - mHandLength * Math.cos(((minute + (second / 60)) / 60) * 2 * Math.PI);

            minuteHand.setEndX(minuteNX);
            minuteHand.setEndY(minuteNY);
        };

        //To draw the minute hand, the idea will be exactly the same as how we draw the second hand.
        //However, the length of the minute hand will be 60% of the clock radius. The angle  will be
        //((minute + second / 60) / 60) * 2 * Math.PI.
    }

    public void drawHourHand() {
        
        double mHandLength = clockRadius * 0.4;
        double hourX = clockCenterX + mHandLength * Math.sin(((hour % 12 + (minute / 60) + (second / 3600)) / 12) * 2 * Math.PI);
        double hourY = clockCenterY - mHandLength * Math.cos(((hour % 12 + (minute / 60) + (second / 3600)) / 12) * 2 * Math.PI);

        Line hourHand = new Line(clockCenterX, clockCenterY, hourX, hourY);
        
        hourHand.setStroke(Color.BLUE);//Calculate the P2 x-coordinate i.e. x1 + x2
        hourHand.setStrokeWidth(2);

        clockPane.getChildren().add(hourHand);

        hourUpdate = e -> {
            setTime();

            double hourNX = clockCenterX + mHandLength * Math.sin(((hour % 12 + (minute / 60) + (second / 3600)) / 12) * 2 * Math.PI);
            double hourNY = clockCenterY - mHandLength * Math.cos(((hour % 12 + (minute / 60) + (second / 3600)) / 12) * 2 * Math.PI);
            
            hourHand.setEndX(hourNX);
            hourHand.setEndY(hourNY);

            //The length of the hour hand will be 40% of the clock radius, The angle  will be
            //(((hour % 12 + (minute / 60) + (second / 3600)) / 12) * 2 * Math.PI);

        };
    }

    @Override
    public void start(Stage stage) {
        createClockFace();

        drawSecondHand();
        drawMinuteHand();
        drawHourHand();

        KeyFrame secondsUpdateKf = new KeyFrame(Duration.seconds(1), secondUpdate);
        KeyFrame minuteUpdateKf = new KeyFrame(Duration.seconds(1), minuteUpdate);
        KeyFrame hourUpdateKf = new KeyFrame(Duration.seconds(1), hourUpdate);
        
        animation = new Timeline(secondsUpdateKf,minuteUpdateKf,hourUpdateKf);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setDelay(Duration.ZERO);
        animation.play();
        
        Scene scene = new Scene(clockPane, 250, 250);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class clock {
        public clock() {
        }
    }
}