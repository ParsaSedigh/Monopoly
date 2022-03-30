import java.util.Scanner;

public class Player{
    private String name;
    private int money;
    private int countProperties = 0;
    private String[] properties;
    private String[] hotels;
    private Board[] emptyPlaces;
    private int cinemaOwned = 0;
    private int position = 1;
    private boolean isPrisoner = false;
    private boolean haveInvest = false;
    private int investedMoney;
    private int emptyPlaceOwned = 0;
    private int houseOwned = 0;
    private int hotelsOwned = 0;
    private boolean monopoly = false;

    public Board[] getEmptyPlaces() {
        return emptyPlaces;
    }

    public void setMonopoly(boolean monopoly) {
        this.monopoly = monopoly;
    }

    public boolean isMonopoly() {
        return monopoly;
    }

    public int getHouseOwned() {
        return houseOwned;
    }

    public int getHotelsOwned() {
        return hotelsOwned;
    }

    public int getEmptyPlaceOwned() {
        return emptyPlaceOwned;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getInvestedMoney() {
        return investedMoney;
    }

    public void setInvestedMoney(int investedMoney) {
        this.investedMoney = investedMoney;
    }

    public boolean getHaveInvest() {
        return haveInvest;
    }

    public void setHaveInvest(boolean haveInvest) {
        this.haveInvest = haveInvest;
    }

    public void setPrisoner(boolean prisoner) {
        isPrisoner = prisoner;
    }

    public boolean isPrisoner() {
        return isPrisoner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void buy(int cost){
        money-=cost;
    }

    public void fine(int cost){
        money -= cost;
    }

    public void inCome(int amount){
        money+=amount;
    }

    public Player() {
        this.money = 1500;// first time that game starts
    }

    public void move(int dice){
        position += dice;
        if(position>24){
            position-=24;
        }
    }

    public int getCountProperties() {
        return countProperties;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void buyProperties(Player player , String property) {
        player.countProperties++;
        player.properties = new String[countProperties];
        player.properties[countProperties - 1] = property;
        if (property.charAt(0) == 'C' || property.charAt(0) == 'c') {// if the property is cinema
            cinemaOwned++;
        } else if (property.charAt(0) == 'E' || property.charAt(0) == 'e') {
            emptyPlaceOwned++;
            Board[] temp = new EmptyPlace[emptyPlaceOwned];
            if(emptyPlaces != null) {
                for (int i = 0; i < emptyPlaceOwned; i++) {
                    if (player.emptyPlaces[i] != null)
                        temp[i] = player.emptyPlaces[i];
                }
            }
            temp[emptyPlaceOwned-1] = new EmptyPlace();
            temp[emptyPlaceOwned - 1].setName("emptyPlace");
            player.emptyPlaces = temp;
        }
    }

    public void buildProperties(Player player , Board emptyPlace){
        if(emptyPlace.getCountHotel() == 0) {
            if (emptyPlace.getCountHouse() < 4) {
                if (buildHousePermit(player)) {
                    player.houseOwned++;
                    emptyPlace.houses = new String[player.getHouseOwned()];
                    emptyPlace.houses[player.getHouseOwned() - 1] = "House";
                    System.out.println("House Built");
                    player.buy(150);
                    emptyPlace.setCountHouse(houseOwned);
                } else {
                    System.out.println("You Are Not Allowed To Build A House");
                }
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Do You Want To Build Hotel and Replace The Hotel With Your Houses?(build / Not Interested");
                if (scanner.next().equals("build")) {
                    emptyPlace.delHouses();
                    houseOwned = 0;
                    player.hotelsOwned++;
                    player.hotels = new String[player.getHotelsOwned()];
                    player.hotels[player.getHotelsOwned() - 1] = "Hotel";
                    player.buy(100);
                    emptyPlace.setCountHotel(hotelsOwned);
                    System.out.println("Build Hotel");
                }
            }
        }
        else{
            System.out.println("You Have Hotel In This Place");
        }
    }

    public String[] getProperties() {
        return properties;
    }

    public int getCinemaOwned() {
        return cinemaOwned;
    }

    public boolean buildHousePermit(Player player){
        boolean flag = true;
        if(player.emptyPlaces.length > 1) {
            for (int i = 0, j = 1; i < getEmptyPlaceOwned(); i++) {
                if(j < getEmptyPlaceOwned()) {
                    if (player.emptyPlaces[i].getCountHouse() != player.emptyPlaces[j].getCountHouse())
                        flag = false;
                    j++;
                }
            }
        }
        return flag;
    }

    public void sell(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the list of your properties :");
        for(int i=0;i<countProperties;i++){
            System.out.format("%d. %s\n", i+1,properties[i]);
        }
        System.out.println("Which one do you want to sell ??");
        int sellChoice = scanner.nextInt();
        while (sellChoice<0 && sellChoice>countProperties){
            System.out.println("Error : Out of range !!");
            sellChoice = scanner.nextInt();
        }
        String propertyChoice = properties[sellChoice-1];
        if(propertyChoice.charAt(0)=='c' || propertyChoice.charAt(0)=='C'){// if the property was a cinema
            System.out.format("You've earned 100$ by selling %s\n",propertyChoice);
            System.out.println("+100$");
            inCome(100);
        }
        String[] temp = new String[countProperties-1];
        for(int i=0,j=0;i<countProperties;i++){
            if(i+1==sellChoice){
                continue;
            }
            temp[j]=properties[i];
            j++;
        }
        countProperties--;
        properties = temp;
    }
}