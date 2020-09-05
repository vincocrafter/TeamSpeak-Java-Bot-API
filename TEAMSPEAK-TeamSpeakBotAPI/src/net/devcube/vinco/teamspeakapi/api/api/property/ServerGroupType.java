package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ServerGroupType {
   DEFAULT(0),
   NORMAL(1),
   QUERY(2);

   private int i = 0;

   private ServerGroupType(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}
