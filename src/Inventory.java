import java.util.ArrayList;

public class Inventory{
    ArrayList<Potion> potions = new ArrayList<Potion>();
    int max_weight;
    int coins = 20;
    int gainedCoins = 0;

    void addPotion(Potion potion){
        potions.add(potion);
    }
    void removePotion(Potion potion){
        potions.remove(potion);
    }
    int calcWeight(){
        int i = 0;
        for(Potion p : potions)
            i += p.getWeight();
        return max_weight - coins - i;
    }
}