public interface Visitor{
    int visit(Warrior warrior);
    int visit(Rogue rogue);
    int visit(Mage mage);
    int visit(Enemy enemy);
}