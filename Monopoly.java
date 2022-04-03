import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Monopoly {
    private static Player[] players = new Player[4];
    private static int dice;
    private static final Board[] boardHouses = new Board[24];

    public static void PlayerInit(int countPlayer) {
        Scanner scanner = new Scanner(System.in);
        String name;
        for (int i = 0; i < countPlayer; i++) {
            players[i] = new Player();
            System.out.print("Enter the name of player number ");
            System.out.print(i+1);
            System.out.println(" : ");
            name = scanner.nextLine();
            players[i].setName(name);
        }
        int choice = menu();
        while(choice!=2){
            if(choice==1){
                System.out.println("ERROR : You have created the game before !!");
                choice = scanner.nextInt();
            }else {
                System.out.println("Error : Out of options !!");
                choice = scanner.nextInt();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean startPermission = false;
        while(!startPermission){
            int choice = menu();
            if(choice == 1){
                System.out.println("Number Of Players:");
                int countPlayer = scanner.nextInt();
                while(countPlayer < 2 || countPlayer > 4) {
                    System.out.println("Number Of Players must be Between 2 & 4!");
                    countPlayer = scanner.nextInt();
                }
                players = new Player[countPlayer];
                PlayerInit(countPlayer);
                startPermission = true;
            } else if(players[0]==null){
                System.out.println("ERROR : No game created!!");
            } else {
                startPermission = true;
            }
        }
        sortPlayerByDice(players);
        startLoadBoard();
        boolean endGame = false;
        while(!endGame){
            for(int i=0 ; i< players.length;i++){
                System.out.format("%s : \n", players[i].getName());
                showBoard();
                String command = scanner.next();
                while (!command.equals("sell") && !command.equals("index") && !command.equals("rank") && !command.equals("property") && !command.equals("dice")){
                    System.out.println("Error : Wrong command !!");
                    command = scanner.next();
                }
                while (!command.equals("dice")){
                    switch (command) {
                        case "sell":
                            players[i].sell();
                            break;
                        case "index":
                            System.out.format("You are in : %d \n", players[i].getPosition());
                            break;
                        case "rank":
                            System.out.println("Rank of players in order of money : ");
                            Player[] tempPlayers = new Player[players.length];
                            for(int j=0;j< players.length;j++){
                                tempPlayers[j]=players[j];
                            }
                            Player sub ;
                            for(int j=0;j< players.length;j++){
                                for(int k=j+1;j< players.length && k< players.length;k++){
                                    if(tempPlayers[j].getMoney()<tempPlayers[k].getMoney()){
                                        sub = tempPlayers[j];
                                        tempPlayers[j] = tempPlayers[k];
                                        tempPlayers[k] = sub;
                                    }
                                }
                            }
                            for(int j=0;j< tempPlayers.length;j++){
                                if(players[i].getName().equals(tempPlayers[j].getName())){
                                    System.out.format("%d.%s  %d$ <-\n",j+1,tempPlayers[j].getName(),tempPlayers[j].getMoney());
                                }else{
                                    System.out.format("%d.%s  %d$\n",j+1,tempPlayers[j].getName(),tempPlayers[j].getMoney());
                                }
                            }
                            break;
                        case "property":
                            String[] temp = players[i].getProperties();
                            if(temp==null){
                                System.out.format("%s has no property\n", players[i].getName());
                                break;
                            }
                            for(int j=0; j<temp.length; j++){
                                System.out.format("%d.%s\n", j+1,temp[j]);
                            }
                            break;
                    }
                    System.out.println("Enter your next command : ");
                    command = scanner.next();
                    while (!command.equals("sell") && !command.equals("index") && !command.equals("rank") && !command.equals("property") && !command.equals("dice")){
                        System.out.println("Error : Wrong command !!");
                        command = scanner.next();
                    }
                }
                if (players[i].isPrisoner()) {
                    System.out.println("You are still a prisoner");
                    System.out.println("free/nothing/dice");
                    jailProcess(players[i]);
                } else {
                    System.out.format("Enter 1 to 6 for dice : \n");
                    int diceNumber = scanner.nextInt();
                    while (diceNumber < 1 || diceNumber > 6) {
                        System.out.println("Error : Dice number out of range !!");
                        diceNumber = scanner.nextInt();
                    }
                    if (diceNumber == 6) {
                        System.out.println("Be careful , next 6 will make you a prisoner , Dice Again:");
                        diceNumber = scanner.nextInt();
                        while (diceNumber < 1 || diceNumber > 6) {
                            System.out.println("Error : Dice number out of range !!");
                            diceNumber = scanner.nextInt();
                        }
                        if (diceNumber == 6) {
                            System.out.println("You َAre in Jail now  :O");
                            players[i].setPrisoner(true);
                            players[i].setPosition(13);
                            System.out.println("free/nothing/dice");
                            jailProcess(players[i]);
                        }
                    } else {
                        players[i].move(diceNumber);
                        playProcesses(players[i]);
                    }
                }
                int inGamePlayers=0;
                for(int j=0; j< players.length;j++){
                    if(players[j].isBankrupt()){
                        continue;
                    }
                    inGamePlayers++;
                }
                Player[] tempPlayersInGame = new Player[inGamePlayers];
                for(int j=0,k=0;j<players.length;j++){
                    if(players[j].isBankrupt()){
                        continue;
                    }
                    tempPlayersInGame[k] = players[j];
                    k++;
                }
                players = tempPlayersInGame;
                if(inGamePlayers==1){
                    endGame = true;
                    break;
                }
            }
        }
        System.out.format("Congratulation %s\n You are the winner",players[0].getName());
    }

    public static void questionAreaMethod(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Question Area");
        System.out.println("Enter '1' to see how lucky you are : ");
        int temp = scanner.nextInt();
        while(temp!=1){
            System.out.println("Wrong number!!");
            temp = scanner.nextInt();
        }
        int rand = new Random().nextInt();
        int num = Math.abs(rand)%7 +1;
        switch (num){
            case 1:
                System.out.println("You have earned $200 from Bank");
                System.out.println("+$200");
                player.inCome(200);
                break;
            case 2:
                System.out.println("You َAre in Jail now  :O");
                player.setPrisoner(true);
                player.setPosition(13);
                System.out.println("free/nothing/dice");
                jailProcess(player);
                break;
            case 3:
                System.out.println("You have to pay 10% of your Money :(");
                System.out.format("-$%d", player.getMoney()/10);
                player.fine(player.getMoney()/10);
                break;
            case 4:
                System.out.println("Position +3");
                player.move(3);
                playProcesses(player);
                break;
            case 5:
                System.out.println("You Get A Ticket For Jail!");
                player.setJailFreeTicket(player.getJailFreeTicket() + 1);
                break;
            case 6:
                System.out.println("You Get A Ticket! : If You Enter The Tax Area, You Do Not Have To Pay Money To The Bank For Once!");
        }
    }

    public static void cinema(Player player , int cinemaNumber){
        boolean mustPay = false;
        String cinemaOwned = "";
        if(cinemaNumber == 4)
            cinemaOwned = "Cinema(4)";
        else if(cinemaNumber == 8)
            cinemaOwned = "Cinema(8)";
        else if(cinemaNumber == 15)
            cinemaOwned = "Cinema(15)";
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].getCountProperties(); j++) {
                String[] temp = players[i].getProperties();
                if(temp[j]!=null){
                    if(temp[j].equals(cinemaOwned)){
                        mustPay = true;
                        if(players[i].getCinemaOwned() == 1){
                            System.out.format("You Must pay 25$ to %s\n" , players[i].getName());
                            System.out.println("-25$");
                            player.fine(25);
                            players[i].inCome(25);
                            return;
                        }
                        else if(players[i].getCinemaOwned() == 2){
                            System.out.format("You Must pay 50$ to %s\n" , players[i].getName());
                            System.out.println("-50$");
                            player.fine(50);
                            players[i].inCome(50);
                            return;
                        }
                        else if(players[i].getCinemaOwned() == 3){
                            System.out.format("You Must pay 100$ to %s\n" , players[i].getName());
                            System.out.println("-100$");
                            player.fine(100);
                            players[i].inCome(100);
                            return;
                        }
                    }
                }
            }
        }
        if(!mustPay){
            Scanner scanner = new Scanner(System.in);
            System.out.println("This Cinema Can be Your Cinema :) Do You Want ??(buy / no)");
            String input = scanner.nextLine();
            while(!input.equals("buy") && !input.equals("no")){
                System.out.println("Error : Undefined command !!");
                input = scanner.nextLine();
            }
            if(input.equals("buy")){
                System.out.println("-200$");
                player.buy(200);
                player.buyProperties(cinemaOwned);
                System.out.println("Thanks For Your Purchase");
            }
        }
    }

    private static void sortPlayerByDice(Player[] array){
        Scanner scanner = new Scanner(System.in);
        int[] diceSort = new int[array.length];
        for (int i = 0; i < array.length;) {
            int rand = new Random().nextInt();
            System.out.format("Enter 1(Dice) For Player '%s' :" , players[i].getName());
            if (scanner.nextInt() == 1) {
                diceSort[i] = (Math.abs(rand) % 5) + 1;// fill diceSort array
                System.out.println(diceSort[i]);// show player dice
                i++;
            }
            else {
                System.out.println("Invalid Number");
            }
        }
        for (int i = 0; i < array.length; i++) {// sort player array by bigger dice number
            for (int j = i; j < array.length ; j++) {
                if (diceSort[i] < diceSort[j]) {
                    Player temp = null;
                    temp = array[i];
                    array[i] = array[j];// problem
                    array[j] = temp;
                    int temp1 = 0;
                    temp1 = diceSort[i];
                    diceSort[i] = diceSort[j];
                    diceSort[j] = temp1;
                }
            }
        }
        System.out.println("Players Turn:");
        for (int i = 0; i < array.length; i++) {
            System.out.format("%d.%s\n" , i + 1 , array[i].getName());
        }
        players = array;
    }

    private static int menu(){
        System.out.println("1. Create game");
        System.out.println("2. Start game");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Choice:");
        int choice = scanner.nextInt();
        while(choice<1 || choice>2){
            System.out.println("ERROR : Not in options!!");
            choice = scanner.nextInt();
        }
        return choice;
    }

    private static void AirportMethod(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Airport , How can i help you ?? ");
        System.out.println("fly/nothing");
        String  airportChoice = scanner.nextLine();
        while (!airportChoice.equals("fly") && !airportChoice.equals("nothing")){
            System.out.println("Error : Undefined command !!");
            airportChoice = scanner.nextLine();
        }
        if(airportChoice.equals("fly")){
            System.out.println("-$50");
            player.buy(50);
            System.out.println("Where do you want to travel ??");
            int index=1;
            int[] temp = new int[2];
            if(player.getPosition()!=3){
                System.out.format("%d.Airport(3)\n", index++);
                temp[index-1] = 3;
            }
            if(player.getPosition()!=11){
                System.out.format("%d.Airport(11)\n", index++);
                temp[index-1] = 11;
            }
            if(player.getPosition()!=20){
                System.out.format("%d.Airport(20)\n", index);
                temp[index-1] = 20;
            }
            int travelChoice = scanner.nextInt();
            while(travelChoice!=1 && travelChoice!=2){
                System.out.println("Error : Out of range !!");
                travelChoice = scanner.nextInt();
            }
            player.setPosition(temp[travelChoice-1]);
        }
    }

    public static void bankAreaMethod(Player player){
        Scanner scanner = new Scanner(System.in);
        if(player.getHaveInvest()){
            player.setMoney(player.getInvestedMoney() * 2 + player.getMoney());
            System.out.format("%d$ Returned To You\n" ,player.getInvestedMoney() * 2);
            player.setInvestedMoney(0);
            player.setHaveInvest(false);
            System.out.println("Would You Like To Make A Deposit Again?(Enter: invest / Not interested)");
            if (scanner.next().equals("invest")) {
                player.setInvestedMoney(player.getMoney() / 2);
                System.out.println("Invested!");
                player.setHaveInvest(true);
            }
            else{
                System.out.println("Maybe Next Time :)");
            }
        }
        else {
            System.out.println("Would You Like To Make A Deposit ?(Enter: invest / Not interested)");
            if (scanner.next().equals("invest")) {
                player.setInvestedMoney(player.getMoney() / 2);
                System.out.println("Invested!");
                player.setHaveInvest(true);
            }
            else{
                System.out.println("Maybe Next Time :)");
            }
        }
    }

    public static void emptyPlace(Player player , Board emptyPlace , int emptyPlaceNumber){// 2 , 7 , 9 , 2 = 12 , 14 , 9 = 18 , 7 = 19 ,14 = 23
        if(emptyPlace.getIsOwned()){
            String[] tempPlayerProperties = player.getProperties();
            String emptyPlaceToString = emptyPlaceFound(emptyPlaceNumber);
            boolean hasOwned = false;
            for (int i = 0; i < player.getCountProperties(); i++) {
                if (Objects.equals(tempPlayerProperties[i], emptyPlaceToString)) {
                    hasOwned = true;
                    break;
                }
            }
            if(hasOwned){
                Scanner scanner = new Scanner(System.in);
                System.out.println("What You Want To Do With Your Own Place(Build / Not Interested)");
                if(scanner.next().equals("build")){
                    player.buildProperties(player , emptyPlace);
                }
                else{
                    System.out.println("Maybe Next Time :)");
                }
            }
            else{
                Player ownerOfProperty = foundPropertyOwner(emptyPlaceNumber);
                int fine;
                if(ownerOfProperty.isMonopoly()){
                    fine = 100;
                    if(emptyPlace.getCountHouse() > 0){
                        fine += emptyPlace.getCountHotel() * 2 * 600  + emptyPlace.getCountHouse() * 2 * 100;
                    }
                }
                else{
                    fine = 50;
                    if(emptyPlace.getCountHouse() > 0){
                        fine += emptyPlace.getCountHotel() * 600  + emptyPlace.getCountHouse() * 100;
                    }
                }
                System.out.format("You Must Pay %d$ to %s\n" , fine , ownerOfProperty.getName());
                ownerOfProperty.inCome(fine);
                player.fine(fine);
            }
        }
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.println("This Place Has No Owner!(buy / Not Interested):");
            if(scanner.next().equals("buy")){
                player.buy(100);
                emptyPlace.setOwned(true);
                player.buyProperties(emptyPlaceFound(emptyPlaceNumber));
                System.out.println("What You Want To Do With Your Own Place(build / Not Interested)");
                if(scanner.next().equals("build")){
                    player.buildProperties(player , emptyPlace);
                    emptyPlace.setColor(colorsOfEmptyplaces(emptyPlaceNumber));
                    if(player.getEmptyPlaceOwned()>1)
                        player.setMonopoly(monopolyOfProperty(player , emptyPlace));
                }
                else{
                    System.out.println("Maybe Next Time :)");
                }
            }
        }
    }

    public static String emptyPlaceFound(int emptyPlaceNumber){
        String property = "";
        switch (emptyPlaceNumber){
            case 2:
                property = "emptyPlace2";
                break;
            case 7:
                property = "emptyPlace7";
                break;
            case 9:
                property = "emptyPlace9";
                break;
            case 12:
                property = "emptyPlace12";
                break;
            case 14:
                property = "emptyPlace14";
                break;
            case 18:
                property = "emptyPlace18";
                break;
            case 19:
                property = "emptyPlace19";
                break;
            case 23:
                property = "emptyPlace23";
                break;
        }
        return property;
    }

    public static void startLoadBoard(){

        boardHouses[0] = new FreeParking();
        boardHouses[1] = new EmptyPlace();
        boardHouses[6] = new EmptyPlace();
        boardHouses[8] = new EmptyPlace();
        boardHouses[11] = new EmptyPlace();
        boardHouses[13] = new EmptyPlace();
        boardHouses[17] = new EmptyPlace();
        boardHouses[18] = new EmptyPlace();
        boardHouses[22] = new EmptyPlace();
        boardHouses[2] = new Airport();
        boardHouses[10] = new Airport();
        boardHouses[19] = new Airport();
        boardHouses[3] = new Cinema();
        boardHouses[7] = new Cinema();
        boardHouses[14] = new Cinema();
        boardHouses[5] = new AwardArea();
        boardHouses[16] = new TaxationArea();
        boardHouses[4] = new Road();
        boardHouses[9] = new Road();
        boardHouses[15] = new Road();
        boardHouses[23] = new QuestionArea();
        boardHouses[12] = new Jail();
        boardHouses[20] = new BankArea();
    }

    private static void playProcesses(Player player){
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof Airport){
            AirportMethod(player);
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof AwardArea){
            player.inCome(200);
            System.out.println("You are in the Award area");
            System.out.println("+$200");
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof BankArea){
            System.out.println("You Are In Bank Area!");
            bankAreaMethod(player);
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof Cinema){
            cinema(player , player.getPosition());
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof EmptyPlace){
            emptyPlace(player , boardHouses[player.getPosition() - 1] , player.getPosition());
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof QuestionArea){
            questionAreaMethod(player);
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof Jail){
            if(player.isPrisoner()){
                jailProcess(player);
            }else{
                System.out.println("Dont worry :)   You are not a Prisoner");
            }
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof TaxationArea){
            int temp=player.getMoney()/10;
            System.out.println("You have to pay Tax!!!");
            System.out.format("-$%d",temp);
            player.buy(temp);
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof FreeParking){
            System.out.println("You are in the Free Parking");
            return;
        }
        if(Monopoly.boardHouses[player.getPosition() - 1] instanceof Road){
            System.out.println("You are in the road and you have to pay $100 for Road tolls");
            System.out.println("-$100");
            player.buy(100);
        }
    }

    public static void jailProcess(Player player){
        Scanner scanner = new Scanner(System.in);
        String jailChoice = scanner.nextLine();
        if(player.getJailFreeTicket() > 1){
            System.out.println("You Have A Ticket To Get Free From Jail! , Do You Want To Use it?(Y / N)");
            if(scanner.next().equals("Y")) {
                player.setPrisoner(false);
                player.setJailFreeTicket(player.getJailFreeTicket() - 1);
            }
        }
        else {
            while (!jailChoice.equals("free") && !jailChoice.equals("nothing") && !jailChoice.equals("dice")) {
                System.out.println("Error : Undefined command !!");
                jailChoice = scanner.nextLine();
            }
            if (jailChoice.equals("free")) {
                player.fine(50);
                player.setPrisoner(false);
                System.out.println("-$50");
            } else if (jailChoice.equals("dice")) {
                System.out.println("Take your chance . Only dice -> 1  will be able to set you free");
                int freeDice = scanner.nextInt();
                while (freeDice < 1 || freeDice > 6) {
                    System.out.println("Error : Wrong input !!");
                    freeDice = scanner.nextInt();
                }
                if (freeDice == 1) {
                    System.out.println("You are a free man :)");
                    player.setPrisoner(false);
                } else {
                    player.fine(10);
                    System.out.println("-10$");
                }
            } else {
                player.fine(10);
                System.out.println("-10$");
            }
        }
    }

    public static boolean monopolyOfProperty(Player player , Board emptyPlace){
        boolean flag = false;
        Board[] playerEmptyPlaces = player.getEmptyPlaces();
        if(playerEmptyPlaces!=null){
            for (int i = 0; i < player.getEmptyPlaceOwned(); i++) {
                if(playerEmptyPlaces[i]!=null)
                {
                    if(playerEmptyPlaces[i].getColor().equals(emptyPlace.getColor()))
                        flag = true;
                }
            }
        }
        return flag;
    }

    public static Player foundPropertyOwner(int emptyPlaceNumber){
        Player result = null;
        String emptyPlace = emptyPlaceFound(emptyPlaceNumber);
        for (Player player : players) {
            String[] tempProperty = player.getProperties();
            if (tempProperty != null) {
                for (String s : tempProperty) {
                    if(s!=null){
                        if (s.equals(emptyPlace)) {
                            result = player;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static String colorsOfEmptyplaces(int emptyPlaceNumber){
        String color = "";
        switch (emptyPlaceNumber){
            case 2:
            case 12:
                color = "green";
                break;
            case 7:
            case 19:
                color = "yellow";
                break;
            case 9:
            case 18:
                color = "red";
                break;
            case 14:
            case 23:
                color = "blue";
                break;
        }
        return color;
    }

    public static void showBoard(){
        System.out.println("╔═══╦════╦═══╦════╦════╦═════╦════╗     1.Free Parking    16.Road");
        System.out.println("║ 7 ║  8 ║ 9 ║ 10 ║ 11 ║ 12  ║ 13 ║     2.Empty Area      17.Tax");
        System.out.println("╠═══╬════╩═══╩════╩════╩═════╬════╣     3.Airport         18.Empty Area");
        System.out.println("║ 6 ║                        ║ 14 ║     4.Cinema          19.Empty Area");
        System.out.println("╠═══╣                        ╠════╣     5.Road            20.Airport");
        System.out.println("║ 5 ║                        ║ 15 ║     6.Award Area      21.Bank");
        System.out.println("╠═══╣                        ╠════╣     7.Empty Area      22.Cinema");
        System.out.println("║ 4 ║      $ MONOPOLY $      ║ 16 ║     8.Cinema          23.Empty Area");
        System.out.println("╠═══╣                        ╠════╣     9.Empty Area      24.Question Area");
        System.out.println("║ 3 ║                        ║ 17 ║     10.Road");
        System.out.println("╠═══╣                        ╠════╣     11.Airport");
        System.out.println("║ 2 ║                        ║ 18 ║     12.Empty Area");
        System.out.println("╠═══╬════╦════╦════╦════╦════╬════╣     13.Jail");
        System.out.println("║ 1 ║ 24 ║ 23 ║ 22 ║ 21 ║ 20 ║ 19 ║     14.Empty Area");
        System.out.println("╚═══╩════╩════╩════╩════╩════╩════╝     15.Cinema");
        System.out.println("Commands : ");
        System.out.println("sell    index    property    rank   dice");
    }
}

