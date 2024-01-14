package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ChannelGroupType {
	
   DEFAULT(0),
   NORMAL(1);

   private int value = 0;

   private ChannelGroupType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}
