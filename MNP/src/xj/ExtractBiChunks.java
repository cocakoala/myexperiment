/**
 * 
 */
package xj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xj
 *
 */
public class ExtractBiChunks {

	public String chunkingPath = "/home/xj/Documents/experiment/052413/english.chunking";
	public String alignPath = "/home/xj/Documents/experiment/052413/Alignment.kickbracket";
	public String chnPath = "/home/xj/Documents/experiment/052413/chinese.kickbracket";
	public String chnChunkPath = "/home/xj/Documents/myexperiment/chunk-concerned/chnChunk";
	public String engChunkPath = "/home/xj/Documents/myexperiment/chunk-concerned/engChunk";
	public String chkAlnPath = "/home/xj/Documents/myexperiment/chunk-concerned/chunkAlign";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ExtractBiChunks().extract();
	}
	
	public void extract() {
		BufferedReader brChk = null;
		BufferedReader brAln = null;
		BufferedReader brChn = null;
		
		BufferedWriter bwChnChunk = null;
		BufferedWriter bwEngChunk = null;
		BufferedWriter bwAln = null;
		
		try {
			brChk = new BufferedReader(new FileReader(new File(chunkingPath)));
			brAln = new BufferedReader(new FileReader(new File(alignPath)));
			brChn = new BufferedReader(new FileReader(new File(chnPath)));
			
			bwChnChunk = new BufferedWriter(new FileWriter(new File(chnChunkPath)));
			bwEngChunk = new BufferedWriter(new FileWriter(new File(engChunkPath)));
			bwAln = new BufferedWriter(new FileWriter(new File(chkAlnPath)));
			
			String align = null, chn = null;
			int[] chnIdx = new int[60], alnIdx = new int[60];
			List<Integer> chnChkIdx = new ArrayList<Integer>();		// store indices of chn words in chn sentences, -1 will be removed
			List<Integer> alnChkIdx = new ArrayList<Integer>();		// store indices of alignments 
			List<Integer> nullIdx = new ArrayList<Integer>();		// store -1, which is used in removeAll()
			nullIdx.add(-1);
			List<Integer> engPosList = new ArrayList<Integer>();	// store indices of eng words in eng sentences 
			List<Integer> chnPosList = new ArrayList<Integer>();	// store indices of chn words 
			while ( ( align = brAln.readLine() ) != null ) {
				chn = brChn.readLine();
				String[] chnAry = chn.split(" ");
				// no sentences in the training data has length over 60, so set chnAl and 
				// engAl's max_length to 60, and initialize them to a negative (-1)
				for ( int i = 0; i < 60; i++ ) {
					chnIdx[i] = -1; alnIdx[i] = -1;
				}
				String[] alnAry = align.split(" ");
				for ( int i = 0; i < alnAry.length; i++ ) {
					String[] tmp = alnAry[i].split("-");
					int engPos = Integer.parseInt(tmp[1]);	// Pos for position
					int chnPos = Integer.parseInt(tmp[0]);
					alnIdx[engPos] = i;
					chnIdx[engPos] = chnPos;
				}
				
				// OK, all the above is preparation. now we are going to extract chunks from this sentence pair.
				String chunkInfo = null;
				chnChkIdx.clear(); alnChkIdx.clear();
				StringBuilder chnChunk = new StringBuilder();
				StringBuilder engChunk = new StringBuilder();
				int engPos;
//				engPosList.clear(); chnPosList.clear(); 
//				chnChkIdx.clear(); alnChkIdx.clear();
				while ( ( chunkInfo = brChk.readLine() ) != null ) {
					
					if ( chunkInfo.length() < 1 )
						break;
					if ( chunkInfo.startsWith("#") )
						continue;
					String[] chunkInfoAry = chunkInfo.split("[ ]+");
					if ( chunkInfoAry[1].startsWith("O") || 
						 chunkInfoAry[1].startsWith("C") ) {
						continue;
					} else {
						// begin of a chunk
						if ( chunkInfoAry[1].startsWith("B") ) {
							chnChunk.delete( 0, chnChunk.length() );
							engChunk.delete( 0, engChunk.length() );
							engPosList.clear(); chnPosList.clear(); 
							chnChkIdx.clear(); alnChkIdx.clear();
						}
						
						engPos = Integer.parseInt( chunkInfoAry[0] );
						chnChkIdx.add( chnIdx[engPos] );
//						chnChkIdx.add( chnIdx[engPos] );
						alnChkIdx.add( alnIdx[engPos] );
						engPosList.add( engPos );
						chnPosList.add( chnIdx[engPos] );
						engChunk.append( chunkInfoAry[2].replaceAll( "COMMA", "," ) + " " );
						if ( chunkInfoAry[1].startsWith("E") ) {	// end of a chunk
							// remove -1
							chnChkIdx.removeAll(nullIdx);

							// sort indices 
							Collections.sort(chnChkIdx);
							Collections.sort(alnChkIdx);
							Collections.sort(engPosList);
							Collections.sort(chnPosList);
							
							// if this IS a chunk, extract it and write it to file
							if ( isAlignConsecutive( alnChkIdx ) ) {
								chnChunk = new StringBuilder();
								for ( int i = chnChkIdx.get(0); i <= chnChkIdx.get( chnChkIdx.size() - 1 ); i++ ) {
									chnChunk.append( chnAry[i] + " " );
								}
								
								// write chunk to file
								bwEngChunk.write( engChunk + "\n" );
								bwChnChunk.write( chnChunk + "\n" );
								
								// write alignment to file
								int chnStart = minPositive( chnPosList );
								for (int i = 0; i < chnPosList.size(); i++ ) {
									if ( chnPosList.get(i) >= 0 ) {
										bwAln.write( ( chnPosList.get(i) - chnStart ) + "-"  + ( engPosList.get(i) - engPosList.get(0) ) + " " );
									}
								}
								bwAln.write("\n");
								
							}

						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bwAln.close();
				bwChnChunk.close();
				bwEngChunk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * judge if the numbers in the list are consecutive
	 * @param list A sorted list in natural order
	 * @return
	 */
	public boolean isAlignConsecutive( List<Integer> list ) {
		return list.size() > 0 && 
				( list.size() == ( list.get( list.size()-1 ) - list.get(0) + 1) );
	}

	/**
	 * 
	 * @param lst
	 * @return
	 */
	public int minPositive( List<Integer> lst ) {
		int min = Integer.MAX_VALUE, tmp;
		for (int i = 0; i < lst.size(); i++ ) {
			tmp = lst.get(i);
			if ( tmp >= 0 && tmp < min ) {
				min = tmp;
			}
		}
		return min;
	}
}
