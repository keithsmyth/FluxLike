package com.keithsmyth.fluxlike.arch.flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {

    private final Map<String, List<ActionHandler>> actionHandlerMap = new HashMap<>();

    public <T extends Action> void register(String actionId, ActionHandler<T> actionHandler) {
        List<ActionHandler> actionHandlers = actionHandlerMap.getOrDefault(actionId, new ArrayList<>());
        actionHandlers.add(actionHandler);
        actionHandlerMap.put(actionId, actionHandlers);
    }

    public void clear() {
        actionHandlerMap.clear();
    }

    public void dispatch(Action action) {
        List<ActionHandler> actionHandlers = actionHandlerMap.getOrDefault(action.actionId(), Collections.emptyList());
        for (ActionHandler actionHandler : actionHandlers) {
            // noinspection unchecked
            actionHandler.handleAction(action);
        }
    }
}
