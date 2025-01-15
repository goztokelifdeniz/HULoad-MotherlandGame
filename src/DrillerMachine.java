public class DrillerMachine {
    private int index_machine_is_at;
    private double money;
    private double haul;
    private double fuel;

    DrillerMachine(){
        this.money = 0;
        this.haul = 0;
        this.fuel = 100000;
        this.index_machine_is_at = 42;
    }

    public void setIndex_machine_is_at(int index_machine_is_at) {
        this.index_machine_is_at = index_machine_is_at;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public void setHaul(double haul) {
        this.haul = haul;
    }

    public void setMoney(double money) {
        this.money = money;
    }



    public int getIndex_machine_is_at() {
        return index_machine_is_at;
    }

    public double getFuel() {
        return fuel;
    }

    public double getHaul() {
        return haul;
    }

    public double getMoney() {
        return money;
    }

}
