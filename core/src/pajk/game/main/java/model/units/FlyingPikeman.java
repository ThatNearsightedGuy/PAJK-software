package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingPikeman extends Pikeman {
    public FlyingPikeman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        grayTextureFilePath = "gray-pike-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-pike-flying-sprite.png";
            textureFilePath = "blue-pike-flying-animation.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-pike-flying-sprite.png";
            textureFilePath = "red-pike-flying-animation.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
