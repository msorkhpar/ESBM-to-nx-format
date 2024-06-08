package edu.nju.ws.seval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.io.AWriter;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.atlas.lib.CharSpace;
import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.writer.WriterStreamRDFPlain;

import edu.nju.ws.seval.utils.ThreeTuple;
/**
 * An example: output triples using Jena API (keep the order)
 * @author qxliu Aug 15, 2017 10:29:29 PM
 *
 */
public class OutputExample {
	public static void main(String[] args) {
//	String s = "a^^b";
//	System.out.println(s.substring(s.indexOf("^^")+2));
		
		File outFile = new File("out.nt");
		List<ThreeTuple<String, String, String>> tupleList = new ArrayList();
		
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/runtime>", "\"6129.0\"^^<http://www.w3.org/2001/XMLSchema#double>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/genre>", "<http://dbpedia.org/resource/Pop_rock>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/genre>", "<http://dbpedia.org/resource/Rock_music>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://xmlns.com/foaf/0.1/name>", "\"Dave Clark's \"Time\": The Album\"@en"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/2000/01/rdf-schema#label>", "\"Time (Dave Clark album)\"@en"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://purl.org/dc/terms/subject>", "<http://dbpedia.org/resource/Category:1986_albums>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://purl.org/dc/terms/subject>", "<http://dbpedia.org/resource/Category:Concept_albums>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/recordLabel>", "<http://dbpedia.org/resource/Capitol_Records>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/recordLabel>", "<http://dbpedia.org/resource/EMI>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://dbpedia.org/ontology/thumbnail>", "<http://commons.wikimedia.org/wiki/Special:FilePath/-_The_Album_1986.jpg?width=300>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://xmlns.com/foaf/0.1/homepage>", "<http://www.time-themusical.com/>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://xmlns.com/foaf/0.1/depiction>", "<http://commons.wikimedia.org/wiki/Special:FilePath/-_The_Album_1986.jpg>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://dbpedia.org/class/yago/PhysicalEntity100001930>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://www.wikidata.org/entity/Q386724>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://dbpedia.org/class/yago/Medium106254669>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://dbpedia.org/class/yago/ConceptAlbums>"));
		tupleList.add(new ThreeTuple<String, String, String>("<http://dbpedia.org/resource/Time_(Dave_Clark_album)>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://dbpedia.org/class/yago/Object100002684>"));
		
		List<Triple> triples = makeTriples(tupleList);
		outputRanked(outFile, triples);
		
	}
	/**
	 * 
	 * @param tupleList (s,p,o)-tuple displayed as string, and 
	 * uri should be placed in a pair of angle brackets, e.g. <http://dbpedia.org/resource/3WAY_FM>
	 * literal like: "Great Ocean Radio"@en  or "3 - Victoria" or "12398"^^<http://www.w3.org/2001/XMLSchema#int>
	 * @author qxliu Aug 16, 2017 10:13:40 AM
	 */
	public static List<Triple> makeTriples(List<ThreeTuple<String, String, String>> tupleList){
		List<Triple> triples = new ArrayList<>();
		for(ThreeTuple<String, String, String> tuple : tupleList){
			String sstr = tuple.getFirst().trim();
			String suri = sstr.substring(sstr.indexOf("<")+1, sstr.lastIndexOf(">"));
			Node s = NodeFactory.createURI(suri);
			
			String pstr = tuple.getSecond();
			String puri = pstr.substring(pstr.indexOf("<")+1, pstr.lastIndexOf(">"));
			Node p = NodeFactory.createURI(puri);
			
			String ostr = tuple.getThird();
			Node o = null;
			if(ostr.contains("\"")){//literal
				int firstIdx = ostr.indexOf("\"");
				int lastIdx = ostr.lastIndexOf("\"");
				String lit = ostr.substring(firstIdx+1, lastIdx);
				if(ostr.contains("@")){
					String lang = ostr.substring(ostr.indexOf("@")+1);
					o = NodeFactory.createLiteral(lit, lang);
				}else if(ostr.contains("^^")){
					String dtstr = ostr.substring(ostr.indexOf("^^")+2);
					String dturi = dtstr.substring(dtstr.indexOf("<")+1, dtstr.lastIndexOf(">"));
					RDFDatatype dtype = new BaseDatatype(dturi);
					o = NodeFactory.createLiteral(lit, dtype);
				}else{
					o = NodeFactory.createLiteral(lit);
				}
			}else{//uri
				String ouri = ostr.substring(ostr.indexOf("<")+1, ostr.lastIndexOf(">"));
				o = NodeFactory.createURI(ouri);
			}
			Triple triple= new Triple(s, p, o);
			triples.add(triple);
		}
		return triples;
	}
	/**
	 * 
	 * @param outFile
	 * @param triples
	 * @author qxliu Aug 16, 2017 10:13:52 AM
	 */
	public static void outputRanked(File outFile, List<Triple> triples){
		try {
			OutputStream outStream = new FileOutputStream(outFile);
			AWriter w = new IndentedWriter(outStream);
//			for(Triple tuple : triples){
//				w.write(tuple.getFirst()+" "+tuple.getSecond()+" "+tuple.getThird()+" .\r\n");
//			}
//			w.flush();
			WriterStreamRDFPlain writer = new WriterStreamRDFPlain(w, CharSpace.UTF8);
			for(Triple triple : triples){
				writer.triple(triple);
			}
			w.flush();
			w.close();
//			writer.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
