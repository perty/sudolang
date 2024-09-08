import java.util.Random;
import java.util.Scanner;

public class Game {
    private Cave[] caves = new Cave[20];
    private int playerCave;
    private int minotaurCave;
    private int[] batCaves = new int[2];
    private int[] pitCaves = new int[2];
    private boolean gameOver = false; // Tracks the state of the game

    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        initCaves();
        placeHazards();
        playerCave = 0; // Start at cave 1, adjust according to zero-based index
    }

    public void initCaves() {
        for (int i = 0; i < caves.length; i++) {
            caves[i] = new Cave(i + 1);
        }

        // Manually set connections between caves based on a dodecahedron layout
        // Here's a potential mapping:
        caves[0].setAdjacentCaves(caves[1], caves[4], caves[7]);
        caves[1].setAdjacentCaves(caves[0], caves[2], caves[9]);
        caves[2].setAdjacentCaves(caves[1], caves[3], caves[11]);
        caves[3].setAdjacentCaves(caves[2], caves[4], caves[13]);
        caves[4].setAdjacentCaves(caves[0], caves[3], caves[5]);
        caves[5].setAdjacentCaves(caves[4], caves[6], caves[14]);
        caves[6].setAdjacentCaves(caves[5], caves[7], caves[16]);
        caves[7].setAdjacentCaves(caves[0], caves[6], caves[8]);
        caves[8].setAdjacentCaves(caves[7], caves[9], caves[17]);
        caves[9].setAdjacentCaves(caves[1], caves[8], caves[10]);
        caves[10].setAdjacentCaves(caves[9], caves[11], caves[18]);
        caves[11].setAdjacentCaves(caves[2], caves[10], caves[12]);
        caves[12].setAdjacentCaves(caves[11], caves[13], caves[19]);
        caves[13].setAdjacentCaves(caves[3], caves[12], caves[14]);
        caves[14].setAdjacentCaves(caves[5], caves[13], caves[15]);
        caves[15].setAdjacentCaves(caves[14], caves[16], caves[19]);
        caves[16].setAdjacentCaves(caves[6], caves[15], caves[17]);
        caves[17].setAdjacentCaves(caves[8], caves[16], caves[18]);
        caves[18].setAdjacentCaves(caves[10], caves[17], caves[19]);
        caves[19].setAdjacentCaves(caves[12], caves[15], caves[18]);
    }

    // Example of how to initialize the game and place hazards
    public void placeHazards() {
        // Random placement of hazards, ensuring no overlap
        do {
            minotaurCave = random.nextInt(caves.length);
            pitCaves[0] = random.nextInt(caves.length);
            pitCaves[1] = random.nextInt(caves.length);
            // Repeat until all hazards are placed in unique caves
        } while (minotaurCave == pitCaves[0] || minotaurCave == pitCaves[1] || pitCaves[0] == pitCaves[1]);
    }

    public void startGame() {
        playerCave = 0; // Starting cave
        while (true) {
            System.out.println("You are in cave " + (playerCave + 1));
            System.out.println("Adjacent caves: ");
            for (Cave cave : caves[playerCave].getAdjacentCaves()) {
                System.out.print(cave.getCaveNumber() + " ");
            }
            System.out.println("\nWhat do you want to do?");
            String action = scanner.nextLine();
            // Parse action to move or shoot
        }
    }

    public int getPlayerCave() {
        return playerCave + 1; // Adjusting for one-based index
    }

    // Method to move the player and check for pits
    public void movePlayer(int newCaveNumber) {
        if (caves[playerCave].isAdjacent(newCaveNumber)) {
            playerCave = newCaveNumber - 1; // Adjust for zero-based index
            // Check for pits
            for (int pit : pitCaves) {
                if (playerCave == pit) {
                    System.out.println("Oh no! You've fallen into a pit!");
                    gameOver = true; // Set game over status
                    return;
                }
            }
            System.out.println("Moved to cave " + newCaveNumber);
        } else {
            System.out.println("Cannot move to cave " + newCaveNumber + ". It is not connected to cave " + (playerCave + 1));
        }
    }

    // Allow setting the Minotaur's cave for testing purposes
    public void setMinotaurCave(int caveNumber) {
        this.minotaurCave = caveNumber - 1; // Adjust for zero-based index if necessary
    }
    // Additional methods to handle moving and shooting
    /**
     * Shoots an arrow through a specified sequence of caves.
     * @param path An array of cave numbers representing the path of the arrow.
     * @return true if the arrow hits the Minotaur, false otherwise.
     */
    public boolean shootArrow(int[] path) {
        int currentCave = playerCave;
        for (int i = 0; i < path.length; i++) {
            int nextCave = path[i] - 1; // Adjust for zero-based index

            // Check if the next cave is valid (adjacent to the current cave)
            if (!caves[currentCave].isAdjacent(nextCave + 1)) {
                // If not a valid next cave, pick a random adjacent cave
                Cave[] adjacentCaves = caves[currentCave].getAdjacentCaves();
                nextCave = adjacentCaves[random.nextInt(adjacentCaves.length)].getCaveNumber() - 1;
            }

            // Move the arrow to the next cave
            currentCave = nextCave;

            // Check if the arrow hits the player (suicide shot)
            if (currentCave == playerCave) {
                System.out.println("Tragically, the arrow comes back and hits you!");
                return false; // The game could end or update state accordingly
            }

            // Check if the arrow hits the Minotaur
            if (currentCave == minotaurCave) {
                System.out.println("The arrow strikes true, and the Minotaur falls!");
                return true;
            }
        }

        // If the arrow travels through all specified caves without hitting the Minotaur
        System.out.println("The arrow did not find its mark.");
        return false;
    }

    /**
     * Sets the specific caves that contain bottomless pits.
     * @param pits An array of two integers specifying the cave numbers for the pits.
     */
    public void setPitCaves(int[] pits) {
        if (pits.length == 2) {
            pitCaves[0] = pits[0] - 1; // Adjust for zero-based index
            pitCaves[1] = pits[1] - 1;
        } else {
            System.out.println("Error: Incorrect number of pits specified.");
        }
    }

    /**
     * Checks if the game is over, either by player falling into a pit or other conditions.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public void processCommand(String command) {
        String[] parts = command.split(" ");
        if (parts[0].equalsIgnoreCase("move") && parts.length == 3) {
            int targetCave = Integer.parseInt(parts[2]);
            movePlayer(targetCave);
        } else if (parts[0].equalsIgnoreCase("shoot") && parts.length > 1) {
            int[] path = new int[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                path[i - 1] = Integer.parseInt(parts[i]);
            }
            shootArrow(path);
        }
    }
}

