package Game.Spells;
import Game.Characters.Character;


public class Fireball extends Spell{
    public Fireball(){
        name="Fireball";
        manaCost=10;
        amount=10;
        type="Damage";
    }
    @Override
    public void Damage(Character Target,  Character Caster) {
        super.Damage(Target, Caster);
    }
}


