public class Cave {
    private int caveNumber;
    private Cave[] adjacentCaves = new Cave[3]; // Each cave has 3 adjacent caves

    public Cave(int number) {
        this.caveNumber = number;
    }

    public void setAdjacentCaves(Cave c1, Cave c2, Cave c3) {
        adjacentCaves[0] = c1;
        adjacentCaves[1] = c2;
        adjacentCaves[2] = c3;
    }

    public Cave[] getAdjacentCaves() {
        return adjacentCaves;
    }

    public int getCaveNumber() {
        return caveNumber;
    }

    public boolean isAdjacent(int caveNumber) {
        for (Cave c : adjacentCaves) {
            if (c.getCaveNumber() == caveNumber) {
                return true;
            }
        }
        return false;
    }
}
