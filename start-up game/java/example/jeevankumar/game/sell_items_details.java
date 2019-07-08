package example.jeevankumar.game;

public class sell_items_details {
    String sellitemname;
    String sellitemenergy;
    String sellitemcost;
    String selleruserid;

    public sell_items_details(String sellitemname, String sellitemenergy, String sellitemcost, String selleruserid) {
        this.sellitemname = sellitemname;
        this.sellitemenergy = sellitemenergy;
        this.sellitemcost = sellitemcost;
        this.selleruserid = selleruserid;
    }

    public String getSellitemname() {
        return sellitemname;
    }

    public String getSellitemenergy() {
        return sellitemenergy;
    }

    public String getSellitemcost() {
        return sellitemcost;
    }
}
