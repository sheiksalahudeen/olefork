package org.kuali.ole.select.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ole.OLEConstants;
import org.kuali.ole.alert.controller.OleTransactionalDocumentControllerBase;
import org.kuali.ole.batch.bo.OLEBatchProcessProfileBo;
import org.kuali.ole.coa.businessobject.*;
import org.kuali.ole.docstore.common.client.DocstoreClientLocator;
import org.kuali.ole.docstore.common.document.*;
import org.kuali.ole.docstore.common.document.content.bib.marc.BibMarcRecord;
import org.kuali.ole.docstore.common.document.content.instance.OleHoldings;
import org.kuali.ole.docstore.common.document.content.instance.xstream.HoldingOlemlRecordProcessor;
import org.kuali.ole.module.purap.PurapConstants;
import org.kuali.ole.module.purap.PurapKeyConstants;
import org.kuali.ole.module.purap.businessobject.PurchaseOrderType;
import org.kuali.ole.module.purap.document.service.RequisitionService;
import org.kuali.ole.select.bo.OLEEResourceOrderRecord;
import org.kuali.ole.select.batch.service.RequisitionCreateDocumentService;
import org.kuali.ole.select.bo.*;
import org.kuali.ole.select.businessobject.OleCopy;
import org.kuali.ole.select.businessobject.OleDocstoreResponse;
import org.kuali.ole.select.document.*;
import org.kuali.ole.select.bo.OLECreatePO;
import org.kuali.ole.select.form.OLEEResourceRecordForm;
import org.kuali.ole.select.gokb.*;
import org.kuali.ole.select.service.OleReqPOCreateDocumentService;
import org.kuali.ole.service.*;
import org.kuali.ole.service.impl.OLEEResourceSearchServiceImpl;
import org.kuali.ole.sys.context.SpringContext;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.coreservice.api.CoreServiceApiServiceLocator;
import org.kuali.rice.coreservice.api.parameter.Parameter;
import org.kuali.rice.coreservice.api.parameter.ParameterKey;
import org.kuali.rice.kew.actiontaken.ActionTakenValue;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.AdHocRoutePerson;
import org.kuali.rice.krad.bo.AdHocRouteRecipient;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintenanceLock;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.KualiRuleService;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.TransactionalDocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasane
 * Date: 6/21/13
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/oleERSController")
public class OLEEResourceRecordController extends OleTransactionalDocumentControllerBase {


    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#(javax.servlet.http.HttpServletRequest)
     */
    private static final Logger LOG = Logger.getLogger(OLEEResourceRecordController.class);

    private OleLicenseRequestWebService oleLicenseRequestWebService = null;
    private KualiRuleService kualiRuleService;
    private BusinessObjectService businessObjectService;
    private OLEEResourceSearchService oleEResourceSearchService = null;
    private DocstoreClientLocator docstoreClientLocator;
    private OLEEResourceHelperService oleeResourceHelperService = new OLEEResourceHelperService();
    private OleReqPOCreateDocumentService oleReqPOCreateDocumentService;
    private RequisitionCreateDocumentService requisitionCreateDocumentService;

    public OLEEResourceHelperService getOleeResourceHelperService() {
        if(oleeResourceHelperService == null) {
            oleeResourceHelperService = new OLEEResourceHelperService();
        }
        return oleeResourceHelperService;
    }

    public DocstoreClientLocator getDocstoreClientLocator() {
        if (docstoreClientLocator == null) {
            docstoreClientLocator = SpringContext.getBean(DocstoreClientLocator.class);
        }
        return docstoreClientLocator;
    }

    public OleReqPOCreateDocumentService getOleReqPOCreateDocumentService() {
        if (oleReqPOCreateDocumentService == null) {
            oleReqPOCreateDocumentService = SpringContext.getBean(OleReqPOCreateDocumentService.class);
        }
        return oleReqPOCreateDocumentService;
    }

    public RequisitionCreateDocumentService getRequisitionCreateDocumentService() {
        if (requisitionCreateDocumentService == null) {
            requisitionCreateDocumentService = SpringContext.getBean(RequisitionCreateDocumentService.class);
        }
        return requisitionCreateDocumentService;
    }

    /**
     * This method will return new Instance of OLEEResourceRecordForm.
     *
     * @return OLEEResourceRecordForm.
     */
    @Override
    protected TransactionalDocumentFormBase createInitialForm(HttpServletRequest request) {
        OLEEResourceRecordForm oleeResourceRecordForm = new OLEEResourceRecordForm();
        oleeResourceRecordForm.setStatusDate(new Date(System.currentTimeMillis()).toString());
        oleeResourceRecordForm.setDocumentDescription(OLEConstants.OLEEResourceRecord.NEW_E_RESOURCE_REC);
        oleeResourceRecordForm.setUrl(ConfigContext.getCurrentContextConfig().getProperty("ole.fs.url.base") + "/ole-kr-krad/oleERSController?viewId=OLEEResourceRecordView&methodToCall=docHandler&command=initiate&documentClass=org.kuali.ole.select.document.OLEEResourceRecordDocument");
        return oleeResourceRecordForm;
    }


