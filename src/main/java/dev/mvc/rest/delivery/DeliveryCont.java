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

	@Autowired  // DI, Spring framework이 자동 구현한 DAO가 자동 할당됨.
	@Qualifier("dev.mvc.rest.delivery.DeliveryProc")
	private DeliveryProcInter deliveryProc;

	public DeliveryCont() {
		System.out.println("--> DeliveryCont created");
	}

	// POST 등록, GET 조회 , PUT 수정, DELETE 삭제
	// http://localhost:9090/delivery_service/delivery_rest/create/delivery_create_jsonp/A001
	// http://172.16.12.4:9090/delivery_service/delivery_rest/create/delivery_create_jsonp/A001
	/**
	 * 등록
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/create/{key}", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(  @PathVariable("key") String key,
											@RequestBody DeliveryVO vo) { // requestbody: jquery에서 받은 json을 바로 받을 수 있음
		System.out.println("등록 요청됨: " + vo.toString());
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
			vo.setDman("스킨핏배송");
			vo.setStatus("S");
			cnt = this.deliveryProc.create(vo);
		} else { // 키가 일치하지 않는 경우
			
		}

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(cnt), HttpStatus.OK);
		return entity;
	}

	// 다른 서버로 접근 가능
	// http://172.16.12.4:9090/delivery_service/delivery_rest/list/A001/skinfit
	// [{"deliveryno":1,"title":"제목1","content":"내용1","writer":"작성자1","rdate":"2020-0715"},{"deliveryno":2,"title":"제목2","content":"내용2","writer":"작성자2","rdate":"2020-0715"},{"deliveryno":3,"title":"제목3","content":"내용3","writer":"작성자3","rdate":"2020-0715"}]
	/**
	 * 목록
	 * @return
	 */
	@RequestMapping(value="/delivery_rest/list/{key}/{porder_company}", method=RequestMethod.GET)
	public ResponseEntity<List<Object>> list(
			@PathVariable("key") String key,
			@PathVariable("porder_company") String porder_company) {
		System.out.println("key: " + key +"/ porder_company: " + porder_company);

		List<Object> list = new ArrayList<Object>();

		if(key.equals("A001")) {
			// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "쿠팡맨","2020-07-22", "O");
			// list.add(vo);
			list.add(this.deliveryProc.status_cnt_by_com(porder_company));
			list.add(this.deliveryProc.list_by_com(porder_company));
		} else {// 키가 일치하지 않는 경우
			list.add(-999);
			DeliveryVO vo = new DeliveryVO(-999, "", -999, -999, "", "", "");
			list.add(vo);
		}


		ResponseEntity<List<Object>> entity = new ResponseEntity<List<Object>>(list, HttpStatus.OK);
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/read/100
	// {"deliveryno":100,"title":"제목1","content":"내용1","writer":"작성자1","rdate":"2020-0715"}
	/**
	 * 조회
	 * @return
	 */
	@RequestMapping(value="/delivery_rest/read/{trackingno}", method=RequestMethod.GET)
	public ResponseEntity<DeliveryVO> read(@PathVariable("trackingno") int trackingno) {
		System.out.println("읽기 요청됨.");
		// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "쿠팡맨","2020-07-22", "O");
		DeliveryVO vo = this.deliveryProc.read(trackingno);
		ResponseEntity<DeliveryVO> entity = new ResponseEntity<DeliveryVO>(vo, HttpStatus.OK); // 요청이 왔는지 확인
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/update
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/update", method = RequestMethod.PUT)
	public ResponseEntity<Integer> update(@RequestBody DeliveryVO vo) {
		System.out.println("수정할 객체 내용: " + vo.toString());
		
		int cnt = this.deliveryProc.update(vo);

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(cnt), HttpStatus.OK);
		return entity;
	}

	// http://localhost:9090/delivery_service/delivery_rest/delete/2022
	/**
	 * 삭제
	 * @param deliveryno
	 * @return
	 */
	@RequestMapping(value = "/delivery_rest/delete/{trackingno}", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> delete(@PathVariable("trackingno") int trackingno) {
		System.out.println("삭제할 PK 번호: " + trackingno);
		
		int cnt = this.deliveryProc.delete(trackingno);
		int delete_pk = trackingno;

		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(new Integer(delete_pk), HttpStatus.OK);
		return entity;
	}

	// jsonp 목록
	/**
	 * 응답 문자열: delivery_list_jsonp({"list":[{"rdate":"2020-0715","deliveryno":1,"writer":"???1","title":"??1","content":"??1"},{"rdate":"2020-0715","deliveryno":2,"writer":"???2","title":"??2","content":"??2"},{"rdate":"2020-0715","deliveryno":3,"writer":"???3","title":"??3","content":"??3"}]})
	 * 목록, JSONP 요청
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
			// DeliveryVO vo = new DeliveryVO(1, "skinfit", 1, 1, "쿠팡맨","2020-07-22", "O");
			// list.add(vo);
			list.add(this.deliveryProc.status_cnt_by_com(porder_company));
			list.add(this.deliveryProc.list_by_com(porder_company));
		} else { // 키가 일치하지 않는 경우
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