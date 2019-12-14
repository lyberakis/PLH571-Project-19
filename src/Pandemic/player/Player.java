package Pandemic.player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Pandemic.Gameboard.GameBoard;
import Pandemic.actions.Action;
import Pandemic.actions.buildResearchStation;
import Pandemic.actions.charterFlight;
import Pandemic.actions.directFlight;
import Pandemic.actions.discoverCure;
import Pandemic.actions.driveCity;
import Pandemic.actions.shuttleFlight;
import Pandemic.actions.treatDisease;
import Pandemic.cities.City;
import Pandemic.variables.Disease;
import Pandemic.variables.Piece;
import Pandemic.variables.Variables;


public class Player{

  String playerName;
  int tactic;
  int diseasesCured=0; //statistics of the game variables
  String playerRole;
  GameBoard pandemicBoard;
  Piece playerPiece;
  int playerAction; //number of action per turn for each player
  boolean activePlayer;
  ArrayList<City>  hand ; //hand_cards maybe from action
  String[] possibleColour = {"Red","Blue","Yellow","Black"};
  ArrayList<Action> suggestions = new ArrayList<Action>();
  
  //------------for storing
  private ArrayList<City>   freezeCities = new ArrayList<City>();
	private  int f_redCube,f_blueCubes,f_yellowCubes,f_blackCubes;
	private  ArrayList<Disease> f_diseases = new ArrayList<Disease>();
	private  ArrayList<City>   f_ResearchStation = new ArrayList<City>();
	private  City f1_location,f2_location,f3_location,f4_location;
	//--------------to return

  /*
   * Constructor for objects of class Player
   */
  public Player(String pName,String pRole)
  {
      playerName = pName;
      hand = new ArrayList<City>();
      playerAction = 4;
      tactic = 50;
      playerRole = pRole;
  }

  public void setGameBoard(GameBoard currentBoard) { this.pandemicBoard = currentBoard;  }

  public void setPlayerPiece(Piece newPiece) { playerPiece = newPiece; }
  
  public ArrayList<Action> getSuggestions() { return suggestions; }

  public void setSuggestions(ArrayList<Action> suggestions) {this.suggestions = suggestions; }

  /*
  *draw @param numberOfCards  from playerPile and check if it is epidemic
  *and call @resolveEpidemic() or draw to  @hand()
  */
  public void drawCard(int numberOfCards)
  {
      // draws a card for the player from the board
      for (int i = 0; i<numberOfCards; i++)
      {
          if (pandemicBoard.playerPile.size()!= 0)
          {
        	 //first element from array list PlayerPile
              if (pandemicBoard.playerPile.get(0).equals(Variables.isEpidemic)) {            	  
            	  System.out.println("-----EPIDEMIC DRAWN!-----");
                  pandemicBoard.resolveEpidemic();//follow the steps for epidemic event
                  pandemicBoard.playerPile.remove(0);
                  break;
              }
              else 
              {
            	 //adds a new card to the players hand.
            	 hand.add((City) pandemicBoard.playerPile.get(0));
              }
              pandemicBoard.playerPile.remove(0);//remove the card from PlayerDeck
              System.out.println(this.getPlayerName() + " draws a card");
          }
          else
          {
              System.out.println("no more cards left");
          }
      }
  }


  /*
   * Count how many cards with a specific colour 
   * player has in his hands 
   * @param colour
   */
  public int getCountXCards(String colour)
  {
      int toReturn = 0;
      for (int i = 0 ; i < hand.size(); i++)
      {
          if (hand.get(i).getColour().equals(colour))
          {
              toReturn ++;
          }
      }
      return toReturn;
  }
  
  /*
   * remove a card from hand ,
   * then calls methods to put it in the discard pile()
   * and remove the card from the hand.
   */
  public void discardCard(String cardName)
  {
    int toDiscard =0;
	for (int i = 0; i < getHand().size(); i++)
	{
	   if (cardName.equals(getHand().get(i).getName())){
	    //System.out.println("found matching card in hand");
		toDiscard=i;   
	   }
	}
	pandemicBoard.addPlayerCardDiscard(hand.get(toDiscard));//remove from playerDeck to put in PlayerDiscardDeck
    hand.remove(toDiscard);
  }
  
