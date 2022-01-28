public class Food extends Position{
    final char heart = '\u2665';

    public Food(int x, int y) {
        super(x, y);
    }

    public char getHeart() {return heart;}
}