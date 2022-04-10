public class Board{

    public String[] houses;
    private boolean isOwned = false;
    private int countHouse = 0;
    private int countHotel = 0;
    private String color;
    private int number;


    private String name;

    public String getName() {
        return name;
    }

    public boolean getIsOwned() {
        return this.isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public void setCountHouse(int countHouse){
        this.countHouse = countHouse;
    }

    public void setName(String emptyPlace){
        this.name = name;
    }

    public int getCountHouse(){return countHouse;}

    public void delHouses(){
        houses = new String[0];
        countHouse = 0;
    };

    public void setCountHotel(int countHotel){
        this.countHouse = countHotel;
    }

    public int getCountHotel(){return countHotel;}

    public String getColor(){return color;}

    public void setColor(String color){
        this.color = color;
    };

}
/* ╔═══╦════╦═══╦════╦════╦═════╦════╗
   ║ 7 ║  8 ║ 9 ║ 10 ║ 11 ║ 12  ║ 13 ║
   ╠═══╬════╩═══╩════╩════╩═════╬════╣
   ║ 6 ║                        ║ 14 ║
   ╠═══╣                        ╠════╣
   ║ 5 ║                        ║ 15 ║
   ╠═══╣                        ╠════╣
   ║ 4 ║      $ MONOPOLY $      ║ 16 ║
   ╠═══╣                        ╠════╣
   ║ 3 ║                        ║ 17 ║
   ╠═══╣                        ╠════╣
   ║ 2 ║                        ║ 18 ║
   ╠═══╬════╦════╦════╦════╦════╬════╣
   ║ 1 ║ 24 ║ 23 ║ 22 ║ 21 ║ 20 ║ 19 ║
   ╚═══╩════╩════╩════╩════╩════╩════╝

 */
