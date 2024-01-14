package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ChannelCodec {
	
   CODEC_SPEEX_NARROWBAND(0),
   CODEC_SPEEX_WIDEBAND(1),
   CODEC_SPEEX_ULTRAWIDEBAND(2),
   CODEC_CELT_MONO(3);

   private int value = 0;

   private ChannelCodec(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}
