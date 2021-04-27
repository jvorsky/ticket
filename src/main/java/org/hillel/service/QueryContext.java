package org.hillel.service;

import org.springframework.util.StringUtils;

public class QueryContext {

    private QueryType queryType;
    private int pageNumber;
    private int pageSize;
    private String orderFieldName;
    private boolean orderAsc;

    /**
     *  @param queryType - тип запроса
     * @param pageNumber - номер страницы
     * @param pageSize - кол-во элементов на странице
     * @param orderFieldName - название поля для сортировки
     * @param orderAsc - напрвление сортировки
     */
    public QueryContext(QueryType queryType, int pageNumber, int pageSize,
                        String orderFieldName, boolean orderAsc) {
        this.queryType = queryType;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderFieldName = orderFieldName;
        this.orderAsc = orderAsc;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getOrderFieldName() {
        return orderFieldName;
    }

    public boolean isOrderAsc() {
        return orderAsc;
    }

    public int getFirstResult(){
        return (pageNumber - 1) * pageSize;
    }

    public String getOrderStr(){
        if (!StringUtils.isEmpty(orderFieldName)){
            return " order by " + orderFieldName + (orderAsc ? " asc" : " desc");
        }
        return "";
    }
}