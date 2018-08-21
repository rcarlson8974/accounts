package com.os.accounts.domain

import com.netflix.hollow.core.write.objectmapper.HollowPrimaryKey

@HollowPrimaryKey(fields = ['name'])
class Account {
    String name
    String description
    String userId
    String passwordHint
    String pinHint
    String url
    String category
    String notes
}
