package com.HackerRank;


import java.util.*;

class myClass implements Comparable<myClass>{
    int a;
    String name;
    public myClass(int a, String name){
        this.a = a;
        this.name = name;
    }

    @Override
    public int compareTo(myClass o) {
        return this.name.compareTo(o.name);
    }
}
class myClass2{
    int a;
    String name;
    public myClass2(int a, String name){
        this.a = a;
        this.name = name;
    }
}
public class Main {

    public static void main(String[] args) {


        ArrayList<myClass> list  = new ArrayList<>();
        myClass m3 = new myClass(3,"dinesh2");
        myClass m1 = new myClass(1,"dinesh");
        myClass m2 = new myClass(2,"divya");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        Collections.sort(list);
        for(myClass m : list){
            System.out.println(m.a + " : "+m.name);
        }

        Comparator<myClass> comp = new Comparator<myClass>() {
            @Override
            public int compare(myClass o1, myClass o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        PriorityQueue q1 = new PriorityQueue(comp);
        myClass m = new myClass(4,"Albert");
        q1.add(m1);
        q1.add(m2);
        q1.add(m3);
        q1.add(m);
        for(Object o : q1){
            myClass temp = (myClass) o;
            System.out.println(temp.name + " : " +temp.a);
        }

    }

}
