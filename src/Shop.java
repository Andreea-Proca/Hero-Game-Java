import java.util.ArrayList;
import java.util.Random;

public class Shop implements CellElement {
    public ArrayList<Potion> potions = new ArrayList<Potion>();

    public char toCharacter() {
        return 'S';
    }

    public Shop() {
        Random random = new Random();
        int rand = random.nextInt(5 - 2) + 2;
        for (int i = 0; i < rand; i++) {
            int rand1 = random.nextInt(2);
            if (rand1 == 1)
                potions.add(new HealthPotion());
            else
                potions.add(new ManaPotion());
        }
    }

    public Potion selectPotion(int index) {
        Potion p = potions.get(index);
        potions.remove(index);
        return p;
    }
}