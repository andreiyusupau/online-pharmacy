package com.vironit.onlinepharmacy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
List<Integer> list=new ArrayList<>();
list.add(1);
list.add(2);
list.add(3);
        System.out.println(list.stream().map(Object::toString).reduce("",(a, b)->a+" "+b));

    }
}
