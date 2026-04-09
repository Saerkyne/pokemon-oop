package pokemonGame.moves;

import pokemonGame.model.Move;
import pokemonGame.model.MoveSlot;
import pokemonGame.core.TypeChart.Type;

/**
 * Enum of all moves in the game, with associated data. Each enum constant represents a unique move,
 * and holds a reference to a {@link Move} object that contains the move's immutable data (name, type, power,
 * accuracy, PP, etc.). This allows for easy reference to moves throughout the codebase while keeping
 * move data organized and consistent.
 * 
 * @see Move
 * @see MoveSlot
 */

public enum PokeMove {
    ABSORB("Absorb", Type.GRASS, Absorb.INSTANCE),
    ACID("Acid", Type.POISON, Acid.INSTANCE),
    ACID_ARMOR("Acid Armor", Type.POISON, AcidArmor.INSTANCE),
    AGILITY("Agility", Type.PSYCHIC, Agility.INSTANCE),
    AMNESIA("Amnesia", Type.PSYCHIC, Amnesia.INSTANCE),
    AURORA_BEAM("Aurora Beam", Type.ICE, AuroraBeam.INSTANCE),
    BARRAGE("Barrage", Type.NORMAL, Barrage.INSTANCE),
    BARRIER("Barrier", Type.PSYCHIC, Barrier.INSTANCE),
    BIDE("Bide", Type.NORMAL, Bide.INSTANCE),
    BIND("Bind", Type.NORMAL, Bind.INSTANCE),
    BITE("Bite", Type.DARK, Bite.INSTANCE),
    BLIZZARD("Blizzard", Type.ICE, Blizzard.INSTANCE),
    BODY_SLAM("Body Slam", Type.NORMAL, BodySlam.INSTANCE),
    BONE_CLUB("Bone Club", Type.GROUND, BoneClub.INSTANCE),
    BONEMERANG("Bonemerang", Type.GROUND, Bonemerang.INSTANCE),
    BUBBLE("Bubble", Type.WATER, Bubble.INSTANCE),
    BUBBLE_BEAM("Bubble Beam", Type.WATER, BubbleBeam.INSTANCE),
    CLAMP("Clamp", Type.WATER, Clamp.INSTANCE),
    COMET_PUNCH("Comet Punch", Type.NORMAL, CometPunch.INSTANCE),
    CONFUSE_RAY("Confuse Ray", Type.GHOST, ConfuseRay.INSTANCE),
    CONFUSION("Confusion", Type.PSYCHIC, Confusion.INSTANCE),
    CONSTRICT("Constrict", Type.NORMAL, Constrict.INSTANCE),
    CONVERSION("Conversion", Type.NORMAL, Conversion.INSTANCE),
    COUNTER("Counter", Type.FIGHTING, Counter.INSTANCE),
    CRABHAMMER("Crabhammer", Type.WATER, Crabhammer.INSTANCE),
    CUT("Cut", Type.NORMAL, Cut.INSTANCE),
    DEFENSE_CURL("Defense Curl", Type.NORMAL, DefenseCurl.INSTANCE),
    DIG("Dig", Type.GROUND, Dig.INSTANCE),
    DISABLE("Disable", Type.NORMAL, Disable.INSTANCE),
    DIZZY_PUNCH("Dizzy Punch", Type.NORMAL, DizzyPunch.INSTANCE),
    DOUBLE_EDGE("Double-Edge", Type.NORMAL, DoubleEdge.INSTANCE),
    DOUBLE_KICK("Double Kick", Type.FIGHTING, DoubleKick.INSTANCE),
    DOUBLE_SLAP("Double Slap", Type.NORMAL, DoubleSlap.INSTANCE),
    DOUBLE_TEAM("Double Team", Type.NORMAL, DoubleTeam.INSTANCE),
    DRAGON_RAGE("Dragon Rage", Type.DRAGON, DragonRage.INSTANCE),
    DREAM_EATER("Dream Eater", Type.PSYCHIC, DreamEater.INSTANCE),
    DRILL_PECK("Drill Peck", Type.FLYING, DrillPeck.INSTANCE),
    EARTHQUAKE("Earthquake", Type.GROUND, Earthquake.INSTANCE),
    EGG_BOMB("Egg Bomb", Type.NORMAL, EggBomb.INSTANCE),
    EMBER("Ember", Type.FIRE, Ember.INSTANCE),
    EXPLOSION("Explosion", Type.NORMAL, Explosion.INSTANCE),
    FIRE_BLAST("Fire Blast", Type.FIRE, FireBlast.INSTANCE),
    FIRE_PUNCH("Fire Punch", Type.FIRE, FirePunch.INSTANCE),
    FIRE_SPIN("Fire Spin", Type.FIRE, FireSpin.INSTANCE),
    FISSURE("Fissure", Type.GROUND, Fissure.INSTANCE),
    FLAMETHROWER("Flamethrower", Type.FIRE, Flamethrower.INSTANCE),
    FLASH("Flash", Type.NORMAL, Flash.INSTANCE),
    FLY("Fly", Type.FLYING, Fly.INSTANCE),
    FOCUS_ENERGY("Focus Energy", Type.NORMAL, FocusEnergy.INSTANCE),
    FURY_ATTACK("Fury Attack", Type.NORMAL, FuryAttack.INSTANCE),
    FURY_SWIPES("Fury Swipes", Type.NORMAL, FurySwipes.INSTANCE),
    GLARE("Glare", Type.NORMAL, Glare.INSTANCE),
    GROWL("Growl", Type.NORMAL, Growl.INSTANCE),
    GROWTH("Growth", Type.NORMAL, Growth.INSTANCE),
    GUILLOTINE("Guillotine", Type.NORMAL, Guillotine.INSTANCE),
    GUST("Gust", Type.FLYING, Gust.INSTANCE),
    HARDEN("Harden", Type.NORMAL, Harden.INSTANCE),
    HAZE("Haze", Type.ICE, Haze.INSTANCE),
    HEADBUTT("Headbutt", Type.NORMAL, Headbutt.INSTANCE),
    HIGH_JUMP_KICK("High Jump Kick", Type.FIGHTING, HighJumpKick.INSTANCE),
    HORN_ATTACK("Horn Attack", Type.NORMAL, HornAttack.INSTANCE),
    HORN_DRILL("Horn Drill", Type.NORMAL, HornDrill.INSTANCE),
    HYDRO_PUMP("Hydro Pump", Type.WATER, HydroPump.INSTANCE),
    HYPER_BEAM("Hyper Beam", Type.NORMAL, HyperBeam.INSTANCE),
    HYPER_FANG("Hyper Fang", Type.NORMAL, HyperFang.INSTANCE),
    HYPNOSIS("Hypnosis", Type.PSYCHIC, Hypnosis.INSTANCE),
    ICE_BEAM("Ice Beam", Type.ICE, IceBeam.INSTANCE),
    ICE_PUNCH("Ice Punch", Type.ICE, IcePunch.INSTANCE),
    JUMP_KICK("Jump Kick", Type.FIGHTING, JumpKick.INSTANCE),
    KARATE_CHOP("Karate Chop", Type.FIGHTING, KarateChop.INSTANCE),
    KINESIS("Kinesis", Type.PSYCHIC, Kinesis.INSTANCE),
    LEECH_LIFE("Leech Life", Type.BUG, LeechLife.INSTANCE),
    LEECH_SEED("Leech Seed", Type.GRASS, LeechSeed.INSTANCE),
    LEER("Leer", Type.NORMAL, Leer.INSTANCE),
    LICK("Lick", Type.GHOST, Lick.INSTANCE),
    LIGHT_SCREEN("Light Screen", Type.PSYCHIC, LightScreen.INSTANCE),
    LOVELY_KISS("Lovely Kiss", Type.NORMAL, LovelyKiss.INSTANCE),
    LOW_KICK("Low Kick", Type.FIGHTING, LowKick.INSTANCE),
    MEDITATE("Meditate", Type.PSYCHIC, Meditate.INSTANCE),
    MEGA_DRAIN("Mega Drain", Type.GRASS, MegaDrain.INSTANCE),
    MEGA_KICK("Mega Kick", Type.NORMAL, MegaKick.INSTANCE),
    MEGA_PUNCH("Mega Punch", Type.NORMAL, MegaPunch.INSTANCE),
    METRONOME("Metronome", Type.NORMAL, Metronome.INSTANCE),
    MIMIC("Mimic", Type.NORMAL, Mimic.INSTANCE),
    MINIMIZE("Minimize", Type.NORMAL, Minimize.INSTANCE),
    MIRROR_MOVE("Mirror Move", Type.FLYING, MirrorMove.INSTANCE),
    MIST("Mist", Type.ICE, Mist.INSTANCE),
    NIGHT_SHADE("Night Shade", Type.GHOST, NightShade.INSTANCE),
    PAY_DAY("Pay Day", Type.NORMAL, PayDay.INSTANCE),
    PECK("Peck", Type.FLYING, Peck.INSTANCE),
    PETAL_DANCE("Petal Dance", Type.GRASS, PetalDance.INSTANCE),
    PIN_MISSILE("Pin Missile", Type.BUG, PinMissile.INSTANCE),
    POISON_GAS("Poison Gas", Type.POISON, PoisonGas.INSTANCE),
    POISON_POWDER("Poison Powder", Type.POISON, PoisonPowder.INSTANCE),
    POISON_STING("Poison Sting", Type.POISON, PoisonSting.INSTANCE),
    POUND("Pound", Type.NORMAL, Pound.INSTANCE),
    PSYBEAM("Psybeam", Type.PSYCHIC, Psybeam.INSTANCE),
    PSYCHIC("Psychic", Type.PSYCHIC, Psychic.INSTANCE),
    PSYWAVE("Psywave", Type.PSYCHIC, Psywave.INSTANCE),
    QUICK_ATTACK("Quick Attack", Type.NORMAL, QuickAttack.INSTANCE),
    RAGE("Rage", Type.NORMAL, Rage.INSTANCE),
    RAZOR_LEAF("Razor Leaf", Type.GRASS, RazorLeaf.INSTANCE),
    RAZOR_WIND("Razor Wind", Type.NORMAL, RazorWind.INSTANCE),
    RECOVER("Recover", Type.NORMAL, Recover.INSTANCE),
    REFLECT("Reflect", Type.PSYCHIC, Reflect.INSTANCE),
    REST("Rest", Type.PSYCHIC, Rest.INSTANCE),
    ROAR("Roar", Type.NORMAL, Roar.INSTANCE),
    ROCK_SLIDE("Rock Slide", Type.ROCK, RockSlide.INSTANCE),
    ROCK_THROW("Rock Throw", Type.ROCK, RockThrow.INSTANCE),
    ROLLING_KICK("Rolling Kick", Type.FIGHTING, RollingKick.INSTANCE),
    SAND_ATTACK("Sand Attack", Type.GROUND, SandAttack.INSTANCE),
    SCRATCH("Scratch", Type.NORMAL, Scratch.INSTANCE),
    SCREECH("Screech", Type.NORMAL, Screech.INSTANCE),
    SEISMIC_TOSS("Seismic Toss", Type.FIGHTING, SeismicToss.INSTANCE),
    SELF_DESTRUCT("Self-Destruct", Type.NORMAL, SelfDestruct.INSTANCE),
    SHARPEN("Sharpen", Type.NORMAL, Sharpen.INSTANCE),
    SING("Sing", Type.NORMAL, Sing.INSTANCE),
    SKY_ATTACK("Sky Attack", Type.FLYING, SkyAttack.INSTANCE),
    SKULL_BASH("Skull Bash", Type.NORMAL, SkullBash.INSTANCE),
    SLAM("Slam", Type.NORMAL, Slam.INSTANCE),
    SLASH("Slash", Type.NORMAL, Slash.INSTANCE),
    SLEEP_POWDER("Sleep Powder", Type.GRASS, SleepPowder.INSTANCE),
    SLUDGE("Sludge", Type.POISON, Sludge.INSTANCE),
    SMOG("Smog", Type.POISON, Smog.INSTANCE),
    SMOKESCREEN("Smokescreen", Type.NORMAL, Smokescreen.INSTANCE),
    SOFT_BOILED("Soft-Boiled", Type.NORMAL, SoftBoiled.INSTANCE),
    SOLAR_BEAM("Solar Beam", Type.GRASS, SolarBeam.INSTANCE),
    SONIC_BOOM("Sonic Boom", Type.NORMAL, SonicBoom.INSTANCE),
    SPIKE_CANNON("Spike Cannon", Type.NORMAL, SpikeCannon.INSTANCE),
    SPLASH("Splash", Type.NORMAL, Splash.INSTANCE),
    SPORE("Spore", Type.GRASS, Spore.INSTANCE),
    STOMP("Stomp", Type.NORMAL, Stomp.INSTANCE),
    STRENGTH("Strength", Type.NORMAL, Strength.INSTANCE),
    STRING_SHOT("String Shot", Type.BUG, StringShot.INSTANCE),
    STRUGGLE("Struggle", Type.NORMAL, Struggle.INSTANCE),
    STUN_SPORE("Stun Spore", Type.GRASS, StunSpore.INSTANCE),
    SUBMISSION("Submission", Type.FIGHTING, Submission.INSTANCE),
    SUBSTITUTE("Substitute", Type.NORMAL, Substitute.INSTANCE),
    SUPER_FANG("Super Fang", Type.NORMAL, SuperFang.INSTANCE),
    SUPERSONIC("Supersonic", Type.NORMAL, Supersonic.INSTANCE),
    SURF("Surf", Type.WATER, Surf.INSTANCE),
    SWIFT("Swift", Type.NORMAL, Swift.INSTANCE),
    SWORDS_DANCE("Swords Dance", Type.NORMAL, SwordsDance.INSTANCE),
    TACKLE("Tackle", Type.NORMAL, Tackle.INSTANCE),
    TAIL_WHIP("Tail Whip", Type.NORMAL, TailWhip.INSTANCE),
    TAKE_DOWN("Take Down", Type.NORMAL, TakeDown.INSTANCE),
    TELEPORT("Teleport", Type.PSYCHIC, Teleport.INSTANCE),
    THRASH("Thrash", Type.NORMAL, Thrash.INSTANCE),
    THUNDER("Thunder", Type.ELECTRIC, Thunder.INSTANCE),
    THUNDERBOLT("Thunderbolt", Type.ELECTRIC, Thunderbolt.INSTANCE),
    THUNDER_PUNCH("Thunder Punch", Type.ELECTRIC, ThunderPunch.INSTANCE),
    THUNDER_SHOCK("ThunderShock", Type.ELECTRIC, ThunderShock.INSTANCE),
    THUNDER_WAVE("ThunderWave", Type.ELECTRIC, ThunderWave.INSTANCE),
    TOXIC("Toxic", Type.POISON, Toxic.INSTANCE),
    TRANSFORM("Transform", Type.NORMAL, Transform.INSTANCE),
    TRI_ATTACK("Tri Attack", Type.NORMAL, TriAttack.INSTANCE),
    TWINEEDLE("Twineedle", Type.BUG, Twineedle.INSTANCE),
    VINE_WHIP("Vine Whip", Type.GRASS, VineWhip.INSTANCE),
    VISE_GRIP("Vise Grip", Type.NORMAL, ViseGrip.INSTANCE),
    WATERFALL("Waterfall", Type.WATER, Waterfall.INSTANCE),
    WATER_GUN("Water Gun", Type.WATER, WaterGun.INSTANCE),
    WHIRLWIND("Whirlwind", Type.NORMAL, Whirlwind.INSTANCE),
    WING_ATTACK("Wing Attack", Type.FLYING, WingAttack.INSTANCE),
    WITHDRAW("Withdraw", Type.WATER, Withdraw.INSTANCE),
    WRAP("Wrap", Type.NORMAL, Wrap.INSTANCE);






    private final String displayName;
    private final Type type;
    private final Move move; // Function to create a Move instance from this enum constant


    // Constructor
    PokeMove(String displayName, Type type, Move moveInstance) {
        this.displayName = displayName;
        this.type = type;
        this.move = moveInstance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Type getType() {
        return type;
    }

    public Move createMove() {
        return move;
    }

    public static PokeMove fromString(String moveName) {
        for (PokeMove move : PokeMove.values()) {
            if (move.displayName.equalsIgnoreCase(moveName)) {
                return move;
            }
        }
        throw new IllegalArgumentException("No move found with name: " + moveName);
    }

}

