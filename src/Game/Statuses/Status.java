package Game.Statuses;

import Game.Characters.Character;

import javax.swing.*;

public class Status {
    protected int amount;
    protected String type;
    protected JLabel graphic;
    public Status(int Amount){
        this.amount = this.amount + Amount;
    }
    public void Damage(Character Afflicted){
        Afflicted.SetHp(-this.amount);
    }
}
