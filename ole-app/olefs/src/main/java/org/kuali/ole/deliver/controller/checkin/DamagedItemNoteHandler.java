package org.kuali.ole.deliver.controller.checkin;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kuali.ole.OLEConstants;
import org.kuali.ole.deliver.bo.OleCirculationDesk;
import org.kuali.ole.deliver.bo.OlePatronDocument;
import org.kuali.ole.deliver.bo.OlePatronNoteType;
import org.kuali.ole.deliver.bo.OlePatronNotes;
import org.kuali.ole.deliver.controller.checkout.CircUtilController;
import org.kuali.ole.deliver.service.ParameterValueResolver;
import org.kuali.ole.sys.context.SpringContext;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by angelind on 8/18/15.
 */
public class DamagedItemNoteHandler {


    public void savePatronNoteForDamaged(Map<String, Object> damagedRecordInfo, OlePatronDocument olePatronDocument) {
        String note = getPatronNoteToRecord(OLEConstants.DAMAGED_ITEM_CHECKED_IN_FLAG, damagedRecordInfo);
        Map map = new HashMap();
        if(olePatronDocument != null) {
            OlePatronNotes olePatronNotes = new OlePatronNotes();
            olePatronNotes.setPatronNoteText(note);
            map.clear();
            map.put("patronNoteTypeCode", "GENERAL");
            OlePatronNoteType olePatronNoteType = (OlePatronNoteType) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(OlePatronNoteType.class, map);
            if(olePatronNoteType != null) {
                olePatronNotes.setPatronNoteTypeId(olePatronNoteType.getPatronNoteTypeId());
                olePatronNotes.setOlePatronId(olePatronDocument.getOlePatronId());
                if(CollectionUtils.isNotEmpty(olePatronDocument.getNotes())) {
                    olePatronDocument.getNotes().add(olePatronNotes);
                } else {
                    List<OlePatronNotes> olePatronNotesList = new ArrayList<>();
                    olePatronNotesList.add(olePatronNotes);
                    olePatronDocument.setNotes(olePatronNotesList);
                }
                KRADServiceLocator.getBusinessObjectService().save(olePatronDocument);
            }
        }
    }

    private String getPatronNoteToRecord(String systemParameter, Map<String, Object> damagedRecordInfo) {
        String note = ParameterValueResolver.getInstance().getParameter(OLEConstants
                .APPL_ID, OLEConstants.DLVR_NMSPC, OLEConstants.DLVR_CMPNT, systemParameter);
        SimpleDateFormat dateFormat = new SimpleDateFormat(RiceConstants.SIMPLE_DATE_FORMAT_FOR_DATE + " " + RiceConstants.SIMPLE_DATE_FORMAT_FOR_TIME);
        String itemBarcode = (String) damagedRecordInfo.get("itemBarcode");
        Date customDate = (Date) damagedRecordInfo.get("customDate");
        String customTime = (String) damagedRecordInfo.get("customTime");
        String selectedCirculationDesk = (String) damagedRecordInfo.get("selectedCirculationDesk");
        if(StringUtils.isNotBlank(itemBarcode)) {
            note = note.replace("[0]", itemBarcode);
        }
        if(customDate != null) {
            try {
                Timestamp customDateTime = new CircUtilController().processDateAndTimeForAlterDueDate(customDate, customTime);
                note = note.replace("[1]",dateFormat.format(customDateTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            note = note.replace("[1]",dateFormat.format(getDateTimeService().getCurrentDate()));
        }
        if(StringUtils.isNotBlank(selectedCirculationDesk)) {
            OleCirculationDesk oleCirculationDesk = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(OleCirculationDesk.class, selectedCirculationDesk);
            if(oleCirculationDesk != null) {
                note = note.replace("[2]", oleCirculationDesk.getCirculationDeskCode());
            }
        }
        return note;
    }

    public DateTimeService getDateTimeService() {
        return (DateTimeService) SpringContext.getService("dateTimeService");
    }
}
