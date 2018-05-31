package switchingsimulation;
/**
 * @author Mark Koshkin
 */

public class Data {
    public static double probabilityStep = 0.01;    
    public static int totalSlots;
    public static int minSlots = 0;
    public static int maxSlots = 30000;

    int totalAtA;
    int totalAtB;
    int totalAtC;
    double throughputAtA;
    double throughputAtC;
    
    
    public Data() {
        totalAtA = 0;
        totalAtB = 0;
        totalAtC = 0;
    }
    
    public void calculateThroughputs() {
        throughputAtA = (double) totalAtA / totalSlots;
        throughputAtC = (double) totalAtC / totalSlots;  
    }
}


