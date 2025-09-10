package Game.Encounters;

import Game.Characters.Boar;
import Game.Characters.EmptyEnemy;
import Game.Characters.Enemy;

import javax.swing.*;
import java.util.Objects;

public class FightEnemy {
    public Enemy slot1;
    public Enemy slot2;
    public Enemy slot3;
    protected JLabel slot1Graphic;
    protected JLabel slot2Graphic;
    protected JLabel slot3Graphic;

    public JLabel getGraphic(JLabel Graphic) {
        return Graphic;
    }

    public FightEnemy(String fightNumber){
       if(Objects.equals(fightNumber, "1")){
           Boar boar = new Boar();
           EmptyEnemy emptyEnemy = new EmptyEnemy();
           this.slot1 = boar;
           this.slot1Graphic=slot1.Graphic;
           this.slot2 = emptyEnemy;
           this.slot2Graphic=slot2.Graphic;
           this.slot3 = emptyEnemy;
           this.slot3Graphic=slot3.Graphic;
       }
        else if(Objects.equals(fightNumber, "2")){
            Boar boar1 = new Boar();
            Boar boar2 = new Boar();
            Boar boar3 = new Boar();
            this.slot1 = boar1;
            this.slot1Graphic=slot1.Graphic;
            this.slot2 = boar2;
            this.slot2Graphic=slot2.Graphic;
            this.slot3 = boar3;
            this.slot3Graphic=slot3.Graphic;
        }

    }

}
