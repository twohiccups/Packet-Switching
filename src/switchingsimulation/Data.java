/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package switchingsimulation;

/**
 *
 * @author XDXD
 */
public class Data {
    public final static double probabilityStep = 0.01;    
    public final static int totalSlots = 6000;

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
