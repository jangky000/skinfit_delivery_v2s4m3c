DROP TABLE delivery CASCADE CONSTRAINTS;

-- 주문 상세와 배송은 1:1 관계
-- REST로 배송 업체에서 관리할 테이블 -> reference 관계가 있을 수 없다. 
-- 관리하는 대상이 다르다
/**********************************/
/* Table Name: 배송 */
/**********************************/
CREATE TABLE delivery(
		trackingno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
        porder_company                      		VARCHAR2(100)		 NULL ,
        porderno                    		NUMBER(10)		 NULL,
        porder_detailno                    		NUMBER(10)		 NULL,
		dman                          		VARCHAR2(30)		 NULL ,
		delivery_date                 		DATE		 NULL ,
		status                        		CHAR(1)		 NULL
);

COMMENT ON TABLE delivery is '배송';
COMMENT ON COLUMN delivery.trackingno is '운송장번호';
COMMENT ON COLUMN delivery.porder_company is '배송요청회사';
COMMENT ON COLUMN delivery.porderno is '주문번호';
COMMENT ON COLUMN delivery.porder_detailno is '주문상세번호';
COMMENT ON COLUMN delivery.dman is '담당자';
COMMENT ON COLUMN delivery.delivery_date is '배송상태최종처리일';
COMMENT ON COLUMN delivery.status is '배송상태'; -- 준비중(R), 배송지시(S), 배송중(O), 배송완료(C), 반품(R) ??

-- trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status

-- 시퀀스
DROP SEQUENCE delivery_seq;
CREATE SEQUENCE delivery_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지



-- CREATE
INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '왕눈이', sysdate, 'S');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '아로미', sysdate, 'S');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '투투', sysdate, 'S');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '왕눈이', sysdate, 'O');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '아로미', sysdate, 'O');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '투투', sysdate, 'O');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '왕눈이', sysdate, 'C');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '아로미', sysdate, 'C');

INSERT INTO delivery(trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status)
VALUES(delivery_seq.nextval, 'SKINFIT', 1, 1, '투투', sysdate, 'C');

COMMIT;

-- LIST
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
ORDER BY trackingno DESC;

-- 회사별 LIST
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
WHERE porder_company = UPPER('skinfit')
ORDER BY trackingno DESC;


-- 배송 상태별 cnt
SELECT status, COUNT(*) as cnt
FROM delivery
WHERE porder_company = UPPER('skinfit')
GROUP BY status
ORDER BY status ASC;


-- 배송지시 받은 상품만 보기
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
WHERE status = 'S'
ORDER BY trackingno DESC;

-- 배송중인 상품만 보기
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
WHERE status = 'O'
ORDER BY trackingno DESC;

-- 배송 완료 상품만 보기
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
WHERE status = 'C'
ORDER BY trackingno DESC;

-- READ
-- 운송장번호에 따른 배송 정보
SELECT trackingno, porder_company, porderno, porder_detailno, dman, delivery_date, status
FROM delivery
WHERE trackingno = 1;

--UPDATE
-- 택배사, 담당자 변경
UPDATE delivery
SET porder_company='배송요청회사', dman='담당자', delivery_date=sysdate, status='O' -- 배송중
WHERE trackingno = 1;

--DELETE
DELETE FROM delivery
WHERE trackingno = 1;

ROLLBACK;