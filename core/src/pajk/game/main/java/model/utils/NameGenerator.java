package game.main.java.model.utils;

import game.main.java.model.units.Unit;

import java.util.List;
import java.util.Random;

/**
 * This class gives out some random names for our units, mostly for fun.
 *
 * Created by Gustav on 2016-05-13.
 */
public abstract class NameGenerator {
    public static String getRandomName(Unit.Allegiance allegiance){
        List<String> nameList;
        if (allegiance == Unit.Allegiance.PLAYER){
            nameList = FileReader.readFile("Scenarios/goodGuys.txt");
        }else{
            nameList = FileReader.readFile("Scenarios/badGuys.txt");
        }
        Random rand = new Random();
        return nameList.get(rand.nextInt(nameList.size()));
    }
}
