package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ChannelProperty {
   CHANNEL_NAME("channel_name"),
   CHANNEL_TOPIC("channel_topic"),
   CHANNEL_DESCRIPTION("channel_description"),
   CHANNEL_PASSWORD("channel_password"),
   CHANNEL_MAXCLIENTS("channel_maxclients"),
   CHANNEL_MAXFAMILYCLIENTS("channel_maxfamilfyclients"),
   CHANNEL_ORDER("channel_order"),
   CHANNEL_FLAG_PERMANENT("channel_flag_permanent"),
   CHANNEL_FLAG_SEMI_PERMANENT("channel_flag_semi_permanent"),
   CHANNEL_FLAG_TEMPORARY("channel_flag_temporary"),
   CHANNEL_FLAG_DEFAULT("channel_flag_default"),
   CHANNEL_FLAG_MAXCLIENTS_UNLIMITED("channel_flag_maxclients_unlimited"),
   CHANNEL_FLAG_MAXFAMILYCLIENTS_UNLIMITED("channel_flag_maxfamilyclients_unlimited"),
   CHANNEL_FLAG_MAXFAMILYCLIENTS_INHERITED("channel_flag_maxfamilyclients_inherited"),
   CHANNEL_NEEDED_TALK_POWER("channel_needed_talk_power"),
   CHANNEL_NAME_PHONETIC("channel_name_phonetic"),
   CHANNEL_ICON_ID("channel_icon_id"),
   CHANNEL_CODEC_IS_UNENCRYPTED("channel_codec_is_unencrypted"),
   CHANNEL_PARENT_ID("cpid");

   String value = "";

   private ChannelProperty(String s) {
      this.value = s;
   }

   public String getValue() {
      return this.value;
   }
}
