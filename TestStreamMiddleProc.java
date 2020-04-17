package cd.tij.demo.jdk8Features;

import cd.tij.demo.net.mindview.util.RandomGenerator;
import cd.tij.demo.onjava.Rand;
import cd.work.demo.beanOperate.Book;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static cd.tij.demo.net.mindview.util.PPrint.*;
import static cd.tij.demo.net.mindview.util.Print.print;

public class TestStreamMiddleProc {
    List<Book> booklist = Arrays.asList(new Book(1,"eckel"),new Book(2,"bruce"),
            new Book(4,"gosling"),new Book(6,"feman"),new Book(10,"minto"),
            new Book(6,"hiller"),new Book(2,"lee"),new Book(11,"ecole"),
            new Book(10,"monto"),new Book(),new Book(2,"eckel"),new Book());
    public Stream<Book> createBuilder(){
        Stream.Builder<Book> builder = Stream.builder();
        for(Iterator<Book> it = booklist.iterator();it.hasNext();) {
            builder.add(it.next());
        }
        return builder.build();
    }
    List<String> somewords = Arrays.asList("eckel","bruce","gosling","feman","eckel","gosling");
    public Stream<String> createRandomString(){
        Stream.Builder<String> sbuilder = Stream.builder();
        somewords.forEach(sbuilder::add);
        return sbuilder.build();
    }
    //stream中间操作peek()，sorted()，skip(10)，limit(10)，distinct()(distinct是根据哈细值来区分的！！)，filter(Predicate)
    //map(Function),mapToInt(ToIntFunction),mapToLong(ToLongFunction),mapToDouble(ToDoubleFunction)
    //flatMap(Function),flatMapToInt(Function) 产生 IntStream 时使用.....
    @Test
    public void MiddleProc1() {
        System.out.println(booklist);
        //创建Stream1
        Stream<Book> bookStream = createBuilder();
        bookStream.filter((x)-> StringUtils.startsWith(x.getName(),"e"))
//                .limit(1)
                .map(x->{
                    Long id = x.getId();
                    x.setId(id+1);
                    x.setName(x.getName().toUpperCase());
                    return x;
                })
                //和hashcode()以及equals相关
                .distinct()
                .forEach(System.out::println);
        System.out.println(booklist);
        List<Book> copylist = new ArrayList<Book>();
        copylist.addAll(booklist);
        System.out.println("copylist:"+copylist);
        //这种类型的对象,并且在map中对原对象修改了，改变了原来list的值
        copylist.stream()
                .map(x->{
                    Long id = x.getId();
                    x.setId(id+100);
                    return x;
                })
                .forEach(System.out::println);
        System.out.println(copylist);
        copylist.stream()
                .sorted((x,y)->{
                    if(x.getName()==null)
                        return -1;
                    return x.getName().toLowerCase().compareTo(y.getName().toLowerCase());
                }).forEach(System.out::println);
        System.out.println("sorted copylist:"+copylist);

        //String[] stringarray = new String[]{"ali","ara","ada","bleek","block","bleek","brain","zeek"};
        // 数组并没有改变起对象值
//        String[] stringarray = new String[]{"ali","ara","ada","bleek","block","bleek","brain","zeek"};
//        pprint(stringarray);
//        Stream<String> stringStream = Arrays.stream(stringarray)
//                .map(x->x.substring(0,2));
//        stringStream.forEach(System.out::println);
//        pprint(stringarray);

    }
    //JDK8
//    1. 操作调用的是map.merge方法,该方法遇到value为null的情况会报npe,
//          即使你使用的是hashMap可以接受null值,也照样报.搞不懂这里为什么这样设计.
//    2. 未指定冲突合并策略,也就是第三个参数BinaryOperator<U> mergeFunction时
//          遇到重复的key会直接抛IllegalStateException,因此需要注意.
    @Test
    public void MiddleProc2(){
        String[] stringarray = new String[]{"ali","ara","ada","bleek","block","bleek","brain","zeek","zeek"};
        //List<String>
        List<String> lsttring = new ArrayList<>();
        for(String a:stringarray){
            lsttring.add(a);
        }
        Stream<String> stream = lsttring.stream();
        pprint(lsttring);
        Map<String,String> map = lsttring.stream()
                .map(x->x.substring(0,3))
                .distinct()
                //Collectors.toMap(Function<? super T, ? extends K> keyMapper,Function<? super T, ? extends U> valueMapper)
                //如果其ke相同，则报错
                .collect(Collectors.toMap(x->x.toUpperCase(), x-> x.substring(0,1)));
        System.out.println(map);
        Map<String,String> map1 = lsttring.stream()
                .map(x->x.substring(0,3))
//                .distinct()
                //如果映射的key包含重复（根据Object.equals(Object) ），
                // 则值映射函数应用于每个相等的元素，并使用提供的合并函数合并结果。
                .collect(Collectors.toMap(x->x.toUpperCase(), x-> x.toLowerCase(),(oldkey,newkey)->oldkey+newkey));
        System.out.println(map1);
        Map<String,Long> map2 = createBuilder()
                .collect(Collectors.toMap(x->x.getName(),y->y.getId(),(oldkey,newkey)->newkey));
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Map<Long,String> map2_1 = createBuilder()
                .collect(Collectors.toMap(y->y.getId(),x->{
                    if(x.getName()==null)
                        return " ";
                    return x.getName();
                },(oldkey, newkey)->newkey));
        System.out.println(map2);
        System.out.println(map2_1);
        List<Book> list1 = createBuilder()
//                .map(x->x.getName())
                .collect(Collectors.toList());
        System.out.println(list1);
        Set<String> set1 = createBuilder()
                .map(x->x.getName())
                .collect(Collectors.toSet());
        System.out.println(set1);

        //collect的方法：<R> R collect(Supplier<R> supplier,
        //                  BiConsumer<R, ? super T> accumulator,
        //                  BiConsumer<R, R> combiner);
        Book b1 =  createBuilder()
                .collect(()-> new Book(0,"fff"),(x,y)->{
                    x.setName(x.getName()+y.getName());
                },(x1,y1)->{});
        print(b1);


    }

}
