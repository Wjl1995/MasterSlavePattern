package pers.MasterSlave;

/**
 * ��ʾ��������ʧ�ܵ��쳣
 * 
 * @author WJL
 *
 */
public class SubTaskFailureException extends Exception {

	/**
	 * �Դ���ʧ�ܵ���������������������Ϣ
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes") 
	public final RetryInfo retryInfo;
	
	@SuppressWarnings("rawtypes") 
	public SubTaskFailureException(RetryInfo retryInfo, Exception cause)
	{
		super(cause);
		this.retryInfo = retryInfo;
	}

}
