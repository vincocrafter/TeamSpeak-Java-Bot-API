package me.vinco.teamspeakapi.apis.api.wrapper;

import me.vinco.teamspeakapi.apis.api.property.ChannelGroupType;

public class ChannelGroup extends _DefaultInfo {
	
   public ChannelGroup(String[] infos) {
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

   public ChannelGroupType getType() {
      for(ChannelGroupType channelgrouptype : ChannelGroupType.values()) {
         if(this.getTypeID() == channelgrouptype.getIndex()) {
            return channelgrouptype;
         }
      }

      return null;
   }

   public boolean isSaved() {
      return this.toBol(this.toInt(this.get(4)));
   }

   public int getNeededModifyPower() {
      return this.toInt(this.get(7));
   }

   public int getNeededMemberAddPower() {
      return this.toInt(this.get(8));
   }

   public int getNeededMemberRemovePower() {
      return this.toInt(this.get(9));
   }
}
