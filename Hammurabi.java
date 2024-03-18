
package hammurabi;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
  Random rand = new Random();
  Scanner scanner = new Scanner(System.in);

  int population;
  int bushels;
  int land;
  int year;
  int landValue;
  int deadPeople;
  int starvedPeople;
  int feedBushels;
  int newPeople;
  boolean uprising;
  int totalHarvested;
  int harvestPerAcre;
  int eatenBushels;
  boolean gameOver;



  public static void main(String[] args) {
    new Hammurabi().playGame();
  }

  public Hammurabi() {
    year = 1;
    population = 100;
    bushels = 2800;
    land = 1000;
    landValue = 19;
    deadPeople = 0;
    starvedPeople = 0;
    feedBushels = 0;
    newPeople = 0;
    uprising = false;
    totalHarvested = 0;
    harvestPerAcre = 0;
    eatenBushels = 0;
    gameOver = false;

  }
  void gameOver() {
    gameOver = true;
  }
  void playGame() {
    // declare local variables here: grain, population, etc. (I declared them above so this method wouldn't be so busy)
    // statements go after the declations
    while(year < 11) { // while the year is less than 10 and gameOver != false (haven't created yet)
      printSummaryYearToYear();

      // Assuming land is bought,
      int totalPurchasedLand = askHowManyAcresToBuy(landValue, bushels); // store the purchased land in a variable
      land = addedLand(land, totalPurchasedLand); // make that = the current land
      bushels -= totalPurchasedLand * landValue; // bushels must decrease at this same rate
      System.out.println("Your total land is " + land);
      System.out.println("Your total bushels are " + bushels);

      // Assuming land is sold,
      int totalSoldLand = askHowManyAcresToSell(land);
      land = soldLand(land, totalSoldLand); // make that = the current land (basically same as above)
      bushels += totalSoldLand * landValue; // except bushels would INCREASE because you're getting bushels
      System.out.println("Your total land is " + land);
      System.out.println("Your total bushels are " + bushels);

      // Assuming you would like to feed people,
      int bushelsToFeed = askHowMuchGrainToFeedPeople(bushels);
      bushels -= bushelsToFeed; // bushels must decrease at this rate
      System.out.println("Your total bushels are " + bushels);

      // Assuming you would like to plant bushels,
      int bushelsToPLant = askHowManyAcresToPlant(land, population, bushels);
      bushels -= bushelsToPLant; // bushels must also decrease at this rate

      // If the plague happens,
      plagueDeaths();

      // If people starve,
      int totalStarved = starvationDeaths();

      // If there is an uprising,
      boolean uprisingOccured = uprising();
      if(uprisingOccured) {
        gameOver();
      }
      population -= totalStarved; // this has to happen after uprising

      // If immigrants come,
      population += immigrants(); // adds immigrants to population

      // If a harvest occurs,
      harvest(bushelsToPLant);

      // If a rat attack happens,
      grainEatenByRats();

      // When the land value increases,
      newCostOfLand();


      year++; // year will increase when all of these methods are done
      if (population < 15) {
        gameOver = true;
      }

      if (gameOver || year == 10) {
        if (uprisingOccured) {
          System.out.println("An uprising has occured due to " + starvedPeople + " people dying. You have been thrown out of office!");
        } else if (population < 15) {
          System.out.println("You have " + population + "people left! You have been thrown out of office!");
        }
        else {
          printEndSummary();
        }
        break;
      }
    }

  }



  int getNumber(String message) {
    while (true) {
      System.out.print(message);
      try {
        return scanner.nextInt();
      }
      catch (InputMismatchException e) {
        System.out.println("\"" + scanner.next() + "\" isn't a number!");
      }
    }
  }

  // PLAYER INPUT METHODS
  public int askHowManyAcresToBuy(int landValue, int bushels) {
      int buyAcres = getNumber("How many acres would you like to buy? \n");
      while (bushels < landValue * buyAcres) {
        buyAcres = getNumber("We only have " + bushels + "left!");
      }
    return buyAcres;
  }

  public int askHowManyAcresToSell(int acresOwned) {
    int sellAcres = getNumber("How many acres would you like to sell? \n");
    while (acresOwned < sellAcres) {
      sellAcres = getNumber("We only have " + acresOwned + "left!");
    }
    return sellAcres;
  }

  public int askHowMuchGrainToFeedPeople(int bushels) {
    feedBushels = getNumber("How much grain would you like to feed people? \n");
    while (bushels < feedBushels) {
      feedBushels = getNumber("You can't feed them more bushels than we have!");
    }
    return feedBushels;
  }

  public int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
    int acresToPlant = getNumber("How many acres would you like to plant grain with? \n");
    while ((acresToPlant / 10) > population) { // each person can only plant 10 bushels, so you would need 10 people for each acre. not sure if this is sound
      acresToPlant = getNumber("We simply do not have enough people for that.");
    }
    while (acresToPlant > acresOwned) { // you can't plant more acres than acres you have. also not sure if this is sound
      acresToPlant = getNumber("We simply do not have enough acres for that.");
    }
    while (acresToPlant > bushels) { // you can't plant more bushels than bushels you have.
      acresToPlant = getNumber("We simply do not have enough bushels for that.");
    }

    return acresToPlant;
  }

  // METHODS THAT REQUIRE MATH
  public int addedLand (int lastYearLand, int purchasedLand) { // this and the one below it run the input against what last year's land total was
    return lastYearLand + purchasedLand;
  }

  public int soldLand (int lastYearLand, int soldLand) {
    return lastYearLand - soldLand;
  }

  public void printSummaryYearToYear() { // anything commented out we'll get to later
    System.out.println("O Great Hammurabi!");
    System.out.println("You are in " + year + " year of your rule!");
    System.out.println("In the previous year, " + starvedPeople + " starved to death.");
    System.out.println("In the previous year, " + newPeople + " came to the city.");
    System.out.println("The population is now " + population + ".");
    System.out.println("We harvested " + totalHarvested + " at " + harvestPerAcre + " bushels per acre.");
    System.out.println("Rats destroyed " + eatenBushels + " of your grain!");
    System.out.println("The plague has killed " + deadPeople + " people.");
    System.out.println("The city now owns " + land + " acres of land.");
    System.out.println("The land is worth " + landValue + "per acre.");
  }



  public int plagueDeaths() {
    int deathChance = rand.nextInt(0, 100);
    if (deathChance < 15) {
      population = population / 2;
      deadPeople = population;
    } else {
      deadPeople = 0;
    }
    return deadPeople;
  }

  public int starvationDeaths() { // each person needs 20 bushels to survive
    starvedPeople = population - (feedBushels / 20);

    if (starvedPeople < 0) { // starved people cannot be negative so anything less than 0,
      starvedPeople = 0; // we set to 0
    }return starvedPeople;
  }

  public boolean uprising() { // these have to be floats because of odd numbers and decimals and things of that nature
    float starvedPeopleFloat = starvedPeople;
    float populationFloat = population;
    if (starvedPeopleFloat >= populationFloat * 0.45) {
      return true;
    } else {
      return false;
    }
  }

  public int immigrants() { // nobody will come to the city if people are starving
    if(starvedPeople > 0) {
      return newPeople = 0;
    }
    return newPeople = (20 * land + bushels) / (100 * population) + 1; // calculation that was in readMe
  }

  public int harvest(int plantedBushels) {
    harvestPerAcre = rand.nextInt(0, 6) + 1;
    if (plantedBushels > 0) {
      totalHarvested = harvestPerAcre * plantedBushels;
      bushels += totalHarvested;
    }
    return totalHarvested;
  }

  public int grainEatenByRats() {
    int ratChance = rand.nextInt(0, 100);
    if (ratChance <= 40) {
      int ratsAteChance = rand.nextInt(10, 31);
      eatenBushels = bushels / ratsAteChance;
      bushels = bushels - eatenBushels;
    }
    return eatenBushels;
  }

  public int newCostOfLand() {
    landValue = rand.nextInt(17, 24);
    return landValue;
  }

  void printEndSummary() {
    if (population >= 100 || bushels >= 2800 || land >= 1000) { // if you did better than what you started with
      System.out.println("O Great Hammurabi! You have done excellent as an emperor. \n We currently have " + population + " people, " + bushels + " grain in storage, and " + land + " acres of land.");
      System.out.println("You have truly outdone yourself! Please be our emperor forever!");
    }
    else {
      System.out.println("O Great Hammurabi. Your term is complete. \n We currently have " + population + " people," + bushels + " grain in storage, and " + land + " acres of land.");
      System.out.println("We humbly thank you for your service. You are welcome to run again next term.");
    }
  }
}
