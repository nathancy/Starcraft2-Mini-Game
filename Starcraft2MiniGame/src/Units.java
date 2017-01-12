import java.awt.Color;
import java.util.Random;

//Class Units that controls all unit commands/functions
public class Units {
																							//1 - bc, 2 - leviathan, 3 - mothership
	//Declaring private variables. 															
	private int NORTH = 1, SOUTH = 2, EAST = 3, WEST = 4, mdirection = EAST;				//Direction of the unit, mdirection is direction of mothership
	private EZImage unit, aura, m_aura, unit2, unit3;										//aura is general, m_aura is only mship, unit2 - bc, unit3 - leviathan
	private EZImage sidepanel1, sidepanel2, sidepanel3, p_aura1, p_aura2, p_aura3, p_aura4;	//Pics of sidepanel, 1 - bc, 2 - leviathan, 3 - mship
	private EZImage explode, sidepanelaura1, sidepanelaura2, sidepanelaura3, projectile;    //Explode pic, 1 - bc, 2 - leviathan, 3 - mship, projectile for shooting  
	private EZSound sound;
	private float m_aura1 = .5f, m_aura2 = 4.2f;		//Controls size of mothership aura
	private int SPEED = 3, direction, singledirection;	//Speed of mothership, direction of moving units, singledirection is direction of projectile
	private int posx, posy;								//Actual position of unit
	private int destx, desty;							//Destination to move object to
	private int rangex, rangey;							//Range unit can move in
    private int xproj, yproj;							//Position of projectile
	private boolean flag, auraflag, projflag = false;	//Flag of unit, auraflag, and projectile flag
	private static int MAPXLENGTH = 1500;				//Max X length
	private static int MAPYLENGTH = 1000;				//Max Y length
	private static int HALFX = MAPXLENGTH/2;			//Half X length
	private static int HALFY = MAPYLENGTH/2;			//Half Y length
	
	//Constructor for mothership
	public Units(String mothershipPic, String mothershipAuraPic1, String mothershipAuraPic2, String mothershipPic2, String mothershipPic3, String attack, int x, int y)
	{
		posx = x;
		posy = y;
		m_aura = EZ.addImage(mothershipAuraPic2, x, y);
		aura = EZ.addImage(mothershipAuraPic1, x, y);
		unit = EZ.addImage(mothershipPic, x, y);
		unit2 =  EZ.addImage(mothershipPic2, x, y);
		unit3 =  EZ.addImage(mothershipPic3, x, y);
		unit2.hide();
		unit3.hide();
		projectile = EZ.addImage(attack, x, y);
		projectile.hide();
		xproj = x;
		yproj = y;
		
		//bc
		sidepanel1 = EZ.addImage(mothershipPic2, 1620, 375);
		//leviathan
		sidepanel2 = EZ.addImage(mothershipPic3, 1620, 625);
		//mothership
		sidepanel3 = EZ.addImage(mothershipPic, 1620, 875);
	}
	
	//Constructor for the auras of the side panel
	public Units(String sideaura)
	{
		sidepanelaura1 = EZ.addImage(sideaura, 1620, 375);
		sidepanelaura2 = EZ.addImage(sideaura, 1620, 625);
		sidepanelaura3 = EZ.addImage(sideaura, 1620, 875);
	}
	
	//Hardcoded to the max 
	public Units(String sideaura, int valuetodistinguishthisconstructorbecauseIcan)
	{
		p_aura1 = EZ.addImage(sideaura, 1235, 650);
		p_aura2 = EZ.addImage(sideaura, 1385, 225);
		p_aura3 = EZ.addImage(sideaura, 200, 100);
		p_aura4 = EZ.addImage(sideaura, 720, 920);
	}
	
