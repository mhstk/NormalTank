public abstract class Moving extends Runner {
    protected int speed;
    protected double angelBody;
    public Moving(String bulletImageAddress) {
        super(bulletImageAddress);
    }

    public abstract void move();
}
