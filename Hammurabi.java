
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
  int price;
  int year;
  int landValue;


  public static void main(String[] args) {
    new Hammurabi().playGame();
  }

  public Hammurabi() {
    year = 1;
    population = 100;
    bushels = 2800;
    land = 1000;
    landValue = 19;
  }

 boolean gameOver() {
    return true;
  }
  void playGame() {
    // declare local variables here: grain, population, etc. (I declared them above so this method wouldn't be so busy)
    // statements go after the declations
    while(year < 10) { // while the year is less than 10 and gameOver != false (haven't created yet)
      printSummaryYearToYear();

      // Assuming land is bought,
      int totalPurchasedLand = askHowManyAcresToBuy(price, bushels); // store the purchased land in a variable
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


      year++; // year will increase when all of these methods are done
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
  public int askHowManyAcresToBuy(int price, int bushels) {
      int buyAcres = getNumber("How many acres would you like to buy? \n");
      while (bushels < price * buyAcres) {
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
    int feedBushels = getNumber("How much grain would you like to feed people? \n");
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
    // System.out.println("In the previous year, " + starvationDeaths + " starved to death.");
    //System.out.println("In the previous year, " + immigrants + " came to the city.");
    System.out.println("The population is now " + population + " .");
    // System.out.println("We harvested " + harvest + " at " + bushelsPerAcre + " bushels per acre.");
   // System.out.println("Rats destroyed); do this when you get here
    // System.out.println("The plague has killed "); do this when you get here
    System.out.println("The city now owns " + land + " acres of land.");
    System.out.println("The land is worth " + newCostOfLand() + "per acre.");
  }



 /* public int plagueDeaths(int i) {
    return i;
  } */

  public int starvationDeaths(int i, int i1) {
    return i;
  }

  /*public boolean uprising(int i, int i1) {
    return false;
  }

   */

  public int immigrants(int i, int i1, int i2) {
    return i;
  }

  public int harvest(int i) {
    return i;
  }

  /* public int grainEatenByRats(int i) {
    return i;
  } */

  public int newCostOfLand() {
    return 0;
  }

  //other methods go here
}
