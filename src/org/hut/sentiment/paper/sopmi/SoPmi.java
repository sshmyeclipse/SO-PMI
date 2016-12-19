package org.hut.sentiment.paper.sopmi;

import java.util.ArrayList;
import java.util.List;

import org.hut.sentiment.paper.search.CorpusSearch;
import org.hut.sentiment.paper.util.FileUtils;

public class SoPmi {
	private CorpusSearch corpusSearch;
	
	/*
	 * 计算候选情感词的so-pmi值
	 */
	private static final String POS_SEED_PATH = "G:\\data\\论文数据\\newdata\\正面种子词20.txt";//正面种子词路径
	private static final String NEG_SEED_PATH = "G:\\data\\论文数据\\newdata\\负面种子词20.txt";//种子词路径
	private static final String CANDIDATE_PATH = "G:\\data\\论文数据\\newdata\\candidate.txt";//候选词路径
	private List<String> posSeedList = new ArrayList<String>();
	private List<String> negSeedList = new ArrayList<String>();
	public SoPmi() {
		initSeedDic();
		corpusSearch = new CorpusSearch();
	}
	
	private void initSeedDic() {
		List<String> seedList = FileUtils.load(POS_SEED_PATH, "utf-8");
		List<String> negList = FileUtils.load(NEG_SEED_PATH, "utf-8");
		for (String seed : seedList) {
			posSeedList.add(seed);
		}
		for (String seed : negList) {
			negSeedList.add(seed);
		}
	}

	public double soPmi(String candidateWord) {
		double posValue = 1;
		double negValue = 1;
		
		if (posSeedList != null && posSeedList.size() > 0) {
			for (String posWord : posSeedList) {
				posValue *= (corpusSearch.search(candidateWord, posWord) + 1);
			}
		}
		if (negSeedList != null && negSeedList.size() > 0) {
			for (String negWord : negSeedList) {
				negValue *=(corpusSearch.search(candidateWord, negWord) + 1);
			}
		}
		double sopmiValue = Math.log(posValue/negValue);
		return sopmiValue;
	}
	/*
	 * 候选种子词情感类别判断
	 */
	public void sentimentTypeJudge(String outputPath) {
		List<String> candidateList = FileUtils.load(CANDIDATE_PATH, "utf-8");
		for (String candidate : candidateList) {
			double soPmiValue = soPmi(candidate);
			FileUtils.string2File(candidate + "\t" + soPmiValue + "\r\n", outputPath, "utf-8", true);
			System.out.println(soPmiValue);
		}
	}
	public static void main(String args[]) {
		SoPmi soPmi = new SoPmi();
		String outputPath = "G:\\data\\论文数据\\newdata\\candidate_pmi.txt";
		soPmi.sentimentTypeJudge(outputPath);
	}

}
