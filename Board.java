public class Board{

    public String[] houses;
    private boolean isOwned = false;


    public boolean getIsOwned() {
        return this.isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public void setCountHouse(int countHouse){};

    public void setName(String emptyPlace){};

    public int getCountHouse(){return 0;}

    public void delHouses(){};

    public void setCountHotel(int countHotel){};

    public int getCountHotel(){return 0;}

    public String getColor(){return null;}

    public void setColor(String color){};
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
