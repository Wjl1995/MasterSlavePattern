package pers.MasterSlave;

import java.util.concurrent.Future;

/**
 * 对Master-Slave模式的Slave参与者的抽象。
 * 
 * @author WJL
 *
 * @param <T>
 * @param <V>
 */
public interface SlaveSpec<T, V> {

	/**
	 * 用于Master向其提交一个子任务。
	 * @param task
	 * @return
	 * @throws InterruptedException
	 */
	Future<V> submit(final T task) throws InterruptedException;
	
	/**
	 * 初始化Slave实例对外提供的服务。
	 */
	void init();
	
	/**
	 * 停止Slave实例对外提供的服务。
	 */
	void shutdown();
}
