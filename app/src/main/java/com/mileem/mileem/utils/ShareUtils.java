package com.mileem.mileem.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.AsyncRestHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramirodiaz on 24/10/14.
 */
public class ShareUtils {
    public static void share(Context context, View view, PublicationDetails p) {
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = null;
        PackageManager pm = view.getContext().getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(sharingIntent, 0);
        for(final ResolveInfo app : activityList) {
            String packageName = app.activityInfo.packageName;
            Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
            targetedShareIntent.setType("text/plain");
            targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MiLEEM");
            if(TextUtils.equals(packageName, "com.twitter.android")){
                shareBody = "Prop. en " + p.getNeighborhood().getName() + " a " + p.getPrice() +  " " + p.getCurrency() + ". "
                        + p.getUser().getTelephone() + ". Descarga MiLEEN App, disponible para Android, o visita "
                        + AsyncRestHttpClient.getAbsoluteUrlRelativeToHost("login") + ".";
                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            } else {
                shareBody = "Propiedad en " + p.getOperationType().getName().toLowerCase() + ", ubicada en " + p.getAddress() + ", "
                        + Integer.toString(p.getSize()) + "m2 a " + p.getPrice() +  " " + p.getCurrency() + ". Tel "
                        + p.getUser().getTelephone() + ". Descarga MiLEEN App, disponible para Android, o visita "
                        + AsyncRestHttpClient.getAbsoluteUrlRelativeToHost("login") +  " para realizar nuevas publicaciones.";

                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            }
            targetedShareIntent.setPackage(packageName);
            targetedShareIntents.add(targetedShareIntent);

        }
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Pega el texto", shareBody);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copiado en el portapapeles", Toast.LENGTH_LONG).show();
        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Compartir con...");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
        context.startActivity(chooserIntent);
    }
}
