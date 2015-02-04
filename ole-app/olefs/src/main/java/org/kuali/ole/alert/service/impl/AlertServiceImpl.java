package org.kuali.ole.alert.service.impl;

import org.apache.log4j.Logger;
import org.kuali.ole.alert.bo.ActionListAlertBo;
import org.kuali.ole.alert.bo.AlertBo;
import org.kuali.ole.alert.document.OleMaintenanceDocumentBase;
import org.kuali.ole.alert.document.OlePersistableBusinessObjectBase;
import org.kuali.ole.alert.document.OleTransactionalDocumentBase;
import org.kuali.ole.alert.service.AlertService;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.DocumentBase;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maheswarang on 11/10/14.
 */
public class AlertServiceImpl implements AlertService{
    private static final Logger LOG = Logger.getLogger(AlertHelperServiceImpl.class);
    private BusinessObjectService businessObjectService = KRADServiceLocator.getBusinessObjectService();
    private GroupService groupService = KimApiServiceLocator.getGroupService();

    /**
     * This method is used to update the alert table for the transaction document
     * @param documentBase
     */
    public void saveAlert(DocumentBase documentBase){
        LOG.info("Inside saveAlert for updating the alert table for the document with document Number :  " +documentBase.getDocumentNumber());
        if(documentBase instanceof OleTransactionalDocumentBase){
            OleTransactionalDocumentBase oleTransactionalDocumentBase = (OleTransactionalDocumentBase)documentBase;
        if(oleTransactionalDocumentBase.getAlertBoList()!=null && oleTransactionalDocumentBase.getAlertBoList().size()>0){
            for(AlertBo alertBo : oleTransactionalDocumentBase.getAlertBoList()){
                alertBo.setDocumentId(oleTransactionalDocumentBase.getDocumentNumber());
                updateActionList(alertBo, oleTransactionalDocumentBase);
            }
        }
        }
        else if(documentBase instanceof MaintenanceDocumentBase){
            MaintenanceDocumentBase maintenanceDocumentBase = (MaintenanceDocumentBase)documentBase;
            if(maintenanceDocumentBase.getDocumentDataObject() instanceof OlePersistableBusinessObjectBase){
                OlePersistableBusinessObjectBase olePersistableBusinessObjectBase = (OlePersistableBusinessObjectBase)maintenanceDocumentBase.getDocumentDataObject();
                if(olePersistableBusinessObjectBase!=null && olePersistableBusinessObjectBase.getAlertBoList()!=null && olePersistableBusinessObjectBase.getAlertBoList().size()>0){
                    for(AlertBo alertBo : olePersistableBusinessObjectBase.getAlertBoList()){
                        alertBo.setDocumentId(maintenanceDocumentBase.getDocumentNumber());
                        updateActionList(alertBo, maintenanceDocumentBase);
                    }
                }
            }
        }
    }

    /**
     *This method is used to retrieve the alerts for the given document number
     * @param documentNumber
     * @return
     */
    public List<AlertBo> retrieveAlertList(String documentNumber){
        LOG.info("Inside the retrieveAlertList for getting the alerts related to the document with the document number : "+documentNumber);
        List<AlertBo> alertBos = new ArrayList<AlertBo>();
        List<AlertBo> unModifiableList = new ArrayList<AlertBo>();
        try{
            Map<String,String> actionMap = new HashMap();
            actionMap.put("documentId",documentNumber);
            unModifiableList = (List<AlertBo>)businessObjectService.findMatching(AlertBo.class,actionMap);
        }catch(Exception e){
            LOG.info("Exception occured while getting the alert information to the user : " + documentNumber);
            LOG.error(e,e);
        }
        if(unModifiableList!=null && unModifiableList.size()>0){
            for(AlertBo alertBo : unModifiableList){
                String status = null;
                if(alertBo.getAlertDate()!=null){
                    int dateCompare= alertBo.getAlertDate().compareTo(new Date(System.currentTimeMillis()));
                    if(dateCompare>0){
                        status = "Future";
                    }else if(dateCompare<0){
                        status="Complete";
                    }else if(dateCompare==0){
                        status = "Active";
                    }

                    alertBo.setStatus(status);
                    alertBo.setAlertInitiatorName(getName(alertBo.getAlertInitiatorId()));
                    if(alertBo.getAlertModifierId()!=null){
                        alertBo.setAlertModifierName(getName(alertBo.getAlertModifierId()));
                    }
                    if(alertBo.getReceivingUserId()!=null){
                        alertBo.setReceivingUserName(getName(alertBo.getReceivingUserId()));
                    }
                    if(alertBo.getReceivingGroupId()!=null && !alertBo.getReceivingGroupId().trim().isEmpty()){
                        alertBo.setReceivingGroupName(getGroupName(alertBo.getReceivingGroupId()));
                    }
                    if(alertBo.getReceivingRoleId()!=null){
                        alertBo.setReceivingRoleName(getRoleName(alertBo.getReceivingRoleId()));
                    }
                }

                alertBos.add(alertBo);
            }
        }
        return alertBos;
    }

