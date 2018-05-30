package AntColonySimulation;
import java.util.*;

import javax.swing.JOptionPane;

import AntColonyGUI.AntSimGUI;

public class Bala extends Ant 
{
	Random rn = new Random();
	ArrayList<ColonyNode> surrounding_nodes;
	ArrayList<Ant> good_ant_list; 
	ColonyNode position;
	int random_number, size,max_ant_age;
	AntSimGUI gui; Thread thread;
		
	Bala() 
    {

    }
	
	Bala(ColonyNode node) 
    {
		prev_move = -1;
	    	position_in_colony = node;
	    	max_ant_age = 3650;
    }

	public void Colonating(int time_step) 
    {

        if (prev_move == time_step)
        {
        		return;
        }
        prev_move = time_step;
        
//    	Once a Bala appears, it should remain in the environment until it is killed, or dies of old age.
        int age_of_ant = time_step - this.current_step;
        if (age_of_ant >= max_ant_age) 
        {
        	 	position_in_colony.removeAnt(this);
            return;
        }
        
       
        good_ant_list = position_in_colony.notABalaList();
        if (good_ant_list.size() > 0) 
        {
        		attack(good_ant_list);
        } 
        else 
        {
         	surrounding_nodes = position_in_colony.getSurroundingNodes();
         	size = surrounding_nodes.size();
         	if (size > 0) 
         	{
//         		Bala ants should always move randomly.
	         	random_number = rn.nextInt(size);
	        		position = surrounding_nodes.get(random_number); 
	            setNewPositionInColony();
         	}
        }   
    }
    
    public void attack(ArrayList<Ant> good_ant_list) 
    {
    	
//    	If a Bala ant is in a square containing one or more friendly ants (scout, forager, soldier, queen), the Bala should attack one of
//    	those ants. The ant that is attacked can be selected at random, or you can pick which ant gets attacked.
 //	    During an attack, there is a 50% chance a Bala kills the ant it attacks; otherwise, the Bala misses and the ant that is attacked
//    	survives.
    	
        int attack = rn.nextInt(101);
        if (attack <= 50) 
        {  
            if (
            		position_in_colony.getRow() == 13 
            		&& position_in_colony.getColumn() ==13 
            		&& position_in_colony.getQueenStatus() == true
            	)
            {
            		gameOver();
            } 
            else 
            {
            		good_ant_list.get(0).antDies();	
            }
        } 
    }    
    public void gameOver() 
    {
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
    public void openNode()
    {
        if (position_in_colony.getNodeStatus() == false) 
        {
        		position_in_colony.setNodeStatus(true);
        }
    }
    
    public void setNewPositionInColony() 
    { 
    		resentAnts(position);
//    	Bala ants may move into squares that have not yet been revealed by scout ants.
        openNode();
    }
    
    public void resentAnts(ColonyNode position)
    {
		position_in_colony.removeAnt(this);
		position_in_colony = position;  
		position_in_colony.addAnt(this);
    }
}

