package org.seasar.teeda.it;

import junit.framework.Test;
import junit.framework.TestCase;

public class AllCoreTests extends TestCase {

    public static Test suite() throws Exception {
        return new FileSystemTestSuiteBuilder().build(AllCoreTests.class);
    }

}
