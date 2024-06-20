package com.zkteco.android.io;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a!\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t\u001a\u0012\u0010\n\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003XT¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"STRING_RESOURCE_IDENTIFIER_REGEX", "Lkotlin/text/Regex;", "TAG", "", "findViewByName", "T", "Landroid/view/View;", "activity", "Landroid/app/Activity;", "(Ljava/lang/String;Landroid/app/Activity;)Landroid/view/View;", "getStringResourceByName", "context", "Landroid/content/Context;", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: StringUtils.kt */
public final class StringUtilsKt {
    private static final Regex STRING_RESOURCE_IDENTIFIER_REGEX = new Regex("@string/(.*)");
    private static final String TAG = "StringUtils";

    public static final String getStringResourceByName(String str, Context context) {
        MatchGroupCollection groups;
        MatchGroup matchGroup;
        String value;
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(context, "context");
        MatchResult matchEntire = STRING_RESOURCE_IDENTIFIER_REGEX.matchEntire(str);
        if (!(matchEntire == null || (groups = matchEntire.getGroups()) == null || (matchGroup = groups.get(1)) == null || (value = matchGroup.getValue()) == null || (str = context.getString(context.getResources().getIdentifier(value, "string", context.getPackageName()))) != null)) {
            Intrinsics.throwNpe();
        }
        return str;
    }

    public static final <T extends View> T findViewByName(String str, Activity activity) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        T findViewById = activity.findViewById(activity.getResources().getIdentifier(str, "id", activity.getPackageName()));
        Intrinsics.checkExpressionValueIsNotNull(findViewById, "activity.findViewById(ac…\", activity.packageName))");
        return findViewById;
    }
}
