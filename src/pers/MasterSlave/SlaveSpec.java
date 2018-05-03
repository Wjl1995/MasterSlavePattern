package pers.MasterSlave;

import java.util.concurrent.Future;

/**
 * ��Master-Slaveģʽ��Slave�����ߵĳ���
 * 
 * @author WJL
 *
 * @param <T>
 * @param <V>
 */
public interface SlaveSpec<T, V> {

	/**
	 * ����Master�����ύһ��������
	 * @param task
	 * @return
	 * @throws InterruptedException
	 */
	Future<V> submit(final T task) throws InterruptedException;
	
	/**
	 * ��ʼ��Slaveʵ�������ṩ�ķ���
	 */
	void init();
	
	/**
	 * ֹͣSlaveʵ�������ṩ�ķ���
	 */
	void shutdown();
}
