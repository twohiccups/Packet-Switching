package switchingsimulation;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/** @author Mark Koshkin **/
public class SwitchingSimulation extends Application {
    
    ArrayList<Data> collectedData;  // Hold throughput at different probabilities;
    
    @Override
    public void start(Stage primaryStage) {
        
        VBox root = new VBox();
        
        root.setPadding(new Insets(15,15,15,15));
        Label label = new Label(
                  "The following program simulates system that consists of input nodes (A, B) and one\n"
                + "output node (C). At a time slot, up to two packeges can arrive to each of the input\n"
                + "nodes. The packets are then forwarded to the output node which accepts up to two packets\n"
                + "and discards the remaining. At each time slot the probability a package arriving is p.\n"
                + "The probability is varied with an increment \"of 0.01. Then the throughput at the nodes\n"
                +  "A and C are plotted against the probability.\n");
        
        Image image = new Image("switchingsimulation/switchnodes.png");
        ImageView imageView = new ImageView(image);
        
        Slider slider = new Slider();
        slider.setMax(Data.maxSlots);
        slider.setMin(Data.minSlots);
        slider.setTooltip(new Tooltip("Number of Time Slots"));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(slider.getMax()/4);
        slider.setBlockIncrement(5);
       
        
        Button button = new Button("Simulate");
        
        button.setOnAction(e -> {
            Data.totalSlots = (int)slider.getValue();
            simulate(new Stage());
        });
        
        
        
        
        root.getChildren().addAll(label, imageView, slider, button);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    public void simulate(Stage primaryStage) {
        
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
        scene.setOnMouseClicked(e -> {
            primaryStage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    // Runs simulations at given probabilities
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
    
    // Generates N time slots
    private void simulateInnerLoop(double probability, Data data) {
        for (int i = 0; i < Data.totalSlots; i++) {
            simulateStep(probability, data);    
        }
    }
    
    
    // Generates packets randomly at a given time slot
    // and updates the data class
    private  void simulateStep(double probability, Data data) {
        
        Boolean atLeastOneAtA = false, atLeastOneAtB = false;
        
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
