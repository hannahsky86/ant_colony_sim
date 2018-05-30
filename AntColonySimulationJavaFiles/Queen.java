package AntColonySimulation;
import java.util.*;


/*
The queen never moves from her square (i.e., she remains in the same square for the entire simulation).
2. The queen's maximum lifespan is 20 years.
3. The queen hatches new ants at a constant rate of 1 ant/day (i.e., 1 ant every 10 turns).
4. New ants should always be hatched on the first turn of each day.
The type of ant that is hatched should be determined randomly according to the initial frequencies listed below. You may
change these frequencies as you see fit — these are simply suggestions for a starting point.
5.
a. Forager - 50%
b. Scout - 25%
c. Soldier - 25%
6. The queen should consume 1 unit of the food in her chamber on each turn, including the turn in which she hatches a new ant.
7. If the food level in the queen's square is zero when the queen tries to eat, the queen dies of starvation.
8. If the queen dies, either by starvation or by a Bala attack, the simulation should end immediately.
 * 
 * */
public class Queen extends Ant 
{
	
	int ant_id, prev_move_id, time_step; 
	int queen_max_age = 73000-1;
	//365 days/year * 10 turns/day * 20 years/life (excludes leap years)
	
	int food_amt;
	boolean tenth_turn;
	ColonyNode enemy_location;
	Random rn = new Random();
	int random_number; 
	
    Queen() 
    {

    }
    
    public Queen(ColonyNode node) 
    {
    		ant_id = 0; prev_move_id = 0;
    		position_in_colony = node;
    }
    
    public void Colonating(int time_step) 
    {
    		this.time_step = time_step;
        deathConditions(time_step);
		tenth_turn = time_step%10 == 0;
		if (tenth_turn) 
		{
			this.makeAntBaby(null);
		} 

		makeBalaBaby(); 
        this.foodStorage();
    } 

    public void makeAntBaby(Ant ant) 
    {
    		random_number = rn.nextInt(101);
    		Ant ant_baby = typeOfBaby(random_number);
        
    		if (ant != null)  
        {
    			ant_baby = ant;
        }
    		ant_baby.setTimeSteps(time_step);
        prev_move_id++;
        ant_baby.setAntId(prev_move_id);
        position_in_colony.addAnt(ant_baby);

    }
    
    public void makeBalaBaby() 
    {
        enemy_location = position_in_colony.colony.colony_node_array[0][0];
        Random rn = new Random();
//		Each turn there is a 3% chance one Bala ant will appear in one of the squares at the boundary of the colony. You may choose
//		to have Bala ants always enter at the same square (e.g., upper left corner), or you may have them enter randomly at any of
//		the 106 squares on the edge of the colony.
        if (rn.nextInt(101) < 4)
        {
        	 	Bala new_bala = new Bala(enemy_location);
            
            new_bala.setTimeSteps(time_step);
            enemy_location.addAnt(new_bala);
            
            prev_move_id++;
            new_bala.setAntId(prev_move_id);
        }
    }
    
//    The type of ant that is hatched should be determined randomly according to the initial frequencies listed below. You may
//    change these frequencies as you see fit — these are simply suggestions for a starting point.
//    5.
//    a. Forager - 50%
//    b. Scout - 25%
//    c. Soldier - 25%
    public Ant typeOfBaby(int random_number) 
    {	
    		Ant ant_baby = null;
    		
        if ( random_number >= 50 && random_number < 101) 
        {
        		ant_baby = new Forager(position_in_colony);
        } 
      
        if ( random_number >= 0 && random_number < 25)
        {
        		ant_baby = new Soldier(position_in_colony);
        } 

        if ( random_number >= 25 && random_number < 50)
         
        {
        		ant_baby = new Scout(position_in_colony);
        }
        
        return ant_baby;
    }
    
    public void deathConditions(int time_step)
    {
    		food_amt = position_in_colony.getFood();
    		
    		if (food_amt< 1 ||time_step > queen_max_age)
        {
        		position_in_colony.cn_view.hideQueenIcon();
            position_in_colony.colony.simulation.gameOver();
            return;
        }
    }
    
    public void foodStorage() 
    {
        food_amt = position_in_colony.getFood();
        if (food_amt <= 0) 
        {
        		position_in_colony.cn_view.hideQueenIcon();
            position_in_colony.colony.simulation.gameOver();
        }	
        food_amt--; 
        position_in_colony.setFood(food_amt);
    }
}
