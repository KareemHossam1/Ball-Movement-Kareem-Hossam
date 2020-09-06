package ball.movement.kareem.hossam;
// Importing Packages // The usage of each package is explained in the report
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
public class BallMovement extends Application {
    public double centerX= 271, centerY= 180 , moveDistance = 15;  // Variables declaration to control the ball movement
    Circle ball;
    Button btnStart;
    Button btnStop;
    Button btnReverse;
    ComboBox colors;
    ToggleGroup direction ;
    RadioButton horizontal;
    RadioButton vertical;
    Pane ballPan;
    HBox buttonsHBox;
    VBox elements;
    Scene scene;
    Alert alert;
    Timeline verticalMove;
    Timeline horizontalMove;
    @Override
    public void start(Stage primaryStage) {
        // Creating Elements
        // Creating the ball
        ball = new Circle (271,180,30);         // Create a circle its position (271,180) and its radius 30 pixel
        ball.setFill(Color.BLUE);               // Set the circle color Blue
        ball.setStroke(Color.BLACK);            // Make a black border around the circle
        //Creating 3 Buttons
        btnStart = new Button("Start");              // Create a start button
        btnStart.autosize();                         // Make its size the same as its name
        btnStop = new Button("Stop");                // Create a stop button
        btnStop.setDisable(true);                    // Disable the button by default because the ball is static and no  need for stopping it
        btnStop.autosize();                          // Make its size the same as its name
        btnReverse = new Button("Reverse");          // Create a reverse button 
        btnReverse.setDisable(true);                 // Disable the button by default because the ball is static and no need for reversing its direction
        btnReverse.autosize();                       // Make its size the same as its name
        // Creating Combo Box
        colors = new ComboBox();                                         // Create colors check box
        colors.getItems().addAll("Red","Blue","Green","Black","White");  // Add items to combo box containing colors
        colors.setPromptText("Blue");                                    // Set default chosen color
        // Creating Radio Buttons and Toggle Group
        direction = new ToggleGroup();                           // Create a toggle group to contain the radio buttons
        horizontal= new RadioButton("Horizontal");               // Create horizontal radio button
        horizontal.setToggleGroup(direction);                    // Add it to the toggle group
        vertical= new RadioButton("Vertical");                   // Create Vertical radio button
        vertical.setToggleGroup(direction);                      // Add it to the toggle group
        // Ordering elements on the scene  
        ballPan = new Pane();                                                                       // Create a new pan to hold elements
        ballPan.getChildren().addAll(ball);                                                         // put the ball on the pane
        buttonsHBox = new HBox(15);                                                                 // Create HBox to hold buttons horizontally with 15 pixels between them
        buttonsHBox.getChildren().addAll(btnStart,btnStop,btnReverse,colors,horizontal,vertical);   // Add elements to HBox
        buttonsHBox.setPadding(new Insets(0,0,10,10));                                              // Set HBox position
        elements = new VBox (290);                                                                  // create VBox to hold the ball pane and the buttons HBox together vertically
        elements.getChildren().addAll(ballPan,buttonsHBox);                                         // Add elements to VBox
        scene = new Scene(elements,530,350);                                                        // Create Scene and add the VBox and set its size to 530 Horizontally and 350 pixles Vertically
        // Movement Part
        // Horizontal Movement
        horizontalMove = new Timeline(new KeyFrame(Duration.millis(500),eventH ->  // Create a timline to repeat a specific code every 500 millis seconds
        {
            centerX+=moveDistance;                                                          // Change the centre position
            ball.setCenterX(centerX);                                                       // Move the ball to the new center
            if (ball.getCenterX() > 500 || ball.getCenterX() < 35)                          // If the ball hits the border
                moveDistance*=-1;                                                           // To reverse the direction
        }));
        // Vertical Movement
        verticalMove = new Timeline(new KeyFrame(Duration.millis(500),eventV ->    // Create a timline to repeat a specific code every 500 millis seconds
        {   
            centerY+=moveDistance;                                                          // Change the centre position
            ball.setCenterY(centerY);                                                       // Move the ball to the new center
            if (ball.getCenterY()>320|| ball.getCenterY() < 50)                             // If the ball hits the border
                moveDistance*=-1;                                                           // To reverse the direction
        }));
        // Buttons event Handler 
        // Start Button
        alert= new Alert (Alert.AlertType.ERROR);                              // Create an alert message
        btnStart.setOnAction(startEvent->                                      // Make an event handler for start button
        {     
         if(horizontal.isSelected()) {                                         // if horizontal radio button is selected do this
            horizontalMove.setCycleCount(Timeline.INDEFINITE);                 // to repeat the movement forever
            horizontalMove.play();                                             // to start horizontal movement
            verticalMove.stop();                                               // to stop vertical movement
            btnStart.setDisable(true);                                         // disable start button
            btnStop.setDisable(false);                                         // enable stop button
         }
         else if(vertical.isSelected()) {                                      // if vertical radio button is selected
            verticalMove.setCycleCount(Timeline.INDEFINITE);                   // to repeat the movement forever
            verticalMove.play();                                               // to start vertical movement
            horizontalMove.stop();                                             // to stop horizontal movement
            btnStart.setDisable(true);                                         // to disable start button
            btnStop.setDisable(false);                                         // enable stop button
         }    
         else {                                                                // If the user didn't choose direction
             alert.setTitle("Missing Direction!");                             // Message title
             alert.setHeaderText("");                                    
             alert.setContentText("You should choose the Direction firstly!"); // Message to force the user to choose direction firstly
             alert.showAndWait();                                              // Show the message
         }   
        });
        // Stop Button
        btnStop.setOnAction(b->       // Event handler for stop button
        {       
         btnStop.setDisable(true);    // disable stop button   
         btnStart.setDisable(false);  // enable start button
         horizontalMove.stop();       // stop horizontal movement
         verticalMove.stop();         // stop vertical movement
        });
        // Reverse Button
        btnReverse.setOnAction(b->{moveDistance = moveDistance*-1;});  // Reverse movement direction if the user clicked on reverse button
        // Changing ball Color
        colors.getSelectionModel().selectedItemProperty().addListener((value1,value2,value3)->{  // A listner for the combo box
        if (colors.getValue()=="Red")        // if the user selected red
        ball.setFill(Color.RED);             // change the ball color to red
        else if(colors.getValue()=="Blue")   // if the user selected blue
        ball.setFill(Color.BLUE);            // change the ball color to  blue
        else if(colors.getValue()=="Green")  // if the user selected green
        ball.setFill(Color.GREEN);           // change the ball color to green
        else if(colors.getValue()=="Black")  // if the user selected black
        ball.setFill(Color.BLACK);           // change the ball color to black
        else if(colors.getValue()=="White")  // if the user selected white
        ball.setFill(Color.WHITE);           // change the ball color to white
        });
        // Changing the direction of Movement
        direction.selectedToggleProperty().addListener(changeEvent -> {  // Create a listner for radio buttons
           if(horizontal.isSelected())                                   // if horizontal radio button is selected do this
           {                                  
            horizontalMove.setCycleCount(Timeline.INDEFINITE);           // to repeat the movement forever
            horizontalMove.play();                                       // to start horizontal movement
            verticalMove.stop();                                         // to stop vertical movement
            btnStart.setDisable(true);                                   // disable start button
            btnStop.setDisable(false);                                   // enable stop button
            btnReverse.setDisable(false);                                // enable reverse button
           }       
           else if (vertical.isSelected())                               // if vertical radio button is selected do this
           {
            verticalMove.setCycleCount(Timeline.INDEFINITE);             // to repeat the movement forever
            verticalMove.play();                                         // to start vertical movement
            horizontalMove.stop();                                       // to stop hoizontal movement
            btnStart.setDisable(true);                                   // disable start button
            btnStop.setDisable(false);                                   // enable stop button
            btnReverse.setDisable(false);                                // enable reverse button
           }
        });
        //Creating Primary Stage
        primaryStage.setResizable(false);         // To disable changing of primary stage size
        primaryStage.setTitle("Ball Movement");   // Title of the window
        primaryStage.setScene(scene);             // put the scene on primary stage
        primaryStage.show();                      // show primary stage
    }
    public static void main(String[] args) {
        launch(args);
    }
}