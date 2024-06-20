package com.zkteco.util;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getNameWithoutExtension", "", "Ljava/io/File;", "HelpersUtils"}, k = 2, mv = {1, 1, 9})
/* compiled from: FileUtils.kt */
public final class FileUtilsKt {
    public static final String getNameWithoutExtension(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        String name = file.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "this.name");
        return StringsKt.substringBefore$default(name, '.', (String) null, 2, (Object) null);
    }
}
