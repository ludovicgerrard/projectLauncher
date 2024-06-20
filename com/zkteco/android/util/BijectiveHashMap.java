package com.zkteco.android.util;

import android.os.Parcel;
import android.os.Parcelable;
import com.zkteco.util.BijectiveMap;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BijectiveHashMap<K, V> implements BijectiveMap<K, V>, Parcelable {
    public static final Parcelable.Creator<BijectiveHashMap> CREATOR = new Parcelable.Creator<BijectiveHashMap>() {
        public BijectiveHashMap createFromParcel(Parcel parcel) {
            return new BijectiveHashMap(parcel);
        }

        public BijectiveHashMap[] newArray(int i) {
            return new BijectiveHashMap[i];
        }
    };
    protected Map<V, K> inverseMap = new HashMap();
    protected Map<K, V> map = new HashMap();

    public int describeContents() {
        return 0;
    }

    public BijectiveHashMap() {
    }

    public BijectiveHashMap(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void clear() {
        this.map.clear();
        this.inverseMap.clear();
    }

    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    public V get(Object obj) {
        return this.map.get(obj);
    }

    public K getKey(V v) {
        return this.inverseMap.get(v);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public V put(K k, V v) {
        this.inverseMap.put(v, k);
        return this.map.put(k, v);
    }

    public void putAll(Map<? extends K, ? extends V> map2) {
        this.map.putAll(map2);
    }

    public void readFromParcel(Parcel parcel) {
        this.map = (Map) parcel.readSerializable();
        this.inverseMap = (Map) parcel.readSerializable();
    }

    public V remove(Object obj) {
        this.inverseMap.remove(this.map.get(obj));
        return this.map.remove(obj);
    }

    public int size() {
        return this.map.size();
    }

    public Collection<V> values() {
        return this.map.values();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable((Serializable) this.map);
        parcel.writeSerializable((Serializable) this.inverseMap);
    }
}
