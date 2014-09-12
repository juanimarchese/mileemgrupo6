package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 09/09/14.
 */
public abstract class CallbackHandler {
    public abstract void onComplete(Object object);
    public abstract void onFailure(Object object);
}