  //discard all the cards needed for cure
  public void discardCureCards(String colour,int numberToDiscard)
  {
      for (int i = 0 ; i < numberToDiscard ; i ++)
      {
          for (int x = 0 ; x < hand.size(); x++ )
          {
              if (hand.get(x).getColour().equals(colour))
              {
                  discardCard(hand.get(x).getName());
                  break;
              }
          }
      }
  }
  
  // get PlayerAction 
  public int getPlayerAction(){ return playerAction; }
  
  // decrease the playerAction (Max=4)
  public void decreasePlayerAction(){ playerAction--;}

  // sets a players action back to 4
  public void resetPlayerAction() { playerAction=4;  }

  // return the name of player
  public String getPlayerName() { return playerName; }

  // Returns an array with the players cards in hand
  public ArrayList<City> getHand() { return hand;    }
  
  public String getPlayerRole() {return playerRole;	  }

  public void setPlayerRole(String playerRole) {this.playerRole = playerRole; }

  public Piece getPlayerPiece() { return playerPiece;	}    
  
  /**********************************************************************************
  *******These are the main (7+4) methods used to control the players actions********
  **********************************************************************************/
  
//Build research station as OPERATIONS_EXPERT

  
  //Build research station
  public boolean buildResearchStation ()
  {
	  
	  buildResearchStation tmp = new buildResearchStation(playerPiece.getLocation(),getHand());
	  if (playerRole.equals("OPERATIONS_EXPERT") && !Variables.CITY_WITH_RESEARCH_STATION.contains(playerPiece.getLocation())) 
	  {
		  Variables.ADD_CITY_WITH_RESEARCH_STATION(playerPiece.getLocation());
          decreasePlayerAction();
          System.out.println("building a research station in " + playerPiece.getLocation().getName());
          suggestions.add(tmp);
          return true;
	  }
	  else
	  {
	      if (tmp.isaLegalMove())
	      {
	          discardCard(playerPiece.getLocation().getName());
	          Variables.ADD_CITY_WITH_RESEARCH_STATION(playerPiece.getLocation());
	          decreasePlayerAction();
	          //System.out.println("building a research station in " + playerPiece.getLocation().getName());
	          suggestions.add(tmp);
	          return true;
	      }
	  }
      return false;
  }
  
//Treat disease action
 public boolean treatDisease (City location, String colour)
 {
	 treatDisease tmp = new treatDisease(location,colour);
	 if (playerRole.equals("MEDIC")) {
	     if(tmp.isaLegalMove()==true && location == playerPiece.getLocation())
	     {
	    	 System.out.println("Removing all " + colour + " cube from " + location.getName());
	    	 for (int i=0;i<location.getCubeColour(colour);i++) {
	        	 location.removeCube(colour);
	             pandemicBoard.addCube(colour);//add in pool of cube             
	         }
	         decreasePlayerAction();
	         suggestions.add(tmp);
	         return true;
	     }
	 }
	 else {
		 if(tmp.isaLegalMove()==true && location == playerPiece.getLocation())
	     {
	         System.out.println("Removing a " + colour + " cube from " + location.getName());
	         location.removeCube(colour);
	         pandemicBoard.addCube(colour);//add in pool of cube
	         decreasePlayerAction();
	         suggestions.add(tmp);
	         return true;
	     }
	 }
     return false;
 }
  
  
  
  // Drive action
  public boolean driveCity (City location, City destination)
  {
	  System.out.println(getPlayerName() + " current location is in " + location);
	  System.out.println("and he wants to go in " + destination);
	  // System.out.println(location.getMaxCube());
	  // System.out.println(destination.getMaxCube());
      // System.out.println("attempting to move " + getPlayerName() + " to " + destination.getName() + " from "+ location.getName());
	  driveCity tmp = new driveCity(location,destination);
      if (tmp.isaLegalMove())
      {    	  
          System.out.println(getPlayerName() + " drives from " + location.getName() + " to " + destination.getName() + ".");
          playerPiece.setLocation(destination);
          decreasePlayerAction();
          suggestions.add(tmp);
          return true;
      }
      else 
      {
          System.out.println("the location isn't connected");
      }
      return false;
  }
  
