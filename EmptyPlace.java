public class EmptyPlace extends Board {
    private int price = 100;
    private int countHouse = 0;
    private int countHotel = 0;
    private String name;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void delHouses() {
        houses = new String[0];
        countHouse = 0;
    }

    @Override
    public void setCountHotel(int countHotel) {
        this.countHotel = countHotel;
    }

    @Override
    public int getCountHotel() {
        return countHotel;
    }

    @Override
    public int getCountHouse() {
        return countHouse;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCountHouse(int countHouse) {
        this.countHouse = countHouse;
    }
}
