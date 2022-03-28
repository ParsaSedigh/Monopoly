public abstract class Board{

    public String[] houses;
    private boolean isOwned = false;


    public boolean getIsOwned() {
        return this.isOwned;
    }

    public abstract void setCountHouse(int countHouse);

    public abstract void setName(String emptyPlace);

    public abstract int getCountHouse();

    public abstract void delHouses();

    public abstract void setCountHotel(int countHotel);

    public abstract int getCountHotel();

    public abstract String getColor();

    public abstract void setColor(String color);
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
