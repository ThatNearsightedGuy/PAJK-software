package pajk.game.main.java.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by palm on 2016-04-15.
 */
class Board {

    enum Direction{
        NORTH, WEST, EAST, SOUTH
    }

    private Tile cursor;
    private Tile[][] tileMatrix;

    Board(int size){
        initMatrix(size);
        cursor = tileMatrix[0][0];
    }

    private void initMatrix(int size){
        tileMatrix = new Tile[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                tileMatrix[i][j] = new Tile(i,j);
            }
        }
    }

    Tile getTile(int x, int y){
        return tileMatrix[x][y];
    }

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

    private void moveCursor(int deltaX, int deltaY){
        if (    cursor.getX() + deltaX >= 0 &&
                cursor.getX() + deltaX < tileMatrix.length &&
                cursor.getY() + deltaY >= 0 &&
                cursor.getY() + deltaY < tileMatrix[0].length){
            cursor = tileMatrix[cursor.getX() + deltaX][cursor.getY() + deltaY];
        }
//        System.out.println(this.toString());
    }

    Tile getCursorTile(){
        return cursor;
    }

    void showMoveRange(Unit unit) {
        Tile tile = getPos(unit);
        //TODO tile.getTerrainType()
        int move = unit.getMovement();
        Set<Tile> moveableTiles = getTilesWithinRange(new HashSet<Tile>(), tile, tile, unit.getMovement());
        //TODO tile.setOverlay for every tile
    }

    Set<Tile> getTilesWithinRange(Set<Tile> tiles, Tile origin, Tile previous, int range) {
        tiles.add(origin);
        if (range < 1){
            return tiles;
        }

        if (origin.getY() > 0){
            Tile northTile = getTile(origin.getX(), origin.getY() - 1);
            if (previous != northTile) {
                tiles.addAll(getTilesWithinRange(tiles, northTile, origin, range - 1));
            }
        }
        if (origin.getY() < getBoardHeight() - 1){
            Tile southTile = getTile(origin.getX(), origin.getY() + 1);
            if (previous != southTile) {
                tiles.addAll(getTilesWithinRange(tiles, southTile, origin, range - 1));
            }
        }
        if (origin.getX() > 0){
            Tile westTile = getTile(origin.getX() - 1, origin.getY());
            if (previous != westTile) {
                tiles.addAll(getTilesWithinRange(tiles, westTile, origin, range - 1));
            }
        }
        if (origin.getX() < getBoardWidth() - 1){
            Tile eastTile = getTile(origin.getX() + 1, origin.getY());
            if (previous != eastTile) {
                tiles.addAll(getTilesWithinRange(tiles, eastTile, origin, range - 1));
            }
        }
        return tiles;
    }

    private int getBoardWidth(){
        return tileMatrix.length;
    }

    private int getBoardHeight(){
        return tileMatrix[0].length;
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

    }

    public String toString(){
        String result = "(" + cursor.getX() + ", " + (cursor.getY() + ")" + "\n");
        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix[0].length; j++) {
                if (j == cursor.getX() && i == cursor.getY()){
                    result = result + "[X]";
                } else {
                    result = result + "[O]";
                }

            }
            result = result + "\n";
        }
        return result;
    }
}
