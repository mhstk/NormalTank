public abstract class Moving extends Runner {
    protected int speed;

    public Moving(String bulletImageAddress) {
        super(bulletImageAddress);
    }

    public abstract void move();
}
