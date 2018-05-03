package pers.MasterSlave;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

public interface SubTaskDispatchStrategy<T, V> {

	/**
	 * ����ָ����ԭʼ����ֽ���ԣ����ֽ�����ĸ��������ɷ���һ��Slave������ʵ����
	 * 
	 * @param slaves ���Խ����������һ��Slave������ʵ��
	 * @param taskDivideStrategy ԭʼ����ֽ����
	 * @return iterator��������iterator�ɵõ����ڻ�ȡ������������Promiseʵ��
	 * @throws InterruptedException ��Slave�������̱߳��ж�ʱ�׳����쳣
	 */
	Iterator<Future<V>> dispatch(Set<? extends SlaveSpec<T, V>> slaves, TaskDivideStrategy<T> taskDivideStrategy)
		throws InterruptedException;
}