    @Override
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response)throws Exception{
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) form;
        oleeResourceRecordForm.setSearchUrl(ConfigContext.getCurrentContextConfig().getProperty("ole.fs.url.base") + "/ole-kr-krad/oleERSController?viewId=OLEEResourceSearchView&methodToCall=start");
        request.getSession().setAttribute("formKeyValue", oleeResourceRecordForm.getFormKey());
        ModelAndView  modelAndView = super.docHandler(oleeResourceRecordForm,result,request,response);
        OLEEResourceRecordForm kualiForm = (OLEEResourceRecordForm) modelAndView.getModel().get("KualiForm");
        HttpSession session = request.getSession();
        //session.removeAttribute("createChildEResource");
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) kualiForm.getDocument();
        if(session.getAttribute("createChildEResource") != null){
            String oleeResourceInstancesIdentifier = (String) session.getAttribute("oleeResourceInstancesIdentifier");
            String[] identifiers = oleeResourceInstancesIdentifier.split(",");
            Map ids = new HashMap();
            for(String identifier : identifiers){
                ids.put("oleEResourceInstanceId",identifier);
            }
            List<OLEEResourceInstance> oleeResourceInstances = (List<OLEEResourceInstance>) getBusinessObjectService().findMatching(OLEEResourceInstance.class, ids);
            oleeResourceRecordDocument.setOleERSInstances(oleeResourceInstances);
        }
        oleeResourceRecordDocument.geteRSInstances().addAll(oleeResourceRecordDocument.getOleERSInstances());
        for (OLELinkedEresource oleLinkedEresource : oleeResourceRecordDocument.getOleLinkedEresources()) {
            if (oleLinkedEresource.getRelationShipType().equalsIgnoreCase("child")) {
                //Displaying the child EResource instance and Licence info in parent Eresource.
                if (oleLinkedEresource.getOleeResourceRecordDocument().getOleERSInstances() != null) {
                    oleeResourceRecordDocument.geteRSInstances().addAll(oleLinkedEresource.getOleeResourceRecordDocument().getOleERSInstances());
                }
                for (OLEEResourceLicense oleeResourceLicense : oleLinkedEresource.getOleeResourceRecordDocument().getOleERSLicenseRequests()) {
                    oleeResourceRecordDocument.getOleERSLicenseRequests().add(oleeResourceLicense);
                }
            }
        }
        getOleEResourceSearchService().getPOInvoiceForERS(oleeResourceRecordDocument);
        /*Displaying the child EResource Po's and invoice in parent E-Resource.*/
        //oleEResourceSearchService = new OLO
        if(oleEResourceSearchService != null){
            oleEResourceSearchService.getPOInvoiceForERS(oleeResourceRecordDocument);
        }
        if (oleeResourceRecordDocument.getOleERSIdentifier() == null) {
            String noticePeriod = oleEResourceSearchService.getParameter("NOTICE_PERIOD", OLEConstants.ERESOURCE_CMPNT);
            String alertEnabled = oleEResourceSearchService.getParameter("ALERT_ENABLED", OLEConstants.ERESOURCE_CMPNT);
            String user = oleEResourceSearchService.getParameter("USER", OLEConstants.ERESOURCE_CMPNT);
            oleeResourceRecordDocument.setRenewalNoticePeriod(noticePeriod);
            if (alertEnabled.equalsIgnoreCase("Y")) {
                oleeResourceRecordDocument.setRenewalAlertEnabled(true);
            } else {
                oleeResourceRecordDocument.setRenewalAlertEnabled(false);
            }
            oleeResourceRecordDocument.setRecipientId(user);
        }
        return modelAndView;
    }

    /**
     * This method takes the initial request when click on E-Resource Record Screen.
     *
     * @param form
     * @return ModelAndView
     */
    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Start -- Start Method of OlePatronRecordForm");
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleeResourceRecordForm.getDocument();
        oleeResourceRecordDocument.setStatusDate(new Date(System.currentTimeMillis()).toString());
        List<OLESearchCondition> oleSearchConditions = oleeResourceRecordForm.getOleSearchParams().getSearchFieldsList();
        for (OLESearchCondition oleSearchCondition : oleSearchConditions) {
            oleSearchCondition.setOperator(OLEConstants.OLEEResourceRecord.AND);
        }
        return super.start(oleeResourceRecordForm, result, request, response);
    }

    /**
     * This method populates date of the eventlog object thereby adding to the existing list.
     *
     * @param uifForm
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=addEventLogLine")
    public ModelAndView addEventLogLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        //String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        /*BindingInfo addLineBindingInfo = (BindingInfo) form.getViewPostMetadata().getComponentPostData(
                selectedCollectionId, UifConstants.PostMetadata.ADD_LINE_BINDING_INFO);*/
        CollectionGroup collectionGroup = uifForm.getPostedView().getViewIndex().getCollectionGroupByPath(
                selectedCollectionPath);
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        Object eventObject = ObjectPropertyUtils.getPropertyValue(uifForm, addLinePath);
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        OLEEResourceEventLog oleERSEventLog = (OLEEResourceEventLog) eventObject;
        if (!getOleEResourceSearchService().addAttachmentFile(oleERSEventLog, "OLEEResourceRecordView-EventLogSection")) {
            return super.navigate(form, result, request, response);
        }
        //oleERSEventLog.setEventType(OLEConstants.OleLicenseRequest.USER);
        if(oleERSEventLog.getLogTypeName().equalsIgnoreCase("Event")){
            oleERSEventLog.setProblemTypeId(null);
            oleERSEventLog.setEventStatus(null);
            oleERSEventLog.setEventResolvedDate(null);
            oleERSEventLog.setEventResolution(null);
        }else if(oleERSEventLog.getLogTypeName().equalsIgnoreCase("Problem")){
            oleERSEventLog.setEventTypeId(null);
        }
        oleERSEventLog.setCurrentTimeStamp();
        oleERSEventLog.setOleERSIdentifier(oleeResourceRecordDocument.getDocumentNumber());
        oleERSEventLog.setSaveFlag(true);
        return addLine(uifForm, result, request, response);
    }

    /**
     * This method populates date of the eventlog object thereby adding to the existing list.
     *
     * @param uifForm
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=deleteEventLogLine")
    public ModelAndView deleteEventLogLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        String selectedLineIndex = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        OLEEResourceEventLog oleERSEventLog = oleeResourceRecordDocument.getOleERSEventLogs().get(Integer.parseInt(selectedLineIndex));
        //getBusinessObjectService().delete(oleERSEventLog);
        return deleteLine(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=editEventLogLine")
    public ModelAndView editEventLogLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        String selectedLineIndex = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        OLEEResourceEventLog oleERSEventLog = oleeResourceRecordDocument.getOleERSEventLogs().get(Integer.parseInt(selectedLineIndex));
        oleERSEventLog.setSaveFlag(false);
        return super.navigate(form,result,request,response);
    }

    @RequestMapping(params = "methodToCall=saveEvent")
    public ModelAndView saveEvent(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        String selectedLineIndex = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        OLEEResourceEventLog oleERSEventLog = oleeResourceRecordDocument.getOleERSEventLogs().get(Integer.parseInt(selectedLineIndex));
        return super.navigate(form,result,request,response);
    }
    /**
     * This method Creates the License Request Document and link to the E-Resource document
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=performCreateLicenseRequest")
    public ModelAndView performCreateLicenseRequest(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("performCreateLicenseRequest method starts");
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleeResourceRecordForm.getDocument();
        OleLicenseRequestBo oleLicenseRequestBo = getOleLicenseRequestService().createLicenseRequest(
                oleeResourceRecordDocument.getDocumentNumber(), null);
        OLEEResourceLicense oleeResourceLicense = new OLEEResourceLicense();
        oleeResourceLicense.setLicenseDocumentNumber(oleLicenseRequestBo.getLicenseDocumentNumber());
        oleeResourceLicense.setDocumentRouteHeaderValue(oleLicenseRequestBo.getDocumentRouteHeaderValue());
       /* Map<String,String> searchCriteria = new HashMap<String,String>();
        searchCriteria.put(OLEConstants.DOC_ID,oleLicenseRequestBo.getLicenseDocumentNumber());*/
        /*DocumentRouteHeaderValue documentHeader= (DocumentRouteHeaderValue)  getBusinessObjectService().findByPrimaryKey
                (DocumentRouteHeaderValue.class,searchCriteria);*/
        /*DocumentRouteHeaderValue documentHeader= KEWServiceLocator.getRouteHeaderService().getRouteHeader(oleLicenseRequestBo.getLicenseDocumentNumber(), true);
        oleLicenseRequestBo.setDocumentRouteHeaderValue(documentHeader);*/

        List<OLEEResourceInstance> listOfERInstances = oleeResourceRecordDocument.getOleERSInstances();
        List<OleLicenseRequestItemTitle> oleLicenseRequestItemTitles = new ArrayList<>();
        for (OLEEResourceInstance oleeResourceInstance : listOfERInstances) {
            OleLicenseRequestItemTitle oleLicenseRequestItemTitle = new OleLicenseRequestItemTitle();
            oleLicenseRequestItemTitle.setItemUUID(oleeResourceInstance.getBibId());
            oleLicenseRequestItemTitle.setOleLicenseRequestId(oleLicenseRequestBo.getOleLicenseRequestId());
            oleLicenseRequestItemTitle.setOleLicenseRequestBo(oleLicenseRequestBo);
            oleLicenseRequestItemTitles.add(oleLicenseRequestItemTitle);
        }
        oleLicenseRequestBo.setOleLicenseRequestItemTitles(oleLicenseRequestItemTitles);
        oleeResourceLicense.setOleLicenseRequestBo(oleLicenseRequestBo);
        oleeResourceRecordDocument.getOleERSLicenseRequests().add(oleeResourceLicense);

        //oleeResourceRecordDocument.
        return getUIFModelAndView(oleeResourceRecordForm);
    }

    /**
     * Saves the document instance contained on the form
     *
     * @param form - document form base containing the document instance that will be saved
     * @return ModelAndView
     */
    @Override
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        String eResId = oleeResourceRecordDocument.getOleERSIdentifier();
        oleeResourceRecordDocument.setStatusDate(oleERSform.getStatusDate().toString());
        if (oleeResourceRecordDocument.getTitle() != null) {
            if (oleeResourceRecordDocument.getTitle().length() < 40) {
                oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(oleeResourceRecordDocument.getTitle());
            } else {
                String documentDescription = oleeResourceRecordDocument.getTitle().substring(0, 39);
                oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(documentDescription);
            }
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("createChildEResource") != null) {
            ModelAndView modelAndView = super.save(oleERSform, result, request, response);
            oleERSform = (OLEEResourceRecordForm) modelAndView.getModel().get("KualiForm");
            oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
            session.removeAttribute("createChildEResource");
            String oleeResourceInstancesIdentifier = (String) session.getAttribute("oleeResourceInstancesIdentifier");
            String[] identifiers = oleeResourceInstancesIdentifier.split(",");
            List<OLEEResourceInstance> ersInstances = new ArrayList<>();
            for (String identifier : identifiers) {
                Map<String, String> criteriaMap = new HashMap<>();
                criteriaMap.put(OLEConstants.INSTANCE_ID, identifier);
                List<OLEEResourceInstance> instances = (List<OLEEResourceInstance>) getBusinessObjectService().findMatching(OLEEResourceInstance.class, criteriaMap);
                ersInstances.add(instances.get(0));
                criteriaMap = new HashMap<>();
                criteriaMap.put(OLEConstants.INSTANCE_ID, identifier);
                criteriaMap.put("oleERSIdentifier", (String) session.getAttribute("oleERSIdentifier"));
                getBusinessObjectService().deleteMatching(OLEEResourceInstance.class, criteriaMap);
                OLEEditorResponse oleEditorResponse = new OLEEditorResponse();
                oleEditorResponse.setLinkedInstanceId(identifier);
                oleEditorResponse.setTokenId(oleeResourceRecordDocument.getDocumentNumber());
                HashMap<String, OLEEditorResponse> oleEditorResponseMap = new HashMap<String, OLEEditorResponse>();
                oleEditorResponseMap.put(oleeResourceRecordDocument.getDocumentNumber(), oleEditorResponse);
                OleDocstoreResponse.getInstance().setEditorResponse(oleEditorResponseMap);
                String documentNumber = oleeResourceRecordDocument.getDocumentNumber();
                oleeResourceRecordDocument.setSelectInstance(OLEConstants.OLEEResourceRecord.LINK_EXIST_INSTANCE);
                try {
                    getOleEResourceSearchService().getNewInstance(oleeResourceRecordDocument, documentNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            oleeResourceRecordDocument.setOleERSInstances(ersInstances);
            String oleERSIdentifier = (String) session.getAttribute("oleERSIdentifier");
            Map<String, String> tempId = new HashMap<String, String>();
            tempId.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleERSIdentifier);
            OLEEResourceRecordDocument parentDocument = (OLEEResourceRecordDocument) getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, tempId);
            Person principalPerson = SpringContext.getBean(PersonService.class).getPerson(GlobalVariables.getUserSession().getPerson().getPrincipalId());
            try {
                parentDocument.getDocumentHeader().setWorkflowDocument(KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(parentDocument.getDocumentNumber(), principalPerson));
            } catch (WorkflowException e) {
                e.printStackTrace();
            }
            OLELinkedEresource linkedEresource = new OLELinkedEresource();
            //linkedEresource.setOleeResourceRecordDocument(parentDocument);
            linkedEresource.setOleeResourceRecordDocument(oleeResourceRecordDocument);
            linkedEresource.setRelationShipType("parent");
            linkedEresource.setLinkedERSIdentifier(oleERSIdentifier);
           /* linkedEresource.seteResourceDocNum(parentDocument.getDocumentNumber());
            linkedEresource.seteResourceName(parentDocument.getTitle());*/
            oleeResourceRecordDocument.getOleLinkedEresources().add(linkedEresource);
            OLELinkedEresource eResource = new OLELinkedEresource();
            eResource.setOleeResourceRecordDocument(oleeResourceRecordDocument);
            eResource.setRelationShipType("child");
            eResource.setLinkedERSIdentifier(oleeResourceRecordDocument.getOleERSIdentifier());
            /*eResource.seteResourceDocNum(oleeResourceRecordDocument.getDocumentNumber());
            eResource.seteResourceName(oleeResourceRecordDocument.getTitle());*/
            parentDocument.getOleLinkedEresources().add(eResource);
            oleEResourceSearchService.getPOInvoiceForERS(parentDocument);
            KRADServiceLocatorWeb.getDocumentService().updateDocument(parentDocument);
            oleEResourceSearchService.getPOInvoiceForERS(oleeResourceRecordDocument);
            KRADServiceLocatorWeb.getDocumentService().updateDocument(oleeResourceRecordDocument);
            return super.reload(oleERSform, result, request, response);
        }
        /*List<OLEEResourceInstance> oleeResourceInstances = (List<OLEEResourceInstance>) session.getAttribute("oleeResourceInstances");

        if(oleeResourceInstances != null && oleeResourceInstances.size() > 0) {
            session.removeAttribute("oleeResourceInstances");
            oleeResourceRecordDocument.setOleERSInstances(oleeResourceInstances);
            try {
                getOleEResourceSearchService().saveEResourceInstanceToDocstore(oleeResourceRecordDocument);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        List<OLEMaterialTypeList> oleMaterialTypeList = oleeResourceRecordDocument.getOleMaterialTypes();
        List<OLEFormatTypeList> oleFormatTypeLists = oleeResourceRecordDocument.getOleFormatTypes();
        List<OLEContentTypes> oleContentTypeList = oleeResourceRecordDocument.getOleContentTypes();
        List<String> instanceId = new ArrayList<String>();
        /*boolean oleERSFlag = false;
        oleERSFlag &= getKualiRuleService().applyRules(new OLEMaterialTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleMaterialTypes()));
        oleERSFlag &= getKualiRuleService().applyRules(new OLEContentTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleContentTypes()));
        if (oleERSFlag) {
        return getUIFModelAndView(oleERSform);
        }*/
        boolean flag = false;
        boolean datesFlag = true;
        flag = getOleEResourceSearchService().validateEResourceDocument(oleeResourceRecordDocument);
        datesFlag &= getOleEResourceSearchService().validateCoverageStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validateCoverageEndDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessEndDates(oleeResourceRecordDocument,oleERSform);
        if (flag) {
            return getUIFModelAndView(oleERSform);
        }
        if (!datesFlag) {
            return getUIFModelAndView(oleERSform);
        }
        String fileName = oleeResourceRecordDocument.getDocumentNumber();
        if (oleERSform.isCreateInstance()) {
            getOleEResourceSearchService().getNewInstance(oleeResourceRecordDocument, fileName);
            oleERSform.setCreateInstance(false);
        }
        oleERSform.setBibId(null);
        oleERSform.setInstanceId(null);
        oleERSform.setLinkInstance(false);
        if (oleeResourceRecordDocument.getOleERSIdentifier() != null && !oleeResourceRecordDocument.getOleERSIdentifier().isEmpty()) {
            oleeResourceRecordDocument = getOleEResourceSearchService().getNewOleERSDoc(oleeResourceRecordDocument);
            Map<String, String> tempId = new HashMap<String, String>();
            tempId.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleeResourceRecordDocument.getOleERSIdentifier());
            OLEEResourceRecordDocument tempDocument = (OLEEResourceRecordDocument) getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, tempId);
            int instancesSize = tempDocument.getOleERSInstances().size();
            int instanceSize = oleeResourceRecordDocument.getOleERSInstances().size();
            if (!oleERSform.isDefaultDatesFlag() && oleERSform.getPageId() != null && oleERSform.getPageId().equalsIgnoreCase("OLEEResourceRecordView-E-ResourceInstanceTab")) {
                if (tempDocument.iseInstanceFlag() && instancesSize >= instanceSize && !oleERSform.isRemoveInstanceFlag()) {
                    try {
                        super.reload(oleERSform, result, request, response);
                    } catch (Exception e) {
                        LOG.error("exception while reloading the e-resource document" + e.getMessage());
                        throw new RuntimeException("exception while reloading the e-resource document", e);
                    }
                }
            }
            oleERSform.setRemoveInstanceFlag(false);
            oleERSform.setDefaultDatesFlag(false);
        }
        getOleEResourceSearchService().processEventAttachments(oleeResourceRecordDocument.getOleERSEventLogs());
        if(oleeResourceRecordDocument.getCurrentSubscriptionEndDate()!=null){
            if(oleeResourceRecordDocument.isRenewalAlertEnabled()){
                if(oleeResourceRecordDocument.getRecipientId()==null){
                    GlobalVariables.getMessageMap().putError(OLEConstants.OLEEResourceRecord.RECIPIENT_ID, OLEConstants.OLEEResourceRecord.ERROR_RECIPIENT_ID);
                    return getUIFModelAndView(oleERSform);
                }
            }
        }
        ModelAndView  modelAndView = super.save(oleERSform, result, request, response);
        OLEEResourceRecordForm kualiForm = (OLEEResourceRecordForm) modelAndView.getModel().get("KualiForm");
        OLEEResourceRecordDocument resourceRecordDocument = (OLEEResourceRecordDocument) kualiForm.getDocument();
        resourceRecordDocument.geteRSInstances().clear();
        resourceRecordDocument.geteRSInstances().addAll(resourceRecordDocument.getOleERSInstances());
        for (OLELinkedEresource oleLinkedEresource : resourceRecordDocument.getOleLinkedEresources()) {
            if (oleLinkedEresource.getRelationShipType().equalsIgnoreCase("child")) {
                //Displaying the child EResource instance and Licence info in parent Eresource.
                if (oleLinkedEresource.getOleeResourceRecordDocument().getOleERSInstances() != null) {
                    resourceRecordDocument.geteRSInstances().addAll(oleLinkedEresource.getOleeResourceRecordDocument().getOleERSInstances());
                }
                for (OLEEResourceLicense oleeResourceLicense : oleLinkedEresource.getOleeResourceRecordDocument().getOleERSLicenseRequests()) {
                    resourceRecordDocument.getOleERSLicenseRequests().add(oleeResourceLicense);
                }
            }
        }
        getOleEResourceSearchService().getPOInvoiceForERS(oleeResourceRecordDocument);
        /*if(StringUtils.isNotEmpty(eResId)) {
            getOleeResourceHelperService().insertOrUpdateGokbElementsForEResource(oleeResourceRecordDocument, true);
        }
        else {
            getOleeResourceHelperService().insertOrUpdateGokbElementsForEResource(oleeResourceRecordDocument, false);
        }*/

        return super.save(oleERSform, result, request, response);
    }

    /**
     * Routes the document instance contained on the form
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        oleeResourceRecordDocument.setStatusDate(oleERSform.getStatusDate().toString());
        if (oleeResourceRecordDocument.getTitle().length() < 40) {
            oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(oleeResourceRecordDocument.getTitle());
        } else {
            String documentDescription = oleeResourceRecordDocument.getTitle().substring(0, 39);
            oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(documentDescription);
        }
        List<OLEMaterialTypeList> oleMaterialTypeList = oleeResourceRecordDocument.getOleMaterialTypes();
        List<OLEFormatTypeList> oleFormatTypeLists = oleeResourceRecordDocument.getOleFormatTypes();
        List<OLEContentTypes> oleContentTypeList = oleeResourceRecordDocument.getOleContentTypes();
        List<String> instanceId = new ArrayList<String>();
        /*boolean oleERSFlag = false;
        oleERSFlag &= getKualiRuleService().applyRules(new OLEMaterialTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleMaterialTypes()));
        oleERSFlag &= getKualiRuleService().applyRules(new OLEContentTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleContentTypes()));
        if (oleERSFlag) {
        return getUIFModelAndView(oleERSform);
        }*/
        boolean flag = false;
        boolean datesFlag = true;
        flag=getOleEResourceSearchService().validateEResourceDocument(oleeResourceRecordDocument);
        datesFlag &= getOleEResourceSearchService().validateCoverageStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validateCoverageEndDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessEndDates(oleeResourceRecordDocument,oleERSform);
        if (flag) {
            return getUIFModelAndView(oleERSform);
        }
        if (!datesFlag) {
            return getUIFModelAndView(oleERSform);
        }
        String fileName = oleeResourceRecordDocument.getDocumentNumber();
        if (oleERSform.isCreateInstance()) {
            try {
                getOleEResourceSearchService().getNewInstance(oleeResourceRecordDocument, fileName);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            oleERSform.setCreateInstance(false);
        }
        oleERSform.setBibId(null);
        oleERSform.setInstanceId(null);
        oleERSform.setLinkInstance(false);
        if (oleeResourceRecordDocument.getOleERSIdentifier() != null && !oleeResourceRecordDocument.getOleERSIdentifier().isEmpty()) {
            oleeResourceRecordDocument = getOleEResourceSearchService().getNewOleERSDoc(oleeResourceRecordDocument);
            Map<String, String> tempId = new HashMap<String, String>();
            tempId.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleeResourceRecordDocument.getOleERSIdentifier());
            OLEEResourceRecordDocument tempDocument = (OLEEResourceRecordDocument) getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, tempId);
            int instancesSize = tempDocument.getOleERSInstances().size();
            int instanceSize = oleeResourceRecordDocument.getOleERSInstances().size();
            if (tempDocument.iseInstanceFlag() && instancesSize > instanceSize && !oleERSform.isRemoveInstanceFlag()) {
                try {
                    super.reload(oleERSform, result, request, response);
                } catch (Exception e) {
                    LOG.error("Exception while reloading the e-resource document"+e.getMessage());
                }
            }
            oleERSform.setRemoveInstanceFlag(false);
        }
        getOleEResourceSearchService().processEventAttachments(oleeResourceRecordDocument.getOleERSEventLogs());
        return super.route(oleERSform, result, request, response);
    }

    /**
     * Performs the approve workflow action on the form document instance
     *
     * @param form - document form base containing the document instance that will be approved
     * @return ModelAndView
     */
    @Override
    @RequestMapping(params = "methodToCall=approve")
    public ModelAndView approve(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        oleeResourceRecordDocument.setStatusDate(oleERSform.getStatusDate().toString());
        if (oleeResourceRecordDocument.getTitle().length() < 40) {
            oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(oleeResourceRecordDocument.getTitle());
        } else {
            String documentDescription = oleeResourceRecordDocument.getTitle().substring(0, 39);
            oleeResourceRecordDocument.getDocumentHeader().setDocumentDescription(documentDescription);
        }
        List<OLEMaterialTypeList> oleMaterialTypeList = oleeResourceRecordDocument.getOleMaterialTypes();
        List<OLEFormatTypeList> oleFormatTypeLists = oleeResourceRecordDocument.getOleFormatTypes();
        List<OLEContentTypes> oleContentTypeList = oleeResourceRecordDocument.getOleContentTypes();
        List<String> instanceId = new ArrayList<String>();
        /*boolean oleERSFlag = false;
        oleERSFlag &= getKualiRuleService().applyRules(new OLEMaterialTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleMaterialTypes()));
        oleERSFlag &= getKualiRuleService().applyRules(new OLEContentTypeEvent(oleeResourceRecordDocument,oleeResourceRecordDocument.getOleContentTypes()));
        if (oleERSFlag) {
        return getUIFModelAndView(oleERSform);
        }*/
        boolean flag = false;
        boolean datesFlag = true;
        flag=getOleEResourceSearchService().validateEResourceDocument(oleeResourceRecordDocument);
        datesFlag &= getOleEResourceSearchService().validateCoverageStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validateCoverageEndDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessStartDates(oleeResourceRecordDocument,oleERSform);
        datesFlag &= getOleEResourceSearchService().validatePerpetualAccessEndDates(oleeResourceRecordDocument,oleERSform);
        if (flag) {
            return getUIFModelAndView(oleERSform);
        }
        if (!datesFlag) {
            return getUIFModelAndView(oleERSform);
        }
        String fileName = oleeResourceRecordDocument.getDocumentNumber();
        if (oleERSform.isCreateInstance()) {
            getOleEResourceSearchService().getNewInstance(oleeResourceRecordDocument, fileName);
            oleERSform.setCreateInstance(false);
        }
        oleERSform.setBibId(null);
        oleERSform.setInstanceId(null);
        oleERSform.setLinkInstance(false);
        if (oleeResourceRecordDocument.getOleERSIdentifier() != null && !oleeResourceRecordDocument.getOleERSIdentifier().isEmpty()) {
            oleeResourceRecordDocument = getOleEResourceSearchService().getNewOleERSDoc(oleeResourceRecordDocument);
            Map<String, String> tempId = new HashMap<String, String>();
            tempId.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleeResourceRecordDocument.getOleERSIdentifier());
            OLEEResourceRecordDocument tempDocument = (OLEEResourceRecordDocument) getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, tempId);
            int instancesSize = tempDocument.getOleERSInstances().size();
            int instanceSize = oleeResourceRecordDocument.getOleERSInstances().size();
            if (tempDocument.iseInstanceFlag() && instancesSize > instanceSize && !oleERSform.isRemoveInstanceFlag()) {
                try {
                    super.reload(oleERSform, result, request, response);
                } catch (Exception e) {
                    LOG.error("Exception while reloading the e-resource document"+e.getMessage());
                }
            }
            oleERSform.setRemoveInstanceFlag(false);
        }
        getOleEResourceSearchService().processEventAttachments(oleeResourceRecordDocument.getOleERSEventLogs());
        return super.approve(oleERSform, result, request, response);
    }

    /*@Override
    @RequestMapping(params = "methodToCall=cancel")
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        if (oleeResourceRecordDocument != null) {
            Map<String, String> criteriaStatusIdMap = new HashMap<String, String>();
            criteriaStatusIdMap.put(OLEConstants.OLEEResourceRecord.STATUS_NAME, OLEConstants.OLEEResourceRecord.CANCELED);
            List<OLEEResourceStatus> oleERSStatusList = (List<OLEEResourceStatus>) getBusinessObjectService().findMatching(OLEEResourceStatus.class, criteriaStatusIdMap);
            if (oleERSStatusList.size() > 0) {
                OLEEResourceStatus oleERSStatus = oleERSStatusList.get(0);
                if (oleERSStatus != null) {
                    oleeResourceRecordDocument.setStatusId(oleERSStatus.getOleEResourceStatusId());
                    oleeResourceRecordDocument.setStatusName(oleERSStatus.getOleEResourceStatusName());
                }
            }
        }
        try {
            save(oleERSform, result, request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return super.cancel(oleERSform, result, request, response);
    }
*/

    /**
     * Create E-Instance document instance contained on the form
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=createInstance")
    public ModelAndView createInstance(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        oleERSform.setSelectFlag(true);
        oleERSform.setLinkInstance(false);
        oleERSform.setCreateInstance(true);
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    /**
     * close the popup in instance tab
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=closePopup")
    public ModelAndView closePopup(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    /**
     * This method takes List of UUids as parameter and creates a LinkedHashMap with instance as key and id as value. and calls
     * Docstore's QueryServiceImpl class getWorkBibRecords method and return workBibDocument for passed instance Id.
     *
     * @param instanceIdsList
     * @return List<WorkBibDocument>
     */
