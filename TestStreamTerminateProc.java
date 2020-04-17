package cd.tij.demo.jdk8Features;

import cd.tij.demo.net.mindview.util.RandomGenerator;
import cd.tij.demo.onjava.Rand;
import cd.work.demo.beanOperate.Book;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static cd.tij.demo.net.mindview.util.Print.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cd.tij.demo.net.mindview.util.PPrint.pprint;

public class TestStreamTerminateProc {
    List<Book> booklist = Arrays.asList(new Book(1,"eckel"),new Book(2,"bruce"),
            new Book(4,"gosling"),new Book(6,"feman"),new Book(10,"minto"),
            new Book(6,"hiller"),new Book(2,"lee"),new Book(11,"ecole"),
            new Book(10,"monto"),new Book(6,"afender"),new Book(2,"eckel"),
            new Book(2,"erik"),new Book(2,"erik"),new Book());
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
    //
    @Test
    public void FindAndMatchProc1() {
        //allMatch/anyMatch/noneMatch/findFirst/findAny/count/max/min
        //全部匹配才返回true
        print(createBuilder()
                .allMatch(x->x.getId()>0));
        //只有一个匹配返回true
        print(createBuilder()
                .anyMatch(x->x.getName().equals("femaner")));
        //没有任何一个匹配返回true
        print(createBuilder()
                .noneMatch(x->x.getName().equals("femaner")));
        Optional<Book> ops = createBuilder().sorted((x,y)->Long.compare(x.getId(),y.getId()))
                .findFirst();//找第一个
        //上行代码相当于min((x,y)->Long.compare(x.getId(),y.getId()))
        //找出任意一个元素
        Optional<Book> ops2 = createBuilder()
                .parallel()
                .sorted((x,y)->Long.compare(x.getId(),y.getId()))
                .filter(x->x.getId()>200)
                .findAny();
        print(ops.orElse(new Book(100,"dadada")));
        print(ops2.orElse(new Book(101,"da_dada")));
    }
    /**Terminate操作：toArray()、forEach(Consumer)、forEachOrdered(Consumer)
     * collect(Collector)、collect(Supplier, BiConsumer, BiConsumer)第一个参数 Supplier 创建了一个新结果集合，
     * 第二个参数 BiConsumer 将下一个元素包含到结果中，
     * 第三个参数 BiConsumer 用于将两个值组合起来。第3个参数是combiner，只有当stream是parallel时才会用，普通的sequential stream不会调它
     * reduce(BinaryOperator)使用 BinaryOperator 来组合所有流中的元素。因为流可能为空，其返回值为 Optional。
     * reduce(identity, BinaryOperator)功能同上，但是使用 identity 作为其组合的初始值。因此如果流为空，identity 就是结果。
     *average()/max() 和 min()/sum()/summaryStatistics()
     */
    @Test
    public void reducingtest1(){
        Book reducingAll = createBuilder()
                .reduce(new Book(0,"stater"),(x,y)->{
                    x.setId(x.getId()+y.getId());
                    x.setName(x.getName()+"||"+y.getName());
                    return x;
                });
        print(reducingAll);

        Set<String> setsname = createBuilder().sorted(Comparator.comparingLong(Book::getId))
                .map(x->x.getName())
                //如果这里sorted()由于需要有顺序，所以使用LinkedHashSet
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        print(setsname);
    }
    @Test
    public void collect_groupbytest1(){
        //初步分组Map<Long,List<Book>
        Map<Long,List<Book>> map1 = createBuilder()
                .sorted(Comparator.comparingLong(Book::getId))
                .collect(Collectors.groupingBy(x->x.getId()));
        print(map1);
        //转换成Map<Long,List<Book>>除了collect_groupbytest1中的分组之外，前用原生collect方法做
        //<R> R collect(Supplier<R> supplier, 返回类型
        //                  BiConsumer<R, ? super T> accumulator, 对每个元素进行处理
        //                  BiConsumer<R, R> combiner);
        Map<Long,List<Book>> map21 = createBuilder()
                .collect(()->new HashMap<Long,List<Book>>(),
                        (hashmap,book)->{
                            //getOrDefault():JDK8中方法，如果map中key是空的，则用defaultvalue
                            List<Book> ls = hashmap.getOrDefault(book.getId(), new ArrayList<>());
                            ls.add(book);
                            hashmap.put(book.getId(), ls);
                        },
                        //HashMap::putAll
                        (x1,y1)->x1.putAll(y1));
        print(map21);
        //继续简洁
        Map<Long,List<Book>> map2 =createBuilder()
                .collect(Collectors.groupingBy(Book::getId, HashMap::new, Collectors.toList()));
        print(map2);
        //见下例子collecttest2中的原生方式
        //再次分组MAP<Long,Map<String,Book>>:统计出每个id下的每个作者的书籍的list
        Map<Long,Map<String,List<Book>>> map3 = createBuilder()
                .collect(Collectors.groupingBy(x->x.getId(),Collectors.groupingBy(y->{
                    if(StringUtils.startsWith(y.getName(),"e"))
                        return "e开头的";
                    else
                        return "其他开头";
                })));
        print(map3);
        //分区
        Map<Boolean,List<Book>> map4 = createBuilder()
                .collect(Collectors.partitioningBy(x->StringUtils.startsWith(x.getName(),"e")));
        print(map4);
    }
    //collector拼接
    @Test
    public void collecttest2(){
        //转换成Map<Long,String>,小心在toMap时候 调用hashmap的merge方法，value为空会报异常
        Map<Long,String> map1 = createBuilder()
                .collect(Collectors.toMap(x->x.getId(),y->{
                    if(y.getName()==null)
                        return " ";
                    return y.getName();
                },(oldvalue,newvalue)->newvalue));
        print(map1);
        //转换成HashSet<Book>
        HashSet<Book> set1 = createBuilder()
                .collect(HashSet<Book>::new,(x1, x2)-> x1.add(x2),(y1,y2)->{});
        //转成HashSet<String>，相当于map之后再Collectors.toSet()
        HashSet<String> set2 = createBuilder()
                .collect(HashSet<String>::new,(x1, x2)-> x1.add(x2.getName()),(y1,y2)->{});
        print(set1);
        print(set2);
        //在拼接字符串的前后prefix和后suffix，字符串之间加入delimiter
        String string1 = createBuilder()
                .map(x->x.getName())
                .collect(Collectors.joining("...","<",">"));
        print(string1);
        //转换成Map<Long,List<Book>>除了collect_groupbytest1中的分组之外，前用原生collect方法做，见上例子
        //<R> R collect(Supplier<R> supplier, 返回类型
        //                  BiConsumer<R, ? super T> accumulator, 对每个元素进行处理
        //                  BiConsumer<R, R> combiner);
        //      转换成Map<Long,HashSet<String>> ::统计每个id下的所有名字类型
        //方案1：初步分组Map<Long,List<Book>后，对Map遍历，然后对list转成Set<String>
        //方案2：直接干试试！！！注意，这个地方和hashcode()和equals有关系，否则会当做不同对象
        Map<Long,HashSet<Book>> map2 = createBuilder()
                .collect(()->new HashMap<Long,HashSet<Book>>(),
                        (hashmap,book)->{
                            //getOrDefault():JDK8中方法，如果map中key是空的，则用defaultvalue
                            HashSet<Book> ls = hashmap.getOrDefault(book.getId(), new HashSet<>());
                            ls.add(book);
                            hashmap.put(book.getId(), ls);
                        },
                        //HashMap::putAll
                        (x1,y1)->x1.putAll(y1));
        print(map2);
    }

}