    /**
     *This method is used to delete the alerts related to the given document number
     * @param documentNumber
     */
    public void deleteAlerts(String documentNumber){
        LOG.info("Inside the deleteAlerts for deleting the alerts related to the document with the document number : "+documentNumber);
        Map<String,String> actionMap = new HashMap<>();
        actionMap.put("documentId",documentNumber);
        businessObjectService.deleteMatching(AlertBo.class, actionMap);
        deleteActionListAlerts(documentNumber);
    }

    /**
     *This method is used to delete the alerts in the action list for the given document number
     * @param documentNumber
     */
    public void deleteActionListAlerts(String documentNumber){
        LOG.info("Inside the deleteActionListAlerts for deleting the alerts in the action list related to the document with the document number : "+documentNumber);
        Map<String,String> actionMap = new HashMap<>();
        actionMap.put("documentId",documentNumber);
        businessObjectService.deleteMatching(ActionListAlertBo.class, actionMap);
    }


    /**
     *This method is used to update the alerts in the action list for the given document
     * @param alertBo
     * @param documentBase
     */
    public void updateActionList(AlertBo alertBo,DocumentBase documentBase){
        LOG.info("Inside the updateActionList for  updating the alerts in the action list related to the document with the document number : "+documentBase.getDocumentNumber() + " for an alert with the alert id : " +alertBo.getAlertId());
        List<String> principalIds = new ArrayList<String>();
        List<ActionListAlertBo> actionListAlertBos = new ArrayList<ActionListAlertBo>();
        ActionListAlertBo actionListAlertBo = new ActionListAlertBo();
        AlertBo alertBo1 = new AlertBo();
        alertBo1.setDocumentId(alertBo.getDocumentId());
        alertBo1.setAlertDate(alertBo.getAlertDate());
        alertBo1.setAlertCreateDate(alertBo.getAlertCreateDate());
        alertBo1.setAlertInitiatorId(alertBo.getAlertInitiatorId());
        alertBo1.setAlertModifiedDate(alertBo.getAlertModifiedDate());
        alertBo1.setAlertInitiatorId(alertBo.getAlertInitiatorId());
        alertBo1.setAlertModifierId(alertBo.getAlertModifierId());
        alertBo1.setAlertNote(alertBo.getAlertNote());
        alertBo1.setReceivingUserId(alertBo.getReceivingUserId());
        alertBo1.setAlertStatus(alertBo.isAlertStatus());
        alertBo1.setAlertApproverId(alertBo.getAlertApproverId());
        alertBo1.setAlertApprovedDate(alertBo.getAlertApprovedDate());
        alertBo1.setReceivingGroupId(alertBo.getReceivingGroupId());
        alertBo1.setReceivingRoleId(alertBo.getReceivingRoleId());
        alertBo1.setReceivingRoleName(alertBo.getReceivingRoleName());
        alertBo1 = businessObjectService.save(alertBo1);

        if(documentBase instanceof  OleTransactionalDocumentBase){
        OleTransactionalDocumentBase oleTransactionalDocumentBase = (OleTransactionalDocumentBase)documentBase;
            actionListAlertBo =  getActionListAlertBo(alertBo1,oleTransactionalDocumentBase.getDocumentHeader().getWorkflowDocument().getDocumentTypeName(),oleTransactionalDocumentBase.getDocumentTitle(),alertBo1.getReceivingUserId());
        }

        if(documentBase instanceof  MaintenanceDocumentBase){
            MaintenanceDocumentBase maintenanceDocumentBase = (MaintenanceDocumentBase)documentBase;
            actionListAlertBo =  getActionListAlertBo(alertBo1,maintenanceDocumentBase.getDocumentHeader().getWorkflowDocument().getDocumentTypeName(),maintenanceDocumentBase.getDocumentTitle(),alertBo1.getReceivingUserId());
        }

        actionListAlertBos.add(actionListAlertBo);
/*        if(alertBo.getReceivingGroupId()!=null){
         List<String> memberIds = groupService.getMemberPrincipalIds(alertBo.getReceivingGroupId());
            principalIds.addAll(memberIds);
        }
        if (alertBo.getReceivingUserId()!=null){
        principalIds.add(alertBo.getReceivingUserId());
        }
        if(principalIds.size()>0){
            for(String receivingUserId : principalIds){
         actionListAlertBo =  getActionListAlertBo(alertBo,oleTransactionalDocumentBase.getDocumentHeader().getWorkflowDocument().getDocumentTypeName(),oleTransactionalDocumentBase.getDocumentTitle(),receivingUserId);
         actionListAlertBo.setAlertId(alertBo1.getAlertId());
         actionListAlertBos.add(actionListAlertBo);
            }
        }*/
        businessObjectService.save(actionListAlertBos);
    }


