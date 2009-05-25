package geomwarsremake.objects;

import geomwarsremake.objects.enemies.AttractionHole;
import geomwarsremake.objects.enemies.BlueLozenge;
import geomwarsremake.objects.enemies.GreenSquare;
import geomwarsremake.objects.enemies.PinkSquare;
import geomwarsremake.objects.enemies.Spinning;
import geomwarsremake.states.IngameState;

import java.util.ArrayList;

/**
 *  Main datastructure for game. Contains all other objects.
 */
public class Level {

	public PlayerShip pship;
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Shot> shots = new ArrayList<Shot>();
	//Attraction hole are also contains in the Enemy list. This list is to help
	//with the attraction and repulsion of the hole over every other objects in the game
	public ArrayList<AttractionHole> holes = new ArrayList<AttractionHole>();
	public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	public ArrayList<Enemy> enemiesToAdd = new ArrayList<Enemy>();

	public int mapWidth = 1200;
	public int mapHeight = 900;

	protected IngameState state;

	public Level(IngameState state) {
		this.state = state;
	}

	public void load() {
		addObject(new PlayerShip());
		initRandomEnemy();
		//TEST ------------- TEST
		addObject(new Spinning(0, 0));
		addObject(new AttractionHole(200, 200));
		//END OF TEST ---------- END OF TEST
	}
	
	public void addObject(GwrObject obj){
		obj.setStateAndLevel(state, this);
		if(obj instanceof PlayerShip){
			pship = (PlayerShip) obj;
		}else if(obj instanceof Enemy){
			addEnemy((Enemy)obj);
		}else if(obj instanceof Shot){
			shots.add((Shot)obj);
		}
	}


	public void resetEnemies(){
		enemies.clear();
		holes.clear();
	}

	private void addEnemy(Enemy enemy){
		if(enemy instanceof AttractionHole){
			holes.add((AttractionHole) enemy);
		}
		enemies.add(enemy);
	}

	private void removeEnemy(Enemy enemy, int index){
		if(enemy instanceof AttractionHole){
			holes.remove(enemy);
		}
		enemies.remove(index);
	}

	public void removeDeadObjects(){
		for(int i=0; i<enemies.size(); i++){
			if(enemies.get(i).isDead()){
				removeEnemy(enemies.get(i), i);
				i--;
			}
		}
		for(int i=0; i<shots.size(); i++){
			if(!shots.get(i).getCanHit()){
				shots.remove(i);
				i--;
			}
		}
		for(int i=0; i<bombs.size(); i++){
			if(bombs.get(i).isDead()){
				bombs.remove(i);
				i--;
			}
		}
	}
	
	public void addEnemies(){
		for(int i=0; i<enemiesToAdd.size(); i++){
			addObject(enemiesToAdd.remove(i));
			i--;
		}
	}


	/**
	 * Enemies generator. BIG BIG PART!!!!!!!!!
	 * 
	 */
	
	public int totalTime = 0;
	
	private final int CANT_SPAWN_RANGE = 300;
	
	private float reduceDelayEnemy = 0.01f;
	private final float MINIMUN_TIME_BETWEEN_ENEMY = 100;
	
	private float timeBetweenEnemy = 2000;
	
	private float timeBetweenUpdate = 5000;
	
	private float delayBeforeNextEnemy = timeBetweenEnemy;
	private float delayBeforeNextUpdate = timeBetweenUpdate;
	
	public void updateGenerator(int deltaTime){
		totalTime += deltaTime;
		createEnemy(deltaTime);
		updateEnemyChance(deltaTime);
		//TEST --------------------------- TEST
		//cornerBlue(deltaTime);
		//cornerPink(deltaTime);
		//cornerGreen(deltaTime);
		//END OF TEST ------------------ END OF TEST
	}
	
	private void updateEnemyChance(int deltaTime){
		delayBeforeNextUpdate -= deltaTime;
		if(delayBeforeNextUpdate <= 0){
			updateChanceList();
            addToChance();
			delayBeforeNextUpdate += timeBetweenUpdate;
		}
	}
	
	private void createEnemy(int deltaTime){
		timeBetweenEnemy -= deltaTime*reduceDelayEnemy;
		if(timeBetweenEnemy < MINIMUN_TIME_BETWEEN_ENEMY){
			timeBetweenEnemy = MINIMUN_TIME_BETWEEN_ENEMY;
		}
		delayBeforeNextEnemy -= deltaTime;
		if(delayBeforeNextEnemy <= 0){
			createRandoms();
			delayBeforeNextEnemy += timeBetweenEnemy;
		}
	}
	
	/**
	 * Create a random numbers of enemies (between 1 and 3) at random place on the map.
	 */
	private void createRandoms(){
		int numberOfEnemy = (int)(Math.random()*3 + 1);
		float shipX = pship.getCircle().getCenterX();
		float shipY = pship.getCircle().getCenterY();
		for(int i=0; i<numberOfEnemy; i++){
			int posX = (int) (Math.random()*mapWidth);
			int posY = (int) (Math.random()*mapHeight);
			float deltaX = shipX - posX;
			float deltaY = shipY - posY;
			float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
			if(distance < CANT_SPAWN_RANGE){
				i--;
			}else{
				addObject(createRandom(posX, posY));
			}
		}
	}
	
