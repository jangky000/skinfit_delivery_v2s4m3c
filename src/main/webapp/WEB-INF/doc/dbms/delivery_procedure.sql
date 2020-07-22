-- 이 작업을 하기 위해선 해당 계정이 create any job 권한을 가지고 있어야 함
-- 계정에 권한 부여
--connect sys as sysdba / pw:1234
-- grant create any job to ai6 ;
-- grant stop any job to ai6 ;


-- 스케줄러에 잡 등록
BEGIN
    DBMS_SCHEDULER.CREATE_JOB
    (
    JOB_NAME => 'EX_DELIVERY_JOB',
    JOB_TYPE => 'STORED_PROCEDURE',
    JOB_ACTION => 'EX_DELIVERY_PROC',
    REPEAT_INTERVAL => 'FREQ=MINUTELY; INTERVAL =10', --10분에 1번
    COMMENTS => '배송 잡 객체'
    );
END;

-- 잡 삭제
BEGIN
  SYS.DBMS_SCHEDULER.DROP_JOB
    (job_name   => 'EX_DELIVERY_JOB', force      => FALSE);
END;



-- 잡 생성 내역 확인
SELECT JOB_NAME, JOB_TYPE, JOB_ACTION, REPEAT_INTERVAL, ENABLED, AUTO_DROP, STATE, COMMENTS 
FROM USER_SCHEDULER_JOBS 


-- 잡 enable 실행
BEGIN
    DBMS_SCHEDULER.ENABLE('EX_DELIVERY_JOB');
END;

-- 잡 disable 중지
BEGIN
    DBMS_SCHEDULER.DISABLE('EX_DELIVERY_JOB');
END;

-- 실행한 로그 기록
SELECT * FROM USER_SCHEDULER_JOB_LOG;
-- 실행한 로그 기록, 더 세부적
SELECT * FROM USER_SCHEDULER_JOB_RUN_DETAILS;


SET SERVEROUTPUT ON; -- putline 출력

-- 프로시저 생성
-- 준비중(R), 배송지시(S), 배송중(O), 배송완료(C), 반품(R) ??
CREATE OR REPLACE PROCEDURE EX_DELIVERY_PROC
IS
    up_status delivery.status%TYPE;
BEGIN
    FOR delivery_list IN  (SELECT * FROM delivery) LOOP
        IF delivery_list.status = 'R' THEN
            up_status := 'S';
        ELSIF delivery_list.status = 'S' THEN
            up_status := 'O';
        ELSIF delivery_list.status = 'O' THEN
            up_status := 'C';
        END IF;
        
        IF delivery_list.status != 'C' THEN
            UPDATE delivery
            SET status = up_status, delivery_date=sysdate
            WHERE trackingno = delivery_list.trackingno;
            DBMS_OUTPUT.PUT_LINE(delivery_list.trackingno);
            DBMS_OUTPUT.PUT_LINE(up_status);
        END IF;
    END LOOP;
    COMMIT WORK;  -- transaction commit
END;
/
  
  

--2. 프로시저 실행
begin
  EX_DELIVERY_PROC;
end;
/