  // Charter Flight action
  public boolean charterFlight(City location, City destination)
  {
	  // System.out.println(getPlayerName() + " wants to flying from " + 
	  // location.getName() + " to "+ destination.getName() + 
	  // " on a charter flight");
	  charterFlight tmp = new charterFlight(location,getHand(), destination);
      if (tmp.isaLegalMove() && playerPiece.getLocation().equals(location))
      {
          System.out.println(getPlayerName() + " takes a charter flight to " + 
          destination.getName() + " from " + location.getName() );
          discardCard(location.getName());
          playerPiece.setLocation(destination);
          decreasePlayerAction();
          suggestions.add(tmp);
          return true;
      }
      return false;
  }
  
  //Direct flight
  public boolean directFlight(City location, City destination)
  {
	// System.out.println(getPlayerName() + " wants to flying from " + 
	// location.getName() + " to "+ destination.getName() + 
	// " on a direct flight");
	  directFlight tmp = new directFlight(destination,getHand());
	  if (tmp.isaLegalMove())
	  {
		  System.out.println(getPlayerName() + " takes a direct flight to " + 
		  destination.getName() + " from " + location.getName() );
		  discardCard(destination.getName());
		  playerPiece.setLocation(destination);
		  decreasePlayerAction();
		  suggestions.add(tmp);
          return true;
	  }
	  return false;
  }
  
  //ShuttleFlight
  public boolean shuttleFlight(City location, City destination)
  {
	// System.out.println(getPlayerName() + " wants to flying from " + 
	// location.getName() + " to "+ destination.getName() + 
	// " on a shuttle flight");
	 shuttleFlight tmp = new shuttleFlight(location,destination);
	 if(tmp.isaLegalMove())
	 {
		 System.out.println(getPlayerName() + " takes a shuttle flight to " + 
		 destination.getName() + " from " + location.getName() );
		 playerPiece.setLocation(destination);
		 decreasePlayerAction();
		 suggestions.add(tmp);
         return true;
	 }
	 return false;
  }
  
  // Discover Cure action
  public boolean discoverCure(City location, String colour)
  { 
	 discoverCure tmp = new discoverCure(location,getHand(),colour);
	 if (playerRole.equals("SCIENTIST")) {
	    if (tmp.isaLegalMove(1))
		{
		   System.out.println("Its possible to discover a cure");
		   discardCureCards(colour,(pandemicBoard.getNeededForCure()-1));
	       pandemicBoard.cureDisease(colour);
	       decreasePlayerAction();
	       suggestions.add(tmp);
	       return true;
		}
	 }
	 else
	 {
		if (tmp.isaLegalMove(0))
		{
		   System.out.println("Its possible to discover a cure");
		   discardCureCards(colour,pandemicBoard.getNeededForCure());
	       pandemicBoard.cureDisease(colour);
	       decreasePlayerAction();
	       suggestions.add(tmp);
	       return true;
		}
	 }
	 return false;
  }
  
  
  /*
   *Below are implemented
   *1) --> OPERATIONS_EXPERT special ability (build research station without to discard a card) is implemented as if in the @BuildResearchStaion method
   *2) --> MEDIC special ability  (treat all the cubes of a specific colour) implemented in @treatDisease as if statement
   *3) --> SCIENTIST special ability is implemented as if in the classic cure disease
   *4) --> QUARANTINE_SPECIALIST special ability is implemented in @infectCityPhase in @GameBoard class 
   */  
  
  //---------------------------------------------------------------------------------
  /**********************************************************************************
  ***************** These methods are used for AI controlled players.****************
  **********************************************************************************/
  //---------------------------------------------------------------------------------
  

  private void decideDoctor() {
    if (!tryTreat(3)) {
        if (!go3CubeCities()) {
            tryTreat(2);
            if (!go2CubeCities()) {
                tryTreat(1);
                if (!go1CubeCities()) {
                    driveRandom();
                }
            }
        }
     }
  }


