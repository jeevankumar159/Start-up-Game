package example.jeevankumar.game;

public class User {
    public String displayname;
    public int noofcompanies=0;
    public int coins=50000;
    public User(){

    }
    public User(String displayname,int coins,int noofcompanies){
        this.displayname=displayname;
        this.coins=coins;
        this.noofcompanies=noofcompanies;
    }
}
