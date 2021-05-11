package com.show.showticketingservice.tool.util.datasource;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/*
    AbstractRoutingDataSource:
    - 여러 DataSource를 등록하고 특정 상황에 맞게 원하는 DataSource를 사용할 수 있도록 추상화한 클래스
    - Map 형식의 targetDataSources에 DataSource를 저장
    - key를 기준으로 매칭되는 DataSource 를 연결해 주는데 상황에 맞게 key 값을 반환해 주는 메소드가 determineCurrentLookupKey()

    TransactionSynchronizationManager
    - 스프링에서 제공하는 트랜잭션 동기화 관리 클래스
    - isCurrentTransactionReadOnly() 메소드: 현재의 트랜잭션이 Read-Only인지 확인

    Read-Only Transaction의 경우 Slave DB Server에서 처리하도록 DataSource의 key 값을 'slave'로 반환
 */

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DatabaseServer.SLAVE
                : DatabaseServer.MASTER;
    }

}
