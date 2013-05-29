/**
 * 
 */
package xj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xj
 *
 */
public class ExtractBiChunks {

	public String chunkingPath = "/home/xj/Documents/myexperiment/chunk-concerned/english.chunking.with-s.head1000";
	public String alignPath = "/home/xj/Documents/myexperiment/chunk-concerned/Alignment.kickbracket.head1000";
	public String chnPath = "/home/xj/Documents/myexperiment/chunk-concerned/chinese.kickbracket.head1000";
	public String engChunkPath = "/home/xj/Documents/myexperiment/chunk-concerned/chnChunk";
	public String chnChunkPath = "/home/xj/Documents/myexperiment/chunk-concerned/engChunk";
	
	
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
		
		try {
			brChk = new BufferedReader(new FileReader(new File(chunkingPath)));
			brAln = new BufferedReader(new FileReader(new File(alignPath)));
			brChn = new BufferedReader(new FileReader(new File(chnPath)));
			
			String align = null, chn = null, enChunk = null;
			int[] chnIdx = new int[60], alnIdx = new int[60];
			List<Integer> chnChkIdx = new ArrayList<Integer>();
			List<Integer> alnChkIdx = new ArrayList<Integer>();
			List<Integer> nullIdx = new ArrayList<Integer>();
			nullIdx.add(-1);
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
						chnChkIdx.add( chnIdx[Integer.parseInt( chunkInfoAry[0] )] );
						alnChkIdx.add( alnIdx[Integer.parseInt( chunkInfoAry[0] )] );
						engChunk.append( chunkInfoAry[2].replaceAll( "COMMA", "," ) + " " );
						if ( chunkInfoAry[1].startsWith("E") ) {	// end of a chunk
							chnChkIdx.removeAll(nullIdx);
							alnChkIdx.removeAll(nullIdx);
							Collections.sort(chnChkIdx);
							Collections.sort(alnChkIdx);
							if ( isAlignConsecutive( alnChkIdx ) ) {
								chnChunk = new StringBuilder();
								for ( int i = chnChkIdx.get(0); i <= chnChkIdx.get( chnChkIdx.size() - 1 ); i++ ) {
									chnChunk.append( chnAry[i] + " " );
								}
								// TODO write chunk to file
								System.out.println(chnChunk + " -- " + engChunk);
							}
							chnChunk.delete( 0, chnChunk.length() );
							engChunk.delete( 0, engChunk.length() );
							chnChkIdx.clear(); alnChkIdx.clear();
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	/**
	 * judge if the numbers in the list are consecutive
	 * @param list A sorted list in natural order
	 * @return
	 */
	public boolean isAlignConsecutive( List<Integer> list ) {
		return list.size() > 0 && ( list.size() == ( list.get( list.size()-1 ) - list.get(0) + 1) );
	}

}
