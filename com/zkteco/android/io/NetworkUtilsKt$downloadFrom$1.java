package com.zkteco.android.io;

import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.kittinunf.result.Result;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007H\n¢\u0006\u0002\b\n"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Lcom/github/kittinunf/fuel/core/Request;", "<anonymous parameter 1>", "Lcom/github/kittinunf/fuel/core/Response;", "result", "Lcom/github/kittinunf/result/Result;", "", "Lcom/github/kittinunf/fuel/core/FuelError;", "invoke"}, k = 3, mv = {1, 1, 9})
/* compiled from: NetworkUtils.kt */
final class NetworkUtilsKt$downloadFrom$1 extends Lambda implements Function3<Request, Response, Result<? extends byte[], ? extends FuelError>, Unit> {
    final /* synthetic */ File receiver$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NetworkUtilsKt$downloadFrom$1(File file) {
        super(3);
        this.receiver$0 = file;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        invoke((Request) obj, (Response) obj2, (Result<byte[], FuelError>) (Result) obj3);
        return Unit.INSTANCE;
    }

    public final void invoke(Request request, Response response, Result<byte[], FuelError> result) {
        Intrinsics.checkParameterIsNotNull(request, "<anonymous parameter 0>");
        Intrinsics.checkParameterIsNotNull(response, "<anonymous parameter 1>");
        Intrinsics.checkParameterIsNotNull(result, "result");
        if (result instanceof Result.Success) {
            FilesKt.writeBytes(this.receiver$0, (byte[]) ((Result.Success) result).getValue());
        } else if (result instanceof Result.Failure) {
            throw new IOException("Error downloading file " + ((FuelError) ((Result.Failure) result).getError()));
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }
}
