/* ******************************
 *
 * File: Buildings.java
 *
 * Class for controlling all building
 * functions and features.
 *
 * *************************** */

public class Buildings {

  private EZImage building, aura;      // Building is building picture
  private EZSound sound;              // Aura is building aura
  private int posx, posy;              // Position of the building
  boolean flag;                        // Building flag, default true

  // Constructor for a building object
  public Buildings(String objectpic, String aurafile, String soundfile, int x, int y) {
    aura = EZ.addImage(aurafile, x, y);
    building = EZ.addImage(objectpic, x, y);
    sound = EZ.addSound(soundfile);
    flag = true;
    posx = x;
    posy = y;
  }

  // Scale actual building. Takes in building # code
  public void scaleBuilding(int i) {
    // stasiscell
    if (i == 5)
      building.scaleTo(.5);
    else
      return;
  }

  // Scale building aura. Takes in building code #
  public void scaleAura(int i) {
    // Pylon
    if (i == 0)
      aura.scaleTo(.9);
      // Hatchery
    else if (i == 1)
      aura.scaleTo(1);
      // Nexus
    else if (i == 2)
      aura.scaleTo(.65);
      // Command Center
    else if (i == 4)
      aura.scaleTo(.6);
      // Stasiscell
    else if (i == 5)
      aura.scaleTo(.6);
      // Arbiterbuilding
    else if (i == 6)
      aura.scaleTo(.8);
      // Armory
    else if (i == 7)
      aura.scaleTo(.6);
      // Barracks
    else if (i == 8)
      aura.scaleTo(1.4);
      // Bunker
    else if (i == 10)
      aura.scaleTo(1.4);
      // Evochamber
    else if (i == 12)
      aura.scaleTo(.8);
      // Forge
    else if (i == 13)
      aura.scaleTo(1.5);
      // Greentoss building
    else if (i == 14)
      aura.scaleTo(.5);
      // Spawning Pool
    else if (i == 15)
      aura.scaleTo(1.5);
      // Refinery
    else if (i == 16)
      aura.scaleTo(1.8);
      // Turret
    else if (i == 17)
      aura.scaleTo(1.3);
      // Zergbuilding
    else if (i == 18)
      aura.scaleTo(.8);
      // Extractor
    else if (i == 19)
      aura.scaleTo(.8);
    else
      return;
  }

  // Switch status flag of the building
  public void changeFlag() {
    if (flag == true)
      flag = false;
    else
      flag = true;
  }

  // Controls building auras. Takes in the building code #
  public void buildingAura(int i) {
    // Pylon
    if (i == 0)
      aura.rotateBy(1.2);
      // Hatchery
    else if (i == 1)
      aura.rotateBy(1.2);
      // Cavern
    else if (i == 3)
      aura.rotateBy(.1);
      // Nexus
    else if (i == 2)
      aura.rotateBy(3);
      // Command Center
    else if (i == 4)
      aura.rotateBy(.8);
      // Stasiscell
    else if (i == 5)
      aura.rotateBy(2);
      // Arbiterbuilding
    else if (i == 6)
      aura.rotateBy(.4);
      // Armory
    else if (i == 7)
      aura.rotateBy(.3);
      // Barracks
    else if (i == 8)
      aura.rotateBy(.3);
      // Engineering bay
    else if (i == 9)
      aura.rotateBy(.3);
      // Bunker
    else if (i == 10)
      aura.rotateBy(.3);
      // dtbuilding
    else if (i == 11)
      aura.rotateBy(.3);
      // Evolution chamber
    else if (i == 12)
      aura.rotateBy(.1);
      // Forge
    else if (i == 13)
      aura.rotateBy(.3);
      // Greentossbuilding
    else if (i == 14)
      aura.rotateBy(.2);
      // Spawning Pool
    else if (i == 15)
      aura.rotateBy(.3);
      // Refinery
    else if (i == 16)
      aura.rotateBy(.3);
      // Turret
    else if (i == 17)
      aura.rotateBy(.3);
      // Zergbuilding
    else if (i == 18)
      aura.rotateBy(.1);
      // Extractor
    else if (i == 19)
      aura.rotateBy(.1);
    else
      return;
  }

  // If mothership hits pylon
  public void pylonhit() {
    // Play sound and add pictures
    sound.play();

    EZImage pylon1 = EZ.addImage("images/Pylon.png", 60, 310);
    EZImage pylon2 = EZ.addImage("images/Pylon.png", 210, 370);
    EZImage pylon3 = EZ.addImage("images/Pylon.png", 200, 560);
    EZImage pylon4 = EZ.addImage("images/pylonexplode.png", 100, 450);

    // Pause and add more explosion pictures
    EZ.pause(1700);
    EZImage pylon5 = EZ.addImage("images/pylonexplode.png", 60, 310);
    EZImage pylon6 = EZ.addImage("images/pylonexplode.png", 210, 370);
    EZImage pylon7 = EZ.addImage("images/pylonexplode.png", 200, 560);

    EZ.pause(1000);

    // Remove the pictures
    EZ.removeEZElement(pylon1);
    EZ.removeEZElement(pylon2);
    EZ.removeEZElement(pylon3);
    EZ.removeEZElement(pylon4);
    EZ.removeEZElement(pylon5);
    EZ.removeEZElement(pylon6);
    EZ.removeEZElement(pylon7);
  }

