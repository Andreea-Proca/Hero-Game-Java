import java.util.Random;

public abstract class Character extends Entity{
    String name;
    int x;
    int y;
    Inventory inventory = new Inventory();
    int lvl;
    final int startLvl = lvl;
    long exp;
    final long startExp = exp;
    int enemiesDefeated = 0;
    int strength;
    int charisma;
    int dexterity;

    public abstract void accept(Visitor visitor);
    abstract int receiveDamage(int dmg);
    abstract int getDamage();

    public abstract void addAbilities();
    public abstract void addBonusPotions();
    public int addEnemiesDefeated(){
        enemiesDefeated += 1;
        return enemiesDefeated;
    }
    public void levelUp(){
        if(exp >= 100) {
            lvl += 1;
            exp = exp - 100;
            calcCharisma();
            calcDexterity();
            calcStrength();
            System.out.println("LEVELED UP! *** LEVEL " + lvl + " ***");
        }
        int nextLvl = lvl + 1;
        long neededExp = 100 - exp;
        System.out.println("You need " + neededExp + "exp more to reach level " + nextLvl);
    }
    public int gainExp(){
        Random random = new Random();
        int rand = random.nextInt(30);
        exp += rand;
        System.out.println("  +" + rand + " exp");
        levelUp();
        return rand;
    }
    public int gainCoins(){
        Random random = new Random();
        int rand = random.nextInt(60);
        inventory.coins += rand;
        inventory.gainedCoins += rand;
        System.out.println("You won " + rand + " coins");
        return rand;
    }
    void calcStrength(){strength += 0.5*lvl*strength;}
    void calcCharisma(){charisma += 0.5*lvl*charisma;}
    void calcDexterity(){dexterity += 0.5*lvl*dexterity;}
    String buyPotions(Potion potion, Shop shop){
        String s = "";
        if(inventory.coins >= potion.getPrice() && inventory.calcWeight() >= potion.getWeight()) {
            inventory.coins  = inventory.coins - potion.getPrice();
            inventory.addPotion(potion);
            shop.selectPotion(shop.potions.indexOf(potion));
            System.out.println(potion.getClass().getName() + " was added to your inventory");
            s = potion.getClass().getName() + " was added to your inventory";
        }else {
            if (inventory.coins < potion.getPrice()) {
                System.out.println("Not enough coins");
                s = "Not enough coins";
            }
            if (inventory.calcWeight() < potion.getWeight()) {
                System.out.println("Not enough space in inventory");
                s = "Not enough space in inventory";
            }
        }
        return s;
    }
}