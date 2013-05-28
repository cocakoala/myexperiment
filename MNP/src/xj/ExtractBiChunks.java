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
import java.util.List;

/**
 * @author xj
 *
 */
public class ExtractBiChunks {

	public String chunkingPath = "";
	public String alignPath = "";
	public String chnPath = "";
	public String engChunkPath = "";
	public String chnChunkPath = "";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

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
			while ( ( align = brAln.readLine() ) != null ) {
				chn = brChn.readLine();
				String[] chnAry = chn.split(" ");
				// no sentences in the training data has length over 60, so set chnAl and 
				// engAl's max_length to 60, and initialize them to a negative (-1)
				for ( int i = 0; i < 60; i++ ) {
					chnIdx[i] = -1; alnIdx[i] = -1;
				}
				String[] alnAry = align.split(" ");
				for ( int i = 0; i < align.length(); i++ ) {
					String[] tmp = alnAry[i].split("-");
					int engPos = Integer.parseInt(tmp[1]);	// Pos for position
					int chnPos = Integer.parseInt(tmp[0]);
					alnIdx[engPos] = i;
					chnIdx[engPos] = chnPos;
				}
				
				// OK, all the above is preparation. now we are going to extract chunks from this sentence pair.
				String chunkInfo = null;
				chnChkIdx.clear(); alnChkIdx.clear();
				while ( ( chunkInfo = brAln.readLine() ) != null ) {
					if ( chunkInfo.length() < 1 ) 
						break;
					String[] chunkInfoAry = chunkInfo.split("[ ]+");
					if ( chunkInfoAry[1].startsWith("O") || 
						 chunkInfoAry[1].startsWith("C") ) {
						continue;
					} else {
						chnChkIdx.add( chnIdx[Integer.parseInt( chunkInfoAry[0] )] );
						alnChkIdx.add( alnIdx[Integer.parseInt( chunkInfoAry[0] )] );
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

}