  private void decideScientist() {
    decideDoctor();
  }

  void decideOpsExpert() {
    City hub = getDiseaseHub();

    if(hub == null) {
        decideDoctor();
        return;
    }

    if (playerPiece.location.getName().equals(hub.getName())) {
        if(!buildResearchStation()) {
            decideDoctor();
            return;
        }
        else {
            return;
        }
    }

    getDistances(new ArrayList<City>(Arrays.asList(hub)));
    City toDriveTo = calculateDestination();

    if (!driveCity(playerPiece.location, toDriveTo)) {
        decideDoctor();
    }
    
  }

  private void decideQuarantineSpecialist() {
    City hub = getDiseaseHub();
    
    if(hub == null) {
        decideDoctor();
        return;
    }

    if (playerPiece.location.getName().equals(hub.getName())) {
        decideDoctor();
        return;
    }

    getDistances(new ArrayList<City>(Arrays.asList(hub)));
    City toDriveTo = calculateDestination();

    if (!driveCity(playerPiece.location, toDriveTo)) {
        decideDoctor();
    }
  }
  
  public void makeDecision(ArrayList<City>  friend_hand, String Role)
  {
	  // driveCity(playerPiece.getLocation(),playerPiece.getLocation().getNeighbors().get(0)); 
	  //take Variables.Suggestions and build model for others player

	  // This is going to be called 4 times
      if (friend_hand == null) { // Our turn
        // freeze();
        System.out.println("Util: " + evaluate(this.pandemicBoard));
        // decideDoctor();
        if(!checkTryCure()) {
            switch (Role) {
                case "MEDIC":
                    decideDoctor();
                    break;
                case "SCIENTIST":
                    decideScientist();
                    break;
                case "OPERATIONS_EXPERT":
                    decideOpsExpert();
                    break;
                case "QUARANTINE_SPECIALIST":
                    decideQuarantineSpecialist();
                    break;
                default:
                    decideDoctor();
                    break;
            }
        }
        
        // hand
        // ArrayList<City> neighbors = playerPiece.getLocationConnections();

        // unfreeze();
		  // ==============================================================================
		//   System.out.println(Variables.Suggestions.length);
		//   for (ArrayList<Action> suggestion : Variables.Suggestions) {
			  // Variables.Suggestions is an array of 4 arrays
			  // Variables.Suggestions has an empty array (current user's)
       		  // suggestion is an array of 4 Actions
			//   System.out.println(suggestion);			  
		//   }
		  
		  // Getting info for the players
		  // this.pandemicBoard.playerPieces[0].owner.playerName;
		  // ==============================================================================
		  
		  // Strategy...
		//   System.out.print(this.getPlayerName() + " is thinking..... ");
	    //   boolean checkCure = checkCureWorthIt();
	    //   if (checkCure)
	    //   {
	    //       System.out.println("might be worth trying to find a cure.");
	    //       checkTryCure();
	    //   }
	    //   if (!checkCure && (getDistanceResearch() > 3) && (tactic > 0) )
	    //   {
	    //       tactic--;
	    //       System.out.print("They are far enough from a research station to consider building one.");
	    //       if (!buildResearchStation())
	    //       {
	    //           System.out.println(" Can't find the required card.");
	    //           rollDice();
	    //       }
	    //   }
	    //   else if (!checkCure)
	    //   {
	    // 	  rollDice();
	    // 	  rollDice();
	    // 	  rollDice();
	    // 	  rollDice();
	    //       tactic--;
	    //   }
	      
	    //   if (tactic < -500 )
	    //   {
	    //       System.out.println("out of ideas, will drive randomly");
	    //       driveRandom();
	    //   }
	    //   tactic--;

	  } else { // Send a suggestion
		  directFlight tmp = new directFlight(this.playerPiece.location.getNeighbors().get(0), this.getHand());
		  suggestions.add(tmp);
		  decreasePlayerAction(); // Player has only 4 moves!
      }      
  }
  
  
  private City getDiseaseHub() {
      City hub = null;
      int maxCubes = 0;
      for (City city : pandemicBoard.get3CubeCities()) {
          int cubes = city.getMaxCube();
          for (City neighbor : city.getNeighbors()) {
            cubes += neighbor.getMaxCube();
          }

          if (cubes > maxCubes) {
            maxCubes = cubes;
            hub = city;
          }
      }

      return hub;
  }


