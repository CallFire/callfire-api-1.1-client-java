package com.callfire.api11.client.api.calls.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VoiceBroadcastConfig extends CallBroadcastConfig {
    private AmConfig answeringMachineConfig;

    private String liveSoundText;
    private Long liveSoundId;
    private Voice liveSoundTextVoice;

    private String machineSoundText;
    private Long machineSoundId;
    private Voice machineSoundTextVoice;

    private String transferSoundText;
    private Long transferSoundId;
    private Voice transferSoundTextVoice;

    private String transferDigit;
    private String transferNumber;

    private String dncSoundText;
    private Long dncSoundId;
    private Voice dncSoundTextVoice;

    private String dncDigit;

    private Integer maxActiveTransfers;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static VoiceBroadcastConfigBuilder create() {
        return new VoiceBroadcastConfigBuilder();
    }

    public AmConfig getAnsweringMachineConfig() {
        return answeringMachineConfig;
    }

    public String getLiveSoundText() {
        return liveSoundText;
    }

    public Long getLiveSoundId() {
        return liveSoundId;
    }

    public Voice getLiveSoundTextVoice() {
        return liveSoundTextVoice;
    }

    public String getMachineSoundText() {
        return machineSoundText;
    }

    public Long getMachineSoundId() {
        return machineSoundId;
    }

    public Voice getMachineSoundTextVoice() {
        return machineSoundTextVoice;
    }

    public String getTransferSoundText() {
        return transferSoundText;
    }

    public Long getTransferSoundId() {
        return transferSoundId;
    }

    public Voice getTransferSoundTextVoice() {
        return transferSoundTextVoice;
    }

    public String getTransferDigit() {
        return transferDigit;
    }

    public String getTransferNumber() {
        return transferNumber;
    }

    public String getDncSoundText() {
        return dncSoundText;
    }

    public Long getDncSoundId() {
        return dncSoundId;
    }

    public Voice getDncSoundTextVoice() {
        return dncSoundTextVoice;
    }

    public String getDncDigit() {
        return dncDigit;
    }

    public Integer getMaxActiveTransfers() {
        return maxActiveTransfers;
    }

    public void setAnsweringMachineConfig(AmConfig answeringMachineConfig) {
        this.answeringMachineConfig = answeringMachineConfig;
    }

    public void setLiveSoundText(String liveSoundText) {
        this.liveSoundText = liveSoundText;
    }

    public void setLiveSoundId(Long liveSoundId) {
        this.liveSoundId = liveSoundId;
    }

    public void setLiveSoundTextVoice(Voice liveSoundTextVoice) {
        this.liveSoundTextVoice = liveSoundTextVoice;
    }

    public void setMachineSoundText(String machineSoundText) {
        this.machineSoundText = machineSoundText;
    }

    public void setMachineSoundId(Long machineSoundId) {
        this.machineSoundId = machineSoundId;
    }

    public void setMachineSoundTextVoice(Voice machineSoundTextVoice) {
        this.machineSoundTextVoice = machineSoundTextVoice;
    }

    public void setTransferSoundText(String transferSoundText) {
        this.transferSoundText = transferSoundText;
    }

    public void setTransferSoundId(Long transferSoundId) {
        this.transferSoundId = transferSoundId;
    }

    public void setTransferSoundTextVoice(Voice transferSoundTextVoice) {
        this.transferSoundTextVoice = transferSoundTextVoice;
    }

    public void setTransferDigit(String transferDigit) {
        this.transferDigit = transferDigit;
    }

    public void setTransferNumber(String transferNumber) {
        this.transferNumber = transferNumber;
    }

    public void setDncSoundText(String dncSoundText) {
        this.dncSoundText = dncSoundText;
    }

    public void setDncSoundId(Long dncSoundId) {
        this.dncSoundId = dncSoundId;
    }

    public void setDncSoundTextVoice(Voice dncSoundTextVoice) {
        this.dncSoundTextVoice = dncSoundTextVoice;
    }

    public void setDncDigit(String dncDigit) {
        this.dncDigit = dncDigit;
    }

    public void setMaxActiveTransfers(Integer maxActiveTransfers) {
        this.maxActiveTransfers = maxActiveTransfers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("answeringMachineConfig", answeringMachineConfig)
            .append("liveSoundText", liveSoundText)
            .append("liveSoundId", liveSoundId)
            .append("liveSoundTextVoice", liveSoundTextVoice)
            .append("machineSoundText", machineSoundText)
            .append("machineSoundId", machineSoundId)
            .append("machineSoundTextVoice", machineSoundTextVoice)
            .append("transferSoundText", transferSoundText)
            .append("transferSoundId", transferSoundId)
            .append("transferSoundTextVoice", transferSoundTextVoice)
            .append("transferDigit", transferDigit)
            .append("transferNumber", transferNumber)
            .append("dncSoundText", dncSoundText)
            .append("dncSoundId", dncSoundId)
            .append("dncSoundTextVoice", dncSoundTextVoice)
            .append("dncDigit", dncDigit)
            .append("maxActiveTransfers", maxActiveTransfers)
            .toString();
    }

    public static class VoiceBroadcastConfigBuilder
        extends BroadcastConfigBuilder<VoiceBroadcastConfigBuilder, VoiceBroadcastConfig> {
        public VoiceBroadcastConfigBuilder() {
            super(new VoiceBroadcastConfig());
        }

        /**
         * Set action to take if machine answers
         *
         * @param amConfig action to take if machine answers
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder amConfig(AmConfig amConfig) {
            request.answeringMachineConfig = amConfig;
            return this;
        }

        /**
         * Set max transfers
         *
         * @param maxActiveTransfers max transfers value
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder maxActiveTransfers(int maxActiveTransfers) {
            request.maxActiveTransfers = maxActiveTransfers;
            return this;
        }

        /**
         * Set sound to play if call answered by live person
         *
         * @param soundId id of sound to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder liveSound(long soundId) {
            request.liveSoundId = soundId;
            return this;
        }

        /**
         * Set sound using Text-To-Speech to play if call answered by live person. Default voice is Voice.FEMALE1
         *
         * @param text text to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder liveSound(String text) {
            return liveSound(text, Voice.FEMALE1);
        }

        /**
         * Set sound using Text-To-Speech to play if call answered by live person
         *
         * @param text  text to play
         * @param voice TTS voice
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder liveSound(String text, Voice voice) {
            request.liveSoundText = text;
            request.liveSoundTextVoice = voice;
            return this;
        }

        /**
         * Set message that plays when an answering machine picks up your call. Answering machines are detected
         * by the CallFire system.
         *
         * @param soundId id of sound to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder amSound(long soundId) {
            request.machineSoundId = soundId;
            return this;
        }

        /**
         * Set message that plays when an answering machine picks up your call. Answering machines are detected
         * by the CallFire system. Default voice is Voice.FEMALE1
         *
         * @param text text to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder amSound(String text) {
            return amSound(text, Voice.FEMALE1);
        }

        /**
         * Set message that plays when an answering machine picks up your call. Answering machines are detected
         * by the CallFire system.
         *
         * @param text  text to play
         * @param voice TTS voice
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder amSound(String text, Voice voice) {
            request.machineSoundText = text;
            request.machineSoundTextVoice = voice;
            return this;
        }

        /**
         * Set sound to play if call transferred
         *
         * @param soundId id of sound to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder transferSound(long soundId) {
            request.transferSoundId = soundId;
            return this;
        }

        /**
         * Set message that plays when the Transfer digit is pressed. This message informs the customer
         * that they are being transferred to a representative.. Default voice is Voice.FEMALE1
         *
         * @param text text to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder transferSound(String text) {
            return transferSound(text, Voice.FEMALE1);
        }

        /**
         * Set message that plays when the Transfer digit is pressed. This message informs the customer that they
         * are being transferred to a representative.
         *
         * @param text  text to play
         * @param voice TTS voice
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder transferSound(String text, Voice voice) {
            request.transferSoundText = text;
            request.transferSoundTextVoice = voice;
            return this;
        }

        /**
         * Set transfer Digit that recipients should press to be transferred
         *
         * @param digit transfer Digit
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder transferDigit(String digit) {
            request.transferDigit = digit;
            return this;
        }

        /**
         * Set the phone number to which calls will be transferred
         *
         * @param number phone number should be E.164 11 digit format
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder transferNumber(String number) {
            request.transferNumber = number;
            return this;
        }

        /**
         * Set message that plays to acknowledge that the Do Not Call digit has been pressed and inform your contact
         * of their placement on the Do Not Call list.
         *
         * @param soundId id of sound to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder dncSound(long soundId) {
            request.dncSoundId = soundId;
            return this;
        }

        /**
         * Set message that plays to acknowledge that the Do Not Call digit has been pressed and inform your contact
         * of their placement on the Do Not Call list. Default voice is Voice.FEMALE1.
         * If the Do Not Call sound is set, then the Live Answer message should indicate the digit a person can
         * press to be placed on your Do Not Call list.
         *
         * @param text text to play
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder dncSound(String text) {
            return dncSound(text, Voice.FEMALE1);
        }

        /**
         * Set message that plays to acknowledge that the Do Not Call digit has been pressed and inform your contact
         * of their placement on the Do Not Call list.
         * If the Do Not Call text is set, then the Live Answer message should indicate the digit a person can
         * press to be placed on your Do Not Call list.
         *
         * @param text  text to play
         * @param voice TTS voice
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder dncSound(String text, Voice voice) {
            request.dncSoundText = text;
            request.dncSoundTextVoice = voice;
            return this;
        }

        /**
         * Set Do Not Call digit. Set by default to 8 if dncSound provided.
         *
         * @param digit dnc digit 0-9 range
         * @return builder self reference
         */
        public VoiceBroadcastConfigBuilder dncDigit(String digit) {
            Validate.matchesPattern(digit, "[0-9]{1}", "digit must be in 0-9 range");
            request.dncDigit = digit;
            return this;
        }
    }
}
