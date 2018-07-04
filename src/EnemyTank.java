public class EnemyTank extends Tank {

    public EnemyTank(int positionX, int PositionY , String imageFileBody, String imageFileGun,String bulletImageAddress) {
        super(imageFileBody ,imageFileGun ,bulletImageAddress);
        this.positionX = positionX;
        this.positionY = positionY;
        speed = 8;
    }

    public void move(){

    }
}