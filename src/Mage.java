import java.util.Random;

public class Mage extends Character{
    int damage = 20;
    //immunity to ice
    //main attribute charisma
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    public Mage(){}
    public Mage(String name, int level, long experience, int max_health, int max_mana){
        inventory.max_weight = 100;
        super.max_health = max_health;
        current_health = max_health;
        super.max_mana = max_mana;
        current_mana = max_mana;
        super.name = name;
        lvl = level;
        exp = experience;
        Abillities.add(new Ice());
        Abillities.add(new Fire());
        Abillities.add(new Earth());
        ice = true;
    }
    public void addAbilities(){
        Abillities.add(new Ice());
        Abillities.add(new Fire());
        Abillities.add(new Earth());
    }
    public void addBonusPotions(){
        inventory.addPotion(new HealthPotion());
        inventory.addPotion(new ManaPotion());
    }
    int receiveDamage(int dmg){
        Random random = new Random();
        int rand = random.nextInt(10);
        if(rand < 5) {
            current_health = current_health - dmg/2;
            return dmg / 2;
        }
        current_health = current_health - dmg;
        return dmg;
    }
    int getDamage(){
        Random random = new Random();
        int rand = random.nextInt(10);
        if(rand < 5)
            return 2*damageIs();
        return damageIs();
    }
    int damageIs(){
        return damage;
    }
}