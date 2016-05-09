package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.Set;

/**
 * Created by Gustav on 2016-04-25.
 */
public class MoveSelectionState implements State{

    private Unit activeUnit;
    private Board board;
    private GameModel model;
    private Set<Tile> allowedTiles;

    public MoveSelectionState(Board board){
        this.board = board;
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:
                board.moveCursor(Board.Direction.NORTH);
                break;
            case LEFT:
                board.moveCursor(Board.Direction.WEST);
                break;
            case RIGHT:
                board.moveCursor(Board.Direction.EAST);
                break;
            case DOWN:
                board.moveCursor(Board.Direction.SOUTH);
                break;
            case ENTER:
                enterAction();
                break;
            case BACK:
                backToMenu();
                break;
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MOVE_SELECT;
    }

    private void enterAction(){
        Tile cursorTile = board.getCursorTile();
        for (Tile t:
                allowedTiles) {
            if (t == cursorTile){
                System.out.println("Moved the unit.");
                for (Tile ti:
                        allowedTiles) {
                    ti.setOverlay(Tile.Overlay.NONE);
                }
                board.moveUnit(activeUnit, cursorTile);

                activeUnit.setUnitState(Unit.UnitState.MOVED);
                //Force the previously active unit to finish its turn if it wasn't done
                Unit prev = model.getPrevActiveUnit();
                if (prev != null && prev.getUnitState() == Unit.UnitState.MOVED) {
                    prev.setUnitState(Unit.UnitState.ATTACKED);
                }
                //Open the menu again when the unit is finished moving
                model.setState(GameModel.StateName.UNIT_MENU);
                break;
            }
        }
    }

    /**
     * Cancel moving and return back to the unit menu
     */
    private void backToMenu() {
        for (Tile t : allowedTiles) {
            t.setOverlay(Tile.Overlay.NONE);
        }
        model.setState(GameModel.StateName.UNIT_MENU);
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        activeUnit = model.getActiveUnit();
        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesWithinMoveRange(activeUnit);
        for (Tile t:
             allowedTiles) {
            t.setOverlay(Tile.Overlay.MOVEMENT);
        }
    }
}
