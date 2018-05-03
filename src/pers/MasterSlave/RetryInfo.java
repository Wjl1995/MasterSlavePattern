package pers.MasterSlave;

import java.util.concurrent.Callable;

/**
 * 对处理失败的子任务进行重试所需的信息
 * 
 * @author WJL
 *
 * @param <T>
 * @param <V>
 */
public class RetryInfo<T, V> {

	public final T subTask;
	public final Callable<V> redoCommand;
	
	public RetryInfo(T subTask, Callable<V> redoCommand)
	{
		this.subTask = subTask;
		this.redoCommand = redoCommand;
	}
	
}
