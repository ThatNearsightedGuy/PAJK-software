package pajk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.CombatState;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.view.BoardView;
import pajk.game.main.java.view.CombatView;
import pajk.game.main.java.view.MainView;

public class PajkGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	public static final String TITLE = "Pajkification";


	private MainView mainView;
	private GameModel gameModel;
	private Controller gameController;
//	private BoardView boardView;
//	private CombatView combatView;
	private SpriteBatch batch;

	private int inputCooldown = 0;
	private int inputDelay = 8;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameModel = GameModel.getInstance();
		gameModel.setState(GameModel.StateName.MAIN_STATE);
		gameController = new Controller(gameModel);

		mainView = new MainView();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor((float)0.46, (float)0.33, (float)0.16, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainView.render(batch);
		listenToKeys();
	}
	
	private void listenToKeys(){
		if (inputCooldown > -1){
			inputCooldown--;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			gameController.leftInput();
			inputCooldown = inputDelay * 2;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			gameController.rightInput();
			inputCooldown = inputDelay * 2;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			gameController.upInput();
			inputCooldown = inputDelay * 2;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			gameController.downInput();
			inputCooldown = inputDelay * 2;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
			gameController.enterInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
			gameController.backInput();
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){//TODO REMOVE? alt change to a "skip" button
			gameController.combatDoneInput();
		}

		if (inputCooldown == 0){
			if (Gdx.input.isKeyPressed(Input.Keys.X) && gameModel.getCurrentStateName() == GameModel.StateName.MAIN_STATE){
				inputDelay = 4;
			} else {
				inputDelay = 8;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				gameController.leftInput();
				inputCooldown = inputDelay;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				gameController.rightInput();
				inputCooldown = inputDelay;
			} else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
				gameController.upInput();
				inputCooldown = inputDelay;
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
				gameController.downInput();
				inputCooldown = inputDelay;
			}
		}
	}
}
