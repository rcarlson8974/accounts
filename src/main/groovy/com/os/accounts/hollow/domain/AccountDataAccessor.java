package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class AccountDataAccessor extends AbstractHollowDataAccessor<Account> {

    public static final String TYPE = "Account";
    private AccountAPI api;

    public AccountDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (AccountAPI)consumer.getAPI();
    }

    public AccountDataAccessor(HollowReadStateEngine rStateEngine, AccountAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public AccountDataAccessor(HollowReadStateEngine rStateEngine, AccountAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public AccountDataAccessor(HollowReadStateEngine rStateEngine, AccountAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public Account getRecord(int ordinal){
        return api.getAccount(ordinal);
    }

}