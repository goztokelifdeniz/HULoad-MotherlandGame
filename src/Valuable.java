public class Valuable extends Ground{
    private int valuable_worth;
    private int valuable_weight;
    Valuable(){
        this.setKills(false);
        this.setDrillable(true);
    }

    public void setValuable_weight(int valuable_weight) {
        this.valuable_weight = valuable_weight;
    }

    public void setValuable_worth(int valuable_worth) {
        this.valuable_worth = valuable_worth;
    }

    public int getValuable_weight() {
        return valuable_weight;
    }

    public int getValuable_worth() {
        return valuable_worth;
    }

    public void setValuableInfo(String file_name, Valuable current_valuable){
        switch (file_name) {
            case "valuable_ironium.png":
                current_valuable.setValuable_worth(30);
                current_valuable.setValuable_weight(10);
            case "valuable_bronzium.png":
                current_valuable.setValuable_worth(60);
                current_valuable.setValuable_weight(10);
            case "valuable_silverium.png":
                current_valuable.setValuable_worth(100);
                current_valuable.setValuable_weight(10);
            case "valuable_goldium.png":
                current_valuable.setValuable_worth(250);
                current_valuable.setValuable_weight(20);
            case "valuable_platinum.png":
                current_valuable.setValuable_worth(750);
                current_valuable.setValuable_weight(30);
            case "valuable_einsteinium.png":
                current_valuable.setValuable_worth(2000);
                current_valuable.setValuable_weight(40);
            case "valuable_emerald.png":
                current_valuable.setValuable_worth(5000);
                current_valuable.setValuable_weight(60);
            case "valuable_ruby.png":
                current_valuable.setValuable_worth(20000);
                current_valuable.setValuable_weight(80);
            case "valuable_diamond.png":
                current_valuable.setValuable_worth(100000);
                current_valuable.setValuable_weight(100);
            case "valuable_amazonite.png":
                current_valuable.setValuable_worth(500000);
                current_valuable.setValuable_weight(120);
        }
    }
}
