package dev.mvc.rest.delivery;

import java.util.HashMap;
import java.util.List;

public interface DeliveryDAOInter {

	/**
	 * <xmp>
	 * 배송 등록
	 * <insert id="create" parameterType="DeliveryVO">
	 * </xmp>
	 * @param deliveryVO
	 * @return
	 */
	public int create(DeliveryVO deliveryVO);
	
	/**
	 * <xmp>
	 * 회사별 배송 리스트
	 * <select id="list_by_com" resultType="DeliveryVO" parameterType="String">
	 * </xmp>
	 * @param porder_company
	 * @return
	 */
	public List<DeliveryVO> list_by_com(String porder_company);
	
	/**
	 * <xmp>
	 * 회사별 배송상태별 개수 오름차순
	 * <select id="status_cnt_by_com" resultType="HashMap" parameterType="String">
	 * </xmp>
	 * @param porder_company
	 * @return
	 */
	public List<HashMap> status_cnt_by_com(String porder_company);
	
	/**
	 * <xmp>
	 * 배송 no로 조회
	 * <select id="read" resultType="DeliveryVO" parameterType="int">
	 * </xmp>
	 * @param trackingno
	 * @return
	 */
	public DeliveryVO read(int trackingno);
	
	/**
	 * <xmp>
	 * 배송 수정
	 * <update id="update" parameterType="DeliveryVO">
	 * </xmp>
	 * @param deliveryVO
	 * @return
	 */
	public int update(DeliveryVO deliveryVO);
	
	
	/**
	 * <xmp>
	 * 배송 삭제
	 * <delete id="delete" parameterType="int">
	 * </xmp>
	 * @param trackingno
	 * @return
	 */
	public int delete(int trackingno);
	

}




