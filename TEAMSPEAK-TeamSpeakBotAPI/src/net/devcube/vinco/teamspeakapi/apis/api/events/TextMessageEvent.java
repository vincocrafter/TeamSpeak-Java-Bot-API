package me.vinco.teamspeakapi.apis.api.events;

import me.vinco.teamspeakapi.apis.api.event.BaseEvent;
import me.vinco.teamspeakapi.apis.api.property.TextMessageType;
import me.vinco.teamspeakapi.apis.api.util.Formatter;

public class TextMessageEvent extends BaseEvent {
	
   public TextMessageEvent(String[] infos) {
      super(infos);
   }
   
   public int getClientID() {
      return toInt(this.get(4));
   }

   public String getClientName() {
      return this.get(5);
   }

   public int getTargetMode() {
      return toInt(this.get(1));
   }

   public String getMessage() {
      return Formatter.toNormalFormat(this.get(2));
   }

   public TextMessageType getTextMessageType() {
      for(TextMessageType textmessagetype : TextMessageType.values()) {
         if(getTargetMode() == textmessagetype.getI()) {
            return textmessagetype;
         }
      }
      return null;
   }
}
