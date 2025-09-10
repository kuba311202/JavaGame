package Game.Spells;

import Game.Characters.Character;

public class Lesser_heal extends Spell {
    public Lesser_heal(){
        name="Lesser Heal";
        manaCost=15;
        amount=15;
        type="Heal";
    }
    @Override
    public void Heal(Game.Characters.Character Target, Character Caster) {
        super.Heal(Target, Caster);
    }
}
