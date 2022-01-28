package dev.vality.cm.handler.event;

import dev.vality.damsel.claim_management.Event;

public interface ClaimEventHandler {

    void handle(Event event);

    boolean isHandle(Event event);

}
