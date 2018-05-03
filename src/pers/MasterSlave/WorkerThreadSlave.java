package pers.MasterSlave;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import per.ThreadTermination.AbstractTerminatableThread;

/**
 * ���ڹ������̵߳�Slave������ͨ��ʵ�֡�
 * 
 * @author WJL
 *
 * @param <T> ����������
 * @param <V> ��������������
 */
public abstract class WorkerThreadSlave<T, V> extends AbstractTerminatableThread implements SlaveSpec<T, V> {

	private final BlockingQueue<Runnable> taskQueue;
	
	public WorkerThreadSlave(BlockingQueue<Runnable> taskQueue)
	{
		this.taskQueue = taskQueue;
	}
	
	@Override
	public Future<V> submit(T task) throws InterruptedException {
		// TODO Auto-generated method stub
		
		FutureTask<V> ft = new FutureTask<V>(new Callable<V>(){

			@Override
			public V call() throws Exception {
				// TODO Auto-generated method stub
				
				V result;
				
				try{
					result = doProcess(task);
				}catch (Exception e){
					SubTaskFailureException stfe = newSubTaskFailureException(task, e);
					throw stfe;
				}
				
				return result;
			}
			
		});
		
		taskQueue.put(ft);
		terminationToken.reservation.incrementAndGet();
		
		return ft;
	}
	
	private SubTaskFailureException newSubTaskFailureException(final T subTask, Exception cause)
	{
		RetryInfo<T, V> retryInfo = new RetryInfo<T, V>(subTask, new Callable<V>(){

			@Override
			public V call() throws Exception {
				// TODO Auto-generated method stub
				V result;
				result = doProcess(subTask);
				return result;
			}
			
		});
		
		return new SubTaskFailureException(retryInfo, cause);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		start();
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		terminate(true);
	}

	@Override
	protected void doRun() throws Exception {
		// TODO Auto-generated method stub
		try{
			Runnable task = taskQueue.take();
			task.run();
		}finally{
			terminationToken.reservation.decrementAndGet();
		}
	}
	
	/**
	 * ��������ʵ�֡�����ʵ��������Ĵ����߼�
	 * 
	 * @param task ������
	 * @return ������Ĵ�����
	 * @throws Exception
	 */
	protected abstract V doProcess(T task) throws Exception;

}
