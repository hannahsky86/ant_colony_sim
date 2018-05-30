package AntColonySimulation;
import java.util.*;

public class Forager extends Ant 
{
	Random rn = new Random();
	ArrayList<ColonyNode>  previous_nodes;
	ColonyNode position;
	ColonyNode this_position;
	ColonyNode max_ph;
	ArrayList<ColonyNode> has_food_in_node;
	int random_number, size, previous_node, max_ant_age;
	boolean retrace_steps;
	
	Forager() 
    {

    }
	
	Forager(ColonyNode node) 
    {
		prev_move = -1;
	    	position_in_colony = node;
	    	max_ant_age = 3650;
	    	previous_nodes = new ArrayList<ColonyNode>();
	    	previous_nodes.add(position_in_colony);
	    	retrace_steps = false;    
	    	previous_node = previous_nodes.size()-1;
    }

    public void Colonating(int time_step) 
    {
    		ColonyNode move_to;
        if (prev_move == time_step)
        {
        		return;
        }
        
        prev_move = time_step; 
        int age_of_ant = time_step - this.current_step;
        if (age_of_ant >= max_ant_age) 
        {
        	 	position_in_colony.removeAnt(this);
        	 	position_in_colony.setFood(position_in_colony.getFood()+1);
            return;
        }

        if (retrace_steps == true)
        {
	  		previous_node--;
	  		move_to = previous_nodes.get(previous_node);
	  		setNewPosition(move_to);
//		    	If the ant has found food and is returning home, increment each node by ten pheromones.  
	  		if (
					 position_in_colony.getRow() != 13 && position_in_colony.getColumn() != 13 
					 && position_in_colony.getPheromone() < 1000
					 && retrace_steps == true
			 ) 
	  		{
	  				position_in_colony.setPheromone(position_in_colony.getPheromone() + 10);
	        }
//	    	Once the ant has reached home, reset retrace_steps to false and increment Queen's food by 1.
		}
		else
		{
	  		move_to = moveToHighestPherCnt();
	  		setNewPosition(move_to);
// 	 		If the ant is still looking for food, once the ant has found food decrease food amount by 1.
	  		if (
	  				position_in_colony.getRow() != 13 && position_in_colony.getColumn() != 13 
	  				&& position_in_colony.getFood() > 0
	  				&& retrace_steps == false
		        ) 
	  			{
		        		position_in_colony.setFood(position_in_colony.getFood() - 1);
		            retrace_steps = true;
		            previous_node = previous_nodes.size() - 1;
	  			}
  			}
    		}

    public ColonyNode pherNode(ArrayList<ColonyNode> surrounding_nodes) 
    {
    		ColonyNode pher_node;
    		ColonyNode pher_n = null;
    		if (surrounding_nodes.size() > 0 && !surrounding_nodes.isEmpty()) 
    		{
    			pher_node = surrounding_nodes.get(0);
    			for (int index = 1; index < surrounding_nodes.size(); ++index) 
    		    {
    				
				ColonyNode this_node = surrounding_nodes.get(index);
    		        if (pher_node.getNodeStatus() == true &&
    		        		pher_node.getPheromone()<this_node.getPheromone()) 
    		        {
    		        		pher_node = this_node;
    		        }
    		    }
    			pher_n = pher_node;
    		} 	
    		return pher_n;
    }
    
    public ColonyNode moveToHighestPherCnt() 
    {
    		ArrayList<ColonyNode> new_node_list = new ArrayList<ColonyNode>();
    		ArrayList<ColonyNode> surrounding_nodes = position_in_colony.getSurroundingNodes();
    		
//    	Remove nodes that are not open
//      Remove nodes that have already been visited
    		for(Iterator<ColonyNode> iter = surrounding_nodes.iterator(); iter.hasNext();)
     	{
            if (iter.next().getNodeStatus() == false) 
            {
            		iter.remove();
            }
            if (previous_nodes.contains(iter.next()))
            {
            		iter.remove();
            }
     	}
     	
//    	If new node equals the node with the max pheromones then add it to the new_node_list
    		ColonyNode max_pher_node = pherNode(surrounding_nodes);
    		ColonyNode this_node; 
    		ColonyNode open_node;
    		int index = 0;
		while(index < surrounding_nodes.size())
        {
        		this_node = surrounding_nodes.get(index);
            
        		if (this_node.getNodeStatus() == true 
            		&& this_node.getPheromone() == max_pher_node.getPheromone()) 
            {
            		new_node_list.add(this_node);
            }
        		index++;
        }
	
        if (new_node_list.size() > 0 && new_node_list.isEmpty() == false) 
        {
        		random_number = rn.nextInt(new_node_list.size());
        		max_pher_node = new_node_list.get(random_number);
        		return max_pher_node;
        } 
        else
        {
	    		random_number = rn.nextInt(surrounding_nodes.size());
	    		open_node = surrounding_nodes.get(random_number);
	    		return open_node;
        }	
    }
      
    public void setNewPosition(ColonyNode position) 
    {	   
	
    		position_in_colony.removeAnt(this);
		position_in_colony = position;  
		position_in_colony.addAnt(this);
		
	 	if (
         		position_in_colony.getRow() == 13 
         		&& position_in_colony.getColumn() == 13
         		&& retrace_steps == true
           ) 
	       {
	         	 incrementQueenFood();
	             previous_nodes.clear();
	             retrace_steps = false;
	       }

    	 	previous_nodes.add(position_in_colony);
   }
}
