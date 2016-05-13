package pajk.game.main.java.model;


/**
 * This state is active when you start the game. It allows you to move the cursor around the board and select units.
 *
 * Created by Gustav on 2016-04-22.
 */
public class MainState extends MoveState {

    private GameModel model;
    private Board board;

    @Override
    public void enterAction() {
        //Open the menu for the unit under the cursor of it's an allied unit that has not yet acted this turn.
        Tile cursorTile = board.getCursorTile();
        if (cursorTile.hasUnit()){
            Unit currentUnit = cursorTile.getUnit();
            if (    currentUnit.getAllegiance() == Unit.Allegiance.PLAYER &&
                    currentUnit.getUnitState() != Unit.UnitState.ATTACKED) {
                model.setActiveUnit(currentUnit);
                model.setState(GameModel.StateName.UNIT_MENU);
            }
        }
    }

    @Override
    public void backAction() {

    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN_STATE;
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        board = GameModel.getInstance().getBoard();
    }
}
