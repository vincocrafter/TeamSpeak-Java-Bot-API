package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class TextMessageEvent extends BaseEvent {
	
   public TextMessageEvent(String[] infos) {
      super(infos);
   }
   
   public int getClientID() {
      return toInt(get(4));
   }

   public String getClientName() {
      return get(5);
   }

   public int getTargetMode() {
      return toInt(get(1));
   }

   public String getMessage() {
      return Formatter.toNormalFormat(get(2));
   }

   public TextMessageType getTextMessageType() {
      for(TextMessageType type : TextMessageType.values()) {
         if(getTargetMode() == type.getValue()) {
            return type;
         }
      }
      return null;
   }
}
