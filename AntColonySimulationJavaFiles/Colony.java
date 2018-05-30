package AntColonySimulation;
import java.util.*;
import AntColonyGUI.*;

public class Colony 
{

	ColonyNode colony_node, colony_node_array[][];
    ColonyNodeView colony_node_view;
	ColonyView colony_view;
    Simulation simulation;
    Random rn = new Random();
    int random_number;
    int initial_food_amt = 1000;
    
    Colony(ColonyView colony_view, Simulation simulation) 
    {
        colony_node_array = new ColonyNode[27][27];
        this.colony_view = colony_view;
        this.simulation = simulation;
    }
    
/*===============================================================================
	GETTER METHODS
=================================================================================*/        
 // Opens a new node in the colony. 	
    public void openNewNodeInTheColony(ColonyNode colony_node, int row, int column) 
    {
    		colony_node_array[row][column] = colony_node;
    }
	public ColonyView getUpdatedColony() 
	{
		return colony_view;
	}
    public void nextMove(int time_step) 
    {
        for (int row = 0; row < 27; row++) 
        {
            for (int column = 0; column < 27; column++) 
            {
            		colony_node_array[row][column].Colonating(time_step);
            }
        }
    }

/*===============================================================================
	POPULATE NODE METHODS
=================================================================================*/    
// Populate the first nine squares in the colony. 
    public void populateCenterOfColony() 
    {
        for (int row = 0; row < 27; row++) 
        {
            for (int column = 0; column < 27; column++) 
            {
            	    initializeNodes(colony_node_view,row, column);  
            	  
                colony_node.setColonyNode(this);
               
                /*The center square represents the entrance to the colony. It should contain the following:
				1. the queen ant
				2. 10 soldier ants
				3. 50 forager ants
				4. 4 scout ants
				5. 1000 units of food*/
                if (row == 13 && column == 13) 
                {
                		initializeCenterNodesWithAntsAndFood();
                }
                // Make these nodes visible. 
    	    			makeCenterNodesVisible(row, column);
            }
    		}
	}
    public void initializeCenterNodesWithAntsAndFood()
    {
     	Queen queen_ant = new Queen(colony_node);
		colony_node.addAnt(queen_ant);
		
		Ant forager_ant = new Forager(colony_node);
		Ant scout_ant = new Scout(colony_node);
		Ant soldier_ant = new Soldier(colony_node);
		
		for(int forager_cnt = 0 ; forager_cnt < 50; forager_cnt++) 
		{
			queen_ant.makeAntBaby(forager_ant);
		}
		
		for(int solider_cnt = 0 ; solider_cnt < 10; solider_cnt++) 
		{
			queen_ant.makeAntBaby(soldier_ant);
		}

		for(int scout_cnt = 0 ; scout_cnt < 4; scout_cnt++) 
		{
			queen_ant.makeAntBaby(scout_ant);
		}
		
		colony_node.setFood(initial_food_amt);
    }
    
    public void makeCenterNodesVisible(int row, int column) 
    {
        if ((row > 11 && row < 15) && (column > 11 && column < 15)) 
        {
            colony_node.setNodeStatus(true);
        }	    
    }

    public void initializeNodes(ColonyNodeView colony_node_view, int row, int column) 
    {
        colony_node_view = new ColonyNodeView();
        colony_node = new ColonyNode(colony_node_view, row, column);
        colony_view.addColonyNodeView(colony_node_view, row, column);
        openNewNodeInTheColony(colony_node, row, column);
        colony_node_view.setID(row + "," + column);
    }
	 
//  get surrounding nodes 
  public ArrayList<ColonyNode> getSurroundingNodes(ColonyNode colony_node) 
  {
	int row = colony_node.getRow();
	int column = colony_node.getColumn();
	ArrayList<ColonyNode> surroundingNodes; 
	surroundingNodes= new ArrayList<ColonyNode>();
	
	for (int next_row = -1; next_row <=1; next_row++)
	{			
		for (int next_column = -1; next_column <=1; next_column++)
		{	
			if (next_row == 0 && next_column ==0) 
			{
				continue;
			}	
			int new_row = row + next_row;
			int new_col = column + next_column;
			if (new_row<27 && new_col<27 && new_row>-1 && new_col>-1 ) 
			{
				surroundingNodes.add(colony_node_array[new_row][new_col]);
			}
		}
	}
      return surroundingNodes;
  }
}
