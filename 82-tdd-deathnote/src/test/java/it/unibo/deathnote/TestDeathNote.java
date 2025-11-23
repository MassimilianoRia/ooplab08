package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
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

    @Test
    void checkNoEmptyOrNullRule() {
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule, "Rule should not be null");
            assertFalse(rule.isBlank(), "Rule should not be blank or empty");
        }
    }

    @Test
    void checkNameCorrectlyWritten() {
        assertFalse(deathnote.isNameWritten("Lucas"));
        deathnote.writeName("Lucas");
        assertTrue(deathnote.isNameWritten("Lucas"));
        assertFalse(deathnote.isNameWritten("Luca"));
        assertFalse(deathnote.isNameWritten(""));
    }

    @Test
    void checkDeathCauseIsCorrectlyManaged() throws InterruptedException {
        try {
            deathnote.writeDeathCause("eating a nail");
            fail("Expected IllegalStateException when writing death cause before writing a name");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        deathnote.writeName("Dustin");
        assertEquals("heart attack", deathnote.getDeathCause("Dustin"));
        deathnote.writeName("Will");
        assertTrue(deathnote.writeDeathCause("karting accident"));
        assertEquals("karting accident", deathnote.getDeathCause("Will"));
        Thread.sleep(100);
        assertFalse(deathnote.writeDeathCause("car accident"));
        assertNotEquals("car accident", deathnote.getDeathCause("Will"));
    }

    @Test
    void checkDeathDetailsAreCorrectlyManaged() throws InterruptedException {
        try {
            deathnote.writeDetails("a lot of pain");
            fail("Expected IllegalStateException when writing death details before writing a name");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());           
        }
        deathnote.writeName("Mike");
        assertEquals("", deathnote.getDeathDetails("Mike"));
        assertTrue(deathnote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathnote.getDeathDetails("Mike"));
        deathnote.writeName("Steve");
        Thread.sleep(6100);
        assertFalse(deathnote.writeDetails("riding an horse"));
        assertNotEquals("riding an horse", deathnote.getDeathDetails("Steve"));
    }
}