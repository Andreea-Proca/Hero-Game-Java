public class HealthPotion implements Potion{
    int price = 30;
    int regValue = 35;
    int weight = 15;

    public void usePotion(Character character){
        character.inventory.removePotion(this);
        character.regenerateHealth(regValue);
        System.out.println("  +" + regValue + " health");
    }
    public int getPrice(){ return price;}
    public int getRegValue(){ return regValue;}
    public int getWeight(){ return weight;}
}