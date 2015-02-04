package org.kuali.ole.deliver.lookup;

import org.apache.commons.lang.StringUtils;
import org.kuali.ole.OLEConstants;
import org.kuali.ole.deliver.bo.OleDeliverRequestBo;
import org.kuali.ole.deliver.service.OleDeliverRequestDocumentHelperServiceImpl;
import org.kuali.ole.sys.context.SpringContext;
import org.kuali.ole.util.DocstoreUtil;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ?
 * Date: 10/11/12
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */

public class OleDeliverRequestLookupableImpl extends LookupableImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OleDeliverRequestLookupableImpl.class);
    private OleDeliverRequestDocumentHelperServiceImpl oleDeliverRequestDocumentHelperService = new OleDeliverRequestDocumentHelperServiceImpl();
    private String itemTitle;
    //private String borrowerName;
    private String firstName;
    private String lastName;
    private String itemId;
    private String borrowerId;
    private String borrowerBarcode;
    private DocstoreUtil docstoreUtil;
    public DocstoreUtil getDocstoreUtil() {

        if (docstoreUtil == null) {
            docstoreUtil = SpringContext.getBean(DocstoreUtil.class);

        }
        return docstoreUtil;
    }


    @Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall,
                                      List<String> pkNames) {
        LOG.debug("Inside getActionUrlHref()");
        LookupView lookupView = (LookupView) lookupForm.getView();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
        for (String primaryKey : primaryKeyValues.keySet()) {
            String primaryKeyValue = primaryKeyValues.get(primaryKey);

            props.put(primaryKey, primaryKeyValue);
        }

        if (StringUtils.isNotBlank(lookupForm.getReturnLocation())) {
            props.put(KRADConstants.RETURN_LOCATION_PARAMETER, lookupForm.getReturnLocation());
        }

        props.put(UifParameters.DATA_OBJECT_CLASS_NAME, OleDeliverRequestBo.class.getName());
        props.put(UifParameters.VIEW_TYPE_NAME, UifConstants.ViewType.MAINTENANCE.name());

        String maintenanceMapping = OLEConstants.OleDeliverRequest.DELIVER_REQUEST_MAINTENANCE_ACTION_LINK;

        return UrlFactory.parameterizeUrl(maintenanceMapping, props);
    }


    @Override
    public Collection<?> performSearch(LookupForm form, Map<String, String> searchCriteria, boolean bounded) {
        LOG.debug("Inside performSearch()");
        Map<String, String> lookupcriteria = form.getLookupCriteria();
        HashMap<String, String> requestParameters = (HashMap<String, String>) form.getInitialRequestParameters();
        if (requestParameters.containsKey(OLEConstants.OleDeliverRequest.FLAG)) {
            if (requestParameters.get(OLEConstants.OleDeliverRequest.FLAG).equalsIgnoreCase("true")) {
                String id = requestParameters.get(OLEConstants.OleDeliverRequest.ITEM_ID);
                searchCriteria.put(OLEConstants.OleDeliverRequest.ITEM_ID, id);
                lookupcriteria.put(OLEConstants.OleDeliverRequest.ITEM_ID, id);
                form.setLookupCriteria(lookupcriteria);
            }
        }
        itemTitle = searchCriteria.get(OLEConstants.TITLE) != null ? searchCriteria.get(OLEConstants.TITLE) : "";
        //borrowerName = searchCriteria.get(OLEConstants.BORROWER_NAME);
        firstName = searchCriteria.get(OLEConstants.OlePatron.PATRON_FIRST_NAME) != null ? searchCriteria.get(OLEConstants.OlePatron.PATRON_FIRST_NAME) : "";
        lastName = searchCriteria.get(OLEConstants.OlePatron.PATRON_LAST_NAME) != null ? searchCriteria.get(OLEConstants.OlePatron.PATRON_LAST_NAME) : "";
        itemId = searchCriteria.get(OLEConstants.OleDeliverRequest.ITEM_ID) != null ? searchCriteria.get(OLEConstants.OleDeliverRequest.ITEM_ID) : "";
        borrowerId = searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_ID) != null ? searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_ID) : "";
        borrowerBarcode = searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_BARCODE) != null ? searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_BARCODE) : "";
       /* firstName = searchCriteria.get(OLEConstants.OlePatron.PATRON_FIRST_NAME);
        lastName = searchCriteria.get(OLEConstants.OlePatron.PATRON_LAST_NAME);
        itemId = searchCriteria.get(OLEConstants.OleDeliverRequest.ITEM_ID);
        borrowerId = searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_ID);
        borrowerBarcode = searchCriteria.get(OLEConstants.OleDeliverRequest.BORROWER_BARCODE);*/
        searchCriteria.remove(OLEConstants.TITLE);
        searchCriteria.remove(OLEConstants.OlePatron.PATRON_FIRST_NAME);
        searchCriteria.remove(OLEConstants.OlePatron.PATRON_LAST_NAME);
        List<OleDeliverRequestBo> displayList = new ArrayList<OleDeliverRequestBo>();
        // List<OlePatronDocument> olePatronDocumentList = new ArrayList<OlePatronDocument>();
        List<EntityNameBo> entityNameBos = new ArrayList<EntityNameBo>();
        //String modifiedBorrowerName = borrowerName.trim();
        String modifiedBorrowerFirstName = firstName.trim();
        String modifiedBorrowerLastName = lastName.trim();
       /* String[] name = borrowerName.split(" ");
        if (name.length > 0) {
            modifiedBorrowerName = name[0];
        }*/

        if ((borrowerBarcode.equals("")) && (!modifiedBorrowerFirstName.equals("") || !modifiedBorrowerLastName.equals(""))) {
            Map<String, String> patronMap = new HashMap<String, String>();
            if (!modifiedBorrowerFirstName.equals("") && modifiedBorrowerFirstName != null) {
                patronMap.put(OLEConstants.OlePatron.PATRON_FIRST_NAME, modifiedBorrowerFirstName);
            }
            if (!modifiedBorrowerLastName.equals("") && modifiedBorrowerLastName != null) {
                patronMap.put(OLEConstants.OlePatron.PATRON_LAST_NAME, modifiedBorrowerLastName);
            }
            //entityNameBos = (List<EntityNameBo>) KRADServiceLocator.getBusinessObjectService().findMatching(EntityNameBo.class, patronMap);
            entityNameBos = (List<EntityNameBo>) getLookupService().findCollectionBySearchHelper(EntityNameBo.class, patronMap, true);

            if (entityNameBos.size() == 0) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, OLEConstants.NO_RECORD_FOUND);
            }
            if (entityNameBos.size() > 0) {
                for (int i = 0; i < entityNameBos.size(); i++) {

                    searchCriteria.put(OLEConstants.OleDeliverRequest.BORROWER_ID, entityNameBos.get(i).getEntityId());
                    displayList.addAll((List<OleDeliverRequestBo>) super.performSearch(form, searchCriteria, bounded));
                    searchCriteria.remove(OLEConstants.OleDeliverRequest.BORROWER_ID);
                }
            }
        } else {
            displayList = (List<OleDeliverRequestBo>) super.performSearch(form, searchCriteria, bounded);
        }
        searchCriteria.put(OLEConstants.TITLE, itemTitle);
        //searchCriteria.put(OLEConstants.BORROWER_NAME, borrowerName);
        searchCriteria.put(OLEConstants.OlePatron.PATRON_FIRST_NAME, firstName);
        searchCriteria.put(OLEConstants.OlePatron.PATRON_LAST_NAME, lastName);

        OleDeliverRequestBo oleDeliverRequestBo;
        List<OleDeliverRequestBo> oleDeliverRequestBoList = new ArrayList<OleDeliverRequestBo>();
        try {
            for (int i = 0; i < displayList.size(); i++) {
                displayList.get(i).setItemType(null);
                oleDeliverRequestBo = displayList.get(i);

                getDocstoreUtil().isItemAvailableInDocStore(oleDeliverRequestBo);
                if (itemTitle != null && !itemTitle.isEmpty() && !oleDeliverRequestBo.getTitle().toUpperCase().equals(itemTitle.toUpperCase())) {
                    // displayList.remove(i);
                } else {
                    Map<String, String> patronMap = new HashMap<String, String>();
                    patronMap.put(OLEConstants.OlePatron.PATRON_FIRST_NAME, displayList.get(i).getFirstName());
                    patronMap.put(OLEConstants.OlePatron.PATRON_LAST_NAME, displayList.get(i).getLastName());
                    entityNameBos = (List<EntityNameBo>) KRADServiceLocator.getBusinessObjectService().findMatching(EntityNameBo.class, patronMap);
                    if (entityNameBos.size() > 0) {
                        displayList.get(i).setFirstName(displayList.get(i).getFirstName());
                        displayList.get(i).setLastName(displayList.get(i).getLastName());
                    }
                    oleDeliverRequestBoList.add(displayList.get(i));
                }

            }
        } catch (Exception e) {
            LOG.error("Not able to retrieve data:" + e.getMessage(), e);
        }
        if (oleDeliverRequestBoList.size() == 0) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, OLEConstants.NO_RECORD_FOUND);
        }

        return oleDeliverRequestBoList;
    }

}


