package com.zkteco.android.io;

import com.github.kittinunf.fuel.FuelKt;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.kittinunf.result.Result;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.File;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0002\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001\u001a\u0017\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u0006*\u00020\u0001¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t*\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b\u001aA\u0010\f\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u0006*\u00020\u00012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u00060\u000e2\u0014\u0010\u000f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\u0002\u0010\u0011\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"TAG", "", "downloadFrom", "Ljava/io/File;", "url", "getRemoteObjectFromHttp", "T", "(Ljava/lang/String;)Ljava/lang/Object;", "getResponse", "Lcom/zkteco/android/io/ResponseData;", "parameters", "", "httpGet", "successBlock", "Lkotlin/Function1;", "errorBlock", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: NetworkUtils.kt */
public final class NetworkUtilsKt {
    private static final String TAG = "NetworkUtils";

    public static final <T> T httpGet(String str, Function1<? super String, ? extends T> function1, Function1<? super String, Unit> function12) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(function1, "successBlock");
        Intrinsics.checkParameterIsNotNull(function12, "errorBlock");
        Result result = (Result) Request.responseString$default(FuelKt.httpGet$default(str, (List) null, 1, (Object) null), (Charset) null, 1, (Object) null).component3();
        if (result instanceof Result.Success) {
            return function1.invoke((String) ((Result.Success) result).getValue());
        }
        if (result instanceof Result.Failure) {
            FuelError fuelError = (FuelError) ((Result.Failure) result).getError();
            LogUtilsKt.log$default((Object) "Error obtaining file: " + fuelError, (String) null, 1, (Object) null);
            function12.invoke(fuelError.getMessage());
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> T getRemoteObjectFromHttp(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        return httpGet(str, NetworkUtilsKt$getRemoteObjectFromHttp$1.INSTANCE, NetworkUtilsKt$getRemoteObjectFromHttp$2.INSTANCE);
    }

    public static final File downloadFrom(File file, String str) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        Intrinsics.checkParameterIsNotNull(str, ImagesContract.URL);
        FuelKt.httpGet$default(str, (List) null, 1, (Object) null).response((Function3<? super Request, ? super Response, ? super Result<byte[], FuelError>, Unit>) new NetworkUtilsKt$downloadFrom$1(file));
        return file;
    }

    public static final ResponseData getResponse(String str, List<String> list) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(list, "parameters");
        Iterable<IndexedValue> withIndex = CollectionsKt.withIndex(list);
        Map linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(withIndex, 10)), 16));
        for (IndexedValue indexedValue : withIndex) {
            Pair pair = TuplesKt.to("param" + indexedValue.component1(), (String) indexedValue.component2());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Result result = (Result) Request.responseString$default(FuelKt.httpGet(str, (List<? extends Pair<String, ? extends Object>>) MapsKt.toList(linkedHashMap)), (Charset) null, 1, (Object) null).component3();
        ResponseData responseData = new ResponseData((String) null, (String) null);
        if (result instanceof Result.Success) {
            responseData.setData((String) ((Result.Success) result).getValue());
        } else if (result instanceof Result.Failure) {
            responseData.setError(((FuelError) ((Result.Failure) result).getError()).getMessage());
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return responseData;
    }
}
