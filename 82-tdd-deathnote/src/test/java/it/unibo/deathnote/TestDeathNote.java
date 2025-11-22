package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.impl.DeathNoteImpl;

/**
 * Test class for the {@link DeathNoteImpl} class.
 */
class TestDeathNote {

    private DeathNoteImpl deathnote;

    /**
     * Configuration step: this is performed BEFORE each test.
     */
    @BeforeEach
    void setUp() {
        this.deathnote = new DeathNoteImpl();
    }

    @Test
    void checkNoZeroOrNegativeRules() {
        try {
            deathnote.getRule(0);
            fail("Expected IllegalArgumentException for rule 0");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        try {
            deathnote.getRule(-1);
            fail("Expected IllegalArgumentException for negative rule number");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

}