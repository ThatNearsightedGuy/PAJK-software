package pajk.game.main.java.model;

import pajk.game.main.java.model.terrain.Plains;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by palm on 2016-04-15.
 */
public class Board {

    enum Direction{
        NORTH, WEST, EAST, SOUTH
    }

    private Tile cursor;
    private Tile[][] tileMatrix;

    Board(int x, int y) {
        initMatrix(x, y);
        cursor = tileMatrix[0][0];
    }

    Board(int size){
        this(size, size);
    }

    private void initMatrix(int x, int y){
        tileMatrix = new Tile[x][y];
        for (int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                tileMatrix[i][j] = new Tile(i,j, new Plains());
            }
        }
    }

    /**
     * Get the tile with the given coordinates, if there is one.
     * @param x X coordinate
     * @param y Y coordinate
     * @return The tile with the given coordinates,
     * null if the coordinates are illegal
     */
    public Tile getTile(int x, int y) {
        if (isWithinBoard(x, y)) {
            return tileMatrix[x][y];
        }
        else return null;
    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }


    private Boolean isWithinBoard (int x, int y) {
        return x >= 0 && x < getBoardWidth() &&
                y >= 0 && y < getBoardHeight();
    }

    /**
     * Moves the cursor in the given direction.
     * @param dir The direction to move in.
     *            Available values are NORTH, SOUTH, EAST and WEST.
     */
    void moveCursor(Direction dir){
        switch (dir){
            case NORTH:
                moveCursor(0, -1);
                break;
            case WEST:
                moveCursor(-1, 0);
                break;
            case EAST:
                moveCursor(1, 0);
                break;
            case SOUTH:
                moveCursor(0, 1);
                break;
        }
    }

    /**
     * Checks if the cursor can be moved in the given direction
     * and moves it if that is the case.
     * @param deltaX The X coordinate of the new tile, relative to the current tile
     * @param deltaY The Y coordinate of the new tile, relative to the current tile
     */
    private void moveCursor(int deltaX, int deltaY){
        int newX = cursor.getX() + deltaX;
        int newY = cursor.getY() + deltaY;
        if (    newX >= 0 && newX < getBoardWidth() &&
                newY >= 0 && newY < getBoardHeight()){
            setCursor(newX, newY);
        }
    }

    /**
     * Sets the cursor to the given coordinates
     * @param x X coordinate
     * @param y Y coordinate
     */
    void setCursor(int x, int y) {
        cursor = tileMatrix[x][y];
    }

    /**
     * @return The tile that the cursor is currently on.
     */
    public Tile getCursorTile(){
        return cursor;
    }

    /**
     * Recursively checks every tile that can be reached from a given tile,
     * with the given movement range, and adds them to a set.
     * @param tiles The set containing the tiles.
     * @param origin The tile to start from.
     * @param previous The previously examined tile.
     * @param range The movement range of the moving unit.
     * @return A set containing every tile that can be reached from the origin tile.
     */
    public Set<Tile> getTilesWithinMoveRange(Set<Tile> tiles, Tile origin, Tile previous, int range) {
        tiles.add(origin);
        if (range < 1){
            return tiles;
        }

        if (origin.getY() > 0){
            Tile northTile = getTile(origin.getX(), origin.getY() - 1);
            if (previous != northTile) {
                tiles.addAll(getTilesWithinMoveRange(tiles, northTile, origin, range - 1));
            }
        }
        if (origin.getY() < getBoardHeight() - 1){
            Tile southTile = getTile(origin.getX(), origin.getY() + 1);
            if (previous != southTile) {
                tiles.addAll(getTilesWithinMoveRange(tiles, southTile, origin, range - 1));
            }
        }
        if (origin.getX() > 0){
            Tile westTile = getTile(origin.getX() - 1, origin.getY());
            if (previous != westTile) {
                tiles.addAll(getTilesWithinMoveRange(tiles, westTile, origin, range - 1));
            }
        }
        if (origin.getX() < getBoardWidth() - 1){
            Tile eastTile = getTile(origin.getX() + 1, origin.getY());
            if (previous != eastTile) {
                tiles.addAll(getTilesWithinMoveRange(tiles, eastTile, origin, range - 1));
            }
        }
        return tiles;
    }

    /**
     * Returns the tiles that are at at least minRange steps away from the center tile but no more than maxRange steps away.
     * Does not take tile movement costs into effect.
     * @param center The tile oyu want to get the surrounding tiles of.
     * @param minRange The minimum movement range to the returned tiles.
     * @param maxRange The maximum movement range to the returned tiles.
     * @return
     */
    public Set<Tile> getTilesAround(Tile center, int minRange, int maxRange){
        Set<Tile> result = new HashSet<>();
        for (int i = minRange; i <= maxRange; i++) {
            for (int j = 0; j < i; j++) {
                if (isWithinBoard(center.getX() - i + j, center.getY() - j)){
                    result.add(getTile(center.getX() - i + j, center.getY() - j));
                }
                if (isWithinBoard(center.getX()+ j, center.getY() - i + j)){
                    result.add(getTile(center.getX()+ j, center.getY() - i + j));
                }
                if (isWithinBoard(center.getX() + i - j, center.getY() + j)){
                    result.add(getTile(center.getX() + i - j, center.getY() + j));
                }
                if (isWithinBoard(center.getX() - j, center.getY() + i - j)){
                    result.add(getTile(center.getX() - j, center.getY() + i - j));
                }
            }
        }
        return result;
    }

    /**
     * TODO delete?
     * @param unit
     */
    void moveToCursor(Unit unit) {
        Tile old = getPos(unit);
        cursor.setUnit(unit);
        old.setUnit(null);
    }

    public int getBoardWidth(){
        return tileMatrix.length;
    }

    public int getBoardHeight(){
        return tileMatrix[0].length;
    }

    void placeUnit(Unit unit, Tile tile) {
        tile.setUnit(unit);
    }

    /**
     * Checks if there is a tile containing the specified unit.
     * @param unit The unit to check for
     * @return The Tile that the unit is standing on,
     * null if the unit can't be found.
     */
    Tile getPos(Unit unit) {
        for (Tile[] tCol : tileMatrix) {
            for (Tile t : tCol) {
                if(t.getUnit() == unit) {
                    return t;
                }
            }
        }
        return null;
    }

    void moveUnit(Unit unit, Tile dest) {
        for (Tile[] arr:
             tileMatrix) {
            for (Tile t:
                 arr) {
                if (t.hasUnit() && t.getUnit() == unit){
                    t.setUnit(null);
                }
            }
        }
        dest.setUnit(unit);
    }

    public String toString(){
        String result = "(" + cursor.getX() + ", " + cursor.getY() + ")\n";
        for (int i = 0; i < getBoardHeight(); i++) {
            for (int j = 0; j < getBoardWidth(); j++) {
                if (j == cursor.getX() && i == cursor.getY()){
                    result = result + "[X]";
                } else if (getTile(j, i).hasUnit()){
                    result = result + "[U]";
                } else{
                    result = result + "[O]";
                }

            }
            result = result + "\n";
        }
        return result;
    }
}
