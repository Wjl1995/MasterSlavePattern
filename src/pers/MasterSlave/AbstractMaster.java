package pers.MasterSlave;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Master-SlaveģʽMaster�����ߵĿɸ���ʵ�֡�
 * 
 * @author WJL
 *
 * @param <T> �������������
 * @param <V> ������Ĵ���������
 * @param <R> ԭʼ������������
 */
public abstract class AbstractMaster<T, V, R> {

	protected volatile Set<? extends SlaveSpec<T, V>> slaves;
	
	private volatile SubTaskDispatchStrategy<T, V> dispatchStrategy;
	
	public AbstractMaster(){
		
	}
	
	protected void init()
	{
		slaves = createSlaves();
		dispatchStrategy = newSubTaskDispatchStrategy();
		for (SlaveSpec<T, V> slave : slaves)
		{
			slave.init();
		}
	}
	
	/**
	 * ��������ʵ�֡����ڴ���Slave������ʵ����
	 * 
	 * @return
	 */
	protected abstract Set<? extends SlaveSpec<T, V>> createSlaves();
	
	/**
	 * ���ڴ����������ɷ��㷨���ԡ�Ĭ��ʹ����ѯ��Round-Robin���ɷ��㷨
	 * 
	 * @return �������ɷ��㷨����ʵ����
	 */
	protected SubTaskDispatchStrategy<T, V> newSubTaskDispatchStrategy()
	{
		return new RoundRobinSubTaskDispatchStrategy<T, V>();
	}
	
	/**
	 * ��������ʵ�֡����ڴ���ԭʼ����ֽ��㷨��
	 * 
	 * @param params �ͻ��˴������service����ʱ���ݵĲ����б�
	 * @return 
	 */
	protected abstract TaskDivideStrategy<T> newTaskDivideStrategy(Object...params);
	
	/**
	 * �����౩¶�ķ��񷽷��������������Ҫ����һ���ȸ÷���������Ϊ����ķ��񷽷�����downloadFileService����
	 * �������������ķ��񷽷�����downloadFileService�����ô˷���
	 * �˷���ʹ����Templateģʽ��Strategyģʽ��
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected R service(Object...params) throws Exception
	{
		final TaskDivideStrategy<T> taskDivideStrategy = newTaskDivideStrategy(params);
		
		/**
		 * ��ԭʼ������зֽ⣬�����ֽ�������������ɷ���Slave������ʵ��������ʹ����Strategyģʽ��ԭʼ����ֽ���������ɷ�
		 * ����������ļ�����ͨ��������Ҫ���㷨����ʵ�ֵġ�
		 */
		Iterator<Future<V>> subResults = dispatchStrategy.dispatch(slaves, taskDivideStrategy);
		
		// �ȴ�Slaveʵ�����������
		for (SlaveSpec<T, V> slave : slaves)
		{
			slave.shutdown();
		}
		
		// �ϲ�������Ĵ�����
		R result = combineResults(subResults);
		return result;
	}

	/**
	 * ��������ʵ�֡����ںϲ�������Ĵ�������
	 * 
	 * @param subResults ��������������
	 * @return ԭʼ�����ʵ�����
	 */
	protected abstract R combineResults(Iterator<Future<V>> subResults);
}
