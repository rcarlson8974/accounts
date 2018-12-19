package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;

@SuppressWarnings("all")
public class AccountDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, AccountDelegate {

    private final int versionOrdinal;
    private final int nameOrdinal;
    private final int descriptionOrdinal;
    private final int userIdOrdinal;
    private final int passwordHintOrdinal;
    private final int pinHintOrdinal;
    private final int urlOrdinal;
    private final int categoryOrdinal;
    private final int notesOrdinal;
    private AccountTypeAPI typeAPI;

    public AccountDelegateCachedImpl(AccountTypeAPI typeAPI, int ordinal) {
        this.versionOrdinal = typeAPI.getVersionOrdinal(ordinal);
        this.nameOrdinal = typeAPI.getNameOrdinal(ordinal);
        this.descriptionOrdinal = typeAPI.getDescriptionOrdinal(ordinal);
        this.userIdOrdinal = typeAPI.getUserIdOrdinal(ordinal);
        this.passwordHintOrdinal = typeAPI.getPasswordHintOrdinal(ordinal);
        this.pinHintOrdinal = typeAPI.getPinHintOrdinal(ordinal);
        this.urlOrdinal = typeAPI.getUrlOrdinal(ordinal);
        this.categoryOrdinal = typeAPI.getCategoryOrdinal(ordinal);
        this.notesOrdinal = typeAPI.getNotesOrdinal(ordinal);
        this.typeAPI = typeAPI;
    }

    public int getVersionOrdinal(int ordinal) {
        return versionOrdinal;
    }

    public int getNameOrdinal(int ordinal) {
        return nameOrdinal;
    }

    public int getDescriptionOrdinal(int ordinal) {
        return descriptionOrdinal;
    }

    public int getUserIdOrdinal(int ordinal) {
        return userIdOrdinal;
    }

    public int getPasswordHintOrdinal(int ordinal) {
        return passwordHintOrdinal;
    }

    public int getPinHintOrdinal(int ordinal) {
        return pinHintOrdinal;
    }

    public int getUrlOrdinal(int ordinal) {
        return urlOrdinal;
    }

    public int getCategoryOrdinal(int ordinal) {
        return categoryOrdinal;
    }

    public int getNotesOrdinal(int ordinal) {
        return notesOrdinal;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public AccountTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (AccountTypeAPI) typeAPI;
    }

}