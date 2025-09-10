package Game.Spells;
import Game.Characters.Character;

public class Spell {
    protected String name;
    protected int manaCost;
    protected Character target;
    protected int amount;
    public String type;
    protected Character caster;

    public void Use(Character Target, Character Caster){

    }

    public String GetName(){return this.name;}

    public void Damage(Character Target, Character Caster) {
        Caster.SetMana(-this.manaCost);
        Target.SetHp(-this.amount);
    }

    public void Heal(Character Target, Character Caster) {
        Caster.SetMana(-this.manaCost);
        Target.SetHp(this.amount);
    }
}