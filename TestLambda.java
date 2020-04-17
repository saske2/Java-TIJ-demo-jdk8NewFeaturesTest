package cd.tij.demo.jdk8Features;

import cd.work.demo.beanOperate.Entity1;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestLambda {
    List<Integer> list = new ArrayList<Integer>();
    @Test
    public void testlambda1(){
        System.out.println("lambda1");
//        Comparator<Integer> comparator = new Comparator<Integer>(){
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return Integer.compare(o1,o2);
//            }
//        };
        Comparator<Integer> comparator1 = (x,y)->Integer.compare(x,y);
        list.add(1);
        list.add(3);
        list.forEach(System.out::println);
 //       Proxy.newProxyInstance(TestLambda.class.getClassLoader(),Comparator.class.getInterfaces(),(x,y,z)->y.invoke(x,z));
    }
    //考虑这样一种场景，假如对一个List<Entity>数据做筛选处理，通常的方式可以写一个接口
    // 比如Iprocess{ public T process(T el); }，另外定义一个函数传递处理接口
    // public List<Entity> filter(List<Entity> entitylist,Iprocess iproc){对entitylist遍历{newlist.add(iproc.process(entitylist));}return newlist;}
    //于是我们调用的时候filter(List<Entity> entitylist,new Iprocess(){ process(List el){xxxxx;}});这样用匿名内部类去处理，于是这种情况下
    //使用如果每个过滤器的代码量很少，想避免每个实现类都去new去实现，lambda表达式让调用代码更简洁
    //调用时List<Entity> list = filter(List entitylist,(e)->e.getsalary()>=5000);list.forEach(System.out::println);

    //Lambda表达式语法:左侧：参数列表。右侧：表达式中所要执行的功能，即为lambda体，即省略了实现类中的@Override和methodName and {}!!!
    //Lambda需要函数式接口支持，函数中只有一个抽象方法才叫函数式接口，可以使用注解@FunctionalInterface修饰，检查是否是函数式接口
    //在java.util.function包下各种类型函数式接口,如void Consumer(T)/T Function(F)/boolean Predicate(T)/T Supplier()/T BiFunction(V,R)
    //语法格式1：无参数、无返回值 ()->System.out.println();
    @Test
    public void gram1(){
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("implment runnable");
//            }
//        };
        Runnable run = ()-> {System.out.println("implent runnable1");};//接口就一个方法，编译器肯定能确定的了哪个方法
        //Runnable run = ()-> System.out.println("implent runnable1");//更加简化，一行代码情况下{}可以不写
        run.run();
    }
    //语法格式2：一个参数,无返回值
    //语法格式2：一个参数，参数的小括号可以不写！！
    @Test
    public void gram2(){
        //消费类型接口Consumer,消费T返回void
        Consumer<String> c1 = (x)-> System.out.println("Consumer_lambda_argX:"+x);
        c1.accept("gram2-1");
        //进化——一个参数，参数的小括号可以不写！！
        Consumer<String> c2 = x->System.out.println("Consumer_lambda_argX:"+x);
        c1.accept("gram2-2");
    }
    //语法格式3：两个以上参数，lambda体多条语句，有返回值,必须要使用大括号
    //语法格式4：若lambda体中只有一条语句，return and {}都可以不写
    //语法格式5：lambda表达式的参数类型可以省略不写，因为jvm可以通过上下文猜出参数的参数类型，称为类型推断
    @Test
    public void gram3(){
        Comparator<Integer> c3 = (t1,t2)->{
            System.out.println("comparator_lambda");
            return Integer.compare(t1,t2);
        };
        //若lambda体中只有一条语句，return and {}都可以不写
        Comparator<Double> c4 = (x,y)->Double.compare(x,y);
    }
    //使用案例， List<Entity> filter(List<Entity> entitylist,Iprocess iproc)对list做操作，filter(list,x->x.process(list)).forEach(System.out::println)
    //练习
    @Test
    public void practis1(){
        List<String> list2 = new ArrayList<>();
        list2.add("jame");
        list2.add("lancy");
        list2.add("trump");
        list2.add("hilary");
        list2.add("ali");
        list2.add("zara");
        list2.add("sea");
        Collections.sort(list2,(x,y)->x.compareTo(y));
        list2.forEach(System.out::println);
//        list2.forEach((x)->System.out.println(x));
        List<String> list3 = new ArrayList<>();
        list2.forEach(x->list3.add(x = x.length()<=3?"":x.toUpperCase()));
        list3.forEach(System.out::println);
    }
    //方法引用：若lambda体中，有方法已经实现了，则可以使用方法引用——为lambda表达式另外表达形式
    //主要三种语法格式：格式1：对象::实例方法名//条件：函数式接口中的的参数列表和返回类型，必须和实例方法名参数列表和返回类型相同！！！
    //格式2：类::静态方法名//条件：函数式接口中的的参数列表和返回类型，必须和静态方法的参数列表和返回类型相同！！！
    //格式3：类::实例方法名//条件：第一个参数的必须是第二个参数的调用者！！！
    //所以总结下来，格式2和格式3其实看起来一样，但是格式2是静态方法，格式3是实例方法，格式3条件更为苛刻
    @Test
    public void methodtest1(){
        //格式1：
//        Consumer c1 = (x)->System.out.println(x);
        Consumer c1 = System.out::println;
        Supplier<String> s1 = ()->new String("supplier method reference");
        //继续简化,相当于return "xxxxxx";
        Supplier<String> s2 = ()-> "more simple than s1";
        c1.accept(s1.get());
        c1.accept(s2.get());
        //格式2：
        Comparator<Integer> cp1 = Integer::compare;
    }
    @Test
    public void methodtes2(){
        //类名::静态方法名
        BiPredicate<String,String> biPredicate1 = StringUtils::equals;
        //类::实例方法名
        BiPredicate<String,String> biPredicate2 = (X,Y)->X.equals(Y);
        //进化写法——类::实例方法名
        //条件：参数列表中，第一个参数的必须是第二个参数的调用者，第二个参数是第一个参数的参数！！！
        biPredicate2 = String::equals;
        System.out.println(biPredicate1.test("BiPredicate","biPredicate"));
        System.out.println(biPredicate2.test("BiPredicate1","biPredicate2"));
    }
    //构造器引用:ClassName::new
    @Test
    public void constructuretest1(){
        Supplier<Entity1> splr1 = ()->new Entity1();
        //构造器引用
        //但是确定其具体调用哪个构造器，需要根据函数式接口以及构造器存不存在来判断。
        Supplier<Entity1> splr2 = Entity1::new;
        Consumer<Entity1> consu_printer = System.out::println;
        consu_printer.accept(splr1.get());
    }
    //数组引用：Type::new
    @Test
    public void arraysTest1(){
        Function<Integer,String[]> function = (x)->new String[x];
        String[] strings = function.apply(10);
        System.out.println(strings.length);
        //类似于函数引用的第三点实际上是对象a调用参数对象b
        Function<Integer,String[]> function1 = String[]::new;
        System.out.println(function1.apply(11).length);
    }
}
