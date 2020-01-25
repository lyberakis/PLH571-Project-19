package Pandemic.Gameboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import Pandemic.actions.driveCity;
import Pandemic.cities.City;
import Pandemic.player.Player;
import Pandemic.variables.Disease;
import Pandemic.variables.Piece;
import Pandemic.variables.Variables;

public class SimulatePandemic {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("starting new game");
        SimulatePandemic testgame;
        Player jesus;
        Player unabomber;
        Player jack;
        Player deadpool;

        String agent = "default";
        if (args.length >= 1) {
            agent = args[0];
        }

        unabomber = new Player("Unabomber", "QUARANTINE_SPECIALIST", agent);
        jack = new Player("Jesus", "OPERATIONS_EXPERT", agent);
        jesus = new Player("Jack the Ripper", "SCIENTIST", agent);
        deadpool = new Player("Zodiac Killer", "MEDIC", agent);
        Player[] currentPlayers;
        currentPlayers = new Player[4];
        currentPlayers[0] = jesus;
        currentPlayers[1] = unabomber;
        currentPlayers[2] = jack;
        currentPlayers[3] = deadpool;

        testgame = new SimulatePandemic(currentPlayers);

        testgame.playGame();

        int totalDiseasesCured = testgame.totalDiseasesCured;
        ArrayList<HashMap<String, Integer>> diseasesCured = testgame.diseasesCured;
        ArrayList<HashMap<String, Integer>> numberOfCards = testgame.numberOfCards;
        ArrayList<HashMap<String, Integer>> cubesRemain = testgame.cubesRemain;
        ArrayList<HashMap<String, Integer>> cubesPerCity = testgame.cubesPerCity;
        ArrayList<HashMap<String, String>> playerLocations = testgame.playerLocations;
        ArrayList<Integer> outbreaks = testgame.outbreaks;
        ArrayList<Integer> cardsLeft = testgame.cardsLeft;
        int totalOutbreaks = testgame.totalOutbreaks;
        ArrayList<ArrayList<String>> researchStations = testgame.researchStations;
        int numberOfRounds = testgame.numberOfRounds;
        boolean gameWon = SimulatePandemic.gameWon;
        int lossReason = testgame.lossReason;

