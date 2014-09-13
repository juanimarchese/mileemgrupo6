package com.mileem.mileem.networking;

import java.util.Collection;

/**
 * Created by ramirodiaz on 09/09/14.
 */
public abstract class CallbackHandler {
    public abstract void onComplete(Object object);
    public abstract void onComplete(Collection collection);
    public abstract void onFailure(Error error);
}