	//Constructor for units
	//objectpic - actual object, aurafile - aura, soundfile - sound, explodepic - explosion, (x,y) - position, (rx, ry) - range
	public Units(String objectpic, String aurafile, String soundfile, String explodepic, int x, int y, int rx, int ry)
	{
		flag = true;
		posx = x;
		posy = y;
		auraflag = true;
		rangex = rx;
		rangey = ry;
		
		sound = EZ.addSound(soundfile);
		aura = EZ.addImage(aurafile, x, y);
		unit = EZ.addImage(objectpic, x, y);
		explode = EZ.addImage(explodepic, posx, posy);
		
		//Set random destination and direction
		setRandomDirection();
		
		explode.pushToBack();
		explode.hide();
	}

/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Side aura functions
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
	
	public void rotateSidePanelAura()
	{
		sidepanelaura1.rotateBy(.5);
		sidepanelaura2.rotateBy(.5);
		sidepanelaura3.rotateBy(.5);
	}
	public void rotateTeleportAura()
	{
		p_aura1.rotateBy(.2);
		p_aura2.rotateBy(.2);
		p_aura3.rotateBy(.2);
		p_aura4.rotateBy(.2);
	}
	
/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MOTHERSHIP FUNCTIONS
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
	
	//Switch out the unit
	public void changeControlUnit()
	{
		//bc
		if(EZInteraction.isKeyDown('1'))
		{
			unit.hide();
			unit2.show();
			unit3.hide();
			sidepanel1.hide();
			sidepanel2.show();
			sidepanel3.show();
		}
		//leviathan
		else if(EZInteraction.isKeyDown('2'))
		{
			unit.hide();
			unit2.hide();
			unit3.show();
			sidepanel1.show();
			sidepanel2.hide();
			sidepanel3.show();
		}	
		//mothership
		else if(EZInteraction.isKeyDown('3'))
		{
		    unit.show();
		    unit2.hide();
		    unit3.hide();
		    sidepanel3.hide();
		    sidepanel1.show();
		    sidepanel2.show();
		}
		else
			return;
	}
	
	//Check if projectile is touching in specificed coordinates, true if is, false otherwise
	public boolean projectileInElement(int x, int y)
	{
		if(projectile.isPointInElement(x, y))
			return true;
		else
			return false;
	}
	
	//Teleport mothership, 1st aura, and 2nd aura to any position
	public void teleportMothership(int x, int y)
	{
			posx = x;
			posy = y;
			m_aura.translateTo(posx, posy);
			unit.translateTo(posx, posy);
			aura.translateTo(posx, posy);
			unit2.translateTo(posx, posy);
			unit3.translateTo(posx, posy);
	}
	
	//Handles the projectile
	public void ControlProjectile()
	{
		//If pressing button
		if (EZInteraction.isKeyDown('k') && projflag == false)
		{
			singledirection = mdirection;
			projflag = true;
			xproj = unit.getXCenter();
			yproj = unit.getYCenter();
			projectile.show();
			projectile.pullToFront();
		}
		//Move the projectile
		if(projflag == true)
		{
			if(singledirection == SOUTH)
				projectile.translateTo(xproj, yproj += 10);
			if(singledirection == NORTH)
				projectile.translateTo(xproj, yproj -= 10);
			if(singledirection == WEST)
				projectile.translateTo(xproj -= 10, yproj);
			if(singledirection == EAST)
				projectile.translateTo(xproj += 10 , yproj);
			
			//reset projectile
			//System.out.println("xproj is " + xproj + "    yproj is " + yproj);
			resetProjectile();
		}
	}
	
