package AntColonySimulation;
import java.util.*;

import AntColonyGUI.ColonyNodeView;

public class ColonyNode 
{
    int row, column, food, pheromone, count;
    boolean is_queen_ant, is_node_open, is_colonating, is_starting_point;
    ArrayList<Ant> antList, antBirth, antDeath;
    ArrayList<Ant> antListByType;
    ArrayList<Ant> antListNoBala;
    ColonyNodeView cn_view;
    Colony colony;
    Random rn = new Random();
    int random_number;
    ColonyNode(ColonyNodeView cn_view, int row, int column) 
    {
        food = 0;
        food = generateRandomFoodNumber();
        this.pheromone = generateRandomPheromoneNumber();
        this.row = row; this.column = column; count = 0;
        is_queen_ant = false; is_node_open = false; is_starting_point = false;        
        antList = new ArrayList<Ant>(); antBirth = new ArrayList<Ant>(); antDeath = new ArrayList<Ant>(); 
        this.cn_view = cn_view;  
    }
/*===============================================================================
	GETTER METHODS
=================================================================================*/
  	public int getFood()
	{	
		return food;
	}
	public int getPheromone() 
	{
		return pheromone;
	}
	public int getRow()
	{
		return row;
	}
	public int getColumn()
	{
		return column;
	}
    public boolean getQueenStatus() 
    {
        return is_queen_ant;
    }
    	public ArrayList<ColonyNode> getSurroundingNodes() 
    	{
    		return colony.getSurroundingNodes(this) ;
    	}
    public boolean getNodeStatus() 
    {
        return is_node_open;
    }  
    public ArrayList<Ant> getAntList() 
    {
        return antBirth;
    }   
/*===============================================================================
    	SETTER METHODS
=================================================================================*/
    public void setFood(int food_amt) 
    {
        food = food_amt;
    }
    public void setPheromone(int pheromone_amt) 
    {
        pheromone = pheromone_amt;
    }
	public void setColonyNode(Colony colony) 
	{
		this.colony = colony;
	}
    public void updateNode() 
    {
        this.cn_view.showNode();
    } 
    public void setBalaCount() 
    {
    		Ant bala = new Bala();
    		int number_of_bala_ants = countNumAntsOfType(bala);
    		cn_view.setBalaCount(number_of_bala_ants);
    		displayBala(number_of_bala_ants);
    }        
    public void setForagerCount() 
    {
    		Ant forager = new Forager();
    		int number_of_forager_ants = countNumAntsOfType(forager);
    		cn_view.setForagerCount(number_of_forager_ants);
    		displayForager(number_of_forager_ants);
    }
    public void setSoldierCount() 
    {
    		Ant soldier = new Soldier();
    		int number_of_soldier_ants = countNumAntsOfType(soldier);
    		cn_view.setSoldierCount(number_of_soldier_ants);
    		displaySoldier(number_of_soldier_ants);
    }
    public void setScoutCount() 
    {
    		Ant scout = new Scout();
    		int number_of_scout_ants = countNumAntsOfType(scout);
    		cn_view.setScoutCount(number_of_scout_ants);
    		displayScout(number_of_scout_ants);
    }   
	public void displayBala(int count)
	{
		if (count > 0)
        {
			cn_view.showBalaIcon();
        } else {
        		cn_view.hideBalaIcon();
        }
	}
    public void displayForager(int count) 
    {
    		if (count > 0)
        {
    			cn_view.showForagerIcon();
        } else {
        		cn_view.hideForagerIcon();
        }
    }
    public void displaySoldier(int count) 
    {
        if (count > 0) 
        {
        		cn_view.showSoldierIcon();
        } else {
        		cn_view.hideSoldierIcon();
        }	
    }
    public void displayScout(int count) 
    {
        if (count > 0)
        {
        		cn_view.showScoutIcon();
        } else {
        		cn_view.hideScoutIcon();
        }
    }
/*===============================================================================
    UPDATE METHODS
=================================================================================*/
	public void updateColonyDisplay() 
	{
		Ant queen = new Queen();
		boolean is_ant_alive = countNumAntsOfType(queen) == 1;
	    if (is_ant_alive) 
	    {
	    		is_queen_ant = true;
	    		cn_view.setQueen(is_queen_ant);
	    		cn_view.showQueenIcon();
	    }
	    setBalaCount();
	    setForagerCount();
	    setScoutCount();
	    setSoldierCount(); 

	    	cn_view.setFoodAmount(food);	
	    cn_view.setPheromoneLevel(pheromone); 
	}  
    public int countNumAntsOfType(Ant ant) 
    {
        int count = 0, i = 0, size = antList.size();
        if(size>0 && ant.getClass() != null) 
        {
	        while (i< size) 
	        {
	            if (antList.get(i).getClass() ==  ant.getClass()) 
	            {
	                count++;
	            }
	            i++;
	        }
        }
        return count;
    }
	public void updateListOfAnts() 
	{
		antBirth();
		antDeath();
		antBirth.clear();
		antDeath.clear();
	}
	public void antBirth() 
	{
		try 
		{
			for (Ant ants: antBirth) 
			{
			    antList.add(ants);
			}
		} catch (ConcurrentModificationException e) 
		{
			
		}
	}
	public void antDeath() 
	{
		try 
		{
			for (Ant ants: antDeath) 
			{
				antList.remove(ants);
			}
		} catch (ConcurrentModificationException e) 
		{
			
		}
	}
    public void Colonating(int time_step) 
    {
    		boolean first_time_step = time_step == 0;
    		boolean tenth_time_step = time_step%10 == 0;
    	
    		if (tenth_time_step)
    		{
    			cn_view.setTimeCount(1+time_step/10);
    		}
    		
        if (tenth_time_step == true && first_time_step == false) 
        {
        		this.setPheromone(getPheromone()/2);  
        }
        
        this.setPheromone(getPheromone());
        is_colonating = true;
        try 
        {
	        for (Ant ant: antList) 
	        {
	            ant.Colonating(time_step);
	        }
	        is_colonating = false;
	        updateListOfAnts();
	        updateColonyDisplay();
        } 
        catch (ConcurrentModificationException e) 
        {
        	
        }
    }
	public int generateRandomFoodNumber()
	{		
		
//		There is a 25% chance that the square will contain a random amount of food between 500 and 1000 units.
//		The other 75% of the time the square is empty.
		
//		You can predetermine the contents of all the squares at the beginning of the simulation, or you can dynamically
//		determine the contents of each square as it is opened.
		
		random_number = rn.nextInt(101);
		if (random_number < 26)
		{
			food = rn.nextInt(500) + 501;
		} 
//		75% chance square will be empty
		return food;
	}
	public int generateRandomPheromoneNumber()
	{
//		rn.nextInt(1001);
		pheromone = rn.nextInt(1000) +1;

		return pheromone;
	}
	public void setNodeStatus(boolean is_node_visible)
	{
		is_node_open = is_node_visible;
		if (is_node_open) 
		{
			cn_view.showNode();
		}
		if (!is_node_open) 
		{
			cn_view.hideNode();
		}
	}
    public void addAnt(Ant ant) 
    {
        if (is_colonating == true) 
        {
        		antBirth.add(ant);
        } else {
            antList.add(ant);
        }
        updateColonyDisplay();
    }
    public void removeAnt(Ant ant) 
    {
        if (is_colonating == true) 
        {
        		antDeath.add(ant);
        } else {
            antList.remove(ant);
        }
        updateColonyDisplay();
    }
    public ArrayList<Ant> notABalaList() 
    {
        ArrayList<Ant> friendly_ant = new ArrayList<Ant>();
//    		try 
//		{ 
	        for (int i = 0; i < antList.size(); i++) 
	        {
	        		Ant bala = new Bala();
	            if (antList.get(i).getClass() != bala.getClass()) 
	            {
	            		friendly_ant.add(antList.get(i));
	            }
	        }
	        return friendly_ant;
//	    }	
//    		catch (NullPointerException e) 
//		{
//			return friendly_ant;
//		}
    }
    public ArrayList<Ant> isABalaList() 
    {
    	 	ArrayList<Ant> enemy_ant = new ArrayList<Ant>();
//    		try 
//    		{   
	        for (int i = 0; i < antList.size(); i++) 
	        {
	        		Ant bala = new Bala();
	            if (antList.get(i).getClass() == bala.getClass()) 
	            {
	            		enemy_ant.add(antList.get(i));
	            }
	        }
	        return enemy_ant;
//    		}
//    		catch (NullPointerException e) 
//    		{
//    			return enemy_ant;
//    		}
    } 
    public ArrayList<Integer> hasPherInNode() 
    {
    	 	ArrayList<Integer> has_pheromone_in_node = new ArrayList<Integer>();
	 	for (int i = 0; i < getSurroundingNodes().size(); i++)
	    {
	 	   if(getSurroundingNodes().get(i).getPheromone() > 0)
	 	   {
	 		   has_pheromone_in_node.add(getSurroundingNodes().get(i).getPheromone());
	 	   }
	    } 
	 	return has_pheromone_in_node;
    }
    public ArrayList<Integer> hasFoodInNode() 
    {
    	 	ArrayList<Integer> has_food_in_node = new ArrayList<Integer>();
	 	for (int i = 0; i < getSurroundingNodes().size(); i++)
	    {
	 	   if(getSurroundingNodes().get(i).getFood() > 0)
	 	   {
	 		  has_food_in_node.add(getSurroundingNodes().get(i).getFood());
	 	   }
	    } 
	 	return has_food_in_node;
    }
}
