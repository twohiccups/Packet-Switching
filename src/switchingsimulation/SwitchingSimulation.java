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
    
    ArrayList<Data> collectedData;  // Hold throughput at different probabilities;
    
    @Override
    public void start(Stage primaryStage) {
        
        // create the array
        collectedData = new ArrayList<Data>();
        
        // run the discrete simulation
        simulateOuterLoop();
        
        // for throughput at A
        NumberAxis probabilityAxis1 = new NumberAxis(-0.1, 1.1, 0.1);
        probabilityAxis1.setLabel("Probability");
        NumberAxis throughputAxis1  = new NumberAxis(-0.1, 1.1, 0.1);
        throughputAxis1.setLabel("Throughput");
        ScatterChart<Number, Number> chart1 = 
                new ScatterChart<Number, Number> (probabilityAxis1, throughputAxis1);
        chart1.setTitle("Throughput at A  vs  Probability");
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Scatter Data");
        
        for (int i = 0; i < collectedData.size(); i++) {
            series1.getData().add(
                    new XYChart.Data(i * 0.01, collectedData.get(i).throughputAtA)); 
        }
        
        chart1.getData().add(series1);
        chart1.setPrefSize(450, 350);
        
        // for throughput at C
        
        NumberAxis probabilityAxis2 = new NumberAxis(-0.1, 1.1, 0.1);
        probabilityAxis2.setLabel("Probability");
        NumberAxis throughputAxis2  = new NumberAxis(-0.1, 1.1, 0.1);
        throughputAxis2.setLabel("Throughput");
        ScatterChart<Number, Number> chart2 = 
                new ScatterChart<Number, Number> (probabilityAxis2, throughputAxis2);
        chart2.setTitle("Throughput at C  vs  Probability");
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Scatter Data");
        
        for (int i = 0; i < collectedData.size(); i++) {
            series2.getData().add(
                    new XYChart.Data(i * 0.01, collectedData.get(i).throughputAtC)); 
        }
        
        chart2.getData().add(series2);
        chart2.setPrefSize(450, 350);
        
        
        
        HBox root = new HBox();
        root.getChildren().addAll(chart1, chart2);
        
        
        Scene scene = new Scene(root, 800, 800);
        try {
        scene.getStylesheets().add("file:src/switchingsimulation/Chart.css");
        } catch (Exception ex) {
            
        }
        
        primaryStage.setTitle("Discrete Probability Simulation!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void simulateOuterLoop() {
        collectedData = new ArrayList();
        Data data;
        for (double p = 0; p <= 1; p += Data.probabilityStep) { 
            data = new Data();
            simulateInnerLoop(p, data);
            data.calculateThroughputs();
            collectedData.add(data);
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
