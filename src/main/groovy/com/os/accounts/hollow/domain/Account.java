package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;

import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class Account extends HollowObject {

    public Account(AccountDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public HString getVersion() {
        int refOrdinal = delegate().getVersionOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getName() {
        int refOrdinal = delegate().getNameOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getDescription() {
        int refOrdinal = delegate().getDescriptionOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getUserId() {
        int refOrdinal = delegate().getUserIdOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getPasswordHint() {
        int refOrdinal = delegate().getPasswordHintOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getPinHint() {
        int refOrdinal = delegate().getPinHintOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getUrl() {
        int refOrdinal = delegate().getUrlOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getCategory() {
        int refOrdinal = delegate().getCategoryOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getNotes() {
        int refOrdinal = delegate().getNotesOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public AccountAPI api() {
        return typeApi().getAPI();
    }

    public AccountTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected AccountDelegate delegate() {
        return (AccountDelegate)delegate;
    }

}