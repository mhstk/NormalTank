import java.util.ArrayList;

public class CheatCode {
    private String test = "";
    private ArrayList<String> cheatCodes;
    private PlayerTank playerTank ;

    public CheatCode(PlayerTank playerTank){
        this.playerTank = playerTank;
        cheatCodes = new ArrayList<>();
        addCheatCodes();
    }

    private void addCheatCodes() {
        cheatCodes.add("health");
        cheatCodes.add("gun");
        cheatCodes.add("speed");
    }

    public void addChar(String string){
        System.out.println("String @@@@@@@@@@@@@@@ "+string);
        test += string;
        System.out.println("TEst ############## " + test);
        switch (check()){
            case 0:
                playerTank.refactor();
                test = "";
                break;
            case 1:
                playerTank.changeNumberOfMashinGun(300);
                playerTank.changeNumberOfTankGun(300);
                test = "";
                break;
            case 2:
                playerTank.speed+=2;
                test = "";
                break;
            case 3:
                break;
            case 4:
                test = "";
                break;
        }
    }

    private int check() {
        for (int i = 0; i < cheatCodes.size(); i++) {
            if (test.equals(cheatCodes.get(i)))
                return i;
        }
        for (int i = 0; i < cheatCodes.size(); i++) {
            if (cheatCodes.get(i).startsWith(test))
                return 3;
        }
        return 4;
    }

}
