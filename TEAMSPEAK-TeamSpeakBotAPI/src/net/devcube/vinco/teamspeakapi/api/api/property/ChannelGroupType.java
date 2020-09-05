package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ChannelGroupType {
   DEFAULT(0),
   NORMAL(1);

   private int i = 0;

   private ChannelGroupType(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}
