package dev.mvc.rest.delivery;

public class DeliveryVO {

	/**
	 * 	trackingno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
        porder_company                      		VARCHAR2(100)		 NULL ,
        porderno                    		NUMBER(10)		 NULL,
        porder_detailno                    		NUMBER(10)		 NULL,
		dman                          		VARCHAR2(30)		 NULL ,
		delivery_date                 		DATE		 NULL ,
		status                        		CHAR(1)		 NULL
	 */
	private int trackingno;
	private String porder_company;
	private int porderno;
	private int porder_detailno;
	private String dman;
	private String delivery_date;
	private String status;

	public DeliveryVO() {

	}

	public DeliveryVO(int trackingno, String porder_company, int porderno, int porder_detailno, String dman,
			String delivery_date, String status) {
		// super();
		this.trackingno = trackingno;
		this.porder_company = porder_company;
		this.porderno = porderno;
		this.porder_detailno = porder_detailno;
		this.dman = dman;
		this.delivery_date = delivery_date;
		this.status = status;
	}

	public int getTrackingno() {
		return trackingno;
	}

	public void setTrackingno(int trackingno) {
		this.trackingno = trackingno;
	}

	public String getPorder_company() {
		return porder_company;
	}

	public void setPorder_company(String porder_company) {
		this.porder_company = porder_company;
	}

	public int getPorderno() {
		return porderno;
	}

	public void setPorderno(int porderno) {
		this.porderno = porderno;
	}

	public int getPorder_detailno() {
		return porder_detailno;
	}

	public void setPorder_detailno(int porder_detailno) {
		this.porder_detailno = porder_detailno;
	}

	public String getDman() {
		return dman;
	}

	public void setDman(String dman) {
		this.dman = dman;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DeliveryVO [trackingno=" + trackingno + ", porder_company=" + porder_company + ", porderno=" + porderno
				+ ", porder_detailno=" + porder_detailno + ", dman=" + dman + ", delivery_date=" + delivery_date
				+ ", status=" + status + "]";
	}

	

}