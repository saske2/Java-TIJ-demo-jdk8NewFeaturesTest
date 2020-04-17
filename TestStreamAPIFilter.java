package cd.tij.demo.jdk8Features;

import cd.work.demo.beanOperate.Book;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestStreamAPIFilter {
//数据源数据Collection集形成stream1->>>>>>过滤、切分等操作->>>>新的数据stream2，但是对原Collection不会造成影响
// 集合是指数据，但是流只是计算，Stream不会存储对象，Stream不会改变对象，Stream操作是延时的
//Stream的三个操作步骤：1.创建一个Stream；2.中间操作；3.终端操作
    @Test
    public void createStreamAPI(){
        //创建Stream1
        List<String> list = new ArrayList<>();
        Stream s1 = list.stream();
        //创建2
        Book[] books = new Book[10];
        Stream<Book> bookStream = Arrays.stream(books);
        //创建3
        Stream<Book> bookStream2 = Stream.of(new Book(),new Book(),new Book());
        //BufferedReader s = new BufferedReader();
        //        s.lines();
    //        Files a = new Files("a.txt");
    }
    @Test
    public void creaStreamiteral(){
        //创建无限流4--程序无限执行
        Book b1 = new Book();
        b1.setId(1);
        Stream<Book> bookiteral = Stream.iterate(b1,x->{x.setName("name");return x;});
        bookiteral.limit(10).forEach(System.out::println);
        //创建5无限流
        Stream<Double> bookgeneral = Stream.generate(()->Math.random());//由于random()的传入类型和返回类型和函数式接口一样，格式2
        bookgeneral.limit(3).forEach(System.out::println);
    }
    @Test
    public void filterStream1(){
        List<Book> boolist = Arrays.asList(new Book(1,"jame"),
                new Book(2,"jim"),
                new Book(3,"frank"),
                new Book(4,"cormen"),
                new Book(5,"bruce"),
                new Book(6,"jane"),
                new Book(7,"jason"),
                new Book(8,"justim"));
        boolist.stream().filter(x-> StringUtils.startsWith(x.getName(),"j")).
                forEach(System.out::println);
        System.out.println(boolist.size());

    }
    public void StreamMiddle1(){
        Consumer<String> printconsumer = System.out::println;
//        String pstring = Integer.parseInt("5")+1+"";
//        printconsumer.accept(pstring);
    }
}
