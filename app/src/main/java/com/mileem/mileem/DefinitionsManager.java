package com.mileem.mileem;

/**
 * Created by ramirodiaz on 09/09/14.
 */

public class DefinitionsManager {
    private static volatile DefinitionsManager instance = null;
    private DefinitionsManager() {};
    public static DefinitionsManager getInstance() {
        if (instance == null) {
            synchronized (DefinitionsManager.class) {
                if (instance == null) {
                    instance = new DefinitionsManager();
                }
            }
        }
        return instance;
    }
}