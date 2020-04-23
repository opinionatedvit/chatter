package com.amgbs.watcher.utility;

public enum ActionType {

    FILE_ACTION_ADDED("added"),
    FILE_ACTION_REMOVED ("removed"),
    FILE_ACTION_MODIFIED ("modified"),
    FILE_ACTION_RENAMED_OLD_NAME ("changed name"),
    FILE_ACTION_RENAMED_NEW_NAME ("assigned new name");

    private String actionName;

    ActionType(String actionName) {
    this.actionName = actionName;
    }
    public String getActionName() {
        return actionName;
    }
}
