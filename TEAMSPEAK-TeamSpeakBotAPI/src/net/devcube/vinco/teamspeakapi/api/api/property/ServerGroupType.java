package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ServerGroupType {
	
   DEFAULT(0),
   NORMAL(1),
   QUERY(2);

   private int value = 0;

   private ServerGroupType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}
