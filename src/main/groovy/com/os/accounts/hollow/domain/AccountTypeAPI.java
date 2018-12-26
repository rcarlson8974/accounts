package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class AccountTypeAPI extends HollowObjectTypeAPI {

    private final AccountDelegateLookupImpl delegateLookupImpl;

    public AccountTypeAPI(AccountAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "name",
            "description",
            "userId",
            "passwordHint",
            "pinHint",
            "url",
            "category",
            "notes"
        });
        this.delegateLookupImpl = new AccountDelegateLookupImpl(this);
    }

    public int getNameOrdinal(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "name");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[0]);
    }

    public StringTypeAPI getNameTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getDescriptionOrdinal(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "description");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[1]);
    }

    public StringTypeAPI getDescriptionTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getUserIdOrdinal(int ordinal) {
        if(fieldIndex[2] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "userId");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[2]);
    }

    public StringTypeAPI getUserIdTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getPasswordHintOrdinal(int ordinal) {
        if(fieldIndex[3] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "passwordHint");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[3]);
    }

    public StringTypeAPI getPasswordHintTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getPinHintOrdinal(int ordinal) {
        if(fieldIndex[4] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "pinHint");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[4]);
    }

    public StringTypeAPI getPinHintTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getUrlOrdinal(int ordinal) {
        if(fieldIndex[5] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "url");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[5]);
    }

    public StringTypeAPI getUrlTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getCategoryOrdinal(int ordinal) {
        if(fieldIndex[6] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "category");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[6]);
    }

    public StringTypeAPI getCategoryTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getNotesOrdinal(int ordinal) {
        if(fieldIndex[7] == -1)
            return missingDataHandler().handleReferencedOrdinal("Account", ordinal, "notes");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[7]);
    }

    public StringTypeAPI getNotesTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public AccountDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public AccountAPI getAPI() {
        return (AccountAPI) api;
    }

}