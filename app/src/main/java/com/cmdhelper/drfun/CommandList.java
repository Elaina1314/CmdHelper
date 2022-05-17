package com.cmdhelper.drfun;

public class CommandList {
    public static Object[] commandList() {
        Object[] commandlist = new Object[]{
                "?",
                "ability",
                "alwaysday",
                "camerashake",
                "changesetting",
                "clear",
                "clearspawnpoint",
                "clone",
                "connect",
                "damage",
                "daylock",
                "dedicatedwsserver",
                "deop",
                "difficulty",
                "effect",
                "enchant",
                "event",
                "execute",
                "fill",
                "fog",
                "function",
                "gamemode",
                "gamerule",
                "gametest",
                "give",
                "help",
                "immutableworld",
                "kick",
                "kill",
                "list",
                "locate",
                "loot",
                "me",
                "mobevent",
                "msg",
                "music",
                "op",
                "ops",
                "particle",
                "permission",
                "playanimation",
                "playsound",
                "reload",
                "replaceitem",
                "ride",
                "save",
                "say",
                "schedule",
                "scoreboard",
                "setblock",
                "setmaxplayers",
                "setworldspawn",
                "spawnpoint",
                "spreadplayers",
                "stop",
                "stopsound",
                "structure",
                "summon",
                "tag",
                "teleport",
                "tell",
                "tellraw",
                "testfor",
                "testforblock",
                "testforblocks",
                "tickingarea",
                "time",
                "title",
                "titleraw",
                "toggledownfall",
                "tp",
                "volumearea",
                "w",
                "wb",
                "weather",
                "whitelist",
                "worldbuilder",
                "wsserver",
                "xp",
        };
        return commandlist;
    }

    public static Object[] commandTarget() {
        Object[] commandtarget = new Object[]{
                "@p",
                "@s",
                "@a",
                "@r",
                "@e",
        };
        return commandtarget;
    }

    public static Object[] commandTargetSelector() {
        Object[] commandtargetselector = new Object[]{
                "@p",
                "@s",
                "@a",
                "@r",
                "@e",
        };
        return commandtargetselector;
    }

    public static Object[] commandTargetSelectorParameters() {
        Object[] commandtargetselectorparameters = new Object[]{
                "x",
                "y",
                "z",
                "r",
                "rm",
                "dx",
                "dy",
                "dz",
                "scores",
                "tag",
                "name",
                "type",
                "family",
                "rx",
                "rxm",
                "ry",
                "rym",
                "hasitem",
                "l",
                "lm",
                "m",
                "c",
        };
        return commandtargetselectorparameters;
    }
}
