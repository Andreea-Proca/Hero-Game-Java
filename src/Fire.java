public class Fire extends Spell{
    int damage = 25;
    int mana_price = 20;
    public int getDamage(){
        return damage;
    }
    public  int getMana_price(){
        return mana_price;
    }
    public Fire(){}
    public int visit(Warrior warrior){
        System.out.println("You -" + 0 + " health");
        return 0;
    }
    public int visit(Rogue rogue){
        rogue.receiveDamage(damage);
        System.out.println("You -" + damage + " health");
        return damage;
    }
    public int visit(Mage mage){
        mage.receiveDamage(damage);
        System.out.println("You -" + damage + " health");
        return damage;
    }
    public int visit(Enemy enemy){
        if(enemy.fire) {
            System.out.println("Enemy -" + 0 + " health");
            return 0;
        }
        int dmg = enemy.receiveDamage(damage);
        if(dmg == 0)
            System.out.println("You missed!");
        System.out.println("Enemy -" + dmg + " health");
        return dmg;
    }
}