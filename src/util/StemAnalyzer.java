package util;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;

public class StemAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {

		Tokenizer source = new StandardTokenizer(TokenStream.DEFAULT_TOKEN_ATTRIBUTE_FACTORY);
		
		boolean stop = true;
		boolean stem = true;
		
		if(stop){
			if(stem){
				return new TokenStreamComponents(source, new PorterStemFilter(new LowerCaseFilter(new StopFilter(source, StandardAnalyzer.STOP_WORDS_SET))));
			}else{
				return new TokenStreamComponents(source, new StopFilter(source, StandardAnalyzer.STOP_WORDS_SET));
			}			
		}else{
			if(stem){
				return new TokenStreamComponents(source, new PorterStemFilter(new LowerCaseFilter(new StopFilter(source, CharArraySet.EMPTY_SET))));				
			}else{
				return new TokenStreamComponents(source, new StopFilter(source, CharArraySet.EMPTY_SET));
			}
		}

	}

}
