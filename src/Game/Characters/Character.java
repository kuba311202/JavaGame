package Game.Characters;
import Game.Spells.Spell;

import java.util.Arrays;

public class Character {
    protected String name;
    protected double maxHp;
    protected double hp;
    protected double mana;
    protected double attack;
    protected double armor;
    public boolean defend;
    public Spell[] spellsLibrary;
    public boolean dead;

    public Character(){

    }

    public double GetMaxHP(){
        return this.maxHp;
    }
    public double GetHP(){
        return this.hp;
    }
    public double GetMana(){
        return this.mana;
    }
    public double GetAttack(){
        return this.attack;
    }
    public double GetArmor(){
        return this.armor;
    }
    public String GetName(){return this.name;}
    public String GetSpellName(int i){return this.spellsLibrary[i].GetName();}


    public void SetMaxHp(double newMaxHp){
        this.maxHp = this.maxHp + Math.ceil(newMaxHp);
    }
    public void SetHp(double newHp){
        if(this.hp + newHp>maxHp){
        this.hp=this.maxHp;
        }else {
            this.hp = this.hp + Math.ceil(newHp);
        }
    }
    public void SetMana(double newMana){
        this.mana = this.mana + Math.ceil(newMana);
    }
    public void SetAttack(double newAttack){
        this.attack = this.attack + Math.ceil(newAttack);
    }
    public void SetArmor(double newArmor){
        this.armor = this.armor + Math.ceil(newArmor);
    }
    public void SetDefend(boolean newDefend){
        this.defend = newDefend;

    }

    public void AddSpell(Spell spellToAdd){
        int i = 0;
        while(i<this.spellsLibrary.length) {
            if (this.spellsLibrary[i] == null || this.spellsLibrary[i].GetName() =="Empty") {
                this.spellsLibrary[i] = spellToAdd;
                break;
            }else{
                i++;
            }
        }
    }

    public int CountSpells(){
        int i = 0;
        int count=0;
        while(i<this.spellsLibrary.length) {
            if (this.spellsLibrary[i].GetName()!="Empty") {
                i++;
            }else{
                count++;
            }
        }
        return count;
    }

    public void AttackAction(Character Attacker, Character Defender){
        Defender.SetHp(-Attacker.attack+(0.5*Defender.armor));
    }


}


