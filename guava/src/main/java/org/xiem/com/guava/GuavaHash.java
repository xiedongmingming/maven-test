package org.xiem.com.guava;

public class GuavaHash {// http://ifeve.com/google-guava-hashing/

	// Java内建的散列码[hash
	// code]概念被限制为32位，并且没有分离散列算法和它们所作用的数据，因此很难用备选算法进行替换。此外，使用Java内
	// 建方法实现的散列码通常是劣质的，部分是因为它们最终都依赖于JDK类中已有的劣质散列码。
	//
	// Object.hashCode往往很快，但是在预防碰撞上却很弱，也没有对分散性的预期。这使得它们很适合在散列表中运用，因为额外碰撞只会带来轻微的性
	// 能损失，同时差劲的分散性也可以容易地通过再散列来纠正（Java中所有合理的散列表都用了
	// 再散列方法）。然而，在简单散列表以外的散列运用中，Object.hashCode几乎总是达不到要求——因此，有了com.google.common.hash包。
	// 散列包的组成
	// 在这个包的Java doc中，我们可以看到很多不同的类，但是文档中没有明显地表明它们是怎样
	// 一起配合工作的。在介绍散列包中的类之前，让我们先来看下面这段代码范例：
	//
	// 1
	// HashFunction hf = Hashing.md5();
	// 2
	// HashCode hc = hf.newHasher()
	// 3
	// .putLong(id)
	// 4
	// .putString(name, Charsets.UTF_8)
	// 5
	// .putObject(person, personFunnel)
	// 6
	// .hash();
	// HashFunction
	//
	// HashFunction是一个单纯的（引用透明的）、无状态的方法，它把任意的数据块映射到固定数目的位指，并且保证相同的输入一定产生相同的输出，不同的输入尽可能产生不同的输出。
	//
	// Hasher
	//
	// HashFunction的实例可以提供有状态的Hasher，Hasher提供了流畅的语法把数据添加到散列运算，然后获取散列值。Hasher可以接受所有原生类型、字节数组、字节数组的片段、字符序列、特定字符集的字符序列等等，或者任何给定了Funnel实现的对象。
	//
	// Hasher实现了PrimitiveSink接口，这个接口为接受原生类型流的对象定义了fluent风格的API
	//
	// Funnel
	//
	// Funnel描述了如何把一个具体的对象类型分解为原生字段值，从而写入PrimitiveSink。比如，如果我们有这样一个类：
	//
	// 1
	// class Person {
	// 2
	// final int id;
	// 3
	// final String firstName;
	// 4
	// final String lastName;
	// 5
	// final int birthYear;
	// 6
	// }
	// 它对应的Funnel实现可能是：
	//
	// 01
	// Funnel<Person> personFunnel = new Funnel<Person>() {
	// 02
	// @Override
	// 03
	// public void funnel(Person person, PrimitiveSink into) {
	// 04
	// into
	// 05
	// .putInt(person.id)
	// 06
	// .putString(person.firstName, Charsets.UTF_8)
	// 07
	// .putString(person.lastName, Charsets.UTF_8)
	// 08
	// .putInt(birthYear);
	// 09
	// }
	// 10
	// }
	// 注：putString(“abc”, Charsets.UTF_8).putString(“def”,
	// Charsets.UTF_8)完全等同于putString(“ab”, Charsets.UTF_8).
	// putString(“cdef”,
	// Charsets.UTF_8)，因为它们提供了相同的字节序列。这可能带来预料之外的散列冲突。增加某种形式的分隔符有助于消除散列冲突。
	//
	// HashCode
	//
	// 一旦Hasher被赋予了所有输入，就可以通过hash()方法获取HashCode实例（多次调用hash()方法的结果是不确定的）。HashCode可以通过asInt()、
	// asLong()、asBytes()方法来做相等性检测，此外，writeBytesTo(array, offset,
	// maxLength)把散列值的前maxLength字节写入字节数组。

	// 布鲁姆过滤器
	// 布鲁姆过滤器是哈希运算的一项优雅运用它可以简单地基于OBJECT.HASHCODE()实现.简而言之布鲁姆过滤器是一种概率数据结构,
	// 它允许你检测某个对象是一定不在过滤器中还是可能已经添加到过滤器了.布鲁姆过滤器的维基页面对此作了全面的介绍,同时我们推荐GITHUB中的一个教程.
	// https://en.wikipedia.org/wiki/Bloom_filter
	// https://llimllib.github.io/bloomfilter-tutorial/
	// GUAVA散列包有一个内建的布鲁姆过滤器实现你只要提供FUNNEL就可以使用它.你可以使用
	// create(Funnel funnel, int expectedInsertions, double
	// falsePositiveProbability)
	// 方法获取
	// BloomFilter<T>
	// 缺省误检率[falsePositiveProbability]为3%。BloomFilter<T>提供了boolean
	// mightContain(T) 和void put(T)，它们的含义都不言自明了。
	//
	// BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500,
	// 0.01);
	// for(Person friend : friendsList) {
	// friends.put(friend);
	// }
	//
	// // 很久以后
	// if (friends.mightContain(dude)) {
	// //dude不是朋友还运行到这里的概率为1%
	// //在这儿，我们可以在做进一步精确检查的同时触发一些异步加载
	// }
	// Hashing类
	// Hashing类提供了若干散列函数，以及运算HashCode对象的工具方法。
	//
	// 已提供的散列函数
	//
	// md5() murmur3_128() murmur3_32() sha1()
	// sha256() sha512() goodFastHash(int bits)
	// HashCode运算
	//
	// 方法 描述
	// HashCode combineOrdered( Iterable<HashCode>)
	// 以有序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能是顺序相等的
	// HashCode combineUnordered( Iterable<HashCode>)
	// 以无序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能在某种排序下是相等的
	// int consistentHash( HashCode, int buckets)
	// 为给定的”桶”大小返回一致性哈希值。当”桶”增长时，该方法保证最小程度的一致性哈希值变化。详见一致性哈希。

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
