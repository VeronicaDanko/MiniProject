public class Food extends Position{
    private char food;

    public Food(int x, int y, char food) {
        super(x, y);
        this.food = food;
    }

    public char getFood() {
        return food;
    }
}
