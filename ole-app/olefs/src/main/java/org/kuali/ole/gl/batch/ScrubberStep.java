/*
 * Copyright 2005-2006 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ole.gl.batch;

import org.kuali.ole.gl.service.ScrubberService;
import org.kuali.ole.sys.batch.AbstractWrappedBatchStep;
import org.kuali.ole.sys.batch.service.WrappedBatchExecutorService.CustomBatchExecutor;

/**
 * A step to run the scrubber process.
 */
public class ScrubberStep extends AbstractWrappedBatchStep {
    private ScrubberService scrubberService;

    @Override
    protected CustomBatchExecutor getCustomBatchExecutor() {
        return new CustomBatchExecutor() {
            public boolean execute() {
                scrubberService.scrubEntries();
                return true;
            }
        };
    }

    public void setScrubberService(ScrubberService scrubberService) {
        this.scrubberService = scrubberService;
    }
}
