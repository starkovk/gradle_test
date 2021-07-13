/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api

import groovy.transform.SelfType
import spock.lang.Issue

@SelfType(AbstractDomainObjectContainerIntegrationTest)
trait AbstractTaskContainerIntegrationTest {
    String makeContainer() {
        return "tasks"
    }

    String getContainerStringRepresentation() {
        return "task set"
    }

    static String getContainerType() {
        return "DefaultTaskContainer"
    }
}

class QueryAndMutateFromAllTaskContainerIntegrationTest extends QueryAndMutateFromAllDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class QueryAndMutateFromCreateTaskContainerIntegrationTest extends QueryAndMutateFromCreateDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class QueryAndMutateFromGetByNameTaskContainerIntegrationTest extends QueryAndMutateFromGetByNameDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class QueryAndMutateFromWithTypeAllTaskContainerIntegrationTest extends QueryAndMutateFromWithTypeAllDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class QueryAndMutateFromWithTypeGetByNameTaskContainerIntegrationTest extends QueryAndMutateFromWithTypeGetByNameDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class QueryTaskContainerIntegrationTest extends AbstractQueryDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromConfigureEachTaskContainerIntegrationTest extends MutationFailureFromConfigureEachDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromMatchingConfigureEachTaskContainerIntegrationTest extends MutationFailureFromMatchingConfigureEachDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromProviderConfigureTaskContainerIntegrationTest extends MutationFailureFromProviderConfigureDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromProviderConfigureRealizedTaskContainerIntegrationTest extends MutationFailureFromProviderConfigureRealizedDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromRegisterTaskContainerIntegrationTest extends MutationFailureFromRegisterDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutationFailureFromWithTypeConfigureEachTaskContainerIntegrationTest extends MutationFailureFromWithTypeConfigureEachDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class MutatingTaskContainerInHookIntegrationTest extends AbstractMutatingDomainObjectContainerInHookIntegrationTest implements AbstractTaskContainerIntegrationTest {
}

class TaskContainerIntegrationTest extends AbstractDomainObjectContainerIntegrationTest implements AbstractTaskContainerIntegrationTest {

    def "chained lookup of tasks.withType.matching"() {
        buildFile """
            tasks.withType(Copy).matching({ it.name.endsWith("foo") }).all { task ->
                assert task.path in [':foo']
            }

            tasks.register("foo", Copy)
            tasks.register("bar", Copy)
            tasks.register("foobar", Delete)
            tasks.register("barfoo", Delete)
        """
        expect:
        succeeds "help"
    }

    @Issue("https://github.com/gradle/gradle/issues/9446")
    def "chained lookup of tasks.matching.withType"() {
        buildFile """
            tasks.matching({ it.name.endsWith("foo") }).withType(Copy).all { task ->
                assert task.path in [':foo']
            }

            tasks.register("foo", Copy)
            tasks.register("bar", Copy)
            tasks.register("foobar", Delete)
            tasks.register("barfoo", Delete)
        """
        expect:
        succeeds "help"
    }
}
