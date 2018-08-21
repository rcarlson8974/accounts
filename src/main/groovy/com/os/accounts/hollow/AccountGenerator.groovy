package com.os.accounts.hollow

import com.netflix.hollow.api.codegen.HollowAPIGenerator
import com.netflix.hollow.core.write.HollowWriteStateEngine
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper
import com.os.accounts.domain.Account

class AccountGenerator {

    static void generate() {
        HollowWriteStateEngine writeEngine = new HollowWriteStateEngine()
        HollowObjectMapper mapper = new HollowObjectMapper(writeEngine)
        mapper.initializeTypeState(Account)

        def baseDir = System.getProperty("user.dir")

        HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                .withAPIClassname("AccountAPI")
                .withDestination("//${baseDir}/src/main/groovy/com/os/accounts/hollow/domain")
                .withPackageName("com.os.accounts.hollow.domain")
                .withDataModel(writeEngine)
                .build()
        generator.generateSourceFiles()

    }
}
