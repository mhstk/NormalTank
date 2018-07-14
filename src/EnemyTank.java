public class EnemyTank extends SuperTank {
    private long timeLastShotGun = 0;

    public EnemyTank(int positionX, int positionY) {
        super("Tank-under.png", "Tank-under2.png", "Tank-top.png", "Tank-Bullet.png", positionX, positionY);
        speed = 5;
    }

    public void isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) > 400 && Math.sqrt(distance) < 1000) move();
        else if (Math.sqrt(distance) <= 400) changePosition();
    }

    private void changePosition() {
        angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        shoot();
    }


    public void move() {
        angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        if (GameState.tankPosition().x > positionX) {
            positionX += speed;
            clientLoc.x += speed;
            right = true;
            left = false;
            if (Collision.CollisionEnemyTank(this)) {
                right = false;
                left = true;
                positionX -= speed;
                clientLoc.x -= speed;
            }
            if (positionX > GameState.tankPosition().x) positionX = GameState.tankPosition().x;
            moveRight();
        } else if (GameState.tankPosition().x < positionX) {
            positionX -= speed;
            clientLoc.x -= speed;
            left = true;
            right = false;
            if (Collision.CollisionEnemyTank(this)) {
                right = true;
                left = false;
                positionX += speed;
                clientLoc.x += speed;
            }
            if (positionX < GameState.tankPosition().x) positionX = GameState.tankPosition().x;
            moveLeft();
        } else {
            right = false;
            left = false;
        }
        if (GameState.tankPosition().y > positionY) {
            positionY += speed;
            clientLoc.y += speed;
            down = true;
            up = false;
            if (Collision.CollisionEnemyTank(this)) {
                down = false;
                up = true;
                positionY -= speed;
                clientLoc.y -= speed;
            }
            if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
            moveDown();

        } else if (GameState.tankPosition().y < positionY) {
            positionY -= speed;
            clientLoc.y -= speed;
            if (Collision.CollisionEnemyTank(this)) {
                down = true;
                up = false;
                positionY += speed;
                clientLoc.y += speed;
            }
            if (positionY < GameState.tankPosition().y) positionY = GameState.tankPosition().y;
            up = true;
            down = false;
            moveUp();
        } else {
            up = false;
            down = false;
        }
        //changeBodyImage();
        shoot();
    }

    public void shoot() {
        Long now = System.nanoTime();
        if ((now - timeLastShotGun) / 1000000000.0 > 2.7) {
            Bullet bullet;
            if (isFirstImage) {
                bullet = new Bullet(getGunX(), getGunY(), GameState.tankPosition().x + (205 / 2), GameState.tankPosition().y + (160 / 2), 0, 20);
            } else {
                bullet = new Bullet(getGunX(), getGunY(), GameState.tankPosition().x + (205 / 2), GameState.tankPosition().y + (160 / 2), 1, 20);
            }
            bullets.add(bullet);
            Sound sound = new Sound("heavygun.wav", 0);
            sound.execute();
            timeLastShotGun = now;
        }
    }


}