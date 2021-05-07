package com.show.showticketingservice.tool.util.datasource;

import com.show.showticketingservice.model.enumerations.DatabaseServer;

/*
    ThreadLocal:
    - 하나의 스레드에 의해 읽고 쓸 수 있는 변수.
    - 각 스레드 별로 독립적이며 다른 스레드의 ThreadLocal을 참조할 수 없다.
    - Thread Pool 환경에서는 스레드를 재사용하기 때문에 저장된 데이터의 사용이 끝나면 ThreadLocal의 데이터를 삭제하여야 한다.
    - 여기서는 database source를 구분하여 설정하기 위한 데이터 값(DatabaseServer Enum) 저장
 */

public class RoutingDataSourceManager {

    private static final ThreadLocal<DatabaseServer> DATASOURCE = new ThreadLocal<>();

    public static void setDataSource(DatabaseServer source) {
        DATASOURCE.set(source);
    }

    public static DatabaseServer getDataSource() {
        return DATASOURCE.get();
    }

    public static void removeDataSource() {
        DATASOURCE.remove();
    }

}
