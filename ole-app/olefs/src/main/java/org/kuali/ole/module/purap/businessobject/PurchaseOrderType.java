/*
 * Copyright 2011 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ole.module.purap.businessobject;

import org.kuali.rice.core.api.mo.common.active.MutableInactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import java.math.BigDecimal;

public class PurchaseOrderType extends PersistableBusinessObjectBase implements MutableInactivatable {

    private BigDecimal purchaseOrderTypeId;
    private String purchaseOrderType;
    private String description;
    private boolean active;

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public BigDecimal getPurchaseOrderTypeId() {
        return purchaseOrderTypeId;
    }


    public void setPurchaseOrderTypeId(BigDecimal purchaseOrderTypeId) {
        this.purchaseOrderTypeId = purchaseOrderTypeId;
    }


    public String getPurchaseOrderType() {
        return purchaseOrderType;
    }


    public void setPurchaseOrderType(String purchaseOrderType) {
        this.purchaseOrderType = purchaseOrderType;
    }


    @Override
    public boolean isActive() {
        return active;
    }


    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

}