	private Enemy createRandom(int posX, int posY){
		switch (chooseEnemy()) {
			case 0: return new Spinning(posX, posY);
			case 1: return new BlueLozenge(posX, posY);
			case 2: return new GreenSquare(posX, posY);
			case 3: return new PinkSquare(posX, posY);
			case 4: return new AttractionHole(posX, posY);
		}
		return new BlueLozenge(posX, posY);
	}
	
	
	
	/**
	 * This part is a try to make an enemy generator for geometry war. The difficulty
	 * with this enemy generator is that the percentage of chance that a specific sort of
	 * enemy appear change in the time.
	 *
	 * To deal with that we store the percentage as Integer in an ArrayList. Each position
	 * of the ArrayList correspond to a specific enemy and the number at the position give
	 * you the chance of appearance of this sort of enemy in percentage if you divide it
	 * by the sums of all the number in the list.
	 *
	 * The enemy position in the list are as follow :
	 * 0. Spinning
	 * 1. BlueLozenge
	 * 2. GreenSquare
	 * 3. PinkSquare
	 * 4. AttractionHole
	 * 5. Pacman
	 *
	 * When the game begin only Spinning can appear. As the game continue more sort of
	 * enemy appear. The order of the list indicate the average appearance of the enemy
	 * in the game. That means that it usually take more time for the pacman to appear
	 * than to the GreenSquare.
	 *
	 * @author Marc
	 */
	final int NUMBER_OF_ENEMY = 6;

    /** The sums of all the number in the array */
    int totalChance = 0;

    /** The sums of all the number in the array */
    int totalAdd = 0;

    /** The maximun number we can get with the random. This number increase as the
     game progress */
    int randomMaxChance = 3;

    int randomMaxAdd = 81;

    int [] listChance = new int[NUMBER_OF_ENEMY];

    int [] listAdd = {0, 62, 19, 12, 6, 1} ;

    int [] entryValue = {8, 5, 3, 2, 1};
    int entrySpot = 0;
    
    int [] sortValue = {0, 50, 15, 8, 3, 0};

    int [] numberOfSpawn = new int[NUMBER_OF_ENEMY];

    /*public GeomWarRandom(){

        listChance[0] = 5;
        totalChance += 5;

        runAddToChance();
    }*/

    private void runAddToChance(){
        for(int i=0; i<100; i++){
            for(int j=0; j<5; j++){
                printList();
                chooseEnemy();
                printListSpawn();
                System.out.println("-----------------------------------");
            }
            updateChanceList();
            addToChance();
        }
    }
    
    private void initRandomEnemy(){
    	listChance[0] = 5;
    	totalChance += 5;
    }

    private int chooseEnemy(){
    	int result = -1;
    	
        int n = (int)(Math.random()*totalChance);
        int current = 0;
        if(n < (current += listChance[0])){
            System.out.println("Random : SPINNING");
            numberOfSpawn[0]++;
            result = 0;
        }else if(n < (current += listChance[1])){
            System.out.println("Random : BLUE");
            numberOfSpawn[1]++;
            result = 1;
        }else if(n < (current += listChance[2])){
            System.out.println("Random : GREEN");
            numberOfSpawn[2]++;
            result = 2;
        }else if(n < (current += listChance[3])){
            System.out.println("Random : PINK");
            numberOfSpawn[3]++;
            result = 3;
        }else if(n < (current += listChance[4])){
            System.out.println("Random : HOLE");
            numberOfSpawn[4]++;
            result = 4;
        }else if(n < (current += listChance[5])){
            System.out.println("Random : PACMAN");
            numberOfSpawn[5]++;
            result = 5;
        }
        return result;
    }

    private void updateChanceList(){
        //Update blue
        if(listChance[1] > 0){
            if(listChance[1] < sortValue[1]){
                if(sortValue[1] - listChance[1] > 1){
                    listChance[1] += 2;
                    totalChance += 2;
                }else{
                    listChance[1] += 1;
                    totalChance += 1;
                }
            }
        }
        //Update green
        if(listChance[2] > 0){
            if(listChance[2] < sortValue[2]){
                listChance[2] += 1;
                totalChance += 1;
            }
        }
        //Update pink
        if(listChance[3] > 0){
            if(listChance[3] < sortValue[3]){
                listChance[3] += 1;
                totalChance += 1;
            }
        }
        //Update hole
        if(listChance[4] > 0){
            if(listChance[4] < sortValue[4]){
                listChance[4] += 1;
                totalChance += 1;
            }
        }
        //Update pacman
        if(listChance[5] > 0){
        	if(listChance[5] < sortValue[5]){
        		listChance[5]++;
        		totalChance++;
        	}
        }
    }

