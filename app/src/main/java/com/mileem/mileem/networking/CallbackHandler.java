package com.mileem.mileem.networking;

import com.mileem.mileem.models.PublicationDetails;

import java.util.Collection;

/**
 * Created by ramirodiaz on 09/09/14.
 */
public abstract class CallbackHandler {
    public abstract void onFailure(Error error);
}

