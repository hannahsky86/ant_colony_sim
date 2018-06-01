package AntColonySimulation;

public class Ant 
{
	boolean alive;
	int ant_id, duration, current_step,  prev_move;
    ColonyNode position_in_colony;
	ColonyNode destination;
	int max_food_amt = 1000;

    public Ant() 
    {
    	
    }
    
    public Ant(ColonyNode node) 
    {
    		alive = true;
        ant_id = 0; duration = 0; prev_move = 0; 
        position_in_colony = node;
    }
    
    public void Colonating(int time_step) 
    {

    }
    
    public void setTimeSteps(int time_step) 
    {
    		current_step = time_step;
	}
   
    public int getAntAge() 
    {
        return current_step;
    }

    public void setAntId(int ant_id) 
    {
        this.ant_id = ant_id;
    }
    
    public void antDies() 
    {
        position_in_colony.removeAnt(this);
    }
    
    public void incrementQueenFood()
    {
    		position_in_colony.setFood(position_in_colony.getFood() + 1);
    }
 
}
