package edu.nju.ws.seval.utils;

import java.util.ArrayList;
import java.util.HashSet;


public class TwoTuple<A,B> {
	private  A first;
	private  B second;
	
	public TwoTuple(A a, B b){
		first = a;
		second = b;
	}
	
	public A getFirst(){
		return this.first;
	}
	
	public B getSecond(){
		return this.second;
	}
	
	public void setFirst(A a){
		this.first = a;
	}
	
	public void setSecond(B b){
		this.second = b;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoTuple<A,B> other = (TwoTuple<A,B>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	public String toString(){
		return "("+first+", "+second+")";
	}
	
	public static void main(String[] args) {
//		String a ="http://www.w3.org/2000/01/rdf-schema#comment";
//			String b= "Nanjing is the capital of Jiangsu province in China and has a prominent place in Chinese history and culture, having been the capital of China on several occasions. Its present name means \"Southern Capital\" and was widely romanized as Nankin and Nanking until the Pinyin language reform, after which Nanjing was gradually adopted as the standard spelling of the city's name in most languages that use the Roman alphabet.";
//		String c="Nanjing is the capital of Jiangsu province in eastern China and has a prominent place in Chinese history and culture, having been the capital of China on several occasions. Its present name means \"Southern Capital\" and was widely romanized as Nankin and Nanking until the Pinyin language reform, after which Nanjing was gradually adopted as the standard spelling of the city's name in most languages that use the Roman alphabet.";
//		String d=" Nanjing is the capital of Jiangsu province in China and has a prominent place in Chinese history and culture, having been the capital of China on several occasions. Its present name means \"Southern Capital\" and was widely romanized as Nankin and Nanking until the Pinyin language reform, after which Nanjing was gradually adopted as the standard spelling of the city's name in most languages that use the Roman alphabet.";	
//		TwoTuple<String,String> tuple = new TwoTuple<String,String>(a,c.trim());
//		TwoTuple<String,String> tuple1 = new TwoTuple<String,String>(a,b.trim());
//		TwoTuple<String,String> tuple2 = new TwoTuple<String,String>(a,d.trim());
//		ArrayList<TwoTuple<String,String>> array = new ArrayList<TwoTuple<String,String>>();
//		HashSet<TwoTuple<String,String>> set= new HashSet<TwoTuple<String,String>>();
//		if(!set.contains(tuple)){
//			set.add(tuple);
//			array.add(tuple);
//		}
//		System.out.println(b.equals(d));
//		System.out.println(tuple.hashCode());
//		System.out.println(tuple1.hashCode());
//		System.out.println(tuple2.hashCode());
		String a ="Nanjing is the capital of Jiangsu province in China and has a prominent place in Chinese history and culture, having been the capital of China on several occasions. Its present name means \"Southern Capital\" and was widely romanized as Nankin and Nanking until the Pinyin language reform, after which Nanjing was gradually adopted as the standard spelling of the city's name in most languages that use the Roman alphabet. Located in the lower Yangtze River drainage basin and Yangtze River Delta economic zone, Nanjing has long been one of China's most important cities. It is recognized as one of the Four Great Ancient Capitals of China. It was the capital of Sun Quan's Wu during the Three Kingdoms Period and the capital of the Republic of China prior to its flight to Taiwan during the Chinese Civil War. Nanjing is also one of the fifteen sub-provincial cities in the People's Republic of China's administrative structure, enjoying jurisdictional and economic autonomy only slightly less than that of a province. Nanjing has long been a national center of education, research, transport networks, and tourism. The city will host the 2014 Summer Youth Olympics. With an urban population of over seven million (2011), Nanjing is the second-largest commercial center in the East China region after Shanghai. It has been ranked seventh in the evaluation of \"Cities with Strongest Comprehensive Strength\" issued by the National Statistics Bureau, and second in the evaluation of cities with most sustainable development potential in the Yangtze River Delta. It has also been awarded the title of 2008 Habitat Scroll of Honor of China, Special UN Habitat Scroll of Honour Award and National Civilized City.";
		String b ="Nanjing is the capital of Jiangsu province in China and has a prominent place in Chinese history and culture, having been the capital of China on several occasions. Its present name means \"Southern Capital\" and was widely romanized as Nankin and Nanking until the Pinyin language reform, after which Nanjing was gradually adopted as the standard spelling of the city's name in most languages that use the Roman alphabet. Located in the lower Yangtze River drainage basin and Yangtze River Delta economic zone, Nanjing has long been one of China's most important cities. It is recognized as one of the Four Great Ancient Capitals of China. It was the capital of Sun Quan's Wu during the Three Kingdoms Period and the capital of the Republic of China prior to its flight to Taiwan during the Chinese Civil War. Nanjing is also one of the fifteen sub-provincial cities in the People's Republic of China's administrative structure, enjoying jurisdictional and economic autonomy only slightly less than that of a province. Nanjing has long been a national centre of education, research, transport networks and tourism. The city will host the 2014 Summer Youth Olympics. With an urban population of over seven million (2011), Nanjing is the second-largest commercial centre in the East China region after Shanghai. It has been ranked seventh in the evaluation of \"Cities with Strongest Comprehensive Strength\" issued by the National Statistics Bureau, and second in the evaluation of cities with most sustainable development potential in the Yangtze River Delta. It has also been awarded the title of 2008 Habitat Scroll of Honour of China, Special UN Habitat Scroll of Honour Award and National Civilized City.";
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		
	}
}
