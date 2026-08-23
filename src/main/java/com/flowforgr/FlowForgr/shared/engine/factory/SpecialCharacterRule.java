package com.flowforgr.FlowForgr.shared.engine.factory;

import org.passay.CharacterData;

public class SpecialCharacterRule implements CharacterData {
    private final String characters;
    public SpecialCharacterRule(String characters) {
        this.characters = characters;
    }

    @Override
    public String getErrorCode() {
        return "INSUFFICIENT_SPECIAL";
    }

    @Override
    public String getCharacters() {
        return characters;
    }
}

