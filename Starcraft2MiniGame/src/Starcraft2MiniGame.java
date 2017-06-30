import java.awt.Color; 
import java.lang.String;
import java.io.*;
import java.util.*;

public class Starcraft2MiniGame {

	public static void main(String[] args) throws java.io.IOException{
	
		//Variables
		int UNITS = 20;                                                                    //Number of units and buildings
		int BUILDINGS = 20;
		int gamescore = 1, unitscore = 0, AuraCounter = 0, backgroundsoundcounter = 0;     //gamescore is total score, unitscore is number of units collected
		float M_auraIncreaser = .15f, previoussize = 0, marineIncreseAura = .035f;         //Auracounter is for increasing aura effect. backgroundsndctr is for occasional sound
		boolean win = false, marinehit = false;											   //Win flag and marinehit flag
		
		//Initialize environment
		EZ.initialize(1750, 1000);
		EZ.addImage("images/sidebackground.jpg", 1650, 500);
		
		//Add scoreboard and side texts
		EZText text = EZ.addText(1625, 75, "SCORE: 0", Color.white, 20);
		EZ.addText(1625, 150, "Press '9' or '0' to switch music", Color.white, 15);
		EZ.addText(1625, 250, "Press '1' for battlecruiser", Color.white, 15);
		EZ.addText(1625, 500, "Press '2' for leviathan", Color.white, 15);
		EZ.addText(1625, 750, "Press '3' for mothership", Color.white, 15);
		EZ.addText(1600, 200, "Press 'k' to shoot", Color.white, 15);
		EZImage side_projectile = EZ.addImage("images/tempestball.png", 1700, 200);
		
		//Add background map 
		EZ.addImage("images/cloudmap.jpg", 750, 500);
	
		//Add background sounds/trigger sounds
		EZSound warp = EZ.addSound("sounds/warpfield.wav");
		EZSound thunder = EZ.addSound("sounds/thunder.wav");
		EZSound bgm1 = EZ.addSound("sounds/terran-23.wav");
		EZSound bgm2 = EZ.addSound("sounds/terran1.wav");
		EZSound victory = EZ.addSound("sounds/victory.wav");
		EZSound defeat = EZ.addSound("sounds/defeat.wav");
		bgm1.loop();
		
		//Setting frame rate
		EZ.setFrameRate(90);
	
		//Initalize auras for side panel
		Units sideAuras = new Units("images/mothershipaura.png");
		Units teleportAuras = new Units("images/tealblueaura.png", 1);
		
		//Initalize mothership
		Units mothership = new Units("images/mothership.png", "images/mothershipaura.png", "images/mothershipaura2.png", "images/bccontrolunit.png", "images/leviathan.png", "images/tempestball.png",200, 100);
		mothership.scaleAura(9);
		
		//Scan building file
		Scanner BuildingScanner = new Scanner(new FileReader("Buildings.txt"));
	
		//Initialize arrays for buildings							// Building codes
		Buildings building[] = new Buildings[BUILDINGS];			// 0 - pylon
		String buildingPic[] = new String[BUILDINGS];				// 1 - hatchery
		String buildingAura[] = new String[BUILDINGS];				// 2 - nexus
		String buildingSound[] = new String[BUILDINGS];				// 3 - cavern
		int buildingX[] = new int[BUILDINGS];						// 4 - cc
		int buildingY[] = new int[BUILDINGS];						// 5 - stasiscell
																	// 6 - arbiterbuilding
		//Load files into array										// 7 - armory
		for(int i = 0; i < BUILDINGS; i++)							// 8 - barracks
		{															// 9 - bay
			buildingPic[i] = BuildingScanner.next();				// 10 - bunker
			buildingAura[i] = BuildingScanner.next();				// 11 - dtbuilding
			buildingSound[i] = BuildingScanner.next();				// 12 - evochamber
			buildingX[i] = BuildingScanner.nextInt();				// 13 - forge
			buildingY[i] = BuildingScanner.nextInt();				// 14 - greentossbuilding
			building[i] = new Buildings(buildingPic[i], buildingAura[i], buildingSound[i], buildingX[i], buildingY[i]);
			building[i].scaleBuilding(i);							// 15 - pool
			building[i].scaleAura(i);								// 16 - refinery
		}															// 17 - turret
		//Close building file										// 18 - zergbuilding
		BuildingScanner.close();									// 19 - extractor
								
		//Open units file
		Scanner UnitsWithAuraScanner = new Scanner(new FileReader("Units.txt"));
	
		//Initialize arrays for units								// Unit codes
		Units units[] = new Units[UNITS];							// 0 - marine
		String unitsPic[] = new String[UNITS];						// 1 - colossus
		String unitsAura[] = new String[UNITS];						// 2 - voidray
		String unitsSound[] = new String[UNITS];					// 3 - bc
		String unitsExplode[] = new String[UNITS];					// 4 - fatultra
		int unitsX[] = new int[UNITS];								// 5 - ultra
		int unitsY[] = new int[UNITS];								// 6 - cuteling
		int unitsRangeX[] = new int[UNITS];							// 7 - archon
		int unitsRangeY[] = new int[UNITS];							// 8 - tempest
		
		//Load files into array										// 9 - overlord
		for(int i = 0; i < UNITS; i++)								// 10 - banshee
		{															// 11 - shuttle
			unitsPic[i] = UnitsWithAuraScanner.next();				// 12 - guardian
			unitsAura[i] = UnitsWithAuraScanner.next();				// 13 - mcore
			unitsSound[i] = UnitsWithAuraScanner.next();			// 14 - wraith
			unitsExplode[i] = UnitsWithAuraScanner.next();			// 15 - pheonix
			unitsX[i] = UnitsWithAuraScanner.nextInt();				// 16 - unitbc
			unitsY[i] = UnitsWithAuraScanner.nextInt();				// 17 - raven
			unitsRangeX[i] = UnitsWithAuraScanner.nextInt();		// 18 - carrier
			unitsRangeY[i] = UnitsWithAuraScanner.nextInt();        // 19 - leviathan
			units[i] = new Units(unitsPic[i], unitsAura[i], unitsSound[i], unitsExplode[i], unitsX[i], unitsY[i], unitsRangeX[i], unitsRangeY[i]);
			units[i].scaleAura(i);
			units[i].scaleUnit(i);
		}
		//Close unit file
		UnitsWithAuraScanner.close();
		
	while (true)
	{
		//Change music
		if(EZInteraction.isKeyDown('0'))
		{
			bgm1.stop();
			bgm2.loop();
		}
		if(EZInteraction.isKeyDown('9'))
		{
			bgm2.stop();
			bgm1.loop();
		}
		
		//All controls for mothership
		mothership.mothershipToFront();
		mothership.ControlMothership();
		mothership.ScaleMothershipAura(0);
		mothership.MothershipAura();
		mothership.changeControlUnit();
		mothership.ControlProjectile();
		
		//Rotate side panel auras and spin projectile
		sideAuras.rotateSidePanelAura();
		side_projectile.rotateBy(.3);
		teleportAuras.rotateTeleportAura();
		
		//Controlling all buildings
		for(int i = 0; i < BUILDINGS; i++)
		{
			//Get building code
			int hit = mothership.MothershipContactBuilding();
	
			//Building auras
			building[i].buildingAura(i);
	
//	System.out.print("X is " + mothership.getXCenter());
//	System.out.println("    Y is " + mothership.getYCenter());

			//If hit a building
			if(hit >= 0 && hit <= BUILDINGS) 
			{
				switch(hit)
				{
				case 0: building[0].pylonhit();
						mothership.teleportMothership(1235, 650); 
						break;
				case 1: building[1].hatcheryhit();
						mothership.teleportMothership(1385, 225);
						break;
				case 2: building[2].nexushit();
						mothership.teleportMothership(200, 100);
						break;
				case 3: building[3].cavernhit();
						mothership.teleportMothership(200, 100);
						break;
				case 4: building[4].cchit();
						mothership.teleportMothership(1385, 225);
						break;
				case 5: building[5].stasiscellhit();
						mothership.teleportMothership(200, 100);
						break;
				case 6: building[6].arbiterbuildinghit();
						mothership.teleportMothership(1235, 650);
						break;
				case 7: building[7].armorybuildinghit();
						mothership.teleportMothership(200, 100);
						break;
				case 8: building[8].barrackshit();
						mothership.teleportMothership(1235, 650);
						break;
				case 9: building[9].bayhit();
						mothership.teleportMothership(1235, 650);
						break;
				case 10: building[10].bunkerhit();
						mothership.teleportMothership(1235, 650);
						break;
				case 11: building[11].dtbuildinghit();
						mothership.teleportMothership(200, 100);
						break;			
				case 12: building[12].evochamberhit();
						mothership.teleportMothership(200, 100);
						break;		
				case 13: building[13].forgehit();
						mothership.teleportMothership(1385, 225);
						break;	
				case 14: building[14].greentossbuildinghit();
						mothership.teleportMothership(1235, 650);
						break;	
				case 15: building[15].poolhit();
						mothership.teleportMothership(200, 100);
						break;	
				case 16: building[16].refineryhit();
						mothership.teleportMothership(200, 100);
						break;	
				case 17: building[17].turrethit();
						mothership.teleportMothership(1385, 225);
						break;	
				case 18: building[18].zergbuildinghit();
						mothership.teleportMothership(720, 920);
						break;	
				case 19: building[19].extractorhit();
						mothership.teleportMothership(200, 100);
						break;		
				}
				
				//Decrement score and display
				gamescore--;
				text.setMsg("SCORE: "+ gamescore);
				
				//If lose
				if(gamescore == 0)
				{
					EZ.addImage("images/gameover.JPG", 750, 500);
					defeat.play();
					EZ.pause(4500);
					System.exit(1);
				}
			}
			//reset hit
			hit = -1;
		}
		
		//Controls for units
		for(int j = 0; j < UNITS; j++)
		{
			//Controls all commands and functions of units
			units[j].move();
			units[j].unitsAura(j);
			units[j].unitsRotate(j);
			
			//If mothership contacts a unit or if projectile hits a unit
			if((mothership.isPointInElement(units[j].getXCenter(), units[j].getYCenter()) || mothership.projectileInElement(units[j].getXCenter(), units[j].getYCenter())))
			{	
				if(j == 0 && (units[0].getFlag() == true)) {
					units[0].marinehit(); 	gamescore++; unitscore++;
				}
				else if(j == 1 && (units[1].getFlag() == true)) {
					units[1].colossushit(); gamescore++; unitscore++;
				}
				else if(j == 2 && (units[2].getFlag() == true)) {
					units[2].voidrayhit();	gamescore++; unitscore++;
				}
				else if(j == 3 && (units[3].getFlag() == true)) {
					units[3].bchit();	    gamescore++; unitscore++;
				}
				else if(j == 4 && (units[4].getFlag() == true)) {
					units[4].fatultrahit();	gamescore++; unitscore++;
				}
				else if(j == 5 && (units[5].getFlag() == true)) {
					units[5].ultrahit();	gamescore++; unitscore++;
				}
				else if(j == 6 && (units[6].getFlag() == true)) {
					units[6].cutelinghit();	gamescore++; unitscore++;
				}
				else if(j == 7 && (units[7].getFlag() == true)) {
					units[7].archonhit();	gamescore++; unitscore++;
				}
				else if(j == 8 && (units[8].getFlag() == true)) {
					units[8].tempesthit();	gamescore++; unitscore++;
				}
				else if(j == 9 && (units[9].getFlag() == true)) {
					units[9].overlordhit(); gamescore++; unitscore++;
				}
				else if(j == 10 && (units[10].getFlag() == true)) {
					units[10].bansheehit(); gamescore++; unitscore++;
				}
				else if(j == 11 && (units[11].getFlag() == true)) {
					units[11].shuttlehit(); gamescore++; unitscore++;
				}
				else if(j == 12 && (units[12].getFlag() == true)) {
					units[12].guardianhit(); gamescore++; unitscore++;
				}
				else if(j == 13 && (units[13].getFlag() == true)) {
					units[13].mothershipcorehit(); gamescore++; unitscore++;
				}
				else if(j == 14 && (units[14].getFlag() == true)) {
					units[14].wraithhit(); gamescore++; unitscore++;
				}
				else if(j == 15 && (units[15].getFlag() == true)) {
					units[15].pheonixhit(); gamescore++; unitscore++;
				}
				else if(j == 16 && (units[16].getFlag() == true)) {
					units[16].unitbchit(); gamescore++; unitscore++;
				}
				else if(j == 17 && (units[17].getFlag() == true)) {
					units[17].ravenhit(); gamescore++; unitscore++;
				}
				else if(j == 18 && (units[18].getFlag() == true)) {
					units[18].carrierhit(); gamescore++; unitscore++;
				}
				else if(j == 19 && (units[19].getFlag() == true)) {
					units[19].leviathanhit(); gamescore++; unitscore++;
				}
			}
			//Update score
			text.setMsg("SCORE: "+ gamescore);
		}
		
		//If win(player collects all objects)
		if(unitscore == UNITS && win == false)
		{
			EZ.addImage("images/blackbackground.jpg", 750, 500);
			EZ.addImage("images/victory1.jpg", 750, 500);
			victory.play();
			win = true;	
		}
		
		//Increase mothership aura with each collected object
		if (units[0].getAuraFlag() == false || units[1].getAuraFlag() == false || units[2].getAuraFlag() == false || units[3].getAuraFlag() == false || units[4].getAuraFlag() == false || units[5].getAuraFlag() == false || units[6].getAuraFlag() == false || units[7].getAuraFlag() == false || units[8].getAuraFlag() == false || units[9].getAuraFlag() == false || units[10].getAuraFlag() == false || units[11].getAuraFlag() == false || units[12].getAuraFlag() == false || units[13].getAuraFlag() == false || units[14].getAuraFlag() == false || units[15].getAuraFlag() == false  || units[16].getAuraFlag() == false || units[17].getAuraFlag() == false || units[18].getAuraFlag() == false || units[19].getAuraFlag() == false)
		{
			//Increase mothership aura
			if(units[0].getAuraFlag() == false)
				mothership.ScaleMothershipAura(marineIncreseAura);
			else
				mothership.ScaleMothershipAura(M_auraIncreaser);
		
			//Set flags to only happen once 
			for (int i = 1; i < UNITS; i++)
				units[i].setAuraFlag(true);

			//Special marine aura effect
			if (units[0].getAuraFlag() == false && marinehit == false)
			{
				previoussize = M_auraIncreaser;
				marinehit = true;
			}
			
			//Reset aura to previous size
			if (AuraCounter >= 170 && units[0].getAuraFlag() == false)
			{
				units[0].setAuraFlag(true);
				mothership.setMothershipAura(previoussize);
				mothership.ScaleMothershipAura(previoussize);
			}
			
			//Increase timer
			AuraCounter++;
		} 
		
		backgroundsoundcounter++;
		
		//Controls periodic warp sound
		if (backgroundsoundcounter == 1000)
		{
			//Play sound
			warp.play();	
		}
		//Controls periodic thunder sound
		if (backgroundsoundcounter == 1500)
		{
			//Play sound and reset counter
			thunder.play();
			backgroundsoundcounter = 0;
		}
		//Refresh screen
		EZ.refreshScreen();
	}
  }
}
