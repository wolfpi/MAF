package com.baidu.maf.util;

public class IDGenerator {
	private static long mStart = 0;
	private static long mEnd = 0 ;
	private static boolean  mInit = false;
	private static long mCurSeq = -1;
	public static void setSeqRange(long start, long end)
	{
		mStart = start;
		mEnd = end;
		mCurSeq = start;
		mInit = true;
	}

	public static long getSeq()
	{
		if(mInit)
		{
			if(++mCurSeq >= mEnd )
			{
				mCurSeq = mStart;
			}
			
			return mCurSeq;
		}
		return  -1;
	}
	
	public static void reset()
	{
		mInit = false;
	}
}
