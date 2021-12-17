package com.rgs.render3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rgs.common.Tri;

public class TrisInWorld {

    private static final Set<Tri> triSet = new HashSet<>();

    public static void addTri(Tri tri) {
        triSet.add(tri);
    }

    public static void addTris(List<Tri> tris) {
        triSet.addAll(tris);
    }

    public static Set<Tri> getTris() {
        return triSet;
    }

}
