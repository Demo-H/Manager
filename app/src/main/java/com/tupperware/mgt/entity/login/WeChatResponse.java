package com.tupperware.mgt.entity.login;

import com.tupperware.mgt.entity.BaseData;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WeChatResponse extends BaseData {
    private EmployeeInfo model;
    private Extra extra;

    public class Extra{
        private boolean isWxBind;
        private Token token;

        public class Token{
            private String token;
            private String type;
            private long expire;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public long getExpire() {
                return expire;
            }

            public void setExpire(long expire) {
                this.expire = expire;
            }
        }

        public boolean isWxBind() {
            return isWxBind;
        }

        public void setWxBind(boolean wxBind) {
            isWxBind = wxBind;
        }

        public Token getToken() {
            return token;
        }

        public void setToken(Token token) {
            this.token = token;
        }
    }

    public EmployeeInfo getModel() {
        return model;
    }

    public void setModel(EmployeeInfo model) {
        this.model = model;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}