    private void addToChance(){
        int n = (int)(Math.random()*randomMaxAdd);
        int current = 0;
        if(n < (current += listAdd[1])){
            //try to add blue
            if(listChance[1] == 0){
                System.out.println("Blue : " + n);
                sortValue[1] += entryValue[entrySpot];
                listChance[1] = 1;
                totalChance += 1;
                entrySpot++;
                updateRandomMaxAdd();
            }
        }else if(n < (current += listAdd[2])){
            //try to add green
            if(listChance[2] == 0){
                System.out.println("Green : " + n);
                sortValue[2] += entryValue[entrySpot];
                listChance[2] = 1;
                totalChance += 1;
                entrySpot++;
                updateRandomMaxAdd();
            }
        }else if(n < (current += listAdd[3])){
            //try to add pink
            if(listChance[3] == 0){
                System.out.println("Pink : " + n);
                sortValue[3] += entryValue[entrySpot];
                listChance[3] = 1;
                totalChance += 1;
                entrySpot++;
                updateRandomMaxAdd();
            }
        }else if(n < (current += listAdd[4])){
            //try to add hole
            if(listChance[4] == 0){
                System.out.println("Hole : " + n);
                sortValue[4] += entryValue[entrySpot];
                listChance[4] = 1;
                totalChance += 1;
                entrySpot++;
                updateRandomMaxAdd();
            }
        }else{
            //try to add pacman.
            if(listChance[5] == 0){
                System.out.println("Pacman : " + n);
                sortValue[5] += entryValue[entrySpot];
                listChance[5] = 1;
                totalChance += 1;
                entrySpot++;
                updateRandomMaxAdd();
            }
        }
    }

    private void updateRandomMaxAdd(){
        if(entrySpot == 1){
            randomMaxAdd += 12;
        }
        if(entrySpot == 2){
            randomMaxAdd += 6;
        }
        if(entrySpot == 3){
            randomMaxAdd += 1;
        }
    }

    private void printList(){
        for(int i=0; i<NUMBER_OF_ENEMY; i++){
            System.out.print(listChance[i] + " ");
        }
        System.out.println(" || Total = " + totalChance);
    }

    private void printListSpawn(){
        System.out.print("NumberOfSpawn = ");
        for(int i=0; i<NUMBER_OF_ENEMY; i++){
            System.out.print(numberOfSpawn[i] + " ");
        }
        System.out.println();
    }
    
    /**
     * This part is where the code for the special attack are.
     */
    
    
    private void updateSpecial(int deltaTime){
    	if(durationLeftBlue > 0){
    		cornerBlue(deltaTime);
    	}
    	if(durationLeftPink > 0){
    		cornerPink(deltaTime);
    	}
    }
    
    private int durationLeftBlue = 0;
    private int timeBeforeNextBlue = 0;
    
    private void cornerBlue(int deltaTime){
    	timeBeforeNextBlue -= deltaTime;
    	if(timeBeforeNextBlue < 0){
    		//top-left
    		addObject(new BlueLozenge(0, 0));
    		//top-right
    		addObject(new BlueLozenge(mapWidth, 0));
    		//bottom-left
    		addObject(new BlueLozenge(0, mapHeight));
    		//bottom-right
    		addObject(new BlueLozenge(mapWidth, mapHeight));
    		//Adjust time
    		int i = (int)(200 - totalTime*0.00001);
        	if(i < 75){
        		i = 75;
        	}
        	timeBeforeNextBlue += i;
    	}
    }
    
    private int durationLeftPink = 0;
    private int timeBeforeNextPink = 0;
    
    private void cornerPink(int deltaTime){
    	timeBeforeNextPink -= deltaTime;
    	if(timeBeforeNextPink < 0){
    		//top-left
    		addObject(new PinkSquare(0, 0));
    		//top-right
    		addObject(new PinkSquare(mapWidth, 0));
    		//bottom-left
    		addObject(new PinkSquare(0, mapHeight));
    		//bottom-right
    		addObject(new PinkSquare(mapWidth, mapHeight));
    		//Adjust time
    		int i = (int)(400 - totalTime*0.00001);
        	if(i < 150){
        		i = 150;
        	}
        	timeBeforeNextPink += i;
    	}
    }
    
    private int durationLeftGreen = 0;
    private int timeBeforeNextGreen = 0;
    
    private void cornerGreen(int deltaTime){
    	timeBeforeNextGreen -= deltaTime;
    	if(timeBeforeNextGreen < 0){
    		//top-left
    		addObject(new GreenSquare(0, 0));
    		//top-right
    		addObject(new GreenSquare(mapWidth, 0));
    		//bottom-left
    		addObject(new GreenSquare(0, mapHeight));
    		//bottom-right
    		addObject(new GreenSquare(mapWidth, mapHeight));
    		//Adjust time
    		int i = (int)(400 - totalTime*0.00001);
        	if(i < 150){
        		i = 150;
        	}
        	timeBeforeNextGreen += i;
    	}
    }

}
