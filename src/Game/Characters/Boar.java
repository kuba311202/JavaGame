package Game.Characters;

import javax.swing.*;

public class Boar extends Enemy {
    public Boar() {
        this.name = "Boar";
        this.maxHp = 40;
        this.hp = 40;
        this.mana = 0;
        this.attack = 10;
        this.armor = 5;
        this.Graphic = new JLabel();
        this.Graphic.setIcon(new ImageIcon("../Gra/images/Boar.png"));
    }
    public void TakeAction(int turnCounter, Character Target, Enemy Caster){
        if(turnCounter%2==1){
            Target.SetHp(-Caster.attack+0.5*Target.armor);
        }else if(turnCounter%2==0){

        }

    }
}
