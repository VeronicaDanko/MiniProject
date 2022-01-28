public class Food extends Position{
    private char food;
    final char heart = '\u2661';

    public Food(int x, int y, char food, char heart) {
        super(x, y);
        this.food = food;
        this.heart = heart;
    }

    public char getFood() {
        return food;
    }

    public char getHeart() {return heart;}

    public void
}