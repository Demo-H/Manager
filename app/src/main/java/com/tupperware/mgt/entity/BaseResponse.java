package com.tupperware.mgt.entity;

/**
 * Created by dhunter on 2018/11/23.
 */

public class BaseResponse extends BaseData{

    private Model model;

    public class Model{

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
