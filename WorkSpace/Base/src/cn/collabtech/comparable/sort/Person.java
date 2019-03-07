package cn.collabtech.comparable.sort;

import java.util.Arrays;

public class Person implements Comparable<Person>
{
    String name;
    int age;
    public Person(String name, int age)
    {
        super();
        this.name = name;
        this.age = age;
    }
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    @Override
    public int compareTo(Person p)
    {
        return this.age-p.getAge();
    }
    public static void main(String[] args)
    {
        Person[] people=new Person[]{new Person("xujian", 20),new Person("xiewei", 10)};
        System.out.println("排序前");
        for (Person person : people)
        {
            System.out.print(person.getName()+":"+person.getAge());
        }
        Arrays.sort(people);
        System.out.println("\n排序后");
        for (Person person : people)
        {
            System.out.print(person.getName()+":"+person.getAge());
        }
    }
}