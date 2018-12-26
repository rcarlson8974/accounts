package com.os.accounts.hollow

import com.netflix.hollow.api.codegen.HollowAPIGenerator
import com.netflix.hollow.core.write.HollowWriteStateEngine
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper
import com.os.accounts.domain.Account

class AccountGenerator {

    static void main(String[] args) {
        println("AccountGenerator generating new domain objects...........")
        generateAccountAPI()
    }


    static void generateAccountAPI() {
        println("generating account domain objects...........")
        HollowWriteStateEngine writeEngine = new HollowWriteStateEngine()
        HollowObjectMapper mapper = new HollowObjectMapper(writeEngine)
        mapper.initializeTypeState(Account)

        HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                .withAPIClassname("AccountAPI")
                .withDestination("${new File(System.getProperty("user.dir"))}/src/main/groovy/com/os/accounts/hollow/domain")
                .withPackageName("com.os.accounts.hollow.domain")
                .withDataModel(writeEngine)
                .build()
        generator.generateSourceFiles()
    }

}