//    private List<WorkBibDocument> getWorkBibDocuments(List<String> instanceIdsList, String docType) {
//        List<LinkedHashMap<String, String>> instanceIdMapList = new ArrayList<LinkedHashMap<String, String>>();
//        for (String instanceId : instanceIdsList) {
//            LinkedHashMap<String, String> instanceIdMap = new LinkedHashMap<String, String>();
//            instanceIdMap.put(docType, instanceId);
//            instanceIdMapList.add(instanceIdMap);
//        }
//
//        QueryService queryService = QueryServiceImpl.getInstance();
//        List<WorkBibDocument> workBibDocuments = new ArrayList<WorkBibDocument>();
//        try {
//            workBibDocuments = queryService.getWorkBibRecords(instanceIdMapList);
//        } catch (Exception ex) {
//            // TODO Auto-generated catch block
//            ex.printStackTrace();
//        }
//        return workBibDocuments;
//    }

    /**
     * Called by the delete line action for a model collection. Method
     * determines which collection the action was selected for and the line
     * index that should be removed, then invokes the view helper service to
     * process the action
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=removeInstance")
    public ModelAndView removeInstance(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleEResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        String selectedCollectionPath = oleEResourceRecordForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        //String selectedCollectionId = uifForm.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException(OLEConstants.OLEEResourceRecord.BLANK_SELECTED_INDEX);
        }
        int selectedLineIndex = -1;
        String selectedLine = oleEResourceRecordForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            OLEEResourceInstance oleERSInstance = oleEResourceRecordDocument.geteRSInstances().get(Integer.parseInt(selectedLine));
            try {
                Holdings holdings = new EHoldings();
                OleHoldings oleHoldings = new OleHoldings();
                holdings = getDocstoreClientLocator().getDocstoreClient().retrieveHoldings(oleERSInstance.getInstanceId());
                if (holdings instanceof EHoldings) {
                    oleHoldings = new HoldingOlemlRecordProcessor().fromXML(holdings.getContent());
                    oleHoldings.setEResourceId(null);
                    holdings.setContent(new HoldingOlemlRecordProcessor().toXML(oleHoldings));
                    getDocstoreClientLocator().getDocstoreClient().updateHoldings(holdings);
                }
            } catch (Exception e) {
                LOG.error("Illegal exception while updating instance record" + e.getMessage());
            }
            selectedLineIndex = Integer.parseInt(selectedLine);
            OLEEResourceEventLog oleEResourceEventLog = new OLEEResourceEventLog();
            oleEResourceEventLog.setCurrentTimeStamp();
            oleEResourceEventLog.setEventUser(GlobalVariables.getUserSession().getPrincipalName());
            oleEResourceEventLog.setEventNote(oleERSInstance.getInstanceTitle() + OLEConstants.OLEEResourceRecord.INSTANCE_ID_REMOVE_NOTE + oleERSInstance.getInstanceId() + OLEConstants.OLEEResourceRecord.REMOVE_NOTE);
            //oleEResourceEventLog.setEventType(OLEConstants.OLEEResourceRecord.SYSTEM);
            oleEResourceEventLog.setLogTypeId("3");
            oleEResourceRecordDocument.getOleERSEventLogs().add(oleEResourceEventLog);
        }
        if (selectedLineIndex == -1) {
            throw new RuntimeException(OLEConstants.OLEEResourceRecord.BLANK_SELECTED_INDEX);
        }
        oleEResourceRecordForm.setRemoveInstanceFlag(true);
        View view = oleEResourceRecordForm.getPostedView();
        view.getViewHelperService().processCollectionDeleteLine(view, oleEResourceRecordForm, selectedCollectionPath,
                selectedLineIndex);
        oleEResourceRecordDocument.getOleERSInstances().remove(selectedLineIndex);
        return getUIFModelAndView(oleEResourceRecordForm);
    }


    /**
     * This method returns the object of OleLicesneRequestService
     *
     * @return oleLicenseRequestService
     */
    public OleLicenseRequestWebService getOleLicenseRequestService() {
        if (oleLicenseRequestWebService == null) {
            oleLicenseRequestWebService = GlobalResourceLoader.getService(OLEConstants.OleLicenseRequest.HELPER_SERVICE);
        }
        return oleLicenseRequestWebService;
    }

    public KualiRuleService getKualiRuleService() {
        if (kualiRuleService == null) {
            kualiRuleService = GlobalResourceLoader.getService(OLEConstants.KUALI_RULE_SERVICE);
        }
        return kualiRuleService;
    }

    public BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KRADServiceLocator.getBusinessObjectService();
        }
        return businessObjectService;
    }

    public OLEEResourceSearchService getOleEResourceSearchService() {
        if (oleEResourceSearchService == null) {
            oleEResourceSearchService = GlobalResourceLoader.getService(OLEConstants.OLEEResourceRecord.ERESOURSE_SEARCH_SERVICE);
        }
        return oleEResourceSearchService;
    }


    /**
     * Edit Default Coverage date for E-Instance
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=editDefaultCoverage")
    public ModelAndView editDefaultCoverage(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        oleERSform.setCoverageFlag(true);
        oleERSform.setPerpetualAccessFlag(false);
        oleERSform.setDefaultDatesFlag(true);
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        String defaultCov = oleeResourceRecordDocument.getDummyDefaultCoverage();
        if(defaultCov != null && !defaultCov.isEmpty()) {
            oleeResourceRecordDocument.setCovEdited(true);
            getOleEResourceSearchService().getDefaultCovDatesToPopup(oleeResourceRecordDocument,defaultCov);
        }
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    /**
     * Edit Default Perpetual date for E-Instance
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=editDefaultPerpetualAccess")
    public ModelAndView editDefaultPerpetualAccess(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                                   HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        oleERSform.setCoverageFlag(false);
        oleERSform.setPerpetualAccessFlag(true);
        oleERSform.setDefaultDatesFlag(true);
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        String defaultPerAcc = oleeResourceRecordDocument.getDummyDefaultPerpetualAccess();
        if(defaultPerAcc != null && !defaultPerAcc.isEmpty()) {
            oleeResourceRecordDocument.setPerAccEdited(true);
            getOleEResourceSearchService().getDefaultPerAccDatesToPopup(oleeResourceRecordDocument,defaultPerAcc);
        }
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    /**
     * close the popup in instance tab
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=closeCoverageOrPerpetualAccessDate")
    public ModelAndView closeCoverageOrPerpetualAccessDate(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        oleERSform.setDefaultDatesFlag(false);
        oleERSform.setDefaultCovStartDateErrorMessage(null);
        oleERSform.setDefaultCovEndDateErrorMessage(null);
        oleERSform.setDefaultPerAccStartDateErrorMessage(null);
        oleERSform.setDefaultPerAccEndDateErrorMessage(null);
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    /**
     * refresh the instance tab
     *
     * @param form - document form base containing the document instance that will be routed
     * @return ModelAndView
     */
    @RequestMapping(params = "methodToCall=refreshDefaultDate")
    public ModelAndView refreshDefaultDate(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleERSform = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleERSform.getDocument();
        if(oleERSform.isCoverageFlag()) {
            getOleEResourceSearchService().getDefaultCovergeDate(oleeResourceRecordDocument);
            oleERSform.setCoverageFlag(false);
        }
        if(oleERSform.isPerpetualAccessFlag()) {
            getOleEResourceSearchService().getDefaultPerpetualAccessDate(oleeResourceRecordDocument);
            oleERSform.setPerpetualAccessFlag(false);
        }
        return getUIFModelAndView(oleERSform, OLEConstants.OLEEResourceRecord.E_RES_INSTANCE_TAB);
    }

    @RequestMapping(params = "methodToCall=addMaterialType")
    public ModelAndView addMaterialType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEMaterialTypeList> oleMaterialTypeLists=oleeResourceRecordDocument.getOleMaterialTypes();
        oleeResourceRecordDocument.getOleMaterialTypes().add(index, new OLEMaterialTypeList());
        oleeResourceRecordDocument.setOleMaterialTypes(oleMaterialTypeLists);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeMaterialType")
    public ModelAndView removeMaterialType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEMaterialTypeList> oleMaterialTypeLists=oleeResourceRecordDocument.getOleMaterialTypes();
        if (oleMaterialTypeLists.size() > 1) {
            oleMaterialTypeLists.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addFormatType")
    public ModelAndView addFormatType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEFormatTypeList> oleFormatTypeLists=oleeResourceRecordDocument.getOleFormatTypes();
        oleeResourceRecordDocument.getOleFormatTypes().add(index, new OLEFormatTypeList());
        oleeResourceRecordDocument.setOleFormatTypes(oleFormatTypeLists);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeFormatType")
    public ModelAndView removeFormatType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEFormatTypeList> oleFormatTypeLists=oleeResourceRecordDocument.getOleFormatTypes();
        if (oleFormatTypeLists.size() > 1) {
            oleFormatTypeLists.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addContentType")
    public ModelAndView addContentType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEContentTypes> oleContentTypes=oleeResourceRecordDocument.getOleContentTypes();
        oleeResourceRecordDocument.getOleContentTypes().add(index, new OLEContentTypes());
        oleeResourceRecordDocument.setOleContentTypes(oleContentTypes);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeContentType")
    public ModelAndView removeContentType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEContentTypes> oleContentTypes=oleeResourceRecordDocument.getOleContentTypes();
        if (oleContentTypes.size() > 1) {
            oleContentTypes.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=addSelectorType")
    public ModelAndView addSelectorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEEResourceSelector> oleeResourceSelectors=oleeResourceRecordDocument.getSelectors();
        oleeResourceRecordDocument.getSelectors().add(index, new OLEEResourceSelector());
        oleeResourceRecordDocument.setSelectors(oleeResourceSelectors);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeSelectorType")
    public ModelAndView removeSelectorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEEResourceSelector> oleeResourceSelectors=oleeResourceRecordDocument.getSelectors();
        if (oleeResourceSelectors.size() > 1) {
            oleeResourceSelectors.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addRequestorType")
    public ModelAndView addRequestorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEEResourceRequestor> oleeResourceRequestors=oleeResourceRecordDocument.getRequestors();
        oleeResourceRecordDocument.getRequestors().add(index, new OLEEResourceRequestor());
        oleeResourceRecordDocument.setRequestors(oleeResourceRequestors);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeRequestorType")
    public ModelAndView removeRequestorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEEResourceRequestor> oleeResourceRequestors=oleeResourceRecordDocument.getRequestors();
        if (oleeResourceRequestors.size() > 1) {
            oleeResourceRequestors.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addVariantTitle")
    public ModelAndView addVariantTitle(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEEResourceVariantTitle> oleEResourceVariantTitleList=oleeResourceRecordDocument.getOleEResourceVariantTitleList();
        oleeResourceRecordDocument.getOleEResourceVariantTitleList().add(index, new OLEEResourceVariantTitle());
        oleeResourceRecordDocument.setOleEResourceVariantTitleList(oleEResourceVariantTitleList);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeVariantTitle")
    public ModelAndView removeVariantTitle(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEEResourceVariantTitle> oleEResourceVariantTitleList=oleeResourceRecordDocument.getOleEResourceVariantTitleList();
        if (oleEResourceVariantTitleList.size() > 1) {
            oleEResourceVariantTitleList.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addRequestorSelectorType")
    public ModelAndView addRequestorSelectorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEEResourceReqSelComments> oleeResourceReqSelCommentses=oleeResourceRecordDocument.getReqSelComments();
        oleeResourceRecordDocument.getReqSelComments().add(index, new OLEEResourceReqSelComments());
        oleeResourceRecordDocument.setReqSelComments(oleeResourceReqSelCommentses);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeRequestorSelectorType")
    public ModelAndView removeRequestorSelectorType(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEEResourceReqSelComments> oleeResourceReqSelCommentses=oleeResourceRecordDocument.getReqSelComments();
        if (oleeResourceReqSelCommentses.size() > 1) {
            oleeResourceReqSelCommentses.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=addNoteTextSection")
    public ModelAndView addNoteTextSection(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                                 HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        index++;
        List<OLEEResourceNotes> oleeResourceNoteses=oleeResourceRecordDocument.getEresNotes();
        oleeResourceRecordDocument.getEresNotes().add(index, new OLEEResourceNotes());
        oleeResourceRecordDocument.setEresNotes(oleeResourceNoteses);
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=removeNoteTextSection")
    public ModelAndView removeNoteTextSection(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        List<OLEEResourceNotes> oleeResourceNoteses=oleeResourceRecordDocument.getEresNotes();
        if (oleeResourceNoteses.size() > 1) {
            oleeResourceNoteses.remove(index);
        }
        form.setDocument(oleeResourceRecordDocument);
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=saveGokbConfig")
    public ModelAndView saveGokbConfig(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        KRADServiceLocatorWeb.getDocumentService().updateDocument(oleeResourceRecordDocument);
        //getOleeResourceHelperService().applyGOKbChangesToOle(oleeResourceRecordDocument.getOleERSIdentifier());
        return super.navigate(form, result, request, response);
    }


    /**
     * This method will fetch the eresource and its instance to create PO(s).
     *
     * @param uifForm
     * @param result
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(params = "methodToCall=getEResourcesAndInstances")
    public ModelAndView getEResourcesAndInstances(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                                  HttpServletRequest request, HttpServletResponse response) {

        String oleERSIdentifier = request.getParameter(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER);
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        oleeResourceRecordForm.setPoSuccessMessage(null);
        oleeResourceRecordForm.setPoErrorMessage(null);
        OLEEResourceRecordDocument oleeResourceRecordDocument = null;
        if (StringUtils.isNotBlank(oleERSIdentifier)) {
            Map eResMap = new HashMap();
            eResMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleERSIdentifier);
            oleeResourceRecordDocument = getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, eResMap);
        }
        if (oleeResourceRecordDocument != null) {
            String purposeId = null;
            String purposeType = getOleEResourceSearchService().getParameter(OLEConstants.OLEEResourceRecord.PURPOSE_TYPE, OLEConstants.ERESOURCE_CMPNT);
            if (StringUtils.isNotBlank(purposeType)) {
                Map purposeMap = new HashMap();
                purposeMap.put(OLEConstants.OlePurchaseOrderPurpose.PURCHASE_ORDER_PURPOSE_CODE, purposeType);
                List<OlePurchaseOrderPurpose> purposeList = (List<OlePurchaseOrderPurpose>) getBusinessObjectService().findMatching(OlePurchaseOrderPurpose.class, purposeMap);
                if (purposeList != null && purposeList.size() > 0) {
                    OlePurchaseOrderPurpose purpose = purposeList.get(0);
                    purposeId = purpose.getPurchaseOrderPurposeId();
                }
            }
            List<OLECreatePO> eResources = getOleEResourceSearchService().getEresources(oleeResourceRecordDocument, purposeId);
            oleeResourceRecordForm.seteResourcePOs(eResources);

            List<OLECreatePO> instancePOs = getOleEResourceSearchService().getInstances(oleeResourceRecordDocument, purposeId);
            oleeResourceRecordForm.setInstancePOs(instancePOs);
        }
        return super.navigate(oleeResourceRecordForm, result, request, response);
    }




    /**
     * This method will fetch the eresource and its instance to create PO(s).
     *
     * @param uifForm
     * @param request
     * @param response
     * @return
     */


    @RequestMapping(params = "methodToCall=getPlatformForEResource")
    public ModelAndView getPlatformForEResource(@ModelAttribute("KualiForm") UifFormBase uifForm,BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response) {


//      String oleERSIdentifier = request.getParameter("oleERSIdentifier");
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument)form.getDocument();
        String oleERSIdentifier = oleeResourceRecordDocument.getOleERSIdentifier();
        List<OLEPlatformAdminUrl> olePlatformAdminUrlList1 = new ArrayList<OLEPlatformAdminUrl>();
        List<String> olePlatformIdList = new ArrayList<>();

        if (oleERSIdentifier!=null)
        {
            Map map = new HashMap();
            map.put("oleERSIdentifier", oleERSIdentifier);

            oleeResourceRecordDocument = getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, map);
            List<OLEEResourceInstance> oleERSInstances = oleeResourceRecordDocument.getOleERSInstances();

            if (oleERSInstances!=null)
            {
                for(OLEEResourceInstance oleeResourceInstance : oleERSInstances) {

                    String olePlatformId = oleeResourceInstance.getPlatformId();
                    Map platformMap = new HashMap();
                    if(olePlatformId!=null)
                    {
                        platformMap.put("olePlatformId", olePlatformId);
                        OLEPlatformRecordDocument olePlatformRecordDocument = (OLEPlatformRecordDocument)getBusinessObjectService().findByPrimaryKey(OLEPlatformRecordDocument.class, platformMap);
                        List<OLEPlatformAdminUrl> olePlatformAdminUrlList = olePlatformRecordDocument.getAdminUrls();
                        ((OLEEResourceRecordDocument) form.getDocument()).setOlePlatformAdminUrlList(olePlatformAdminUrlList1);

                        if (olePlatformAdminUrlList.size()>0)
                        {
                            //We are only with unique platform records in this table,so we ignore if more than 1 adminUrls associated with same platform
                            olePlatformAdminUrlList1.add(olePlatformAdminUrlList.get(0));
                        }
                        else{
                            OLEPlatformAdminUrl olePlatformAdminUrl = new OLEPlatformAdminUrl();
                            olePlatformAdminUrl.setOlePlatformRecordDocument(olePlatformRecordDocument);

                            if (!olePlatformIdList.contains(olePlatformRecordDocument.getOlePlatformId()))
                            {
                                olePlatformAdminUrlList1.add(olePlatformAdminUrl);
                                olePlatformIdList.add(olePlatformRecordDocument.getOlePlatformId());
                            }

                        }
                        HashSet hs = new HashSet();
                        hs.addAll(olePlatformAdminUrlList1);
                        olePlatformAdminUrlList1.clear();
                        olePlatformAdminUrlList1.addAll(hs);
                        ((OLEEResourceRecordDocument) form.getDocument()).setOlePlatformAdminUrlList(olePlatformAdminUrlList1);
                    }

                    else
                    {
                        LOG.error("No platforms associated with this e-holding");
                    }
                }
            }
            else{
                LOG.error("No holdings associated with this e-resource");
            }
        }
        return super.navigate(form, result, request, response);
    }

    private String getMask()
    {
        return "******";
    }


    public final String getParameter(String parameterName) {
        ParameterKey parameterKey = ParameterKey.create(OLEConstants.APPL_ID, OLEConstants.SELECT_NMSPC, OLEConstants.SELECT_CMPNT, parameterName);
        Parameter parameter = CoreServiceApiServiceLocator.getParameterRepositoryService().getParameter(parameterKey);
        return parameter != null ? parameter.getValue() : null;
    }


    @RequestMapping(params = "methodToCall=addPOForInstance")
    public ModelAndView addPOForInstance(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws CloneNotSupportedException {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        form.setPoSuccessMessage(null);
        form.setPoErrorMessage(null);
        int index = form.getIndex();
        List<OLECreatePO> inOleCreatePOs = form.getInstancePOs();
        OLECreatePO instancePO1 = (OLECreatePO) inOleCreatePOs.get(index).clone();
        index++;
        instancePO1.setPoId(null);
        inOleCreatePOs.add(index, instancePO1);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=addPOForEResource")
    public ModelAndView addPOForEResource(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws CloneNotSupportedException {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        form.setPoSuccessMessage(null);
        form.setPoErrorMessage(null);
        int index = form.getIndex();
        List<OLECreatePO> eResourceCreatePOs = form.geteResourcePOs();
        OLECreatePO eResourcePO1 = (OLECreatePO) eResourceCreatePOs.get(index).clone();
        index++;
        eResourcePO1.setPoId(null);
        eResourceCreatePOs.add(index, eResourcePO1);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=synchronizeWithGokb")
    public ModelAndView synchronizeWithGokb(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument)form.getDocument();
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=createPOs")
    public ModelAndView createPOs(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        boolean selectFlag = false;
        oleeResourceRecordForm.setPoSuccessMessage(null);
        oleeResourceRecordForm.setPoErrorMessage(null);
        StringBuffer poSuccessMessage = new StringBuffer();
        StringBuffer poErrorMessage = new StringBuffer();
        String selectedPOType = oleeResourceRecordForm.getSelectedPOType();
        List<OLECreatePO> posToCreateForEholdings = new ArrayList<>();
        List<OLECreatePO> posToCreateForHoldings = new ArrayList<>();
        List<OLECreatePO> posToCreateForEResources = new ArrayList<>();
        List<OLECreatePO> instancePOs = oleeResourceRecordForm.getInstancePOs();
        List<OLECreatePO> eResourcePOs = oleeResourceRecordForm.geteResourcePOs();
        for (OLECreatePO instancePO : instancePOs) {
            if (instancePO.isSelectFlag()) {
                selectFlag = true;
                String validationMsg = getOleEResourceSearchService().validateAccountngLinesVendorAndPrice(instancePO);
                if (validationMsg.length() == 0) {
                    Map createPOMap = new HashMap();
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, instancePO.getOleERSIdentifier());
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_INSTANCE_ID, instancePO.getInstanceId());
                    getBusinessObjectService().deleteMatching(OLECreatePO.class, createPOMap);
                    if (StringUtils.isNotBlank(instancePO.getInstanceFlag()) && instancePO.getInstanceFlag().equalsIgnoreCase("false")) {
                        posToCreateForEholdings.add(instancePO);
                    } else if (StringUtils.isNotBlank(instancePO.getInstanceFlag()) && instancePO.getInstanceFlag().equalsIgnoreCase("true")) {
                        posToCreateForHoldings.add(instancePO);
                    }
                } else {
                    poErrorMessage.append(validationMsg.toString());
                }
            }
        }
        for (OLECreatePO eResourcePo : eResourcePOs) {
            if (eResourcePo.isSelectFlag()) {
                selectFlag = true;
                String validationMsg = getOleEResourceSearchService().validateAccountngLinesVendorAndPrice(eResourcePo);
                if (validationMsg.length() == 0) {
                    Map createPOMap = new HashMap();
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, eResourcePo.getOleERSIdentifier());
                    List<OLECreatePO> oleCreatePOs = (List<OLECreatePO>) getBusinessObjectService().findMatching(OLECreatePO.class, createPOMap);
                    if (oleCreatePOs != null) {
                        for (OLECreatePO oleCreatePO : oleCreatePOs) {
                            if (StringUtils.isBlank(oleCreatePO.getInstanceId())) {
                                getBusinessObjectService().delete(oleCreatePO);
                            }
                        }
                    }
                    posToCreateForEResources.add(eResourcePo);
                } else {
                    poErrorMessage.append(validationMsg.toString());
                }
            }
        }
        List<OLEEResourceOrderRecord> oleEResourceOrderRecordList = new ArrayList<>();
        if (posToCreateForEholdings.size() > 0) {
            oleEResourceOrderRecordList.addAll(getOleEResourceSearchService().fetchOleOrderRecordList(posToCreateForEholdings, OLEConstants.ORDER_RECORD_IMPORT_MARC_ONLY_ELECTRONIC, getOleEResourceSearchService().getParameter(OLEConstants.OLEEResourceRecord.LOCATION, OLEConstants.ERESOURCE_CMPNT)));
        }
        if (posToCreateForHoldings.size() > 0) {
            oleEResourceOrderRecordList.addAll(getOleEResourceSearchService().fetchOleOrderRecordList(posToCreateForHoldings, OLEConstants.ORDER_RECORD_IMPORT_MARC_ONLY_PRINT, getOleEResourceSearchService().getParameter(OLEConstants.OLEEResourceRecord.LOCATION, OLEConstants.ERESOURCE_CMPNT)));
        }
        if (posToCreateForEResources.size() > 0) {
            oleEResourceOrderRecordList.addAll(getOleEResourceSearchService().fetchOleOrderRecordList(posToCreateForEResources, "E-Resource", "E-Resource"));
        }
        if (selectedPOType.equals(OLEConstants.OLEEResourceRecord.ONE_PO_PER_TITLE)) {
            for (OLEEResourceOrderRecord oleEResourceOrderRecord : oleEResourceOrderRecordList) {
                if (oleEResourceOrderRecord.getOleEResourceTxnRecord() != null) {
                    OleRequisitionDocument requisitionDocument = null;
                    try {
                        requisitionDocument = getOleReqPOCreateDocumentService().createRequisitionDocument();
                        requisitionDocument.setRequisitionSourceCode(oleEResourceOrderRecord.getOleEResourceTxnRecord().getRequisitionSource());
                        Map purchaseOrderTypeMap = new HashMap();
                        purchaseOrderTypeMap.put(OLEConstants.PURCHASE_ORDER_TYPE_ID, oleEResourceOrderRecord.getOleEResourceTxnRecord().getOrderType());
                        List<PurchaseOrderType> purchaseOrderTypeDocumentList = (List) getBusinessObjectService().findMatching(PurchaseOrderType.class, purchaseOrderTypeMap);
                        if (purchaseOrderTypeDocumentList != null && purchaseOrderTypeDocumentList.size() > 0) {
                            requisitionDocument.setPurchaseOrderTypeId(purchaseOrderTypeDocumentList.get(0).getPurchaseOrderTypeId());
                        }
                        getOleEResourceSearchService().setDocumentValues(requisitionDocument, oleEResourceOrderRecord);
                        requisitionDocument.setItems(getOleEResourceSearchService().generateItemList(oleEResourceOrderRecord, requisitionDocument));

                        RequisitionService requisitionService = SpringContext.getBean(RequisitionService.class);
                        boolean apoRuleFlag = requisitionService.isAutomaticPurchaseOrderAllowed(requisitionDocument);
                        if (!apoRuleFlag) {
                            oleEResourceOrderRecord.getMessageMap().put(OLEConstants.IS_APO_RULE, true);
                        }
                        requisitionDocument.setApplicationDocumentStatus(PurapConstants.RequisitionStatuses.APPDOC_IN_PROCESS);
                        getRequisitionCreateDocumentService().saveRequisitionDocuments(requisitionDocument);
                        if (oleEResourceOrderRecord.getOleBibRecord() != null) {
                            poSuccessMessage.append("Requisition Document created for title '" + oleEResourceOrderRecord.getOleBibRecord().getBib().getTitle() + "' - " + requisitionDocument.getPurapDocumentIdentifier());
                        } else if (StringUtils.isNotBlank(oleEResourceOrderRecord.getOleERSIdentifier())) {
                            OLEEResourceRecordDocument oleeResourceRecordDocument = getBusinessObjectService().findBySinglePrimaryKey(OLEEResourceRecordDocument.class, oleEResourceOrderRecord.getOleERSIdentifier());
                            if (oleeResourceRecordDocument != null) {
                                poSuccessMessage.append("Requisition Document created for title '" + oleeResourceRecordDocument.getTitle() + "' - " + requisitionDocument.getPurapDocumentIdentifier());
                            }
                        }
                        poSuccessMessage.append(OLEConstants.BREAK);

                    } catch (Exception e) {
                        e.printStackTrace();
                        oleEResourceOrderRecord.addMessageToMap(OLEConstants.IS_VALID_RECORD, false);
                    }
                }
            }
        } else if (selectedPOType.equals(OLEConstants.OLEEResourceRecord.ONE_PO_WITH_ALL_TITLES)) {
            OleRequisitionDocument requisitionDocument = null;
            try {
                requisitionDocument = getOleReqPOCreateDocumentService().createRequisitionDocument();
                requisitionDocument.setRequisitionSourceCode(oleEResourceOrderRecordList.get(0).getOleEResourceTxnRecord().getRequisitionSource());
                Map purchaseOrderTypeMap = new HashMap();
                purchaseOrderTypeMap.put(OLEConstants.PURCHASE_ORDER_TYPE_ID, oleEResourceOrderRecordList.get(0).getOleEResourceTxnRecord().getOrderType());
                List<PurchaseOrderType> purchaseOrderTypeDocumentList = (List) getBusinessObjectService().findMatching(PurchaseOrderType.class, purchaseOrderTypeMap);
                if (purchaseOrderTypeDocumentList != null && purchaseOrderTypeDocumentList.size() > 0) {
                    requisitionDocument.setPurchaseOrderTypeId(purchaseOrderTypeDocumentList.get(0).getPurchaseOrderTypeId());
                }
                getOleEResourceSearchService().setDocumentValues(requisitionDocument, oleEResourceOrderRecordList.get(0));
                requisitionDocument.setItems(getOleEResourceSearchService().generateMultipleItemsForOneRequisition(oleEResourceOrderRecordList, requisitionDocument));

                requisitionDocument.setApplicationDocumentStatus(PurapConstants.RequisitionStatuses.APPDOC_IN_PROCESS);
                getRequisitionCreateDocumentService().saveRequisitionDocuments(requisitionDocument);
                String tiles = new String();
                for (OLEEResourceOrderRecord oleEResourceOrderRecord : oleEResourceOrderRecordList) {
                    if (oleEResourceOrderRecord.getOleBibRecord() != null) {
                        tiles = tiles.concat("'");
                        tiles = tiles.concat(oleEResourceOrderRecord.getOleBibRecord().getBib().getTitle());
                        tiles = tiles.concat("'");
                        tiles = tiles.concat(" ");
                    } else if (StringUtils.isNotBlank(oleEResourceOrderRecord.getOleERSIdentifier())) {
                        OLEEResourceRecordDocument oleeResourceRecordDocument = getBusinessObjectService().findBySinglePrimaryKey(OLEEResourceRecordDocument.class, oleEResourceOrderRecord.getOleERSIdentifier());
                        if (oleeResourceRecordDocument != null) {
                            tiles = tiles.concat("'");
                            tiles = tiles.concat(oleeResourceRecordDocument.getTitle());
                            tiles = tiles.concat("'");
                            tiles = tiles.concat(" ");
                        }
                    }
                }
                poSuccessMessage.append("Requisition Document created for titles " + tiles + " - " + requisitionDocument.getPurapDocumentIdentifier());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (selectFlag) {
            if (poSuccessMessage.length() > 1) {
                oleeResourceRecordForm.setPoSuccessMessage(poSuccessMessage.toString());
            }
            if (poErrorMessage.length() > 1) {
                oleeResourceRecordForm.setPoErrorMessage(poErrorMessage.toString());
            }
        } else {
            oleeResourceRecordForm.setPoErrorMessage("Atleast one E-Resource/Instance should be selected.");
        }
        return super.navigate(oleeResourceRecordForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=savePoChanges")
    public ModelAndView savePoChanges(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        boolean selectFlag = false;
        oleeResourceRecordForm.setPoSuccessMessage(null);
        oleeResourceRecordForm.setPoErrorMessage(null);
        for (OLECreatePO eResourcePO : oleeResourceRecordForm.geteResourcePOs()) {
            if (eResourcePO.isSelectFlag()) {
                selectFlag = true;
                if (StringUtils.isNotBlank(eResourcePO.getCreatePOId())) {
                    getBusinessObjectService().save(eResourcePO);
                } else {
                    Map createPOMap = new HashMap();
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, eResourcePO.getOleERSIdentifier());
                    List<OLECreatePO> oleCreatePOs = (List<OLECreatePO>) getBusinessObjectService().findMatching(OLECreatePO.class, createPOMap);
                    if (oleCreatePOs != null && oleCreatePOs.size() > 0) {
                        for (OLECreatePO oleCreatePO : oleCreatePOs) {
                            if (oleCreatePO.getInstanceId() == null) {
                                eResourcePO.setCreatePOId(oleCreatePO.getCreatePOId());
                                eResourcePO.setObjectId(oleCreatePO.getObjectId());
                                eResourcePO.setVersionNumber(oleCreatePO.getVersionNumber());
                            }
                        }
                    }
                    getBusinessObjectService().save(eResourcePO);
                }
            }
        }
        for (OLECreatePO instancePO : oleeResourceRecordForm.getInstancePOs()) {
            if (instancePO.isSelectFlag()) {
                selectFlag = true;
                if (StringUtils.isNotBlank(instancePO.getCreatePOId())) {
                    getBusinessObjectService().save(instancePO);
                } else {
                    Map createPOMap = new HashMap();
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, instancePO.getOleERSIdentifier());
                    createPOMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_INSTANCE_ID, instancePO.getInstanceId());
                    List<OLECreatePO> oleCreatePOs = (List<OLECreatePO>) getBusinessObjectService().findMatching(OLECreatePO.class, createPOMap);
                    if (oleCreatePOs != null && oleCreatePOs.size() > 0) {
                        OLECreatePO oleCreatePO = oleCreatePOs.get(0);
                        instancePO.setCreatePOId(oleCreatePO.getCreatePOId());
                        instancePO.setObjectId(oleCreatePO.getObjectId());
                        instancePO.setVersionNumber(oleCreatePO.getVersionNumber());
                    }
                    getBusinessObjectService().save(instancePO);
                }
            }
        }
        if (selectFlag){
            oleeResourceRecordForm.setPoSuccessMessage("Changes have been saved successfully");
        }else {
            oleeResourceRecordForm.setPoErrorMessage("Atleast one E-Resource/Instance should be selected");
        }
        return super.navigate(oleeResourceRecordForm,result,request,response);
    }

    @RequestMapping(params = "methodToCall=next")
    public ModelAndView next(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        return getUIFModelAndView(form, "OLEEResourceRecordView");
    }


    @RequestMapping(params = "methodToCall=saveRelationship")
    public ModelAndView saveRelationship(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        ListIterator<OLELinkedEresource> oleLinkedEresourceListIterator = oleeResourceRecordDocument.getOleLinkedEresources().listIterator();
        while(oleLinkedEresourceListIterator.hasNext()){
            OLELinkedEresource linkedEresource = oleLinkedEresourceListIterator.next();
            Map<String, String> tempId = new HashMap<String, String>();
            tempId.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, linkedEresource.getLinkedERSIdentifier());
            OLEEResourceRecordDocument parentDocument = (OLEEResourceRecordDocument) getBusinessObjectService().findByPrimaryKey(OLEEResourceRecordDocument.class, tempId);
            Person principalPerson = SpringContext.getBean(PersonService.class).getPerson(GlobalVariables.getUserSession().getPerson().getPrincipalId());
            try {
                parentDocument.getDocumentHeader().setWorkflowDocument(KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(parentDocument.getDocumentNumber(), principalPerson));
            } catch (WorkflowException e) {
                e.printStackTrace();
            }
            String removeChild= form.getActionParamaterValue("removeChild");
            if(removeChild.equalsIgnoreCase("no")){
                linkedEresource.setRemoveRelationShip(false);
                return getUIFModelAndView(form);
            }
            if(linkedEresource.getRelationShipType().equalsIgnoreCase("child") && linkedEresource.isRemoveRelationShip()) {
                if(oleeResourceRecordDocument.getRemoveOrRelinkToParent() !=null && oleeResourceRecordDocument.getRemoveOrRelinkToParent().equalsIgnoreCase("relinkToParent")){
                    for(OLEEResourceInstance instance : linkedEresource.getOleeResourceRecordDocument().getOleERSInstances()){
                        Map criteriaMap = new HashMap();
                        criteriaMap.put("oleEResourceInstanceId", instance.getOleEResourceInstanceId());
                        OLEEResourceInstance oleeResourceInstance = getBusinessObjectService().findByPrimaryKey(OLEEResourceInstance.class, criteriaMap);
                        oleeResourceInstance.setOleERSIdentifier(oleeResourceRecordDocument.getOleERSIdentifier());
                        oleLinkedEresourceListIterator.remove();
                    }
                } else if(oleeResourceRecordDocument.getRemoveOrRelinkToParent() !=null && oleeResourceRecordDocument.getRemoveOrRelinkToParent().equalsIgnoreCase("removelink")) {
                    Map criteriaMap = new HashMap();
                    criteriaMap.put("linkedERSIdentifier", linkedEresource.getLinkedERSIdentifier());
                    criteriaMap.put("oleERSIdentifier", linkedEresource.getOleERSIdentifier());
                    getBusinessObjectService().deleteMatching(OLELinkedEresource.class, criteriaMap);
                    criteriaMap.clear();
                    criteriaMap.put("oleERSIdentifier", linkedEresource.getLinkedERSIdentifier());
                    criteriaMap.put("linkedERSIdentifier", linkedEresource.getOleERSIdentifier());
                    getBusinessObjectService().deleteMatching(OLELinkedEresource.class, criteriaMap);
                    oleLinkedEresourceListIterator.remove();
                }
            }else if(linkedEresource.getRelationShipType().equalsIgnoreCase("parent")) {
                String removeParent = form.getActionParamaterValue("removeParent");
                if(removeParent.equalsIgnoreCase("yes")){
                    Map criteriaMap = new HashMap();
                    criteriaMap.put("linkedERSIdentifier", linkedEresource.getLinkedERSIdentifier());
                    criteriaMap.put("oleERSIdentifier", linkedEresource.getOleERSIdentifier());
                    getBusinessObjectService().deleteMatching(OLELinkedEresource.class, criteriaMap);
                    criteriaMap.clear();
                    criteriaMap.put("oleERSIdentifier", linkedEresource.getLinkedERSIdentifier());
                    criteriaMap.put("linkedERSIdentifier", linkedEresource.getOleERSIdentifier());
                    getBusinessObjectService().deleteMatching(OLELinkedEresource.class, criteriaMap);
                    oleLinkedEresourceListIterator.remove();
                }
            }
            linkedEresource.setRemoveRelationShip(false);
            oleeResourceRecordDocument.setOleLinkedEresources(null);
            oleEResourceSearchService.getPOInvoiceForERS(oleeResourceRecordDocument);
        }
        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=saveResultToEventlog")
    public ModelAndView saveResultToEventlog(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        OLEEResourceEventLog oleERSEventLog = new OLEEResourceEventLog();
        //oleERSEventLog.setEventType(OLEConstants.OleLicenseRequest.USER);
        oleERSEventLog.setLogTypeId("3");
        oleERSEventLog.setCurrentTimeStamp();
        oleERSEventLog.setOleERSIdentifier(oleeResourceRecordDocument.getDocumentNumber());
        oleERSEventLog.setEventNote("Price Increase Analysis: Last year's cost was "+oleeResourceRecordDocument.getFiscalYearCost() + ". This year's price quote is " + oleeResourceRecordDocument.getYearPriceQuote() + ". This is a price increase of " + oleeResourceRecordDocument.getCostIncrease() + " or " + oleeResourceRecordDocument.getPercentageIncrease());
        oleeResourceRecordDocument.getOleERSEventLogs().add(oleERSEventLog);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=downloadCsv")
    public ModelAndView downloadCsv(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        response.setHeader("Content-disposition", "attachment;filename=PriceIncreaseAnalysis.csv");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("text");
        StringBuffer sb = new StringBuffer();
        sb.append("YearCost");
        sb.append(",");
        sb.append("quote");
        sb.append(",");
        sb.append("CostIncrease");
        sb.append(",");
        sb.append("PercentIncrease");
        sb.append("\n");

        sb.append(oleeResourceRecordDocument.getFiscalYearCost());
        sb.append(",");
        sb.append(oleeResourceRecordDocument.getYearPriceQuote());
        sb.append(",");
        sb.append(oleeResourceRecordDocument.getCostIncrease());
        sb.append(",");
        sb.append(oleeResourceRecordDocument.getPercentageIncrease());
        sb.append("\n");

        byte [] txt = sb.toString().getBytes();
        OutputStream out;
        try {
            out = response.getOutputStream();
            out.write(txt);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=generateEmailText")
    public ModelAndView generateEmailText(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        form.setEmailFlag(true);
        oleeResourceRecordDocument.setEmailText(OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_APPROVAL_TEXT + oleeResourceRecordDocument.getTitle() + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_LAST_YEAR_COST + oleeResourceRecordDocument.getFiscalYearCost() + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_THIS_YEAR_QUOTE + oleeResourceRecordDocument.getYearPriceQuote() + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_PRICE_INCREASE + oleeResourceRecordDocument.getCostIncrease() + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_OR + oleeResourceRecordDocument.getPercentageIncrease() + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_PERCENT + OLEConstants.OLEEResourceRecord.ERESOURCE_EMAIL_CONFIRM);
        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=calculateIncrease")
    public ModelAndView calculateIncrease(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        List<OLEEResourceInvoices> oleERSInvoices = oleeResourceRecordDocument.getOleERSInvoices();
        int fiscalYearCost = 0;
        for(OLEEResourceInvoices oleeResourceInvoices : oleERSInvoices){
            fiscalYearCost = fiscalYearCost + Integer.valueOf(oleeResourceInvoices.getInvoicedAmount());
        }
        double cost = oleeResourceRecordDocument.getYearPriceQuote() - oleeResourceRecordDocument.getFiscalYearCost();
        oleeResourceRecordDocument.setCostIncrease(Math.round(cost));
        double percentage = (cost / oleeResourceRecordDocument.getFiscalYearCost()) * 100;
        oleeResourceRecordDocument.setPercentageIncrease(Math.round(percentage));
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=closeDialog")
    public ModelAndView closeDialog(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        form.setEmailFlag(false);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) form;
        String formKey = (String) request.getSession().getValue("formKeyValue");
        List<String> oleEResIdentifiers = new ArrayList<>();
        if (StringUtils.isNotBlank(formKey)) {
            OLEEResourceRecordForm sessionForm = (OLEEResourceRecordForm) GlobalVariables.getUifFormManager().getSessionForm(formKey);
            OLEEResourceRecordDocument oleEResourceRecordDocument = (OLEEResourceRecordDocument) sessionForm.getDocument();
            oleEResIdentifiers.add(oleEResourceRecordDocument.getOleERSIdentifier());
            List<OLELinkedEresource> oleLinkedEresources = oleEResourceRecordDocument.getOleLinkedEresources();
            for (OLELinkedEresource oleLinkedEresource : oleLinkedEresources) {
                oleEResIdentifiers.add(oleLinkedEresource.getLinkedERSIdentifier());
            }
        }
        OLEEResourceSearchServiceImpl oleEResourceSearchService = GlobalResourceLoader.getService(OLEConstants.OLEEResourceRecord.ERESOURSE_SEARCH_SERVICE);
        List<OLESearchCondition> oleSearchConditionsList = oleeResourceRecordForm.getOleSearchParams().getSearchFieldsList();
        List<OLEEResourceRecordDocument> eresourceDocumentList = new ArrayList<OLEEResourceRecordDocument>();
        List<OLEEResourceRecordDocument> eresourceList = new ArrayList<OLEEResourceRecordDocument>();
        try {
            eresourceList = oleEResourceSearchService.performSearch(oleSearchConditionsList);
        } catch (Exception e) {
            LOG.error("Exception while hitting the performSearch()" + e.getMessage());
        }
        if (oleEResIdentifiers != null && oleEResIdentifiers.size()>0) {
            List<OLEEResourceRecordDocument> oleeResourceRecordDocumentList = new ArrayList<OLEEResourceRecordDocument>();
            for (OLEEResourceRecordDocument oleeResourceRecordDocument : eresourceList) {
                if (!oleEResIdentifiers.contains(oleeResourceRecordDocument.getOleERSIdentifier())) {
                    oleeResourceRecordDocumentList.add(oleeResourceRecordDocument);
                    oleeResourceRecordDocument.setRelationshipType("child");
                }
            }
            eresourceList = oleeResourceRecordDocumentList;
            for (OLEEResourceRecordDocument oleEResourceRecordDocument : eresourceList) {
                Map criteriaMap = new HashMap();
                criteriaMap.put(OLEConstants.OLEEResourceRecord.ERESOURCE_IDENTIFIER, oleEResourceRecordDocument.getOleERSIdentifier());
                List<OLELinkedEresource> oleLinkedEresourceList = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, criteriaMap);
                oleEResourceRecordDocument.setOleLinkedEresources(oleLinkedEresourceList);
            }
        }
        if (oleeResourceRecordForm.getStatus() != null) {
            eresourceList = oleEResourceSearchService.statusNotNull(eresourceList, oleeResourceRecordForm.getStatus());
        }
        if (oleeResourceRecordForm.iseResStatusDate()) {
            eresourceDocumentList = oleEResourceSearchService.filterEResRecBystatusDate(oleeResourceRecordForm.getBeginDate(),oleeResourceRecordForm.getEndDate(),eresourceList);
            oleeResourceRecordForm.setEresourceDocumentList(eresourceDocumentList);
        } else {
            oleeResourceRecordForm.setEresourceDocumentList(eresourceList);
        }
        List<OLEEResourceRecordDocument> eresDocumentList = oleeResourceRecordForm.getEresourceDocumentList();
        oleEResourceSearchService.removeDuplicateEresDocumentsFromList(eresDocumentList);
        if (!GlobalVariables.getMessageMap().hasMessages()){
            if (oleeResourceRecordForm.getEresourceDocumentList().size()==0)
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, OLEConstants.NO_RECORD_FOUND);
        }
        else {
            oleeResourceRecordForm.setEresourceDocumentList(null);
        }
        return getUIFModelAndView(oleeResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=linkEResource")
    public ModelAndView linkEResource(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)throws Exception{
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) form;
        oleeResourceRecordForm.setMessage(null);
        List<OLEEResourceRecordDocument> oleeResourceRecordDocumentList = oleeResourceRecordForm.getEresourceDocumentList();
        String formKey = (String) request.getSession().getValue("formKeyValue");
        OLEEResourceRecordForm sessionForm;
        if (StringUtils.isNotBlank(formKey)) {
            sessionForm = (OLEEResourceRecordForm) GlobalVariables.getUifFormManager().getSessionForm(formKey);
            OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) sessionForm.getDocument();
            List<OLELinkedEresource> oleLinkedEresources = oleeResourceRecordDocument.getOleLinkedEresources();
            int parentCount = 0;
            int selectCount = 0;
            for (OLEEResourceRecordDocument eResourceRecordDocument : oleeResourceRecordDocumentList) {
                if (eResourceRecordDocument.isSelectEResFlag()) {
                    selectCount++;
                    if (eResourceRecordDocument.getRelationshipType().equals("parent")) {
                        parentCount++;
                        if (parentCount > 1) {
                            break;
                        }
                    }
                }
            }
            if (selectCount == 0){
                oleeResourceRecordForm.setMessage(SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(PurapKeyConstants.ERROR_SELECT_ERESOURCE));
                return getUIFModelAndView(oleeResourceRecordForm);
            }
            else if (parentCount > 1) {
                oleeResourceRecordForm.setMessage(SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(PurapKeyConstants.ERROR_ERESOURCE_ONLY_ONE_PARENT));
                return getUIFModelAndView(oleeResourceRecordForm);
            }
            int linkedParentCount = 0;
            for (OLELinkedEresource linkedEresource : oleLinkedEresources) {
                if (linkedEresource.getRelationShipType().equals("parent")) {
                    linkedParentCount++;
                }
            }
            if (linkedParentCount > 0 && parentCount > 0) {
                oleeResourceRecordForm.setMessage(SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(PurapKeyConstants.ERROR_ERESOURCE_ALREADY_HAS_PARENT));
                return getUIFModelAndView(oleeResourceRecordForm);
            }
            StringBuffer message = new StringBuffer();
            List<String> ids = new ArrayList<String>();
            boolean messageText = false;
            for (OLEEResourceRecordDocument selectedeResourceRecordDocument : oleeResourceRecordDocumentList) {
                if (selectedeResourceRecordDocument.isSelectEResFlag()) {
                    for(OLELinkedEresource linkedEresource : selectedeResourceRecordDocument.getOleLinkedEresources()){
                        if (linkedEresource.getRelationShipType().equalsIgnoreCase("parent")) {
                            message.append(selectedeResourceRecordDocument.getTitle());
                            message.append(", ");
                            ids.add(selectedeResourceRecordDocument.getOleERSIdentifier());
                        } else {
                            Map map = new HashMap();
                            map.put("linkedERSIdentifier", oleeResourceRecordDocument.getOleERSIdentifier());
                            map.put("relationShipType", "child");
                            List<OLELinkedEresource> eResource = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, map);
                            OLELinkedEresource oleLinkedEresource = eResource.get(0);
                            String chain = oleLinkedEresource.getChainString();
                            if(chain.contains(selectedeResourceRecordDocument.getOleERSIdentifier())){
                                message.append(selectedeResourceRecordDocument.getTitle());
                                message.append(", ");
                                messageText = true;
                                ids.add(selectedeResourceRecordDocument.getOleERSIdentifier());
                            }
                        }
                    }
                }
            }
            Iterator<OLEEResourceRecordDocument> iterator = oleeResourceRecordDocumentList.iterator();
            for(String id : ids){
                while(iterator.hasNext()){
                    if(id.equalsIgnoreCase(iterator.next().getOleERSIdentifier())){
                        iterator.remove();
                        break;
                    }
                }
            }
            for (OLEEResourceRecordDocument selectedeResourceRecordDocument : oleeResourceRecordDocumentList) {
                if (selectedeResourceRecordDocument.isSelectEResFlag()) {

                    Map map = new HashMap();
                    map.put("linkedERSIdentifier", oleeResourceRecordDocument.getOleERSIdentifier());
                    map.put("relationShipType", "child");
                    List<OLELinkedEresource> eResource1 = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, map);
                    if(eResource1.size()>0){
                        OLELinkedEresource oleLinkedEresource1 = eResource1.get(0);
                        String chain = oleLinkedEresource1.getChainString();
                        if(!chain.contains(selectedeResourceRecordDocument.getOleERSIdentifier())){
                            OLELinkedEresource oleLinkedEresource = new OLELinkedEresource();
                            oleLinkedEresource.setLinkedERSIdentifier(selectedeResourceRecordDocument.getOleERSIdentifier());
                            oleLinkedEresource.setRelationShipType(selectedeResourceRecordDocument.getRelationshipType());
                            if(selectedeResourceRecordDocument.getRelationshipType().equalsIgnoreCase("child")){
                                Map linkedEresourceMap = new HashMap();
                                linkedEresourceMap.put("linkedERSIdentifier", oleeResourceRecordDocument.getOleERSIdentifier());
                                linkedEresourceMap.put("relationShipType", "child");
                                List<OLELinkedEresource> eResource = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, linkedEresourceMap);
                                if(eResource.size()>0 && StringUtils.isNotEmpty(eResource.get(0).getChainString())){
                                    oleLinkedEresource.setChainString(eResource.get(0).getChainString() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                                }else{
                                    oleLinkedEresource.setChainString(oleeResourceRecordDocument.getOleERSIdentifier() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                                }
                            }
                            oleLinkedEresources.add(oleLinkedEresource);
                            OLELinkedEresource linkedEresource = new OLELinkedEresource();
                            linkedEresource.setLinkedERSIdentifier(oleeResourceRecordDocument.getOleERSIdentifier());
                            linkedEresource.setOleERSIdentifier(selectedeResourceRecordDocument.getOleERSIdentifier());
                            if(selectedeResourceRecordDocument.getRelationshipType().equalsIgnoreCase("parent")){
                                linkedEresource.setRelationShipType("child");
                                Map eResourceMap = new HashMap();
                                eResourceMap.put("oleERSIdentifier", selectedeResourceRecordDocument.getOleERSIdentifier());
                                eResourceMap.put("relationShipType", "child");
                                List<OLELinkedEresource> eResource = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, eResourceMap);
                                if(eResource.size()>0 && StringUtils.isNotEmpty(eResource.get(0).getChainString())){
                                    oleLinkedEresource.setChainString(eResource.get(0).getChainString() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                                }else{
                                    oleLinkedEresource.setChainString(oleeResourceRecordDocument.getOleERSIdentifier() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                                }
                            }else{
                                linkedEresource.setRelationShipType("parent");
                            }
                            getBusinessObjectService().save(linkedEresource);
                        }
                    }else{
                        OLELinkedEresource oleLinkedEresource = new OLELinkedEresource();
                        oleLinkedEresource.setLinkedERSIdentifier(selectedeResourceRecordDocument.getOleERSIdentifier());
                        oleLinkedEresource.setRelationShipType(selectedeResourceRecordDocument.getRelationshipType());
                        if(selectedeResourceRecordDocument.getRelationshipType().equalsIgnoreCase("child")){
                            Map linkedEresourceMap = new HashMap();
                            linkedEresourceMap.put("oleERSIdentifier", oleeResourceRecordDocument.getOleERSIdentifier());
                            linkedEresourceMap.put("relationShipType", "child");
                            List<OLELinkedEresource> eResource = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, linkedEresourceMap);
                            if(eResource.size()>0 && StringUtils.isNotEmpty(eResource.get(0).getChainString())){
                                oleLinkedEresource.setChainString(eResource.get(0).getChainString() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                            }else{
                                oleLinkedEresource.setChainString(oleeResourceRecordDocument.getOleERSIdentifier() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                            }
                        }
                        oleLinkedEresources.add(oleLinkedEresource);
                        OLELinkedEresource linkedEresource = new OLELinkedEresource();
                        linkedEresource.setLinkedERSIdentifier(oleeResourceRecordDocument.getOleERSIdentifier());
                        linkedEresource.setOleERSIdentifier(selectedeResourceRecordDocument.getOleERSIdentifier());
                        if(selectedeResourceRecordDocument.getRelationshipType().equalsIgnoreCase("parent")){
                            linkedEresource.setRelationShipType("child");
                            Map eResourceMap = new HashMap();
                            eResourceMap.put("oleERSIdentifier", selectedeResourceRecordDocument.getOleERSIdentifier());
                            eResourceMap.put("relationShipType", "child");
                            List<OLELinkedEresource> eResource = (List<OLELinkedEresource>) getBusinessObjectService().findMatching(OLELinkedEresource.class, eResourceMap);
                            if(eResource.size()>0 && StringUtils.isNotEmpty(eResource.get(0).getChainString())){
                                oleLinkedEresource.setChainString(eResource.get(0).getChainString() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                            }else{
                                oleLinkedEresource.setChainString(oleeResourceRecordDocument.getOleERSIdentifier() + ":" +selectedeResourceRecordDocument.getOleERSIdentifier());
                            }
                        }else{
                            linkedEresource.setRelationShipType("parent");
                        }
                        getBusinessObjectService().save(linkedEresource);
                    }

                }
            }
            if (message.length() > 0 && !messageText) {
                String format = String.format("The following documents (%s) can not be linked, since it already has parent. ", message.substring(0, message.lastIndexOf(",")));
                oleeResourceRecordForm.setMessage(format);
            }else if(message.length() > 0 && messageText) {
                String format = String.format("The following documents (%s) can not be linked,  since it is already a parent in the hierarchy. ", message.substring(0, message.lastIndexOf(",")));
                oleeResourceRecordForm.setMessage(format);
            }
        }
        return getUIFModelAndView(oleeResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument document = (OLEEResourceRecordDocument)form.getDocument();
        List<OLEEResourceInstance>  oleeResourceInstances = document.getOleERSInstances();
        List<OLEEResourceLicense> oleERSLicenses = document.getOleERSLicenseRequests();
        List<OLELinkedEresource> oleLikedEResources = document.getOleLinkedEresources();
        List<OLEEResourcePO> oleEResourcePOList = document.getOleERSPOItems();

        if (oleeResourceInstances.size()>0 || oleERSLicenses.size()>0 || oleLikedEResources.size()>0 || oleEResourcePOList.size()>0)
        {
            document.setAccessStatus("Active");
        }
        else{
            document.setAccessStatus("Inactive");
            getBusinessObjectService().save(document);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, OLEConstants.OLEEResourceRecord.ERESOURCE_HAS_BEEN_DELETED);
        }
        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=deleteInstance")
    public ModelAndView deleteInstance(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;

        OLEEResourceRecordDocument document = (OLEEResourceRecordDocument)form.getDocument();
        List<OLEEResourceInstance> oleInstancesDeleteList = new ArrayList<>();
        List<OLEEResourceInstance> oleInstancesAddList = new ArrayList<>();
        List<OLEEResourceInstance> oleERSInstances = document.getOleERSInstances();
        if (oleERSInstances!=null) {
            for(OLEEResourceInstance oleeResourceInstance : oleERSInstances) {
                int purchaseOrderCount = 0;
                if (oleeResourceInstance.isSelect()) {
                    String instanceId = oleeResourceInstance.getInstanceId();
                    Map instanceMap = new HashMap();
                    instanceMap.put("instanceId", instanceId);
                    List<OleCopy> oleCopyList = (List<OleCopy>)getBusinessObjectService().findMatching(OleCopy.class, instanceMap);
                    for(OleCopy oleCopy : oleCopyList){
                        if(oleCopy.getPoItemId()!=null){
                            purchaseOrderCount++;
                        }
                    }

                    if (purchaseOrderCount == 0) {
                        oleInstancesDeleteList.add(oleeResourceInstance);
                    }
                    else {
                        oleInstancesAddList.add(oleeResourceInstance);
                    }
                }
            }
        }
        document.setDeletedInstances(oleInstancesDeleteList);
        document.setPurchaseOrderInstances(oleInstancesAddList);
        //form.setDeletedInstance(oleInstancesDeleteList.size());
        BibTrees bibTrees = new BibTrees();
        //StringBuffer deletdInfo  = new StringBuffer();
        for(OLEEResourceInstance oleeResourceInstance : oleInstancesDeleteList){
            //deletdInfo.append(oleeResourceInstance.getInstanceTitle());
            //deletdInfo.append(",");
            BibTree bibTree = new BibTree();
            Bib bib = new Bib();
            bib.setId(oleeResourceInstance.getBibId());
            bibTree.setBib(bib);
            bibTrees.getBibTrees().add(bibTree);
            HoldingsTree holdingsTree = new HoldingsTree();
            Holdings holdings = new Holdings();
            holdings.setOperation(DocstoreDocument.OperationType.DELETE);
            holdings.setId(oleeResourceInstance.getInstanceId());
            holdingsTree.setHoldings(holdings);
            bibTree.getHoldingsTrees().add(holdingsTree);
            getBusinessObjectService().delete(oleeResourceInstance);
            Map<String, String> criteriaMap = new HashMap<>();
            criteriaMap.put(OLEConstants.INSTANCE_ID, oleeResourceInstance.getInstanceId());
            getBusinessObjectService().deleteMatching(OleCopy.class,criteriaMap);
        }
        try{
            getDocstoreClientLocator().getDocstoreClient().processBibTrees(bibTrees);
        }catch(Exception e){

        }
       /* if (deletdInfo.length() > 0) {
            String info = deletdInfo.toString().substring(0, deletdInfo.lastIndexOf(","));
            form.setDeletedInstanceInfo(info);
        }*/

       return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addSearchCriteria")
    public ModelAndView addSearchCriteria(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        List<OLESearchCondition> oleSearchConditions = oleeResourceRecordForm.getOleSearchParams().getSearchFieldsList();
        oleSearchConditions.add(new OLESearchCondition());
        for (OLESearchCondition oleSearchCondition : oleSearchConditions) {
            if (oleSearchCondition.getOperator() == null) {
                oleSearchCondition.setOperator(OLEConstants.OLEEResourceRecord.AND);
            }
        }
        return super.navigate(oleeResourceRecordForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=clearSearch")
    public ModelAndView clearSearch(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        List<OLESearchCondition> oleSearchConditions = oleeResourceRecordForm.getOleSearchParams().getSearchFieldsList();
        int searchConditionSize = oleSearchConditions.size();
        oleeResourceRecordForm.setOleSearchParams(new OLESearchParams());
        oleSearchConditions = oleeResourceRecordForm.getOleSearchParams().getSearchFieldsList();
        for (int ersCount = 0; ersCount < searchConditionSize; ersCount++) {
            oleSearchConditions.add(new OLESearchCondition());
        }
        for (OLESearchCondition oleSearchCondition : oleSearchConditions) {
            oleSearchCondition.setOperator(OLEConstants.OLEEResourceRecord.AND);
        }
        oleeResourceRecordForm.setEresourceDocumentList(null);
        oleeResourceRecordForm.seteResStatusDate(false);
        oleeResourceRecordForm.setBeginDate(null);
        oleeResourceRecordForm.setEndDate(null);
        oleeResourceRecordForm.setStatus(null);
        return super.navigate(oleeResourceRecordForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=cancelSearch")
    public ModelAndView cancelSearch(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        return getUIFModelAndView(oleeResourceRecordForm, "OLEEResourceRecordView-Related-E-ResourceTab");
    }


    @RequestMapping(params = "methodToCall=linkEResourceInstance")
    public ModelAndView linkEResourceInstance(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        List<OLEEResourceInstance> oleeResourceInstances = new ArrayList<>();

        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        List<OLEEResourceInstance> oleeResourceInstanceList = oleeResourceRecordDocument.getOleERSInstances();

        HttpSession session = request.getSession();
        session.setAttribute("oleERSIdentifier", oleeResourceRecordDocument.getOleERSIdentifier());
        session.setAttribute("createChildEResource",Boolean.TRUE);
        StringBuffer instanceIds = new StringBuffer();
        for(OLEEResourceInstance oleeResourceInstance : oleeResourceInstanceList) {
            if(oleeResourceInstance.isSelect()) {
                instanceIds.append(oleeResourceInstance.getInstanceId());
                instanceIds.append(",");
            }
        }
        session.setAttribute("oleeResourceInstancesIdentifier", instanceIds.toString());
        form.setOleeResourceInstances(oleeResourceInstances);

        String url = ConfigContext.getCurrentContextConfig().getProperty("ole.fs.url.base") +
                "/ole-kr-krad/oleERSController?viewId=OLEEResourceRecordView&methodToCall=docHandler&command=initiate&documentClass=org.kuali.ole.select.document.OLEEResourceRecordDocument";

        return performRedirect(form, url);

    }



    @RequestMapping(params = "methodToCall=packageSearch")
    public ModelAndView packageSearch(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {

        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();

        String packageName = oleEResourceRecordForm.getPackageName();
        String platformName = oleEResourceRecordForm.getPlatformName();
        String title = oleEResourceRecordForm.getTitle();
        String platformProvider = oleEResourceRecordForm.getPlatformProvider();
        String publisher = oleEResourceRecordForm.getPublisher();
        String titleInstanceType = oleEResourceRecordForm.getTitleInstanceType();

        List<String> packageStatusList = new ArrayList<>();
        packageStatusList.add("Current");
        packageStatusList.add("Excepted");

        List<String> platformStatusList = new ArrayList<>();
        platformStatusList.add("Current");
        platformStatusList.add("Excepted");

        List<String> tippStatus = new ArrayList<>();
        tippStatus.add("Current");
        tippStatus.add("Excepted");


        List<String> platformProviderList = oleEResourceRecordForm.getPlatformProviderList();
        if(StringUtils.isNotEmpty(platformProvider)) {
            platformProviderList.add(platformProvider);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(packageName);
        stringBuilder.append(platformName);
        stringBuilder.append(title);
        stringBuilder.append(titleInstanceType);

        List<OleGokbView> oleGokbViews = null;

        List<String> isbnList = new ArrayList<>();
        for (OLEStandardIdentifier oleStandardIdentifier : oleeResourceRecordDocument.getStandardIdentifiers()) {
            if(oleStandardIdentifier.getIdentifier() != null && oleStandardIdentifier.getIdentifierType() != null && oleStandardIdentifier.getIdentifierType().equalsIgnoreCase("issn") && StringUtils.isNotEmpty(oleStandardIdentifier.getIdentifier())) {
                isbnList.add(oleStandardIdentifier.getIdentifier());
            }
        }

        if (stringBuilder != null && stringBuilder.length() > 0 || isbnList.size() > 0 || platformProviderList.size() > 0) {

            OLEGOKBSearchDaoOjb olegokbSearchDaoOjb = (OLEGOKBSearchDaoOjb) SpringContext.getBean("oleGOKBSearchDaoOjb");
            oleGokbViews = olegokbSearchDaoOjb.packageSearch(packageName, platformName, platformProviderList, title, isbnList, titleInstanceType, packageStatusList, platformStatusList, tippStatus);

        }
        else {
            oleGokbViews = (List<OleGokbView>) getBusinessObjectService().findAll(OleGokbView.class);
        }

        //search by publisher if given in search params
        if (StringUtils.isNotEmpty(publisher)) {

            Map publisherMap = new HashMap();
            publisherMap.put("organizationName", publisher);
            List<OleGokbOrganization> oleGokbOrganizations = (List<OleGokbOrganization>) getBusinessObjectService().findMatching(OleGokbOrganization.class, publisherMap);

            List<Integer> gokbOrgIds = new ArrayList<>();

            for(OleGokbOrganization oleGokbOrganization : oleGokbOrganizations) {
                gokbOrgIds.add(oleGokbOrganization.getGokbOrganizationId());
            }

            if(oleGokbViews != null && oleGokbViews.size() > 0) {
                Iterator<OleGokbView> oleGokbViewIterator = oleGokbViews.iterator();

                while(oleGokbViewIterator.hasNext()) {
                    OleGokbView oleGokbView = oleGokbViewIterator.next();

                    if(!gokbOrgIds.contains(oleGokbView.getPublisherId())) {
                        oleGokbViewIterator.remove();
                    }
                }
            }
            else if (stringBuilder.length() == 0) {
                oleGokbViews = new ArrayList<>();
                for(Integer id : gokbOrgIds) {
                    oleGokbViews.add(getBusinessObjectService().findBySinglePrimaryKey(OleGokbView.class, id));
                }
            }
        }

        List<OLEGOKbPackage> olegoKbPackages = getOleeResourceHelperService().searchGokbForPackages(oleGokbViews, oleEResourceRecordForm);
        oleeResourceRecordDocument.setGoKbPackageList(olegoKbPackages);

        return getUIFModelAndView(oleEResourceRecordForm);
    }

    @RequestMapping(params = "methodToCall=showTipps")
    public ModelAndView showTipps(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {




        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=getPlatforms")
    public ModelAndView getPlatforms(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();

        List<OLEGOKbPlatform> olegoKbPlatforms = getOleeResourceHelperService().getPlatformByPlackage(oleEResourceRecordForm.getPackageId());
        oleEResourceRecordForm.setShowMultiplePlatforms(true);
        oleeResourceRecordDocument.setGoKbPlatformList(olegoKbPlatforms);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=getTipps")
    public ModelAndView getTipps(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();

        List<OLEGOKbPlatform> goKbPlatformList = new ArrayList<>();
        for(OLEGOKbPlatform gokbPlatform : oleeResourceRecordDocument.getGoKbPlatformList()) {
            if(gokbPlatform.isSelect()) {
                gokbPlatform.setGoKbTIPPList(getOleeResourceHelperService().getTippsByPlatform(gokbPlatform.getPlatformId()));
                goKbPlatformList.add(gokbPlatform);
                gokbPlatform.setSelect(Boolean.FALSE);
            }
        }

        oleeResourceRecordDocument.setGoKbPlatformList(goKbPlatformList);
        oleEResourceRecordForm.setShowTippsWithMorePlatform(true);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=platformSearch")
    public ModelAndView platformSearch(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        List<OLEGOKbTIPP> goKbTIPPs = new ArrayList<>();
        OLEGOKbTIPP olegoKbTIPP = new OLEGOKbTIPP();
        goKbTIPPs.add(olegoKbTIPP);
        goKbTIPPs.add(olegoKbTIPP);
        goKbTIPPs.add(olegoKbTIPP);
        oleeResourceRecordDocument.setGoKbTIPPList(goKbTIPPs);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=tiipResults")
    public ModelAndView tiipResults(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        oleEResourceRecordForm.setShowTiipResults(true);
        return super.navigate(form, result, request, response);
    }


    @RequestMapping(params = "methodToCall=importData")
    public ModelAndView importData(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        KRADServiceLocatorWeb.getDocumentService().updateDocument(oleeResourceRecordDocument);


        OLEBatchProcessProfileBo gokbImportProfile = getOleeResourceHelperService().getGOKBImportProfile(oleeResourceRecordDocument.getProfile());

        List<BibMarcRecord> bibMarcRecords = getOleeResourceHelperService().buildBibMarcRecords(oleeResourceRecordDocument.getGoKbPlatformList(), oleeResourceRecordDocument.getOleERSIdentifier());

        if(bibMarcRecords != null && bibMarcRecords.size() > 0 && gokbImportProfile == null) {
            return getUIFModelAndView(oleEResourceRecordForm);
        }

        if(oleEResourceRecordForm.isImportPackageMetaDataOnly() || (bibMarcRecords != null && bibMarcRecords.size() > 0)) {

            OleGokbPackage oleGokbPackage = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(OleGokbPackage.class, oleEResourceRecordForm.getPackageId());
            getOleeResourceHelperService().overwriteEresourceWithPackage(oleeResourceRecordDocument, oleGokbPackage,"");
            getOleeResourceHelperService().insertOrUpdateGokbElementsForEResource(oleeResourceRecordDocument, false);
            getOleeResourceHelperService().createOrUpdateVendorAndPlatform(oleeResourceRecordDocument);
        }

        if(bibMarcRecords != null && bibMarcRecords.size() > 0) {
            getOleeResourceHelperService().importTipps(gokbImportProfile, bibMarcRecords);
        }
        return getUIFModelAndView(oleEResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=setDefaults")
    public ModelAndView setDefaults(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        if(oleeResourceRecordDocument.getTitle() !=null && StringUtils.isNotEmpty(oleeResourceRecordDocument.getTitle())){
            oleEResourceRecordForm.setPackageName(oleeResourceRecordDocument.getTitle());
        }
//        if(oleeResourceRecordDocument.getPlatformProvider() !=null && StringUtils.isNotEmpty(oleeResourceRecordDocument.getPlatformProvider())){
//            oleEResourceRecordForm.setPlatformProvider(oleeResourceRecordDocument.getPlatformProvider());
//        }

        List<String> platformProviderList = getOleeResourceHelperService().getPlatformProvidersForInstance(oleeResourceRecordDocument.getOleERSInstances());

        oleEResourceRecordForm.setPlatformProviderList(platformProviderList);

        if(oleeResourceRecordDocument.getIsbn() != null && StringUtils.isNotEmpty(oleeResourceRecordDocument.getIsbn())){
            oleeResourceRecordDocument.getStandardIdentifiers().get(0).setIdentifier(oleeResourceRecordDocument.getIsbn());
        }
        this.packageSearch(oleEResourceRecordForm, result, request, response);

        return super.navigate(oleEResourceRecordForm, result, request, response);
    }

    /**
     * This method is to get manage GOKb data on E-Resource
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */

    @RequestMapping(params = "methodToCall=getManageGOKbDataOnEIns")
    public ModelAndView getManageGOKbDataOnEIns(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        String selectedLine = request.getParameter("lineIndex");
        String eResId = request.getParameter("eResId");
        OLEEResourceRecordDocument oleeResourceRecordDocument = getBusinessObjectService().findBySinglePrimaryKey(OLEEResourceRecordDocument.class, eResId);
        oleeResourceRecordDocument.setOleGOKbMappingValueList(new ArrayList<OLEGOKbMappingValue>());

        if (StringUtils.isNotBlank(selectedLine)) {
            OLEEResourceInstance oleERSInstance = oleeResourceRecordDocument.getOleERSInstances().get(Integer.parseInt(selectedLine));
            Map map = new HashMap();
            map.put("recordId", oleERSInstance.getInstanceId());
            map.put("recordType", "E-Instance");
            List<OLEGOKbMappingValue> goKbMappingValueList = (List<OLEGOKbMappingValue>) getBusinessObjectService().findMatching(OLEGOKbMappingValue.class, map);
            if (goKbMappingValueList != null && goKbMappingValueList.size() > 0) {
                for (OLEGOKbMappingValue olegoKbMappingValue : goKbMappingValueList) {
                    if(StringUtils.isNotEmpty(olegoKbMappingValue.getLocalValue())) {
                        olegoKbMappingValue.setGokbFlag(false);
                    }
                    else {
                        olegoKbMappingValue.setGokbFlag(true);
                    }
                    oleEResourceRecordForm.getOleGOKbMappingValueList().add(olegoKbMappingValue);
                }
            }
        }
        oleEResourceRecordForm.setFlagPop(true);
        return getUIFModelAndView(oleEResourceRecordForm);
    }

    /**
     * This method is to get manage GOKb data on E-Instance
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */

    @RequestMapping(params = "methodToCall=getManageGOKbDataOnERes")
    public ModelAndView getManageGOKbDataOnERes(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        oleeResourceRecordDocument.setOleGOKbMappingValueList(new ArrayList<OLEGOKbMappingValue>());
        Map map = new HashMap();
        map.put("recordId", oleeResourceRecordDocument.getOleERSIdentifier());
        map.put("recordType", "E-Resource");
        List<OLEGOKbMappingValue> goKbMappingValueList = (List<OLEGOKbMappingValue>) getBusinessObjectService().findMatching(OLEGOKbMappingValue.class, map);
        if (goKbMappingValueList != null && goKbMappingValueList.size() > 0) {
            for (OLEGOKbMappingValue olegoKbMappingValue : goKbMappingValueList) {
                if(StringUtils.isNotEmpty(olegoKbMappingValue.getLocalValue())) {
                    olegoKbMappingValue.setGokbFlag(false);
                }
                else {
                    olegoKbMappingValue.setGokbFlag(true);
                }
                oleeResourceRecordDocument.getOleGOKbMappingValueList().add(olegoKbMappingValue);
            }
        }
        oleEResourceRecordForm.setFlagPop(true);
        return getUIFModelAndView(oleEResourceRecordForm);
    }

    /**
     * This method is to get manage GOKb data on E-Instance
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return ModelAndView
     */

    @RequestMapping(params = "methodToCall=showMultipleContacts")
    public ModelAndView showMultipleContacts(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        String eResourceId = request.getParameter("eResourceId");
        Map map = new HashMap();
        map.put("oleERSIdentifier", eResourceId);
        List<OLEEResourceRecordDocument> oleeResourceRecordDocuments = (List<OLEEResourceRecordDocument>) getBusinessObjectService().findMatching(OLEEResourceRecordDocument.class, map);
        OLEEResourceRecordDocument oleeResourceRecordDocument = null;
        if(oleeResourceRecordDocuments != null && oleeResourceRecordDocuments.size() > 0) {
            oleeResourceRecordDocument = oleeResourceRecordDocuments.get(0);
            int index = Integer.parseInt(request.getParameter("index"));
            getOleeResourceHelperService().updateVendorInfo(oleeResourceRecordDocument);

            if(oleeResourceRecordDocument.getOleERSContacts() != null && oleeResourceRecordDocument.getOleERSContacts().size() >= index) {
                OLEEResourceContacts oleeResourceContacts = oleeResourceRecordDocument.getOleERSContacts().get(index);
                oleEResourceRecordForm.setVendorNameForContacts(oleeResourceContacts.getOrganization());
                oleEResourceRecordForm.setPhoneNos(oleeResourceContacts.getOlePhoneNumbers());
            }
        }

        return getUIFModelAndView(oleEResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=deleteEresource")
    public ModelAndView deleteEresource(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument)oleEResourceRecordForm.getDocument();
        if(oleeResourceRecordDocument.geteRSInstances().size()==0 && oleeResourceRecordDocument.getOleLinkedEresources().size() ==0 && oleeResourceRecordDocument.getOleERSPOItems().size()==0 && oleeResourceRecordDocument.getOleERSLicenseRequests().size()==0){
            getBusinessObjectService().delete(oleeResourceRecordDocument);
        }else{
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_INFO,OLEConstants.OLEEResourceRecord.ERROR_LINKED_RECORD);
            return getUIFModelAndView(oleEResourceRecordForm);
        }
        String url = ConfigContext.getCurrentContextConfig().getProperty("ole.fs.url.base") + "?selectedTab=oleSelectAcquire";
        return performRedirect(oleEResourceRecordForm, url);
    }

    @RequestMapping(params = "methodToCall=refreshPOSection")
    public ModelAndView refreshPOSection(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleeResourceRecordForm = (OLEEResourceRecordForm) uifForm;
        return getUIFModelAndView(oleeResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=populateAccountingLinesToEResource")
    public ModelAndView populateAccountingLinesToEResource(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                           HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        oleEResourceRecordForm.setPoSuccessMessage(null);
        oleEResourceRecordForm.setPoErrorMessage(null);
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        /*BindingInfo addLineBindingInfo = (BindingInfo) form.getViewPostMetadata().getComponentPostData(
                selectedCollectionId, UifConstants.PostMetadata.ADD_LINE_BINDING_INFO);*/
        CollectionGroup collectionGroup = form.getPostedView().getViewIndex().getCollectionGroupByPath(
                selectedCollectionPath);
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        Object eventObject = ObjectPropertyUtils.getPropertyValue(oleEResourceRecordForm, addLinePath);
        OleFundCode oleFundCode = (OleFundCode) eventObject;
        if (oleFundCode != null) {
            if (StringUtils.isBlank(oleFundCode.getFundCode())) {
                oleEResourceRecordForm.setPoErrorMessage("Fund Code is required.");
                return getUIFModelAndView(oleEResourceRecordForm);
            } else {
                Map fundMap = new HashMap();
                fundMap.put(OLEConstants.OLEEResourceRecord.FUND_CODE, oleFundCode.getFundCode());
                OleFundCode fundCode = getBusinessObjectService().findByPrimaryKey(OleFundCode.class, fundMap);
                if (fundCode == null) {
                    oleEResourceRecordForm.setPoErrorMessage("Fund Code is invalid.");
                    return getUIFModelAndView(oleEResourceRecordForm);
                } else {
                    if (fundCode.getOleFundCodeAccountingLineList() != null) {
                        Map<String, String> actionParameters = form.getActionParameters();
                        String mainCollectionIndex = StringUtils.substringBefore(StringUtils.substringAfter(actionParameters.get(UifParameters.SELLECTED_COLLECTION_PATH), "["), "]");
                        int mainIndex = Integer.parseInt(mainCollectionIndex);
                        OLECreatePO oleCreatePO = oleEResourceRecordForm.geteResourcePOs().get(mainIndex);
                        if (oleCreatePO != null) {
                            oleFundCode.setFundCode(null);
                            for (OleFundCodeAccountingLine oleFundCodeAccountingLine : fundCode.getOleFundCodeAccountingLineList()) {
                                OLECretePOAccountingLine oleCretePOAccountingLine = new OLECretePOAccountingLine();
                                oleCretePOAccountingLine.setChartOfAccountsCode(oleFundCodeAccountingLine.getChartCode());
                                oleCretePOAccountingLine.setAccountNumber(oleFundCodeAccountingLine.getAccountNumber());
                                oleCretePOAccountingLine.setSubAccountNumber(oleFundCodeAccountingLine.getSubAccount());
                                oleCretePOAccountingLine.setFinancialObjectCode(oleFundCodeAccountingLine.getObjectCode());
                                oleCretePOAccountingLine.setFinancialSubObjectCode(oleFundCodeAccountingLine.getSubObject());
                                oleCretePOAccountingLine.setProjectCode(oleFundCodeAccountingLine.getProject());
                                oleCretePOAccountingLine.setOrganizationReferenceId(oleFundCodeAccountingLine.getOrgRefId());
                                oleCretePOAccountingLine.setAccountLinePercent(oleFundCodeAccountingLine.getPercentage());
                                oleCreatePO.getAccountingLines().add(oleCretePOAccountingLine);
                            }
                        }
                    }
                }
            }
        }
        return getUIFModelAndView(oleEResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=populateAccountingLinesToInstance")
    public ModelAndView populateAccountingLinesToInstance(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        oleEResourceRecordForm.setPoSuccessMessage(null);
        oleEResourceRecordForm.setPoErrorMessage(null);
        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        /*BindingInfo addLineBindingInfo = (BindingInfo) form.getViewPostMetadata().getComponentPostData(
                selectedCollectionId, UifConstants.PostMetadata.ADD_LINE_BINDING_INFO);*/
        CollectionGroup collectionGroup = form.getPostedView().getViewIndex().getCollectionGroupByPath(
                selectedCollectionId);
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        Object eventObject = ObjectPropertyUtils.getPropertyValue(oleEResourceRecordForm, addLinePath);
        OleFundCode oleFundCode = (OleFundCode) eventObject;
        if (oleFundCode != null) {
            if (StringUtils.isBlank(oleFundCode.getFundCode())) {
                oleEResourceRecordForm.setPoErrorMessage("Fund Code is required.");
                return getUIFModelAndView(oleEResourceRecordForm);
            } else {
                Map fundMap = new HashMap();
                fundMap.put(OLEConstants.OLEEResourceRecord.FUND_CODE, oleFundCode.getFundCode());
                OleFundCode fundCode = getBusinessObjectService().findByPrimaryKey(OleFundCode.class, fundMap);
                if (fundCode == null) {
                    oleEResourceRecordForm.setPoErrorMessage("Fund Code is invalid.");
                    return getUIFModelAndView(oleEResourceRecordForm);
                } else {
                    if (fundCode.getOleFundCodeAccountingLineList() != null) {
                        Map<String, String> actionParameters = form.getActionParameters();
                        String mainCollectionIndex = StringUtils.substringBefore(StringUtils.substringAfter(actionParameters.get(UifParameters.SELLECTED_COLLECTION_PATH), "["), "]");
                        int mainIndex = Integer.parseInt(mainCollectionIndex);
                        OLECreatePO oleCreatePO = oleEResourceRecordForm.getInstancePOs().get(mainIndex);
                        if (oleCreatePO != null) {
                            oleFundCode.setFundCode(null);
                            for (OleFundCodeAccountingLine oleFundCodeAccountingLine : fundCode.getOleFundCodeAccountingLineList()) {
                                OLECretePOAccountingLine oleCretePOAccountingLine = new OLECretePOAccountingLine();
                                oleCretePOAccountingLine.setChartOfAccountsCode(oleFundCodeAccountingLine.getChartCode());
                                oleCretePOAccountingLine.setAccountNumber(oleFundCodeAccountingLine.getAccountNumber());
                                oleCretePOAccountingLine.setSubAccountNumber(oleFundCodeAccountingLine.getSubAccount());
                                oleCretePOAccountingLine.setFinancialObjectCode(oleFundCodeAccountingLine.getObjectCode());
                                oleCretePOAccountingLine.setFinancialSubObjectCode(oleFundCodeAccountingLine.getSubObject());
                                oleCretePOAccountingLine.setProjectCode(oleFundCodeAccountingLine.getProject());
                                oleCretePOAccountingLine.setOrganizationReferenceId(oleFundCodeAccountingLine.getOrgRefId());
                                oleCretePOAccountingLine.setAccountLinePercent(oleFundCodeAccountingLine.getPercentage());
                                oleCreatePO.getAccountingLines().add(oleCretePOAccountingLine);
                            }
                        }
                    }
                }
            }
        }
        return getUIFModelAndView(oleEResourceRecordForm);
    }


    @RequestMapping(params = "methodToCall=addAccountingLine")
    public ModelAndView addAccountingLine(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        oleEResourceRecordForm.setPoSuccessMessage(null);
        oleEResourceRecordForm.setPoErrorMessage(null);
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        CollectionGroup collectionGroup = form.getPostedView().getViewIndex().getCollectionGroupByPath(
                selectedCollectionPath);
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        Object eventObject = ObjectPropertyUtils.getPropertyValue(oleEResourceRecordForm, addLinePath);
        OLECretePOAccountingLine accountingLine = (OLECretePOAccountingLine) eventObject;
        if (StringUtils.isEmpty(accountingLine.getChartOfAccountsCode()) || StringUtils.isEmpty(accountingLine.getAccountNumber()) || StringUtils.isEmpty(accountingLine.getFinancialObjectCode()) || ObjectUtils.isNull(accountingLine.getAccountLinePercent())) {
            oleEResourceRecordForm.setPoErrorMessage("Please fill all mandatory fields.");
            return getUIFModelAndView(oleEResourceRecordForm);
        } else {
            String errorMessage = new String();
            errorMessage = getOleEResourceSearchService().validateAccountingLines(errorMessage, accountingLine);
            if (errorMessage.length() > 0) {
                Map<String, String> actionParameters = form.getActionParameters();
                String mainCollectionIndex = StringUtils.substringBefore(StringUtils.substringAfter(actionParameters.get(UifParameters.SELLECTED_COLLECTION_PATH), "["), "]");
                int mainIndex = Integer.parseInt(mainCollectionIndex);
                OLECreatePO oleCreatePO = oleEResourceRecordForm.getInstancePOs().get(mainIndex);
                errorMessage = errorMessage.concat(" for '" + oleCreatePO.getTitle() + "'");
                errorMessage = errorMessage.concat(OLEConstants.BREAK);
                oleEResourceRecordForm.setPoErrorMessage(errorMessage);
                return getUIFModelAndView(oleEResourceRecordForm);
            }
        }
        return addLine(oleEResourceRecordForm,result,request,response);
    }

    @RequestMapping(params = "methodToCall=addEResAccountingLine")
    public ModelAndView addEResAccountingLine(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        CollectionGroup collectionGroup = form.getPostedView().getViewIndex().getCollectionGroupByPath(
                selectedCollectionPath);
        String addLinePath = collectionGroup.getAddLineBindingInfo().getBindingPath();
        Object eventObject = ObjectPropertyUtils.getPropertyValue(oleEResourceRecordForm, addLinePath);
        OLEEResourceAccountingLine accountingLine = (OLEEResourceAccountingLine) eventObject;
        if (StringUtils.isEmpty(accountingLine.getChartOfAccountsCode()) || StringUtils.isEmpty(accountingLine.getAccountNumber()) || StringUtils.isEmpty(accountingLine.getFinancialObjectCode()) || ObjectUtils.isNull(accountingLine.getAccountLinePercent())) {
            GlobalVariables.getMessageMap().putErrorForSectionId("OLEEResourceMainTab-AccountingLines", OLEConstants.OLEEResourceRecord.ERROR_FILL_MANDATORY_FIELDS);
            return getUIFModelAndView(oleEResourceRecordForm);
        } else {
            if (getOleEResourceSearchService().validateAccountingLines(accountingLine,"OLEEResourceMainTab-AccountingLines")) {
                return getUIFModelAndView(oleEResourceRecordForm);
            }
        }
        return addLine(oleEResourceRecordForm,result,request,response);
    }

    @RequestMapping(params = "methodToCall=populateAccountingLines")
    public ModelAndView populateAccountingLines(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        if (StringUtils.isNotBlank(oleeResourceRecordDocument.getFundCode())) {
            Map fundMap = new HashMap();
            fundMap.put(OLEConstants.OLEEResourceRecord.FUND_CODE, oleeResourceRecordDocument.getFundCode());
            OleFundCode fundCode = getBusinessObjectService().findByPrimaryKey(OleFundCode.class, fundMap);
            if (fundCode == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId("OLEEResourceMainTab-FundCode", OLEConstants.OLEEResourceRecord.ERROR_INVALID_FUND_CODE);
                return getUIFModelAndView(oleEResourceRecordForm);
            } else {
                if (fundCode.getOleFundCodeAccountingLineList() != null) {
                    oleeResourceRecordDocument.setFundCode(null);
                    for (OleFundCodeAccountingLine oleFundCodeAccountingLine : fundCode.getOleFundCodeAccountingLineList()) {
                        OLEEResourceAccountingLine oleeResourceAccountingLine = new OLEEResourceAccountingLine();
                        oleeResourceAccountingLine.setChartOfAccountsCode(oleFundCodeAccountingLine.getChartCode());
                        oleeResourceAccountingLine.setAccountNumber(oleFundCodeAccountingLine.getAccountNumber());
                        oleeResourceAccountingLine.setSubAccountNumber(oleFundCodeAccountingLine.getSubAccount());
                        oleeResourceAccountingLine.setFinancialObjectCode(oleFundCodeAccountingLine.getObjectCode());
                        oleeResourceAccountingLine.setFinancialSubObjectCode(oleFundCodeAccountingLine.getSubObject());
                        oleeResourceAccountingLine.setProjectCode(oleFundCodeAccountingLine.getProject());
                        oleeResourceAccountingLine.setOrganizationReferenceId(oleFundCodeAccountingLine.getOrgRefId());
                        oleeResourceAccountingLine.setAccountLinePercent(oleFundCodeAccountingLine.getPercentage());
                        oleeResourceRecordDocument.getAccountingLines().add(oleeResourceAccountingLine);
                    }
                }
            }
        }
        return getUIFModelAndView(oleEResourceRecordForm);
    }

    @RequestMapping(params = "methodToCall=startAccessWorkflow")
    public ModelAndView startAccessWorkflow(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response)  {

        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        OLEAccessActivationWorkFlow accessActivationWorkFlow = null;
        String status=null;
        String roleId=null;
        String namespaceCode = null;
        MaintenanceDocument newDocument = null;
        deleteMaintenanceLock();
        org.kuali.rice.krad.service.DocumentService documentService =  GlobalResourceLoader.getService(OLEConstants.DOCUMENT_HEADER_SERVICE);
        try{
        newDocument = (MaintenanceDocument) documentService.getNewDocument("OLE_ERES_ACCESS_MD");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        newDocument.getDocumentHeader().setDocumentDescription(OLEConstants.ACCESS_ACTIVATION_DESCRIPTION);
        OLEEResourceAccessActivation oleeResourceAccess = (OLEEResourceAccessActivation)newDocument.getNewMaintainableObject().getDataObject();
        oleeResourceAccess.setDateAccessConfirmed(oleeResourceRecordDocument.getDateAccessConfirmed());
        oleeResourceAccess.setWorkflowId(oleeResourceRecordDocument.getWorkflowConfigurationId());
        oleeResourceAccess.setOleERSIdentifier(oleeResourceRecordDocument.getOleERSIdentifier());
        Map<String,String> accessConfigMap = new HashMap<String,String>();
        accessConfigMap.put("accessActivationConfigurationId",oleeResourceRecordDocument.getWorkflowConfigurationId());
        List<OLEAccessActivationConfiguration> oleAccessActivationConfigurations = (List<OLEAccessActivationConfiguration>)KRADServiceLocator.getBusinessObjectService().findMatchingOrderBy(OLEAccessActivationConfiguration.class,accessConfigMap,"orderNo",true);
        List<OLEAccessActivationWorkFlow> oleAccessActivationWorkFlows = null;
        if(oleAccessActivationConfigurations!=null && oleAccessActivationConfigurations.size()>0){
            oleAccessActivationWorkFlows = oleAccessActivationConfigurations.get(0).getAccessActivationWorkflowList();
       if(oleAccessActivationWorkFlows!=null && oleAccessActivationWorkFlows.size()>0){
           accessActivationWorkFlow  = oleAccessActivationWorkFlows.get(0);
           status = accessActivationWorkFlow.getStatus();
           roleId = accessActivationWorkFlow.getRoleId();
       }
        }else{
           GlobalVariables.getMessageMap().putError("accessActivationConfigurationId", "Invalid workflow");
           return getUIFModelAndView(form);
       }
        oleeResourceAccess.setAccessStatus(status);
        List<AdHocRoutePerson> adHocRouteRecipients = new ArrayList<AdHocRoutePerson>();
        org.kuali.rice.kim.api.role.RoleService roleService = (org.kuali.rice.kim.api.role.RoleService) KimApiServiceLocator.getRoleService();
        Role role = roleService.getRole(roleId);
        Collection<String> principalIds = (Collection<String>) roleService.getRoleMemberPrincipalIds(role.getNamespaceCode(),role.getName(),new HashMap<String, String>());
        IdentityService identityService = KimApiServiceLocator.getIdentityService();
        List<String> principalList = new ArrayList<String>();
        principalList.addAll(principalIds);
        List<Principal> principals = identityService.getPrincipals(principalList);
        AdHocRoutePerson adHocRoutePerson;
        OLEEResourceAccessWorkflow oleeResourceAccessWorkflow = new OLEEResourceAccessWorkflow();
        oleeResourceAccessWorkflow.setStatus(status);
        if(principals!=null && principals.size()>0){
        oleeResourceAccessWorkflow.setCurrentOwner(principals.get(0).getPrincipalName());
        }
        oleeResourceAccessWorkflow.setDescription(oleeResourceRecordDocument.getAccessDescription());
        oleeResourceAccessWorkflow.setLastApproved(new Timestamp(System.currentTimeMillis()));
        oleeResourceAccess.getOleERSAccessWorkflows().add(oleeResourceAccessWorkflow);
        if(principals!=null && principals.size()>0){
            for(Principal principal : principals){
                adHocRoutePerson = new AdHocRoutePerson();
                adHocRoutePerson.setId(principal.getPrincipalId());
                adHocRoutePerson.setName(principal.getPrincipalName());
                adHocRoutePerson.setActionRequested("A");
                adHocRoutePerson.setdocumentNumber(newDocument.getDocumentNumber());
                adHocRoutePerson.setType(0);
                adHocRouteRecipients.add(adHocRoutePerson);
            }
        }
        List<AdHocRouteRecipient>  adHocRouteRecipientList = new ArrayList<AdHocRouteRecipient>();
        adHocRouteRecipientList.addAll(adHocRouteRecipients);
        try{
            getDocumentService().routeDocument(newDocument, "Needed Approval for the status : " + status + " from the members of the Role : " + role.getName()  , adHocRouteRecipientList);
            List<ActionTakenValue>  actionTakenList = (List<ActionTakenValue>)KEWServiceLocator.getActionTakenService().getActionsTaken(newDocument.getDocumentNumber());
            ActionTakenValue actionTakenValue = (ActionTakenValue)actionTakenList.get(actionTakenList.size()-1);
            actionTakenValue.setAnnotation("Initiated the access activation workflow");
            KEWServiceLocator.getActionTakenService().saveActionTaken(actionTakenValue);
            oleeResourceRecordDocument.setOleAccessActivationDocumentNumber(newDocument.getDocumentNumber());
            deleteMaintenanceLock();
        }catch(Exception e){
            e.printStackTrace();
        }
   return  getUIFModelAndView(form);
    }




    public void deleteMaintenanceLock(){
        List<MaintenanceLock> maintenanceLocks = (List<MaintenanceLock>)getBusinessObjectService().findAll(MaintenanceLock.class);
        List<MaintenanceLock> deleteMaintenanceLockList = new ArrayList<MaintenanceLock>();
        if(maintenanceLocks!=null && maintenanceLocks.size()>0){
            for(MaintenanceLock maintenanceLock : maintenanceLocks){
                if(maintenanceLock.getLockingRepresentation().contains("org.kuali.ole.select.bo.OLEEResourceAccessActivation")){
                    deleteMaintenanceLockList.add(maintenanceLock);
                }
            }
        }
        if(deleteMaintenanceLockList.size()>0){
            getBusinessObjectService().delete(deleteMaintenanceLockList);
        }
    }

    @RequestMapping(params = "methodToCall=downloadEventAttachment1")
    public ModelAndView downloadEventAttachment1(@ModelAttribute("KualiForm") TransactionalDocumentFormBase form, BindingResult result,
                                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        int index = Integer.parseInt(oleEResourceRecordForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        OLEEResourceEventLog oleeResourceEventLog = oleeResourceRecordDocument.getOleERSEventLogs().get(index);
        getOleEResourceSearchService().downloadAttachment(response, oleeResourceEventLog.getOleEResEventLogID(), oleeResourceEventLog.getAttachmentFileName1(), oleeResourceEventLog.getAttachmentContent1(), oleeResourceEventLog.getAttachmentMimeType1());
        return super.navigate(oleEResourceRecordForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=downloadEventAttachment2")
    public ModelAndView downloadEventAttachment2(@ModelAttribute("KualiForm") TransactionalDocumentFormBase form, BindingResult result,
                                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        OLEEResourceRecordForm oleEResourceRecordForm = (OLEEResourceRecordForm) form;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) oleEResourceRecordForm.getDocument();
        int index = Integer.parseInt(oleEResourceRecordForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        OLEEResourceEventLog oleeResourceEventLog = oleeResourceRecordDocument.getOleERSEventLogs().get(index);
        getOleEResourceSearchService().downloadAttachment(response, oleeResourceEventLog.getOleEResEventLogID(), oleeResourceEventLog.getAttachmentFileName2(), oleeResourceEventLog.getAttachmentContent2(), oleeResourceEventLog.getAttachmentMimeType2());
        return super.navigate(oleEResourceRecordForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=saveGokbData")
    public ModelAndView saveGokbData(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {
        OLEEResourceRecordForm form = (OLEEResourceRecordForm) uifForm;
        OLEEResourceRecordDocument oleeResourceRecordDocument = (OLEEResourceRecordDocument) form.getDocument();
        for (OLEGOKbMappingValue olegoKbMappingValue : oleeResourceRecordDocument.getOleGOKbMappingValueList()) {
            if (olegoKbMappingValue.isReset()) {
                getBusinessObjectService().delete(olegoKbMappingValue);
            }
        }
        return getUIFModelAndView(form);
    }


}

