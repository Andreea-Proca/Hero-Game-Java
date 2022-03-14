import java.util.Random;

public class Enemy extends Entity implements CellElement{
    int damage;
    public char toCharacter(){
        return 'E';
    }
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    public Enemy(){
        Random random = new Random();
        damage = random.nextInt(35 - 5) + 5;
        max_health = random.nextInt(101 - 25) + 25;
        current_health = max_health;
        max_mana = random.nextInt(101 - 25) + 25;
        current_mana = max_mana;

        int rand = random.nextInt(2);
        if(rand == 0) fire = false;
        else fire = true;

        rand = random.nextInt(2);
        if(rand == 0) ice = false;
        else ice = true;

        rand = random.nextInt(2);
        if(rand == 0) earth = false;
        else earth = true;
        // randomise some abillities
        rand = random.nextInt(5 - 2) + 2;
        for(int i = 0; i < rand; i++){
            int rand1 = random.nextInt(4 - 1) + 1;
            if(rand1 == 1)
                Abillities.add(new Ice());
            if(rand1 == 2)
                Abillities.add(new Fire());
            if(rand1 == 3)
                Abillities.add(new Earth());
        }

    }
    int receiveDamage(int dmg){
        Random random = new Random();
        int rand = random.nextInt(10);
        if(rand < 5) {
            System.out.println("MISS! Enemy -0 health");
            return 0;
        }
        current_health = current_health - dmg;
        return dmg;
    }
    int getDamage(){
        Random random = new Random();
        int rand = random.nextInt(10);
        if(rand < 5)
            return 2*damage;
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}