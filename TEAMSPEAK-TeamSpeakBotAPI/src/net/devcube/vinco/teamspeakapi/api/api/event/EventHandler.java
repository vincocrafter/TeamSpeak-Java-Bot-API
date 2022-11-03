/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2021  
 *
 * Datum : 18.06.2021
 * 
 * Uhrzeit : 16:58:15
 */
package net.devcube.vinco.teamspeakapi.api.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Annotation for the new Event System in EventManager.callNewEvent()

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

}
