import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Game {
    ArrayList<Account> Accounts;
    HashMap<Enum, ArrayList<String>> Cells;

    private static Game instance;
    public static Game getInstance() throws Account.Information.UserBuilder.InformationIncompleteException, InvalidCommandException {
        if( instance == null)
            instance = new Game();
        return instance;
    }

    private Game() throws Account.Information.UserBuilder.InformationIncompleteException, InvalidCommandException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Accounts = new ArrayList();
        Cells = new HashMap<>();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Chooose the game type :)");
        System.out.println("Press 1 for terminal");
        System.out.println("Press 2 for graphic interface");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        if(a == 1) {
            System.out.println("You chose terminal");
            new TerminalType().terminalType(Accounts, Cells);
        }
        if(a == 2) {
            System.out.println("You chose graphic interface");
            new GUIType().GUIType(Accounts, Cells);
        }
        if( a != 1 && a != 2)
            System.out.println("Invalid command");

    }

    public void run() throws IOException, ParseException, JSONException, Account.Information.UserBuilder.InformationIncompleteException {
        Object object = new JSONParser().parse(new FileReader("input/accounts.json"));
        JSONObject obj = (JSONObject) object;
        JSONArray accountList = (JSONArray) obj.get("accounts");

        Iterator<JSONObject> iterator = accountList.iterator();
        while (iterator.hasNext()) {
            JSONObject jo = iterator.next();
            JSONObject credentials = (JSONObject) jo.get("credentials");
            String email = (String) credentials.get("email");
            String password = (String) credentials.get("password");
            String name = (String) jo.get("name");
            String country = (String) jo.get("country");

            JSONArray fav_games = (JSONArray) jo.get("favorite_games");
            ArrayList favorite_games = new ArrayList();
            favorite_games.sort(null);
            for (Object c : fav_games){
                    favorite_games.add(c.toString());
            }
            String maps_cmplt = (String) jo.get("maps_completed");
            int maps_completed = Integer.parseInt(maps_cmplt);

            JSONArray characters = (JSONArray) jo.get("characters");
            ArrayList<Character> characterArrayList = new ArrayList<>();
            Iterator<JSONObject> it = characters.iterator();
            while (it.hasNext()) {
                JSONObject jobj = it.next();
                String name1 = (String) jobj.get("name");
                String profession = (String) jobj.get("profession");
                String level1 = (String) jobj.get("level");
                long experience = (long) jobj.get("experience");
                int level = Integer.parseInt(level1);

                CharacterFactory characterFactory = new CharacterFactory();
                Character character = characterFactory.getCharacter(profession, name1, level, experience);
                characterArrayList.add(character);
            }
                Account account = new Account(characterArrayList, maps_completed, email, password, favorite_games, name, country);
                Accounts.add(account);

        }
        //getting the stories
        Object object1 = new JSONParser().parse(new FileReader("input/stories.json"));
        JSONObject obj1 = (JSONObject) object1;
        JSONArray stories = (JSONArray) obj1.get("stories");
        ArrayList empty_stories = new ArrayList();
        Cells.put(Enum.EMPTY, empty_stories);
        ArrayList enemy_stories = new ArrayList();
        Cells.put(Enum.ENEMY, enemy_stories);
        ArrayList shop_stories = new ArrayList();
        Cells.put(Enum.SHOP, shop_stories);
        ArrayList finish_stories = new ArrayList();
        Cells.put(Enum.FINISH, finish_stories);

        Iterator<JSONObject> iter = stories.iterator();
        while (iter.hasNext()) {
            JSONObject jo = iter.next();
            String type = (String) jo.get("type");
            String value = (String) jo.get("value");
            Cells.get(Enum.valueOf(type)).add(value);
        }
    }

    void saveProgress(){
        JSONArray accountList = new JSONArray();
        for(Account account : Accounts){
            JSONObject acc = new JSONObject();
            JSONObject credentials = new JSONObject();
            credentials.put("email", account.info_player.getCredentials().getEmail());
            credentials.put("password", account.info_player.getCredentials().getPassword());
            acc.put("credentials", credentials);
            acc.put("name", account.info_player.getName());
            acc.put("country", account.info_player.getCountry());
            JSONArray fav_games = new JSONArray();
            for (Object c : account.info_player.getFav_games()){
                fav_games.add(c.toString());
            }
            acc.put("favorite_games", fav_games);
            acc.put("mas_completed", account.nr_games);
            JSONArray characterList = new JSONArray();
            for(Character character : account.characters) {
                JSONObject chr = new JSONObject();
                chr.put("name", character.name);
                chr.put("profession", character.getClass().getName());
                chr.put("level", character.lvl);
                chr.put("experience", character.exp);
                characterList.add(chr);
            }
            acc.put("characters", characterList);
            accountList.add(acc);
        }
        JSONObject accounts = new JSONObject();
        accounts.put("accounts", accountList);
        try (FileWriter file = new FileWriter("output/UpdatesAccounts.json")) {
            file.write(accounts.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}