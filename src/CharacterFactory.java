public class CharacterFactory {
    public CharacterFactory(){}
    public Character getCharacter(String characterType, String name, int level, long experience){
        if(characterType == null){
            return null;
        }
        if(characterType.equalsIgnoreCase("Warrior")){
            return new Warrior(name, level, experience, 100, 70);

        } else if(characterType.equalsIgnoreCase("Rogue")){
            return new Rogue(name, level, experience, 120, 90);

        } else if(characterType.equalsIgnoreCase("Mage")){
            return new Mage(name, level, experience, 150, 100);
        }
        return null;
    }
}