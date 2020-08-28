package net.devcube.vinco.teamspeakapi.apis.api.property;

public enum ChannelCodec {
   CODEC_SPEEX_NARROWBAND(0),
   CODEC_SPEEX_WIDEBAND(1),
   CODEC_SPEEX_ULTRAWIDEBAND(2),
   CODEC_CELT_MONO(3);

   private int i = 0;

   private ChannelCodec(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}
