/**
 * Copyright (C) 2016 Matthieu Brouillard [http://oss.brouillard.fr/jgitver] (matthieu@brouillard.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.brouillard.oss.jgitver;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class BranchingPolicyTest {
    @Test
    public void fixedbranchName_removes_chars_to_underscore_and_lowercase() {
        String branchName = "MY-BRAnch";
        BranchingPolicy bp = BranchingPolicy.fixedBranchName(branchName);
        
        // The branch policy should not resolve other name
        assertFalse("branch with other name must not be resolved", bp.qualifier("my-branch").isPresent());
        assertFalse("branch with other name must not be resolved", bp.qualifier("SomeOther").isPresent());
        
        Optional<String> resolutionResult = bp.qualifier(branchName);
        
        assertThat(resolutionResult, notNullValue());
        assertTrue("with the good branch name the resolution must occur", resolutionResult.isPresent());
        assertThat("unexpected chars should have been transformed to underscore", resolutionResult.get(), containsString("_"));
        assertThat("no unexpected chars should remain", resolutionResult.get(), not(containsString("-")));
        assertThat("no uppercase chars should remain", resolutionResult.get(), not(matchesPattern("[A-Z]")));
    }
}
