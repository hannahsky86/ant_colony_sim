package AntColonySimulation;
import java.util.*;

public class Scout extends Ant 
{
	Random rn = new Random();
	ArrayList<ColonyNode> surrounding_nodes;
	ColonyNode position;
	int random_number, size, max_ant_age;
	
	Scout() 
    {

    }
	
	Scout(ColonyNode node) 
    {
		prev_move = -1;
		position_in_colony = node;
    		max_ant_age = 3650;
    }
	
//	Scouts are responsible for enlarging the foraging area available to the foragers. The specific requirements for the scout ant are:
//	Scouts should always randomly pick one of the eight possible directions of movement when it is their turn to do something.

	//	If the chosen square is open, the scout should simply move into that square.
//	If the chosen square is closed, the scout should move into that square and the contents of that square should be
//	revealed.
	
    public void Colonating(int time_step) 
    {

        if (prev_move == time_step)
        {
        		return;
        }
        prev_move = time_step;
        
        int age_of_ant = time_step - this.current_step;
        if (age_of_ant >= max_ant_age) 
        {
        	 	position_in_colony.removeAnt(this);
            return;
        }

        setNewPositionInColony();  
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
     	surrounding_nodes = position_in_colony.getSurroundingNodes();
		size = surrounding_nodes.size();
		if(size>0) 
		{
			random_number = rn.nextInt(size);
	    		position = surrounding_nodes.get(random_number);
	    		if(position.getQueenStatus() == false)
	    		{
	    			resentAnts(position);
	    			openNode();
	    		
	    		}
    		}
    }
    
    public void resentAnts(ColonyNode position)
    {
		position_in_colony.removeAnt(this);
		position_in_colony = position;  
		position_in_colony.addAnt(this);
    }
}

