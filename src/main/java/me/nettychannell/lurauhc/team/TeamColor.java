package me.nettychannell.lurauhc.team;

import lombok.Getter;

public enum TeamColor {

    RED("c"),
    BLUE("9"),
    GREEN("a"),
    YELLOW("e"),
    AQUA("b"),
    PINK("d"),
    WHITE("f"),
    GRAY("7");

    @Getter
    private final String colorCode;

    TeamColor(String colorCode) {
        this.colorCode = colorCode;
    }

}
