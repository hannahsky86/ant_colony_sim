package AntColonySimulation;

import java.util.*;

public class Soldier extends Ant 
{
	Random rn = new Random();
	ArrayList<ColonyNode> surrounding_nodes;
	ArrayList<ColonyNode>  previous_nodes;
	ArrayList<Ant> bad_ant_list; 
	ColonyNode position;
	int random_number, size, max_ant_age;
	
	Soldier() 
	{
	
	}
		
	Soldier(ColonyNode node) 
	{
		prev_move = -1;
		position_in_colony = node;
    		max_ant_age = 3650;
	    	previous_nodes = new ArrayList<ColonyNode>();
	    	previous_nodes.add(position_in_colony);
	}

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

        bad_ant_list = position_in_colony.isABalaList();
       
        if (bad_ant_list.size() > 0) 
        {
//      A soldier is in attack mode when it is in a square that contains one or more Bala ants. Attack mode takes precedence
//      over scout mode.
        		attack(bad_ant_list);
        } 
        else
        {
        		position = moveToHighestBalaCnt();  
        		setNewPosition(position);
        } 
    }

    public ColonyNode balaNode(ArrayList<ColonyNode> surrounding_nodes) 
    {
    		ColonyNode bala_node;
    		ColonyNode bala_n = null;
    		if (surrounding_nodes.size() > 0 && !surrounding_nodes.isEmpty()) 
    		{
    			bala_node = surrounding_nodes.get(0);
    			for (int index = 1; index < surrounding_nodes.size(); ++index) 
    		    {
    				
				ColonyNode this_node = surrounding_nodes.get(index);
    		        if (bala_node.getNodeStatus() == true &&
    		        		bala_node.isABalaList().size()<this_node.isABalaList().size()) 
    		        {
    		        		bala_node = this_node;
    		        }
    		    }
    			bala_n = bala_node;
    		} 	
    		return bala_n;
    }
    
    public ColonyNode moveToHighestBalaCnt() 
    {
    		ArrayList<ColonyNode> new_node_list = new ArrayList<ColonyNode>();
    		ArrayList<ColonyNode> surrounding_nodes = position_in_colony.getSurroundingNodes();
     	for(Iterator<ColonyNode> iter = surrounding_nodes.iterator(); iter.hasNext();)
     	{
            if (iter.next().getNodeStatus() == false ) 
            {
            		iter.remove();
            }
     	}
    	
		ColonyNode max_bala_node = balaNode(surrounding_nodes); 
		ColonyNode this_node; 
		ColonyNode open_node;
		int index = 0;
		while(index < surrounding_nodes.size())
        {
        		this_node = surrounding_nodes.get(index);
            if (this_node.getNodeStatus() == true 
            		&& this_node.isABalaList().size() == max_bala_node.isABalaList().size()) 
            {
            		new_node_list.add(this_node);
            }
            index++;
        }
		
	//  If there are one or more Bala ants in one or more of the squares adjacent to the square the soldier is in, the
	//  soldier should move into any one of the squares containing a Bala ant.
        if (new_node_list.size() > 0 && new_node_list.isEmpty() == false) 
        {
        		random_number = rn.nextInt(new_node_list.size());
        		max_bala_node = new_node_list.get(random_number);
        		return max_bala_node;
        } 
        else
        {
//        	If there are no Bala ants in any of the adjacent squares, the soldier should move randomly.
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
		previous_nodes.add(position_in_colony);
    }
    
    public void attack(ArrayList<Ant> bad_ant_list) 
    {       	
//    	During an attack, there is a 50% chance the soldier kills the enemy ant; otherwise, the soldier misses and the enemy
//    	ant survives
        int attack = rn.nextInt(101);
        if (attack <= 50 && bad_ant_list.size() >=1) 
        {
        		// the bad ant dies        		
        		bad_ant_list.get(0).antDies();
        }  
    }    
}


