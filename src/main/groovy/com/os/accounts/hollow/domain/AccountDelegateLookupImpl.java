package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class AccountDelegateLookupImpl extends HollowObjectAbstractDelegate implements AccountDelegate {

    private final AccountTypeAPI typeAPI;

    public AccountDelegateLookupImpl(AccountTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public int getVersionOrdinal(int ordinal) {
        return typeAPI.getVersionOrdinal(ordinal);
    }

    public int getNameOrdinal(int ordinal) {
        return typeAPI.getNameOrdinal(ordinal);
    }

    public int getDescriptionOrdinal(int ordinal) {
        return typeAPI.getDescriptionOrdinal(ordinal);
    }

    public int getUserIdOrdinal(int ordinal) {
        return typeAPI.getUserIdOrdinal(ordinal);
    }

    public int getPasswordHintOrdinal(int ordinal) {
        return typeAPI.getPasswordHintOrdinal(ordinal);
    }

    public int getPinHintOrdinal(int ordinal) {
        return typeAPI.getPinHintOrdinal(ordinal);
    }

    public int getUrlOrdinal(int ordinal) {
        return typeAPI.getUrlOrdinal(ordinal);
    }

    public int getCategoryOrdinal(int ordinal) {
        return typeAPI.getCategoryOrdinal(ordinal);
    }

    public int getNotesOrdinal(int ordinal) {
        return typeAPI.getNotesOrdinal(ordinal);
    }

    public AccountTypeAPI getTypeAPI() {
        return typeAPI;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

}