package pers.ParallelPrimeGenerator;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import pers.MasterSlave.WorkerThreadSlave;

/**
 * 质数生成器。模式角色：Master-Slave.Slave
 * 
 * @author WJL
 *
 */
public class PrimeGenerator extends WorkerThreadSlave<Range, Set<BigInteger>> {

	public PrimeGenerator(BlockingQueue<Runnable> taskQueue)
	{
		super(taskQueue);
	}

	@Override
	protected Set<BigInteger> doProcess(Range range) throws Exception {
		// TODO Auto-generated method stub
		
		Set<BigInteger> result = new TreeSet<BigInteger>();
		BigInteger start = BigInteger.valueOf(range.lowerBound);
		BigInteger end = BigInteger.valueOf(range.upperBound);
		
		while (-1 == (start = start.nextProbablePrime()).compareTo(end))
		{
			result.add(start);
		}
		
		return result;
	}
	
}
