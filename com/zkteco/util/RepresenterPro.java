package com.zkteco.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u001c\u0010\u0002\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J.\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014R$\u0010\u0002\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/zkteco/util/RepresenterPro;", "Lorg/yaml/snakeyaml/representer/Representer;", "ignoreClassField", "", "Lkotlin/reflect/KClass;", "", "", "(Ljava/util/Map;)V", "ignore", "", "javaBean", "", "property", "Lorg/yaml/snakeyaml/introspector/Property;", "representJavaBeanProperty", "Lorg/yaml/snakeyaml/nodes/NodeTuple;", "propertyValue", "customTag", "Lorg/yaml/snakeyaml/nodes/Tag;", "HelpersUtils"}, k = 1, mv = {1, 1, 9})
/* compiled from: RepresenterPro.kt */
public final class RepresenterPro extends Representer {
    private final Map<KClass<?>, Collection<String>> ignoreClassField;

    public RepresenterPro(Map<KClass<?>, ? extends Collection<String>> map) {
        Intrinsics.checkParameterIsNotNull(map, "ignoreClassField");
        this.ignoreClassField = map;
    }

    /* access modifiers changed from: protected */
    public NodeTuple representJavaBeanProperty(Object obj, Property property, Object obj2, Tag tag) {
        Intrinsics.checkParameterIsNotNull(obj, "javaBean");
        Intrinsics.checkParameterIsNotNull(property, "property");
        if (ignore(obj, property)) {
            return null;
        }
        return super.representJavaBeanProperty(obj, property, obj2, tag);
    }

    private final boolean ignore(Object obj, Property property) {
        Map<KClass<?>, Collection<String>> map = this.ignoreClassField;
        Map linkedHashMap = new LinkedHashMap();
        for (Map.Entry next : map.entrySet()) {
            if (((KClass) next.getKey()).isInstance(obj)) {
                linkedHashMap.put(next.getKey(), next.getValue());
            }
        }
        if (linkedHashMap.isEmpty()) {
            return false;
        }
        for (Map.Entry value : linkedHashMap.entrySet()) {
            if (((Collection) value.getValue()).contains(property.getName())) {
                return true;
            }
        }
        return false;
    }
}
