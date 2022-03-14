import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TerminalType{
    ArrayList<Account> Accounts;
    HashMap<Enum, ArrayList<String>> Cells;

   void terminalType(ArrayList<Account> Accounts, HashMap<Enum, ArrayList<String>> Cells) throws InvalidCommandException {
        this.Accounts = Accounts;
        this.Cells = Cells;

        System.out.println("Choose an account :)");
        for(int i = 0; i < Accounts.size(); i++){
            Credentials credentials = Accounts.get(i).info_player.getCredentials();
            System.out.println("Press " + i + " for: " + credentials.getEmail());
            System.out.println("    " + Accounts.get(i).info_player.getName() + ", Country: " + Accounts.get(i).info_player.getCountry() + ", Maps completed: " + Accounts.get(i).nr_games);
        }

        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
       if(a > Accounts.size() ||  a < 0)
           throw new InvalidCommandException("Invalid command");

       Account account =  Accounts.get(a);

        System.out.println("Choose a character :)");
        for(int i = 0; i < account.characters.size(); i++){
            System.out.println("Press " + i + " for: " + account.characters.get(i).name);
            System.out.println(" - " + account.characters.get(i).getClass() + " - LVL " + account.characters.get(i).lvl + " - EXP " + account.characters.get(i).exp);
            System.out.println(" - ATTRIBUTES: * strength " + account.characters.get(i).strength + " * charisma " + account.characters.get(i).charisma + " * dexterity " + account.characters.get(i).dexterity);
        }

        Scanner inn = new Scanner(System.in);
        int b = inn.nextInt();
        if(b > account.characters.size() ||  b < 0)
            throw new InvalidCommandException("Invalid command");

       Character character =  account.characters.get(b);
        character.addAbilities();
        //character.addBonusPotions();  //optional

        Grid grid = Grid.generateMap(6,6);
        grid.setCharacter(character);
        grid.printGrid();
        while(grid.current_cell.type != Enum.FINISH) {
            System.out.println("Your next move (Press 'N', 'S', 'E' OR 'W'): ");
            Scanner scan = new Scanner(System.in);
            char c = scan.next().charAt(0);
            if( c == 'N')
                grid.goNorth();
            if( c == 'S')
                grid.goSouth();
            if( c == 'W')
                grid.goWest();
            if( c == 'E')
                grid.goEast();
            if( c != 'N' && c != 'S' &&  c != 'W' && c != 'E')
                throw new InvalidCommandException("Invalid command");
            if( grid.previous_cell != grid.current_cell) {
                System.out.println(" ** " + grid.current_cell.getType() + " ** ");
                tell_story(grid.current_cell);
                grid.printGrid();
                show_options(grid.current_cell, grid);
            }
        }
        account.nr_games += 1;
    }

    public void show_options(Cell current_cell, Grid grid) throws InvalidCommandException {
        if(current_cell.type == Enum.SHOP){
            currentShop(current_cell, grid);
        }
        if(current_cell.type == Enum.ENEMY){
            System.out.println("Enemy ahead!");
            Enemy enemy = (Enemy) current_cell.getElem();
            while(enemy.current_health > 0 && grid.character.current_health > 0) {
                characterAttacks(grid, enemy);
                if( enemy.current_health > 0)
                    enemyAttacks(grid, enemy);
                System.out.println("You " + grid.character.current_health + " health  || " + " Enemy " + enemy.current_health + " health");
            }
            if(enemy.current_health <= 0){
                System.out.println("Enemy defeated!");
                grid.character.addEnemiesDefeated();
                grid.character.gainCoins();
            }
            if(grid.character.current_health <= 0){
                System.out.println("You lost!");
                System.exit(0);
            }
        }
    }

    public void currentShop(Cell current_cell, Grid grid) throws InvalidCommandException {
        System.out.println("The shop is open");
        Shop shop = (Shop) current_cell.getElem();
        for(Potion p : shop.potions){
            System.out.println(" Press " + shop.potions.indexOf(p) + " to buy *" + p.getClass().getName() + "* --- Price: " + p.getPrice() + " --- Regenerate value: " + p.getRegValue());
        }
        System.out.println(" Press X to close shop");
        Scanner in = new Scanner(System.in);
        char c = in.next().charAt(0);
        int x = (int) c - 48;
        if( (x > shop.potions.size() || x < 0) && c != 'X')
            throw new InvalidCommandException("Invalid command");
        if(c != 'X') {
            int a = (int) c - 48;
            System.out.println(a);
            grid.character.buyPotions(shop.potions.get(a), shop);
        }
    }

    public void characterAttacks(Grid grid, Enemy enemy) throws InvalidCommandException {
        System.out.println(" Press 0 to *Attack enemy*");
        System.out.println(" Press 1 to *Use abilities*");
        System.out.println(" Press 2 to *Use potions*");
        Scanner in = new Scanner(System.in);
        int c = in.nextInt();
        if( c > 2 || c < 0)
            throw new InvalidCommandException("Invalid command");
        if (c == 0) {
            int dmg = enemy.receiveDamage(grid.character.getDamage());
            System.out.println("Enemy -" + dmg + " health");
            grid.character.gainExp();
        }
        if (c == 1) {
            System.out.println("Choose ability");
            System.out.println(" Press 0 for *ICE* --- damage: " + grid.character.Abillities.get(0).getDamage() + " --- mana price: " + grid.character.Abillities.get(0).getMana_price());
            System.out.println(" Press 1 to *FIRE* --- damage: " + grid.character.Abillities.get(1).getDamage() + " --- mana price: " + grid.character.Abillities.get(1).getMana_price());
            System.out.println(" Press 2 to *EARTH* --- damage: " + grid.character.Abillities.get(2).getDamage() + " --- mana price: " + grid.character.Abillities.get(2).getMana_price());
            int b = in.nextInt();
            if( b > 2 || b < 0)
                throw new InvalidCommandException("Invalid command");
            grid.character.useAbility(grid.character.Abillities.get(b), enemy);
            grid.character.gainExp();
        }
        if (c == 2) {
            if (grid.character.inventory.potions.isEmpty())
                System.out.println("Inventory is empty. You don't have any potions");
            else {
                for (Potion p : grid.character.inventory.potions) {
                    System.out.println(" Press " + grid.character.inventory.potions.indexOf(p) + " to use *" + p.getClass().getName() + "*" + " --- Regenerate value: " + p.getRegValue());
                }
                Scanner in2 = new Scanner(System.in);
                int d = in2.nextInt();
                if( d > grid.character.inventory.potions.size() || d < 0)
                    throw new InvalidCommandException("Invalid command");
                grid.character.inventory.potions.get(d).usePotion(grid.character);
            }
        }
    }
    public void enemyAttacks(Grid grid, Enemy enemy){
        Random random = new Random();
        int rand = random.nextInt(enemy.Abillities.size() + 1);
        if(rand == enemy.Abillities.size()){
            int dmg = grid.character.receiveDamage(enemy.getDamage());
            System.out.println("You -" + dmg + " health");
        }else
            enemy.useAbility(enemy.Abillities.get(rand), grid.character);
    }

    public void tell_story(Cell current_cell){
        String s = "";
        if(!current_cell.isVisited()){
            Random random = new Random();
            int rand = random.nextInt(Cells.get(current_cell.type).size());
            s = Cells.get(current_cell.type).get(rand);
            current_cell.setVisited(true);
        }else
            s = "You've been there...";
        System.out.println(s);
    }
}
