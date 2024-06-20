package com.zkteco.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\u0013\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00028\u0000¢\u0006\u0002\u0010\u0017J\u001b\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\u00020\u00152\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bJ\u001c\u0010\u0019\u001a\u00020\u00152\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001b2\u0006\u0010\t\u001a\u00020\u0005J\u0006\u0010\u001c\u001a\u00020\u0015J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u001e2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bH\u0016J\u0013\u0010!\u001a\u00020\u001e2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002J\b\u0010$\u001a\u00020\u0011H\u0016J\b\u0010%\u001a\u00020\u001eH\u0016J\t\u0010&\u001a\u00020'H\u0002J\u0013\u0010(\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00028\u0000¢\u0006\u0002\u0010\u0017J\u0014\u0010)\u001a\u00020\u00152\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bJ\u0014\u0010*\u001a\u00020\u00152\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bJ-\u0010+\u001a\u0012\u0012\u0002\b\u0003 ,*\b\u0012\u0002\b\u0003\u0018\u00010\r0\r2\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010-J\b\u0010.\u001a\u00020/H\u0016R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\fX\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u0011X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u00060"}, d2 = {"Lcom/zkteco/util/ConcurrentTemporarySet;", "T", "", "()V", "globalPeriod", "", "(J)V", "scheduler", "Ljava/util/concurrent/ScheduledExecutorService;", "period", "(Ljava/util/concurrent/ScheduledExecutorService;J)V", "map", "", "Ljava/util/concurrent/ScheduledFuture;", "getPeriod", "()J", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)V", "(Ljava/lang/Object;J)V", "addAll", "elements", "", "clear", "contains", "", "(Ljava/lang/Object;)Z", "containsAll", "equals", "other", "", "hashCode", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "scheduleRemove", "kotlin.jvm.PlatformType", "(Ljava/lang/Object;J)Ljava/util/concurrent/ScheduledFuture;", "toString", "", "HelpersUtils"}, k = 1, mv = {1, 1, 9})
/* compiled from: ConcurrentTemporarySet.kt */
public final class ConcurrentTemporarySet<T> implements Set<T>, KMappedMarker {
    /* access modifiers changed from: private */
    public final Map<T, ScheduledFuture<?>> map;
    private final long period;
    private final ScheduledExecutorService scheduler;
    private final int size;

    /* renamed from: add  reason: collision with other method in class */
    public boolean m10add(T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: addAll  reason: collision with other method in class */
    public boolean m11addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: remove  reason: collision with other method in class */
    public boolean m12remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: removeAll  reason: collision with other method in class */
    public boolean m13removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: retainAll  reason: collision with other method in class */
    public boolean m14retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return CollectionToArray.toArray(this, tArr);
    }

    public ConcurrentTemporarySet(ScheduledExecutorService scheduledExecutorService, long j) {
        int size2;
        Intrinsics.checkParameterIsNotNull(scheduledExecutorService, "scheduler");
        this.scheduler = scheduledExecutorService;
        this.period = j;
        Map<T, ScheduledFuture<?>> linkedHashMap = new LinkedHashMap<>();
        this.map = linkedHashMap;
        synchronized (linkedHashMap) {
            size2 = linkedHashMap.size();
        }
        this.size = size2;
    }

    public final long getPeriod() {
        return this.period;
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ConcurrentTemporarySet() {
        /*
            r3 = this;
            java.util.concurrent.ScheduledExecutorService r0 = com.zkteco.util.ExecutorSingleton.getScheduled()
            java.lang.String r1 = "ExecutorSingleton.getScheduled()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            r1 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r3.<init>(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.util.ConcurrentTemporarySet.<init>():void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ConcurrentTemporarySet(long r3) {
        /*
            r2 = this;
            java.util.concurrent.ScheduledExecutorService r0 = com.zkteco.util.ExecutorSingleton.getScheduled()
            java.lang.String r1 = "ExecutorSingleton.getScheduled()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            r2.<init>(r0, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.util.ConcurrentTemporarySet.<init>(long):void");
    }

    public int getSize() {
        return this.size;
    }

    public boolean contains(Object obj) {
        boolean containsKey;
        synchronized (this.map) {
            containsKey = this.map.containsKey(obj);
        }
        return containsKey;
    }

    public boolean containsAll(Collection<? extends Object> collection) {
        boolean containsAll;
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        synchronized (this.map) {
            containsAll = this.map.keySet().containsAll(collection);
        }
        return containsAll;
    }

    public boolean isEmpty() {
        boolean none;
        synchronized (this.map) {
            none = MapsKt.none(this.map);
        }
        return none;
    }

    public Void iterator() {
        throw new UnsupportedOperationException("Concurrent set cannot expose internal iterator.");
    }

    public boolean equals(Object obj) {
        boolean areEqual;
        synchronized (this.map) {
            areEqual = Intrinsics.areEqual((Object) this.map.keySet(), obj);
        }
        return areEqual;
    }

    public int hashCode() {
        int hashCode;
        synchronized (this.map) {
            hashCode = this.map.keySet().hashCode();
        }
        return hashCode;
    }

    public String toString() {
        return "ConcurrentTemporarySet(scheduler=" + this.scheduler + ", period=" + this.period + ", size=" + size() + ", set=" + this.map.keySet() + ')';
    }

    private final ScheduledFuture<?> scheduleRemove(T t, long j) {
        return this.scheduler.schedule(new ConcurrentTemporarySet$scheduleRemove$1(this, t), j, TimeUnit.MILLISECONDS);
    }

    public final void add(T t) {
        add(t, this.period);
    }

    public final void add(T t, long j) {
        synchronized (this.map) {
            ScheduledFuture scheduledFuture = this.map.get(t);
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            Map<T, ScheduledFuture<?>> map2 = this.map;
            ScheduledFuture<?> scheduleRemove = scheduleRemove(t, j);
            Intrinsics.checkExpressionValueIsNotNull(scheduleRemove, "scheduleRemove(element, period)");
            map2.put(t, scheduleRemove);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addAll(Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        addAll(collection, this.period);
    }

    public final void addAll(Collection<? extends T> collection, long j) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        for (Object add : collection) {
            add(add, j);
        }
    }

    public final void clear() {
        synchronized (this.map) {
            this.map.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void remove(T t) {
        synchronized (this.map) {
            ScheduledFuture scheduledFuture = this.map.get(t);
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            ScheduledFuture remove = this.map.remove(t);
        }
    }

    public final void removeAll(Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        synchronized (this.map) {
            for (Object next : collection) {
                ScheduledFuture scheduledFuture = this.map.get(next);
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                this.map.remove(next);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void retainAll(Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        synchronized (this.map) {
            for (Object next : this.map.keySet()) {
                if (!collection.contains(next)) {
                    remove(next);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
