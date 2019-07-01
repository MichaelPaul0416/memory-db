package com.paul.base.sync.oracle.user.dao;

import com.paul.base.sync.oracle.user.model.StkCode;

import java.util.List;

public interface StkCodeMapper {

    List<StkCode> queryByCondition(StkCode stkCode);
}