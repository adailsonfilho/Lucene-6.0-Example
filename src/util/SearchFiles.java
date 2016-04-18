/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class SearchFiles {

	String indexPath;
	int repeat = 0;
	boolean raw = false;
	Analyzer analyzer;
	int hitsPerPage = 300;
	
	boolean stop;
	boolean stem;

	public SearchFiles(String indexPath, boolean stop, boolean stem) throws Exception {
		this.indexPath = indexPath;
		this.stop = stop;
		this.stem = stem;
		analyzer = new StemAnalyzer(stop, stem);

	}

	public String[][] query(String queryString, String field) throws IOException, ParseException{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

		QueryParser parser = new QueryParser(field, analyzer);

		if (queryString == null || queryString.length() == -1) {
			new Exception("Query vazia não pode, pow!");
		}

		queryString = queryString.trim();
		if (queryString.length() == 0) {
			new Exception("Só tem caracter de espaço enssa query, jovem!!");
		}

		Query query = parser.parse(queryString);
		System.out.println("Searching for: " + query.toString(field));

		if (repeat > 0) {                           // repeat & time as benchmark
			Date start = new Date();
			for (int i = 0; i < repeat; i++) {
				searcher.search(query,100);
			}
			Date end = new Date();
			System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
		}

		ScoreDoc[] hits = doPagingSearch(in, searcher, query, hitsPerPage, raw, queryString == null);

		String[][] result = new String[hits.length][2];

		for(int i=0;i<hits.length;++i) 
		{
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			int index = i+1;
			result[i][0] = ""+index;
			result[i][1] = d.get("title");
		}
		
		reader.close();
		
		return result;
	}

	/**
	 * This demonstrates a typical paging search scenario, where the search engine presents 
	 * pages of size n to the user. The user can then go to the next page if interested in
	 * the next hits.
	 * 
	 * When the query is executed for the first time, then only enough results are collected
	 * to fill 5 result pages. If the user wants to page beyond this limit, then the query
	 * is executed another time and all hits are collected.
	 * 
	 */
	private ScoreDoc[] doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
			int hitsPerPage, boolean raw, boolean interactive) throws IOException {

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		return hits;
//		int start = 0;
//		int end = Math.min(numTotalHits, hitsPerPage);
//
//		while (true) {
//			if (end > hits.length) {
//				System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
//				System.out.println("Collect more (y/n) ?");
//				String line = in.readLine();
//				if (line.length() == 0 || line.charAt(0) == 'n') {
//					break;
//				}
//
//				hits = searcher.search(query, numTotalHits).scoreDocs;
//			}
//
//			end = Math.min(hits.length, start + hitsPerPage);
//
//			for (int i = start; i < end; i++) {
//				if (raw) {                              // output raw format
//					System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
//					continue;
//				}
//
//				Document doc = searcher.doc(hits[i].doc);
//				String path = doc.get("path");
//				if (path != null) {
//					System.out.println((i+1) + ". " + path);
//					String title = doc.get("title");
//					if (title != null) {
//						System.out.println("   Title: " + doc.get("title"));
//					}
//				} else {
//					System.out.println((i+1) + ". " + "No path for this document");
//				}
//
//			}
//
//			if (!interactive || end == 0) {
//				break;
//			}
//
//			if (numTotalHits >= end) {
//				boolean quit = false;
//				while (true) {
//					System.out.print("Press ");
//					if (start - hitsPerPage >= 0) {
//						System.out.print("(p)revious page, ");  
//					}
//					if (start + hitsPerPage < numTotalHits) {
//						System.out.print("(n)ext page, ");
//					}
//					System.out.println("(q)uit or enter number to jump to a page.");
//
//					String line = in.readLine();
//					if (line.length() == 0 || line.charAt(0)=='q') {
//						quit = true;
//						break;
//					}
//					if (line.charAt(0) == 'p') {
//						start = Math.max(0, start - hitsPerPage);
//						break;
//					} else if (line.charAt(0) == 'n') {
//						if (start + hitsPerPage < numTotalHits) {
//							start+=hitsPerPage;
//						}
//						break;
//					} else {
//						int page = Integer.parseInt(line);
//						if ((page - 1) * hitsPerPage < numTotalHits) {
//							start = (page - 1) * hitsPerPage;
//							break;
//						} else {
//							System.out.println("No such page");
//						}
//					}
//				}
//				if (quit) break;
//				end = Math.min(numTotalHits, start + hitsPerPage);
//			}
//		}
	}
}