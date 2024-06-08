package edu.nju.ws.seval.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Collection;
/**
 * mynote: from EntityValues.java
 * @author qxliu Jul 27, 2017 10:37:03 AM
 *
 */
public class AlgoMeasures {
	/**
	 * 
	 * @param correctT a gold-standard summary
	 * @param algoResult the generated summary
	 * @return [precision, recall, f-measure]
	 * @author Qingxia Liu 2016-9-4 3:43:32 PM
	 */
	public static double[] getPRF(Collection correctT, Collection algoResult){
		Set join = new HashSet(correctT);
		join.retainAll(algoResult);
//		System.out.println("join:\t"+join+"\t"+correctT+"\t"+algoResult);
		double precision = (double)join.size()/algoResult.size();
		double recall = (double)join.size()/correctT.size();
		double fmeasure = (precision==0||recall==0) ?
				0 : (precision*recall)*2/(precision+recall);
//		System.out.println("prf:\t"+precision+"\t"+recall+"\t"+fmeasure);
//		System.out.println("join:\t"+join.size());
		return new double[]{precision, recall, fmeasure};
	}
	
	/**
	 * 
	 * @param correctT
	 * @param algoResult
	 * @return
	 * @author qxliu 2017-7-10 4:34:58 PM
	 */
	public static double getMAP(Collection correctT, List algoResult){
		double aveP = 0;
		int resultSize = algoResult.size();
		int correctSize = 0;//correctT.size();//mj
		for(int i=1; i<=resultSize; i++){
			if(correctT.contains(algoResult.get(i-1))){//Rel(e,u,i)
				double[] prf = getPRF(correctT, algoResult.subList(0, i));
				correctSize++;
				aveP += prf[0];
			}
//			System.out.println(i+"\t"+aveP);
		}
		if(correctSize!=0) aveP /= correctT.size();
		else aveP=0;
		return aveP;
	}
}
