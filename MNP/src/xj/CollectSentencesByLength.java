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

/**
 * @author xj
 *
 */
public class CollectSentencesByLength {

	public static final String inPath = "/home/xj/Documents/myexperiment/testdata/test938";
	public static final String inTreePath = "/home/xj/Documents/myexperiment/testdata/test938.tree";
	public static final String inSkeletonPath = "/home/xj/Documents/myexperiment/testdata/skeleton938";
	public static final String inSkeletonTreePath = "/home/xj/Documents/myexperiment/testdata/skeleton938.tree";
	public static final String refPath = "/home/xj/Documents/myexperiment/testdata/ref938";
	
	
	public static final String s0_20 = "/home/xj/Documents/myexperiment/050713/sents0_20";
	public static final String t0_20 = "/home/xj/Documents/myexperiment/050713/tree0_20";
	public static final String skeleton0_20 = "/home/xj/Documents/myexperiment/050713/skeleton0_20";
	public static final String skeletonTree0_20 = "/home/xj/Documents/myexperiment/050713/skeletonTree0_20";
	
	public static final String s20_40 = "/home/xj/Documents/myexperiment/050713/sents20_40";
	public static final String t20_40 = "/home/xj/Documents/myexperiment/050713/tree20_40";
	public static final String skeleton20_40 = "/home/xj/Documents/myexperiment/050713/skeleton20_40";
	public static final String skeletonTree20_40 = "/home/xj/Documents/myexperiment/050713/skeletonTree20_40";
	
	public static final String s40_ = "/home/xj/Documents/myexperiment/050713/sents40_";
	public static final String t40_ = "/home/xj/Documents/myexperiment/050713/tree40_";
	public static final String skeleton40_ = "/home/xj/Documents/myexperiment/050713/skeleton40_";
	public static final String skeletonTree40_ = "/home/xj/Documents/myexperiment/050713/skeletonTree40_";
	
	public static final String r0_20 = "/home/xj/Documents/myexperiment/062213/input/ref0_20";
	public static final String r20_40 = "/home/xj/Documents/myexperiment/062213/input/ref20_40";
	public static final String r40_ = "/home/xj/Documents/myexperiment/062213/input/ref40_";
	
	public static final String log = "/home/xj/Documents/myexperiment/050713/collectSents.log";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CollectSentencesByLength().collect();
	}
	
	public void collect() {
		BufferedReader br = null;
		BufferedReader brT = null;
		BufferedReader brSkeleton = null;
		BufferedReader brSkeletonTree = null;
		BufferedReader brRef = null;
		
		BufferedWriter bw0 = null;
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		
		BufferedWriter bwT0 = null;
		BufferedWriter bwT1 = null;
		BufferedWriter bwT2 = null;

		BufferedWriter bwSkeleton0 = null;
		BufferedWriter bwSkeleton1 = null;
		BufferedWriter bwSkeleton2 = null;
		
		BufferedWriter bwSkeletonTree0 = null;
		BufferedWriter bwSkeletonTree1 = null;
		BufferedWriter bwSkeletonTree2 = null;
		
		BufferedWriter bwRef0 = null;
		BufferedWriter bwRef1 = null;
		BufferedWriter bwRef2 = null;
		
		BufferedWriter bwlog = null;
		
		try {
			br = new BufferedReader(new FileReader(new File(inPath)));
			brT = new BufferedReader(new FileReader(new File(inTreePath)));
			brSkeleton = new BufferedReader(new FileReader(new File(inSkeletonPath)));
			brSkeletonTree = new BufferedReader(new FileReader(new File(inSkeletonTreePath)));
			brRef = new BufferedReader(new FileReader(new File(refPath)));
			
			bw0 = new BufferedWriter(new FileWriter(new File(s0_20)));
			bw1 = new BufferedWriter(new FileWriter(new File(s20_40)));
			bw2 = new BufferedWriter(new FileWriter(new File(s40_)));
			
			bwT0 = new BufferedWriter(new FileWriter(new File(t0_20)));
			bwT1 = new BufferedWriter(new FileWriter(new File(t20_40)));
			bwT2 = new BufferedWriter(new FileWriter(new File(t40_)));
			
			bwSkeleton0 = new BufferedWriter(new FileWriter(new File(skeleton0_20)));
			bwSkeleton1 = new BufferedWriter(new FileWriter(new File(skeleton20_40)));
			bwSkeleton2 = new BufferedWriter(new FileWriter(new File(skeleton40_)));
			
			bwSkeletonTree0 = new BufferedWriter(new FileWriter(new File(skeletonTree0_20)));
			bwSkeletonTree1 = new BufferedWriter(new FileWriter(new File(skeletonTree20_40)));
			bwSkeletonTree2 = new BufferedWriter(new FileWriter(new File(skeletonTree40_)));
			
			bwRef0 = new BufferedWriter(new FileWriter(new File(r0_20)));
			bwRef1 = new BufferedWriter(new FileWriter(new File(r20_40)));
			bwRef2 = new BufferedWriter(new FileWriter(new File(r40_)));
			
			bwlog = new BufferedWriter(new FileWriter(new File(log)));
			
			String in = null;
			while ((in = br.readLine()) != null) {
				int wordCount = in.split("\\s+").length;
				bwlog.write(in + " -- " + wordCount + "\n");
				if (wordCount < 20) {
					bw0.write(in + "\n");
					bwT0.write(brT.readLine() + "\n");
					bwSkeleton0.write(brSkeleton.readLine() + "\n");
					bwSkeletonTree0.write(brSkeletonTree.readLine() + "\n");
					for ( int i = 0; i < 3; i++ ) {
						bwRef0.write(brRef.readLine() + "\n");
					}
				} else if (wordCount >= 40) {
					bw2.write(in + "\n");
					bwT2.write(brT.readLine() + "\n");
					bwSkeleton2.write(brSkeleton.readLine() + "\n");
					bwSkeletonTree2.write(brSkeletonTree.readLine() + "\n");
					for ( int i = 0; i < 3; i++ ) {
						bwRef2.write(brRef.readLine() + "\n");
					}
				} else {
					bw1.write(in + "\n");
					bwT1.write(brT.readLine() + "\n");
					bwSkeleton1.write(brSkeleton.readLine() + "\n");
					bwSkeletonTree1.write(brSkeletonTree.readLine() + "\n");
					for ( int i = 0; i < 3; i++ ) {
						bwRef1.write(brRef.readLine() + "\n");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw0.close();
				bw1.close();
				bw2.close();
				bwT0.close();
				bwT1.close();
				bwT2.close();
				bwSkeleton0.close();
				bwSkeleton1.close();
				bwSkeleton2.close();
				bwSkeletonTree0.close();
				bwSkeletonTree1.close();
				bwSkeletonTree2.close();
				bwlog.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
