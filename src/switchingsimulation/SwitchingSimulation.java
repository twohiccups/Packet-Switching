/*
*
*
*
*

 .
 */
package switchingsimulation;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author XDXD
 */
public class SwitchingSimulation extends Application {
    
    ArrayList<Data> allData;  // Hold throughput at different probabilities;
    
    @Override
    public void start(Stage primaryStage) {
        
        // create the array
        allData = new ArrayList<Data>();
        
        // run the discrete simulation
        simulateOuterLoop();
        
 
        NumberAxis probabilityAxis = new NumberAxis(0,1,0.01);
        probabilityAxis.setLabel("Probability");
        NumberAxis throughputAxis  = new NumberAxis(0,1,0.01);
        throughputAxis.setLabel("Throughput");
        ScatterChart<Number, Number> chart1 = 
                new ScatterChart<Number, Number> (probabilityAxis,throughputAxis);
        chart1.setTitle("Throughput at A  vs  Probability");
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Scatter Data");
        
        for (int i = 0; i < allData.size(); i++) {
            series1.getData().add(
                    new XYChart.Data(i * 0.01, allData.get(i).throughputAtA)); 
        }
        
        chart1.getData().add(series1);
        
        
        HBox root = new HBox();
        root.getChildren().add(chart1);
        
       
        
        
        Scene scene = new Scene(root, 500, 500);
        
        primaryStage.setTitle("Discrete Probability Simulation!");
        primaryStage.setScene(scene);
       primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void simulateOuterLoop() {
        allData = new ArrayList();
        Data data;
        for (double p = 0; p <= 1; p += Data.probabilityStep) { 
            data = new Data();
            simulateInnerLoop(p, data);
            data.calculateThroughputs();
            allData.add(data);
        }  
    }
    
    // generates packets at a given timeslot
    // and updates the data class
    public void simulateInnerLoop(double probability, Data data) {
        for (int i = 0; i < Data.totalSlots; i++) {
            simulateStep(probability, data);    
        }
    }
    
    
    // generates packets at a given time slot
    // and updates the data class
    private  void simulateStep(double probability, Data data) {
        
        Boolean atLeastOneAtA = false;
        Boolean atLeastOneAtB = false;
        
        // 0,1: packets at A
        if (Math.random() < probability) {
            data.totalAtA++;  
            atLeastOneAtA = true;
        }
        else if (Math.random() < probability) {
             data.totalAtA++;  
             atLeastOneAtA = true;
        }
        // 2,3: packets at B
        if (Math.random() < probability) {
             data.totalAtB++;  
             atLeastOneAtB = true;
        }
        else if (Math.random() < probability) {
             data.totalAtB++;  
             atLeastOneAtB = true;
        }
        
        // packets at C 
        if (atLeastOneAtA || atLeastOneAtB) {
            data.totalAtC++;
        } 
    
    }
    
    
}
