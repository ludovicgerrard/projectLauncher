package com.zkteco.util;

import java.util.concurrent.ScheduledFuture;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "T", "run"}, k = 3, mv = {1, 1, 9})
/* compiled from: ConcurrentTemporarySet.kt */
final class ConcurrentTemporarySet$scheduleRemove$1 implements Runnable {
    final /* synthetic */ Object $element;
    final /* synthetic */ ConcurrentTemporarySet this$0;

    ConcurrentTemporarySet$scheduleRemove$1(ConcurrentTemporarySet concurrentTemporarySet, Object obj) {
        this.this$0 = concurrentTemporarySet;
        this.$element = obj;
    }

    public final void run() {
        synchronized (this.this$0.map) {
            ScheduledFuture scheduledFuture = (ScheduledFuture) this.this$0.map.remove(this.$element);
        }
    }
}
