package dev.mvc.rest.delivery;

import java.util.HashMap;
import java.util.List;

public interface DeliveryDAOInter {

	/**
	 * <xmp>
	 * ��� ���
	 * <insert id="create" parameterType="DeliveryVO">
	 * </xmp>
	 * @param deliveryVO
	 * @return
	 */
	public int create(DeliveryVO deliveryVO);
	
	/**
	 * <xmp>
	 * ȸ�纰 ��� ����Ʈ
	 * <select id="list_by_com" resultType="DeliveryVO" parameterType="String">
	 * </xmp>
	 * @param porder_company
	 * @return
	 */
	public List<DeliveryVO> list_by_com(String porder_company);
	
	/**
	 * <xmp>
	 * ȸ�纰 ��ۻ��º� ���� ��������
	 * <select id="status_cnt_by_com" resultType="HashMap" parameterType="String">
	 * </xmp>
	 * @param porder_company
	 * @return
	 */
	public List<HashMap> status_cnt_by_com(String porder_company);
	
	/**
	 * <xmp>
	 * ��� no�� ��ȸ
	 * <select id="read" resultType="DeliveryVO" parameterType="int">
	 * </xmp>
	 * @param trackingno
	 * @return
	 */
	public DeliveryVO read(int trackingno);
	
	/**
	 * <xmp>
	 * ��� ����
	 * <update id="update" parameterType="DeliveryVO">
	 * </xmp>
	 * @param deliveryVO
	 * @return
	 */
	public int update(DeliveryVO deliveryVO);
	
	
	/**
	 * <xmp>
	 * ��� ����
	 * <delete id="delete" parameterType="int">
	 * </xmp>
	 * @param trackingno
	 * @return
	 */
	public int delete(int trackingno);
	

}




