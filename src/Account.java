import java.util.ArrayList;
import java.util.Collection;

public class Account{
    Information info_player;
    ArrayList<Character> characters;
    int nr_games;

    public Account(){}
    public Account(ArrayList<Character> characters, int nr_games, String email, String password, Collection fav_games, String name, String country) throws Information.UserBuilder.InformationIncompleteException {
        this.characters = characters;
        this.nr_games = nr_games;
        this.info_player = new Information.UserBuilder(email, password)
                .fav_games(fav_games)
                .name(name)
                .country(country)
                .build();
    }

    static class Information{
        private Credentials credentials;
        private Collection fav_games;
        private String name;
        private String country;

        private Information(UserBuilder builder){
            this.credentials = builder.credentials;
            this.fav_games = builder.fav_games;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }
        public Collection getFav_games() {
            return fav_games;
        }
        public String getName() {
            return name;
        }
        public String getCountry() {
            return country;
        }

        public static class UserBuilder {
            private Credentials credentials;
            private Collection fav_games;
            private String name;
            private String country;

            public UserBuilder(String email, String password) {
                credentials = new Credentials(email, password);
            }
            public UserBuilder fav_games(Collection fav_games) {
                this.fav_games = fav_games;
                return this;
            }
            public UserBuilder name(String name) {
                this.name = name;
                return this;
            }
            public UserBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() throws InformationIncompleteException {
                Information inf =  new Information(this);
                if (credentials == null || name == null)
                    throw new InformationIncompleteException("Incomplete information");
                return inf;
            }

            public class InformationIncompleteException extends Exception {
                public InformationIncompleteException(String errorMsg) {
                    super(errorMsg);
                }
            }

        }
    }
}