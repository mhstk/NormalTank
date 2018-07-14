import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadImage {

    static BufferedImage tankUnder1 ;
    static BufferedImage tankUnder2 ;
    static BufferedImage tankUnder3 ;
    static BufferedImage tankUnder4 ;
    static BufferedImage tankTop1 ;
    static BufferedImage tankTop2 ;
    static BufferedImage tankTop3 ;
    static BufferedImage tankTop4 ;
    static BufferedImage bullet1 ;
    static BufferedImage bullet2 ;
    static BufferedImage softWall0 ;
    static BufferedImage softWall1 ;
    static BufferedImage softWall2 ;
    static BufferedImage softWall3 ;
    static BufferedImage hardWall ;
    static BufferedImage area ;
    static BufferedImage plant ;
    static BufferedImage idiotEnemy1 ;
    static BufferedImage idiotEnemy2 ;
    static BufferedImage teazel ;
    static BufferedImage cursor ;
    static BufferedImage turretGun;
    static BufferedImage turretBody;
    static BufferedImage onMine;
    static BufferedImage offMine;
    static BufferedImage star;
    static BufferedImage mashinGun;
    static BufferedImage tankGun;
    static BufferedImage repair;
    static BufferedImage blank;

    public static void LoadImage() throws IOException {
        tankUnder1 = ImageIO.read(new File("Tank-under.png")) ;
        tankUnder2 = ImageIO.read(new File("Tank-under2.png")) ;
        tankUnder3 = ImageIO.read(new File("Tank-under3.png")) ;
        tankUnder4 = ImageIO.read(new File("Tank-under4.png")) ;
        tankTop1 = ImageIO.read(new File("Tank-top.png")) ;
        tankTop2 = ImageIO.read(new File("Tank-top2.png")) ;
        tankTop3 = ImageIO.read(new File("Tank-top4.png")) ;
        tankTop4 = ImageIO.read(new File("Tank-top3.png")) ;
        bullet1 = ImageIO.read(new File("Tank-Bullet.png")) ;
        bullet2 = ImageIO.read(new File("Tank-Bullet3.png")) ;
        softWall0 = ImageIO.read(new File("softWall0.png")) ;
        softWall1 = ImageIO.read(new File("softWall1.png")) ;
        softWall2 = ImageIO.read(new File("softWall2.png")) ;
        softWall3 = ImageIO.read(new File("softWall3.png")) ;
        hardWall = ImageIO.read(new File("hardWall.png")) ;
        area = ImageIO.read(new File("Area.png")) ;
        plant = ImageIO.read(new File("plant.png")) ;
        idiotEnemy1 = ImageIO.read(new File("idiotEnemy1.png")) ;
        idiotEnemy2 = ImageIO.read(new File("idiotEnemy2.png")) ;
        teazel = ImageIO.read(new File("sim.png")) ;
        cursor = ImageIO.read(new File("pointer2.png")) ;
        turretGun = ImageIO.read(new File("turret3.png"));
        turretBody = ImageIO.read(new File("Tank-under3.png"));
        onMine = ImageIO.read(new File("onMine.png"));
        offMine = ImageIO.read(new File("offMine.png"));
        star =ImageIO.read(new File("star.png"));
        tankGun = ImageIO.read(new File("star.png"));
        mashinGun = ImageIO.read(new File("star.png"));
        repair = ImageIO.read(new File("star.png"));
        blank = ImageIO.read(new File("blankCursor.png"));
    }
}
