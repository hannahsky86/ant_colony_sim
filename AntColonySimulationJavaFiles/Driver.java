package AntColonySimulation;
import AntColonyGUI.AntSimGUI;

public class Driver 
{
    public static void main(String [] args) 
    {
    	
        AntSimGUI simulation_gui = new AntSimGUI();
        
        Simulation simulation = new Simulation(simulation_gui);
       
        simulation_gui.addSimulationEventListener(simulation);

    }
}