  private float evaluate(GameBoard board) {
        float discountFactor = 1.0f;
        // float diseaseCountMod = (board.outbreakCount+1)/Variables.MAX_NUMBER_OF_OUTBREAK;
        float diseaseCountMod = (board.outbreakCount+1);
        discountFactor -= (0.02*diseaseCountMod) * board.get2CubeCities().size();
        discountFactor -= (0.01*diseaseCountMod) * board.get1CubeCities().size();
        discountFactor -= (0.03*diseaseCountMod) * board.get3CubeCities().size();
        
        discountFactor += (0.05) * board.getResearchCentre().size();
        for (Disease disease : board.diseases) {
            // modify for disease color
            if (disease.cured) {
                discountFactor += (0.1);
            }
            else {
                discountFactor -= (0.1);
            }
        }

        int numberOfCards = board.playerPile.size();
        if (numberOfCards <= 44 && numberOfCards >= 34) {
            // discountFactor -= (0.01);
            }
        else if (numberOfCards <= 33 && numberOfCards >= 24) {
            discountFactor -= (0.01);
        }
        else if (numberOfCards <= 23 && numberOfCards >= 14) {
            discountFactor -= (0.25);
        }
        else {
            discountFactor -= (0.5);
        }

        return 100.f*discountFactor;
  }

  private void freeze() {	    
   	freezeCities  = (ArrayList<City>) pandemicBoard.getCities().clone();
	  f_redCube         = pandemicBoard.redCubes;
	  f_blueCubes       = pandemicBoard.blueCubes;
	  f_yellowCubes     = pandemicBoard.yellowCubes;
	  f_blackCubes      = pandemicBoard.blackCubes;
	  f_diseases        = (ArrayList<Disease>) pandemicBoard.getDiseases().clone();
	  f_ResearchStation = (ArrayList<City>) pandemicBoard.getResearchCentre().clone();
	  f1_location		= pandemicBoard.playerPieces[0].getLocation();
	  f2_location		= pandemicBoard.playerPieces[1].getLocation();
	  f3_location   	= pandemicBoard.playerPieces[2].getLocation();
	  f4_location   	= pandemicBoard.playerPieces[3].getLocation();
   
   }
   
  private void unfreeze() {	    
	  pandemicBoard.cities      = freezeCities;
		pandemicBoard.redCubes    = f_redCube;
		pandemicBoard.blueCubes   = f_blueCubes;  
		pandemicBoard.yellowCubes = f_yellowCubes;
		pandemicBoard.blackCubes  = f_blackCubes;
		pandemicBoard.diseases	= f_diseases;		  
		pandemicBoard.playerPieces[0].setLocation(f1_location);
		pandemicBoard.playerPieces[1].setLocation(f2_location);
		pandemicBoard.playerPieces[2].setLocation(f3_location);
		pandemicBoard.playerPieces[3].setLocation(f4_location);
		Variables.CITY_WITH_RESEARCH_STATION = f_ResearchStation;
  }
  
  public void printStats() {
	  System.out.println("Diseases cured: "+diseasesCured);
	  System.out.println();
  }

//Player will either treat disease or go to a city with 3 cubes.
  public void rollDice()
  {
      System.out.print("Wants to treat disease... ");
      if (!tryTreat(3)) {
         if (!go3CubeCities()) {
             tryTreat(2);
             if (!go2CubeCities()) {
                 tryTreat(1);
                 if (!go1CubeCities()) {
                     System.out.println("Going to drive randomly as can't think of anything.");
                     driveRandom();
                 }
             }
         }
      }                
  }

