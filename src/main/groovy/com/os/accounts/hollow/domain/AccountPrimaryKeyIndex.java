package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.api.consumer.index.HollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class AccountPrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<AccountAPI, Account> implements HollowUniqueKeyIndex<Account> {

    public AccountPrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);
    }

    public AccountPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema("Account")).getPrimaryKey().getFieldPaths());
    }

    public AccountPrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public AccountPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "Account", isListenToDataRefresh, fieldPaths);
    }

    @Override
    public Account findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getAccount(ordinal);
    }

}