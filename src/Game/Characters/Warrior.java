package Game.Characters;

import Game.Spells.Spell;

public class Warrior extends Character{
    public Warrior(){
        this.name = name;
        this.maxHp = 50;
        this.hp=50;
        this.mana=20;
        this.attack=15;
        this.armor=10;
        this.spellsLibrary=new Spell[10];
    }
}