	//Reset it if off screen
	private void resetProjectile()
	{
		if(xproj > 1500 || xproj < 0 || yproj > 1000 || yproj < 0)
		{
			projectile.hide();
			xproj = unit.getXCenter();
			yproj = unit.getYCenter();
			projectile.translateTo(xproj, yproj);
			//System.out.println("resetted     " + "xproj is " + xproj + "    yproj is " + yproj);
			projflag = false;
		}
		else
			return;
	}
	//Controls the mothership. Takes in (w,d,a,d) to move
	public void ControlMothership()
	{	
		if(EZInteraction.isKeyDown('d'))
		{	
			unit.rotateTo(0);
			unit.translateTo(posx += SPEED, posy);
			unit2.rotateTo(0);
			unit2.translateTo(posx += SPEED, posy);
			unit3.rotateTo(0);
			unit3.translateTo(posx += SPEED, posy);
			mdirection = EAST;
		}
		else if(EZInteraction.isKeyDown('a'))
		{
			unit.rotateTo(180);
			unit.translateTo(posx -= SPEED, posy);
			unit2.rotateTo(180);
			unit2.translateTo(posx -= SPEED, posy);
			unit3.rotateTo(180);
			unit3.translateTo(posx -= SPEED, posy);
			mdirection = WEST;
		}
		else if(EZInteraction.isKeyDown('w'))
		{
			unit.rotateTo(270);
			unit.translateTo(posx, posy -= SPEED);
			unit2.rotateTo(270);
			unit2.translateTo(posx, posy -= SPEED);
			unit3.rotateTo(270);
			unit3.translateTo(posx, posy -= SPEED);
			mdirection = NORTH;	
		}
		else if(EZInteraction.isKeyDown('s'))
		{
			unit.rotateTo(90);
			unit.translateTo(posx, posy += SPEED);
			unit2.rotateTo(90);
			unit2.translateTo(posx, posy += SPEED);
			unit3.rotateTo(90);
			unit3.translateTo(posx, posy += SPEED);
			mdirection = SOUTH;
		}
			
		//Get coordinates of mothership to put on auras
		int mshipX = unit.getXCenter();
		int mshipY = unit.getYCenter();
			
		//Put aura on mothership
		m_aura.translateTo(mshipX, mshipY);
		aura.translateTo(mshipX, mshipY);
	}	

	//Controls rotation of mothership, and both auras
	public void MothershipAura()
	{
		unit.rotateBy(1);
		aura.rotateBy(.6);
		m_aura.rotateBy(.5);
	}
	
	//Scale each mothership aura
	public void ScaleMothershipAura(float value)
	{
		m_aura1 += value;
		m_aura2 += value;
		aura.scaleTo(m_aura1);
		m_aura.scaleTo(m_aura2);
	}
	
	//Set size of the mothership aura
	public void setMothershipAura(float value)
	{
		m_aura1 = value;
		m_aura2 = value;
	}
	
	//Bring the mothership to the front
	public void mothershipToFront()
	{
		projectile.pullToFront();
		m_aura.pullToFront();
		aura.pullToFront();
		unit.pullToFront();
		unit2.pullToFront();
		unit3.pullToFront();
	}
	
	//If mothership contacts a building. Returns the building code #
	public int MothershipContactBuilding()
	{
		/*
		pylon = 0;
		hatchery= 1;
		nexus= 2;
		cavern = 3;
		cc= 4;
		stasiscell = 5;
		nohit= -1;  */
			
		//Hatchery
		if(unit.isPointInElement(600, 425))
			return 1;
		//pylon
		else if(unit.isPointInElement(100, 450))
			return 0;
		//nexus
		else if(unit.isPointInElement(435, 225))
			return 2;
		//cavern
		else if(unit.isPointInElement(1050, 100))
			return 3;
		//cc
		else if(unit.isPointInElement(600, 780))
			return 4;
		//stasiscell
		else if(unit.isPointInElement(1125, 800))
			return 5;
		//Arbiterbuilding
		else if(unit.isPointInElement(900, 925))
			return 6;
		//Armory
		else if(unit.isPointInElement(1215, 90))
			return 7;
		//Barracks
		else if(unit.isPointInElement(1360, 90))
			return 8;
		//Bay
		else if(unit.isPointInElement(90, 920))
			return 9;
		//Bunker
		else if(unit.isPointInElement(340, 615))
			return 10;
		//DTbuilding
		else if(unit.isPointInElement(715, 75))
			return 11;
		//evochamber
		else if(unit.isPointInElement(1375, 560))
			return 12;
		//forge
		else if(unit.isPointInElement(70, 70))
			return 13;
		//Greentossbuilding
		else if(unit.isPointInElement(230, 920))
			return 14;
		//pool
		else if(unit.isPointInElement(1280, 340))
			return 15;
		//refinery
		else if(unit.isPointInElement(85, 725))
			return 16;
		//Turret
		else if(unit.isPointInElement(1405, 765))
			return 17;
		//Zergbuilding
		else if(unit.isPointInElement(85, 265))
			return 18;
		//Extractor
		else if(unit.isPointInElement(920, 420))
			return 19;	
		//nohit
		else
			return -1;
	}	
	
/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   UNIT FUNCTIONS
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
	