  //Check to see if the disease can be treat according to the @param threshold of cubes.
  public boolean tryTreat(int threshold)
  {
      boolean toReturn = false;
      City locationCity = playerPiece.getLocation();
      if (locationCity.getMaxCube() >= threshold)
      {
          System.out.println("As there are " + locationCity.getMaxCube() + " cubes in " + locationCity.getName() + " " + this.getPlayerName() + " will try and treat disease.");
          String locationColour = locationCity.getColour();
          treatDisease(locationCity,locationColour);
          toReturn = true;
      }
      else 
      {
          System.out.println("Doesn't think it's worth trying to treat here.");
      }
      return toReturn;
      
  }
  
  public boolean checkTryCure()
  {
      if (checkCureWorthIt()) {
          if (discoverCure(playerPiece.getLocation(),tryCureCardColour())) {
              System.out.println(this.getPlayerName() + " has discovered a cure!");
              System.out.println("Yeah!!");
              System.out.println("  Yeah!!");
              System.out.println("    Yeah!!");    
              diseasesCured++;
              return true;
          }
          else{
              if (playerRole.equals("OPERATIONS_EXPERT") && buildResearchStation()) {
                  return true;
              }
              else {
                System.out.println("They need to go to a researh station.");
                tryDriveResearchStation();
                return true;
              }
              
          }
      }
      else{
         System.out.println("no point in trying to find a cure.");
      }
      return false;
  }
  
  //evaluate the chance for cure , (if is it worth)
  public boolean checkCureWorthIt()
  {
      String toCure = tryCureCardColour();
      if (toCure != null)
      {
          for (int i = 0 ; i < pandemicBoard.diseases.size() ; i ++){
        	  
              Disease disease = pandemicBoard.diseases.get(i);
              if (toCure == disease.getColour() && !disease.getCured()){
                  return true;
              }
          }          
      }
      return false;      
  }
  
  //an attempt to drive to nearest research station
  public void tryDriveResearchStation()
  {
      System.out.println("Searching cities with research stations as destinations.");
      getDistances(Variables.GET_CITIES_WITH_RESEARCH_STATION());
      // System.out.println("Calculating destination");
      City toDriveTo = calculateDestination();
      //System.out.println("I'll try to drive to " + toDriveTo.getName());
      driveCity(playerPiece.getLocation(),toDriveTo); 
  }
  
  
	
  /** 
   * Check to see if the count of cards in any color equal the number required
   * for a cure
  **/
  public String tryCureCardColour()
  {
      for (int i = 0; i < pandemicBoard.getNumberColours(); i ++)
      {
          if (getCountXCards(possibleColour[i]) >= pandemicBoard.getNeededForCure())
          {
              return possibleColour[i];
          }           
      }
      return null;
  }
  
  //get distance 
  public int getDistanceResearch()
  {
      getDistances(Variables.GET_CITIES_WITH_RESEARCH_STATION());
      return playerPiece.getLocation().getDistance();
  }
  
  //get distances of the cities which a look to travel
  public void getDistances(ArrayList<City> destinations){
      pandemicBoard.resetDistances();
      setDestination(destinations);
      int distance = 1;
      int loc=-1;
      for(int i = 0 ; i < pandemicBoard.cities.size() ; i ++){
        if (pandemicBoard.cities.get(i).getName().equals( playerPiece.getLocation().getName() )){
            loc = i;
            break;
          }
      }
      while (pandemicBoard.cities.get(loc).getDistance() == 9999999){
          // System.out.println("Looking for places distance of " + distance);
          for (int i = 0 ; i < pandemicBoard.cities.size() ; i ++){
        	  for (int x = 0 ; x < pandemicBoard.cities.get(i).getNeighbors().size() ; x ++){
                  if ( pandemicBoard.cities.get(i).getDistance() == (distance-1) && pandemicBoard.cities.get(i).getNeighbors().get(x).getDistance() > distance ){
                      pandemicBoard.cities.get(i).getNeighbors().get(x).setDistance(distance);
                  }
              }
          }
          distance ++;
      }
  }
  
  public void setDestination(ArrayList<City> destinations)
  {
      for (int i = 0 ; i < destinations.size() ; i ++)
      {
          destinations.get(i).setDistance(0);
      }        
  }
  
