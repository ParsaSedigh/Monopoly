import java.util.Scanner;

public class Player{
    private String name;
    private int money;
    private int countProperties = 0;
    public String[] properties;
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
    private boolean isBankrupt = false;
    private int jailFreeTicket = 0;
    private int taxAreaTicket = 0;

    public void setTaxAreaTicket(int taxAreaTicket) {
        this.taxAreaTicket = taxAreaTicket;
    }

    public int getTaxAreaTicket() {
        return taxAreaTicket;
    }

    public void setJailFreeTicket(int jailFreeTicket) {
        this.jailFreeTicket = jailFreeTicket;
    }

    public int getJailFreeTicket() {
        return jailFreeTicket;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

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
        if(money - cost >= 0)
            money-=cost;
        else
            System.out.println("You don't have enough money to buy it !!");
    }

    public void fine(int cost){
        if(money - cost >= 0)
            money -= cost;
        else {
            while(money - cost <0){
                System.out.println("You don't have enough money to pay it !!");
                if(countProperties==0){
                    setBankrupt(true);
                    System.out.format("Player '%s' lost the game",getName());
                    return;
                }else{
                    System.out.println("You are out of money\nYou have to sell your properties");
                    sell();
                }
            }
            money-= cost;
        }
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

    public void buyProperties(String property) {
        countProperties++;
        properties = new String[countProperties];
        properties[countProperties - 1] = property;
        if (property.charAt(0) == 'C' || property.charAt(0) == 'c') {// if the property is cinema
            cinemaOwned++;
        } else if (property.charAt(0) == 'E' || property.charAt(0) == 'e') {
            emptyPlaceOwned++;
            Board[] temp = new EmptyPlace[emptyPlaceOwned];
            if(emptyPlaces != null) {
                for (int i = 0; i < emptyPlaceOwned; i++) {
                    if (emptyPlaces[i] != null)
                        temp[i] =emptyPlaces[i];
                }
            }
            temp[emptyPlaceOwned-1] = new EmptyPlace();
            temp[emptyPlaceOwned - 1].setName("emptyPlace");
            emptyPlaces = temp;
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
        System.out.println("1.Cinema");
        System.out.println("2.Empty place");
        int firstChoice = scanner.nextInt();
        while (firstChoice!=1 && firstChoice!=2){
            System.out.println("Error : not in Options!!");
            firstChoice = scanner.nextInt();
        }
        if(firstChoice==1){
            System.out.println("Which one do you want to sell ??");
            int[] tempIndex = new int[cinemaOwned];
            for(int i=0,j=0;i<countProperties;i++){
                if(properties[i].charAt(0)=='c' || properties[i].charAt(0)=='C'){
                    System.out.format("%d. %s\n", j+1,properties[i]);
                    tempIndex[j] = i;// saving the indexes of Cinemas in Properties
                    j++;
                }
            }
            int sellChoice = scanner.nextInt();
            while (sellChoice<0 || sellChoice>cinemaOwned){
                System.out.println("Error : Out of range !!");
                sellChoice = scanner.nextInt();
            }
            inCome(200/2);
            System.out.println("+100$");
            String[] tempProperties = new String[countProperties-1];
            for(int i=0,j=0 ; i<countProperties ; i++){
                if(i==tempIndex[sellChoice-1])
                    continue;
                tempProperties[j]=properties[i];
                j++;
            }
            System.out.format("%s sold successfully", properties[tempIndex[sellChoice-1]]);
            cinemaOwned--;
            countProperties--;
            properties = tempProperties;

        }else {
            System.out.println("Which one do you want to sell ??");
            for(int i=0;i<emptyPlaceOwned;i++){
                System.out.format("%d. %s\n", i+1,emptyPlaces[i]);
            }
            int sellChoice = scanner.nextInt();
            while (sellChoice<0 || sellChoice>emptyPlaceOwned){
                System.out.println("Error : Out of range !!");
                sellChoice = scanner.nextInt();
            }
            int moneyIncome = 100/2 +  emptyPlaces[sellChoice-1].getCountHouse()*150/2 + emptyPlaces[sellChoice-1].getCountHotel()*100/2 ;
            inCome(moneyIncome);
            System.out.format("+%d$\n",moneyIncome);
            Board[] temp = new Board[emptyPlaceOwned-1];
            for(int i=0,j=0;i<emptyPlaceOwned;i++){
                if(i==sellChoice-1)
                    continue;
                temp[j] = emptyPlaces[i];
                j++;
            }
            emptyPlaces[sellChoice-1].setOwned(false);
            emptyPlaceOwned--;
            emptyPlaces = temp;
        }
    }
}