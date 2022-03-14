public class Test {
    public static void main(String[] string) throws Account.Information.UserBuilder.InformationIncompleteException, InvalidCommandException {
        Game.getInstance();
        Game.getInstance().saveProgress();
    }
}
