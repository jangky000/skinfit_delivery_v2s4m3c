package dev.mvc.rest.delivery;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.rest.delivery.DeliveryProc")
public class DeliveryProc implements DeliveryProcInter {

	@Autowired  // DI, Spring framework이 자동 구현한 DAO가 자동 할당됨.
	private DeliveryDAOInter deliveryDAO;

	public DeliveryProc(){
	}

	@Override
	public int create(DeliveryVO deliveryVO) {
		int cnt = this.deliveryDAO.create(deliveryVO);
		return cnt;
	}

	@Override
	public List<DeliveryVO> list_by_com(String porder_company) {
		List<DeliveryVO> list = this.deliveryDAO.list_by_com(porder_company);
		return list;
	}

	@Override
	public List<HashMap> status_cnt_by_com(String porder_company){
		List<HashMap> list  = this.deliveryDAO.status_cnt_by_com(porder_company);
		return list;
	}

	@Override
	public DeliveryVO read(int trackingno) {
		DeliveryVO deliveryVO = this.deliveryDAO.read(trackingno);
		return deliveryVO;
	}

	@Override
	public int update(DeliveryVO deliveryVO) {
		int cnt = this.deliveryDAO.update(deliveryVO);
		return cnt;
	}

	@Override
	public int delete(int trackingno) {
		int cnt = this.deliveryDAO.delete(trackingno);
		return cnt;
	}

}





