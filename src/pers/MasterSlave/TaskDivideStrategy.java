package pers.MasterSlave;

/**
 * ��ԭʼ����ֽ��㷨���Եĳ���
 * 
 * @author WJL
 *
 * @param <T>
 */
public interface TaskDivideStrategy<T> {

	/**
	 * ������һ��������������ֵΪnull�����ʾ�޺���������
	 * 
	 * @return
	 */
	T nextChunk();
}
