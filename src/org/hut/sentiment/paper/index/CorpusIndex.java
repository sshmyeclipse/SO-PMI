package org.hut.sentiment.paper.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.hut.sentiment.paper.util.FileUtils;

public class CorpusIndex {
	
//	private static final String CORPUS_PATH =  CorpusIndex.class.getClassLoader().getResource("").getPath();//语料路径
	private static final String CORPUS_PATH = "G:\\data\\论文数据\\newdata\\Sentence.txt";
	private static final String INDEX_PATH = "G:\\data\\论文数据\\newdata\\index";
	private Analyzer analyzer = new StandardAnalyzer();
	
	public CorpusIndex() {
		initIndex();
	}

	private void initIndex() {
		try {
			Directory directory = FSDirectory.open(new File(INDEX_PATH));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
			IndexWriter iw = new IndexWriter(directory, config);
			List<String> lines = FileUtils.load(CORPUS_PATH, "utf-8");
			List<Document> documents = new ArrayList<Document>();
			//创建索引
			if (lines != null && lines.size() > 0) {
				for(String line : lines) {
					Document document = new Document();	
					IndexableField textField = new TextField("content", line, Field.Store.YES);
					document.add(textField);
					documents.add(document);
				}
				iw.addDocuments(documents);
				iw.commit();
				iw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		CorpusIndex corpusIndex = new CorpusIndex();
	}
	
}
