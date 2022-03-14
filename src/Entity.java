import java.util.ArrayList;

public abstract class Entity implements Element{
    ArrayList<Spell> Abillities = new ArrayList<>();
    int current_health;
    int max_health;
    int current_mana;
    int max_mana;
    boolean fire;
    boolean ice;
    boolean earth;

    public abstract void accept(Visitor visitor);

    void regenerateHealth(int add){
        if(current_health + add > max_health)
            current_health = max_health;
        current_health += add;
    }
    void regenerateMana(int add){
        if(current_mana + add > max_mana)
            current_mana = max_mana;
        current_mana += add;
    }
    void useAbility(Spell ability, Entity enemy){
        if(current_mana >= ability.getMana_price()){
            enemy.accept(ability);
            current_mana = current_mana - ability.getMana_price();
        }
    }
    abstract int receiveDamage(int dmg);
    abstract int getDamage();
}