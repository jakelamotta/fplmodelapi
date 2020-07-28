package com.fplstats.common.dto.fplstats;

public class MatchUnderstatPlayerDto{

    private UnderstatPlayerDto understatPlayerDto;

    private PlayerDto playerDto;

    public UnderstatPlayerDto getUnderstatPlayerDto() {
        return understatPlayerDto;
    }

    public void setUnderstatPlayerDto(UnderstatPlayerDto understatPlayerDto) {
        this.understatPlayerDto = understatPlayerDto;
    }

    public PlayerDto getPlayerDto() {
        return playerDto;
    }

    public void setPlayerDto(PlayerDto playerDto) {
        this.playerDto = playerDto;
    }
}
