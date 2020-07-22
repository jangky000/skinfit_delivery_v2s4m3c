-- �� �۾��� �ϱ� ���ؼ� �ش� ������ create any job ������ ������ �־�� ��
-- ������ ���� �ο�
--connect sys as sysdba / pw:1234
-- grant create any job to ai6 ;
-- grant stop any job to ai6 ;


-- �����ٷ��� �� ���
BEGIN
    DBMS_SCHEDULER.CREATE_JOB
    (
    JOB_NAME => 'EX_DELIVERY_JOB',
    JOB_TYPE => 'STORED_PROCEDURE',
    JOB_ACTION => 'EX_DELIVERY_PROC',
    REPEAT_INTERVAL => 'FREQ=MINUTELY; INTERVAL =10', --10�п� 1��
    COMMENTS => '��� �� ��ü'
    );
END;

-- �� ����
BEGIN
  SYS.DBMS_SCHEDULER.DROP_JOB
    (job_name   => 'EX_DELIVERY_JOB', force      => FALSE);
END;



-- �� ���� ���� Ȯ��
SELECT JOB_NAME, JOB_TYPE, JOB_ACTION, REPEAT_INTERVAL, ENABLED, AUTO_DROP, STATE, COMMENTS 
FROM USER_SCHEDULER_JOBS 


-- �� enable ����
BEGIN
    DBMS_SCHEDULER.ENABLE('EX_DELIVERY_JOB');
END;

-- �� disable ����
BEGIN
    DBMS_SCHEDULER.DISABLE('EX_DELIVERY_JOB');
END;

-- ������ �α� ���
SELECT * FROM USER_SCHEDULER_JOB_LOG;
-- ������ �α� ���, �� ������
SELECT * FROM USER_SCHEDULER_JOB_RUN_DETAILS;


SET SERVEROUTPUT ON; -- putline ���

-- ���ν��� ����
-- �غ���(R), �������(S), �����(O), ��ۿϷ�(C), ��ǰ(R) ??
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
  
  

--2. ���ν��� ����
begin
  EX_DELIVERY_PROC;
end;
/