        if (false) {

            for (int i = 0; i < numberOfRounds; i++) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Round " + (i + 1)
                        + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                int printCount = 0;
                int maxLength = 0;
                for (String player : diseasesCured.get(i).keySet()) {
                    if (player.length() > maxLength) {
                        maxLength = player.length();
                    }
                }
                maxLength = (Math.floorDiv(maxLength, 8) + 1) * 8;

                System.out.printf("%47s\n", "Diseases cured:");

                for (String player : diseasesCured.get(i).keySet()) {
                    System.out.print(player);
                    for (int j = 0; j < Math.floorDiv(maxLength - player.length(), 8)
                            + ((((maxLength - player.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                for (Integer diseases : diseasesCured.get(i).values()) {
                    System.out.print(diseases);
                    for (int j = 0; j < Math.floorDiv(maxLength - diseases.toString().length(), 8)
                            + ((((maxLength - diseases.toString().length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                System.out.printf("%51s\n", "Cards remain in deck: " + cardsLeft.get(i));
                System.out.printf("%47s\n", "Number of cards:");

                for (String player : numberOfCards.get(i).keySet()) {
                    System.out.print(player);
                    for (int j = 0; j < Math.floorDiv(maxLength - player.length(), 8)
                            + ((((maxLength - player.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                for (Integer numOfCards : numberOfCards.get(i).values()) {
                    System.out.print(numOfCards);
                    for (int j = 0; j < Math.floorDiv(maxLength - numOfCards.toString().length(), 8)
                            + ((((maxLength - numOfCards.toString().length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                System.out.printf("%47s\n", "Player location:");

                for (String player : playerLocations.get(i).keySet()) {
                    System.out.print(player);
                    for (int j = 0; j < Math.floorDiv(maxLength - player.length(), 8)
                            + ((((maxLength - player.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                for (String location : playerLocations.get(i).values()) {
                    System.out.print(location);
                    for (int j = 0; j < Math.floorDiv(maxLength - location.length(), 8)
                            + ((((maxLength - location.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                System.out.printf("%53s\n", "Research station locations:");

                for (String station : researchStations.get(i)) {
                    System.out.print(station);
                    for (int j = 0; j < Math.floorDiv(maxLength - station.length(), 8)
                            + ((((maxLength - station.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                System.out.printf("%47s\n", "Cubes remaining:");

                for (String color : cubesRemain.get(i).keySet()) {
                    System.out.print(color);
                    for (int j = 0; j < Math.floorDiv(maxLength - color.length(), 8)
                            + ((((maxLength - color.length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                for (Integer num : cubesRemain.get(i).values()) {
                    System.out.print(num);
                    for (int j = 0; j < Math.floorDiv(maxLength - num.toString().length(), 8)
                            + ((((maxLength - num.toString().length()) % 8) != 0) ? 1 : 0); j++) {
                        System.out.print("\t");
                    }
                }
                System.out.print("\n");

                System.out.printf("%47s\n", "Cubes per city:");

                for (String city : cubesPerCity.get(i).keySet()) {
                    if (cubesPerCity.get(i).get(city).equals(Integer.valueOf(3))) {
                        System.out.print(city);
                        for (int j = 0; j < Math.floorDiv(maxLength - city.length(), 8)
                                + ((((maxLength - city.length()) % 8) != 0) ? 1 : 0); j++) {
                            System.out.print("\t");
                        }
                        printCount++;
                        if (printCount >= 6) {
                            System.out.println("");
                            for (int j = 0; j < printCount; j++) {
                                System.out.print(3);
                                for (int k = 0; k < Math.floorDiv(maxLength - 1, 8)
                                        + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                                    System.out.print("\t");
                                }
                            }
                            System.out.print("\n");
                            printCount = 0;
                        }
                    }
                }
                System.out.print("\n");

                for (int j = 0; j < printCount; j++) {
                    System.out.print(3);
                    for (int k = 0; k < Math.floorDiv(maxLength - 1, 8) + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                        System.out.print("\t");
                    }
                }

                if (printCount > 0) {
                    System.out.print("\n");
                }

                printCount = 0;
                for (String city : cubesPerCity.get(i).keySet()) {
                    if (cubesPerCity.get(i).get(city).equals(Integer.valueOf(2))) {
                        System.out.print(city);
                        for (int j = 0; j < Math.floorDiv(maxLength - city.length(), 8)
                                + ((((maxLength - city.length()) % 8) != 0) ? 1 : 0); j++) {
                            System.out.print("\t");
                        }
                        printCount++;
                        if (printCount >= 6) {
                            System.out.println("");
                            for (int j = 0; j < printCount; j++) {
                                System.out.print(2);
                                for (int k = 0; k < Math.floorDiv(maxLength - 1, 8)
                                        + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                                    System.out.print("\t");
                                }
                            }
                            System.out.print("\n");
                            printCount = 0;
                        }
                    }
                }
                System.out.print("\n");

                for (int j = 0; j < printCount; j++) {
                    System.out.print(2);
                    for (int k = 0; k < Math.floorDiv(maxLength - 1, 8) + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                        System.out.print("\t");
                    }
                }
                if (printCount > 0) {
                    System.out.print("\n");
                }

                printCount = 0;
                for (String city : cubesPerCity.get(i).keySet()) {
                    if (cubesPerCity.get(i).get(city).equals(Integer.valueOf(1))) {
                        System.out.print(city);
                        for (int j = 0; j < Math.floorDiv(maxLength - city.length(), 8)
                                + ((((maxLength - city.length()) % 8) != 0) ? 1 : 0); j++) {
                            System.out.print("\t");
                        }
                        printCount++;
                        if (printCount >= 6) {
                            System.out.println("");
                            for (int j = 0; j < printCount; j++) {
                                System.out.print(1);
                                for (int k = 0; k < Math.floorDiv(maxLength - 1, 8)
                                        + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                                    System.out.print("\t");
                                }
                            }
                            System.out.print("\n");
                            printCount = 0;
                        }
                    }
                }
                System.out.print("\n");

                for (int j = 0; j < printCount; j++) {
                    System.out.print(1);
                    for (int k = 0; k < Math.floorDiv(maxLength - 1, 8) + ((((maxLength - 1) % 8) != 0) ? 1 : 0); k++) {
                        System.out.print("\t");
                    }
                }
                if (printCount > 0) {
                    System.out.print("\n");
                }

                System.out.printf("%47s\n", "Outbreaks this round: " + outbreaks.get(i));

            }
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("%40s\n", ((gameWon) ? "ITS A WIN!" : "BOO HOO!"));
        System.out.printf("%35s%35s\n", "Outbreaks this game: " + totalOutbreaks,
                "Diseases cured this game: " + totalDiseasesCured);

        if (lossReason == 0) {
            System.out.printf("%60s\n", "Lost because the deck run out of cards.");
        } else if (lossReason == 1) {
            System.out.printf("%60s\n", "Lost because there where too many outbreaks.");
        } else if (lossReason == 2) {
            System.out.printf("%60s\n", "Lost the game run out off cubes to use.");
        }

        if (args.length == 2) {
            try {
                FileWriter writter = new FileWriter(args[1], true);
                writter.write(gameWon + "," + lossReason + "\n");
                writter.close();
            } catch (IOException e) {
                System.out.println("SimulatePandemic.main()");
                return;
            }
        }
    }

    // ------------for stats
    public int totalDiseasesCured = 0;
    public ArrayList<HashMap<String, Integer>> diseasesCured = new ArrayList<HashMap<String, Integer>>();
    public ArrayList<HashMap<String, Integer>> numberOfCards = new ArrayList<HashMap<String, Integer>>();
    public ArrayList<HashMap<String, Integer>> cubesRemain = new ArrayList<HashMap<String, Integer>>();
    public ArrayList<HashMap<String, Integer>> cubesPerCity = new ArrayList<HashMap<String, Integer>>();
    public ArrayList<HashMap<String, String>> playerLocations = new ArrayList<HashMap<String, String>>();
    public ArrayList<Integer> outbreaks = new ArrayList<Integer>();
    public ArrayList<Integer> cardsLeft = new ArrayList<Integer>();
    public int totalOutbreaks = 0;
    public ArrayList<ArrayList<String>> researchStations = new ArrayList<ArrayList<String>>();
    public int numberOfRounds = 0;
    public int lossReason = -1;
    // ---------------------

    // ------------for storing
    private int f_redCube, f_blueCubes, f_yellowCubes, f_blackCubes;
    private ArrayList<City> f_ResearchStation = new ArrayList<City>();
    private boolean cured[] = new boolean[4];
    private boolean eliminated[] = new boolean[4];
    private City f1_location;
    private City f2_location;
    private City f3_location;
    private City f4_location;
    private int cubes[][] = new int[48][4];
    private boolean outs[] = new boolean[48];
    private int dist[] = new int[48];
    private int infect[] = new int[48];
    // --------------to return

    private driveCity test = new driveCity();
    // The Game Board contains all the objects of the game.
    public GameBoard gameBoard;

    // An array of the players who are playing
    public Player[] gamePlayers;
    protected GameBoard cloned;

    // boolean attributes which indicate if the game is won or lost.
    public static boolean gameOver;
    public static boolean gameWon;
    public static boolean gameLost;

    /**
     * adjustable features of the game that is set up, including numberColours -
     * number of colours of disease played with. (default 4 colour) requiredForCure-
     * the number of cards which must be discarded to cure a disease (we set 4
     * instead of 5). infectionRateSetting - the arrary of values needed for the
     * infection rate. (implenent in GameBoard @increaseEpidemic) maximumOutbreaks -
     * the maximum number of outbreaks that can happen before the game is lost. (8)
     * cardsDrawnInitally - the number of cards drawn between the players at the
     * start of the game. maximumHandSize - The maximum handsize of the players.
     * (doesn't implement in this edition)
     */

    /*
     * This constructor for a Pandemic game creates a new instance of a GameBoard
     * object called GameBoard with the given adjustable features of the game. It
     * requires an array of players to be provided.
     */
    public SimulatePandemic(Player[] currentGamePlayers) {
        System.out.println("Setting up a new game of pandemic with the below features.");
        System.out.println(4 + " Colours of disease.");
        System.out.println(4 + " Cards of the same colour must be discarded at a research station to cure a disease.");
        System.out.println(4 + " is the maximum number of epidemics.");
        System.out.println("Any more than " + 7 + " outbreaks and the game will be lost");
        System.out.println("The initial infection step will see " + 9 + " cities infected ");
        System.out.println("Between all players a maximum of " + 8 + " cards are drawn at start of the game");
        System.out.println("There are " + 5 + " research stations which can be placed, and " + 1
                + " (min 1) are placed at the start of the game");

        // initialise instance variables
        // for 4 player the initial hand size is 2 cards
        int startingHandSize;

        gameBoard = new GameBoard();
        gamePlayers = currentGamePlayers;

        // sets the players to the gameboard
        sitPlayersDown(gameBoard);
        gameBoard.startGame();
        startingHandSize = calcHandSize();
        drawHands(startingHandSize);
        placePieces();
        gameOver = false;
    }

    public void playGame() throws CloneNotSupportedException {
        int i, z;
        int turns = 0;
        while (gameOver == false) {
            i = gamePlayers.length;
            
            diseasesCured.add(new HashMap<String, Integer>());
            numberOfCards.add(new HashMap<String, Integer>());
            cubesPerCity.add(new HashMap<String, Integer>());
            cubesRemain.add(new HashMap<String, Integer>());
            playerLocations.add(new HashMap<String, String>());
            researchStations.add(new ArrayList<String>());

            while (i > 0 && !gameOver) {

                /*
                 * Below we freeze the game to allow other players play our turn to suggest a
                 * set of actions.
                 */

                i = i - 1;
                for (z = 0; z < gamePlayers.length; z++) {
                    cloned = freeze();
                    // System.out.println("Before suggestion, Clone Equals
                    // GameBoard?:"+(cloned.equals(gameBoard)));
                    if (i != z) {
                        /*
                         * Each player has access to the entire gameBoard , his hands and the hands of
                         * player in command (The player who choose the final moves) and don't have
                         * knowledge of the cards of other players
                         */
                        System.out.println("------------------------------------");
                        System.out.println("sug:"+ (z+1) +" " +gamePlayers[z].getPlayerName() + "make suggestion for "+ gamePlayers[i].getPlayerName());
                        System.out.println("------------------------------------");

                        while (gamePlayers[z].getPlayerAction() > 0 && !gameOver) {
                            gamePlayers[z].makeDecision(gamePlayers[i].getHand(), gamePlayers[i].getPlayerRole(),
                                    gamePlayers[i].getPlayerPiece().location);
                            Variables.Suggestions[z] = gamePlayers[z].getSuggestions();
                            checkGameOver();
                        }
                    }
                    unfreeze(cloned);

                    sitPlayersDown(gameBoard);
                    // for (Action c : gamePlayers[z].getSuggestions()) {
                    // System.out.println(c);
                    // }
                }

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Time to take the final actions: " + gamePlayers[i].getPlayerName()
                        + " investigate the comrades suggestions");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                while (gamePlayers[i].getPlayerAction() > 0 && !gameOver) {

                    // here is where an action should happen
                    // System.out.println("Chance for action here!");
                    /*
                     * Here you choose between 2 options 1) modify the dummy AI methods 2) Create
                     * smart enough new methods to win the game
                     */

                    gamePlayers[i].makeDecision(null, gamePlayers[i].getPlayerRole(),
                            gamePlayers[i].getPlayerPiece().location);
                    checkGameOver();
                }
                // System.out.println(gamePlayers[0].getPlayerPiece().location);
                // System.out.println(gamePlayers[1].getPlayerPiece().location);
                // System.out.println(gamePlayers[2].getPlayerPiece().location);
                // System.out.println(gamePlayers[3].getPlayerPiece().location);

                resetAllPlayerAction();
                Variables.Suggestions[i] = gamePlayers[i].getSuggestions();
                // empty all the suggestions array
                for (int i1 = 0; i1 < Variables.Suggestions.length; i1++) {
                    Variables.Suggestions[i1].clear();
                }

                if (!checkGameOver()) {
                    System.out.println(gamePlayers[i].getPlayerName() + " completed 4 actions");
                    gamePlayers[i].drawCard(2, false);
                    gameBoard.infectCityPhase(gameBoard.getInfectionRate());

                }
                checkGameOver();

                int curDiseasesCured = 0;
                for (Disease disease : gameBoard.getDiseases()) {
                    if (disease.cured) {
                        curDiseasesCured++;
                    }
                }
                
                if (curDiseasesCured != totalDiseasesCured) {
                    diseasesCured.get(turns).put(gamePlayers[i].getPlayerRole().toLowerCase(), Integer.valueOf(curDiseasesCured - totalDiseasesCured));
                    totalDiseasesCured = curDiseasesCured;
                }
                else {
                    diseasesCured.get(turns).put(gamePlayers[i].getPlayerRole().toLowerCase(), Integer.valueOf(0));
                }
            }
            // resetAllPlayerAction();

            for (int j = 0; j < gamePlayers.length; j++) {
                numberOfCards.get(turns).put(gamePlayers[j].getPlayerRole().toLowerCase(), Integer.valueOf(gamePlayers[i].getHand().size()));
                playerLocations.get(turns).put(gamePlayers[j].getPlayerRole().toLowerCase(), gamePlayers[i].getPlayerPiece().getLocation().getName().toLowerCase());
            }

            cubesRemain.get(turns).put("black", Integer.valueOf(gameBoard.blackCubes));
            cubesRemain.get(turns).put("red", Integer.valueOf(gameBoard.redCubes));
            cubesRemain.get(turns).put("yellow", Integer.valueOf(gameBoard.yellowCubes));
            cubesRemain.get(turns).put("blue", Integer.valueOf(gameBoard.blueCubes));

            for (City city : gameBoard.get3CubeCities()) {
                cubesPerCity.get(turns).put(city.getName().toLowerCase(), Integer.valueOf(city.getMaxCube()));
            }

            for (City city : gameBoard.get2CubeCities()) {
                cubesPerCity.get(turns).put(city.getName().toLowerCase(), Integer.valueOf(city.getMaxCube()));
            }

            for (City city : gameBoard.get1CubeCities()) {
                cubesPerCity.get(turns).put(city.getName().toLowerCase(), Integer.valueOf(city.getMaxCube()));
            }

            if (gameBoard.getOutbreakCount() != totalOutbreaks) {
                outbreaks.add(Integer.valueOf(gameBoard.getOutbreakCount() - totalOutbreaks));
                totalOutbreaks = gameBoard.getOutbreakCount();
            }
            else {
                outbreaks.add(Integer.valueOf(0));
            }

            cardsLeft.add(Integer.valueOf(gameBoard.playerPile.size()));

            for(City city : gameBoard.getResearchCentres()) {
                researchStations.get(turns).add(city.getName().toLowerCase());
            }

            turns++;
            if (!checkGameOver()) {
                System.out.println("================================================");
                System.out.println("Ending turn " + turns + " everybody has had a go.");
                System.out.println("================================================");
            }
        }
        
        numberOfRounds = turns;
        for (int j = 0; j < gamePlayers.length; j++) {
            if (!diseasesCured.get(turns - 1).containsKey(gamePlayers[j].getPlayerRole().toLowerCase())) {
                diseasesCured.get(turns - 1).put(gamePlayers[j].getPlayerRole().toLowerCase(), Integer.valueOf(0));
            }
        }

        if (gameLost && lossReason == -1) {
            lossReason = 2;
        }

        gamePlayers[0].printStats();
        for (i = 0; i < 4; i++) {
            System.out.print(gamePlayers[i].getPlayerRole() + "'s ");
            gamePlayers[i].printPlayerStats();
        }

        gameBoard.printStats();
    }

    private GameBoard freeze() throws CloneNotSupportedException {
        for (int k = 0; k < gameBoard.cities.size(); k++) {
            this.cubes[k][0] = gameBoard.cities.get(k).getRedCubes();
            this.cubes[k][1] = gameBoard.cities.get(k).getBlueCubes();
            this.cubes[k][2] = gameBoard.cities.get(k).getBlackCubes();
            this.cubes[k][3] = gameBoard.cities.get(k).getYellowCubes();
            this.infect[k] = gameBoard.cities.get(k).getInfectionLevel();
            this.dist[k] = gameBoard.cities.get(k).getDistance();
            this.outs[k] = gameBoard.cities.get(k).isHasOutbreak();
        }
        for (int k = 0; k < gameBoard.diseases.size(); k++) {
            this.cured[k] = gameBoard.diseases.get(k).cured;
            this.eliminated[k] = gameBoard.diseases.get(k).eliminated;

        }

        f_redCube = this.gameBoard.redCubes;
        f_blueCubes = this.gameBoard.blueCubes;
        f_yellowCubes = this.gameBoard.yellowCubes;
        f_blackCubes = this.gameBoard.blackCubes;

        // f_ResearchStation = (ArrayList<City>) this.gameBoard.getResearchCentres();
        f_ResearchStation = new ArrayList<City>();
        for (int k = 0; k < gameBoard.getResearchCentres().size(); k++) {
            f_ResearchStation.add(gameBoard.getResearchCentres().get(k));
        }
        f1_location = gamePlayers[0].getPlayerPiece().location;
        f2_location = gamePlayers[1].getPlayerPiece().location;
        f3_location = gamePlayers[2].getPlayerPiece().location;
        f4_location = gamePlayers[3].getPlayerPiece().location;
        GameBoard cloned = (GameBoard) gameBoard.clone();
        return cloned;
    }

    private void unfreeze(GameBoard cloned) throws CloneNotSupportedException {
        this.gameBoard = cloned;
        for (int k = 0; k < gameBoard.cities.size(); k++) {
            gameBoard.cities.get(k).setRedCubes(this.cubes[k][0]);
            gameBoard.cities.get(k).setBlueCubes(this.cubes[k][1]);
            gameBoard.cities.get(k).setBlackCubes(this.cubes[k][2]);
            gameBoard.cities.get(k).setYellowCubes(this.cubes[k][3]);
            gameBoard.cities.get(k).setInfectionLevel(this.infect[k]);
            gameBoard.cities.get(k).setDistance(this.dist[k]);
            gameBoard.cities.get(k).setHasOutbreak(this.outs[k]);
        }

        this.gameBoard.redCubes = f_redCube;
        this.gameBoard.blueCubes = f_blueCubes;
        this.gameBoard.yellowCubes = f_yellowCubes;
        this.gameBoard.blackCubes = f_blackCubes;

        for (int k = 0; k < gameBoard.diseases.size(); k++) {
            gameBoard.diseases.get(k).setCured((this.cured[k]));
            gameBoard.diseases.get(k).setEliminated(((this.eliminated[k])));

        }
        Variables.CITY_WITH_RESEARCH_STATION = new ArrayList<City>();
        for (int k = 0; k < gameBoard.getResearchCentres().size(); k++) {
            Variables.CITY_WITH_RESEARCH_STATION.add(f_ResearchStation.get(k));
        }

        gamePlayers[0].getPlayerPiece().location = f1_location;
        gamePlayers[1].getPlayerPiece().location = f2_location;
        gamePlayers[2].getPlayerPiece().location = f3_location;
        gamePlayers[3].getPlayerPiece().location = f4_location;

        this.gameBoard.playerPieces[0].setLocation(f1_location);
        this.gameBoard.playerPieces[1].setLocation(f2_location);
        this.gameBoard.playerPieces[2].setLocation(f3_location);
        this.gameBoard.playerPieces[3].setLocation(f4_location);

        Variables.CITY_WITH_RESEARCH_STATION = f_ResearchStation;
        //

    }

    public ArrayList<City> returnLocation() {
        ArrayList<City> f_loc = new ArrayList<City>();
        f_loc.add(gamePlayers[0].getPlayerPiece().location);
        f_loc.add(gamePlayers[1].getPlayerPiece().location);
        f_loc.add(gamePlayers[2].getPlayerPiece().location);
        f_loc.add(gamePlayers[3].getPlayerPiece().location);
        return f_loc;

    }

    public boolean checkGameOver() {
        if (checkGameWon()) {
            for (int i = 0; i < 5; i++) {
                System.out.println(" YOU WIN!!! ");
            }
            gameOver = true;
        }
        checkGameLost();
        return gameOver;
    }

    // checks if the game is lost
    public void checkGameLost() {
        if (gameBoard.getOutbreakCount() > Variables.MAX_NUMBER_OF_OUTBREAK) {
            System.out.println(" game over, too many outbreaks! ");
            lossReason = 1;
            gameLost = true;
            gameOver = true;
            looserPrint();
        } else if (gameBoard.emptyDeck()) {
            System.out.println(" That's it game over, no more cards. ");
            lossReason = 0;
            gameLost = true;
            gameOver = true;
            looserPrint();
        }

        // the condition of not have any other cubes to set
        // is implemented in @GameBoard.checkCubesRemain and @NoMoreCubes
    }

    // Prints looser
    public static void looserPrint() {
        for (int i = 0; i < 5; i++) {
            System.out.println(" THE CIVILATION IS DEAD!");
        }
    }

    // sets all players actions back to 4
    public void resetAllPlayerAction() {
        int i = gamePlayers.length;
        while (i > 0) {
            i--;
            gamePlayers[i].resetPlayerAction();
        }
    }

    public void placePieces() {
        City temp3 = new City(0, 0, 0, 0, 0);
        for (int x = 0; x < gameBoard.cities.size(); x++) {
            if (gameBoard.cities.get(x).getName().equals(Variables.atlanta.getName())) {
                temp3 = gameBoard.cities.get(x);
            }
        }
        int i = gamePlayers.length;
        while (i > 0) {
            i = i - 1;
            // placing pieces
            // System.out.println("Placing a piece for " +
            // gamePlayers[i-1].getPlayerName());

            gameBoard.playerPieces[i] = new Piece(gamePlayers[i]/* , gameBoard */, temp3);
            // System.out.println("making sure they know the piece is their piece!");
            gamePlayers[i].setPlayerPiece(gameBoard.playerPieces[i]);
        }
    }

    // calculates initial hand size based on number of players
    public int calcHandSize() {
        int i = gamePlayers.length;
        int toReturn = 0;
        if (i == 2) {
            toReturn = 4;
        } else if (i == 3) {
            toReturn = 3;
        } else if (i == 4) {
            toReturn = 2;
        }
        return toReturn;
    }

    // draw cards for each player
    public void drawHands(int handSize) {
        int i = gamePlayers.length;
        while (i > 0) {
            i = i - 1;
            System.out.println("drawing hand for " + gamePlayers[i].getPlayerName());
            gamePlayers[i].drawCard(handSize, true);
        }
    }

    public boolean checkGameWon() {
        boolean isWon = true;
        for (int i = 0; i < gameBoard.diseases.size(); i++) {
            isWon = isWon && gameBoard.diseases.get(i).getCured();
        }
        gameWon = isWon;
        return isWon;
    }

    public void sitPlayersDown(GameBoard gb) {
        int i = gamePlayers.length;
        while (i > 0) {
            i = i - 1;
            // System.out.println(gamePlayers[i].getPlayerName() + " has joined the game");
            gamePlayers[i].setGameBoard(gb);
        }
    }

}
