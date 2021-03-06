package game.main.java.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.main.java.model.GameModel;

/**
 * Created by palm on 2016-05-10.
 */
public class ViewHandler {

    private BoardView boardView;
    private CombatView combatView;
    private UnitMenuView menuView;
    private StatusView statusView;
    private EndView endView;
    private CombatInfoView combatInfoView;
    private MainMenuView mainMenuView;

    /**
     * Creates the ViewHandler
     * ViewHandler creates all other views and draws them. The separate View classes decides if it should be drawn or not.
     */
    public ViewHandler(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        OrthographicCamera camera = new OrthographicCamera(w, h);
        boardView = new BoardView(camera);
        combatView = new CombatView();
        menuView = new UnitMenuView(camera);
        statusView = new StatusView(camera);
        endView = new EndView(camera);
        combatInfoView = new CombatInfoView(camera);
        mainMenuView = new MainMenuView(camera);
    }

    public void render(SpriteBatch spriteBatch){
        mainMenuView.render(spriteBatch);
        boardView.render(spriteBatch);
        combatView.render(spriteBatch);
        menuView.render(spriteBatch);
        statusView.render(spriteBatch);
        endView.render(spriteBatch);
        combatInfoView.render(spriteBatch);
    }
}
