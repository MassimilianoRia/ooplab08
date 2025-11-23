package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final Map<String, Data> notes;
    private String lastName;

    private final static class Data {
        String deathCause;
        String deathDetails;
        long nameTimestamp;
        long causeTimestamp;
    }

    public DeathNoteImpl() {
        this.notes = new HashMap<>();
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule number out of range");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        this.lastName = name;
        this.notes.put(name, new Data());
        this.notes.get(name).nameTimestamp = System.currentTimeMillis();
        this.notes.get(name).causeTimestamp = System.currentTimeMillis();
        this.notes.get(name).deathCause = "heart attack";
        this.notes.get(name).deathDetails = "";
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null) {
            throw new IllegalStateException("Death Cause cannot be null");
        }
        if (this.lastName == null) {
            throw new IllegalStateException("DeathNote cannot be empty");
        }
        final Data lastNameData = this.notes.get(lastName);
        final long lastNameTimestamp = lastNameData.nameTimestamp;
        final long currentTime = System.currentTimeMillis();
        if (currentTime - lastNameTimestamp <= 40) {
            lastNameData.deathCause = cause;
            lastNameData.causeTimestamp = currentTime;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null) {
            throw new IllegalStateException("Details cannot be null");
        }
        if (this.lastName == null) {
            throw new IllegalStateException("DeathNote cannot be empty");
        }
        final Data lastNameData = this.notes.get(lastName);
        final long currentTime = System.currentTimeMillis();
        final long lastNameCauseTimestamp = lastNameData.causeTimestamp;
        if (currentTime - lastNameCauseTimestamp <= 6040) {
            lastNameData.deathDetails = details;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        final Data nameData = this.notes.get(name);
        if (nameData == null) {
            throw new IllegalArgumentException("Name is not written in DeathNote");
        }
        return nameData.deathCause;
    }

    @Override
    public String getDeathDetails(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        final Data nameData = this.notes.get(name);
        if (nameData == null) {
            throw new IllegalArgumentException("Name is not written in DeathNote");
        }
        return nameData.deathDetails;
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.notes.containsKey(name);
    }
    
}
