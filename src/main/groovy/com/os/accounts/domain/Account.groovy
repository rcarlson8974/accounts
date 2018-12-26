package com.os.accounts.domain

import com.netflix.hollow.core.write.objectmapper.HollowPrimaryKey
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@HollowPrimaryKey(fields = ['name'])
@ToString
@EqualsAndHashCode
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
