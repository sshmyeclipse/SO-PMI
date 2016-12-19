package org.hut.sentiment.paper.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CorpusSearch {
	private static final String INDEX_PATH = "G:\\data\\论文数据\\newdata\\index";
//	private Analyzer analyzer = new StandardAnalyzer();
	private Directory directory;
	public CorpusSearch() {
		try {
			directory = FSDirectory.open(new File(INDEX_PATH));					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public double search(String term1, String term2) {
//		List<String> result = new ArrayList<String>();
		double result = 0;
		try {
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher is = new IndexSearcher(reader);
			BooleanQuery booleanQueryTerm1 = new BooleanQuery();
			BooleanQuery booleanQueryTerm2 = new BooleanQuery();
			BooleanQuery booleanQuery = new BooleanQuery();
			char[] chars = term1.toCharArray();
			char[] chars2 = term2.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				TermQuery termQuery = new TermQuery(new Term("content", String.valueOf(chars[i])));				
				booleanQueryTerm1.add(termQuery, Occur.MUST);
			}
			for (int i = 0; i < chars2.length; i++) {
				TermQuery termQuery = new TermQuery(new Term("content", String.valueOf(chars2[i])));				
				booleanQueryTerm2.add(termQuery, Occur.MUST);
			}
			booleanQuery.add(booleanQueryTerm1, Occur.MUST);
			booleanQuery.add(booleanQueryTerm2, Occur.MUST);
			TopDocs topDocs = is.search(booleanQuery, Integer.MAX_VALUE);
			ScoreDoc[] scoreDoc = topDocs.scoreDocs;
			result = scoreDoc.length;
			
//			for (ScoreDoc sd : scoreDoc) {
//				Document doc = is.doc(sd.doc);
////				result.add(doc.getField("content").stringValue());
//				System.out.println(doc.getField("content").stringValue());
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	/*
	 * 统计词出现的次数
	 */
	public double termCount(String term) {
		double result = 0;
		try {
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher is = new IndexSearcher(reader);
			BooleanQuery booleanQuery = new BooleanQuery();
			char[] chars = term.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				TermQuery termQuery = new TermQuery(new Term("content", String.valueOf(chars[i])));				
				booleanQuery.add(termQuery, Occur.MUST);
			}
			TopDocs topDocs = is.search(booleanQuery, Integer.MAX_VALUE);
			ScoreDoc[] scoreDoc = topDocs.scoreDocs;
			result = scoreDoc.length;
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	public static void main(String arg[]) {
		String term1 = "中国";
		String term2 = "我";
		CorpusSearch corpusSearch = new CorpusSearch();
		corpusSearch.search(term1, term2);
//		corpusSearch.termCount(term1);
	}

}
