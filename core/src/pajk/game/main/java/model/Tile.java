package pajk.game.main.java.model;

import pajk.game.main.java.model.terrain.Terrain;

/**
 * Created by palm on 2016-04-15.
 */
public class Tile{
    private final int x;
    private final int y;
    private final Terrain terrain;
    private Unit unit;
    private Overlay overlay = Overlay.NONE;

    private double pthH;
    private Tile pthParent = null;
    private double pthG;
    private double pthF;

    public Tile getPthParent() {
        return pthParent;
    }

    public void setPthParent(Tile pthParent) {
        this.pthParent = pthParent;
    }

    public double getPthG() {
        return pthG;
    }

    public void setPthG(double pthG) {
        this.pthG = pthG;
    }

    public double getPthF() {
        return pthF;
    }

    public void setPthF(double pthF) {
        this.pthF = pthF;
    }

    public double getPthH() {
        return pthH;
    }

    public void setPthH(double pthH) {
        this.pthH = pthH;
    }

    public enum Overlay{
        MOVEMENT, TARGET, NONE
    }

    Tile(int x, int y, Terrain terrain){
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }

    public int getX(){
        return x;
    }

    public boolean hasUnit(){
        return unit != null;
    }

    public int getY(){
        return y;
    }

    Unit getUnit() {
        return unit;
    }

    void setUnit(Unit unit) {
        this.unit = unit;
    }

    int getMovementCost(Unit.MovementType movType) {
        return terrain.getMovementCost(movType);
    }

    public String getTerrainType(){
        return terrain.getType();
    }

    int getEvasion() {
        return terrain.getEvasion();
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }

    public String toString(){
        return "Tile at " +x+" "+y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile otherTile = (Tile) obj;
            return (otherTile.getX() == this.getX() &&
                    otherTile.getY() == this.getY());
        }
        return false;
    }
}
