public abstract class Spell implements Visitor{
    public abstract int getDamage();
    public abstract int getMana_price();
    public abstract int visit(Warrior warrior);
    public abstract int visit(Rogue rogue);
    public abstract int visit(Mage mage);
    public abstract int visit(Enemy enemy);
}