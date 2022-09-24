import org.junit.Assert;
import org.junit.Test;

public class TokenizerTester {
    @Test
    public void testStringConstructor() {
        Tokenizer t1 = new Tokenizer("C:\\Users\\emile\\Desktop\\test1.txt");
        Assert.assertEquals("[test]", t1.wordList().toString());
        Tokenizer t2 = new Tokenizer("C:\\Users\\emile\\Desktop\\test2.txt");
        Assert.assertEquals("[]", t2.wordList().toString());
        Tokenizer t3 = new Tokenizer("C:\\Users\\emile\\Desktop\\test3.txt");
        Assert.assertEquals("[35, ultra, space, usp, cries, from, distant, worlds, reverberate, type, modifiers, " +
                            "dark, moves, increase, in, power, x15, ability, modifiers, pokemon, with, the, following, " +
                            "abilities, randomly, become, a, different, type, at, the, end, of, each, turn, multitype, " +
                            "rks, system, victory, star, additionally, boosts, user, and, allys, attack, and, sp, atk, " +
                            "x15, shadow, shield, additionally, doubles, the, pokemons, defense, and, sp, def, the, following, " +
                            "abilities, are, disabled, magnet, pull, flame, body, plus, minus, blaze, download, forewarn, " +
                            "solid, rock, parental, bond, symbiosis, power, construct, earthen, aura, power, spot, earthinize, " +
                            "cultivate, neutralizing, gas, air, lock, additionally, raises, speed, upon, entry, mold, breaker, " +
                            "additionally, raises, sp, atk, upon, entry, the, statchanging, effects, of, the, following, " +
                            "abilities, are, amplified, by, 1, stage, beast, boost, supernova, shields, down, additionally, "+
                            "boosts, attack, and, sp, atk, when, hp, falls, below, half, move, modifiers, the, following, " +
                            "moves, increase, in, power, x15, psystrike, aeroblast, sacred, fire, mist, ball, origin, pulse, " +
                            "precipice, blades, dragon, ascent, psycho, boost, roar, of, time, shadow, force, seed, flare, " +
                            "judgment, vcreate, sacred, sword, secret, sword, fusion, bolt, fusion, flare, bolt, strike, " +
                            "blue, flare, glaciate, ice, burn, freeze, shock, relic, song, oblivion, wing, thousand, arrows, " +
                            "thousand, waves, earth, power, fleur, cannon, prismatic, laser, sunsteel, strike, spectral, " +
                            "thief, moongeist, beam, multiattack, continental, crush, core, enforcer, genesis, supernova, " +
                            "searing, sunraze, smash, draco, meteor, ancient, power, menacing, moonraze, maelstrom, meteor, " +
                            "mash, comet, punch, lands, wrath, diamond, storm, soulstealing, 7star, strike, swift, energy, " +
                            "ball, photon, geyser, splintered, stormshards, light, that, burns, the, sky, meteor, beam, " +
                            "meteor, tempest, infinite, force, the, following, moves, increase, in, power, x12, mirror, " +
                            "shot, aurora, beam, signal, beam, dazzling, gleam, flash, cannon, moonblast, photon, geyser, " +
                            "luster, purge, the, following, moves, increase, in, power, x2, vacuum, wave, spacial, rend, " +
                            "hyperspace, hole, hyperspace, fury, shattered, psyche, heart, swap, additionally, gains, pain, " +
                            "splits, effect, the, base, accuracy, of, the, following, moves, is, increased, to, 100, dark, " +
                            "void, lunar, dance, additionally, boosts, all, of, the, recipients, stats, trick, room, magic, " +
                            "room, and, wonder, room, instead, last, for, 8, turns, the, statchanging, effects, of, the, " +
                            "following, moves, are, amplified, by, 1, stage, cosmic, power, flash, calm, mind, moonlight, " +
                            "instead, heals, 23, max, hp, black, hole, eclipse, increases, in, power, x4, nature, power, " +
                            "becomes, spacial, rend, camouflage, changes, the, users, type, to, a, random, type, secret, " +
                            "power, may, lower, the, targets, sp, def, terrain, pulse, increases, in, power, x2, and, becomes, " +
                            "a, random, type, the, following, moves, fail, upon, use, earthquake, fissure, dig, poison, " +
                            "gas, smokescreen, hidden, power, instead, becomes, a, random, type, vacuum, wave, becomes, " +
                            "darktype, energy, ball, becomes, psychictype, light, that, burns, the, sky, decreases, in, " +
                            "power, x05, item, modifiers, magical, seed, boosts, defense, and, sp, def, and, creates, wonder, " +
                            "room, the, following, items, instead, boost, the, power, of, affected, moves, x15, adamant, " +
                            "orb, griseous, orb, lustrous, orb, field, transformation, the, following, moves, transform, " +
                            "the, field, into, a, starlight, arena, geomancy, the, following, moves, transform, the, field, " +
                            "into, a, random, field, temporarily, hyperspace, hole, teleport, other, effects, prevents, all, " +
                            "weather, besides, strong, winds, and, generated, field, effects, pokemon, no, longer, gain, " +
                            "an, extra, damage, boost, for, using, moves, that, share, a, type, with, them, wind, moves, " +
                            "and, sound, moves, fail, upon, use]", t3.wordList().toString());
        Tokenizer t4 = new Tokenizer("C:\\Users\\emile\\Desktop\\test4.txt");
        Assert.assertEquals("[]", t4.wordList().toString());
    }

    @Test
    public void testArrayConstructor() {
        Tokenizer t1 = new Tokenizer(new String[]{"5I$^*"});
        Assert.assertEquals("[5i]", t1.wordList().toString());
        Tokenizer t2 = new Tokenizer(new String[]{});
        Assert.assertEquals("[]", t2.wordList().toString());
        Tokenizer t3 = new Tokenizer(new String[]{":::ITEM MODIFIERS:::"});
        Assert.assertEquals("[item, modifiers]", t3.wordList().toString());
        Tokenizer t4 = new Tokenizer(new String[]{":::ITEM", "MODIFIERS:::"});
        Assert.assertEquals("[item, modifiers]", t4.wordList().toString());
        Tokenizer t5 = new Tokenizer(new String[]{":::ITEM", "MODIFIERS:::", "mod", ";;;;:#*(@#&$", "352;;1"});
        Assert.assertEquals("[item, modifiers, mod, 3521]", t5.wordList().toString());
    }
}