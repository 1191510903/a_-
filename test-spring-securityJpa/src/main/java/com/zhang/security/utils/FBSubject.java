package com.zhang.security.utils;

import com.zhang.security.anotation.Description;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.io.Serializable;

public class FBSubject extends FBEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(unique = true)
    private String identifier;


    @ManyToOne(cascade = CascadeType.ALL)
    private FBSubject superior;

    //物理分页
    @Description("页码")
    @Transient
    private int pageNum;

    @Description("每页显示条数")
    @Transient
    private int pageSize;

    protected FBSubject() {
        super();
    }

    public FBSubject(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public FBSubject getSuperior() { return superior; }

    public void setSuperior(FBSubject superior) {
        this.superior = superior;
    }
    public int getPageNum() {
        if( pageNum > 0){
            return pageNum;
        }else{
            return 1;
        }
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if(pageSize > 0){
            return pageSize;
        }else{
            // 如果配置项无此属性，则默认每页30条
            String pgsz = "30";
            return Integer.parseInt(pgsz);
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