  // Try to random charter flight
  public void charterRandom(){
      Random rand = new Random();
      int n = rand.nextInt(pandemicBoard.cities.size());
      charterFlight(playerPiece.getLocation(),pandemicBoard.cities.get(n));
  }
  
  //try to drive to random city until a possible city is chosen.
  public void driveRandom(){
	  Random rand1 = new Random();
	  City temp = playerPiece.getLocation();
      int n = rand1.nextInt(temp.getNeighbors().size());
      driveCity(playerPiece.getLocation(),temp.getNeighbors().get(n));
  }
  
  public City calculateDestination()
  {
      int closestDestination = 9999999;
      City toReturn = new City(0,0,0,0,0);
      for (int i = 0 ; i < playerPiece.getLocation().getNeighbors().size(); i++)
      {
          if (playerPiece.getLocation().getNeighbors().get(i).getDistance() < closestDestination)
          {
              //System.out.println("Will probably go to " + playerPiece.getLocationConnections()[i].getName());
              toReturn = playerPiece.getLocation().getNeighbors().get(i);
              closestDestination = playerPiece.getLocation().getNeighbors().get(i).getDistance();
          }
          
      }
      return toReturn;
  }
  
  //find all the cities with 3 cubes and measure distances to make a decision 
  //in which city to drive
  public boolean go3CubeCities()
  {        
	  ArrayList<City> CitiesWith3Cubes =  pandemicBoard.get3CubeCities();
      if (CitiesWith3Cubes.size() > 0)
      {
          //System.out.print("Setting 3 cube cities ");
//          for (int i = 0 ; i < CitiesWith3Cubes.size() ; i ++)
//          {
//              System.out.print("#:"+(i+1) + " " + CitiesWith3Cubes.get(i).getName());
//          }
         
          getDistances(CitiesWith3Cubes);
          //System.out.println(" as destinations.");
          City toDriveTo = calculateDestination();
          //System.out.println(this.getPlayerName() + " will go to " + toDriveTo.getName());
          driveCity(playerPiece.getLocation(),toDriveTo);
          return true;
      }
      else
      {
          System.out.println("No 3 cube cities.");
          return false;
      }
  }
  
  //find all the cities with 2 cubes 
  //measure distance and drive to best case (city)
  public boolean go2CubeCities()
  {        
	  ArrayList<City> CitiesWith2Cubes = pandemicBoard.get2CubeCities();
      if (CitiesWith2Cubes.size() > 0)
      {
          //System.out.print("Setting 2 cube cities ");
//          for (int i = 0 ; i < CitiesWith2Cubes.size() ; i ++)
//          {
//             System.out.print("#:"+(i+1) + " " + CitiesWith2Cubes.get(i).getName());
//          }
          getDistances(CitiesWith2Cubes);
          //System.out.println(" as destinations.");
          City toDriveTo = calculateDestination();
          //System.out.println(this.getPlayerName() + " will go to " + toDriveTo.getName());
          driveCity(playerPiece.getLocation(),toDriveTo);
          return true;
      }
      else
      {
          //System.out.println("No 2 cube cities.");
          return false;
      }
  } 
  
  //find all the cities with 1 cubes 
  //measure distance and drive to best case (city)
  public boolean go1CubeCities()
  {        
	  ArrayList<City> CitiesWith1Cubes = pandemicBoard.get1CubeCities();
      if (CitiesWith1Cubes.size() > 0)
      {
          //System.out.print("Setting 1 cube cities ");
//          for (int i = 0 ; i < CitiesWith1Cubes.size() ; i ++)
//          {
//              System.out.print("#:"+(i+1) + " " + CitiesWith1Cubes.get(i).getName());
//          }
          getDistances(CitiesWith1Cubes);
          //System.out.println(" as destinations.");
          City toDriveTo = calculateDestination();
          //System.out.println(this.getPlayerName() + " will go to " + toDriveTo.getName());
          driveCity(playerPiece.getLocation(),toDriveTo);
          return true;
      }
      else
      {
          //System.out.println("No 1 cube cities.");
          return false;
      }
  }

  
  
}
