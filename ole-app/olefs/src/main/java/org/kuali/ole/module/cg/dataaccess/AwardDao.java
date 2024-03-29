/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.ole.module.cg.dataaccess;

import java.util.Collection;

import org.kuali.ole.module.cg.businessobject.Award;
import org.kuali.ole.module.cg.document.ProposalAwardCloseDocument;

/**
 * Implementations of this interface provide access to persisted Awards.
 */
public interface AwardDao {

    /**
     * Get a {@link Collection} of {@link Award}s to close. This is used by the {@link CloseBatchStep}.
     * 
     * @param c
     * @return
     */
    public Collection<Award> getAwardsToClose(ProposalAwardCloseDocument c);

    /**
     * Delete all Awards.
     */
    public void deleteAll();
}
