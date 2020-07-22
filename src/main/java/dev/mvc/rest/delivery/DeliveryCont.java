package dev.mvc.rest.delivery;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryCont {

	@Autowired  // DI, Spring framework�� �ڵ� ������ DAO�� �ڵ� �Ҵ��.
	@Qualifier("dev.mvc.rest.delivery.DeliveryProc")
	private DeliveryProcInter deliveryProc;

	public DeliveryCont() {
		System.out.println("--> DeliveryCont created");
	}

	// POST ���, GET ��ȸ , PUT ����, DELETE ����
	// http://localhost:9090/delivery_service/delivery_rest/create/delivery_create_jsonp/A001
	// http://172.16.12.4:9090/delivery_service/delivery_rest/create/delivery_create_jsonp/A001
	/**
	 * ���
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/create/{key}", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(  @PathVariable("key") String key,
											@RequestBody DeliveryVO vo) { // requestbody: jquery���� ���� json�� �ٷ� ���� �� ����
		System.out.println("��� ��û��: " + vo.toString());
		int cnt = 0;
		/*
		private int trackingno;
		private String porder_company;
		private int porderno;
		private int porder_detailno;
		private String dman;
		private String delivery_date;
		private String status;
		*/
		if (key.equals("A001")) {
			vo.setDman("��Ų�͹��");
			vo.setStatus("S");
			cnt = this.deliveryProc.create(vo);
		} else { // Ű�� ��ġ���� �ʴ� ���
			
		}

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(cnt), HttpStatus.OK);
		return entity;
	}

	// �ٸ� ������ ���� ����
	// http://172.16.12.4:9090/delivery_service/delivery_rest/list/A001/skinfit
	// [{"deliveryno":1,"title":"����1","content":"����1","writer":"�ۼ���1","rdate":"2020-0715"},{"deliveryno":2,"title":"����2","content":"����2","writer":"�ۼ���2","rdate":"2020-0715"},{"deliveryno":3,"title":"����3","content":"����3","writer":"�ۼ���3","rdate":"2020-0715"}]
	/**
	 * ���
	 * @return
	 */
	@RequestMapping(value="/delivery_rest/list/{key}/{porder_company}", method=RequestMethod.GET)
	public ResponseEntity<List<Object>> list(
			@PathVariable("key") String key,
			@PathVariable("porder_company") String porder_company) {
		System.out.println("key: " + key +"/ porder_company: " + porder_company);

		List<Object> list = new ArrayList<Object>();

		if(key.equals("A001")) {
			// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "���θ�","2020-07-22", "O");
			// list.add(vo);
			list.add(this.deliveryProc.status_cnt_by_com(porder_company));
			list.add(this.deliveryProc.list_by_com(porder_company));
		} else {// Ű�� ��ġ���� �ʴ� ���
			list.add(-999);
			DeliveryVO vo = new DeliveryVO(-999, "", -999, -999, "", "", "");
			list.add(vo);
		}


		ResponseEntity<List<Object>> entity = new ResponseEntity<List<Object>>(list, HttpStatus.OK);
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/read/100
	// {"deliveryno":100,"title":"����1","content":"����1","writer":"�ۼ���1","rdate":"2020-0715"}
	/**
	 * ��ȸ
	 * @return
	 */
	@RequestMapping(value="/delivery_rest/read/{trackingno}", method=RequestMethod.GET)
	public ResponseEntity<DeliveryVO> read(@PathVariable("trackingno") int trackingno) {
		System.out.println("�б� ��û��.");
		// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "���θ�","2020-07-22", "O");
		DeliveryVO vo = this.deliveryProc.read(trackingno);
		ResponseEntity<DeliveryVO> entity = new ResponseEntity<DeliveryVO>(vo, HttpStatus.OK); // ��û�� �Դ��� Ȯ��
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/update
	/**
	 * ����
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/update", method = RequestMethod.PUT)
	public ResponseEntity<Integer> update(@RequestBody DeliveryVO vo) {
		System.out.println("������ ��ü ����: " + vo.toString());
		
		int cnt = this.deliveryProc.update(vo);

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(cnt), HttpStatus.OK);
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/delete/2022
	/**
	 * ����
	 * @param deliveryno
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/delete/{trackingno}", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> delete(@PathVariable("trackingno") int trackingno) {
		System.out.println("������ PK ��ȣ: " + trackingno);
		
		int cnt = this.deliveryProc.delete(trackingno);
		int delete_pk = trackingno;

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(delete_pk), HttpStatus.OK);
		return entity;
	}

	// jsonp ���
	/**
	 * ���� ���ڿ�: delivery_list_jsonp({"list":[{"rdate":"2020-0715","deliveryno":1,"writer":"???1","title":"??1","content":"??1"},{"rdate":"2020-0715","deliveryno":2,"writer":"???2","title":"??2","content":"??2"},{"rdate":"2020-0715","deliveryno":3,"writer":"???3","title":"??3","content":"??3"}]})
	 * ���, JSONP ��û
	 * @return
	 */
	@RequestMapping(value="/delivery_rest/list_jsonp/{callback}/{key}/{porder_company}", 
			method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> list_jsonp(
			@PathVariable("callback") String callback,
			@PathVariable("key") String key,
			@PathVariable("porder_company") String porder_company) {
		System.out.println("callback: " + callback);
		System.out.println("key: " + key + " / porder_company: " + porder_company);

		List<Object> list = new ArrayList<Object>();
		if (key.equals("A001")) {
			// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "���θ�","2020-07-22", "O");
			// list.add(vo);
			list.add(this.deliveryProc.status_cnt_by_com(porder_company));
			list.add(this.deliveryProc.list_by_com(porder_company));
		} else { // Ű�� ��ġ���� �ʴ� ���
			list.add(-999);
			DeliveryVO vo = new DeliveryVO(-999, "", -999, -999, "", "", "");
			list.add(vo);

		}
		JSONObject obj = new JSONObject();
		obj.put("list", list);    
		String rvalue = callback + "(" + obj.toString() + ")";    

		ResponseEntity<String> entity = new ResponseEntity<String>(rvalue, HttpStatus.OK);
		return entity;
	}


}