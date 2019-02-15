package me.vinco.teamspeakapi.apis.api.wrapper;

import me.vinco.teamspeakapi.apis.api.property.ServerGroupType;

public class ServerGroup extends _DefaultInfo {
   public ServerGroup(String[] infos) {
      super(infos);
   }

   public int getID() {
      return this.toInt(this.get(0));
   }

   public String getName() {
      return this.get(1).replace("\\s", " ");
   }

   private int getTypeID() {
      return this.toInt(this.get(2));
   }

   public ServerGroupType getType() {
      for(ServerGroupType servergrouptype : ServerGroupType.values()) {
         if(this.getTypeID() == servergrouptype.getIndex()) {
            return servergrouptype;
         }
      }

      return null;
   }

   public long getIconID() {
      return this.toLong(this.get(3));
   }

   public boolean isSaved() {
      return this.toBol(Integer.parseInt(this.get(4)));
   }

   public int getSortID() {
      return this.toInt(this.get(5));
   }

   public int getNeededModifyPower() {
      return this.toInt(this.get(7));
   }

   public int getNeededMemberAddPower() {
      return this.toInt(this.get(8));
   }

   public int getNeededMemberRemovePower() {
      return this.toInt(this.get(9).replace("|", ""));
   }

   public String toString() {
      return "ServerGruppe[ServerGroupName=" + this.getName() + ",GroupID=" + this.getID() + ",SortID=" + this.getSortID() + ",Type=" + this.getType() + ",IconID=" + this.getIconID() + "]";
   }
}
