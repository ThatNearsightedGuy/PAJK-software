package pajk.game.main.java.model.units;

/**
 * Created by Gustav on 2016-05-19.
 */
public class Swordsman extends Unit {
    public Swordsman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Swordsman";
        maxHealth = 18;
        strength = 5;
        skill = 5;
        speed = 7;
        luck = 7;
        defence = 5;
        resistance = 0;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 80;
        strengthGrowth = 45;
        skillGrowth = 50;
        speedGrowth = 45;
        luckGrowth = 45;
        defenceGrowth = 30;
        resistanceGrowth = 35;

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "gray-sword-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-sword-animation.png";
            textureFilePath = "blue-sword-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-sword-animation.png";
            textureFilePath = "red-sword-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