	//Set destination
	public void setDestination(int x, int y)
	{
		destx = x;
		desty = y;
	}
	
	//Switch flag value
	public void changeFlag()
	{
		if (flag == true)
			flag = false;
		else
			flag = true;
	}
	
	//Switch auraflag 
	public void changeAuraFlag()
	{
		if (auraflag == true)
			auraflag = false;
		else
			auraflag = true;
	}
	
	//Set true or false to auraflag
	public void setAuraFlag(boolean value)
	{
		auraflag = value;
	}
	
	//Get value of the auraflag
	public boolean getAuraFlag()
	{
		return auraflag;
	}
	
	//Return value of flag, DIFFERENT from auraflag (controls unit)
	public boolean getFlag()
	{
		return flag;
	}
	
	//Change position of the unit
	public void changePosition(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	//Set random destination and set direction of object (N, S, E, W)
	public void setRandomDirection()
	{
		Random randomGenerator = new Random();
	
		int ranx = randomGenerator.nextInt(rangex);
		int rany = randomGenerator.nextInt(rangey);		
		
		setDestination(ranx,rany);
		
		//quadrant 1
		if(ranx > HALFX && ranx <= MAPXLENGTH && rany >= 0 && rany <= HALFY)
			direction = EAST;
		//quadrant 2
		if(ranx >= 0 && ranx <= HALFX && rany >= 0 && rany <= HALFY)
			direction = NORTH;
		//quadrant 3
		if(ranx >= 0 && ranx <= HALFX && rany > HALFY && rany <= MAPYLENGTH)
			direction = WEST;
		//quadrant 4
		if(ranx > HALFX && ranx <= MAPXLENGTH && rany > HALFY && rany <= MAPYLENGTH)
			direction = SOUTH;
	}
	
	//Check if touching in specified coordinates. true if is, false otherwise
	public boolean isPointInElement(int x, int y)
	{
		if(unit.isPointInElement(x, y))
			return true;
		else
			return false;
	}
	
	//Controls unit movement
	public void move()
	{
		if (posx > destx) moveLeft(1);		
		if (posx < destx) moveRight(1);
		if (posy > desty) moveUp(1);		
		if (posy < desty) moveDown(1);
		
		//If at destination
		if ((posx == destx) && (posy == desty))
		{
			setRandomDirection();
		}	
		/*
		//CODE TO AVOID CONTACT WITH BUILDINGS. logic behind it is very gross
		if (unit.isPointInElement(930, 420) || unit.isPointInElement(85, 265) || unit.isPointInElement(1405, 765) || unit.isPointInElement(85, 725) || unit.isPointInElement(1280, 340) || unit.isPointInElement(230, 920) || unit.isPointInElement(70, 70) || unit.isPointInElement(1375, 560) || unit.isPointInElement(715, 75) || unit.isPointInElement(340, 615) || unit.isPointInElement(90, 920) || unit.isPointInElement(1360, 90) || unit.isPointInElement(1215, 90) || unit.isPointInElement(900, 925) || unit.isPointInElement(100, 450) || unit.isPointInElement(600, 425) || unit.isPointInElement(435, 225) || unit.isPointInElement(1050, 100) || unit.isPointInElement(600, 780) || unit.isPointInElement(475, 900))
		{
			Random randomGenerator = new Random();
			
			if(direction == SOUTH)
			{
				int ranx = randomGenerator.nextInt(HALFX);
				int rany = randomGenerator.nextInt(HALFY);		
		
				setDestination(ranx,rany);
				
				direction = NORTH;
			}
			if(direction == NORTH)
			{
				int ranx = randomGenerator.nextInt(MAPXLENGTH);
				int rany = randomGenerator.nextInt(MAPYLENGTH);
				while(ranx <= HALFX || rany <= HALFY)
				{
					ranx = randomGenerator.nextInt(MAPXLENGTH);
					rany = randomGenerator.nextInt(MAPYLENGTH);
				}
				setDestination(ranx,rany);
				direction = SOUTH;
			}
			if(direction == EAST)
			{
				int ranx = randomGenerator.nextInt(HALFX);
				int rany = randomGenerator.nextInt(MAPYLENGTH);
				while(rany <= HALFY)
				{
					rany = randomGenerator.nextInt(MAPYLENGTH);
				}
				setDestination(ranx,rany);
				direction = WEST;
			}
			if(direction == WEST)
			{
				int ranx = randomGenerator.nextInt(MAPXLENGTH);
				int rany = randomGenerator.nextInt(HALFY);
				while(ranx <= HALFX)
				{
					ranx = randomGenerator.nextInt(MAPXLENGTH);
				}
				setDestination(ranx,rany);
				direction = EAST;
			} 
		}*/
	}
	
	//Teleport any unit, its aura, and explosion to any position
	public void teleportUnit(int x, int y)
	{
			posx = x;
			posy = y;
			unit.translateTo(posx, posy);
			aura.translateTo(posx, posy);
			explode.translateTo(posx, posy);
	}
	
	//Set object to specific position. Think this function is unnecessary
	private void setImagePosition(int posx, int posy)
	{
		if (flag)
		{
			unit.translateTo(posx, posy);
			aura.translateTo(posx, posy);
			explode.translateTo(posx, posy);
		}
	}
	
	//Move left
	public void moveLeft(int step)
	{
		posx = posx - step;
		setImagePosition(posx, posy);
	}
	
	//Move right
	public void moveRight(int step)
	{
		posx = posx + step;
		setImagePosition(posx, posy);
	}
	
	//Move up
	public void moveUp(int step)
	{
		posy = posy-step;
		setImagePosition(posx, posy);
	}
	
	//Move down
	public void moveDown(int step) 
	{
		posy = posy + step;
		setImagePosition(posx, posy);
	}
	
	//Scale a units aura. Takes in the # code of the unit
	public void scaleAura(int i)
	{
		//marine
		if (i == 0)
			aura.scaleTo(1.4);
		//Archon
		else if (i == 7)
			aura.scaleTo(2.2);
		//fat ultra
		else if (i == 4)
			aura.scaleTo(1.9);
		//ultra
		else if(i == 5)
			aura.scaleTo(1.3);
		//voidray
		else if(i == 2)
			aura.scaleTo(1.9);
		//tempest
		else if(i == 8)
			aura.scaleTo(1.7);
		//cuteling
		else if(i == 6)
			aura.scaleTo(.5);
		//overlord
		else if(i == 9)
			aura.scaleTo(.6);
		else
			return;
	}
	
	//Scale the actual unit. Takes in the # code of the unit
	public void scaleUnit(int i)
	{
		//archon
		if(i == 7)
			unit.scaleTo(.25);
		//tempest
		else if(i == 8)
			unit.scaleTo(.2);
		else
			return;
	}
	
	//Rotate the actual unit. Takes in the # code of the unit
	public void unitsRotate(int i)
	{
		//mcore
		if(i == 13)
		{
			unit.rotateBy(.4);
		}
		else
			return;
	}
	
	//Rotates the unit's aura. Takes in thet # code of the unit
	public void unitsAura(int i)
	{
		//marine
		if(i == 0)
			aura.rotateBy(.8);
		//colossus
		else if (i == 1)
			aura.rotateBy(.3);
		//voidray
		else if(i == 2)
			aura.rotateBy(.3);
		//bc
		else if(i == 3)
			aura.rotateBy(.3);
		//fatultra
		else if(i == 4)
			aura.rotateBy(.3);
		//ultra
		else if(i == 5)
			aura.rotateBy(.8);
		//cuteling
		else if(i == 6)
			aura.rotateBy(.7);
		//archon
		else if(i == 7)
			aura.rotateBy(.3);
		//tempest
		else if(i == 8)
			aura.rotateBy(.3);
		//overlord
		else if(i == 9)
			aura.rotateBy(.3);
		//banshee
		else if(i == 10)
			aura.rotateBy(.6);
		//shuttle
		else if(i == 11)
			aura.rotateBy(.6);
		//guardian
		else if(i == 12)
			aura.rotateBy(.6);
		//mcore
		else if(i == 13)
			aura.rotateBy(.6);
		//wraith
		else if(i == 14)
			aura.rotateBy(.6);
		//pheonix
		else if(i == 15)
			aura.rotateBy(.6);
		//unitbc
		else if(i == 16)
			aura.rotateBy(.6);
		//raven
		else if(i == 17)
			aura.rotateBy(.6);
		//carrier
		else if(i == 18)
			aura.rotateBy(.6);
		//leviathan
		else if(i == 19)
			aura.rotateBy(.6);
		else
			return;
	}
	
	//Get the X coordinate of the unit (Or mothership)
	public int getXCenter()
	{
		return unit.getXCenter();
	}
	
	//Get the Y coordinate of the unit (Or mothership)
	public int getYCenter()
	{
		return unit.getYCenter();
	}

	//Move the unit
	public void translateTo(int posx, int posy)
	{
		unit.translateTo(posx, posy);
	}
	
	//Move the unit
	public void translateBy(int posx, int posy)
	{
		unit.translateBy(posx, posy);
	}
	
	//Marine hit
	public void marinehit()
	{
		deadTriggers();
	}
	
	//Colossus hit
	public void colossushit()
	{
		deadTriggers();
	}
	
	//Void ray hit
	public void voidrayhit()
	{
		deadTriggers();
	}
	
	//BC hit
	public void bchit()
	{
		deadTriggers();
	}
	
	//Shuttle hit
	public void shuttlehit()
	{
		deadTriggers();
	}
	
	//Guardian hit
	public void guardianhit()
	{
		deadTriggers();
	}
	
	//Fat ultra hit
	public void fatultrahit()
	{
		deadTriggers();
		
		EZ.addImage("cloudaura.png", getXCenter(), getYCenter());
		EZ.addImage("bluering.png", getXCenter(), getYCenter());
		EZ.addImage("portal.png", getXCenter(), getYCenter());
		EZ.addImage("diablo.png", getXCenter(), getYCenter());
	}
	
	//Ultra hit
	public void ultrahit()
	{
		deadTriggers();
	
		EZ.addImage("babyultra.png", getXCenter(), getYCenter());
		
	}
	
	//Cute ling hit
	public void cutelinghit()
	{
		deadTriggers();
	}
	
	//Archon hit
	public void archonhit()
	{
		deadTriggers();
	}
	
	//Tempest hit
	public void tempesthit()
	{
		deadTriggers();
	}
	
	//Overlord hit
	public void overlordhit()
	{
		deadTriggers();
	}
	
	//Banshee hit
	public void bansheehit()
	{
		deadTriggers();
	}
	
	//Mcore hit
	public void mothershipcorehit()
	{
		deadTriggers();
	}
	
	//Wraith hit
	public void wraithhit()
	{
		deadTriggers();
	}
	
	//Pheonix hit
	public void pheonixhit()
	{
		deadTriggers();
	}
	
	//Other bc hit
	public void unitbchit()
	{
		deadTriggers();
	}
	
	//Raven hit
	public void ravenhit()
	{
		deadTriggers();
	}
	
	//Carrier hit
	public void carrierhit()
	{
		deadTriggers();
	}
	
	//Leviathan hit
	public void leviathanhit()
	{
		deadTriggers();
	}
	
	//Activates triggers when unit is hit
	private void deadTriggers()
	{
		sound.play();
		aura.hide();
		unit.hide();
		explode.pullToFront();
		explode.show();
		changeFlag();
		changeAuraFlag();
	}
}