    public List<AlertBo> getAlertBo(AlertBo alertBo,List<String> receivingPrincipalIds){
        AlertBo alertBo1 = null;
        List<AlertBo> alertBos = new ArrayList<AlertBo>();
        for(String receivingUserId : receivingPrincipalIds){
            alertBo1 = new AlertBo();
            alertBo1.setDocumentId(alertBo.getDocumentId());
            alertBo1.setAlertDate(alertBo.getAlertDate());
            alertBo1.setAlertCreateDate(alertBo.getAlertCreateDate());
            alertBo1.setAlertInitiatorId(alertBo.getAlertInitiatorId());
            alertBo1.setAlertModifiedDate(alertBo.getAlertModifiedDate());
            alertBo1.setAlertInitiatorId(alertBo.getAlertInitiatorId());
            alertBo1.setAlertInitiatorName(alertBo.getAlertInitiatorName());
            alertBo1.setAlertModifierId(alertBo.getAlertModifierId());
            alertBo1.setAlertNote(alertBo.getAlertNote());
            alertBo1.setReceivingUserId(receivingUserId);
            alertBo1.setAlertStatus(alertBo.isAlertStatus());
            alertBo1.setAlertApproverId(alertBo.getAlertApproverId());
            alertBo1.setAlertApprovedDate(alertBo.getAlertApprovedDate());
            alertBo1.setReceivingUserName(getName(receivingUserId));
            alertBo1.setReceivingGroupId(alertBo.getReceivingGroupId());
            alertBo1.setReceivingGroupName(getGroupName(alertBo.getReceivingGroupId()));
            alertBo1.setReceivingRoleId(alertBo.getReceivingRoleId());
            alertBo1.setReceivingRoleName(alertBo.getReceivingRoleName());
            alertBo1.setStatus(alertBo.getStatus());
            alertBos.add(alertBo1);
        }
        return alertBos;
    }



    public ActionListAlertBo getActionListAlertBo(AlertBo alertBo,String documentTypeName,String title,String alertUserId){
        ActionListAlertBo actionListAlertBo = new ActionListAlertBo();
        actionListAlertBo.setDocumentId(alertBo.getDocumentId());
        actionListAlertBo.setActive(alertBo.isAlertStatus());
        actionListAlertBo.setAlertDate(alertBo.getAlertDate());
        actionListAlertBo.setRecordType(documentTypeName);
        actionListAlertBo.setTitle(title);
        actionListAlertBo.setNote(alertBo.getAlertNote());
        actionListAlertBo.setAlertUserId(alertUserId);
        actionListAlertBo.setAlertInitiatorId(alertBo.getAlertInitiatorId());
        actionListAlertBo.setAlertApproverId(alertBo.getAlertApproverId());
        actionListAlertBo.setAlertApprovedDate(alertBo.getAlertApprovedDate());
        actionListAlertBo.setAlertId(alertBo.getAlertId());
        return actionListAlertBo;
    }





    /**
     *
     * @param principalId
     * @return
     */
    public String getName(String principalId){
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
        return   principal.getPrincipalName();
    }


    /**
     * This method returns the group name
     * @param groupId
     * @return
     */
    public String getGroupName(String groupId){
        Group group = groupService.getGroup(groupId);
        return  group.getName();
    }


    public String getRoleName(String roleId){
        Role role = KimApiServiceLocator.getRoleService().getRole(roleId);
        return role.getName();
    }


    public String getPersonId(String personName){
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(personName);
        return principal.getPrincipalId();
    }

    public String getGroupId(String groupName){
        Group group = groupService.getGroupByNamespaceCodeAndName(null,groupName);
        return group.getId();
    }

    public String getRoleId(String roleName){
        Role role = KimApiServiceLocator.getRoleService().getRoleByNamespaceCodeAndName(null,roleName);
        return role.getId();
    }

}
