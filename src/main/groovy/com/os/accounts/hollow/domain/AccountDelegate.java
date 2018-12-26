package com.os.accounts.hollow.domain;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface AccountDelegate extends HollowObjectDelegate {

    public int getNameOrdinal(int ordinal);

    public int getDescriptionOrdinal(int ordinal);

    public int getUserIdOrdinal(int ordinal);

    public int getPasswordHintOrdinal(int ordinal);

    public int getPinHintOrdinal(int ordinal);

    public int getUrlOrdinal(int ordinal);

    public int getCategoryOrdinal(int ordinal);

    public int getNotesOrdinal(int ordinal);

    public AccountTypeAPI getTypeAPI();

}