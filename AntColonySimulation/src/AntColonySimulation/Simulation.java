package AntColonySimulation;
import javax.swing.*;
import AntColonyGUI.*;

public class Simulation implements SimulationEventListener 
{

	AntSimGUI gui;
	Colony colony;
	ColonyNode colony_node;
	ColonyView colony_view;
	Thread thread;
	boolean is_moving, is_queen_alive;
	boolean init_colony_pop = false;
	int turn = 0;
   
    Simulation(AntSimGUI gui) 
    {	
    		
        is_moving = true;
        is_queen_alive = true;
        colony = new Colony(new ColonyView(27, 27), this);
        this.gui = gui;
        gui.initGUI(colony.getUpdatedColony());
        thread = null;
    }
    
    public void simulationEventOccurred(SimulationEvent simEvent) 
    {
        if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) 
        {
	    		if(init_colony_pop == false) 
	    		{
	    			initColonyPopulationFirst();
	    		}
            is_moving = false;

            thread = new Thread() 
		    	{
		        public void run() 
		        {
		        		simulationTimeIncrement();
		        }
		    };
		    thread.start();
        }
        if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) 
        {
        		if(init_colony_pop == false) 
        		{
        			initColonyPopulationFirst();
        		}
	        	is_moving = true;
	        	simulationTimeIncrement();
	    }
        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) 
        {
        		init_colony_pop = true; 
            colony.populateCenterOfColony();
        }
    }
    
    public void simulationTimeIncrement()
    {
    		do {
    			colony.nextMove(turn);
 			turn++;
 			
    			try 
			{	
			    Thread.sleep(5);
			} 
			catch (InterruptedException|NullPointerException ex) 
			{
			    Thread.currentThread().interrupt();
			}
    			
	    } while (turn<=73000 && is_queen_alive == true && is_moving == false);
    }

    public void initColonyPopulationFirst()
    {
		if (thread != null)  thread.interrupt();
		int pane = JOptionPane.showOptionDialog
    		(
    				gui,"Click Normal Setup before Run or Step, please.", 
			    "",
    				0,JOptionPane.PLAIN_MESSAGE,
                    null,null,null
                );
            
            if (pane == 0) System.exit(0);        
    }
    
    public void gameOver() 
    {
    		is_queen_alive = false;
    		if (thread != null)  thread.interrupt();
        int pane = JOptionPane.showOptionDialog
        		(
        				gui,"Game Over.\nBe nice to ants and... they'll be nice to you.", 
				" Game Over ",
				0,JOptionPane.PLAIN_MESSAGE,
                null,null,null
            );
        
        if (pane == 0) System.exit(0);        
    }
}
