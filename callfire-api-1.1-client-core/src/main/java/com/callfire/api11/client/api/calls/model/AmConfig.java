package com.callfire.api11.client.api.calls.model;

/**
 * Answering machine config.
 * AMD - answering machine detection
 */
public enum AmConfig {
    /**
     * run AMD, hang up if LA
     */
    AM_ONLY,
    /**
     * run AMD, play separate live vs. machine sound
     */
    AM_AND_LIVE,
    /**
     * run AMD, hang up if machine
     */
    LIVE_WITH_AMD,
    /**
     * no AMD, play live sound immediately
     */
    LIVE_IMMEDIATE
}
