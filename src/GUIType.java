import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class GUIType {
    ArrayList<Account> Accounts;
    HashMap<Enum, ArrayList<String>> Cells;
    void GUIType(ArrayList<Account> Accounts, HashMap<Enum, ArrayList<String>> Cells){
        this.Accounts = Accounts;
        this.Cells = Cells;

        JFrame frame = new JFrame();
        frame.setTitle("World of Marcel");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationByPlatform(true);
        frame.setLayout(new GridLayout(0,1));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(400, 325);
        frame.setBackground(new Color(204, 255, 229));
        frame.add(new JLabel(new ImageIcon("images/title.png"), SwingConstants.CENTER));

        ///////////AUTHENTICATION
        JPanel panel = new JPanel(new GridLayout(6,1));
        panel.setBackground(new Color(204, 255, 229));
        frame.add(panel);

        JLabel choose_acc = new JLabel("Login to your account", SwingConstants.CENTER);
        panel.add(choose_acc, BorderLayout.CENTER);
        JLabel insert_email = new JLabel("EMAIL:", SwingConstants.CENTER);
        panel.add(insert_email, BorderLayout.CENTER);
        JTextField email_field = new JTextField(10);
        panel.add(email_field, BorderLayout.CENTER);
        JLabel insert_password = new JLabel("PASSWORD:", SwingConstants.CENTER);
        panel.add(insert_password, BorderLayout.CENTER);
        JTextField password_field = new JTextField(10);
        panel.add(password_field, BorderLayout.CENTER);

        JButton login = new JButton();
        login.setEnabled(true);
        login.setText("LOG IN");

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                String email = email_field.getText();
                String password = password_field.getText();
                if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    Account account = null;
                    for(int i = 0; i < Accounts.size(); i++){
                        Credentials credentials = Accounts.get(i).info_player.getCredentials();
                        if(email.equals(credentials.getEmail()) && password.equals(credentials.getPassword()))
                            account = Accounts.get(i);
                    }
                    if(account == null)
                        showMessageDialog(frame,"Incorrect email or password!");
                    else {
                        showMessageDialog(frame, "Logging in...");
                        panel.setVisible(false);

                        chooseCharacter(account, frame);
                    }
                }else{
                    showMessageDialog(frame,"Missing email or password!");
                }
            }
        };
        panel.add(login);
        login.addActionListener(act);

        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

    void chooseCharacter(Account account, JFrame frame){
        frame.setSize(620, 350);
        final ArrayList<Character> characterArrayList = account.characters;
        JPanel panel1 = new JPanel();
        frame.add(panel1);
        panel1.add(BorderLayout.CENTER, new JScrollPane());

        JLabel choose_ch = new JLabel("Chooose a character :)");
        panel1.add(choose_ch);

        JButton choose = new JButton();
        choose.setEnabled(false);
        choose.setText("CHOOSE");

        ButtonGroup group = new ButtonGroup();
        ActionListener actdis = new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                if (e.getSource() instanceof JRadioButton ) {
                    choose.setEnabled(true);
                }
            }
        };
        ActionListener actlis = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e ) {
                Enumeration<AbstractButton> buttonEnum = group.getElements();
                while(buttonEnum.hasMoreElements()){
                    JRadioButton butt = (JRadioButton) buttonEnum.nextElement();
                    if(butt.isSelected() ){
                        butt.setBackground(Color.ORANGE);
                        String text = butt.getText();
                        char index = text.charAt(0);
                        showMessageDialog(frame,"You chose  " + text);
                        int a = (int) index - 48;

                        Character character = characterArrayList.get(a);
                        showMessageDialog(frame,"Loading map...");
                        loadMap(character, account);
                        panel1.setVisible(false);
                        frame.dispose();
                        break;
                    }
                }
            }
        };
        choose.addActionListener(actlis);

        for(int i = 0; i < account.characters.size(); i++){
            JRadioButton button = new JRadioButton();
            String s = i + " -> ";
            s += account.characters.get(i).name;
            s += " - " + account.characters.get(i).getClass() + " - LVL " + account.characters.get(i).lvl + " - EXP "+ account.characters.get(i).exp;
            s += " - ATTRIBUTES: * strength " + account.characters.get(i).strength + " * charisma " + account.characters.get(i).charisma + " * dexterity " + account.characters.get(i).dexterity;
            button.setText(s);
            button.addActionListener(actdis);
            panel1.add(button);
            group.add(button);
        }
        panel1.add(choose);
    }

    void loadMap(Character character, Account account){
        character.addAbilities();
        //character.addBonusPotions(); // optional

        Grid grid = Grid.generateMap(6,6);
        grid.setCharacter(character);

        ButtonGroup group = new ButtonGroup();
        JButton north = new JButton("North");
        JButton south = new JButton("South");
        JButton east = new JButton("East");
        JButton west = new JButton("West");
        group.add(north);
        group.add(south);
        group.add(east);
        group.add(west);

        JPanel text = new JPanel();
        text.setFont(new Font("EightBit Atari-Regular", Font.BOLD,16));
        text.setBackground(new Color(255, 178, 102));
        JFrame frame = new JFrame("World of Marcel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        final ArrayList<JSplitPane> splitPanes = new ArrayList<>();
        JSplitPane sp = grid.printGridGUI(frame, north, south, east, west, text);
        splitPanes.add(sp);
        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(grid.current_cell.type != Enum.FINISH) {
                    if (e.getSource() == north)
                        grid.goNorth();
                    if (e.getSource() == south)
                        grid.goSouth();
                    if (e.getSource() == east)
                        grid.goEast();
                    if (e.getSource() == west)
                        grid.goWest();
                    if( grid.previous_cell != grid.current_cell) {
                        frame.getContentPane().remove(splitPanes.get(splitPanes.size() - 1));

                        JPanel text = new JPanel(new GridLayout(0, 1));
                        text.setFont(new Font("EightBit Atari-Regular", Font.BOLD, 16));
                        text.setBackground(new Color(255, 178, 102));

                        JSplitPane sp = grid.printGridGUI(frame, north, south, east, west, text);
                        splitPanes.add(sp);
                        showMessageDialog(frame, tell_story(grid));

                        text.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
                        text.doLayout();
                        show_options(grid.current_cell, grid, text, frame);
                    }else
                        showMessageDialog(frame, "INVALID MOVE!");
                }else {
                    account.nr_games += 1;
                    showMessageDialog(frame, "REACHED FINISH! YOU WON!");
                    frame.dispose();
                    showProgress(grid.character);
                }
            }
        };
        north.addActionListener(act);
        south.addActionListener(act);
        east.addActionListener(act);
        west.addActionListener(act);
    }

    public void show_options(Cell current_cell, Grid grid, JPanel text, JFrame frame){
        if(current_cell.type == Enum.SHOP){
            currentShop(current_cell, grid, text);
        }
        if(current_cell.type == Enum.ENEMY){
            showMessageDialog(frame, "ENEMY AHEAD!");
            Enemy enemy = (Enemy) current_cell.getElem();
            text.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
            text.doLayout();
            characterAttacks(grid, enemy, text);
            if(enemy.current_health <= 0){
                text.add(new JLabel("ENEMY DEFEATED!"));
                text.doLayout();
                showMessageDialog(frame, "ENEMY DEFEATED!");
                grid.character.addEnemiesDefeated();
                grid.character.gainCoins();
            }
            if(grid.character.current_health <= 0){
                showMessageDialog(frame, "YOU DIED!");
                System.exit(0);
            }
        }
    }

    public void currentShop(Cell current_cell, Grid grid, JPanel text){
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(0,1));
        frame.setTitle("Potion Shop");
        frame.setMinimumSize(new Dimension(400, 300));
        JPanel text1 = new JPanel(new GridLayout(0,1));

        text1.add(new JLabel("The shop is open"));
        Shop shop = (Shop) current_cell.getElem();

        DefaultListModel<Potion> potionList = new DefaultListModel<>();
        for(Potion p : shop.potions){
            potionList.addElement(p);
        }
        JScrollPane scroll = new JScrollPane();
        JList<Potion> list = new JList<>(potionList);
        scroll.setViewportView(list);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton buy = new JButton("Buy");
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty())
                    return;
                else {
                    String s = grid.character.buyPotions(list.getSelectedValue(), shop);
                    showMessageDialog(frame, s);
                    potionList.remove(list.getSelectedIndex());
                    frame.dispose();
                }
            }
        });

        JButton close = new JButton("Close shop");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        buttons.add(buy);
        buttons.add(close);
        text1.add(scroll);
        text1.add(buttons);
        frame.add(text1);
        frame.show();
    }

    public void checkDeath(Character character, Enemy enemy, JFrame frame, JPanel panel){
        if(enemy.current_health <= 0){
            panel.add(new JLabel("ENEMY DEFEATED!"));
            panel.doLayout();
            showMessageDialog(frame, "ENEMY DEFEATED!");
            character.addEnemiesDefeated();
            showMessageDialog(frame, "You gained " + character.gainCoins() + " coins");
        }
        if(character.current_health <= 0){
            showMessageDialog(frame, "YOU DIED!");
            System.exit(0);
        }
    }

    public void characterAttacks(Grid grid, Enemy enemy, JPanel panel){
        JFrame frame = new JFrame("ATTACK");
        frame.setLayout(new GridLayout(0,1));
        frame.setMinimumSize(new Dimension(400, 300));
        JPanel text1 = new JPanel(new GridLayout(0,1));

        JButton button1 = new JButton("*Attack enemy*");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dmg = enemy.receiveDamage(grid.character.getDamage());
                showMessageDialog(frame, "Enemy -" + dmg + " health");
                grid.character.gainExp();
                frame.dispose();
                panel.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
                panel.doLayout();
                checkDeath(grid.character, enemy, frame, panel);
                if( enemy.current_health > 0)
                    enemyAttacks(grid, enemy, frame, panel);
            }
        });

        JButton button2 = new JButton("*Use abilities*");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame f = new JFrame("ABILITIES");
                f.setLayout(new GridLayout(0,1));
                f.setMinimumSize(new Dimension(400, 300));
                JPanel text2 = new JPanel(new GridLayout(0,1));

                JButton ice = new JButton("*ICE* --- damage: " + grid.character.Abillities.get(0).getDamage() + " --- mana price: " + grid.character.Abillities.get(0).getMana_price());
                JButton fire = new JButton("*FIRE* --- damage: " + grid.character.Abillities.get(1).getDamage() + " --- mana price: " + grid.character.Abillities.get(1).getMana_price());
                JButton earth = new JButton("*EARTH* --- damage: " + grid.character.Abillities.get(2).getDamage() + " --- mana price: " + grid.character.Abillities.get(2).getMana_price());
                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource() == ice){
                            grid.character.useAbility(grid.character.Abillities.get(0), enemy);
                            grid.character.gainExp();
                            f.dispose();
                        }
                        if(e.getSource() == fire){
                            grid.character.useAbility(grid.character.Abillities.get(1), enemy);
                            grid.character.gainExp();
                            f.dispose();
                        }
                        if(e.getSource() == earth){
                            grid.character.useAbility(grid.character.Abillities.get(2), enemy);
                            grid.character.gainExp();
                            f.dispose();
                        }
                        panel.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
                        panel.doLayout();
                        checkDeath(grid.character, enemy, frame, panel);
                        if( enemy.current_health > 0)
                            enemyAttacks(grid, enemy, frame, panel);
                    }
                };
                ice.addActionListener(actionListener);
                fire.addActionListener(actionListener);
                earth.addActionListener(actionListener);
                text2.add(ice);
                text2.add(fire);
                text2.add(earth);
                f.add(text2);
                f.show();
            }
        });

        JButton button3 = new JButton("*Use potions*");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (grid.character.inventory.potions.isEmpty())
                    showMessageDialog(frame, "Inventory is empty. You don't have any potions");
                else {
                    frame.dispose();
                    JFrame fr = new JFrame("ABILITIES");
                    fr.setLayout(new GridLayout(0,1));
                    fr.setMinimumSize(new Dimension(400, 300));
                    JPanel text3 = new JPanel(new GridLayout(0,1));

                    DefaultListModel<Potion> potionList = new DefaultListModel<>();
                    for (Potion p : grid.character.inventory.potions) {
                        potionList.addElement(p);
                    }
                    JScrollPane scroll = new JScrollPane();
                    JList<Potion> list = new JList<>(potionList);
                    scroll.setViewportView(list);

                    JPanel buttons = new JPanel(new FlowLayout());
                    JButton use = new JButton("Use");
                    use.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (list.isSelectionEmpty())
                                return;
                            else {
                                grid.character.inventory.potions.get(list.getSelectedIndex()).usePotion(grid.character);
                                showMessageDialog(frame, "You used " + list.getSelectedValue().getClass().getName());
                                potionList.remove(list.getSelectedIndex());
                                fr.dispose();
                                panel.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
                                panel.doLayout();
                                checkDeath(grid.character, enemy, frame, panel);
                                if( enemy.current_health > 0)
                                    enemyAttacks(grid, enemy, frame, panel);
                            }
                        }
                    });
                    JButton close = new JButton("Close");
                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            fr.dispose();
                            panel.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
                            panel.doLayout();
                            checkDeath(grid.character, enemy, frame, panel);
                            if( enemy.current_health > 0)
                                enemyAttacks(grid, enemy, frame, panel);
                        }
                    });
                    buttons.add(use);
                    buttons.add(close);
                    text3.add(scroll);
                    text3.add(buttons);
                    fr.add(text3);
                    fr.show();
                }
            }
        });
        text1.add(button1);
        text1.add(button2);
        text1.add(button3);
        frame.add(text1);
        frame.show();
    }

    public void enemyAttacks(Grid grid, Enemy enemy, JFrame frame, JPanel panel){
        Random random = new Random();
        int rand = random.nextInt(enemy.Abillities.size() + 1);
        if(rand == enemy.Abillities.size()){
            int dmg = grid.character.receiveDamage(enemy.getDamage());
            showMessageDialog(frame, "You -" + dmg + " health");
        }else
            enemy.useAbility(enemy.Abillities.get(rand), grid.character);
        panel.add(new JLabel(getInfo(grid.character, grid.current_cell.getElem())));
        panel.doLayout();
        checkDeath(grid.character, enemy, frame, panel);
        if(grid.character.current_health > 0)
            characterAttacks(grid, enemy, panel);
    }

    public String tell_story(Grid grid){
        String s = "";
        if(grid.current_cell.x - 1 < 0 && grid.current_cell.x + 1 > grid.width && grid.current_cell.y - 1 < 0 && grid.current_cell.y + 1 > grid.length)
            s = "Invalid move!";
        else{
            if(!grid.current_cell.isVisited()){
                Random random = new Random();
                int rand = random.nextInt(Cells.get(grid.current_cell.type).size());
                s = Cells.get(grid.current_cell.type).get(rand);
                grid.current_cell.setVisited(true);
            }else
                    s = "You've been there...";
        }
        System.out.println(s);
        return s;
    }

    public String getInfo(Character character, CellElement elem){
        String s = "YOU  --->  ";
        s += "Health: " + character.current_health +  "  *  ";
        s += "Mana: "+ character.current_mana + "  *  ";
        s += "Exp: " + character.exp + "  *  ";
        s += "Lvl:" + character.lvl + "  *  ";
        s += "Coins:" + character.inventory.coins;

        if(elem instanceof Enemy) {
            Enemy enemy = (Enemy) elem;
            s += "            ||            ENEMY --->  ";
            s += "Health: " + enemy.current_health + "  *  ";
            s += "Mana: " + enemy.current_mana + "  *  ";
            if (enemy.ice == true)
                s += "Protection: ICE  *  ";
            if (enemy.fire == true)
                s += "Protection: FIRE  *  ";
            if (enemy.earth == true)
                s += "Protection: EARTH  *  ";
        }
        return s;
    }

    public void showProgress(Character character){
        JFrame frame = new JFrame("World of Marcel");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(300, 300);

        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.setBackground(new Color(204, 255, 204));

        JLabel progress = new JLabel("****YOUR PROGRESS****", SwingConstants.CENTER);
        progress.setFont(new Font("EightBit Atari-Regular", Font.BOLD,18));
        progress.setBackground(new Color(248, 196, 113));
        panel.add(progress, SwingConstants.CENTER);
        JLabel exp = new JLabel("EXP +" + (character.exp - character.startExp), SwingConstants.CENTER);
        exp.setFont(new Font("EightBit Atari-Regular", Font.BOLD,18));
        panel.add(exp, SwingConstants.CENTER);
        JLabel lvl = new JLabel("LVL " + character.lvl, SwingConstants.CENTER);
        lvl.setFont(new Font("EightBit Atari-Regular", Font.BOLD,18));
        panel.add(lvl, SwingConstants.CENTER);
        JLabel enemies = new JLabel("ENEMIES DEFEATED " + character.enemiesDefeated, SwingConstants.CENTER);
        enemies.setFont(new Font("EightBit Atari-Regular", Font.BOLD,18));
        panel.add(enemies, SwingConstants.CENTER);
        JLabel coins = new JLabel("COINS +" + character.inventory.gainedCoins, SwingConstants.CENTER);
        coins.setFont(new Font("EightBit Atari-Regular", Font.BOLD,18));
        panel.add(coins, SwingConstants.CENTER);

        frame.add(panel);
        frame.show();
    }
}