package Game.Characters;

import javax.swing.*;

public class EmptyEnemy extends Enemy{
    public EmptyEnemy(){
        this.name="EmptyEnemy";
        this.maxHp=0;
        this.hp=0;
        this.mana=0;
        this.attack=0;
        this.armor=0;
        this.Graphic = new JLabel();
        this.Graphic.setIcon(new ImageIcon("../Gra/images/EmptyEnemy.png"));
    }
}
