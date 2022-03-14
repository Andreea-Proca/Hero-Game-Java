public class ManaPotion implements Potion{
    int price = 25;
    int regValue = 30;
    int weight = 20;

    public void usePotion(Character character){
        character.inventory.removePotion(this);
        character.regenerateMana(regValue);
        System.out.println("  +" + regValue + " mana");
    }
    public int getPrice(){ return price;}
    public int getRegValue(){ return regValue;}
    public int getWeight(){ return weight;}
}