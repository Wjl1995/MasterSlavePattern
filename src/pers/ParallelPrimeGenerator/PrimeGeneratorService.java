package pers.ParallelPrimeGenerator;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import pers.MasterSlave.AbstractMaster;
import pers.MasterSlave.RetryInfo;
import pers.MasterSlave.SlaveSpec;
import pers.MasterSlave.SubTaskFailureException;
import pers.MasterSlave.TaskDivideStrategy;

public class PrimeGeneratorService extends AbstractMaster<Range, Set<BigInteger>, Set<BigInteger>> {

	public PrimeGeneratorService(){
		this.init();
	}
	
	@Override
	protected Set<? extends SlaveSpec<Range, Set<BigInteger>>> createSlaves() {
		// TODO Auto-generated method stub
		Set<PrimeGenerator> slaves = new HashSet<PrimeGenerator>();
		for (int i=0; i<Runtime.getRuntime().availableProcessors(); i++)
		{
			slaves.add(new PrimeGenerator(new ArrayBlockingQueue<Runnable>(2)));
		}
		
		return slaves;
	}

	@Override
	protected TaskDivideStrategy<Range> newTaskDivideStrategy(Object... params) {
		// TODO Auto-generated method stub
		
		final int numOfSlaves = slaves.size();
		final int originalTaskScale = (Integer) params[0];
		final int subTaskScale = originalTaskScale / numOfSlaves;
		final int subTaskCount = (0 == (originalTaskScale % numOfSlaves)) ? numOfSlaves : numOfSlaves+1;
		
		TaskDivideStrategy<Range> tds = new TaskDivideStrategy<Range>()
				{
					private int i = 1;

					@Override
					public Range nextChunk() {
						// TODO Auto-generated method stub
						
						int upperBound;
						if (i < subTaskCount)
							upperBound = i * subTaskScale;
						else
							if (i == subTaskCount)
								upperBound = originalTaskScale;
							else
								return null;
						
						int lowerBound = (i - 1) * subTaskScale + 1;
						i++;
						
						return new Range(lowerBound, upperBound);
					}
				};
		
		return tds;
	}

	@Override
	protected Set<BigInteger> combineResults(Iterator<Future<Set<BigInteger>>> subResults) {
		// TODO Auto-generated method stub
		
		Set<BigInteger> result = new TreeSet<BigInteger>();
		
		while (subResults.hasNext())
		{
			try{
				result.addAll(subResults.next().get());
			}catch (InterruptedException e){
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				Throwable cause = e.getCause();
				if (SubTaskFailureException.class.isInstance(cause))
				{
					@SuppressWarnings("rawtypes")
					RetryInfo retryInfo = ((SubTaskFailureException)cause).retryInfo;
					Object subTask = retryInfo.subTask;
					System.err.println("falied subTask:" + subTask);
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public Set<BigInteger> generatePrime(int upperBound) throws Exception
	{
		return this.service(upperBound);
	}

}