  // Hit hatchery
  public void hatcheryhit() {
    // Play sound and add pictures
    sound.play();
    EZImage hatch1 = EZ.addImage("images/supergreennutsaura.png", 620, 410);
    EZImage hatch2 = EZ.addImage("images/unburied.png", 650, 400);
    EZImage hatch3 = EZ.addImage("images/lightning.png", 575, 275);

    EZ.pause(2000);

    // Remove the pictures
    EZ.removeEZElement(hatch1);
    EZ.removeEZElement(hatch2);
    EZ.removeEZElement(hatch3);
  }

  // Hit Nexus
  public void nexushit() {
    EZImage nexus1 = EZ.addImage("images/nexusexplosion.png", 435, 225);

    // Play nexus explosion sound
    sound.play();

    EZ.pause(1700);

    EZ.removeEZElement(nexus1);
  }

  // Hit cavern
  public void cavernhit() {
    // Spawn zergling and troll zergling
    EZImage cavern1 = EZ.addImage("images/ling.png", 1000, 100);

    // Add zergling sound and picture
    EZSound zergling = EZ.addSound("sounds/zergling.wav");
    zergling.play();

    EZImage cavern2 = EZ.addImage("images/trollling.png", 1050, 100);

    cavern2.scaleTo(1.6);
    EZ.pause(1000);

    EZ.removeEZElement(cavern1);
    EZ.removeEZElement(cavern2);
  }

  // Hit command center
  public void cchit() {
    //Add nuke picture and play sound
    EZImage cc1 = EZ.addImage("images/nuke.png", 600, 780);
    EZImage cc2 = EZ.addImage("images/thor.png", 450, 560);
    sound.play();

    EZ.pause(1700);

    EZImage cc3 = EZ.addImage("images/explosion1.png", 600, 780);
    EZ.pause(2000);

    EZ.removeEZElement(cc1);
    EZ.removeEZElement(cc2);
    EZ.removeEZElement(cc3);
  }

  // Hit arbiter building
  public void arbiterbuildinghit() {
    sound.play();
  }

  // Hit armory
  public void armorybuildinghit() {
    sound.play();
  }

  // Hit barracks
  public void barrackshit() {
    sound.play();
  }

  // Hit engineering bay
  public void bayhit() {
    sound.play();
  }

  // Hut hunker
  public void bunkerhit() {
    sound.play();
  }

  // Hit dtbuilding
  public void dtbuildinghit() {
    sound.play();
  }

  // Hit evolution chamber
  public void evochamberhit() {
    sound.play();
  }

  // Hit forge
  public void forgehit() {
    sound.play();
  }

  // Hit greentossbuilding
  public void greentossbuildinghit() {
    sound.play();
  }

  // Hit Spawning pool
  public void poolhit() {
    sound.play();
  }

  // Hit refinery
  public void refineryhit() {
    sound.play();
  }

  // Hit turret
  public void turrethit() {
    sound.play();
  }

  // Hit zerg building
  public void zergbuildinghit() {
    sound.play();
  }

  // Hit extractor
  public void extractorhit() {
    sound.play();
  }

  // Hit stasis cell
  public void stasiscellhit() {
    //int i, w, z, m ,n, q, h;
    //w = 800;
    //z = 975;
    //n = 1000;
    //q = 975;

    // Play bad manner sound
    sound.play();

    // Add blood picture
    EZImage stasiscell1 = EZ.addImage("images/blood.png", 1125, 800);

    EZ.pause(1700);
    EZ.removeEZElement(stasiscell1);

		/*
		//Loop to spell EZ on the screen
		for (i = 0; i < 10; i++)
		{
			EZ.addImage("depot.png", w += 5, 775);
			EZImage overlord3 = EZ.addImage("depot.png", w += 5, 875);
			EZImage overlord4 = EZ.addImage("depot.png", w += 5, 975);
			EZImage overlord5 = EZ.addImage("depot.png", 800, z -=17);
		
		}
		//Loop to spell EZ on the screen
		for (m = 0; m < 10; m++)
		{
			EZImage overlord6 = EZ.addImage("depot.png", n += 5, 775);
			EZImage overlord7 = EZ.addImage("depot.png", n += 5, 975);
			EZImage overlord8 = EZ.addImage("depot.png", n += 5, q -=18);
			if(m == 10)
			{
				EZ.removeEZElement(overlord6);
				EZ.removeEZElement(overlord7);
				EZ.removeEZElement(overlord8);
			}
		
		} 
		*/
  }
}
