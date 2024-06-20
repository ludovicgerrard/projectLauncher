package com.zkteco.android.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\b\u0018\u00002\u00020\u0001B3\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0016\b\u0002\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00070\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0017\u0010\u0012\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00070\u0007HÆ\u0003J7\u0010\u0013\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0016\b\u0002\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00070\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0004HÖ\u0001R&\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR(\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00070\u0007X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lcom/zkteco/android/util/CursorHolder;", "", "columnInfo", "", "", "", "data", "", "(Ljava/util/Map;Ljava/util/List;)V", "getColumnInfo", "()Ljava/util/Map;", "setColumnInfo", "(Ljava/util/Map;)V", "getData", "()Ljava/util/List;", "setData", "(Ljava/util/List;)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: DatabaseUtils.kt */
public final class CursorHolder {
    private Map<String, Integer> columnInfo;
    private List<? extends List<String>> data;

    public CursorHolder() {
        this((Map) null, (List) null, 3, (DefaultConstructorMarker) null);
    }

    public static /* bridge */ /* synthetic */ CursorHolder copy$default(CursorHolder cursorHolder, Map<String, Integer> map, List<? extends List<String>> list, int i, Object obj) {
        if ((i & 1) != 0) {
            map = cursorHolder.columnInfo;
        }
        if ((i & 2) != 0) {
            list = cursorHolder.data;
        }
        return cursorHolder.copy(map, list);
    }

    public final Map<String, Integer> component1() {
        return this.columnInfo;
    }

    public final List<List<String>> component2() {
        return this.data;
    }

    public final CursorHolder copy(Map<String, Integer> map, List<? extends List<String>> list) {
        Intrinsics.checkParameterIsNotNull(map, "columnInfo");
        Intrinsics.checkParameterIsNotNull(list, "data");
        return new CursorHolder(map, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursorHolder)) {
            return false;
        }
        CursorHolder cursorHolder = (CursorHolder) obj;
        return Intrinsics.areEqual((Object) this.columnInfo, (Object) cursorHolder.columnInfo) && Intrinsics.areEqual((Object) this.data, (Object) cursorHolder.data);
    }

    public int hashCode() {
        Map<String, Integer> map = this.columnInfo;
        int i = 0;
        int hashCode = (map != null ? map.hashCode() : 0) * 31;
        List<? extends List<String>> list = this.data;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "CursorHolder(columnInfo=" + this.columnInfo + ", data=" + this.data + ")";
    }

    public CursorHolder(Map<String, Integer> map, List<? extends List<String>> list) {
        Intrinsics.checkParameterIsNotNull(map, "columnInfo");
        Intrinsics.checkParameterIsNotNull(list, "data");
        this.columnInfo = map;
        this.data = list;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ CursorHolder(Map map, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new LinkedHashMap() : map, (i & 2) != 0 ? new ArrayList() : list);
    }

    public final Map<String, Integer> getColumnInfo() {
        return this.columnInfo;
    }

    public final List<List<String>> getData() {
        return this.data;
    }

    public final void setColumnInfo(Map<String, Integer> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.columnInfo = map;
    }

    public final void setData(List<? extends List<String>> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.data = list;
    }
}
